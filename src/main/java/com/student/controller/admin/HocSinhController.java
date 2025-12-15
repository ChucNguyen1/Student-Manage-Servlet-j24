package com.student.controller.admin;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import com.student.model.HocSinh;
import com.student.model.LopHoc;
import com.student.service.HocSinhService;
import com.student.service.LopHocService;
import com.student.service.impl.HocSinhServiceImpl;
import com.student.service.impl.LopHocServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = { "/admin/hocsinh-list", "/admin/hocsinh-add", "/admin/hocsinh-edit",
		"/admin/hocsinh-delete" })
public class HocSinhController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private HocSinhService service = new HocSinhServiceImpl();
	private LopHocService lopService = new LopHocServiceImpl(); 

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		switch (path) {
		case "/admin/hocsinh-list":
			showList(req, resp);
			break;
		case "/admin/hocsinh-delete":
			handleDelete(req, resp);
			break;
		default:
			resp.sendRedirect(req.getContextPath() + "/admin/hocsinh-list");
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String path = req.getServletPath();
		HttpSession session = req.getSession();

		if ("/admin/hocsinh-add".equals(path))
			handleAdd(req, session);
		else if ("/admin/hocsinh-edit".equals(path))
			handleEdit(req, session);

		resp.sendRedirect(req.getContextPath() + "/admin/hocsinh-list");
	}

	private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String searchKey = req.getParameter("searchKey");
		int page = 1;
		try {
			page = Integer.parseInt(req.getParameter("page"));
		} catch (Exception e) {
		}

		int pageSize = 10;
		List<HocSinh> listHS = service.findAndPaginate(searchKey, page, pageSize);
		int totalItems = service.count(searchKey);
		int totalPages = (int) Math.ceil((double) totalItems / pageSize);
		List<LopHoc> listLop = lopService.findAll();

		req.setAttribute("dsHocSinh", listHS);
		req.setAttribute("dsLopHoc", listLop); 

		req.setAttribute("totalItems", totalItems);
		req.setAttribute("totalPages", totalPages);
		req.setAttribute("currentPage", page);

		req.getRequestDispatcher("/WEB-INF/views/admin/hocsinh-list.jsp").forward(req, resp);
	}

	private void handleAdd(HttpServletRequest req, HttpSession session) {
		try {
			HocSinh hs = extractData(req); 

			if (service.insert(hs))
				session.setAttribute("message", "Thêm học sinh thành công!");
			else
				session.setAttribute("error", "Thêm thất bại!");
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("error", "Lỗi dữ liệu: " + e.getMessage());
		}
	}

	private void handleEdit(HttpServletRequest req, HttpSession session) {
		try {
			HocSinh hs = extractData(req);
			hs.setMaHS(Integer.parseInt(req.getParameter("maHS_edit")));
			if (service.update(hs))
				session.setAttribute("message", "Cập nhật thành công!");
			else
				session.setAttribute("error", "Cập nhật thất bại!");
		} catch (Exception e) {
			session.setAttribute("error", "Lỗi dữ liệu: " + e.getMessage());
		}
	}

	private void handleDelete(HttpServletRequest req, HttpServletResponse resp) {
		try {
			int maHS = Integer.parseInt(req.getParameter("id"));
			if (service.delete(maHS))
				req.getSession().setAttribute("message", "Đã xóa hồ sơ học sinh!");
			else
				req.getSession().setAttribute("error", "Xóa thất bại!");
		} catch (Exception e) {
		}
	}

	// --- Helper Method: Lấy dữ liệu từ Request ---
	private HocSinh extractData(HttpServletRequest req) {
		HocSinh hs = new HocSinh();
		hs.setHoTen(req.getParameter("hoTen"));
		String ns = req.getParameter("ngaySinh");
		if (ns != null && !ns.isEmpty())
			hs.setNgaySinh(Date.valueOf(ns));

		hs.setGioiTinh(req.getParameter("gioiTinh"));
		hs.setNoiSinh(req.getParameter("noiSinh"));
		hs.setDanToc(req.getParameter("danToc"));
		hs.setTonGiao(req.getParameter("tonGiao"));
		hs.setDiaChi(req.getParameter("diaChi"));

		// Liên lạc
		hs.setEmail(req.getParameter("email"));
		hs.setSdtCaNhan(req.getParameter("sdtCaNhan"));

		// Gia đình
		hs.setHoTenCha(req.getParameter("hoTenCha"));
		hs.setNgheNghiepCha(req.getParameter("ngheNghiepCha"));
		hs.setSdtCha(req.getParameter("sdtCha"));
		hs.setHoTenMe(req.getParameter("hoTenMe"));
		hs.setNgheNghiepMe(req.getParameter("ngheNghiepMe"));
		hs.setSdtMe(req.getParameter("sdtMe"));

		// Học vụ
		String maLopStr = req.getParameter("maLop");
		if (maLopStr != null && !maLopStr.isEmpty()) {
			hs.setMaLop(Integer.parseInt(maLopStr));
		}
		hs.setTrangThaiHocTap(req.getParameter("trangThaiHocTap")); 

		return hs;
	}
}