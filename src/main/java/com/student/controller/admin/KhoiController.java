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

@WebServlet(urlPatterns = { "/admin/khoi-list", "/admin/khoi-add", "/admin/khoi-delete", "/admin/khoi-edit" })
public class KhoiController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private KhoiService khoiService = new KhoiServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
	}

	/**
	 * Xử lý request POST
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		String servletPath = req.getServletPath();
		HttpSession session = req.getSession();

		if (servletPath.equals("/admin/khoi-add")) {
			System.out.println("Controller: Nhận request /admin/khoi-add (POST).");
			String tenKhoi = req.getParameter("tenKhoi");
			System.out.println("Controller: Dữ liệu nhận được: tenKhoi=" + tenKhoi);
			Khoi khoiMoi = new Khoi();
			khoiMoi.setTenKhoi(tenKhoi);
			boolean thanhCong = khoiService.insert(khoiMoi);

			if (thanhCong) {
				System.out.println("Controller: Thêm mới thành công.");
				session.setAttribute("message", "Thêm mới khối '" + tenKhoi + "' thành công!");
			} else {
				System.out.println("Controller: Thêm mới thất bại.");
				session.setAttribute("error", "Thêm mới thất bại! Vui lòng thử lại.");
			}

			resp.sendRedirect(req.getContextPath() + "/admin/khoi-list");
		} else if (servletPath.equals("/admin/khoi-edit")) {

			System.out.println("Controller: Nhận request /admin/khoi-edit (POST).");

			try {

				int maKhoi = Integer.parseInt(req.getParameter("maKhoi_edit"));
				String tenKhoi = req.getParameter("tenKhoi_edit");

				Khoi khoiCapNhat = new Khoi();
				khoiCapNhat.setMaKhoi(maKhoi);
				khoiCapNhat.setTenKhoi(tenKhoi);
				boolean thanhCong = khoiService.update(khoiCapNhat);
				if (thanhCong) {
					session.setAttribute("message", "Cập nhật khối '" + tenKhoi + "' thành công!");
				} else {
					session.setAttribute("error", "Cập nhật thất bại!");
				}

			} catch (NumberFormatException e) {
				session.setAttribute("error", "Lỗi: ID khối không hợp lệ.");
			}
		}
		resp.sendRedirect(req.getContextPath() + "/admin/khoi-list");
	}

	/**
	 * Phân trang và tìm kiếm
	 */
	private void showKhoiList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String searchKey = req.getParameter("searchKey");
		String pageParam = req.getParameter("page");
		String entriesParam = req.getParameter("entries");
		int pageNumber = 1;
		if (pageParam != null && !pageParam.isEmpty()) {
			try {
				pageNumber = Integer.parseInt(pageParam);
			} catch (NumberFormatException e) {
				pageNumber = 1;
			}
		}

		int pageSize = 10;
		if (entriesParam != null && !entriesParam.isEmpty()) {
			try {
				pageSize = Integer.parseInt(entriesParam);
			} catch (NumberFormatException e) {
				pageSize = 10;
			}
		}

		List<Khoi> listKhoi = khoiService.findAndPaginate(searchKey, pageNumber, pageSize);
		int totalItems = khoiService.count(searchKey);
		int totalPages = (int) Math.ceil((double) totalItems / pageSize);

		req.setAttribute("dsKhoi", listKhoi);
		req.setAttribute("totalItems", totalItems);
		req.setAttribute("totalPages", totalPages);
		req.setAttribute("currentPage", pageNumber);
		req.setAttribute("pageSize", pageSize);
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/admin/khoi-list.jsp");
		dispatcher.forward(req, resp);
	}

	/**
	 * Hiển thị form thêm mới
	 */
	private void showAddForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		System.out.println("Controller: Nhận request /admin/khoi-add (GET). Đang hiển thị form.");
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/admin/khoi-form.jsp");
		dispatcher.forward(req, resp);
	}

	private void handleDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		System.out.println("Controller: Nhận request /admin/khoi-delete (GET).");
		HttpSession session = req.getSession();
		String idParam = req.getParameter("id");

		try {
			int maKhoi = Integer.parseInt(idParam);
			boolean thanhCong = khoiService.delete(maKhoi);
			if (thanhCong) {
				session.setAttribute("message", "Đã xóa khối thành công!");
			} else {
				session.setAttribute("error", "Xóa thất bại! Khối có thể đang được sử dụng.");
			}

		} catch (NumberFormatException e) {
			System.out.println("Controller: Lỗi: ID không hợp lệ.");
			session.setAttribute("error", "ID không hợp lệ!");
		}

		resp.sendRedirect(req.getContextPath() + "/admin/khoi-list");
	}
}