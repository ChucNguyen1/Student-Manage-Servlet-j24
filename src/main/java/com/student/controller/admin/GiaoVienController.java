package com.student.controller.admin;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import com.student.model.GiaoVien;
import com.student.model.MonHoc; // Import Model Môn học
import com.student.service.GiaoVienService;
import com.student.service.MonHocService; // Import Service Môn học
import com.student.service.impl.GiaoVienServiceImpl;
import com.student.service.impl.MonHocServiceImpl;

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

	// Khởi tạo Service Giáo viên
	private GiaoVienService service = new GiaoVienServiceImpl();

	// [QUAN TRỌNG] Khởi tạo Service Môn học để lấy dữ liệu cho Dropdown
	private MonHocService monHocService = new MonHocServiceImpl();

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
			resp.sendRedirect(req.getContextPath() + "/admin/giaovien-list");
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		String path = req.getServletPath();
		HttpSession session = req.getSession();

		if ("/admin/giaovien-add".equals(path)) {
			handleAdd(req, session);
		} else if ("/admin/giaovien-edit".equals(path)) {
			handleEdit(req, session);
		}

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

		int pageSize = 10;

		// 2. Gọi Service lấy danh sách Giáo viên
		List<GiaoVien> list = service.findAndPaginate(searchKey, page, pageSize);
		int totalItems = service.count(searchKey);
		int totalPages = (int) Math.ceil((double) totalItems / pageSize);

		// [QUAN TRỌNG] 3. Gọi Service lấy danh sách Môn học (để đổ vào Dropdown)
		List<MonHoc> listMonHoc = monHocService.findAll();

		// 4. Gửi dữ liệu sang JSP
		req.setAttribute("dsGiaoVien", list);
		req.setAttribute("dsMonHoc", listMonHoc); // <-- Gửi list Môn học sang View

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
			String ngaySinhStr = req.getParameter("ngaySinh");
			String gioiTinh = req.getParameter("gioiTinh");
			String email = req.getParameter("email");
			String sdt = req.getParameter("sdt");
			String diaChi = req.getParameter("diaChi");

			// [SỬA] Lấy Mã Môn Học (int) từ dropdown select name="maMH"
			int maMonHoc = Integer.parseInt(req.getParameter("maMH"));

			// 2. Tạo đối tượng
			GiaoVien gv = new GiaoVien();
			gv.setHoTen(hoTen);
			gv.setGioiTinh(gioiTinh);
			gv.setMaMonHocChuyenMon(maMonHoc); // Set ID môn học
			gv.setEmail(email);
			gv.setSdt(sdt);
			gv.setDiaChi(diaChi);
			gv.setTrangThai(true);

			if (ngaySinhStr != null && !ngaySinhStr.isEmpty()) {
				gv.setNgaySinh(Date.valueOf(ngaySinhStr));
			}

			// 3. Gọi Service
			if (service.insert(gv)) {
				session.setAttribute("message", "Thêm giáo viên thành công!");
			} else {
				session.setAttribute("error", "Thêm thất bại! Email hoặc SĐT có thể đã tồn tại.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
		}
	}

	private void handleEdit(HttpServletRequest req, HttpSession session) {
		try {
			// 1. Lấy ID
			int maGV = Integer.parseInt(req.getParameter("maGV_edit"));

			// 2. Lấy thông tin khác
			String hoTen = req.getParameter("hoTen_edit");
			String ngaySinhStr = req.getParameter("ngaySinh_edit");
			String gioiTinh = req.getParameter("gioiTinh_edit");
			String email = req.getParameter("email_edit");
			String sdt = req.getParameter("sdt_edit");
			String diaChi = req.getParameter("diaChi_edit");

			// [SỬA] Lấy Mã Môn Học (int) từ dropdown select name="maMH_edit"
			int maMonHoc = Integer.parseInt(req.getParameter("maMH_edit"));

			// 3. Tạo đối tượng
			GiaoVien gv = new GiaoVien();
			gv.setMaGV(maGV);
			gv.setHoTen(hoTen);
			gv.setGioiTinh(gioiTinh);
			gv.setMaMonHocChuyenMon(maMonHoc); // Set ID môn học
			gv.setEmail(email);
			gv.setSdt(sdt);
			gv.setDiaChi(diaChi);

			// Không setTrangThai ở đây (vì logic update thường giữ nguyên trạng thái cũ
			// hoặc DAO sẽ tự xử lý chỉ update các trường thông tin)

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

			// Gọi hàm delete (đã được sửa thành xóa mềm trong DAO)
			if (service.delete(maGV)) {
				session.setAttribute("message", "Xóa (khóa) giáo viên thành công!");
			} else {
				session.setAttribute("error", "Xóa thất bại!");
			}
		} catch (Exception e) {
			session.setAttribute("error", "ID không hợp lệ.");
		}
		resp.sendRedirect(req.getContextPath() + "/admin/giaovien-list");
	}
}