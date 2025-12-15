package com.student.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Controller: LogoutController
 * URL: /logout
 * 
 * Chức năng:
 * - Xóa session (đăng xuất)
 * - Redirect về trang login
 */
@WebServlet("/logout")
public class LogoutController extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        handleLogout(request, response);
    }
    
    /**
     * POST /logout
     * 
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        handleLogout(request, response);
    }
    
    /**
     * Xử lý đăng xuất
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    private void handleLogout(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {

        HttpSession session = request.getSession(false);
        
        if (session != null) {
            Object account = session.getAttribute("account");
            String username = (String) session.getAttribute("username");
            
            System.out.println("========================================");
            System.out.println("Đăng xuất:");
            System.out.println("- Username: " + username);
            System.out.println("- Session ID: " + session.getId());
            System.out.println("========================================");

            session.invalidate();
        }
        
        String contextPath = request.getContextPath();
        response.sendRedirect(contextPath + "/login");
    }
}
