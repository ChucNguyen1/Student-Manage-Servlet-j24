package com.student.controller.student;

import java.io.IOException;
import java.util.List;

import com.student.dao.HocKyDAO;
import com.student.dao.HocSinhDAO;
import com.student.dao.ThoiKhoaBieuDAO;
import com.student.dao.impl.HocKyDAOImpl;
import com.student.dao.impl.HocSinhDAOImpl;
import com.student.dao.impl.ThoiKhoaBieuDAOImpl;
import com.student.model.HocKy;
import com.student.model.HocSinh;
import com.student.model.TaiKhoan;
import com.student.model.ThoiKhoaBieu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Controller: StudentScheduleController
 * URL: /student/tkb
 * 
 */
@WebServlet("/student/tkb")
public class StudentScheduleController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final HocSinhDAO hocSinhDAO;
	private final ThoiKhoaBieuDAO thoiKhoaBieuDAO;
	private final HocKyDAO hocKyDAO;

	public StudentScheduleController() {
		this.hocSinhDAO = new HocSinhDAOImpl();
		this.thoiKhoaBieuDAO = new ThoiKhoaBieuDAOImpl();
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
		if (account == null || !"HOCSINH".equals(account.getRole())) {
			response.sendRedirect(request.getContextPath() + "/403.jsp");
			return;
		}

		Integer maHS = account.getMaHS();
		if (maHS == null) {
			request.setAttribute("error", "Không tìm thấy thông tin học sinh.");
			request.getRequestDispatcher("/WEB-INF/views/student/tkb.jsp").forward(request, response);
			return;
		}

		HocSinh hocSinh = hocSinhDAO.findById(maHS);
		if (hocSinh == null) {
			request.setAttribute("error", "Không tìm thấy thông tin học sinh.");
			request.getRequestDispatcher("/WEB-INF/views/student/tkb.jsp").forward(request, response);
			return;
		}

		int maLop = hocSinh.getMaLop();
		if (maLop == 0) {
			request.setAttribute("error", "Bạn chưa được phân vào lớp nào.");
			request.getRequestDispatcher("/WEB-INF/views/student/tkb.jsp").forward(request, response);
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
			List<ThoiKhoaBieu> dsTKB = thoiKhoaBieuDAO.findByLopAndHocKy(maLop, maHocKy);
			request.setAttribute("dsTKB", dsTKB);
		} else {
			request.setAttribute("dsTKB", List.of());
		}

		request.setAttribute("hocSinh", hocSinh);
		request.setAttribute("tenLop", hocSinh.getTenLop());
		request.getRequestDispatcher("/WEB-INF/views/student/tkb.jsp").forward(request, response);
	}
}
