package com.student.controller.student;

import java.io.IOException;
import java.util.List;

import com.student.dao.DiemChiTietDAO;
import com.student.dao.HocKyDAO;
import com.student.dao.HocSinhDAO;
import com.student.dao.impl.DiemChiTietDAOImpl;
import com.student.dao.impl.HocKyDAOImpl;
import com.student.dao.impl.HocSinhDAOImpl;
import com.student.model.DiemChiTiet;
import com.student.model.HocKy;
import com.student.model.HocSinh;
import com.student.model.TaiKhoan;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Controller: Xem Bảng Điểm Cá Nhân cho Học Sinh
 * 
 */
@WebServlet("/student/xem-diem")
public class StudentGradeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private HocSinhDAO hocSinhDAO = new HocSinhDAOImpl();
	private DiemChiTietDAO diemChiTietDAO = new DiemChiTietDAOImpl();
	private HocKyDAO hocKyDAO = new HocKyDAOImpl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (session == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
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
			request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
			return;
		}

		HocSinh hocSinh = hocSinhDAO.findById(maHS);
		if (hocSinh == null) {
			request.setAttribute("error", "Học sinh không tồn tại.");
			request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
			return;
		}

		int maLop = hocSinh.getMaLop();
		if (maLop == 0) {
			request.setAttribute("error", "Bạn chưa được phân vào lớp nào. Vui lòng liên hệ phòng đào tạo.");
			request.setAttribute("hocSinh", hocSinh);
			request.getRequestDispatcher("/WEB-INF/views/student/xem-diem.jsp").forward(request, response);
			return;
		}

		List<HocKy> dsHocKy = hocKyDAO.findAll();

		int maHocKy = parseMaHocKy(request, dsHocKy);

		List<DiemChiTiet> dsDiem = diemChiTietDAO.getBangDiemCaNhan(maHS, maHocKy);
		double diemTBHK = tinhDiemTrungBinhHocKy(dsDiem);

		request.setAttribute("hocSinh", hocSinh);
		request.setAttribute("dsHocKy", dsHocKy);
		request.setAttribute("maHocKyHienTai", maHocKy);
		request.setAttribute("dsDiem", dsDiem);
		request.setAttribute("diemTBHK", diemTBHK);
		request.getRequestDispatcher("/WEB-INF/views/student/xem-diem.jsp").forward(request, response);
	}

	/**
	 * Parse tham số maHocKy từ request.
	 * Nếu không có hoặc không hợp lệ, lấy học kỳ đầu tiên .
	 */
	private int parseMaHocKy(HttpServletRequest request, List<HocKy> dsHocKy) {
		String param = request.getParameter("maHocKy");

		if (param != null && !param.isEmpty()) {
			try {
				return Integer.parseInt(param);
			} catch (NumberFormatException e) {

			}
		}
		if (!dsHocKy.isEmpty()) {
			return dsHocKy.get(0).getMaHK();
		}

		return 0; 
	}

	/**
	 * Tính Điểm Trung Bình Học Kỳ (ĐTBHK).
	 * 
	 * Công thức: Trung bình cộng của ĐTB các môn (chỉ tính môn có điểm).
	 * 
	 * @param dsDiem Danh sách điểm các môn
	 * @return ĐTBHK làm tròn 2 chữ số thập phân
	 */
	private double tinhDiemTrungBinhHocKy(List<DiemChiTiet> dsDiem) {
		double tong = 0;
		int count = 0;

		for (DiemChiTiet d : dsDiem) {
			if (d.getDiemTBM() != null) {
				tong += d.getDiemTBM();
				count++;
			}
		}

		if (count == 0) {
			return 0.0; 
		}
		return Math.round((tong / count) * 100.0) / 100.0;
	}
}
