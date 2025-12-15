package com.student.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.student.dao.ToBoMonDAO;
import com.student.mapper.ToBoMonMapper;
import com.student.model.ToBoMon;
import com.student.utils.DBConnection;

public class ToBoMonDAOImpl implements ToBoMonDAO {

	@Override
	public List<ToBoMon> getAllToBoMon() {
		List<ToBoMon> list = new ArrayList<>();
		String sql = "SELECT t.maTo, t.tenTo, t.moTa, t.maToTruong, " 
				+ "       g.hoTen AS tenToTruong, "
				+ "       (SELECT COUNT(*) FROM GiaoVien WHERE maTo = t.maTo AND trangThai = 1) AS soLuongGiaoVien " 
				+ "FROM ToBoMon t "
				+ "LEFT JOIN GiaoVien g ON t.maToTruong = g.maGV "
				+ "ORDER BY t.maTo DESC";

		try (Connection conn = DBConnection.getNewConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				list.add(ToBoMonMapper.mapRow(rs));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<ToBoMon> findAndPaginate(String searchKey, int pageNumber, int pageSize) {
		List<ToBoMon> list = new ArrayList<>();
		String sql = "SELECT t.maTo, t.tenTo, t.moTa, t.maToTruong, " 
				+ "       g.hoTen AS tenToTruong, "
				+ "       (SELECT COUNT(*) FROM GiaoVien WHERE maTo = t.maTo AND trangThai = 1) AS soLuongGiaoVien " 
				+ "FROM ToBoMon t "
				+ "LEFT JOIN GiaoVien g ON t.maToTruong = g.maGV "
				+ "WHERE 1=1 ";
		if (searchKey != null && !searchKey.isEmpty()) {
			sql += " AND (t.tenTo LIKE ? OR t.moTa LIKE ?) ";
		}

		sql += " ORDER BY t.maTo DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

		try (Connection conn = DBConnection.getNewConnection(); 
				PreparedStatement ps = conn.prepareStatement(sql)) {

			int index = 1;

			if (searchKey != null && !searchKey.isEmpty()) {
				String keyword = "%" + searchKey + "%";
				ps.setString(index++, keyword);
				ps.setString(index++, keyword);
			}

			int offset = (pageNumber - 1) * pageSize;
			ps.setInt(index++, offset);
			ps.setInt(index++, pageSize);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					list.add(ToBoMonMapper.mapRow(rs));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int count(String searchKey) {
		String sql = "SELECT COUNT(*) FROM ToBoMon t WHERE 1=1";

		if (searchKey != null && !searchKey.isEmpty()) {
			sql += " AND (t.tenTo LIKE ? OR t.moTa LIKE ?) ";
		}

		try (Connection conn = DBConnection.getNewConnection(); 
				PreparedStatement ps = conn.prepareStatement(sql)) {

			if (searchKey != null && !searchKey.isEmpty()) {
				String keyword = "%" + searchKey + "%";
				ps.setString(1, keyword);
				ps.setString(2, keyword);
			}

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public ToBoMon findById(int maTo) {
		String sql = "SELECT t.maTo, t.tenTo, t.moTa, t.maToTruong, " 
				+ "       g.hoTen AS tenToTruong, "
				+ "       (SELECT COUNT(*) FROM GiaoVien WHERE maTo = t.maTo AND trangThai = 1) AS soLuongGiaoVien " 
				+ "FROM ToBoMon t "
				+ "LEFT JOIN GiaoVien g ON t.maToTruong = g.maGV "
				+ "WHERE t.maTo = ?";

		try (Connection conn = DBConnection.getNewConnection(); 
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, maTo);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return ToBoMonMapper.mapRow(rs);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean addToBoMon(ToBoMon toBoMon) {
		String sql = "INSERT INTO ToBoMon(tenTo, moTa, maToTruong) VALUES (?, ?, ?)";
		try (Connection conn = DBConnection.getNewConnection(); 
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, toBoMon.getTenTo());
			ps.setString(2, toBoMon.getMoTa());
			
			// Set maToTruong (có thể null)
			if (toBoMon.getMaToTruong() > 0) {
				ps.setInt(3, toBoMon.getMaToTruong());
			} else {
				ps.setNull(3, java.sql.Types.INTEGER);
			}
			
			return ps.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateToBoMon(ToBoMon toBoMon) {
		String sql = "UPDATE ToBoMon SET tenTo=?, moTa=?, maToTruong=? WHERE maTo=?";
		try (Connection conn = DBConnection.getNewConnection(); 
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, toBoMon.getTenTo());
			ps.setString(2, toBoMon.getMoTa());
			
			// Set maToTruong (có thể null)
			if (toBoMon.getMaToTruong() > 0) {
				ps.setInt(3, toBoMon.getMaToTruong());
			} else {
				ps.setNull(3, java.sql.Types.INTEGER);
			}
			
			ps.setInt(4, toBoMon.getMaTo());

			return ps.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateToTruong(int maTo, int maGV) {
		String sql = "UPDATE ToBoMon SET maToTruong = ? WHERE maTo = ?";
		try (Connection conn = DBConnection.getNewConnection(); 
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, maGV);
			ps.setInt(2, maTo);
			return ps.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteToBoMon(int maTo) {
		String sql = "DELETE FROM ToBoMon WHERE maTo = ?";
		try (Connection conn = DBConnection.getNewConnection(); 
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, maTo);
			return ps.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}