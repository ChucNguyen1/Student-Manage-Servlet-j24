package com.student.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.student.dao.HocKyDAO;
import com.student.mapper.HocKyMapper;
import com.student.model.HocKy;
import com.student.utils.DBConnection;

public class HocKyDAOImpl implements HocKyDAO {

	@Override
	public List<HocKy> findAndPaginate(String searchKey, int pageNumber, int pageSize) {
		List<HocKy> list = new ArrayList<>();
		String sql = "SELECT hk.*, nh.tenNH " + "FROM HocKy hk " + "JOIN NamHoc nh ON hk.maNH = nh.maNH "
				+ "WHERE hk.trangThai = 1 ";

		if (searchKey != null && !searchKey.isEmpty()) {
			sql += " AND (hk.tenHK LIKE ? OR nh.tenNH LIKE ?) ";
		}

		sql += " ORDER BY nh.maNH DESC, hk.tenHK ASC " + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			int index = 1;
			if (searchKey != null && !searchKey.isEmpty()) {
				String keyword = "%" + searchKey + "%";
				ps.setString(index++, keyword);
				ps.setString(index++, keyword);
			}
			ps.setInt(index++, (pageNumber - 1) * pageSize);
			ps.setInt(index++, pageSize);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					list.add(HocKyMapper.mapRow(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int count(String searchKey) {
		String sql = "SELECT COUNT(*) FROM HocKy hk JOIN NamHoc nh ON hk.maNH = nh.maNH WHERE hk.trangThai = 1";

		if (searchKey != null && !searchKey.isEmpty()) {
			sql += " AND (hk.tenHK LIKE ? OR nh.tenNH LIKE ?)";
		}

		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			if (searchKey != null && !searchKey.isEmpty()) {
				String keyword = "%" + searchKey + "%";
				ps.setString(1, keyword);
				ps.setString(2, keyword);
			}

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
	public boolean insert(HocKy hk) {
		String sql = "INSERT INTO HocKy (tenHK, heSo, maNH, trangThai) VALUES (?, ?, ?, 1)";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, hk.getTenHK());
			ps.setInt(2, hk.getHeSo());
			ps.setString(3, hk.getMaNH());

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(HocKy hk) {
		String sql = "UPDATE HocKy SET tenHK=?, heSo=?, maNH=? WHERE maHK=?";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, hk.getTenHK());
			ps.setInt(2, hk.getHeSo());
			ps.setString(3, hk.getMaNH());
			ps.setInt(4, hk.getMaHK());

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(int maHK) {
		String sql = "DELETE FROM HocKy WHERE maHK = ?";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, maHK);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateStatus(int maHK, boolean status) {
		String sql = "UPDATE HocKy SET trangThai = ? WHERE maHK = ?";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setBoolean(1, status);
			ps.setInt(2, maHK);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public HocKy findById(int maHK) {
		String sql = "SELECT * FROM HocKy WHERE maHK = ?";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, maHK);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next())
					return HocKyMapper.mapRow(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean isUsed(int maHK) {
		String sql1 = "SELECT COUNT(*) FROM BangDiem WHERE maHocKy = ?";
		String sql2 = "SELECT COUNT(*) FROM PhanCong WHERE maHocKy = ?";

		try (Connection conn = DBConnection.getNewConnection()) {
			try (PreparedStatement ps = conn.prepareStatement(sql1)) {
				ps.setInt(1, maHK);
				ResultSet rs = ps.executeQuery();
				if (rs.next() && rs.getInt(1) > 0)
					return true;
			}
			try (PreparedStatement ps = conn.prepareStatement(sql2)) {
				ps.setInt(1, maHK);
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
	public List<HocKy> findAll() {
		List<HocKy> list = new ArrayList<>();
		String sql = "SELECT hk.*, nh.tenNH " + "FROM HocKy hk " + "JOIN NamHoc nh ON hk.maNH = nh.maNH "
				+ "WHERE hk.trangThai = 1 " + "ORDER BY nh.maNH DESC, hk.tenHK ASC"; 
		try (Connection conn = DBConnection.getNewConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				list.add(HocKyMapper.mapRow(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<HocKy> findByNamHoc(String maNH) {
		List<HocKy> list = new ArrayList<>();
		String sql = "SELECT * FROM HocKy WHERE maNH = ? AND trangThai = 1 ORDER BY tenHK ASC";

		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, maNH);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					list.add(HocKyMapper.mapRow(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}