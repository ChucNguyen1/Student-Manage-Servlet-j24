package com.student.controller.student;

import java.io.IOException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import com.student.model.TaiKhoan;
import com.student.model.ThongBao;
import com.student.service.ThongBaoService;
import com.student.service.impl.ThongBaoServiceImpl;
import com.student.dao.DocThongBaoDAO;
import com.student.dao.impl.DocThongBaoDAOImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Controller: StudentAnnouncementController
 * Xử lý xem danh sách thông báo của học sinh
 */
@WebServlet("/student/thong-bao")
public class StudentAnnouncementController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int PAGE_SIZE = 10;

	private final ThongBaoService thongBaoService;
	private final DocThongBaoDAO docThongBaoDAO;

	public StudentAnnouncementController() {
		this.thongBaoService = new ThongBaoServiceImpl();
		this.docThongBaoDAO = new DocThongBaoDAOImpl();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		TaiKhoan account = (TaiKhoan) session.getAttribute("account");
		if (account == null || !"HOCSINH".equals(account.getRole())) {
			response.sendRedirect(request.getContextPath() + "/403.jsp");
			return;
		}

		Integer maHS = account.getMaHS();
		if (maHS == null) {
			response.sendRedirect(request.getContextPath() + "/student/home");
			return;
		}

		// Lấy tham số phân trang
		String pageParam = request.getParameter("page");
		int currentPage = 1;
		try {
			if (pageParam != null && !pageParam.isEmpty()) {
				currentPage = Integer.parseInt(pageParam);
				if (currentPage < 1) currentPage = 1;
			}
		} catch (NumberFormatException e) {
			currentPage = 1;
		}

		// Lấy danh sách thông báo
		String searchKey = request.getParameter("search");
		List<ThongBao> announcements = thongBaoService.findAll(searchKey, currentPage, PAGE_SIZE);
		int totalRecords = thongBaoService.count(searchKey);
		int totalPages = (int) Math.ceil((double) totalRecords / PAGE_SIZE);

		// Tạo map để lưu trạng thái đã đọc của từng thông báo
		Map<Integer, Boolean> readStatusMap = new HashMap<>();
		for (ThongBao tb : announcements) {
			readStatusMap.put(tb.getMaTB(), docThongBaoDAO.isRead(maHS, tb.getMaTB()));
		}

		// Gửi dữ liệu sang view
		request.setAttribute("announcements", announcements);
		request.setAttribute("readStatusMap", readStatusMap);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("totalRecords", totalRecords);
		request.setAttribute("searchKey", searchKey != null ? searchKey : "");

		request.getRequestDispatcher("/WEB-INF/views/student/announcement.jsp").forward(request, response);
	}
}
