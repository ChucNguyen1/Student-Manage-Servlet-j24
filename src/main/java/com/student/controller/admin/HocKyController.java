package com.student.controller.admin;

import java.io.IOException;
import java.util.List;

import com.student.model.HocKy;
import com.student.model.NamHoc;
import com.student.service.HocKyService;
import com.student.service.NamHocService;
import com.student.service.impl.HocKyServiceImpl;
import com.student.service.impl.NamHocServiceImpl;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = { "/admin/hocky-list", "/admin/hocky-add", "/admin/hocky-edit", "/admin/hocky-delete",
		"/admin/hocky-status" })
public class HocKyController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// KHỞI TẠO 2 SERVICE
	private HocKyService hkService = new HocKyServiceImpl();
	private NamHocService nhService = new NamHocServiceImpl(); // <-- Cần cái này cho Dropdown

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String path = req.getServletPath();
		switch (path) {
		case "/admin/hocky-list":
			showList(req, resp);
			break;
		case "/admin/hocky-delete":
			handleDelete(req, resp);
			break;
		case "/admin/hocky-status":
			handleStatus(req, resp);
			break;
		default:
			resp.sendRedirect(req.getContextPath() + "/admin/hocky-list");
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String path = req.getServletPath();
		HttpSession session = req.getSession();

		if ("/admin/hocky-add".equals(path)) {
			handleAdd(req, session);
		} else if ("/admin/hocky-edit".equals(path)) {
			handleEdit(req, session);
		}

		resp.sendRedirect(req.getContextPath() + "/admin/hocky-list");
	}

	// --- CÁC HÀM XỬ LÝ ---

	private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 1. Xử lý phân trang & tìm kiếm cho Học Kỳ
		String searchKey = req.getParameter("searchKey");
		int page = 1;
		try {
			page = Integer.parseInt(req.getParameter("page"));
		} catch (Exception e) {
		}

		int pageSize = 10;
		List<HocKy> listHK = hkService.findAndPaginate(searchKey, page, pageSize);
		int totalItems = hkService.count(searchKey);
		int totalPages = (int) Math.ceil((double) totalItems / pageSize);

		// 2. LẤY DANH SÁCH NĂM HỌC (QUAN TRỌNG)
		// Để JSP có thể dùng trong vòng lặp <c:forEach> tạo <option>
		// Lưu ý: Hàm findAll() này bạn đã thêm vào NamHocService ở bước chuẩn bị
		List<NamHoc> listNamHoc = nhService.findAll();
		if (listNamHoc != null) {
			System.out.println("Controller: Tìm thấy " + listNamHoc.size() + " năm học.");
		} else {
			System.out.println("Controller: Lỗi! listNamHoc bị NULL.");
			// Nếu null, khởi tạo list rỗng để JSP không bị lỗi tiếp theo
			listNamHoc = new java.util.ArrayList<>();
		}
		// 3. Gửi dữ liệu sang JSP
		req.setAttribute("dsHocKy", listHK);
		req.setAttribute("dsNamHoc", listNamHoc); // <-- Gửi list này sang

		req.setAttribute("totalItems", totalItems);
		req.setAttribute("totalPages", totalPages);
		req.setAttribute("currentPage", page);

		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/admin/hocky-list.jsp");
		rd.forward(req, resp);
	}

	private void handleAdd(HttpServletRequest req, HttpSession session) {
		try {
			String tenHK = req.getParameter("tenHK");
			int heSo = Integer.parseInt(req.getParameter("heSo"));
			String maNH = req.getParameter("maNH"); // Lấy ID năm học từ dropdown

			HocKy hk = new HocKy();
			hk.setTenHK(tenHK);
			hk.setHeSo(heSo);
			hk.setMaNH(maNH);

			if (hkService.insert(hk)) {
				session.setAttribute("message", "Thêm học kỳ thành công!");
			} else {
				session.setAttribute("error", "Thêm thất bại! Vui lòng kiểm tra lại.");
			}
		} catch (Exception e) {
			session.setAttribute("error", "Lỗi dữ liệu: " + e.getMessage());
		}
	}

	private void handleEdit(HttpServletRequest req, HttpSession session) {
		try {
			int maHK = Integer.parseInt(req.getParameter("maHK_edit"));
			String tenHK = req.getParameter("tenHK_edit");
			int heSo = Integer.parseInt(req.getParameter("heSo_edit"));
			String maNH = req.getParameter("maNH_edit"); // Lấy ID năm học mới

			HocKy hk = new HocKy();
			hk.setMaHK(maHK);
			hk.setTenHK(tenHK);
			hk.setHeSo(heSo);
			hk.setMaNH(maNH);

			if (hkService.update(hk)) {
				session.setAttribute("message", "Cập nhật thành công!");
			} else {
				session.setAttribute("error", "Cập nhật thất bại!");
			}
		} catch (Exception e) {
			session.setAttribute("error", "Lỗi dữ liệu: " + e.getMessage());
		}
	}

	private void handleDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession();
		try {
			int maHK = Integer.parseInt(req.getParameter("id"));
			String result = hkService.delete(maHK);

			if ("SUCCESS".equals(result)) {
				session.setAttribute("message", "Đã xóa học kỳ!");
			} else if ("FOREIGN_KEY_ERROR".equals(result)) {
				session.setAttribute("error", "Không thể xóa! Học kỳ này đang có dữ liệu điểm số/phân công.");
			} else {
				session.setAttribute("error", "Lỗi hệ thống.");
			}
		} catch (Exception e) {
			session.setAttribute("error", "ID không hợp lệ.");
		}
		resp.sendRedirect(req.getContextPath() + "/admin/hocky-list");
	}

	private void handleStatus(HttpServletRequest req, HttpServletResponse resp) {
		try {
			int maHK = Integer.parseInt(req.getParameter("id"));
			boolean status = Boolean.parseBoolean(req.getParameter("status"));
			hkService.updateStatus(maHK, status);
			resp.setStatus(HttpServletResponse.SC_OK);
		} catch (Exception e) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
}