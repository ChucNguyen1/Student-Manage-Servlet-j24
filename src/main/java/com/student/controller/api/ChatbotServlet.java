package com.student.controller.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.student.dao.*;
import com.student.dao.impl.*;
import com.student.model.*;
import com.student.service.ThongBaoService;
import com.student.service.impl.ThongBaoServiceImpl;
import com.student.utils.ConfigLoader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Chatbot API Servlet
 * Xử lý câu hỏi từ chatbot và trả về dữ liệu thực từ database
 */
@WebServlet("/api/student/chatbot")
public class ChatbotServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final HocSinhDAO hocSinhDAO;
	private final LopHocDAO lopHocDAO;
	private final ThoiKhoaBieuDAO thoiKhoaBieuDAO;
	private final DiemChiTietDAO diemChiTietDAO;
	private final HocKyDAO hocKyDAO;
	private final ThongBaoService thongBaoService;
	private final DocThongBaoDAO docThongBaoDAO;
	private final Gson gson;
	
	// Gemini AI configuration
	private static final String GEMINI_API_KEY = getGeminiApiKey();
	private static final String GEMINI_MODEL = "gemini-2.5-flash"; // Updated to latest available model
	private static final String GEMINI_API_BASE = "https://generativelanguage.googleapis.com/v1beta/models/";

	public ChatbotServlet() {
		this.hocSinhDAO = new HocSinhDAOImpl();
		this.lopHocDAO = new LopHocDAOImpl();
		this.thoiKhoaBieuDAO = new ThoiKhoaBieuDAOImpl();
		this.diemChiTietDAO = new DiemChiTietDAOImpl();
		this.hocKyDAO = new HocKyDAOImpl();
		this.thongBaoService = new ThongBaoServiceImpl();
		this.docThongBaoDAO = new DocThongBaoDAOImpl();
		this.gson = new Gson();
	}
	
	/**
	 * Lấy Gemini API key
	 * 
	 * Thứ tự ưu tiên:
	 * 1. Biến môi trường GEMINI_API_KEY (Production)
	 * 2. File config.properties (Development)
	 * 3. Fallback mode nếu không có API key
	 */
	private static String getGeminiApiKey() {
		// 1. Thử lấy từ environment variable (Production server)
		String apiKey = System.getenv("GEMINI_API_KEY");
		if (apiKey != null && !apiKey.trim().isEmpty()) {
			return apiKey;
		}
		
		// 2. Thử lấy từ config file (Development)
		apiKey = ConfigLoader.get("gemini.api.key");
		if (apiKey != null && !apiKey.trim().isEmpty() && 
		    !"YOUR_GEMINI_API_KEY_HERE".equals(apiKey) && 
		    !"YOUR_API_KEY_HERE".equals(apiKey)) {
			return apiKey;
		}
		
		// 3. Không có API key - sẽ dùng fallback mode
		System.err.println("⚠️  Chatbot: Không tìm thấy Gemini API key!");
		System.err.println("💡 Chatbot sẽ chạy ở chế độ fallback (không có AI)");
		System.err.println("📝 Để bật AI:");
		System.err.println("   - Tạo file src/main/resources/config.properties");
		System.err.println("   - Copy từ config.properties.example");
		System.err.println("   - Thêm: gemini.api.key=AIzaSy...");
		System.err.println("   - Hoặc set biến môi trường: GEMINI_API_KEY");
		
		return null; // Sẽ trigger fallback mode
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");

		// Kiểm tra session
		HttpSession session = request.getSession(false);
		if (session == null) {
			sendError(response, "Chưa đăng nhập");
			return;
		}

		TaiKhoan account = (TaiKhoan) session.getAttribute("account");
		if (account == null || !"HOCSINH".equals(account.getRole())) {
			sendError(response, "Không có quyền truy cập");
			return;
		}

		Integer maHS = account.getMaHS();
		if (maHS == null) {
			sendError(response, "Không tìm thấy thông tin học sinh");
			return;
		}

		// Đọc request body
		String requestBody = request.getReader().lines().collect(Collectors.joining());
		JsonObject jsonRequest = gson.fromJson(requestBody, JsonObject.class);
		String message = jsonRequest.get("message").getAsString();
		
		// Lấy context từ request (nếu có)
		String context = null;
		if (jsonRequest.has("context") && !jsonRequest.get("context").isJsonNull()) {
			context = jsonRequest.get("context").getAsString();
		}

		// Xử lý câu hỏi với context
		ChatResponse chatResponse = processQuestion(message, context, maHS);

		// Trả về kết quả
		JsonObject jsonResponse = new JsonObject();
		jsonResponse.addProperty("success", true);
		jsonResponse.addProperty("answer", chatResponse.answer);
		if (chatResponse.context != null) {
			jsonResponse.addProperty("context", chatResponse.context);
		}

		PrintWriter out = response.getWriter();
		out.print(gson.toJson(jsonResponse));
		out.flush();
	}
	
	/**
	 * Chat Response class để trả về answer + context
	 */
	private static class ChatResponse {
		String answer;
		String context; // Lưu context cho câu hỏi tiếp theo (VD: "subject:Toán")
		
		ChatResponse(String answer, String context) {
			this.answer = answer;
			this.context = context;
		}
		
		ChatResponse(String answer) {
			this(answer, null);
		}
	}

	/**
	 * Xử lý câu hỏi và trả về câu trả lời (SỬ DỤNG GEMINI AI)
	 */
	private ChatResponse processQuestion(String message, String context, int maHS) {
		try {
			// Lấy thông tin học sinh
			HocSinh hocSinh = hocSinhDAO.findById(maHS);
			if (hocSinh == null) {
				return new ChatResponse("Không tìm thấy thông tin của bạn trong hệ thống. 😢");
			}

			// Lấy lớp học của học sinh để xác định năm học
			LopHoc lopHoc = lopHocDAO.findById(hocSinh.getMaLop());
			if (lopHoc == null) {
				return new ChatResponse("Không tìm thấy thông tin lớp học. Vui lòng liên hệ phòng đào tạo! 📞");
			}

			// Lấy học kỳ theo năm học của lớp học sinh
			int currentHocKy = getCurrentHocKyByNamHoc(lopHoc.getMaNH());
			if (currentHocKy == 0) {
				return new ChatResponse("Hệ thống chưa có học kỳ nào cho năm học " + lopHoc.getTenNamHoc() + ". Vui lòng liên hệ phòng đào tạo! 📞");
			}

			// Lấy dữ liệu thực từ database
			String studentData = gatherStudentData(maHS, hocSinh, currentHocKy, lopHoc.getTenNamHoc(), message, context);
			
			// Gọi Gemini AI để xử lý câu hỏi
			String aiResponse = callGeminiForChat(message, studentData, context);
			
			// Phát hiện context cho câu hỏi tiếp theo
			String newContext = detectContext(message, aiResponse);
			
			return new ChatResponse(aiResponse, newContext);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ChatResponse("Xin lỗi, tôi đang gặp vấn đề kỹ thuật. Vui lòng thử lại sau! 😅");
		}
	}
	
	/**
	 * Thu thập dữ liệu học sinh để cung cấp cho AI
	 */
	private String gatherStudentData(int maHS, HocSinh hocSinh, int currentHocKy, String tenNamHoc, String message, String context) {
		StringBuilder data = new StringBuilder();
		
		// Thông tin cơ bản
		data.append("=== THÔNG TIN HỌC SINH ===\n");
		data.append(String.format("Họ tên: %s\n", hocSinh.getHoTen()));
		data.append(String.format("Lớp: %s\n", hocSinh.getTenLop()));
		data.append(String.format("Năm học: %s\n", tenNamHoc != null ? tenNamHoc : "N/A"));
		data.append(String.format("Giới tính: %s\n", hocSinh.getGioiTinh()));
		data.append("\n");
		
		// Điểm các môn học
		data.append("=== BẢNG ĐIỂM NĂM HỌC " + (tenNamHoc != null ? tenNamHoc : "HIỆN TẠI") + " ===\n");
		List<DiemChiTiet> dsDiem = diemChiTietDAO.getBangDiemCaNhan(maHS, currentHocKy);
		if (!dsDiem.isEmpty()) {
			for (DiemChiTiet d : dsDiem) {
				data.append(String.format("\nMôn: %s\n", d.getTenMonHoc()));
				data.append(String.format("  - Điểm miệng: %s, %s, %s\n", 
					formatScore(d.getDiemMieng1()), formatScore(d.getDiemMieng2()), formatScore(d.getDiemMieng3())));
				data.append(String.format("  - Điểm 15p: %s, %s, %s\n",
					formatScore(d.getDiem15p1()), formatScore(d.getDiem15p2()), formatScore(d.getDiem15p3())));
				data.append(String.format("  - Điểm 1 tiết: %s, %s\n",
					formatScore(d.getDiem1Tiet1()), formatScore(d.getDiem1Tiet2())));
				data.append(String.format("  - Điểm thi: %s\n", formatScore(d.getDiemThi())));
				data.append(String.format("  - Điểm TB: %.1f\n", calculateDiemTB(d)));
			}
		} else {
			data.append("Chưa có điểm nào.\n");
		}
		data.append("\n");
		
		// Thời khóa biểu
		data.append("=== THỜI KHÓA BIỂU ===\n");
		if (hocSinh.getMaLop() > 0) {
			List<ThoiKhoaBieu> dsTKB = thoiKhoaBieuDAO.findByLopAndHocKy(hocSinh.getMaLop(), currentHocKy);
			if (!dsTKB.isEmpty()) {
				Map<Integer, List<ThoiKhoaBieu>> tkbByDay = dsTKB.stream()
					.collect(Collectors.groupingBy(ThoiKhoaBieu::getThu));
				
				for (int thu = 2; thu <= 7; thu++) {
					List<ThoiKhoaBieu> daySchedule = tkbByDay.get(thu);
					if (daySchedule != null && !daySchedule.isEmpty()) {
						data.append(String.format("Thứ %d:\n", thu));
						daySchedule.sort(Comparator.comparingInt(ThoiKhoaBieu::getTiet));
						for (ThoiKhoaBieu tkb : daySchedule) {
							data.append(String.format("  Tiết %d: %s (Phòng %s)\n", 
								tkb.getTiet(), tkb.getTenMonHoc(), tkb.getPhongHoc()));
						}
					}
				}
			}
		}
		data.append("\n");
		
		// Thông báo gần đây
		data.append("=== THÔNG BÁO MỚI NHẤT ===\n");
		List<ThongBao> thongBaos = thongBaoService.findAll("", 1, 5);
		if (!thongBaos.isEmpty()) {
			for (ThongBao tb : thongBaos) {
				boolean chuaDoc = !docThongBaoDAO.isRead(maHS, tb.getMaTB());
				data.append(String.format("[ID: %d] [%s] %s - %s\n", 
					tb.getMaTB(),
					chuaDoc ? "CHƯA ĐỌC" : "Đã đọc",
					tb.getTieuDe(),
					new java.text.SimpleDateFormat("dd/MM/yyyy").format(tb.getNgayDang())));
			}
		} else {
			data.append("Chưa có thông báo nào.\n");
		}
		
		// Context từ câu hỏi trước
		if (context != null && !context.isEmpty()) {
			data.append("\n=== NGỮ CẢNH CÂU HỎI TRƯỚC ===\n");
			data.append(context).append("\n");
		}
		
		// Kiểm tra nếu hỏi về tóm tắt thông báo cụ thể
		String msg = message != null ? message.toLowerCase() : "";
		if (msg.contains("tóm tắt") || msg.contains("tom tat")) {
			// Tìm ID thông báo trong câu hỏi
			Integer tbId = extractThongBaoId(message, thongBaos);
			if (tbId != null) {
				ThongBao tb = thongBaoService.findById(tbId);
				if (tb != null) {
					data.append("\n=== NỘI DUNG THÔNG BÁO CẦN TÓM TẮT ===\n");
					data.append(String.format("Tiêu đề: %s\n", tb.getTieuDe()));
					data.append(String.format("Ngày: %s\n", new java.text.SimpleDateFormat("dd/MM/yyyy").format(tb.getNgayDang())));
					data.append(String.format("Nội dung đầy đủ:\n%s\n", tb.getNoiDung()));
				}
			}
		}
		
		return data.toString();
	}
	
	/**
	 * Trích xuất ID thông báo từ câu hỏi
	 */
	private Integer extractThongBaoId(String message, List<ThongBao> thongBaos) {
		if (message == null || thongBaos == null) return null;
		
		String msg = message.toLowerCase();
		
		// Tìm theo ID số (VD: "thông báo 1", "ID 2")
		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("(?:id|thông báo|thong bao)\\s*(\\d+)");
		java.util.regex.Matcher matcher = pattern.matcher(msg);
		if (matcher.find()) {
			try {
				return Integer.parseInt(matcher.group(1));
			} catch (NumberFormatException e) {
				// Ignore
			}
		}
		
		// Tìm theo từ khóa trong tiêu đề
		for (ThongBao tb : thongBaos) {
			String tieuDe = tb.getTieuDe().toLowerCase();
			// Tách tiêu đề thành các từ quan trọng
			String[] keywords = tieuDe.split("\\s+");
			int matchCount = 0;
			for (String keyword : keywords) {
				if (keyword.length() > 3 && msg.contains(keyword)) {
					matchCount++;
				}
			}
			// Nếu khớp nhiều hơn 50% từ khóa
			if (matchCount >= keywords.length / 2 && matchCount > 0) {
				return tb.getMaTB();
			}
		}
		
		return null;
	}
	
	/**
	 * Gọi Gemini AI để xử lý câu hỏi chat
	 */
	@SuppressWarnings("deprecation")
	private String callGeminiForChat(String userQuestion, String studentData, String previousContext) throws Exception {
		// Kiểm tra API key
		if (GEMINI_API_KEY == null || GEMINI_API_KEY.trim().isEmpty()) {
			System.err.println("⚠️  Chatbot: API key null, sử dụng fallback");
			return useFallbackResponse(userQuestion, studentData);
		}
		
		// Tạo prompt cho Gemini
		String prompt = buildChatPrompt(userQuestion, studentData, previousContext);
		
		// Construct URL with API key
		String apiUrl = GEMINI_API_BASE + GEMINI_MODEL + ":generateContent?key=" + GEMINI_API_KEY;
		
		// Gọi API
		URL url = new URL(apiUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setDoOutput(true);
		conn.setConnectTimeout(10000);
		conn.setReadTimeout(10000);
		
		// Tạo request body
		JsonObject requestBody = new JsonObject();
		com.google.gson.JsonArray contents = new com.google.gson.JsonArray();
		JsonObject content = new JsonObject();
		com.google.gson.JsonArray parts = new com.google.gson.JsonArray();
		JsonObject part = new JsonObject();
		part.addProperty("text", prompt);
		parts.add(part);
		content.add("parts", parts);
		contents.add(content);
		requestBody.add("contents", contents);
		
		// Gửi request
		try (OutputStream os = conn.getOutputStream()) {
			byte[] input = requestBody.toString().getBytes("utf-8");
			os.write(input, 0, input.length);
		}
		
		// Đọc response
		int responseCode = conn.getResponseCode();
		if (responseCode == 200) {
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			StringBuilder response = new StringBuilder();
			String responseLine;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			br.close();
			
			// Parse JSON response
			JsonObject jsonResponse = gson.fromJson(response.toString(), JsonObject.class);
			String aiText = jsonResponse.getAsJsonArray("candidates")
				.get(0).getAsJsonObject()
				.get("content").getAsJsonObject()
				.get("parts").getAsJsonArray()
				.get(0).getAsJsonObject()
				.get("text").getAsString();
			
			return aiText;
		} else {
			// Đọc error response để debug
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"));
			StringBuilder errorResponse = new StringBuilder();
			String errorLine;
			while ((errorLine = br.readLine()) != null) {
				errorResponse.append(errorLine);
			}
			br.close();
			
			System.err.println("❌ Gemini API error: " + responseCode);
			System.err.println("   Error details: " + errorResponse.toString());
			System.err.println("   API URL: " + apiUrl.substring(0, apiUrl.indexOf("?key=")));
			return useFallbackResponse(userQuestion, studentData);
		}
	}
	
	/**
	 * Tạo prompt cho Gemini
	 */
	private String buildChatPrompt(String userQuestion, String studentData, String previousContext) {
		StringBuilder prompt = new StringBuilder();
		
		prompt.append("Bạn là trợ lý ảo thông minh của hệ thống quản lý học sinh.\n\n");
		prompt.append("NHIỆM VỤ:\n");
		prompt.append("- Trả lời câu hỏi của học sinh dựa trên dữ liệu thực được cung cấp\n");
		prompt.append("- Phong cách: Thân thiện, nhiệt tình, dễ hiểu, sử dụng emoji phù hợp\n");
		prompt.append("- Trả lời NGẮN GỌN (3-5 câu), trừ khi cần liệt kê dữ liệu\n");
		prompt.append("- Nếu hỏi về điểm: Hiển thị ĐẦY ĐỦ các cột điểm (miệng, 15p, 1 tiết, thi)\n");
		prompt.append("- Nếu hỏi về cải thiện: Đưa lời khuyên CỤ THỂ dựa trên điểm số\n");
		prompt.append("- Nếu hỏi về thời khóa biểu: Liệt kê rõ ràng theo thứ/tiết\n");
		prompt.append("- Nếu hỏi về thông báo mới: Liệt kê các thông báo với ID, trạng thái đọc, tiêu đề và ngày\n");
		prompt.append("- Nếu hỏi TÓM TẮT thông báo: Tóm tắt các ý chính của nội dung thông báo một cách súc tích (3-5 điểm chính)\n\n");
		
		prompt.append("DỮ LIỆU HỌC SINH:\n");
		prompt.append(studentData);
		prompt.append("\n");
		
		if (previousContext != null && !previousContext.isEmpty()) {
			prompt.append("NGỮ CẢNH CÂU TRƯỚC: ").append(previousContext).append("\n\n");
		}
		
		prompt.append("CÂU HỎI CỦA HỌC SINH: ").append(userQuestion).append("\n\n");
		prompt.append("TRẢ LỜI (sử dụng markdown và emoji):");
		
		return prompt.toString();
	}
	
	/**
	 * Phát hiện context cho câu hỏi tiếp theo
	 */
	private String detectContext(String question, String aiResponse) {
		String q = question.toLowerCase();
		String r = aiResponse.toLowerCase();
		
		// Phát hiện đang hỏi về môn học cụ thể
		String[] subjects = {"toán", "văn", "anh", "lý", "hóa", "sinh", "sử", "địa", "gdcd", "tin", "thể dục", "nghề"};
		for (String subject : subjects) {
			if (q.contains(subject) || r.contains("môn " + subject)) {
				return "subject:" + capitalizeFirstLetter(subject);
			}
		}
		
		return null;
	}
	
	/**
	 * Fallback response khi không có API key hoặc API lỗi
	 */
	private String useFallbackResponse(String question, String studentData) {
		String q = question.toLowerCase();
		
		// Parse dữ liệu để trả lời cơ bản
		if (q.contains("điểm") || q.contains("diem")) {
			if (studentData.contains("=== BẢNG ĐIỂM")) {
				int start = studentData.indexOf("=== BẢNG ĐIỂM");
				int end = studentData.indexOf("=== THỜI KHÓA BIỂU");
				if (end > start) {
					return "📊 **Bảng điểm của bạn:**\n\n" + 
						studentData.substring(start, end).replace("=== BẢNG ĐIỂM HỌC KỲ HIỆN TẠI ===", "");
				}
			}
			return "Bạn chưa có điểm nào trong học kỳ này.";
		}
		
		if (q.contains("tkb") || q.contains("thời khóa biểu") || q.contains("lịch học")) {
			if (studentData.contains("=== THỜI KHÓA BIỂU")) {
				int start = studentData.indexOf("=== THỜI KHÓA BIỂU");
				int end = studentData.indexOf("=== THÔNG BÁO");
				if (end > start) {
					return "📅 **Thời khóa biểu của bạn:**\n\n" +
						studentData.substring(start, end).replace("=== THỜI KHÓA BIỂU ===", "");
				}
			}
			return "Lớp của bạn chưa có thời khóa biểu.";
		}
		
		if (q.contains("thông báo") || q.contains("thong bao")) {
			if (studentData.contains("=== THÔNG BÁO")) {
				int start = studentData.indexOf("=== THÔNG BÁO");
				return "📢 **Thông báo mới nhất:**\n\n" +
					studentData.substring(start).replace("=== THÔNG BÁO MỚI NHẤT ===", "");
			}
			return "Hiện chưa có thông báo nào.";
		}
		
		return "Xin chào! Tôi có thể giúp bạn:\n" +
			"- Xem điểm học tập\n" +
			"- Xem thời khóa biểu\n" +
			"- Xem thông báo mới\n" +
			"- Hỏi về học tập\n\n" +
			"Hãy thử hỏi tôi nhé! 😊";
	}
	
	private String capitalizeFirstLetter(String str) {
		if (str == null || str.isEmpty()) return str;
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	/**
	 * Format điểm số (null → "Chưa có")
	 */
	private String formatScore(Double score) {
		if (score == null || score == 0.0) {
			return "Chưa có";
		}
		return String.format("%.1f", score);
	}

	/**
	 * Tính điểm trung bình môn
	 * Công thức: (ĐM + Đ15p + Đ1T*2 + ĐThi*3) / 7
	 */
	private double calculateDiemTB(DiemChiTiet d) {
		// Nếu có điemTBM (đã tính sẵn), dùng luôn
		if (d.getDiemTBM() != null && d.getDiemTBM() > 0) {
			return d.getDiemTBM();
		}

		// Tính trung bình điểm miệng
		double diemMieng = 0;
		int countMieng = 0;
		if (d.getDiemMieng1() != null && d.getDiemMieng1() > 0) { diemMieng += d.getDiemMieng1(); countMieng++; }
		if (d.getDiemMieng2() != null && d.getDiemMieng2() > 0) { diemMieng += d.getDiemMieng2(); countMieng++; }
		if (d.getDiemMieng3() != null && d.getDiemMieng3() > 0) { diemMieng += d.getDiemMieng3(); countMieng++; }
		diemMieng = countMieng > 0 ? diemMieng / countMieng : 0;

		// Tính trung bình điểm 15p
		double diem15p = 0;
		int count15p = 0;
		if (d.getDiem15p1() != null && d.getDiem15p1() > 0) { diem15p += d.getDiem15p1(); count15p++; }
		if (d.getDiem15p2() != null && d.getDiem15p2() > 0) { diem15p += d.getDiem15p2(); count15p++; }
		if (d.getDiem15p3() != null && d.getDiem15p3() > 0) { diem15p += d.getDiem15p3(); count15p++; }
		diem15p = count15p > 0 ? diem15p / count15p : 0;

		// Tính trung bình điểm 1 tiết
		double diem1Tiet = 0;
		int count1Tiet = 0;
		if (d.getDiem1Tiet1() != null && d.getDiem1Tiet1() > 0) { diem1Tiet += d.getDiem1Tiet1(); count1Tiet++; }
		if (d.getDiem1Tiet2() != null && d.getDiem1Tiet2() > 0) { diem1Tiet += d.getDiem1Tiet2(); count1Tiet++; }
		diem1Tiet = count1Tiet > 0 ? diem1Tiet / count1Tiet : 0;

		// Điểm thi
		double diemThi = (d.getDiemThi() != null && d.getDiemThi() > 0) ? d.getDiemThi() : 0;

		// Tính điểm TB theo công thức: (ĐM + Đ15p + Đ1T*2 + ĐThi*3) / 7
		if (diemThi == 0) {
			// Nếu chưa có điểm thi, tính tạm không kể điểm thi
			double sum = diemMieng + diem15p + diem1Tiet * 2;
			double weight = 1 + 1 + 2; // 4
			return weight > 0 ? sum / weight : 0;
		}

		double sum = diemMieng + diem15p + diem1Tiet * 2 + diemThi * 3;
		return sum / 7.0;
	}

	/**
	 * Lấy học kỳ hiện tại (đang hoạt động) - DEPRECATED
	 * Sử dụng getCurrentHocKyByNamHoc() thay thế
	 */
	@Deprecated
	private int getCurrentHocKy() {
		List<HocKy> dsHocKy = hocKyDAO.findAll();
		for (HocKy hk : dsHocKy) {
			if (hk.isTrangThai()) {
				return hk.getMaHK();
			}
		}
		return dsHocKy.isEmpty() ? 0 : dsHocKy.get(0).getMaHK();
	}
	
	/**
	 * Lấy học kỳ theo năm học của lớp học sinh (ưu tiên học kỳ đang hoạt động)
	 */
	private int getCurrentHocKyByNamHoc(String maNH) {
		if (maNH == null || maNH.trim().isEmpty()) {
			return getCurrentHocKy(); // Fallback
		}
		
		List<HocKy> dsHocKy = hocKyDAO.findByNamHoc(maNH);
		if (dsHocKy.isEmpty()) {
			return 0;
		}
		
		// Ưu tiên học kỳ đang hoạt động
		for (HocKy hk : dsHocKy) {
			if (hk.isTrangThai()) {
				return hk.getMaHK();
			}
		}
		
		// Nếu không có học kỳ nào đang hoạt động, lấy học kỳ đầu tiên
		return dsHocKy.get(0).getMaHK();
	}

	/**
	 * Gửi lỗi JSON
	 */
	private void sendError(HttpServletResponse response, String message) throws IOException {
		JsonObject jsonResponse = new JsonObject();
		jsonResponse.addProperty("success", false);
		jsonResponse.addProperty("error", message);

		PrintWriter out = response.getWriter();
		out.print(gson.toJson(jsonResponse));
		out.flush();
	}
}
