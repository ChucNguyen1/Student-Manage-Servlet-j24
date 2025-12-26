package com.student.model;

import java.sql.Timestamp;

/**
 * Model: RefreshToken
 * Lưu trữ refresh tokens trong database
 */
public class RefreshToken {
    private int id;
    private String tokenId;      // UUID unique identifier
    private int userId;
    private String tokenHash;    // Hash của token để bảo mật
    private Timestamp expiresAt;
    private Timestamp createdAt;
    private boolean revoked;     // Đánh dấu token đã bị thu hồi
    
    public RefreshToken() {
    }
    
    public RefreshToken(String tokenId, int userId, String tokenHash, Timestamp expiresAt) {
        this.tokenId = tokenId;
        this.userId = userId;
        this.tokenHash = tokenHash;
        this.expiresAt = expiresAt;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTokenId() {
        return tokenId;
    }
    
    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getTokenHash() {
        return tokenHash;
    }
    
    public void setTokenHash(String tokenHash) {
        this.tokenHash = tokenHash;
    }
    
    public Timestamp getExpiresAt() {
        return expiresAt;
    }
    
    public void setExpiresAt(Timestamp expiresAt) {
        this.expiresAt = expiresAt;
    }
    
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    public boolean isRevoked() {
        return revoked;
    }
    
    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }
    
    /**
     * Kiểm tra token có còn valid không
     */
    public boolean isValid() {
        if (revoked) {
            return false;
        }
        return expiresAt != null && expiresAt.after(new Timestamp(System.currentTimeMillis()));
    }
}
