package com.student.controller.admin;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import com.student.model.NamHoc;
import com.student.service.NamHocService;
import com.student.service.impl.NamHocServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = { "/admin/namhoc-list", "/admin/namhoc-add", "/admin/namhoc-edit", "/admin/namhoc-delete",
		"/admin/namhoc-status" })
public class NamHocController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private NamHocService service = new NamHocServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		switch (path) {
		case "/admin/namhoc-list":
			showList(req, resp);
			break;
		case "/admin/namhoc-delete":
			handleDelete(req, resp);
			break;
		default:
			resp.sendRedirect(req.getContextPath() + "/admin/namhoc-list");
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String path = req.getServletPath();
		HttpSession session = req.getSession();

		if ("/admin/namhoc-add".equals(path))
			handleAdd(req, session);
		else if ("/admin/namhoc-edit".equals(path))
			handleEdit(req, session);

		resp.sendRedirect(req.getContextPath() + "/admin/namhoc-list");
	}

	private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String searchKey = req.getParameter("searchKey");
		int page = 1;
		try {
			page = Integer.parseInt(req.getParameter("page"));
		} catch (Exception e) {
		}

		int pageSize = 10;
		List<NamHoc> list = service.findAndPaginate(searchKey, page, pageSize);
		int totalItems = service.count(searchKey);
		int totalPages = (int) Math.ceil((double) totalItems / pageSize);

		req.setAttribute("dsNamHoc", list);
		req.setAttribute("totalItems", totalItems);
		req.setAttribute("totalPages", totalPages);
		req.setAttribute("currentPage", page);

		req.getRequestDispatcher("/WEB-INF/views/admin/namhoc-list.jsp").forward(req, resp);
	}

	private void handleAdd(HttpServletRequest req, HttpSession session) {
		try {
			String maNH = req.getParameter("maNH"); 
			String tenNH = req.getParameter("tenNH");
			Date ngayBatDau = Date.valueOf(req.getParameter("ngayBatDau"));
			Date ngayKetThuc = Date.valueOf(req.getParameter("ngayKetThuc"));

			NamHoc nh = new NamHoc(maNH, tenNH, ngayBatDau, ngayKetThuc, true);

			if (service.insert(nh))
				session.setAttribute("message", "Thêm năm học thành công!");
			else
				session.setAttribute("error", "Thêm thất bại! Mã năm học trùng hoặc ngày không hợp lệ.");
		} catch (Exception e) {
			session.setAttribute("error", "Lỗi dữ liệu: " + e.getMessage());
		}
	}

	private void handleEdit(HttpServletRequest req, HttpSession session) {
		try {
			String maNH = req.getParameter("maNH_edit"); 
			String tenNH = req.getParameter("tenNH_edit");
			Date ngayBatDau = Date.valueOf(req.getParameter("ngayBatDau_edit"));
			Date ngayKetThuc = Date.valueOf(req.getParameter("ngayKetThuc_edit"));

			NamHoc nh = new NamHoc(maNH, tenNH, ngayBatDau, ngayKetThuc, true);

			if (service.update(nh))
				session.setAttribute("message", "Cập nhật thành công!");
			else
				session.setAttribute("error", "Cập nhật thất bại!");
		} catch (Exception e) {
			session.setAttribute("error", "Lỗi dữ liệu: " + e.getMessage());
		}
	}

	// 1. XỬ LÝ XÓA CỨNG
	private void handleDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession();
		String maNH = req.getParameter("id");

		String result = service.delete(maNH); 

		if ("SUCCESS".equals(result)) {
			session.setAttribute("message", "Đã xóa vĩnh viễn năm học!");
		} else if ("FOREIGN_KEY_ERROR".equals(result)) {
			session.setAttribute("error",
					"Không thể xóa! Năm học này đang có dữ liệu (Lớp học/Học kỳ). Hãy tắt trạng thái thay vì xóa.");
		} else {
			session.setAttribute("error", "Lỗi hệ thống khi xóa.");
		}

		resp.sendRedirect(req.getContextPath() + "/admin/namhoc-list");
	}

	// 2. XỬ LÝ SWITCH TRẠNG THÁI 
	private void handleStatus(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String maNH = req.getParameter("id");
		String statusStr = req.getParameter("status");
		boolean newStatus = Boolean.parseBoolean(statusStr);

		service.updateStatus(maNH, newStatus);
		resp.setStatus(HttpServletResponse.SC_OK);
	}
}