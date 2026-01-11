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


@WebServlet(urlPatterns = { "/admin/index", "/admin/dashboard" })
public class DashboardController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private GiaoVienService giaoVienService = new GiaoVienServiceImpl();
	private MonHocService monHocService = new MonHocServiceImpl();
	private NamHocService namHocService = new NamHocServiceImpl();
	private HocKyService hocKyService = new HocKyServiceImpl();


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		System.out.println("Controller: Đang tải dữ liệu Dashboard...");
		
		try {
			long startTime = System.currentTimeMillis();
			
			System.out.println("Dashboard: Đang đếm giáo viên...");
			int totalGiaoVien = giaoVienService.count(null);
			System.out.println("Dashboard: Tìm thấy " + totalGiaoVien + " giáo viên");
			
			System.out.println("Dashboard: Đang đếm môn học...");
			int totalMonHoc = monHocService.count(null);
			System.out.println("Dashboard: Tìm thấy " + totalMonHoc + " môn học");
			
			System.out.println("Dashboard: Đang đếm năm học...");
			int totalNamHoc = namHocService.count(null);
			System.out.println("Dashboard: Tìm thấy " + totalNamHoc + " năm học");
			
			System.out.println("Dashboard: Đang đếm học kỳ...");
			int totalHocKy = hocKyService.count(null);
			System.out.println("Dashboard: Tìm thấy " + totalHocKy + " học kỳ");

			long endTime = System.currentTimeMillis();
			System.out.println("Dashboard: Tải dữ liệu hoàn tất trong " + (endTime - startTime) + "ms");

			req.setAttribute("totalGiaoVien", totalGiaoVien);
			req.setAttribute("totalMonHoc", totalMonHoc);
			req.setAttribute("totalNamHoc", totalNamHoc);
			req.setAttribute("totalHocKy", totalHocKy);

			RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");
			rd.forward(req, resp);
			
		} catch (Exception e) {
			System.err.println("LỖI tại Dashboard Controller: " + e.getMessage());
			e.printStackTrace();
			
			// Set giá trị mặc định khi lỗi
			req.setAttribute("totalGiaoVien", 0);
			req.setAttribute("totalMonHoc", 0);
			req.setAttribute("totalNamHoc", 0);
			req.setAttribute("totalHocKy", 0);
			req.setAttribute("error", "Không thể tải dữ liệu dashboard. Vui lòng kiểm tra kết nối database.");
			
			RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");
			rd.forward(req, resp);
		}
	}
}