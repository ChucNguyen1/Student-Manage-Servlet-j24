package com.student.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.student.dao.ThoiKhoaBieuDAO;
import com.student.mapper.ThoiKhoaBieuMapper;
import com.student.model.ThoiKhoaBieu;
import com.student.utils.DBConnection;

public class ThoiKhoaBieuDAOImpl implements ThoiKhoaBieuDAO {

	@Override
	public List<ThoiKhoaBieu> findByLopAndHocKy(int maLop, int maHocKy) {
		List<ThoiKhoaBieu> list = new ArrayList<>();
		
		String sql = "SELECT tkb.*, " +
		             "lh.tenLop, " +
		             "hk.tenHK, " +
		             "mh.tenMH, " +
		             "gv.hoTen AS tenGV " +
		             "FROM ThoiKhoaBieu tkb " +
		             "INNER JOIN LopHoc lh ON tkb.maLop = lh.maLop " +
		             "INNER JOIN HocKy hk ON tkb.maHocKy = hk.maHK " +
		             "INNER JOIN MonHoc mh ON tkb.maMonHoc = mh.maMH " +
		             "INNER JOIN GiaoVien gv ON tkb.maGV = gv.maGV " +
		             "WHERE tkb.maLop = ? AND tkb.maHocKy = ? " +
		             "ORDER BY tkb.thu, tkb.tiet";
		
		try (Connection conn = DBConnection.getNewConnection();
		     PreparedStatement ps = conn.prepareStatement(sql)) {
			
			ps.setInt(1, maLop);
			ps.setInt(2, maHocKy);
			
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					list.add(ThoiKhoaBieuMapper.mapRow(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public List<ThoiKhoaBieu> findByGiaoVienAndHocKy(int maGV, int maHocKy) {
		List<ThoiKhoaBieu> list = new ArrayList<>();
		
		String sql = "SELECT tkb.*, " +
		             "lh.tenLop, " +
		             "hk.tenHK, " +
		             "mh.tenMH, " +
		             "gv.hoTen AS tenGV " +
		             "FROM ThoiKhoaBieu tkb " +
		             "INNER JOIN LopHoc lh ON tkb.maLop = lh.maLop " +
		             "INNER JOIN HocKy hk ON tkb.maHocKy = hk.maHK " +
		             "INNER JOIN MonHoc mh ON tkb.maMonHoc = mh.maMH " +
		             "INNER JOIN GiaoVien gv ON tkb.maGV = gv.maGV " +
		             "WHERE tkb.maGV = ? AND tkb.maHocKy = ? " +
		             "ORDER BY tkb.thu, tkb.tiet";
		
		try (Connection conn = DBConnection.getNewConnection();
		     PreparedStatement ps = conn.prepareStatement(sql)) {
			
			ps.setInt(1, maGV);
			ps.setInt(2, maHocKy);
			
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					list.add(ThoiKhoaBieuMapper.mapRow(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public ThoiKhoaBieu findByUnique(int maLop, int maHocKy, int thu, int tiet) {
		String sql = "SELECT tkb.*, " +
		             "lh.tenLop, " +
		             "hk.tenHK, " +
		             "mh.tenMH, " +
		             "gv.hoTen AS tenGV " +
		             "FROM ThoiKhoaBieu tkb " +
		             "INNER JOIN LopHoc lh ON tkb.maLop = lh.maLop " +
		             "INNER JOIN HocKy hk ON tkb.maHocKy = hk.maHK " +
		             "INNER JOIN MonHoc mh ON tkb.maMonHoc = mh.maMH " +
		             "INNER JOIN GiaoVien gv ON tkb.maGV = gv.maGV " +
		             "WHERE tkb.maLop = ? AND tkb.maHocKy = ? AND tkb.thu = ? AND tkb.tiet = ?";
		
		try (Connection conn = DBConnection.getNewConnection();
		     PreparedStatement ps = conn.prepareStatement(sql)) {
			
			ps.setInt(1, maLop);
			ps.setInt(2, maHocKy);
			ps.setInt(3, thu);
			ps.setInt(4, tiet);
			
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return ThoiKhoaBieuMapper.mapRow(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean checkGiaoVienConflict(int maGV, int maHocKy, int thu, int tiet, int excludeMaLop) {
		String sql = "SELECT COUNT(*) FROM ThoiKhoaBieu " +
		             "WHERE maGV = ? AND maHocKy = ? AND thu = ? AND tiet = ?";
		
		if (excludeMaLop > 0) {
			sql += " AND maLop <> ?";
		}
		
		try (Connection conn = DBConnection.getNewConnection();
		     PreparedStatement ps = conn.prepareStatement(sql)) {
			
			ps.setInt(1, maGV);
			ps.setInt(2, maHocKy);
			ps.setInt(3, thu);
			ps.setInt(4, tiet);
			
			if (excludeMaLop > 0) {
				ps.setInt(5, excludeMaLop);
			}
			
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public Map<String, Object> getGiaoVienConflictDetail(int maGV, int maHocKy, int thu, int tiet, int excludeMaLop) {
		Map<String, Object> result = new HashMap<>();
		result.put("conflictCount", 0);
		result.put("danhSachLop", "");
		
		String sql = "SELECT COUNT(*) AS conflictCount, " +
		             "STRING_AGG(lh.tenLop, ', ') AS danhSachLop " +
		             "FROM ThoiKhoaBieu tkb " +
		             "INNER JOIN LopHoc lh ON tkb.maLop = lh.maLop " +
		             "WHERE tkb.maGV = ? AND tkb.maHocKy = ? AND tkb.thu = ? AND tkb.tiet = ?";
		
		if (excludeMaLop > 0) {
			sql += " AND tkb.maLop <> ?";
		}
		
		try (Connection conn = DBConnection.getNewConnection();
		     PreparedStatement ps = conn.prepareStatement(sql)) {
			
			ps.setInt(1, maGV);
			ps.setInt(2, maHocKy);
			ps.setInt(3, thu);
			ps.setInt(4, tiet);
			
			if (excludeMaLop > 0) {
				ps.setInt(5, excludeMaLop);
			}
			
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					result.put("conflictCount", rs.getInt("conflictCount"));
					result.put("danhSachLop", rs.getString("danhSachLop"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public boolean insert(ThoiKhoaBieu tkb) {
		String sql = "INSERT INTO ThoiKhoaBieu (maLop, maHocKy, maMonHoc, maGV, thu, tiet, phongHoc) " +
		             "VALUES (?, ?, ?, ?, ?, ?, ?)";
		
		try (Connection conn = DBConnection.getNewConnection();
		     PreparedStatement ps = conn.prepareStatement(sql)) {
			
			ps.setInt(1, tkb.getMaLop());
			ps.setInt(2, tkb.getMaHocKy());
			ps.setInt(3, tkb.getMaMonHoc());
			ps.setInt(4, tkb.getMaGV());
			ps.setInt(5, tkb.getThu());
			ps.setInt(6, tkb.getTiet());
			ps.setString(7, tkb.getPhongHoc());
			
			return ps.executeUpdate() > 0;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(ThoiKhoaBieu tkb) {
		String sql = "UPDATE ThoiKhoaBieu " +
		             "SET maMonHoc = ?, maGV = ?, phongHoc = ?, updatedAt = GETDATE() " +
		             "WHERE maLop = ? AND maHocKy = ? AND thu = ? AND tiet = ?";
		
		try (Connection conn = DBConnection.getNewConnection();
		     PreparedStatement ps = conn.prepareStatement(sql)) {
			
			ps.setInt(1, tkb.getMaMonHoc());
			ps.setInt(2, tkb.getMaGV());
			ps.setString(3, tkb.getPhongHoc());
			ps.setInt(4, tkb.getMaLop());
			ps.setInt(5, tkb.getMaHocKy());
			ps.setInt(6, tkb.getThu());
			ps.setInt(7, tkb.getTiet());
			
			return ps.executeUpdate() > 0;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(int maLop, int maHocKy, int thu, int tiet) {
		String sql = "DELETE FROM ThoiKhoaBieu WHERE maLop = ? AND maHocKy = ? AND thu = ? AND tiet = ?";
		
		try (Connection conn = DBConnection.getNewConnection();
		     PreparedStatement ps = conn.prepareStatement(sql)) {
			
			ps.setInt(1, maLop);
			ps.setInt(2, maHocKy);
			ps.setInt(3, thu);
			ps.setInt(4, tiet);
			
			return ps.executeUpdate() > 0;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteAll(int maLop, int maHocKy) {
		String sql = "DELETE FROM ThoiKhoaBieu WHERE maLop = ? AND maHocKy = ?";
		
		try (Connection conn = DBConnection.getNewConnection();
		     PreparedStatement ps = conn.prepareStatement(sql)) {
			
			ps.setInt(1, maLop);
			ps.setInt(2, maHocKy);
			
			return ps.executeUpdate() >= 0; 
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public int countScheduled(int maLop, int maHocKy) {
		String sql = "SELECT COUNT(*) FROM ThoiKhoaBieu WHERE maLop = ? AND maHocKy = ?";
		
		try (Connection conn = DBConnection.getNewConnection();
		     PreparedStatement ps = conn.prepareStatement(sql)) {
			
			ps.setInt(1, maLop);
			ps.setInt(2, maHocKy);
			
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
}
