package com.student.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

@WebServlet(urlPatterns = { "/admin/phancong-list", "/admin/phancong-save" })
public class PhanCongController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Khởi tạo các Service
    private PhanCongService phanCongService = new PhanCongServiceImpl();
    private NamHocService namHocService = new NamHocServiceImpl();
    private HocKyService hocKyService = new HocKyServiceImpl();
    private KhoiService khoiService = new KhoiServiceImpl();
    private LopHocService lopHocService = new LopHocServiceImpl();
    private GiaoVienService giaoVienService = new GiaoVienServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/admin/phancong-list".equals(path)) {
            showList(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/admin/phancong-list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/admin/phancong-save".equals(path)) {
            handleSave(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/admin/phancong-list");
        }
    }

    private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        // 1. LOAD DỮ LIỆU CỐ ĐỊNH (Năm, Khối)
        List<NamHoc> listNamHoc = namHocService.findAll();
        List<Khoi> listKhoi = khoiService.findAll();
        
        // Load danh sách Giáo viên (Để đổ vào Dropdown chọn người dạy)
        List<GiaoVien> listGiaoVien = giaoVienService.findAll();

        req.setAttribute("dsNamHoc", listNamHoc);
        req.setAttribute("dsKhoi", listKhoi);
        req.setAttribute("dsGiaoVien", listGiaoVien); // <-- Quan trọng

        // 2. LẤY THAM SỐ TỪ URL
        String maNH = req.getParameter("maNH");
        String maKhoiStr = req.getParameter("maKhoi");
        String maLopStr = req.getParameter("maLop");
        String maHocKyStr = req.getParameter("maHK");

        // 3. LOGIC LỌC (Cascading)
        
        // 3.1. Lọc Học kỳ (Theo Năm)
        List<HocKy> listHocKy = new ArrayList<>();
        if (maNH != null && !maNH.isEmpty()) {
            listHocKy = hocKyService.findByNamHoc(maNH);
        }
        req.setAttribute("dsHocKy", listHocKy);

        // 3.2. Lọc Lớp (Theo Năm & Khối)
        List<LopHoc> listLopHoc = new ArrayList<>();
        if (maNH != null && !maNH.isEmpty() && maKhoiStr != null && !maKhoiStr.isEmpty()) {
            try {
                int maKhoi = Integer.parseInt(maKhoiStr);
                listLopHoc = lopHocService.findByNamHocAndKhoi(maNH, maKhoi);
            } catch (Exception e) {}
        }
        req.setAttribute("dsLopHoc", listLopHoc);

        // 4. LẤY BẢNG PHÂN CÔNG (Khi đã chọn Lớp và Học kỳ)
        if (maLopStr != null && maHocKyStr != null) {
            try {
                int maLop = Integer.parseInt(maLopStr);
                int maHocKy = Integer.parseInt(maHocKyStr);

                // Gọi Service để lấy danh sách merged (Môn học + Giáo viên đã gán)
                List<PhanCong> listPhanCong = phanCongService.getPhanCongView(maLop, maHocKy);
                req.setAttribute("dsPhanCong", listPhanCong);

                // Giữ lại lựa chọn
                req.setAttribute("selectedLop", maLop);
                req.setAttribute("selectedHK", maHocKy);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/admin/phancong-list.jsp");
        rd.forward(req, resp);
    }

    private void handleSave(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        try {
            // Lấy thông tin chung để redirect
            String maNH = req.getParameter("maNH");
            String maKhoi = req.getParameter("maKhoi");
            int maLop = Integer.parseInt(req.getParameter("maLop"));
            int maHocKy = Integer.parseInt(req.getParameter("maHK"));

            // Lấy danh sách Môn học (ID) từ form
            String[] listMaMon = req.getParameterValues("maMonHoc_list");

            if (listMaMon != null) {
                for (String maMonStr : listMaMon) {
                    int maMon = Integer.parseInt(maMonStr);
                    
                    // Lấy ID Giáo viên được chọn cho môn này
                    // Tên input trong JSP là: maGV_IDMON
                    String maGVStr = req.getParameter("maGV_" + maMon);
                    int maGV = (maGVStr != null && !maGVStr.isEmpty()) ? Integer.parseInt(maGVStr) : 0;
                    
                    // Gọi Service lưu
                    phanCongService.savePhanCong(maLop, maMon, maHocKy, maGV);
                }
                session.setAttribute("message", "Cập nhật phân công thành công!");
            }

            // Redirect về đúng trang đang đứng
            resp.sendRedirect(req.getContextPath() + "/admin/phancong-list?maNH=" + maNH + "&maKhoi=" + maKhoi + "&maLop=" + maLop + "&maHK=" + maHocKy);

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/admin/phancong-list");
        }
    }
}