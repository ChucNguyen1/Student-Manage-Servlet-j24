package com.student.controller.admin;

import java.io.IOException;
import java.util.List;

import com.student.model.MonHoc;
import com.student.service.MonHocService;
import com.student.service.impl.MonHocServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = { "/admin/monhoc-list", "/admin/monhoc-add", "/admin/monhoc-edit", "/admin/monhoc-delete",
		"/admin/monhoc-status" })
public class MonHocController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MonHocService service = new MonHocServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		switch (path) {
		case "/admin/monhoc-list":
			showList(req, resp);
			break;
		case "/admin/monhoc-delete":
			handleDelete(req, resp);
			break;
		case "/admin/monhoc-status":
			handleStatus(req, resp);
			break;
		default:
			resp.sendRedirect(req.getContextPath() + "/admin/monhoc-list");
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String path = req.getServletPath();
		HttpSession session = req.getSession();

		if ("/admin/monhoc-add".equals(path))
			handleAdd(req, session);
		else if ("/admin/monhoc-edit".equals(path))
			handleEdit(req, session);

		resp.sendRedirect(req.getContextPath() + "/admin/monhoc-list");
	}

	private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String searchKey = req.getParameter("searchKey");
		int page = 1;
		try {
			page = Integer.parseInt(req.getParameter("page"));
		} catch (Exception e) {
		}

		int pageSize = 10;
		List<MonHoc> list = service.findAndPaginate(searchKey, page, pageSize);
		int totalItems = service.count(searchKey);
		int totalPages = (int) Math.ceil((double) totalItems / pageSize);

		req.setAttribute("dsMonHoc", list);
		req.setAttribute("totalItems", totalItems);
		req.setAttribute("totalPages", totalPages);
		req.setAttribute("currentPage", page);

		req.getRequestDispatcher("/WEB-INF/views/admin/monhoc-list.jsp").forward(req, resp);
	}

	private void handleAdd(HttpServletRequest req, HttpSession session) {
		try {
			String tenMH = req.getParameter("tenMH");
			int soTiet = Integer.parseInt(req.getParameter("soTiet"));
			MonHoc mh = new MonHoc(0, tenMH, soTiet, true);

			if (service.insert(mh))
				session.setAttribute("message", "Thêm môn học thành công!");
			else
				session.setAttribute("error", "Thêm thất bại! Số tiết phải > 0.");
		} catch (Exception e) {
			session.setAttribute("error", "Lỗi dữ liệu: " + e.getMessage());
		}
	}

	private void handleEdit(HttpServletRequest req, HttpSession session) {
		try {
			int maMH = Integer.parseInt(req.getParameter("maMH_edit"));
			String tenMH = req.getParameter("tenMH_edit");
			int soTiet = Integer.parseInt(req.getParameter("soTiet_edit"));
			MonHoc mh = new MonHoc(maMH, tenMH, soTiet, true);

			if (service.update(mh))
				session.setAttribute("message", "Cập nhật thành công!");
			else
				session.setAttribute("error", "Cập nhật thất bại!");
		} catch (Exception e) {
			session.setAttribute("error", "Lỗi dữ liệu: " + e.getMessage());
		}
	}

	private void handleDelete(HttpServletRequest req, HttpServletResponse resp) {
		try {
			int maMH = Integer.parseInt(req.getParameter("id"));
			String result = service.delete(maMH);
			HttpSession session = req.getSession();
			if ("SUCCESS".equals(result))
				session.setAttribute("message", "Đã xóa môn học!");
			else if ("FOREIGN_KEY_ERROR".equals(result))
				session.setAttribute("error", "Không thể xóa! Môn học đang được sử dụng.");
			else
				session.setAttribute("error", "Lỗi hệ thống.");
		} catch (Exception e) {
		}
	}

	private void handleStatus(HttpServletRequest req, HttpServletResponse resp) {
		try {
			int maMH = Integer.parseInt(req.getParameter("id"));
			boolean status = Boolean.parseBoolean(req.getParameter("status"));
			service.updateStatus(maMH, status);
			resp.setStatus(HttpServletResponse.SC_OK);
		} catch (Exception e) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
}