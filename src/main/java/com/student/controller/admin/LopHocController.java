package com.student.controller.admin;

import java.io.IOException;
import java.util.List;

import com.student.model.GiaoVien;
import com.student.model.Khoi;
import com.student.model.LopHoc;
import com.student.model.NamHoc;
import com.student.service.GiaoVienService;
import com.student.service.KhoiService;
import com.student.service.LopHocService;
import com.student.service.NamHocService;
import com.student.service.impl.GiaoVienServiceImpl;
import com.student.service.impl.KhoiServiceImpl;
import com.student.service.impl.LopHocServiceImpl;
import com.student.service.impl.NamHocServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = { "/admin/lophoc-list", "/admin/lophoc-add", "/admin/lophoc-edit", "/admin/lophoc-delete",
		"/admin/lophoc-status" })
public class LopHocController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Khởi tạo 4 Service
	private LopHocService lopService = new LopHocServiceImpl();
	private KhoiService khoiService = new KhoiServiceImpl();
	private NamHocService namHocService = new NamHocServiceImpl();
	private GiaoVienService giaoVienService = new GiaoVienServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		switch (path) {
		case "/admin/lophoc-list":
			showList(req, resp);
			break;
		case "/admin/lophoc-delete":
			handleDelete(req, resp);
			break;
		case "/admin/lophoc-status":
			handleStatus(req, resp);
			break;
		default:
			resp.sendRedirect(req.getContextPath() + "/admin/lophoc-list");
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String path = req.getServletPath();
		HttpSession session = req.getSession();

		if ("/admin/lophoc-add".equals(path))
			handleAdd(req, session);
		else if ("/admin/lophoc-edit".equals(path))
			handleEdit(req, session);

		resp.sendRedirect(req.getContextPath() + "/admin/lophoc-list");
	}

	private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String searchKey = req.getParameter("searchKey");
		int page = 1;
		try {
			page = Integer.parseInt(req.getParameter("page"));
		} catch (Exception e) {
		}

		int pageSize = 10;
		List<LopHoc> listLop = lopService.findAndPaginate(searchKey, page, pageSize);
		int totalItems = lopService.count(searchKey);
		int totalPages = (int) Math.ceil((double) totalItems / pageSize);

		// --- LẤY DỮ LIỆU CHO 3 DROPDOWN ---
		List<Khoi> listKhoi = khoiService.findAll();
		List<NamHoc> listNamHoc = namHocService.findAll();
		List<GiaoVien> listGiaoVien = giaoVienService.findAll();

		req.setAttribute("dsLopHoc", listLop);
		req.setAttribute("dsKhoi", listKhoi); // Dropdown Khối
		req.setAttribute("dsNamHoc", listNamHoc); // Dropdown Năm học
		req.setAttribute("dsGiaoVien", listGiaoVien); // Dropdown GVCN

		req.setAttribute("totalItems", totalItems);
		req.setAttribute("totalPages", totalPages);
		req.setAttribute("currentPage", page);

		req.getRequestDispatcher("/WEB-INF/views/admin/lophoc-list.jsp").forward(req, resp);
	}

	private void handleAdd(HttpServletRequest req, HttpSession session) {
		try {
			String tenLop = req.getParameter("tenLop");
			int maKhoi = Integer.parseInt(req.getParameter("maKhoi"));
			String maNH = req.getParameter("maNH");

			// Xử lý GVCN (có thể null nếu chưa chọn)
			String gvcnStr = req.getParameter("maGVCN");
			int maGVCN = (gvcnStr != null && !gvcnStr.isEmpty()) ? Integer.parseInt(gvcnStr) : 0;

			LopHoc lh = new LopHoc();
			lh.setTenLop(tenLop);
			lh.setMaKhoi(maKhoi);
			lh.setMaNH(maNH);
			lh.setMaGVCN(maGVCN);

			if (lopService.insert(lh))
				session.setAttribute("message", "Thêm lớp thành công!");
			else
				session.setAttribute("error", "Thêm thất bại! Tên lớp có thể đã trùng.");
		} catch (Exception e) {
			session.setAttribute("error", "Lỗi dữ liệu: " + e.getMessage());
		}
	}

	private void handleEdit(HttpServletRequest req, HttpSession session) {
		try {
			int maLop = Integer.parseInt(req.getParameter("maLop_edit"));
			String tenLop = req.getParameter("tenLop_edit");
			int maKhoi = Integer.parseInt(req.getParameter("maKhoi_edit"));
			String maNH = req.getParameter("maNH_edit");

			String gvcnStr = req.getParameter("maGVCN_edit");
			int maGVCN = (gvcnStr != null && !gvcnStr.isEmpty()) ? Integer.parseInt(gvcnStr) : 0;

			LopHoc lh = new LopHoc();
			lh.setMaLop(maLop);
			lh.setTenLop(tenLop);
			lh.setMaKhoi(maKhoi);
			lh.setMaNH(maNH);
			lh.setMaGVCN(maGVCN);

			if (lopService.update(lh))
				session.setAttribute("message", "Cập nhật thành công!");
			else
				session.setAttribute("error", "Cập nhật thất bại!");
		} catch (Exception e) {
			session.setAttribute("error", "Lỗi dữ liệu: " + e.getMessage());
		}
	}

	private void handleDelete(HttpServletRequest req, HttpServletResponse resp) {
		try {
			int maLop = Integer.parseInt(req.getParameter("id"));
			String result = lopService.delete(maLop);
			HttpSession session = req.getSession();

			if ("SUCCESS".equals(result))
				session.setAttribute("message", "Đã xóa lớp học!");
			else if ("FOREIGN_KEY_ERROR".equals(result))
				session.setAttribute("error", "Không thể xóa! Lớp học đang có học sinh.");
			else
				session.setAttribute("error", "Lỗi hệ thống.");
		} catch (Exception e) {
		}
	}

	private void handleStatus(HttpServletRequest req, HttpServletResponse resp) {
		try {
			int maLop = Integer.parseInt(req.getParameter("id"));
			boolean status = Boolean.parseBoolean(req.getParameter("status"));
			lopService.updateStatus(maLop, status);
			resp.setStatus(HttpServletResponse.SC_OK);
		} catch (Exception e) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
}