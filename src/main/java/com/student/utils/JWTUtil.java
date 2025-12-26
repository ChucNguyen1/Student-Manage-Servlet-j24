package com.student.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Utility class for JWT token generation and validation
 */
public class JWTUtil {
    
    // Secret key - NÊN LƯU TRONG BIẾN MÔI TRƯỜNG (.env file)
    private static final String SECRET_KEY = getSecretKey();
    
    // Token validity periods
    private static final long ACCESS_TOKEN_VALIDITY = 30 * 60 * 1000; // 30 phút
    private static final long REFRESH_TOKEN_VALIDITY = 7 * 24 * 60 * 60 * 1000; // 7 ngày
    
    private static SecretKey key;
    
    static {
        // Tạo secret key từ string
        key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }
    
    /**
     * Lấy secret key từ biến môi trường hoặc dùng default
     */
    private static String getSecretKey() {
        String envKey = System.getenv("JWT_SECRET_KEY");
        if (envKey != null && !envKey.trim().isEmpty()) {
            return envKey;
        }
        
        // Default key - CHỈ DÙNG CHO DEVELOPMENT
        // PRODUCTION PHẢI DÙNG BIẾN MÔI TRƯỜNG
        return "StudentManagementSystemJWTSecretKey2025VerySecureAndLongEnoughForHS512Algorithm";
    }
    
    /**
     * Tạo Access Token
     * 
     * @param username Username
     * @param userId User ID
     * @param role User role (ADMIN, GIAOVIEN, HOCSINH)
     * @return JWT Access Token
     */
    public static String generateAccessToken(String username, int userId, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("role", role);
        claims.put("type", "access");
        
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY))
                .signWith(key)
                .compact();
    }
    
    /**
     * Tạo Refresh Token
     * 
     * @param username Username
     * @param tokenId Unique token ID (UUID)
     * @return JWT Refresh Token
     */
    public static String generateRefreshToken(String username, String tokenId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("tokenId", tokenId);
        claims.put("type", "refresh");
        
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY))
                .signWith(key)
                .compact();
    }
    
    /**
     * Tạo Refresh Token với UUID tự động
     */
    public static String generateRefreshToken(String username) {
        return generateRefreshToken(username, UUID.randomUUID().toString());
    }
    
    /**
     * Validate token và parse claims
     * 
     * @param token JWT token
     * @return Claims nếu token hợp lệ
     * @throws JwtException nếu token không hợp lệ
     */
    public static Claims validateAndParseClaims(String token) throws JwtException {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    
    /**
     * Kiểm tra token có hợp lệ không
     * 
     * @param token JWT token
     * @return true nếu token hợp lệ
     */
    public static boolean isTokenValid(String token) {
        try {
            validateAndParseClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
    
    /**
     * Lấy username từ token
     */
    public static String getUsernameFromToken(String token) {
        try {
            Claims claims = validateAndParseClaims(token);
            return claims.getSubject();
        } catch (JwtException e) {
            return null;
        }
    }
    
    /**
     * Lấy user ID từ token
     */
    public static Integer getUserIdFromToken(String token) {
        try {
            Claims claims = validateAndParseClaims(token);
            return claims.get("userId", Integer.class);
        } catch (JwtException e) {
            return null;
        }
    }
    
    /**
     * Lấy role từ token
     */
    public static String getRoleFromToken(String token) {
        try {
            Claims claims = validateAndParseClaims(token);
            return claims.get("role", String.class);
        } catch (JwtException e) {
            return null;
        }
    }
    
    /**
     * Lấy token ID từ refresh token
     */
    public static String getTokenIdFromToken(String token) {
        try {
            Claims claims = validateAndParseClaims(token);
            return claims.get("tokenId", String.class);
        } catch (JwtException e) {
            return null;
        }
    }
    
    /**
     * Lấy token type (access/refresh)
     */
    public static String getTokenType(String token) {
        try {
            Claims claims = validateAndParseClaims(token);
            return claims.get("type", String.class);
        } catch (JwtException e) {
            return null;
        }
    }
    
    /**
     * Kiểm tra token đã hết hạn chưa
     */
    public static boolean isTokenExpired(String token) {
        try {
            Claims claims = validateAndParseClaims(token);
            return claims.getExpiration().before(new Date());
        } catch (JwtException e) {
            return true;
        }
    }
    
    /**
     * Lấy thời gian hết hạn của token
     */
    public static Date getExpirationDate(String token) {
        try {
            Claims claims = validateAndParseClaims(token);
            return claims.getExpiration();
        } catch (JwtException e) {
            return null;
        }
    }
    
    /**
     * Lấy thời gian còn lại của token (seconds)
     */
    public static long getTimeToExpire(String token) {
        Date expiration = getExpirationDate(token);
        if (expiration == null) {
            return 0;
        }
        long timeLeft = expiration.getTime() - System.currentTimeMillis();
        return Math.max(0, timeLeft / 1000);
    }
}
