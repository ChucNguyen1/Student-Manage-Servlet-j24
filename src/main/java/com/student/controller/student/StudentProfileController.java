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

@WebServlet("/student/profile")
public class StudentProfileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HocSinhDAO hocSinhDAO;

	@Override
	public void init() throws ServletException {
		hocSinhDAO = new HocSinhDAOImpl();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		TaiKhoan account = (TaiKhoan) session.getAttribute("account");
		if (account == null || !"HOCSINH".equals(account.getRole())) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		Integer maHS = account.getMaHS();
		if (maHS == null) {
			response.sendRedirect(request.getContextPath() + "/student/home");
			return;
		}

		HocSinh hocSinh = hocSinhDAO.findById(maHS);

		if (hocSinh == null) {
			response.sendRedirect(request.getContextPath() + "/student/home");
			return;
		}

		request.setAttribute("hocSinh", hocSinh);
		request.getRequestDispatcher("/WEB-INF/views/student/profile.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		Integer maHS = account.getMaHS();
		if (maHS == null) {
			response.sendRedirect(request.getContextPath() + "/student/home");
			return;
		}
		String email = request.getParameter("email");
		String sdtCaNhan = request.getParameter("sdtCaNhan");
		String diaChi = request.getParameter("diaChi");

		boolean success = hocSinhDAO.updateProfile(maHS, email, sdtCaNhan, diaChi);

		if (success) {
			request.setAttribute("successMessage", "Cập nhật thông tin thành công!");
		} else {
			request.setAttribute("errorMessage", "Cập nhật thông tin thất bại!");
		}

		// Reload thông tin
		HocSinh hocSinh = hocSinhDAO.findById(maHS);
		request.setAttribute("hocSinh", hocSinh);
		request.getRequestDispatcher("/WEB-INF/views/student/profile.jsp").forward(request, response);
	}
}
