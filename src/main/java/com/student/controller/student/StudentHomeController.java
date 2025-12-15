package com.student.controller.student;

import java.io.IOException;

import com.student.dao.HocSinhDAO;
import com.student.dao.impl.HocSinhDAOImpl;
import com.student.model.HocSinh;
import com.student.model.TaiKhoan;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Controller: StudentHomeController
 * 
 */
@WebServlet("/student/home")
public class StudentHomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final HocSinhDAO hocSinhDAO;

	public StudentHomeController() {
		this.hocSinhDAO = new HocSinhDAOImpl();
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
			request.getRequestDispatcher("/WEB-INF/views/student/home.jsp").forward(request, response);
			return;
		}

		HocSinh hocSinh = hocSinhDAO.findById(maHS);
		request.setAttribute("hocSinh", hocSinh);

		request.getRequestDispatcher("/WEB-INF/views/student/home.jsp").forward(request, response);
	}
}
