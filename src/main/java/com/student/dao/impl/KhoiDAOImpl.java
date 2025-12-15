package com.student.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.student.dao.KhoiDAO;
import com.student.mapper.KhoiMapper;
import com.student.model.Khoi;
import com.student.utils.DBConnection;

public class KhoiDAOImpl implements KhoiDAO {

	@Override
	public boolean insert(Khoi khoi) {
		String sql = "INSERT INTO Khoi (tenKhoi) VALUES (?)";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, khoi.getTenKhoi());
			int rowsAffected = ps.executeUpdate();

			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Lỗi khi thêm mới Khối");
			return false;
		}
	}

	@Override
	public boolean delete(int maKhoi) {
		String sql = "DELETE FROM Khoi WHERE maKhoi = ?";

		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, maKhoi);

			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Lỗi khi xóa Khối: " + e.getMessage());
			return false;
		}
	}

	@Override
	public boolean update(Khoi khoi) {
		String sql = "UPDATE Khoi SET tenKhoi = ? WHERE maKhoi = ?";

		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, khoi.getTenKhoi());
			ps.setInt(2, khoi.getMaKhoi());

			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Lỗi khi cập nhật Khối: " + e.getMessage());
			return false;
		}
	}

	@Override
	public int count(String searchKey) {
		String sql = "SELECT COUNT(*) FROM Khoi";
		if (searchKey != null && !searchKey.isEmpty()) {
			sql += " WHERE tenKhoi LIKE ?";
		}

		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			if (searchKey != null && !searchKey.isEmpty()) {
				ps.setString(1, "%" + searchKey + "%");
			}

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1); 
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0; 
	}

	/**
	 * Lấy dữ liệu phân trang
	 */
	@Override
	public List<Khoi> findAndPaginate(String searchKey, int pageNumber, int pageSize) {
		List<Khoi> results = new ArrayList<>();
		String sql = "SELECT * FROM Khoi";
		if (searchKey != null && !searchKey.isEmpty()) {
			sql += " WHERE tenKhoi LIKE ?";
		}
		sql += " ORDER BY maKhoi ASC"; 

		sql += " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			int paramIndex = 1;

			if (searchKey != null && !searchKey.isEmpty()) {
				ps.setString(paramIndex++, "%" + searchKey + "%");
			}
			int offset = (pageNumber - 1) * pageSize;
			ps.setInt(paramIndex++, offset);
			ps.setInt(paramIndex++, pageSize);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					results.add(KhoiMapper.mapRow(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}

	public static void main(String[] args) {
		KhoiDAO khoiDAO = new KhoiDAOImpl();

	}

	@Override
	public List<Khoi> getAll() {
		return null;
	}

	@Override
	public List<Khoi> findAll() {
		List<Khoi> list = new ArrayList<>();
		String sql = "SELECT * FROM Khoi WHERE trangThai = 1 ORDER BY tenKhoi ASC";

		try (Connection conn = DBConnection.getNewConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				list.add(KhoiMapper.mapRow(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}