package com.student.controller.admin;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.student.model.TaiKhoan;
import com.student.service.TaiKhoanService;
import com.student.service.impl.TaiKhoanServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = {
    "/admin/taikhoan-list",
    "/admin/taikhoan-toggle-status",
    "/admin/taikhoan-reset-password",
    "/admin/taikhoan-auto-provision-students",
    "/admin/taikhoan-auto-provision-teachers"
})
public class TaiKhoanController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private TaiKhoanService taiKhoanService = new TaiKhoanServiceImpl();
    private static final String DEFAULT_PASSWORD = "123456";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Kiểm tra phân quyền ADMIN
        HttpSession session = req.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
            req.getRequestDispatcher("/WEB-INF/views/403.jsp").forward(req, resp);
            return;
        }
        
        String path = req.getServletPath();
        
        switch (path) {
            case "/admin/taikhoan-list":
                showList(req, resp);
                break;
            case "/admin/taikhoan-toggle-status":
                handleToggleStatus(req, resp);
                break;
            case "/admin/taikhoan-reset-password":
                handleResetPassword(req, resp);
                break;
            case "/admin/taikhoan-auto-provision-students":
                handleAutoProvisionStudents(req, resp);
                break;
            case "/admin/taikhoan-auto-provision-teachers":
                handleAutoProvisionTeachers(req, resp);
                break;
            default:
                resp.sendRedirect(req.getContextPath() + "/admin/taikhoan-list");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        doGet(req, resp);
    }

    private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchKey = req.getParameter("searchKey");
        int page = 1;
        try {
            page = Integer.parseInt(req.getParameter("page"));
        } catch (Exception e) {
        }

        int pageSize = 10;
        List<TaiKhoan> listTaiKhoan = taiKhoanService.findAllWithPagination(searchKey, page, pageSize);
        int totalItems = taiKhoanService.count(searchKey);
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);

        req.setAttribute("dsTaiKhoan", listTaiKhoan);
        req.setAttribute("totalItems", totalItems);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("currentPage", page);

        req.getRequestDispatcher("/WEB-INF/views/admin/taikhoan-list.jsp").forward(req, resp);
    }

    private void handleToggleStatus(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        try {
            int maTK = Integer.parseInt(req.getParameter("id"));
            boolean newStatus = Boolean.parseBoolean(req.getParameter("status"));
            
            TaiKhoan tk = taiKhoanService.findById(maTK);
            if (tk != null) {
                if (taiKhoanService.toggleAccountStatus(maTK, newStatus)) {
                    String action = newStatus ? "mở khóa" : "khóa";
                    session.setAttribute("message", "Đã " + action + " tài khoản " + tk.getUsername() + " thành công!");
                } else {
                    session.setAttribute("error", "Không thể thay đổi trạng thái tài khoản!");
                }
            } else {
                session.setAttribute("error", "Không tìm thấy tài khoản!");
            }
        } catch (Exception e) {
            session.setAttribute("error", "Lỗi: " + e.getMessage());
        }
        
        resp.sendRedirect(req.getContextPath() + "/admin/taikhoan-list");
    }

    private void handleResetPassword(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        try {
            int maTK = Integer.parseInt(req.getParameter("id"));
            
            TaiKhoan tk = taiKhoanService.findById(maTK);
            if (tk != null) {
                if (taiKhoanService.resetPasswordToDefault(maTK, DEFAULT_PASSWORD)) {
                    session.setAttribute("message", "Đã reset mật khẩu tài khoản " + tk.getUsername() + " về '" + DEFAULT_PASSWORD + "'");
                } else {
                    session.setAttribute("error", "Không thể reset mật khẩu!");
                }
            } else {
                session.setAttribute("error", "Không tìm thấy tài khoản!");
            }
        } catch (Exception e) {
            session.setAttribute("error", "Lỗi: " + e.getMessage());
        }
        
        resp.sendRedirect(req.getContextPath() + "/admin/taikhoan-list");
    }

    private void handleAutoProvisionStudents(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        try {
            Map<String, Object> result = taiKhoanService.autoProvisionStudentAccounts(DEFAULT_PASSWORD);
            
            if ((Boolean) result.get("success")) {
                int count = (Integer) result.get("count");
                if (count > 0) {
                    session.setAttribute("message", result.get("message") + 
                        ". Username = HS{mã HS}, Password = " + DEFAULT_PASSWORD);
                } else {
                    session.setAttribute("info", result.get("message"));
                }
            } else {
                session.setAttribute("error", "Có lỗi khi tạo tài khoản!");
            }
        } catch (Exception e) {
            session.setAttribute("error", "Lỗi: " + e.getMessage());
        }
        
        resp.sendRedirect(req.getContextPath() + "/admin/taikhoan-list");
    }

    private void handleAutoProvisionTeachers(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        try {
            Map<String, Object> result = taiKhoanService.autoProvisionTeacherAccounts(DEFAULT_PASSWORD);
            
            if ((Boolean) result.get("success")) {
                int count = (Integer) result.get("count");
                if (count > 0) {
                    session.setAttribute("message", result.get("message") + 
                        ". Username = GV{mã GV}, Password = " + DEFAULT_PASSWORD);
                } else {
                    session.setAttribute("info", result.get("message"));
                }
            } else {
                session.setAttribute("error", "Có lỗi khi tạo tài khoản!");
            }
        } catch (Exception e) {
            session.setAttribute("error", "Lỗi: " + e.getMessage());
        }
        
        resp.sendRedirect(req.getContextPath() + "/admin/taikhoan-list");
    }
}
