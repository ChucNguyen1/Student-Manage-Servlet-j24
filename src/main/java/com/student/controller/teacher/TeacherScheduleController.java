package com.student.controller.teacher;

import java.io.IOException;
import java.util.List;

import com.student.dao.HocKyDAO;
import com.student.dao.NamHocDAO;
import com.student.dao.ThoiKhoaBieuDAO;
import com.student.dao.impl.HocKyDAOImpl;
import com.student.dao.impl.NamHocDAOImpl;
import com.student.dao.impl.ThoiKhoaBieuDAOImpl;
import com.student.model.HocKy;
import com.student.model.NamHoc;
import com.student.model.TaiKhoan;
import com.student.model.ThoiKhoaBieu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Controller: TeacherScheduleController
 */
@WebServlet("/teacher/lich-day")
public class TeacherScheduleController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final ThoiKhoaBieuDAO thoiKhoaBieuDAO;
	private final HocKyDAO hocKyDAO;
	private final NamHocDAO namHocDAO;

	public TeacherScheduleController() {
		this.thoiKhoaBieuDAO = new ThoiKhoaBieuDAOImpl();
		this.hocKyDAO = new HocKyDAOImpl();
		this.namHocDAO = new NamHocDAOImpl();
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
			request.getRequestDispatcher("/WEB-INF/views/teacher/lich-day.jsp").forward(request, response);
			return;
		}
		// Lấy danh sách năm học
		List<NamHoc> dsNamHoc = namHocDAO.findAll();
		request.setAttribute("dsNamHoc", dsNamHoc);

		// Lấy năm học được chọn (hoặc mặc định năm học đang hoạt động)
		String maNH = request.getParameter("maNH");
		if (maNH == null || maNH.isEmpty()) {
			// Tìm năm học đang hoạt động (trangThai = true)
			for (NamHoc nh : dsNamHoc) {
				if (nh.isTrangThai()) {
					maNH = nh.getMaNH();
					break;
				}
			}
			// Nếu không có năm học nào active, lấy năm đầu tiên
			if ((maNH == null || maNH.isEmpty()) && !dsNamHoc.isEmpty()) {
				maNH = dsNamHoc.get(0).getMaNH();
			}
		}
		request.setAttribute("maNHHienTai", maNH);

		// Lấy danh sách học kỳ theo năm học
		List<HocKy> dsHocKy = List.of();
		if (maNH != null && !maNH.isEmpty()) {
			dsHocKy = hocKyDAO.findByNamHoc(maNH);
		}
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
			List<ThoiKhoaBieu> dsTKB = thoiKhoaBieuDAO.findByGiaoVienAndHocKy(maGV, maHocKy);
			request.setAttribute("dsTKB", dsTKB);
		} else {
			request.setAttribute("dsTKB", List.of());
		}

		request.getRequestDispatcher("/WEB-INF/views/teacher/lich-day.jsp").forward(request, response);
	}
}
