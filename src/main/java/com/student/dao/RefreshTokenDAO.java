package com.student.dao;

import com.student.model.RefreshToken;

/**
 * DAO interface for RefreshToken
 */
public interface RefreshTokenDAO {
    
    /**
     * Lưu refresh token vào database
     * 
     * @param refreshToken RefreshToken object
     * @return true nếu thành công
     */
    boolean save(RefreshToken refreshToken);
    
    /**
     * Tìm refresh token theo tokenId
     * 
     * @param tokenId UUID của token
     * @return RefreshToken object hoặc null
     */
    RefreshToken findByTokenId(String tokenId);
    
    /**
     * Revoke (thu hồi) refresh token
     * 
     * @param tokenId UUID của token
     * @return true nếu thành công
     */
    boolean revokeToken(String tokenId);
    
    /**
     * Revoke tất cả tokens của user
     * 
     * @param userId User ID
     * @return true nếu thành công
     */
    boolean revokeAllTokensByUser(int userId);
    
    /**
     * Xóa các tokens đã hết hạn
     * 
     * @return số lượng tokens đã xóa
     */
    int deleteExpiredTokens();
    
    /**
     * Đếm số lượng active tokens của user
     * 
     * @param userId User ID
     * @return số lượng active tokens
     */
    int countActiveTokensByUser(int userId);
}
