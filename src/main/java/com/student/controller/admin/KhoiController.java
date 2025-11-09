package com.student.controller.admin;

import java.io.IOException;
import java.util.List;

import com.student.model.Khoi;
import com.student.service.KhoiService;
import com.student.service.impl.KhoiServiceImpl;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Cập nhật: Servlet này bây giờ xử lý TẤT CẢ các URL liên quan đến Khối
 */
@WebServlet(urlPatterns = { "/admin/khoi-list", "/admin/khoi-add", "/admin/khoi-delete", "/admin/khoi-edit" })
public class KhoiController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private KhoiService khoiService = new KhoiServiceImpl();

	/**
	 * Xử lý request GET Chúng ta cần kiểm tra xem người dùng muốn xem LIST hay xem
	 * FORM ADD
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Lấy đường dẫn URL mà người dùng gọi
		String servletPath = req.getServletPath();

		switch (servletPath) {
		case "/admin/khoi-list":
			showKhoiList(req, resp);
			break;
		case "/admin/khoi-add":
			showAddForm(req, resp);
			break;
		case "/admin/khoi-delete":
			handleDelete(req, resp);
			break;
		default:
			// Trang 404 (tạm thời)
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
	}

	/**
	 * Xử lý request POST (Sẽ làm ở bước 6) Khi người dùng nhấn nút "Lưu" trên form
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Set UTF-8 để xử lý tiếng Việt
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		String servletPath = req.getServletPath();
		HttpSession session = req.getSession();

		if (servletPath.equals("/admin/khoi-add")) {
			System.out.println("Controller: Nhận request /admin/khoi-add (POST).");

			// 1. Lấy dữ liệu từ form (trong Modal)
			// Tên "tenKhoi" phải khớp với thuộc tính 'name' trong <input>
			String tenKhoi = req.getParameter("tenKhoi");
			System.out.println("Controller: Dữ liệu nhận được: tenKhoi=" + tenKhoi);

			// 2. Tạo đối tượng Khoi
			Khoi khoiMoi = new Khoi();
			khoiMoi.setTenKhoi(tenKhoi);

			// 3. Gọi Service để thực hiện thêm mới

			boolean thanhCong = khoiService.insert(khoiMoi);

			if (thanhCong) {
				System.out.println("Controller: Thêm mới thành công.");
				// Đặt thông báo thành công vào session
				session.setAttribute("message", "Thêm mới khối '" + tenKhoi + "' thành công!");
			} else {
				System.out.println("Controller: Thêm mới thất bại.");
				// Đặt thông báo lỗi vào session
				session.setAttribute("error", "Thêm mới thất bại! Vui lòng thử lại.");
			}

			// 4. Chuyển hướng (Redirect) về trang danh sách
			// Redirect sẽ khiến trình duyệt tải lại trang list
			resp.sendRedirect(req.getContextPath() + "/admin/khoi-list");
		} else if (servletPath.equals("/admin/khoi-edit")) {
			// ===================================
			// == XỬ LÝ LOGIC SỬA (UPDATE) ==
			// ===================================
			System.out.println("Controller: Nhận request /admin/khoi-edit (POST).");

			try {
				// 1. Lấy dữ liệu từ form Modal Sửa
				// Tên (maKhoi_edit, tenKhoi_edit) phải khớp 'name' trong <input>
				int maKhoi = Integer.parseInt(req.getParameter("maKhoi_edit"));
				String tenKhoi = req.getParameter("tenKhoi_edit");

				// 2. Tạo đối tượng
				Khoi khoiCapNhat = new Khoi();
				khoiCapNhat.setMaKhoi(maKhoi);
				khoiCapNhat.setTenKhoi(tenKhoi);

				// 3. Gọi Service
				boolean thanhCong = khoiService.update(khoiCapNhat);

				// 4. Đặt thông báo Toast (Tái sử dụng cơ chế)
				if (thanhCong) {
					session.setAttribute("message", "Cập nhật khối '" + tenKhoi + "' thành công!");
				} else {
					session.setAttribute("error", "Cập nhật thất bại!");
				}

			} catch (NumberFormatException e) {
				session.setAttribute("error", "Lỗi: ID khối không hợp lệ.");
			}
		}

		// SAU KHI (add) HOẶC (edit), redirect về trang list
		resp.sendRedirect(req.getContextPath() + "/admin/khoi-list");
	}

	/**
	 * Phương thức hiển thị danh sách (Không thay đổi)
	 */
	private void showKhoiList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 1. Lấy tất cả tham số đầu vào từ URL
		String searchKey = req.getParameter("searchKey");
		String pageParam = req.getParameter("page");
		String entriesParam = req.getParameter("entries");

		// 2. Ép kiểu và đặt giá trị mặc định
		int pageNumber = 1; // Mặc định là trang 1
		if (pageParam != null && !pageParam.isEmpty()) {
			try {
				pageNumber = Integer.parseInt(pageParam);
			} catch (NumberFormatException e) {
				pageNumber = 1; // Nếu nhập bậy, về trang 1
			}
		}

		int pageSize = 10; // Mặc định là 10 mục mỗi trang
		if (entriesParam != null && !entriesParam.isEmpty()) {
			try {
				pageSize = Integer.parseInt(entriesParam);
			} catch (NumberFormatException e) {
				pageSize = 10; // Nếu nhập bậy, về 10
			}
		}

		// 3. Gọi Service (dùng các phương thức mới)

		// Lấy danh sách cho trang hiện tại (đã lọc nếu có searchKey)
		List<Khoi> listKhoi = khoiService.findAndPaginate(searchKey, pageNumber, pageSize);

		// Lấy tổng số bản ghi (cũng đã lọc nếu có searchKey)
		int totalItems = khoiService.count(searchKey);

		// 4. Tính toán tổng số trang
		// Dùng Math.ceil để làm tròn lên (ví dụ: 11 mục / 10 = 1.1 -> 2 trang)
		int totalPages = (int) Math.ceil((double) totalItems / pageSize);

		// 5. Gửi tất cả dữ liệu cần thiết sang JSP
		req.setAttribute("dsKhoi", listKhoi); // Danh sách khối của trang này
		req.setAttribute("totalItems", totalItems); // Tổng số mục (ví dụ: 11)
		req.setAttribute("totalPages", totalPages); // Tổng số trang (ví dụ: 2)
		req.setAttribute("currentPage", pageNumber); // Trang hiện tại (ví dụ: 1)
		req.setAttribute("pageSize", pageSize); // Số mục/trang (ví dụ: 10)

		// (Chúng ta không cần set "searchKey", vì JSP dùng ${param.searchKey}
		// để tự điền vào ô input)

		// 6. Forward đến JSP (không đổi)
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/admin/khoi-list.jsp");
		dispatcher.forward(req, resp);
	}

	/**
	 * PHƯƠNG THỨC MỚI: Hiển thị form thêm mới
	 */
	private void showAddForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		System.out.println("Controller: Nhận request /admin/khoi-add (GET). Đang hiển thị form.");
		// Chỉ cần forward đến file JSP mới
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/admin/khoi-form.jsp");
		dispatcher.forward(req, resp);
	}

	private void handleDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		System.out.println("Controller: Nhận request /admin/khoi-delete (GET).");
		HttpSession session = req.getSession();
		String idParam = req.getParameter("id");

		try {
			int maKhoi = Integer.parseInt(idParam);

			// 2. Gọi Service để xóa
			boolean thanhCong = khoiService.delete(maKhoi);

			// 3. ĐẶT THÔNG BÁO VÀO SESSION (GIỐNG HỆT KHI THÊM MỚI)
			if (thanhCong) {
				session.setAttribute("message", "Đã xóa khối thành công!");
			} else {
				session.setAttribute("error", "Xóa thất bại! Khối có thể đang được sử dụng.");
			}

		} catch (NumberFormatException e) {
			System.out.println("Controller: Lỗi: ID không hợp lệ.");
			session.setAttribute("error", "ID không hợp lệ!");
		}

		// 4. Redirect về trang list
		resp.sendRedirect(req.getContextPath() + "/admin/khoi-list");
	}
}