package com.student.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.student.dao.ThongBaoDAO;
import com.student.model.ThongBao;
import com.student.utils.DBConnection; 

public class ThongBaoDAOImpl implements ThongBaoDAO {

    @Override
    public List<ThongBao> findAll(String searchKey, int page, int pageSize) {
        List<ThongBao> list = new ArrayList<>();
        String sql = "SELECT tb.*, u.username " 
                   + "FROM ThongBao tb "
                   + "LEFT JOIN Users u ON tb.maNguoiTao = u.userID "
                   + "WHERE tb.tieuDe LIKE ? "
                   + "ORDER BY tb.ngayDang DESC "
                   + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        if (searchKey == null) searchKey = "";
        String pattern = "%" + searchKey + "%";
        int offset = (page - 1) * pageSize;
        try (Connection conn = DBConnection.getNewConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, pattern);
            ps.setInt(2, offset);
            ps.setInt(3, pageSize);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ThongBao tb = new ThongBao();
                tb.setMaTB(rs.getInt("maTB"));
                tb.setTieuDe(rs.getString("tieuDe"));
                tb.setNoiDung(rs.getString("noiDung"));
                tb.setNgayDang(rs.getTimestamp("ngayDang"));
                tb.setMaNguoiTao(rs.getInt("maNguoiTao"));
                tb.setTenNguoiTao(rs.getString("username")); 
                list.add(tb);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public int count(String searchKey) {
        String sql = "SELECT COUNT(*) FROM ThongBao WHERE tieuDe LIKE ?";
        if (searchKey == null) searchKey = "";
        try (Connection conn = DBConnection.getNewConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setString(1, "%" + searchKey + "%");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
        return 0;
    }

    @Override
    public boolean insert(ThongBao tb) {
        String sql = "INSERT INTO ThongBao(tieuDe, noiDung, maNguoiTao, ngayDang) VALUES(?, ?, ?, GETDATE())";
        try (Connection conn = DBConnection.getNewConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tb.getTieuDe());
            ps.setString(2, tb.getNoiDung());
            ps.setInt(3, tb.getMaNguoiTao());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
    @Override
    public boolean update(ThongBao tb) {
        String sql = "UPDATE ThongBao SET tieuDe=?, noiDung=? WHERE maTB=?";
        
        try (Connection conn = DBConnection.getNewConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, tb.getTieuDe());
            ps.setString(2, tb.getNoiDung());
            ps.setInt(3, tb.getMaTB());
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean delete(int maTB) {
        String sql = "DELETE FROM ThongBao WHERE maTB=?";
        try (Connection conn = DBConnection.getNewConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maTB);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
}