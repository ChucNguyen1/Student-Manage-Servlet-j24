package com.student.filter;

import com.student.model.TaiKhoan;
import com.student.service.TaiKhoanService;
import com.student.service.impl.TaiKhoanServiceImpl;
import com.student.utils.JWTUtil;
import com.student.utils.ResponseUtil;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * JWT Authentication Filter
 * 
 * Filter này áp dụng cho tất cả các API endpoints (/api/*)
 * NGOẠI TRỪ authentication endpoints (/api/auth/*)
 * 
 * Luồng hoạt động:
 * 1. Kiểm tra Authorization header
 * 2. Extract và validate JWT token
 * 3. Lấy thông tin user từ token
 * 4. Kiểm tra quyền truy cập
 * 5. Set user vào request attributes
 * 6. Cho phép request tiếp tục
 */
@WebFilter("/api/*")
public class JWTAuthenticationFilter implements Filter {
    
    private TaiKhoanService taiKhoanService;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.taiKhoanService = new TaiKhoanServiceImpl();
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        String path = requestURI.substring(contextPath.length());
        
        // Bypass authentication endpoints
        if (isPublicEndpoint(path)) {
            chain.doFilter(request, response);
            return;
        }
        
        // Handle preflight requests (CORS)
        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
            httpResponse.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        
        // Extract token from Authorization header
        String authHeader = httpRequest.getHeader("Authorization");
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            ResponseUtil.sendUnauthorized(httpResponse, "Missing or invalid Authorization header");
            return;
        }
        
        String token = authHeader.substring(7); // Remove "Bearer " prefix
        
        // Validate token
        if (!JWTUtil.isTokenValid(token)) {
            ResponseUtil.sendUnauthorized(httpResponse, "Invalid or expired token");
            return;
        }
        
        // Check if it's an access token (not refresh token)
        String tokenType = JWTUtil.getTokenType(token);
        if (!"access".equals(tokenType)) {
            ResponseUtil.sendUnauthorized(httpResponse, "Invalid token type");
            return;
        }
        
        // Extract user info from token
        String username = JWTUtil.getUsernameFromToken(token);
        Integer userId = JWTUtil.getUserIdFromToken(token);
        String role = JWTUtil.getRoleFromToken(token);
        
        if (username == null || userId == null || role == null) {
            ResponseUtil.sendUnauthorized(httpResponse, "Invalid token claims");
            return;
        }
        
        // Optional: Verify user still exists and is active
        TaiKhoan account = taiKhoanService.findById(userId);
        if (account == null || !account.isActive()) {
            ResponseUtil.sendUnauthorized(httpResponse, "User not found or account disabled");
            return;
        }
        
        // Check role-based access
        if (!hasAccess(path, role)) {
            ResponseUtil.sendForbidden(httpResponse, "You don't have permission to access this resource");
            return;
        }
        
        // Set user info in request attributes for use in controllers
        httpRequest.setAttribute("userId", userId);
        httpRequest.setAttribute("username", username);
        httpRequest.setAttribute("role", role);
        httpRequest.setAttribute("account", account);
        
        // Continue the filter chain
        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {
        this.taiKhoanService = null;
    }
    
    /**
     * Kiểm tra xem endpoint có public không (không cần JWT)
     */
    private boolean isPublicEndpoint(String path) {
        // Auth endpoints are public
        if (path.startsWith("/api/auth/")) {
            return true;
        }
        
        // Session-based endpoints (không dùng JWT, dùng HttpSession)
        if (path.equals("/api/student/ai-advisor")) {
            return true;
        }
        
        if (path.equals("/api/student/chatbot")) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Kiểm tra quyền truy cập dựa trên role
     */
    private boolean hasAccess(String path, String role) {
        // Admin có quyền truy cập tất cả
        if ("ADMIN".equalsIgnoreCase(role)) {
            return true;
        }
        
        // Teacher endpoints
        if (path.startsWith("/api/teacher/")) {
            return "GIAOVIEN".equalsIgnoreCase(role);
        }
        
        // Student endpoints
        if (path.startsWith("/api/student/")) {
            return "HOCSINH".equalsIgnoreCase(role);
        }
        
        // Admin endpoints
        if (path.startsWith("/api/admin/")) {
            return "ADMIN".equalsIgnoreCase(role);
        }
        
        // Default: allow if no specific restriction
        return true;
    }
}
