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

@WebServlet(urlPatterns = { "/admin/phancong-list", "/admin/phancong-save" })
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

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/admin/phancong-list.jsp");
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
                resp.sendRedirect(req.getContextPath() + "/admin/phancong-list");
                return;
            }
            
            int maLop = Integer.parseInt(maLopStr);
            int maHocKy = Integer.parseInt(maHocKyStr);

            String[] listMaMon = req.getParameterValues("maMonHoc_list");

            if (listMaMon == null || listMaMon.length == 0) {
                session.setAttribute("error", "Không có môn học nào để phân công!");
                resp.sendRedirect(req.getContextPath() + "/admin/phancong-list?maNH=" + maNH + "&maKhoi=" + maKhoi + "&maLop=" + maLop + "&maHK=" + maHocKy);
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

            resp.sendRedirect(req.getContextPath() + "/admin/phancong-list?maNH=" + maNH + "&maKhoi=" + maKhoi + "&maLop=" + maLop + "&maHK=" + maHocKy);

        } catch (NumberFormatException e) {
            session.setAttribute("error", "Dữ liệu không hợp lệ: " + e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/admin/phancong-list");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/admin/phancong-list");
        }
    }
}