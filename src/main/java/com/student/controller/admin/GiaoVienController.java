package com.student.controller.admin;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import com.student.model.GiaoVien;
import com.student.model.ToBoMon;
import com.student.service.GiaoVienService;
import com.student.service.ToBoMonService;
import com.student.service.impl.GiaoVienServiceImpl;
import com.student.service.impl.ToBoMonServiceImpl;

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
	private ToBoMonService toBoMonService = new ToBoMonServiceImpl();

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

	private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

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

		List<GiaoVien> list = service.findAndPaginate(searchKey, page, pageSize);
		int totalItems = service.count(searchKey);
		int totalPages = (int) Math.ceil((double) totalItems / pageSize);

		List<ToBoMon> listTo = toBoMonService.findAll();
		req.setAttribute("dsGiaoVien", list);
		req.setAttribute("dsToBoMon", listTo);

		req.setAttribute("totalItems", totalItems);
		req.setAttribute("totalPages", totalPages);
		req.setAttribute("currentPage", page);

		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/admin/giaovien-list.jsp");
		rd.forward(req, resp);
	}

	private void handleAdd(HttpServletRequest req, HttpSession session) {
		try {
			String hoTen = req.getParameter("hoTen");
			String ngaySinhStr = req.getParameter("ngaySinh");
			String gioiTinh = req.getParameter("gioiTinh");
			String email = req.getParameter("email");
			String sdt = req.getParameter("sdt");
			String diaChi = req.getParameter("diaChi");
			String maToStr = req.getParameter("maTo");

			GiaoVien gv = new GiaoVien();
			gv.setHoTen(hoTen);
			gv.setGioiTinh(gioiTinh);
			gv.setEmail(email);
			gv.setSdt(sdt);
			gv.setDiaChi(diaChi);
			gv.setTrangThai(true);

			if (ngaySinhStr != null && !ngaySinhStr.isEmpty()) {
				gv.setNgaySinh(Date.valueOf(ngaySinhStr));
			}

			int maTo = 0;
			if (maToStr != null && !maToStr.isEmpty()) {
				try {
					maTo = Integer.parseInt(maToStr);
				} catch (NumberFormatException e) {
					System.out.println("Lỗi parse mã tổ: " + e.getMessage());
				}
			}
			gv.setMaTo(maTo);

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
			String maGVStr = req.getParameter("maGV_edit");
			if (maGVStr == null || maGVStr.isEmpty()) {
				session.setAttribute("error", "Không tìm thấy ID giáo viên cần sửa!");
				return;
			}
			int maGV = Integer.parseInt(maGVStr);
			String hoTen = req.getParameter("hoTen_edit");
			String ngaySinhStr = req.getParameter("ngaySinh_edit");
			String gioiTinh = req.getParameter("gioiTinh_edit");
			String email = req.getParameter("email_edit");
			String sdt = req.getParameter("sdt_edit");
			String diaChi = req.getParameter("diaChi_edit");
			String maToStr = req.getParameter("maTo_edit");
			GiaoVien gv = new GiaoVien();
			gv.setMaGV(maGV);
			gv.setHoTen(hoTen);
			gv.setGioiTinh(gioiTinh);
			gv.setEmail(email);
			gv.setSdt(sdt);
			gv.setDiaChi(diaChi);

			if (ngaySinhStr != null && !ngaySinhStr.isEmpty()) {
				gv.setNgaySinh(Date.valueOf(ngaySinhStr));
			}

			int maTo = 0;
			if (maToStr != null && !maToStr.isEmpty()) {
				try {
					maTo = Integer.parseInt(maToStr);
				} catch (NumberFormatException e) {
					System.out.println("Lỗi parse mã tổ khi edit");
				}
			}
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