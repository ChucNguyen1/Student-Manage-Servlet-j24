package com.student.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.student.dao.MonHocDAO;
import com.student.mapper.MonHocMapper;
import com.student.model.MonHoc;
import com.student.utils.DBConnection;

public class MonHocDAOImpl implements MonHocDAO {

	@Override
	public List<MonHoc> findAndPaginate(String searchKey, int pageNumber, int pageSize) {
		List<MonHoc> list = new ArrayList<>();
		String sql = "SELECT * FROM MonHoc WHERE trangThai = 1";
		if (searchKey != null && !searchKey.isEmpty()) {
			sql += " AND tenMH LIKE ?";
		}
		sql += " ORDER BY maMH DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			int index = 1;
			if (searchKey != null && !searchKey.isEmpty()) {
				ps.setString(index++, "%" + searchKey + "%");
			}
			ps.setInt(index++, (pageNumber - 1) * pageSize);
			ps.setInt(index++, pageSize);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next())
					list.add(MonHocMapper.mapRow(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int count(String searchKey) {
		String sql = "SELECT COUNT(*) FROM MonHoc WHERE trangThai = 1";
		if (searchKey != null && !searchKey.isEmpty())
			sql += " AND tenMH LIKE ?";

		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			if (searchKey != null && !searchKey.isEmpty())
				ps.setString(1, "%" + searchKey + "%");
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next())
					return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public boolean insert(MonHoc mh) {
		String sql = "INSERT INTO MonHoc (tenMH, soTiet, trangThai) VALUES (?, ?, 1)";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, mh.getTenMH());
			ps.setInt(2, mh.getSoTiet());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(MonHoc mh) {
		String sql = "UPDATE MonHoc SET tenMH=?, soTiet=? WHERE maMH=?";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, mh.getTenMH());
			ps.setInt(2, mh.getSoTiet());
			ps.setInt(3, mh.getMaMH());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(int maMH) {
		String sql = "DELETE FROM MonHoc WHERE maMH=?";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, maMH);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateStatus(int maMH, boolean status) {
		String sql = "UPDATE MonHoc SET trangThai=? WHERE maMH=?";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setBoolean(1, status);
			ps.setInt(2, maMH);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean isUsed(int maMH) {
		String sql1 = "SELECT COUNT(*) FROM BangDiem WHERE maMonHoc = ?";
		String sql2 = "SELECT COUNT(*) FROM PhanCong WHERE maMonHoc = ?";
		try (Connection conn = DBConnection.getNewConnection()) {
			try (PreparedStatement ps = conn.prepareStatement(sql1)) {
				ps.setInt(1, maMH);
				ResultSet rs = ps.executeQuery();
				if (rs.next() && rs.getInt(1) > 0)
					return true;
			}
			try (PreparedStatement ps = conn.prepareStatement(sql2)) {
				ps.setInt(1, maMH);
				ResultSet rs = ps.executeQuery();
				if (rs.next() && rs.getInt(1) > 0)
					return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<MonHoc> findAll() {
		List<MonHoc> list = new ArrayList<>();
		String sql = "SELECT mh.*, t.tenTo FROM MonHoc mh LEFT JOIN ToBoMon t WHERE trangThai = 1";
		try (Connection conn = DBConnection.getNewConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next())
				list.add(MonHocMapper.mapRow(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public MonHoc findById(int maMH) {
		String sql = "SELECT mh.*, t.tenTo FROM MonHoc mh " + "LEFT JOIN ToBoMon t ON mh.maTo = t.maTo "
				+ "WHERE mh.maMH = ?";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, maMH);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next())
					return MonHocMapper.mapRow(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}