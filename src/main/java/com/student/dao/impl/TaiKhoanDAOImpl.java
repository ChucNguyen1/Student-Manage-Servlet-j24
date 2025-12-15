package com.student.dao.impl;

import com.student.dao.TaiKhoanDAO;
import com.student.model.TaiKhoan;
import com.student.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaiKhoanDAOImpl implements TaiKhoanDAO {

    /**
     * Kiểm tra đăng nhập và lấy thông tin người dùng
     * 
     * @param username 
     * @param password 
     * @return TaiKhoan object với đầy đủ thông tin, hoặc null nếu sai
     */
    @Override
    public TaiKhoan checkLogin(String username, String password) {
        String sql = "SELECT " +
                     "    tk.maTK, " +
                     "    tk.username, " +
                     "    tk.password, " +
                     "    tk.role, " +
                     "    tk.maGV, " +
                     "    tk.maHS, " +
                     "    tk.isActive, " +
                     "    tk.createdAt, " +
                     "    tk.updatedAt, " +
                     "    COALESCE(gv.hoTen, hs.hoTen, 'Admin') AS hoTenHienThi " +
                     "FROM TaiKhoan tk " +
                     "LEFT JOIN GiaoVien gv ON tk.maGV = gv.maGV " +
                     "LEFT JOIN HocSinh hs ON tk.maHS = hs.maHS " +
                     "WHERE tk.username = ? " +
                     "  AND tk.password = ? " +
                     "  AND tk.isActive = 1";

        try (Connection conn = DBConnection.getNewConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTaiKhoan(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error in checkLogin: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Tìm tài khoản theo username 
     */
    @Override
    public TaiKhoan findByUsername(String username) {
        String sql = "SELECT " +
                     "    tk.maTK, " +
                     "    tk.username, " +
                     "    tk.password, " +
                     "    tk.role, " +
                     "    tk.maGV, " +
                     "    tk.maHS, " +
                     "    tk.isActive, " +
                     "    tk.createdAt, " +
                     "    tk.updatedAt, " +
                     "    COALESCE(gv.hoTen, hs.hoTen, 'Admin') AS hoTenHienThi " +
                     "FROM TaiKhoan tk " +
                     "LEFT JOIN GiaoVien gv ON tk.maGV = gv.maGV " +
                     "LEFT JOIN HocSinh hs ON tk.maHS = hs.maHS " +
                     "WHERE tk.username = ?";

        try (Connection conn = DBConnection.getNewConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTaiKhoan(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error in findByUsername: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Tìm tài khoản theo maTK 
     */
    @Override
    public TaiKhoan findById(int maTK) {
        String sql = "SELECT " +
                     "    tk.maTK, " +
                     "    tk.username, " +
                     "    tk.password, " +
                     "    tk.role, " +
                     "    tk.maGV, " +
                     "    tk.maHS, " +
                     "    tk.isActive, " +
                     "    tk.createdAt, " +
                     "    tk.updatedAt, " +
                     "    COALESCE(gv.hoTen, hs.hoTen, 'Admin') AS hoTenHienThi " +
                     "FROM TaiKhoan tk " +
                     "LEFT JOIN GiaoVien gv ON tk.maGV = gv.maGV " +
                     "LEFT JOIN HocSinh hs ON tk.maHS = hs.maHS " +
                     "WHERE tk.maTK = ?";

        try (Connection conn = DBConnection.getNewConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maTK);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTaiKhoan(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error in findById: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Thêm mới tài khoản
     */
    @Override
    public boolean insert(TaiKhoan taiKhoan) {
        String sql = "INSERT INTO TaiKhoan (username, password, role, maGV, maHS, isActive) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getNewConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, taiKhoan.getUsername());
            ps.setString(2, taiKhoan.getPassword());
            ps.setString(3, taiKhoan.getRole());
            if (taiKhoan.getMaGV() != null) {
                ps.setInt(4, taiKhoan.getMaGV());
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }
            
            if (taiKhoan.getMaHS() != null) {
                ps.setInt(5, taiKhoan.getMaHS());
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }
            
            ps.setBoolean(6, taiKhoan.isActive());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error in insert: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Cập nhật tài khoản
     */
    @Override
    public boolean update(TaiKhoan taiKhoan) {
        String sql = "UPDATE TaiKhoan " +
                     "SET username = ?, " +
                     "    role = ?, " +
                     "    maGV = ?, " +
                     "    maHS = ?, " +
                     "    isActive = ?, " +
                     "    updatedAt = GETDATE() " +
                     "WHERE maTK = ?";

        try (Connection conn = DBConnection.getNewConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, taiKhoan.getUsername());
            ps.setString(2, taiKhoan.getRole());
            
            if (taiKhoan.getMaGV() != null) {
                ps.setInt(3, taiKhoan.getMaGV());
            } else {
                ps.setNull(3, java.sql.Types.INTEGER);
            }
            
            if (taiKhoan.getMaHS() != null) {
                ps.setInt(4, taiKhoan.getMaHS());
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }
            
            ps.setBoolean(5, taiKhoan.isActive());
            ps.setInt(6, taiKhoan.getMaTK());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error in update: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Cập nhật mật khẩu
     */
    @Override
    public boolean updatePassword(int maTK, String newPassword) {
        String sql = "UPDATE TaiKhoan " +
                     "SET password = ?, " +
                     "    updatedAt = GETDATE() " +
                     "WHERE maTK = ?";

        try (Connection conn = DBConnection.getNewConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newPassword);
            ps.setInt(2, maTK);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error in updatePassword: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Vô hiệu hóa tài khoản 
     */
    @Override
    public boolean deactivate(int maTK) {
        String sql = "UPDATE TaiKhoan " +
                     "SET isActive = 0, " +
                     "    updatedAt = GETDATE() " +
                     "WHERE maTK = ?";

        try (Connection conn = DBConnection.getNewConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maTK);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error in deactivate: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Kích hoạt lại tài khoản
     */
    @Override
    public boolean activate(int maTK) {
        String sql = "UPDATE TaiKhoan " +
                     "SET isActive = 1, " +
                     "    updatedAt = GETDATE() " +
                     "WHERE maTK = ?";

        try (Connection conn = DBConnection.getNewConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maTK);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error in activate: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 
     * @param rs ResultSet từ query
     * @return TaiKhoan object đầy đủ thông tin
     */
    private TaiKhoan mapResultSetToTaiKhoan(ResultSet rs) throws SQLException {
        TaiKhoan tk = new TaiKhoan();
        
        tk.setMaTK(rs.getInt("maTK"));
        tk.setUsername(rs.getString("username"));
        tk.setPassword(rs.getString("password"));
        tk.setRole(rs.getString("role"));
        
        // Handle nullable Integer fields
        int maGV = rs.getInt("maGV");
        tk.setMaGV(rs.wasNull() ? null : maGV);
        
        int maHS = rs.getInt("maHS");
        tk.setMaHS(rs.wasNull() ? null : maHS);
        
        tk.setHoTenHienThi(rs.getString("hoTenHienThi"));
        tk.setActive(rs.getBoolean("isActive"));
        tk.setCreatedAt(rs.getTimestamp("createdAt"));
        tk.setUpdatedAt(rs.getTimestamp("updatedAt"));
        
        return tk;
    }
}
