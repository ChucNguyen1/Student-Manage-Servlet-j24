package com.student.controller.api;

import com.google.gson.Gson;
import com.student.dao.RefreshTokenDAO;
import com.student.dao.impl.RefreshTokenDAOImpl;
import com.student.dto.LoginRequest;
import com.student.dto.LoginResponse;
import com.student.dto.RefreshTokenRequest;
import com.student.model.RefreshToken;
import com.student.model.TaiKhoan;
import com.student.service.TaiKhoanService;
import com.student.service.impl.TaiKhoanServiceImpl;
import com.student.utils.JWTUtil;
import com.student.utils.ResponseUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * REST API Controller cho Authentication (JWT)
 * 
 * Endpoints:
 * - POST /api/auth/login - Đăng nhập và nhận JWT tokens
 * - POST /api/auth/refresh - Làm mới access token
 * - POST /api/auth/logout - Đăng xuất (revoke refresh token)
 */
@WebServlet({
    "/api/auth/login",
    "/api/auth/refresh",
    "/api/auth/logout"
})
public class AuthController extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private final TaiKhoanService taiKhoanService;
    private final RefreshTokenDAO refreshTokenDAO;
    private final Gson gson;
    
    public AuthController() {
        this.taiKhoanService = new TaiKhoanServiceImpl();
        this.refreshTokenDAO = new RefreshTokenDAOImpl();
        this.gson = new Gson();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String path = request.getServletPath();
        
        switch (path) {
            case "/api/auth/login":
                handleLogin(request, response);
                break;
            case "/api/auth/refresh":
                handleRefreshToken(request, response);
                break;
            case "/api/auth/logout":
                handleLogout(request, response);
                break;
            default:
                ResponseUtil.sendError(response, HttpServletResponse.SC_NOT_FOUND, "Endpoint not found");
        }
    }
    
    /**
     * POST /api/auth/login
     * Đăng nhập và trả về JWT tokens
     */
    private void handleLogin(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        // Parse JSON request body
        String requestBody = request.getReader().lines().collect(Collectors.joining());
        LoginRequest loginRequest;
        
        try {
            loginRequest = gson.fromJson(requestBody, LoginRequest.class);
        } catch (Exception e) {
            ResponseUtil.sendBadRequest(response, "Invalid JSON format");
            return;
        }
        
        // Validate input
        if (loginRequest == null || loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
            ResponseUtil.sendBadRequest(response, "Username and password are required");
            return;
        }
        
        String username = loginRequest.getUsername().trim();
        String password = loginRequest.getPassword();
        
        if (username.isEmpty() || password.isEmpty()) {
            ResponseUtil.sendBadRequest(response, "Username and password cannot be empty");
            return;
        }
        
        // Authenticate user
        TaiKhoan account = taiKhoanService.login(username, password);
        
        if (account == null) {
            ResponseUtil.sendUnauthorized(response, "Invalid username or password");
            return;
        }
        
        if (!account.isActive()) {
            ResponseUtil.sendForbidden(response, "Account is disabled. Please contact administrator.");
            return;
        }
        
        // Generate tokens
        String accessToken = JWTUtil.generateAccessToken(
            account.getUsername(), 
            account.getMaTK(), 
            account.getRole()
        );
        
        String tokenId = UUID.randomUUID().toString();
        String refreshToken = JWTUtil.generateRefreshToken(account.getUsername(), tokenId);
        
        // Save refresh token to database
        try {
            String tokenHash = hashToken(refreshToken);
            long expiresInMs = 7 * 24 * 60 * 60 * 1000L; // 7 days
            Timestamp expiresAt = new Timestamp(System.currentTimeMillis() + expiresInMs);
            
            RefreshToken refreshTokenEntity = new RefreshToken(
                tokenId,
                account.getMaTK(),
                tokenHash,
                expiresAt
            );
            
            refreshTokenDAO.save(refreshTokenEntity);
            
        } catch (Exception e) {
            System.err.println("Error saving refresh token: " + e.getMessage());
            e.printStackTrace();
            ResponseUtil.sendInternalError(response, "Failed to generate refresh token");
            return;
        }
        
        // Build response
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setSuccess(true);
        loginResponse.setMessage("Login successful");
        loginResponse.setAccessToken(accessToken);
        loginResponse.setRefreshToken(refreshToken);
        loginResponse.setExpiresIn(1800); // 30 minutes in seconds
        
        LoginResponse.UserInfo userInfo = new LoginResponse.UserInfo();
        userInfo.setId(account.getMaTK());
        userInfo.setUsername(account.getUsername());
        userInfo.setRole(account.getRole());
        userInfo.setFullName(account.getHoTenHienThi());
        
        loginResponse.setUser(userInfo);
        
        ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_OK, loginResponse);
    }
    
    /**
     * POST /api/auth/refresh
     * Làm mới access token bằng refresh token
     */
    private void handleRefreshToken(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        // Parse request body
        String requestBody = request.getReader().lines().collect(Collectors.joining());
        RefreshTokenRequest refreshRequest;
        
        try {
            refreshRequest = gson.fromJson(requestBody, RefreshTokenRequest.class);
        } catch (Exception e) {
            ResponseUtil.sendBadRequest(response, "Invalid JSON format");
            return;
        }
        
        if (refreshRequest == null || refreshRequest.getRefreshToken() == null) {
            ResponseUtil.sendBadRequest(response, "Refresh token is required");
            return;
        }
        
        String refreshToken = refreshRequest.getRefreshToken();
        
        // Validate refresh token
        if (!JWTUtil.isTokenValid(refreshToken)) {
            ResponseUtil.sendUnauthorized(response, "Invalid or expired refresh token");
            return;
        }
        
        // Check token type
        String tokenType = JWTUtil.getTokenType(refreshToken);
        if (!"refresh".equals(tokenType)) {
            ResponseUtil.sendBadRequest(response, "Token is not a refresh token");
            return;
        }
        
        // Get token ID and check in database
        String tokenId = JWTUtil.getTokenIdFromToken(refreshToken);
        String username = JWTUtil.getUsernameFromToken(refreshToken);
        
        if (tokenId == null || username == null) {
            ResponseUtil.sendUnauthorized(response, "Invalid token claims");
            return;
        }
        
        // Verify token in database
        RefreshToken dbToken = refreshTokenDAO.findByTokenId(tokenId);
        
        if (dbToken == null) {
            ResponseUtil.sendUnauthorized(response, "Refresh token not found");
            return;
        }
        
        if (!dbToken.isValid()) {
            ResponseUtil.sendUnauthorized(response, "Refresh token has been revoked or expired");
            return;
        }
        
        // Get user info
        TaiKhoan account = taiKhoanService.findByUsername(username);
        
        if (account == null || !account.isActive()) {
            ResponseUtil.sendUnauthorized(response, "User not found or account disabled");
            return;
        }
        
        // Generate new access token
        String newAccessToken = JWTUtil.generateAccessToken(
            account.getUsername(),
            account.getMaTK(),
            account.getRole()
        );
        
        // Build response
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("success", true);
        responseData.put("accessToken", newAccessToken);
        responseData.put("expiresIn", 1800); // 30 minutes
        
        ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_OK, responseData);
    }
    
    /**
     * POST /api/auth/logout
     * Revoke refresh token
     */
    private void handleLogout(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        // Parse request body
        String requestBody = request.getReader().lines().collect(Collectors.joining());
        RefreshTokenRequest logoutRequest;
        
        try {
            logoutRequest = gson.fromJson(requestBody, RefreshTokenRequest.class);
        } catch (Exception e) {
            ResponseUtil.sendBadRequest(response, "Invalid JSON format");
            return;
        }
        
        if (logoutRequest == null || logoutRequest.getRefreshToken() == null) {
            ResponseUtil.sendBadRequest(response, "Refresh token is required");
            return;
        }
        
        String refreshToken = logoutRequest.getRefreshToken();
        String tokenId = JWTUtil.getTokenIdFromToken(refreshToken);
        
        if (tokenId == null) {
            ResponseUtil.sendBadRequest(response, "Invalid refresh token");
            return;
        }
        
        // Revoke token
        boolean revoked = refreshTokenDAO.revokeToken(tokenId);
        
        if (revoked) {
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("success", true);
            responseData.put("message", "Logged out successfully");
            ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_OK, responseData);
        } else {
            ResponseUtil.sendInternalError(response, "Failed to logout");
        }
    }
    
    /**
     * Hash token for storage (SHA-256)
     */
    private String hashToken(String token) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(token.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }
}
