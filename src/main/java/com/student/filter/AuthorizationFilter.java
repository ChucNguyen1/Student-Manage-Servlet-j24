package com.student.filter;

import java.io.IOException;

import com.student.model.TaiKhoan;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Filter: AuthorizationFilter
 * 
 * Quy tắc:
 * - Allow list: /login, /logout, static resources -> Cho qua
 * - Chưa login: account == null -> Redirect /login
 * - Phân quyền:
 *   + /admin/* 
 *   + /teacher/* 
 *   + /student/* 
 */
@WebFilter("/*")
public class AuthorizationFilter implements Filter {

    /**
     * Khởi tạo filter
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("========================================");
        System.out.println("AuthorizationFilter initialized");
        System.out.println("========================================");
    }

    /**
     * Xử lý filter logic
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        String path = requestURI.substring(contextPath.length());
        System.out.println("Filter checking: " + path);
        if (isPublicResource(path)) {
            System.out.println("✓ Allow: Public resource - " + path);
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = httpRequest.getSession(false);
        TaiKhoan account = null;
        
        if (session != null) {
            account = (TaiKhoan) session.getAttribute("account");
        }
        
        if (account == null) {
            System.out.println("✗ Block: Not logged in - Redirect to /login");
            httpResponse.sendRedirect(contextPath + "/login");
            return;
        }
        
        String role = account.getRole();
        
        if (path.startsWith("/admin/")) {
            if (!"ADMIN".equalsIgnoreCase(role)) {
                System.out.println("✗ Block: Access denied to /admin/* for role: " + role);
                handleForbidden(httpRequest, httpResponse, "Bạn không có quyền truy cập trang quản trị.");
                return;
            }
        }

        if (path.startsWith("/teacher/")) {
            if (!"GIAOVIEN".equalsIgnoreCase(role)) {
                System.out.println("✗ Block: Access denied to /teacher/* for role: " + role);
                handleForbidden(httpRequest, httpResponse, "Bạn không có quyền truy cập trang giáo viên.");
                return;
            }
        }

        if (path.startsWith("/student/")) {
            if (!"HOCSINH".equalsIgnoreCase(role)) {
                System.out.println("✗ Block: Access denied to /student/* for role: " + role);
                handleForbidden(httpRequest, httpResponse, "Bạn không có quyền truy cập trang học sinh.");
                return;
            }
        }
        

        System.out.println("✓ Allow: " + path + " for role: " + role);
        chain.doFilter(request, response);
    }

    /**
     * Kiểm tra xem path có phải là tài nguyên công khai không
     * 
     * @param path Đường dẫn tương đối 
     * @return true nếu là tài nguyên công khai
     */
    private boolean isPublicResource(String path) {
        if (path.equals("/login") || path.equals("/logout")) {
            return true;
        }
        
        if (path.equals("/") || path.equals("")) {
            return true;
        }
        String lowerPath = path.toLowerCase();

        if (lowerPath.startsWith("/assets/")) {
            return true;
        }

        if (lowerPath.startsWith("/js/")) {
            return true;
        }

        if (lowerPath.endsWith(".css") || 
            lowerPath.endsWith(".js") || 
            lowerPath.endsWith(".jpg") || 
            lowerPath.endsWith(".jpeg") || 
            lowerPath.endsWith(".png") || 
            lowerPath.endsWith(".gif") || 
            lowerPath.endsWith(".ico") || 
            lowerPath.endsWith(".svg") || 
            lowerPath.endsWith(".woff") || 
            lowerPath.endsWith(".woff2") || 
            lowerPath.endsWith(".ttf") || 
            lowerPath.endsWith(".eot") || 
            lowerPath.endsWith(".map")) {
            return true;
        }
        
        return false;
    }

    /**
     * Xử lý lỗi 403 Forbidden
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param message Thông báo lỗi
     */
    private void handleForbidden(HttpServletRequest request, HttpServletResponse response, String message) 
            throws ServletException, IOException {

        request.setAttribute("errorMessage", message);
        request.getRequestDispatcher("/WEB-INF/views/403.jsp").forward(request, response);
        
    }

    /**
     * Xác định URL trang chủ dựa trên role
     * 
     */
    @SuppressWarnings("unused")
    private String getHomeUrlByRole(HttpServletRequest request, String role) {
        String contextPath = request.getContextPath();
        
        if (role == null) {
            return contextPath + "/login";
        }
        
        switch (role.toUpperCase()) {
            case "ADMIN":
                return contextPath + "/admin/dashboard";
            case "GIAOVIEN":
                return contextPath + "/teacher/home";
            case "HOCSINH":
                return contextPath + "/student/home";
            default:
                return contextPath + "/login";
        }
    }

    /**
     * Hủy filter
     */
    @Override
    public void destroy() {
        System.out.println("========================================");
        System.out.println("AuthorizationFilter destroyed");
        System.out.println("========================================");
    }
}
