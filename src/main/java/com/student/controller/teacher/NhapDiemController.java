package com.student.controller.teacher;

import java.io.IOException;
import java.util.List;

import com.student.dao.DiemChiTietDAO;
import com.student.dao.LopHocDAO;
import com.student.dao.MonHocDAO;
import com.student.dao.PhanCongDAO;
import com.student.dao.impl.DiemChiTietDAOImpl;
import com.student.dao.impl.LopHocDAOImpl;
import com.student.dao.impl.MonHocDAOImpl;
import com.student.dao.impl.PhanCongDAOImpl;
import com.student.model.DiemChiTiet;
import com.student.model.LopHoc;
import com.student.model.MonHoc;
import com.student.model.PhanCong;
import com.student.model.TaiKhoan;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Controller: NhapDiemController
 * 
 */
@WebServlet("/teacher/nhap-diem-chi-tiet")
public class NhapDiemController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final DiemChiTietDAO diemDAO;
	private final PhanCongDAO phanCongDAO;
	private final LopHocDAO lopHocDAO;
	private final MonHocDAO monHocDAO;

	public NhapDiemController() {
		this.diemDAO = new DiemChiTietDAOImpl();
		this.phanCongDAO = new PhanCongDAOImpl();
		this.lopHocDAO = new LopHocDAOImpl();
		this.monHocDAO = new MonHocDAOImpl();
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
			request.getRequestDispatcher("/WEB-INF/views/teacher/nhap-diem-chi-tiet.jsp").forward(request, response);
			return;
		}

		String maLopStr = request.getParameter("maLop");
		String maMonStr = request.getParameter("maMon");
		String maHocKyStr = request.getParameter("maHocKy");

		if (maLopStr == null || maMonStr == null || maHocKyStr == null) {
			request.setAttribute("error", "Thiếu tham số maLop, maMon hoặc maHocKy.");
			request.getRequestDispatcher("/WEB-INF/views/teacher/nhap-diem-chi-tiet.jsp").forward(request, response);
			return;
		}

		int maLop = 0, maMonHoc = 0, maHocKy = 0;
		try {
			maLop = Integer.parseInt(maLopStr);
			maMonHoc = Integer.parseInt(maMonStr);
			maHocKy = Integer.parseInt(maHocKyStr);
		} catch (NumberFormatException e) {
			request.setAttribute("error", "Tham số không hợp lệ.");
			request.getRequestDispatcher("/WEB-INF/views/teacher/nhap-diem-chi-tiet.jsp").forward(request, response);
			return;
		}

		List<PhanCong> dsPhanCong = phanCongDAO.findByGiaoVienAndHocKy(maGV, maHocKy);
		boolean hasPermission = false;

		if (dsPhanCong != null) {
			for (PhanCong pc : dsPhanCong) {
				if (pc.getMaLop() == maLop && pc.getMaMonHoc() == maMonHoc) {
					hasPermission = true;
					break;
				}
			}
		}

		if (!hasPermission) {
			request.setAttribute("error", "Bạn không có quyền nhập điểm cho lớp này.");
			request.getRequestDispatcher("/WEB-INF/views/teacher/nhap-diem-chi-tiet.jsp").forward(request, response);
			return;
		}

		LopHoc lopHoc = lopHocDAO.findById(maLop);
		MonHoc monHoc = monHocDAO.findById(maMonHoc);

		request.setAttribute("lopHoc", lopHoc);
		request.setAttribute("monHoc", monHoc);
		request.setAttribute("maLop", maLop);
		request.setAttribute("maMonHoc", maMonHoc);
		request.setAttribute("maHocKy", maHocKy);

		List<DiemChiTiet> dsBangDiem = diemDAO.getBangDiemLop(maLop, maMonHoc, maHocKy);
		request.setAttribute("dsBangDiem", dsBangDiem);

		request.getRequestDispatcher("/WEB-INF/views/teacher/nhap-diem-chi-tiet.jsp").forward(request, response);
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
		if (account == null || !"GIAOVIEN".equals(account.getRole())) {
			response.sendRedirect(request.getContextPath() + "/403.jsp");
			return;
		}

		int maLop = Integer.parseInt(request.getParameter("maLop"));
		int maMonHoc = Integer.parseInt(request.getParameter("maMonHoc"));
		int maHocKy = Integer.parseInt(request.getParameter("maHocKy"));
		String[] dsMaHS = request.getParameterValues("maHS");

		if (dsMaHS == null || dsMaHS.length == 0) {
			session.setAttribute("error", "Không có học sinh nào để lưu điểm.");
			response.sendRedirect(request.getContextPath() + "/teacher/nhap-diem-chi-tiet?maLop=" + maLop
					+ "&maMon=" + maMonHoc + "&maHocKy=" + maHocKy);
			return;
		}

		int successCount = 0;
		int errorCount = 0;

		for (String maHSStr : dsMaHS) {
			try {
				int maHS = Integer.parseInt(maHSStr);
				String miengStr = request.getParameter("mieng_" + maHS);
				String p15Str = request.getParameter("p15_" + maHS);
				String tiet1Str = request.getParameter("tiet1_" + maHS);
				String thiStr = request.getParameter("thi_" + maHS);

				Double diemMieng = parseDoubleOrNull(miengStr);
				Double diem15p = parseDoubleOrNull(p15Str);
				Double diem1Tiet = parseDoubleOrNull(tiet1Str);
				Double diemThi = parseDoubleOrNull(thiStr);

				Double diemTBM = tinhDiemTrungBinh(diemMieng, diem15p, diem1Tiet, diemThi);

				DiemChiTiet diem = new DiemChiTiet();
				diem.setMaHS(maHS);
				diem.setMaMonHoc(maMonHoc);
				diem.setMaHocKy(maHocKy);
				diem.setDiemMieng1(diemMieng);
				diem.setDiem15p1(diem15p);
				diem.setDiem1Tiet1(diem1Tiet);
				diem.setDiemThi(diemThi);
				diem.setDiemTBM(diemTBM);

				boolean exists = diemDAO.checkExist(maHS, maMonHoc, maHocKy);

				boolean success = false;
				if (exists) {
					success = diemDAO.update(diem);
				} else {
					success = diemDAO.insert(diem);
				}

				if (success) {
					successCount++;
				} else {
					errorCount++;
				}

			} catch (Exception e) {
				errorCount++;
				e.printStackTrace();
			}
		}

		if (errorCount == 0) {
			session.setAttribute("success", "Lưu điểm thành công cho " + successCount + " học sinh!");
		} else {
			session.setAttribute("warning",
					"Lưu thành công " + successCount + " học sinh. " + errorCount + " học sinh lỗi.");
		}

		response.sendRedirect(request.getContextPath() + "/teacher/nhap-diem-chi-tiet?maLop=" + maLop + "&maMon="
				+ maMonHoc + "&maHocKy=" + maHocKy);
	}

	/**
	 * Parse chuỗi thành Double
	 */
	private Double parseDoubleOrNull(String str) {
		if (str == null || str.trim().isEmpty()) {
			return null;
		}
		try {
			return Double.parseDouble(str.trim());
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Công thức tính điểm trung bình môn: 
	 * ĐTB = (Miệng + 15p + 1Tiết*2 + Thi*3) / 7
	 * 
	 */
	private Double tinhDiemTrungBinh(Double mieng, Double p15, Double tiet1, Double thi) {
		if (thi == null) {
			return null;
		}

		double m = (mieng != null) ? mieng : 0.0;
		double p = (p15 != null) ? p15 : 0.0;
		double t = (tiet1 != null) ? tiet1 : 0.0;

		double dtb = (m + p + t * 2 + thi * 3) / 7.0;
		return Math.round(dtb * 100.0) / 100.0;
	}
}
