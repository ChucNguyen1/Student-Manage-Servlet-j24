package com.student.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.student.model.*;
import com.student.service.*;
import com.student.service.impl.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = { "/admin/tkb", "/admin/tkb-detail", "/admin/tkb-save", "/admin/tkb-delete", "/admin/tkb-reset" })
public class ThoiKhoaBieuController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// Khởi tạo Service
	private ThoiKhoaBieuService tkbService = new ThoiKhoaBieuServiceImpl();
	private NamHocService namHocService = new NamHocServiceImpl();
	private HocKyService hocKyService = new HocKyServiceImpl();
	private KhoiService khoiService = new KhoiServiceImpl();
	private LopHocService lopHocService = new LopHocServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();

		if (path.equals("/admin/tkb")) {
			showDanhSachLop(req, resp);  // Danh sách lớp
		} else if (path.equals("/admin/tkb-detail")) {
			showTKBForm(req, resp);  // Form xếp TKB chi tiết
		} else if (path.equals("/admin/tkb-reset")) {
			resetTKB(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();

		if (path.equals("/admin/tkb-save")) {
			saveTKB(req, resp);
		} else if (path.equals("/admin/tkb-delete")) {
			deleteTiet(req, resp);
		}
	}

	/**
	 * Hiển thị danh sách lớp TKB (tương tự phân công)
	 */
	private void showDanhSachLop(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Lấy tham số lọc
		String maNH = req.getParameter("maNH");
		String maHKStr = req.getParameter("maHK");
		String maKhoiStr = req.getParameter("maKhoi");
		String maLopStr = req.getParameter("maLop");

		// Lấy danh sách cho bộ lọc
		List<NamHoc> listNamHoc = namHocService.findAll();
		List<Khoi> listKhoi = khoiService.findAll();
		
		// Load học kỳ: Nếu chọn năm học -> chỉ load học kỳ của năm đó, ngược lại load tất cả
		List<HocKy> listHocKy;
		if (maNH != null && !maNH.isEmpty()) {
			listHocKy = hocKyService.findByNamHoc(maNH);
		} else {
			listHocKy = hocKyService.findAll();
		}

		req.setAttribute("dsNamHoc", listNamHoc);
		req.setAttribute("dsHocKy", listHocKy);
		req.setAttribute("dsKhoi", listKhoi);

		// Lấy danh sách lớp học theo năm học và khối
		List<LopHoc> listLopHoc = new ArrayList<>();
		if (maNH != null && !maNH.isEmpty() && maKhoiStr != null && !maKhoiStr.isEmpty()) {
			try {
				int maKhoi = Integer.parseInt(maKhoiStr);
				listLopHoc = lopHocService.findByNamHocAndKhoi(maNH, maKhoi);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		req.setAttribute("dsLopHoc", listLopHoc);

		// Parse tham số
		Integer maHocKy = null;
		Integer maKhoi = null;
		Integer maLop = null;
		
		try {
			if (maHKStr != null && !maHKStr.isEmpty()) {
				maHocKy = Integer.parseInt(maHKStr);
			}
			if (maKhoiStr != null && !maKhoiStr.isEmpty()) {
				maKhoi = Integer.parseInt(maKhoiStr);
			}
			if (maLopStr != null && !maLopStr.isEmpty()) {
				maLop = Integer.parseInt(maLopStr);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		// Lấy danh sách lớp với thống kê TKB
		List<com.student.dto.LopThoiKhoaBieuDTO> danhSach = tkbService.getDanhSachLopTKB(
			maNH, maHocKy, maKhoi, maLop
		);

		// Phân trang
		int currentPage = 1;
		String pageParam = req.getParameter("page");
		if (pageParam != null) {
			try {
				currentPage = Integer.parseInt(pageParam);
			} catch (NumberFormatException e) {}
		}

		int pageSize = 10;
		int totalItems = danhSach.size();
		int totalPages = (int) Math.ceil((double) totalItems / pageSize);

		int fromIndex = (currentPage - 1) * pageSize;
		int toIndex = Math.min(fromIndex + pageSize, totalItems);

		List<com.student.dto.LopThoiKhoaBieuDTO> danhSachPage = danhSach.subList(fromIndex, toIndex);

		req.setAttribute("danhSach", danhSachPage);
		req.setAttribute("currentPage", currentPage);
		req.setAttribute("totalPages", totalPages);
		req.setAttribute("totalItems", totalItems);

		// Truyền lại các tham số lọc
		req.setAttribute("selectedNH", maNH);
		req.setAttribute("selectedHK", maHKStr);
		req.setAttribute("selectedKhoi", maKhoiStr);
		req.setAttribute("selectedLop", maLopStr);

		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/admin/tkb-danh-sach.jsp");
		rd.forward(req, resp);
	}

	/**
	 * Hiển thị form xếp TKB chi tiết
	 */
	private void showTKBForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();

		List<NamHoc> listNamHoc = namHocService.findAll();
		List<Khoi> listKhoi = khoiService.findAll();

		req.setAttribute("dsNamHoc", listNamHoc);
		req.setAttribute("dsKhoi", listKhoi);

		String maNH = req.getParameter("maNH");
		String maKhoiStr = req.getParameter("maKhoi");
		String maLopStr = req.getParameter("maLop");
		String maHocKyStr = req.getParameter("maHK");
		List<HocKy> listHocKy = new ArrayList<>();
		if (maNH != null && !maNH.isEmpty()) {
			listHocKy = hocKyService.findByNamHoc(maNH);
			if (listHocKy.isEmpty()) {
				session.setAttribute("warning", "Năm học này chưa có học kỳ!");
			}
		}
		req.setAttribute("dsHocKy", listHocKy);

		List<LopHoc> listLopHoc = new ArrayList<>();
		if (maNH != null && !maNH.isEmpty() && maKhoiStr != null && !maKhoiStr.isEmpty()) {
			try {
				int maKhoi = Integer.parseInt(maKhoiStr);
				listLopHoc = lopHocService.findByNamHocAndKhoi(maNH, maKhoi);
				if (listLopHoc.isEmpty()) {
					session.setAttribute("warning", "Khối này chưa có lớp nào!");
				}
			} catch (Exception e) {
				session.setAttribute("error", "Lỗi khi tải danh sách lớp!");
			}
		}
		req.setAttribute("dsLopHoc", listLopHoc);

		if (maLopStr != null && !maLopStr.isEmpty() && maHocKyStr != null && !maHocKyStr.isEmpty()) {
			try {
				int maLop = Integer.parseInt(maLopStr);
				int maHocKy = Integer.parseInt(maHocKyStr);
				LopHoc lopHoc = lopHocService.findById(maLop);
				if (lopHoc == null) {
					session.setAttribute("error", "Không tìm thấy lớp học!");
				} else {
					List<PhanCong> dsPhanCong = tkbService.getAvailableMonHoc(maLop, maHocKy);
					
					if (dsPhanCong.isEmpty()) {
						session.setAttribute("warning", "Lớp này chưa phân công môn học nào! Vui lòng phân công trước khi xếp TKB.");
					} else {
						req.setAttribute("dsPhanCong", dsPhanCong);
						Map<String, ThoiKhoaBieu> tkbGrid = tkbService.getTKBGrid(maLop, maHocKy);
						req.setAttribute("tkbGrid", tkbGrid);
						int countScheduled = tkbService.countScheduled(maLop, maHocKy);
						int totalSlots = 5 * 6; 
						
						req.setAttribute("countScheduled", countScheduled);
						req.setAttribute("totalSlots", totalSlots);
						req.setAttribute("tenLop", lopHoc.getTenLop());
						
						if (countScheduled == 0) {
							session.setAttribute("info", "Lớp này chưa có TKB. Hãy bắt đầu xếp lịch!");
						} else if (countScheduled < totalSlots) {
							session.setAttribute("info", String.format("Đã xếp %d/%d tiết. Còn %d tiết trống.", 
								countScheduled, totalSlots, (totalSlots - countScheduled)));
						} else {
							session.setAttribute("success", "Đã xếp đầy đủ " + totalSlots + " tiết!");
						}
					}

					req.setAttribute("selectedLop", maLop);
					req.setAttribute("selectedHK", maHocKy);
				}

			} catch (Exception e) {
				e.printStackTrace();
				session.setAttribute("error", "Lỗi khi tải TKB: " + e.getMessage());
			}
		}

		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/admin/tkb-list.jsp");
		rd.forward(req, resp);
	}

	/**
	 * Lưu TKB 
	 */
	private void saveTKB(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession();

		try {
			String maNH = req.getParameter("maNH");
			String maKhoi = req.getParameter("maKhoi");
			int maLop = Integer.parseInt(req.getParameter("maLop"));
			int maHocKy = Integer.parseInt(req.getParameter("maHK"));

			int successCount = 0;
			int errorCount = 0;
			StringBuilder errors = new StringBuilder();
			for (int thu = 2; thu <= 7; thu++) {
				for (int tiet = 1; tiet <= 5; tiet++) {
					String key = thu + "_" + tiet;
					String maMonHocStr = req.getParameter("mon_" + key);
					String phongHoc = req.getParameter("phong_" + key);

					if (maMonHocStr == null || maMonHocStr.isEmpty() || maMonHocStr.equals("0")) {
						tkbService.deleteTiet(maLop, maHocKy, thu, tiet);
						continue;
					}

					String[] parts = maMonHocStr.split("_");
					if (parts.length != 2) {
						errorCount++;
						errors.append(String.format("Lỗi format dữ liệu tại Thứ %d Tiết %d; ", thu, tiet));
						continue;
					}

					int maMonHoc = Integer.parseInt(parts[0]);
					int maGV = Integer.parseInt(parts[1]);
					ThoiKhoaBieu tkb = new ThoiKhoaBieu();
					tkb.setMaLop(maLop);
					tkb.setMaHocKy(maHocKy);
					tkb.setMaMonHoc(maMonHoc);
					tkb.setMaGV(maGV);
					tkb.setThu(thu);
					tkb.setTiet(tiet);
					tkb.setPhongHoc(phongHoc);
					Map<String, Object> result = tkbService.saveTiet(tkb);
					
					if ((boolean) result.get("success")) {
						successCount++;
					} else {
						errorCount++;
						errors.append(String.format("Thứ %d Tiết %d: %s; ", thu, tiet, result.get("message")));
					}
				}
			}

			if (errorCount == 0) {
				session.setAttribute("message", String.format("Lưu TKB thành công! Đã xếp %d tiết.", successCount));
			} else if (successCount > 0) {
				session.setAttribute("warning", String.format(
					"Lưu một phần: %d tiết thành công, %d lỗi. Chi tiết: %s", 
					successCount, errorCount, errors.toString()));
			} else {
				session.setAttribute("error", "Lưu thất bại! " + errors.toString());
			}

			resp.sendRedirect(req.getContextPath() + "/admin/tkb-detail?maNH=" + maNH + 
				"&maKhoi=" + maKhoi + "&maLop=" + maLop + "&maHK=" + maHocKy);

		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
			resp.sendRedirect(req.getContextPath() + "/admin/tkb");
		}
	}

	/**
	 * Xóa 1 tiết 
	 */
	private void deleteTiet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession();

		try {
			int maLop = Integer.parseInt(req.getParameter("maLop"));
			int maHocKy = Integer.parseInt(req.getParameter("maHK"));
			int thu = Integer.parseInt(req.getParameter("thu"));
			int tiet = Integer.parseInt(req.getParameter("tiet"));

			boolean success = tkbService.deleteTiet(maLop, maHocKy, thu, tiet);

			if (success) {
				session.setAttribute("message", "Đã xóa tiết học!");
			} else {
				session.setAttribute("error", "Lỗi khi xóa tiết!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("error", "Lỗi: " + e.getMessage());
		}

		resp.sendRedirect(req.getHeader("Referer"));
	}

	/**
	 * Reset TKB
	 */
	private void resetTKB(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession();

		try {
			int maLop = Integer.parseInt(req.getParameter("maLop"));
			int maHocKy = Integer.parseInt(req.getParameter("maHK"));

			boolean success = tkbService.resetTKB(maLop, maHocKy);

			if (success) {
				session.setAttribute("message", "Đã reset TKB! Có thể xếp lại từ đầu.");
			} else {
				session.setAttribute("error", "Lỗi khi reset TKB!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("error", "Lỗi: " + e.getMessage());
		}

		resp.sendRedirect(req.getHeader("Referer"));
	}
}
