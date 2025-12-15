package com.student.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.student.model.DiemChiTiet;
import com.student.model.HocKy;
import com.student.model.Khoi;
import com.student.model.LopHoc;
import com.student.model.MonHoc;
import com.student.model.NamHoc;
import com.student.service.DiemChiTietService;
import com.student.service.HocKyService;
import com.student.service.KhoiService;
import com.student.service.LopHocService;
import com.student.service.MonHocService;
import com.student.service.NamHocService;
import com.student.service.impl.DiemChiTietServiceImpl;
import com.student.service.impl.HocKyServiceImpl;
import com.student.service.impl.KhoiServiceImpl;
import com.student.service.impl.LopHocServiceImpl;
import com.student.service.impl.MonHocServiceImpl;
import com.student.service.impl.NamHocServiceImpl;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = { "/admin/diem-list", "/admin/diem-save" })
public class DiemController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private DiemChiTietService diemService = new DiemChiTietServiceImpl();
	private NamHocService namHocService = new NamHocServiceImpl();
	private HocKyService hocKyService = new HocKyServiceImpl();
	private MonHocService monHocService = new MonHocServiceImpl();

	private LopHocService lopHocService = new LopHocServiceImpl();
	private KhoiService khoiService = new KhoiServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		if ("/admin/diem-list".equals(path)) {
			showBangDiem(req, resp);
		} else {
			resp.sendRedirect(req.getContextPath() + "/admin/diem-list");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		if ("/admin/diem-save".equals(path)) {
			handleSaveDiem(req, resp);
		} else {
			resp.sendRedirect(req.getContextPath() + "/admin/diem-list");
		}
	}

	// --- HIỂN THỊ BẢNG ĐIỂM ---
	private void showBangDiem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		List<NamHoc> listNamHoc = namHocService.findAll();
		List<HocKy> listHocKy = hocKyService.findAll();
		List<MonHoc> listMonHoc = monHocService.findAll();
		List<Khoi> listKhoi = khoiService.findAll(); 

		req.setAttribute("dsNamHoc", listNamHoc);
		req.setAttribute("dsHocKy", listHocKy);
		req.setAttribute("dsMonHoc", listMonHoc);
		req.setAttribute("dsKhoi", listKhoi);

		String maNH = req.getParameter("maNH");
		String maKhoiStr = req.getParameter("maKhoi");
		String maLopStr = req.getParameter("maLop");
		String maMonHocStr = req.getParameter("maMH");
		String maHocKyStr = req.getParameter("maHK");

		if (maNH != null && !maNH.isEmpty()) {
			listHocKy = hocKyService.findByNamHoc(maNH);
		} 
		req.setAttribute("dsHocKy", listHocKy); 

		List<LopHoc> listLopHoc = new ArrayList<>();
		if (maNH != null && !maNH.isEmpty() && maKhoiStr != null && !maKhoiStr.isEmpty()) {
			try {
				int maKhoi = Integer.parseInt(maKhoiStr);
				listLopHoc = lopHocService.findByNamHocAndKhoi(maNH, maKhoi);
			} catch (NumberFormatException e) {
			}
		}
		req.setAttribute("dsLopHoc", listLopHoc); 

		if (maLopStr != null && maMonHocStr != null && maHocKyStr != null) {
			try {
				int maLop = Integer.parseInt(maLopStr);
				int maMonHoc = Integer.parseInt(maMonHocStr);
				int maHocKy = Integer.parseInt(maHocKyStr);

				List<DiemChiTiet> listDiem = diemService.getBangDiemLop(maLop, maMonHoc, maHocKy);
				req.setAttribute("dsDiem", listDiem);
			} catch (NumberFormatException e) {
			}
		}

		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/admin/diem-list.jsp");
		rd.forward(req, resp);
	}

	// --- LƯU ĐIỂM ---
	private void handleSaveDiem(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession();
		try {
			int maLop = Integer.parseInt(req.getParameter("maLop"));
			int maMonHoc = Integer.parseInt(req.getParameter("maMH"));
			int maHocKy = Integer.parseInt(req.getParameter("maHK"));
			String[] listMaHS = req.getParameterValues("maHS_list");

			if (listMaHS != null) {
				for (String maHSStr : listMaHS) {
					int maHS = Integer.parseInt(maHSStr);
					DiemChiTiet diem = new DiemChiTiet();
					diem.setMaHS(maHS);
					diem.setMaMonHoc(maMonHoc);
					diem.setMaHocKy(maHocKy);

					diem.setDiemMieng1(getDoubleParam(req, "diemMieng1_" + maHS));
					diem.setDiemMieng2(getDoubleParam(req, "diemMieng2_" + maHS));
					diem.setDiemMieng3(getDoubleParam(req, "diemMieng3_" + maHS));

					diem.setDiem15p1(getDoubleParam(req, "diem15p1_" + maHS));
					diem.setDiem15p2(getDoubleParam(req, "diem15p2_" + maHS));
					diem.setDiem15p3(getDoubleParam(req, "diem15p3_" + maHS));

					diem.setDiem1Tiet1(getDoubleParam(req, "diem1Tiet1_" + maHS));
					diem.setDiem1Tiet2(getDoubleParam(req, "diem1Tiet2_" + maHS));

					diem.setDiemThi(getDoubleParam(req, "diemThi_" + maHS));
					diemService.saveDiem(diem);
				}
				session.setAttribute("message", "Lưu điểm thành công!");
			}

			resp.sendRedirect(req.getContextPath() + "/admin/diem-list?maLop=" + maLop + "&maMH=" + maMonHoc + "&maHK="
					+ maHocKy);

		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("error", "Lỗi khi lưu điểm: " + e.getMessage());
			resp.sendRedirect(req.getContextPath() + "/admin/diem-list");
		}
	}

	private Double getDoubleParam(HttpServletRequest req, String paramName) {
		String val = req.getParameter(paramName);
		if (val != null && !val.trim().isEmpty()) {
			try {
				return Double.parseDouble(val);
			} catch (NumberFormatException e) {
				return null;
			}
		}
		return null;
	}
}