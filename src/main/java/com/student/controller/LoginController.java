package com.student.controller;

import java.io.IOException;

import com.student.model.TaiKhoan;
import com.student.service.TaiKhoanService;
import com.student.service.impl.TaiKhoanServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Controller: LoginController
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    private final TaiKhoanService taiKhoanService;
    
    public LoginController() {
        this.taiKhoanService = new TaiKhoanServiceImpl();
    }
    
    /**
     * GET /login
     * 
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);

        if (session != null) {
            TaiKhoan account = (TaiKhoan) session.getAttribute("account");
            
            if (account != null) {
                String homeUrl = getHomeUrlByRole(request, account.getRole());
                response.sendRedirect(homeUrl);
                return;
            }
        }

        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }
    
    /**
     * POST /login
     * 
     * Logic:
     * 1. Nhận username, password từ form
     * 2. Gọi TaiKhoanService.login()
     * 3. Nếu thất bại -> set error message, forward về login.jsp
     * 4. Nếu thành công:
     *    - Lưu TaiKhoan vào session với key "account"
     *    - Redirect về trang chủ theo role
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username == null || username.trim().isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập tên đăng nhập");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }
        
        if (password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập mật khẩu");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }
        TaiKhoan account = taiKhoanService.login(username.trim(), password);
        
        if (account == null) {
            request.setAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng");
            request.setAttribute("username", username); // Giữ lại username đã nhập
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }

        if (!account.isActive()) {
            request.setAttribute("error", "Tài khoản của bạn đã bị vô hiệu hóa. Vui lòng liên hệ quản trị viên.");
            request.setAttribute("username", username);
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }
        HttpSession session = request.getSession(true);
        session.setAttribute("account", account);
        session.setAttribute("username", account.getUsername());
        session.setAttribute("role", account.getRole());
        session.setAttribute("hoTenHienThi", account.getHoTenHienThi());
        session.setMaxInactiveInterval(30 * 60);
        System.out.println("========================================");
        System.out.println("Đăng nhập thành công:");
        System.out.println("- Username: " + account.getUsername());
        System.out.println("- Role: " + account.getRole());
        System.out.println("- Họ tên: " + account.getHoTenHienThi());
        System.out.println("- Session ID: " + session.getId());
        System.out.println("========================================");
        String homeUrl = getHomeUrlByRole(request, account.getRole());
        response.sendRedirect(homeUrl);
    }
    
    /**
     * Helper method: Xác định URL trang chủ dựa trên role
     * 
     * @param request HttpServletRequest để lấy context path
     * @param role Role của user 
     * @return URL
     */
    private String getHomeUrlByRole(HttpServletRequest request, String role) {
        String contextPath = request.getContextPath();
        
        if (role == null) {
            return contextPath + "/login";
        }
        
        switch (role.toUpperCase()) {
            case "ADMIN":
                return contextPath + "/admin/index";
                
            case "GIAOVIEN":
                return contextPath + "/teacher/home";
                
            case "HOCSINH":
                return contextPath + "/student/home";
                
            default:
                return contextPath + "/login";
        }
    }
}
