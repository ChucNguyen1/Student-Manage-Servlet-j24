package com.student.controller.student;

import java.io.IOException;

import com.student.dao.DocThongBaoDAO;
import com.student.dao.impl.DocThongBaoDAOImpl;
import com.student.model.TaiKhoan;
import com.student.model.ThongBao;
import com.student.service.ThongBaoService;
import com.student.service.impl.ThongBaoServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Controller: StudentAnnouncementDetailController
 * Xử lý xem chi tiết thông báo và đánh dấu đã đọc
 */
@WebServlet("/student/thong-bao/chi-tiet")
public class StudentAnnouncementDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final ThongBaoService thongBaoService;
	private final DocThongBaoDAO docThongBaoDAO;

	public StudentAnnouncementDetailController() {
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
			request.setAttribute("error", "Không tìm thấy thông tin học sinh.");
			response.sendRedirect(request.getContextPath() + "/student/thong-bao");
			return;
		}

		// Lấy mã thông báo
		String maTBParam = request.getParameter("id");
		if (maTBParam == null || maTBParam.isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/student/thong-bao");
			return;
		}

		try {
			int maTB = Integer.parseInt(maTBParam);
			
			// Lấy thông tin thông báo
			ThongBao announcement = thongBaoService.findById(maTB);
			if (announcement == null) {
				request.setAttribute("error", "Không tìm thấy thông báo.");
				response.sendRedirect(request.getContextPath() + "/student/thong-bao");
				return;
			}

			// Đánh dấu đã đọc
			docThongBaoDAO.markAsRead(maHS, maTB);

			// Gửi dữ liệu sang view
			request.setAttribute("announcement", announcement);
			request.getRequestDispatcher("/WEB-INF/views/student/announcement-detail.jsp")
				.forward(request, response);

		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/student/thong-bao");
		}
	}
}
