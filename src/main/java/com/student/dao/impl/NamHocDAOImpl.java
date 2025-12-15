package com.student.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.student.dao.NamHocDAO;
import com.student.mapper.NamHocMapper;
import com.student.model.NamHoc;
import com.student.utils.DBConnection;

public class NamHocDAOImpl implements NamHocDAO {

	@Override
	public List<NamHoc> findAndPaginate(String searchKey, int pageNumber, int pageSize) {
		List<NamHoc> list = new ArrayList<>();
		String sql = "SELECT * FROM NamHoc WHERE trangThai = 1";

		if (searchKey != null && !searchKey.isEmpty()) {
			sql += " AND (maNH LIKE ? OR tenNH LIKE ?)";
		}
		sql += " ORDER BY maNH DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			int index = 1;
			if (searchKey != null && !searchKey.isEmpty()) {
				ps.setString(index++, "%" + searchKey + "%");
				ps.setString(index++, "%" + searchKey + "%");
			}
			ps.setInt(index++, (pageNumber - 1) * pageSize);
			ps.setInt(index++, pageSize);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next())
					list.add(NamHocMapper.mapRow(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int count(String searchKey) {
		String sql = "SELECT COUNT(*) FROM NamHoc WHERE trangThai = 1";
		if (searchKey != null && !searchKey.isEmpty()) {
			sql += " AND (maNH LIKE ? OR tenNH LIKE ?)";
		}
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			if (searchKey != null && !searchKey.isEmpty()) {
				ps.setString(1, "%" + searchKey + "%");
				ps.setString(2, "%" + searchKey + "%");
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
	public boolean insert(NamHoc nh) {
		String sql = "INSERT INTO NamHoc (maNH, tenNH, ngayBatDau, ngayKetThuc, trangThai) VALUES (?, ?, ?, ?, 1)";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, nh.getMaNH());
			ps.setString(2, nh.getTenNH());
			ps.setDate(3, nh.getNgayBatDau());
			ps.setDate(4, nh.getNgayKetThuc());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(NamHoc nh) {
		String sql = "UPDATE NamHoc SET tenNH=?, ngayBatDau=?, ngayKetThuc=? WHERE maNH=?";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, nh.getTenNH());
			ps.setDate(2, nh.getNgayBatDau());
			ps.setDate(3, nh.getNgayKetThuc());
			ps.setString(4, nh.getMaNH());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(String maNH) {
		String sql = "DELETE FROM NamHoc WHERE maNH = ?";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, maNH);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateStatus(String maNH, boolean status) {
		String sql = "UPDATE NamHoc SET trangThai = ? WHERE maNH = ?";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setBoolean(1, status);
			ps.setString(2, maNH);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean isUsed(String maNH) {
		String sql1 = "SELECT COUNT(*) FROM LopHoc WHERE maNH = ?";
		String sql2 = "SELECT COUNT(*) FROM HocKy WHERE maNH = ?";

		try (Connection conn = DBConnection.getNewConnection()) {
			try (PreparedStatement ps = conn.prepareStatement(sql1)) {
				ps.setString(1, maNH);
				ResultSet rs = ps.executeQuery();
				if (rs.next() && rs.getInt(1) > 0)
					return true; 
			}
			try (PreparedStatement ps = conn.prepareStatement(sql2)) {
				ps.setString(1, maNH);
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
	public NamHoc findById(String maNH) {
		String sql = "SELECT * FROM NamHoc WHERE maNH = ?";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, maNH);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next())
					return NamHocMapper.mapRow(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean checkExist(String maNH) {
		String sql = "SELECT COUNT(*) FROM NamHoc WHERE maNH = ?";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, maNH);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next())
					return rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<NamHoc> findAll() {
		List<NamHoc> list = new ArrayList<>();
		String sql = "SELECT * FROM NamHoc WHERE trangThai = 1 ORDER BY maNH DESC";

		try (Connection conn = DBConnection.getNewConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				list.add(NamHocMapper.mapRow(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}