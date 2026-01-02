package com.student.controller.teacher;

import java.io.IOException;
import java.util.List;

import com.student.dao.HocKyDAO;
import com.student.dao.NamHocDAO;
import com.student.dao.PhanCongDAO;
import com.student.dao.impl.HocKyDAOImpl;
import com.student.dao.impl.NamHocDAOImpl;
import com.student.dao.impl.PhanCongDAOImpl;
import com.student.model.HocKy;
import com.student.model.NamHoc;
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
	private final NamHocDAO namHocDAO;

	public TeacherClassController() {
		this.phanCongDAO = new PhanCongDAOImpl();
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
			request.getRequestDispatcher("/WEB-INF/views/teacher/danh-sach-lop.jsp").forward(request, response);
			return;
		}

		// Lấy danh sách năm học
		List<NamHoc> dsNamHoc = namHocDAO.findAll();
		request.setAttribute("dsNamHoc", dsNamHoc);

		// Lấy maNH từ request hoặc chọn năm học mặc định
		String maNHParam = request.getParameter("maNH");
		String maNH = null;

		if (maNHParam != null && !maNHParam.isEmpty()) {
			maNH = maNHParam;
		} else if (dsNamHoc != null && !dsNamHoc.isEmpty()) {
			// Chọn năm học đầu tiên làm mặc định
			maNH = dsNamHoc.get(0).getMaNH();
		}

		request.setAttribute("maNHHienTai", maNH);

		// Lấy danh sách học kỳ theo năm học
		List<HocKy> dsHocKy = null;
		if (maNH != null) {
			dsHocKy = hocKyDAO.findByNamHoc(maNH);
		}
		request.setAttribute("dsHocKy", dsHocKy);

		// Lấy maHocKy từ request hoặc chọn học kỳ mặc định
		String maHocKyParam = request.getParameter("maHocKy");
		int maHocKy = 0;

		if (maHocKyParam != null && !maHocKyParam.isEmpty()) {
			try {
				maHocKy = Integer.parseInt(maHocKyParam);
			} catch (NumberFormatException e) {
			}
		}

		// Nếu chưa chọn học kỳ, chọn học kỳ đầu tiên đang hoạt động
		if (maHocKy == 0 && dsHocKy != null && !dsHocKy.isEmpty()) {
			for (HocKy hk : dsHocKy) {
				if (hk.isTrangThai()) {
					maHocKy = hk.getMaHK();
					break;
				}
			}
			// Nếu không có học kỳ nào đang hoạt động, chọn học kỳ đầu tiên
			if (maHocKy == 0) {
				maHocKy = dsHocKy.get(0).getMaHK();
			}
		}

		request.setAttribute("maHocKyHienTai", maHocKy);

		// Lấy danh sách phân công theo giáo viên và học kỳ
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
