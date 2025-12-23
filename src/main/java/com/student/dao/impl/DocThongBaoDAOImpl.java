package com.student.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.student.dao.DocThongBaoDAO;
import com.student.utils.DBConnection;

public class DocThongBaoDAOImpl implements DocThongBaoDAO {

    @Override
    public boolean markAsRead(int maHS, int maTB) {
        String sql = "IF NOT EXISTS (SELECT 1 FROM DocThongBao WHERE maHS = ? AND maTB = ?) " +
                     "INSERT INTO DocThongBao(maHS, maTB, ngayDoc) VALUES(?, ?, GETDATE())";
        try (Connection conn = DBConnection.getNewConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maHS);
            ps.setInt(2, maTB);
            ps.setInt(3, maHS);
            ps.setInt(4, maTB);
            return ps.executeUpdate() >= 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isRead(int maHS, int maTB) {
        String sql = "SELECT COUNT(*) FROM DocThongBao WHERE maHS = ? AND maTB = ?";
        try (Connection conn = DBConnection.getNewConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maHS);
            ps.setInt(2, maTB);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int countUnreadAnnouncements(int maHS) {
        String sql = "SELECT COUNT(*) FROM ThongBao tb " +
                     "LEFT JOIN DocThongBao dtb ON tb.maTB = dtb.maTB AND dtb.maHS = ? " +
                     "WHERE dtb.maDocTB IS NULL";
        try (Connection conn = DBConnection.getNewConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maHS);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
