package com.student.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

@WebServlet(urlPatterns = { "/admin/phancong", "/admin/phancong-detail", "/admin/phancong-save" })
public class PhanCongController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Khởi tạo Service
    private PhanCongService phanCongService = new PhanCongServiceImpl();
    private NamHocService namHocService = new NamHocServiceImpl();
    private HocKyService hocKyService = new HocKyServiceImpl();
    private KhoiService khoiService = new KhoiServiceImpl();
    private LopHocService lopHocService = new LopHocServiceImpl();
    private GiaoVienService giaoVienService = new GiaoVienServiceImpl();
    private MonHocService monHocService = new MonHocServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/admin/phancong".equals(path)) {
            showDanhSachLopMonChuaPhanCong(req, resp);
        } else if ("/admin/phancong-detail".equals(path)) {
            showList(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/admin/phancong");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/admin/phancong-save".equals(path)) {
            handleSave(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/admin/phancong");
        }
    }

    private void showDanhSachLopMonChuaPhanCong(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy tham số lọc
        String maNH = req.getParameter("maNH");
        String maHKStr = req.getParameter("maHK");
        String maKhoiStr = req.getParameter("maKhoi");
        String maLopStr = req.getParameter("maLop");

        // Lấy danh sách năm học, khối cho các bộ lọc
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

        // Parse tham số bộ lọc
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

        // Lấy danh sách LỚP đã phân công (không hiển thị từng môn)
        List<com.student.dto.LopPhanCongDTO> danhSach = phanCongService.getDanhSachLopPhanCong(
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

        List<com.student.dto.LopPhanCongDTO> danhSachPage = danhSach.subList(fromIndex, toIndex);

        req.setAttribute("danhSach", danhSachPage);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("totalItems", totalItems);

        // Truyền lại các tham số lọc để giữ trên form
        req.setAttribute("selectedNH", maNH);
        req.setAttribute("selectedHK", maHKStr);
        req.setAttribute("selectedKhoi", maKhoiStr);
        req.setAttribute("selectedLop", maLopStr);

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/admin/phancong-danh-sach.jsp");
        rd.forward(req, resp);
    }

    private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<NamHoc> listNamHoc = namHocService.findAll();
        List<Khoi> listKhoi = khoiService.findAll();
        List<GiaoVien> listGiaoVien = giaoVienService.findAll();

        req.setAttribute("dsNamHoc", listNamHoc);
        req.setAttribute("dsKhoi", listKhoi);
        req.setAttribute("dsGiaoVien", listGiaoVien);
        
        Map<Integer, List<GiaoVien>> mapGVTheoTo = new HashMap<>();
        for (GiaoVien gv : listGiaoVien) {
            int maTo = gv.getMaTo();
            if (maTo > 0) {
                if (!mapGVTheoTo.containsKey(maTo)) {
                    mapGVTheoTo.put(maTo, new ArrayList<>());
                }
                mapGVTheoTo.get(maTo).add(gv);
            }
        }
        req.setAttribute("mapGVTheoTo", mapGVTheoTo); 
        String maNH = req.getParameter("maNH");
        String maKhoiStr = req.getParameter("maKhoi");
        String maLopStr = req.getParameter("maLop");
        String maHocKyStr = req.getParameter("maHK");
        List<HocKy> listHocKy = new ArrayList<>();
        if (maNH != null && !maNH.isEmpty()) {
            listHocKy = hocKyService.findByNamHoc(maNH);
        }
        req.setAttribute("dsHocKy", listHocKy);
        List<LopHoc> listLopHoc = new ArrayList<>();
        if (maNH != null && !maNH.isEmpty() && maKhoiStr != null && !maKhoiStr.isEmpty()) {
            try {
                int maKhoi = Integer.parseInt(maKhoiStr);
                listLopHoc = lopHocService.findByNamHocAndKhoi(maNH, maKhoi);
            } catch (Exception e) {}
        }
        req.setAttribute("dsLopHoc", listLopHoc);
        if (maLopStr != null && maHocKyStr != null) {
            try {
                int maLop = Integer.parseInt(maLopStr);
                int maHocKy = Integer.parseInt(maHocKyStr);
                List<PhanCong> listPhanCong = phanCongService.getPhanCongView(maLop, maHocKy);
                req.setAttribute("dsPhanCong", listPhanCong);
                req.setAttribute("selectedLop", maLop);
                req.setAttribute("selectedHK", maHocKy);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/admin/phancong-detail.jsp");
        rd.forward(req, resp);
    }

    private void handleSave(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        
        try {
            String maNH = req.getParameter("maNH");
            String maKhoi = req.getParameter("maKhoi");
            String maLopStr = req.getParameter("maLop");
            String maHocKyStr = req.getParameter("maHK");
            
            if (maLopStr == null || maLopStr.isEmpty() || maHocKyStr == null || maHocKyStr.isEmpty()) {
                session.setAttribute("error", "Thiếu thông tin lớp hoặc học kỳ!");
                resp.sendRedirect(req.getContextPath() + "/admin/phancong");
                return;
            }
            
            int maLop = Integer.parseInt(maLopStr);
            int maHocKy = Integer.parseInt(maHocKyStr);

            String[] listMaMon = req.getParameterValues("maMonHoc_list");

            if (listMaMon == null || listMaMon.length == 0) {
                session.setAttribute("error", "Không có môn học nào để phân công!");
                resp.sendRedirect(req.getContextPath() + "/admin/phancong-detail?maNH=" + maNH + "&maKhoi=" + maKhoi + "&maLop=" + maLop + "&maHK=" + maHocKy);
                return;
            }
            
            int successCount = 0;
            int errorCount = 0;
            StringBuilder errors = new StringBuilder();

            for (String maMonStr : listMaMon) {
                try {
                    int maMon = Integer.parseInt(maMonStr);
                    String maGVStr = req.getParameter("maGV_" + maMon);
                    int maGV = (maGVStr != null && !maGVStr.isEmpty()) ? Integer.parseInt(maGVStr) : 0;
                    if (phanCongService.savePhanCong(maLop, maMon, maHocKy, maGV)) {
                        successCount++;
                    } else {
                        errorCount++;
                        errors.append("Môn ").append(maMon).append(", ");
                    }
                } catch (Exception e) {
                    errorCount++;
                    errors.append("Môn ").append(maMonStr).append(" lỗi: ").append(e.getMessage()).append("; ");
                }
            }

            if (errorCount == 0) {
                session.setAttribute("message", String.format(
                    "Cập nhật phân công thành công! Đã lưu %d môn học.", successCount));
            } else if (successCount > 0) {
                session.setAttribute("warning", String.format(
                    "Cập nhật một phần: %d thành công, %d lỗi. Chi tiết: %s", 
                    successCount, errorCount, errors.toString()));
            } else {
                session.setAttribute("error", "Lưu thất bại! " + errors.toString());
            }

            resp.sendRedirect(req.getContextPath() + "/admin/phancong-detail?maNH=" + maNH + "&maKhoi=" + maKhoi + "&maLop=" + maLop + "&maHK=" + maHocKy);

        } catch (NumberFormatException e) {
            session.setAttribute("error", "Dữ liệu không hợp lệ: " + e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/admin/phancong");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/admin/phancong");
        }
    }
}