package com.student.controller.teacher;

import java.io.IOException;
import java.util.List;

import com.student.dao.HocKyDAO;
import com.student.dao.PhanCongDAO;
import com.student.dao.impl.HocKyDAOImpl;
import com.student.dao.impl.PhanCongDAOImpl;
import com.student.model.HocKy;
import com.student.model.PhanCong;
import com.student.model.TaiKhoan;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Controller: TeacherClassController
 * URL: /teacher/danh-sach-lop
 * 
 */
@WebServlet("/teacher/danh-sach-lop")
public class TeacherClassController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final PhanCongDAO phanCongDAO;
	private final HocKyDAO hocKyDAO;

	public TeacherClassController() {
		this.phanCongDAO = new PhanCongDAOImpl();
		this.hocKyDAO = new HocKyDAOImpl();
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
		if (account == null || !"GIAOVIEN".equals(account.getRole())) {
			response.sendRedirect(request.getContextPath() + "/403.jsp");
			return;
		}

		Integer maGV = account.getMaGV();
		if (maGV == null) {
			request.setAttribute("error", "Không tìm thấy thông tin giáo viên.");
			request.getRequestDispatcher("/WEB-INF/views/teacher/danh-sach-lop.jsp").forward(request, response);
			return;
		}
		List<HocKy> dsHocKy = hocKyDAO.findAll();
		request.setAttribute("dsHocKy", dsHocKy);
		String maHocKyParam = request.getParameter("maHocKy");
		int maHocKy = 0;

		if (maHocKyParam != null && !maHocKyParam.isEmpty()) {
			try {
				maHocKy = Integer.parseInt(maHocKyParam);
			} catch (NumberFormatException e) {
			}
		}

		if (maHocKy == 0 && dsHocKy != null && !dsHocKy.isEmpty()) {
			for (HocKy hk : dsHocKy) {
				if (hk.isTrangThai()) {
					maHocKy = hk.getMaHK();
					break;
				}
			}
			if (maHocKy == 0) {
				maHocKy = dsHocKy.get(0).getMaHK();
			}
		}

		request.setAttribute("maHocKyHienTai", maHocKy);

		if (maHocKy > 0) {
			List<PhanCong> dsPhanCong = phanCongDAO.findByGiaoVienAndHocKy(maGV, maHocKy);
			request.setAttribute("dsPhanCong", dsPhanCong);
			request.setAttribute("soLopDay", dsPhanCong != null ? dsPhanCong.size() : 0);
		} else {
			request.setAttribute("dsPhanCong", List.of());
			request.setAttribute("soLopDay", 0);
		}

		request.getRequestDispatcher("/WEB-INF/views/teacher/danh-sach-lop.jsp").forward(request, response);
	}
}
