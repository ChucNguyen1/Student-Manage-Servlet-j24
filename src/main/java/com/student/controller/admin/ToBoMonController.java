package com.student.controller.admin;

import java.io.IOException;
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

@WebServlet(urlPatterns = { "/admin/tobomon-list", "/admin/tobomon-add", "/admin/tobomon-edit", "/admin/tobomon-delete" })
public class ToBoMonController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ToBoMonService toBoMonService = new ToBoMonServiceImpl();
	private GiaoVienService giaoVienService = new GiaoVienServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();

		switch (path) {
		case "/admin/tobomon-list":
			showList(req, resp);
			break;
		case "/admin/tobomon-delete":
			handleDelete(req, resp);
			break;
		default:
			resp.sendRedirect(req.getContextPath() + "/admin/tobomon-list");
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		String path = req.getServletPath();
		HttpSession session = req.getSession();

		if ("/admin/tobomon-add".equals(path)) {
			handleAdd(req, session);
		} else if ("/admin/tobomon-edit".equals(path)) {
			handleEdit(req, session);
		}

		resp.sendRedirect(req.getContextPath() + "/admin/tobomon-list");
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
		List<ToBoMon> listTo = toBoMonService.findAndPaginate(searchKey, page, pageSize);
		int totalItems = toBoMonService.count(searchKey);
		int totalPages = (int) Math.ceil((double) totalItems / pageSize);
		List<GiaoVien> listGV = giaoVienService.findAll();
		req.setAttribute("dsToBoMon", listTo);
		req.setAttribute("dsGiaoVien", listGV);

		req.setAttribute("totalItems", totalItems);
		req.setAttribute("totalPages", totalPages);
		req.setAttribute("currentPage", page);
		req.setAttribute("searchKey", searchKey);

		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/admin/tobomon-list.jsp");
		rd.forward(req, resp);
	}

	private void handleAdd(HttpServletRequest req, HttpSession session) {
		try {
			String tenTo = req.getParameter("tenTo");
			String moTa = req.getParameter("moTa");
			String maToTruongStr = req.getParameter("maToTruong");
			ToBoMon to = new ToBoMon();
			to.setTenTo(tenTo);
			to.setMoTa(moTa);
			if (maToTruongStr != null && !maToTruongStr.isEmpty()) {
				try {
					int maToTruong = Integer.parseInt(maToTruongStr);
					to.setMaToTruong(maToTruong);
				} catch (NumberFormatException e) {
					System.out.println("Lỗi parse mã tổ trưởng: " + e.getMessage());
				}
			}
			if (toBoMonService.themToBoMon(to)) {
				session.setAttribute("message", "Thêm tổ bộ môn thành công!");
			} else {
				session.setAttribute("error", "Thêm thất bại!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
		}
	}

	private void handleEdit(HttpServletRequest req, HttpSession session) {
		try {
			String maToStr = req.getParameter("maTo_edit");
			if (maToStr == null || maToStr.isEmpty()) {
				session.setAttribute("error", "Không tìm thấy ID tổ bộ môn cần sửa!");
				return;
			}
			int maTo = Integer.parseInt(maToStr);

			String tenTo = req.getParameter("tenTo_edit");
			String moTa = req.getParameter("moTa_edit");
			String maToTruongStr = req.getParameter("maToTruong_edit");
			ToBoMon to = new ToBoMon();
			to.setMaTo(maTo);
			to.setTenTo(tenTo);
			to.setMoTa(moTa);
			if (maToTruongStr != null && !maToTruongStr.isEmpty()) {
				try {
					int maToTruong = Integer.parseInt(maToTruongStr);
					to.setMaToTruong(maToTruong);
				} catch (NumberFormatException e) {
					System.out.println("Lỗi parse mã tổ trưởng khi edit");
				}
			}

			if (toBoMonService.capNhatToBoMon(to)) {
				session.setAttribute("message", "Cập nhật tổ bộ môn thành công!");
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
			String idStr = req.getParameter("id");
			if (idStr != null && !idStr.isEmpty()) {
				int maTo = Integer.parseInt(idStr);
				
				if (toBoMonService.xoaToBoMon(maTo)) {
					session.setAttribute("message", "Xóa tổ bộ môn thành công!");
				} else {
					session.setAttribute("error", "Xóa thất bại!");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("error", "Không thể xóa (Tổ này đang có giáo viên hoặc liên kết khác)!");
		}
		resp.sendRedirect(req.getContextPath() + "/admin/tobomon-list");
	}
}