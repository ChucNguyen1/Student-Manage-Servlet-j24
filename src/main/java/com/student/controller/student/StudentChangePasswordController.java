package com.student.controller.student;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.student.dao.UsersDAO;
import com.student.dao.impl.UsersDAOImpl;
import com.student.model.TaiKhoan;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/student/change-password")
public class StudentChangePasswordController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UsersDAO usersDAO;

	@Override
	public void init() throws ServletException {
		usersDAO = new UsersDAOImpl();
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

		request.getRequestDispatcher("/WEB-INF/views/student/change-password.jsp").forward(request, response);
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

		int userID = account.getMaTK();
		String currentPassword = request.getParameter("currentPassword");
		String newPassword = request.getParameter("newPassword");
		String confirmPassword = request.getParameter("confirmPassword");

		// Validation
		if (currentPassword == null || currentPassword.trim().isEmpty() || 
			newPassword == null || newPassword.trim().isEmpty() ||
			confirmPassword == null || confirmPassword.trim().isEmpty()) {
			request.setAttribute("errorMessage", "Vui lòng điền đầy đủ thông tin!");
			request.getRequestDispatcher("/WEB-INF/views/student/change-password.jsp").forward(request, response);
			return;
		}

		if (!newPassword.equals(confirmPassword)) {
			request.setAttribute("errorMessage", "Mật khẩu mới và xác nhận mật khẩu không khớp!");
			request.getRequestDispatcher("/WEB-INF/views/student/change-password.jsp").forward(request, response);
			return;
		}

		if (newPassword.length() < 6) {
			request.setAttribute("errorMessage", "Mật khẩu mới phải có ít nhất 6 ký tự!");
			request.getRequestDispatcher("/WEB-INF/views/student/change-password.jsp").forward(request, response);
			return;
		}

		// Verify current password
		String currentPasswordHash = hashPassword(currentPassword);
		String storedPasswordHash = usersDAO.getPasswordHash(userID);

		if (!currentPasswordHash.equals(storedPasswordHash)) {
			request.setAttribute("errorMessage", "Mật khẩu hiện tại không đúng!");
			request.getRequestDispatcher("/WEB-INF/views/student/change-password.jsp").forward(request, response);
			return;
		}

		// Update password
		String newPasswordHash = hashPassword(newPassword);
		boolean success = usersDAO.updatePassword(userID, newPasswordHash);

		if (success) {
			request.setAttribute("successMessage", "Đổi mật khẩu thành công!");
		} else {
			request.setAttribute("errorMessage", "Đổi mật khẩu thất bại!");
		}

		request.getRequestDispatcher("/WEB-INF/views/student/change-password.jsp").forward(request, response);
	}

	private String hashPassword(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(password.getBytes());
			StringBuilder hexString = new StringBuilder();
			for (byte b : hash) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}
