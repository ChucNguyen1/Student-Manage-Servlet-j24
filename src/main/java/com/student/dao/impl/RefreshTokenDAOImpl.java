package com.student.dao.impl;

import com.student.dao.RefreshTokenDAO;
import com.student.model.RefreshToken;
import com.student.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Implementation of RefreshTokenDAO
 */
public class RefreshTokenDAOImpl implements RefreshTokenDAO {
    
    @Override
    public boolean save(RefreshToken refreshToken) {
        String sql = "INSERT INTO refresh_tokens (token_id, user_id, token_hash, expires_at, created_at, revoked) " +
                     "VALUES (?, ?, ?, ?, GETDATE(), 0)";
        
        try (Connection conn = DBConnection.getNewConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, refreshToken.getTokenId());
            ps.setInt(2, refreshToken.getUserId());
            ps.setString(3, refreshToken.getTokenHash());
            ps.setTimestamp(4, refreshToken.getExpiresAt());
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error saving refresh token: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public RefreshToken findByTokenId(String tokenId) {
        String sql = "SELECT * FROM refresh_tokens WHERE token_id = ?";
        
        try (Connection conn = DBConnection.getNewConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, tokenId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToRefreshToken(rs);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding refresh token: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    public boolean revokeToken(String tokenId) {
        String sql = "UPDATE refresh_tokens SET revoked = 1 WHERE token_id = ?";
        
        try (Connection conn = DBConnection.getNewConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, tokenId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error revoking token: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean revokeAllTokensByUser(int userId) {
        String sql = "UPDATE refresh_tokens SET revoked = 1 WHERE user_id = ? AND revoked = 0";
        
        try (Connection conn = DBConnection.getNewConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            ps.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error revoking all tokens for user: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public int deleteExpiredTokens() {
        String sql = "DELETE FROM refresh_tokens WHERE expires_at < GETDATE()";
        
        try (Connection conn = DBConnection.getNewConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            return ps.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error deleting expired tokens: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }
    
    @Override
    public int countActiveTokensByUser(int userId) {
        String sql = "SELECT COUNT(*) FROM refresh_tokens " +
                     "WHERE user_id = ? AND revoked = 0 AND expires_at > GETDATE()";
        
        try (Connection conn = DBConnection.getNewConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error counting active tokens: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0;
    }
    
    /**
     * Map ResultSet to RefreshToken object
     */
    private RefreshToken mapResultSetToRefreshToken(ResultSet rs) throws SQLException {
        RefreshToken token = new RefreshToken();
        token.setId(rs.getInt("id"));
        token.setTokenId(rs.getString("token_id"));
        token.setUserId(rs.getInt("user_id"));
        token.setTokenHash(rs.getString("token_hash"));
        token.setExpiresAt(rs.getTimestamp("expires_at"));
        token.setCreatedAt(rs.getTimestamp("created_at"));
        token.setRevoked(rs.getBoolean("revoked"));
        return token;
    }
}
