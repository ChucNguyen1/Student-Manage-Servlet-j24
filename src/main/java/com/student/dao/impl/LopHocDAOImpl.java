package com.student.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.student.dao.LopHocDAO;
import com.student.mapper.LopHocMapper;
import com.student.model.LopHoc;
import com.student.utils.DBConnection;

public class LopHocDAOImpl implements LopHocDAO {

	@Override
	public List<LopHoc> findAndPaginate(String searchKey, int pageNumber, int pageSize) {
		List<LopHoc> list = new ArrayList<>();
		String sql = "SELECT l.*, k.tenKhoi, n.tenNH, g.hoTen AS tenGVCN " + "FROM LopHoc l "
				+ "JOIN Khoi k ON l.maKhoi = k.maKhoi " + "JOIN NamHoc n ON l.maNH = n.maNH "
				+ "LEFT JOIN GiaoVien g ON l.maGVCN = g.maGV " + "WHERE l.trangThai = 1 ";

		if (searchKey != null && !searchKey.isEmpty()) {
			sql += " AND (l.tenLop LIKE ? OR g.hoTen LIKE ?) ";
		}
		sql += " ORDER BY n.maNH DESC, k.tenKhoi ASC, l.tenLop ASC " + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			int idx = 1;
			if (searchKey != null && !searchKey.isEmpty()) {
				ps.setString(idx++, "%" + searchKey + "%");
				ps.setString(idx++, "%" + searchKey + "%");
			}
			ps.setInt(idx++, (pageNumber - 1) * pageSize);
			ps.setInt(idx++, pageSize);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next())
					list.add(LopHocMapper.mapRow(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<LopHoc> findAll() {
		List<LopHoc> list = new ArrayList<>();
		String sql = "SELECT * FROM LopHoc WHERE trangThai = 1 ORDER BY tenLop ASC";
		try (Connection conn = DBConnection.getNewConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next())
				list.add(LopHocMapper.mapRow(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int count(String searchKey) {
		String sql = "SELECT COUNT(*) FROM LopHoc l LEFT JOIN GiaoVien g ON l.maGVCN = g.maGV WHERE l.trangThai = 1";
		if (searchKey != null && !searchKey.isEmpty())
			sql += " AND (l.tenLop LIKE ? OR g.hoTen LIKE ?)";
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
	public boolean insert(LopHoc lh) {
		String sql = "INSERT INTO LopHoc (tenLop, maKhoi, maNH, maGVCN, trangThai) VALUES (?, ?, ?, ?, 1)";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, lh.getTenLop());
			ps.setInt(2, lh.getMaKhoi());
			ps.setString(3, lh.getMaNH());
			if (lh.getMaGVCN() > 0)
				ps.setInt(4, lh.getMaGVCN());
			else
				ps.setNull(4, java.sql.Types.INTEGER);

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(LopHoc lh) {
		String sql = "UPDATE LopHoc SET tenLop=?, maKhoi=?, maNH=?, maGVCN=? WHERE maLop=?";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, lh.getTenLop());
			ps.setInt(2, lh.getMaKhoi());
			ps.setString(3, lh.getMaNH());

			if (lh.getMaGVCN() > 0)
				ps.setInt(4, lh.getMaGVCN());
			else
				ps.setNull(4, java.sql.Types.INTEGER);

			ps.setInt(5, lh.getMaLop());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(int maLop) {
		String sql = "DELETE FROM LopHoc WHERE maLop=?";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, maLop);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateStatus(int maLop, boolean status) {
		String sql = "UPDATE LopHoc SET trangThai=? WHERE maLop=?";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setBoolean(1, status);
			ps.setInt(2, maLop);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean isUsed(int maLop) {
		String sql = "SELECT COUNT(*) FROM HocSinh WHERE maLop = ?";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, maLop);
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
	public boolean checkDuplicate(String tenLop, String maNH) {
		String sql = "SELECT COUNT(*) FROM LopHoc WHERE tenLop = ? AND maNH = ? AND trangThai = 1";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, tenLop);
			ps.setString(2, maNH);
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
	public LopHoc findById(int maLop) {
		String sql = "SELECT * FROM LopHoc WHERE maLop = ?";
		try (Connection conn = DBConnection.getNewConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, maLop);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return LopHocMapper.mapRow(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<LopHoc> findByNamHocAndKhoi(String maNH, int maKhoi) {
		List<LopHoc> list = new ArrayList<>();
		String sql = "SELECT * FROM LopHoc WHERE maNH = ? AND maKhoi = ? AND trangThai = 1 ORDER BY tenLop ASC";

		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, maNH);
			ps.setInt(2, maKhoi);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next())
					list.add(LopHocMapper.mapRow(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}