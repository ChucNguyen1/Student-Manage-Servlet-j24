package com.student.controller.admin;

import java.io.IOException;

import com.student.service.GiaoVienService;
import com.student.service.HocKyService;
import com.student.service.MonHocService;
import com.student.service.NamHocService;
import com.student.service.impl.GiaoVienServiceImpl;
import com.student.service.impl.HocKyServiceImpl;
import com.student.service.impl.MonHocServiceImpl;
import com.student.service.impl.NamHocServiceImpl;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet này xử lý trang chủ (Dashboard) Nó sẽ thay thế việc truy cập file
 * index.jsp tĩnh.
 */
@WebServlet(urlPatterns = { "/admin/dashboard" }) // <-- Bắt cả URL gốc
public class DashboardController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// Gọi tất cả các Service mà chúng ta cần để lấy số liệu
	private GiaoVienService giaoVienService = new GiaoVienServiceImpl();
	private MonHocService monHocService = new MonHocServiceImpl();
	private NamHocService namHocService = new NamHocServiceImpl();
	private HocKyService hocKyService = new HocKyServiceImpl();

	// (Sau này bạn sẽ thêm HocSinhService, LopHocService...)

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		System.out.println("Controller: Đang tải dữ liệu Dashboard...");

		// 1. Lấy dữ liệu thống kê (Tổng số)
		// (null nghĩa là không tìm kiếm)
		int totalGiaoVien = giaoVienService.count(null);
		int totalMonHoc = monHocService.count(null);
		int totalNamHoc = namHocService.count(null);
		int totalHocKy = hocKyService.count(null);

		// 2. Gửi dữ liệu sang JSP
		req.setAttribute("totalGiaoVien", totalGiaoVien);
		req.setAttribute("totalMonHoc", totalMonHoc);
		req.setAttribute("totalNamHoc", totalNamHoc);
		req.setAttribute("totalHocKy", totalHocKy);

		// 3. Forward đến file view
		// Chúng ta sẽ dùng file index.jsp CŨ của bạn làm view
		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp");
		rd.forward(req, resp);
	}
}