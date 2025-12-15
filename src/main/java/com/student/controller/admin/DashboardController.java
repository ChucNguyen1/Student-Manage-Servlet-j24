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
		int totalGiaoVien = giaoVienService.count(null);
		int totalMonHoc = monHocService.count(null);
		int totalNamHoc = namHocService.count(null);
		int totalHocKy = hocKyService.count(null);

		req.setAttribute("totalGiaoVien", totalGiaoVien);
		req.setAttribute("totalMonHoc", totalMonHoc);
		req.setAttribute("totalNamHoc", totalNamHoc);
		req.setAttribute("totalHocKy", totalHocKy);

		RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");
		rd.forward(req, resp);
	}
}