package com.student.controller.admin;

import java.io.IOException;
import java.sql.Date; // Dùng để xử lý ngày tháng
import java.util.List;

import com.student.model.GiaoVien;
import com.student.service.GiaoVienService;
import com.student.service.impl.GiaoVienServiceImpl;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = { "/admin/giaovien-list", "/admin/giaovien-add", "/admin/giaovien-edit",
		"/admin/giaovien-delete" })
public class GiaoVienController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private GiaoVienService service = new GiaoVienServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String path = req.getServletPath();

		switch (path) {
		case "/admin/giaovien-list":
			showList(req, resp);
			break;
		case "/admin/giaovien-delete":
			handleDelete(req, resp);
			break;
		default:
			// Các URL add/edit (GET) không dùng vì ta dùng Modal,
			// nhưng để tránh lỗi 404 ta có thể redirect về list
			resp.sendRedirect(req.getContextPath() + "/admin/giaovien-list");
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Xử lý tiếng Việt
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		String path = req.getServletPath();
		HttpSession session = req.getSession();

		if ("/admin/giaovien-add".equals(path)) {
			handleAdd(req, session);
		} else if ("/admin/giaovien-edit".equals(path)) {
			handleEdit(req, session);
		}

		// Sau khi xử lý POST, luôn redirect về trang danh sách
		resp.sendRedirect(req.getContextPath() + "/admin/giaovien-list");
	}

	// --- CÁC HÀM XỬ LÝ CHI TIẾT ---

	private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 1. Lấy tham số phân trang & tìm kiếm
		String searchKey = req.getParameter("searchKey");
		String pageParam = req.getParameter("page");

		int page = 1;
		if (pageParam != null && !pageParam.isEmpty()) {
			try {
				page = Integer.parseInt(pageParam);
			} catch (Exception e) {
				page = 1;
			}
		}

		int pageSize = 10; // Cố định 10 dòng/trang

		// 2. Gọi Service
		List<GiaoVien> list = service.findAndPaginate(searchKey, page, pageSize);
		int totalItems = service.count(searchKey);
		int totalPages = (int) Math.ceil((double) totalItems / pageSize);

		// 3. Gửi dữ liệu sang JSP
		req.setAttribute("dsGiaoVien", list);
		req.setAttribute("totalItems", totalItems);
		req.setAttribute("totalPages", totalPages);
		req.setAttribute("currentPage", page);

		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/admin/giaovien-list.jsp");
		rd.forward(req, resp);
	}

	private void handleAdd(HttpServletRequest req, HttpSession session) {
		try {
			// 1. Lấy dữ liệu từ form
			String hoTen = req.getParameter("hoTen");
			String ngaySinhStr = req.getParameter("ngaySinh"); // Dạng yyyy-MM-dd
			String gioiTinh = req.getParameter("gioiTinh");
			String chuyenMon = req.getParameter("chuyenMon");
			String email = req.getParameter("email");
			String sdt = req.getParameter("sdt");
			String diaChi = req.getParameter("diaChi");

			// 2. Tạo đối tượng
			GiaoVien gv = new GiaoVien();
			gv.setHoTen(hoTen);
			gv.setGioiTinh(gioiTinh);
			gv.setChuyenMon(chuyenMon);
			gv.setEmail(email);
			gv.setSdt(sdt);
			gv.setDiaChi(diaChi);

			// Xử lý ngày sinh (String -> java.sql.Date)
			if (ngaySinhStr != null && !ngaySinhStr.isEmpty()) {
				gv.setNgaySinh(Date.valueOf(ngaySinhStr));
			}

			// 3. Gọi Service
			if (service.insert(gv)) {
				session.setAttribute("message", "Thêm giáo viên thành công!");
			} else {
				session.setAttribute("error", "Thêm thất bại! Có thể Email hoặc SĐT đã tồn tại.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
		}
	}

	private void handleEdit(HttpServletRequest req, HttpSession session) {
		try {
			// 1. Lấy ID (quan trọng)
			int maGV = Integer.parseInt(req.getParameter("maGV_edit"));

			// 2. Lấy các thông tin khác
			String hoTen = req.getParameter("hoTen_edit");
			String ngaySinhStr = req.getParameter("ngaySinh_edit");
			String gioiTinh = req.getParameter("gioiTinh_edit");
			String chuyenMon = req.getParameter("chuyenMon_edit");
			String email = req.getParameter("email_edit");
			String sdt = req.getParameter("sdt_edit");
			String diaChi = req.getParameter("diaChi_edit");

			// 3. Tạo đối tượng
			GiaoVien gv = new GiaoVien();
			gv.setMaGV(maGV);
			gv.setHoTen(hoTen);
			gv.setGioiTinh(gioiTinh);
			gv.setChuyenMon(chuyenMon);
			gv.setEmail(email);
			gv.setSdt(sdt);
			gv.setDiaChi(diaChi);

			if (ngaySinhStr != null && !ngaySinhStr.isEmpty()) {
				gv.setNgaySinh(Date.valueOf(ngaySinhStr));
			}

			// 4. Gọi Service
			if (service.update(gv)) {
				session.setAttribute("message", "Cập nhật giáo viên thành công!");
			} else {
				session.setAttribute("error", "Cập nhật thất bại!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("error", "Lỗi cập nhật: " + e.getMessage());
		}
	}

	private void handleDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession();
		try {
			int maGV = Integer.parseInt(req.getParameter("id"));

			if (service.delete(maGV)) {
				session.setAttribute("message", "Xóa giáo viên thành công!");
			} else {
				session.setAttribute("error", "Xóa thất bại! Giáo viên có thể đang phụ trách lớp học.");
			}
		} catch (Exception e) {
			session.setAttribute("error", "ID không hợp lệ.");
		}
		// Redirect về list
		resp.sendRedirect(req.getContextPath() + "/admin/giaovien-list");
	}
}