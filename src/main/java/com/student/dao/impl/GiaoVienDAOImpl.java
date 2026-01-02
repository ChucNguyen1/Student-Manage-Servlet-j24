package com.student.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.student.dao.GiaoVienDAO;
import com.student.mapper.GiaoVienMapper;
import com.student.model.GiaoVien;
import com.student.utils.DBConnection;

public class GiaoVienDAOImpl implements GiaoVienDAO {

	@Override
	public List<GiaoVien> findAndPaginate(String searchKey, int pageNumber, int pageSize) {
		List<GiaoVien> list = new ArrayList<>();

		String sql = "SELECT gv.*, t.tenTo, mh.tenMH AS tenMonHocChuyenMon " + "FROM GiaoVien gv "
				+ "LEFT JOIN ToBoMon t ON gv.maTo = t.maTo "
				+ "LEFT JOIN MonHoc mh ON gv.maMonHocChuyenMon = mh.maMH "
				+ "WHERE gv.trangThai = 1";

		if (searchKey != null && !searchKey.isEmpty()) {
			sql += " AND (gv.hoTen LIKE ? OR gv.email LIKE ?) ";
		}

		sql += " ORDER BY gv.maGV DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

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
					list.add(GiaoVienMapper.mapRow(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int count(String searchKey) {
		String sql = "SELECT COUNT(*) FROM GiaoVien WHERE trangThai = 1";

		if (searchKey != null && !searchKey.isEmpty()) {
			sql += " AND (hoTen LIKE ? OR email LIKE ?) ";
		}

		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public boolean insert(GiaoVien gv) {
		String sql = "INSERT INTO GiaoVien (hoTen, ngaySinh, gioitinh, sdt, email, diaChi, maTo, trangThai) VALUES (?, ?, ?, ?, ?, ?, ?, 1)";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, gv.getHoTen());
			ps.setDate(2, gv.getNgaySinh());
			ps.setString(3, gv.getGioiTinh());
			ps.setString(4, gv.getSdt());
			ps.setString(5, gv.getEmail());
			ps.setString(6, gv.getDiaChi());
			ps.setInt(7, gv.getMaTo());

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(GiaoVien gv) {
		String sql = "UPDATE GiaoVien SET hoTen=?, ngaySinh=?, gioitinh=?, sdt=?, email=?, diaChi=?, maTo=? WHERE maGV=?";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, gv.getHoTen());
			ps.setDate(2, gv.getNgaySinh());
			ps.setString(3, gv.getGioiTinh());
			ps.setString(4, gv.getSdt());
			ps.setString(5, gv.getEmail());
			ps.setString(6, gv.getDiaChi());
			ps.setInt(7, gv.getMaTo());
			ps.setInt(8, gv.getMaGV());

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(int maGV) {
		String sql = "UPDATE GiaoVien SET trangThai = 0 WHERE maGV=?";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, maGV);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public GiaoVien findById(int maGV) {
		String sql = "SELECT gv.*, t.tenTo, mh.tenMH AS tenMonHocChuyenMon " + "FROM GiaoVien gv "
				+ "LEFT JOIN ToBoMon t ON gv.maTo = t.maTo "
				+ "LEFT JOIN MonHoc mh ON gv.maMonHocChuyenMon = mh.maMH "
				+ "WHERE gv.maGV = ?";

		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, maGV);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return GiaoVienMapper.mapRow(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<GiaoVien> findAll() {
		List<GiaoVien> list = new ArrayList<>();
		String sql = "SELECT gv.*, t.tenTo, mh.tenMH AS tenMonHocChuyenMon FROM GiaoVien gv " +
				"LEFT JOIN ToBoMon t ON gv.maTo = t.maTo " +
				"LEFT JOIN MonHoc mh ON gv.maMonHocChuyenMon = mh.maMH " +
				"WHERE gv.trangThai = 1 ORDER BY gv.hoTen ASC";

		try (Connection conn = DBConnection.getNewConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				list.add(GiaoVienMapper.mapRow(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<GiaoVien> findByToBoMon(int maTo) {
		List<GiaoVien> list = new ArrayList<>();
		String sql = "SELECT gv.*, t.tenTo, mh.tenMH AS tenMonHocChuyenMon FROM GiaoVien gv " +
					 "LEFT JOIN ToBoMon t ON gv.maTo = t.maTo " +
					 "LEFT JOIN MonHoc mh ON gv.maMonHocChuyenMon = mh.maMH " +
					 "WHERE gv.maTo = ? AND gv.trangThai = 1 " +
					 "ORDER BY gv.hoTen ASC";
		try (Connection conn = DBConnection.getNewConnection(); 
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, maTo);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					list.add(GiaoVienMapper.mapRow(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<GiaoVien> findByChuyenMon(int maMonHoc) {
		List<GiaoVien> list = new ArrayList<>();
		String sql = "SELECT * FROM GiaoVien WHERE maMonHocChuyenMon = ? AND trangThai = 1";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, maMonHoc);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next())
					list.add(GiaoVienMapper.mapRow(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static void main(String[] args) {
		GiaoVienDAOImpl dao = new GiaoVienDAOImpl();

		System.out.println("--- TEST LIST (Page 1, 5 items) ---");
		List<GiaoVien> list = dao.findAndPaginate(null, 1, 5);
		for (GiaoVien gv : list) {
			System.out.println(gv.getMaGV() + " - " + gv.getHoTen() + " - Môn: " + gv.getMaTo());
		}

		System.out.println("--- TEST COUNT ---");
		System.out.println("Tổng số GV: " + dao.count(null));
	}
}