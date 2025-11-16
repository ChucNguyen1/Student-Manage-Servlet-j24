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
		// SQL cơ bản
		String sql = "SELECT * FROM GiaoVien";

		// Thêm điều kiện tìm kiếm (nếu có)
		if (searchKey != null && !searchKey.isEmpty()) {
			sql += " WHERE hoTen LIKE ? OR email LIKE ?"; // Tìm theo Tên hoặc Email
		}

		// Bắt buộc phải có ORDER BY khi dùng OFFSET
		sql += " ORDER BY maGV DESC"; // Sắp xếp người mới nhất lên đầu

		// Thêm phân trang
		sql += " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			int index = 1;

			// Set tham số tìm kiếm
			if (searchKey != null && !searchKey.isEmpty()) {
				String keyword = "%" + searchKey + "%";
				ps.setString(index++, keyword);
				ps.setString(index++, keyword);
			}

			// Set tham số phân trang
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
		String sql = "SELECT COUNT(*) FROM GiaoVien";
		if (searchKey != null && !searchKey.isEmpty()) {
			sql += " WHERE hoTen LIKE ? OR email LIKE ?";
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
		String sql = "INSERT INTO GiaoVien (hoTen, ngaySinh, gioiTinh, chuyenMon, email, sdt, diaChi) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, gv.getHoTen());
			ps.setDate(2, gv.getNgaySinh()); // java.sql.Date
			ps.setString(3, gv.getGioiTinh());
			ps.setString(4, gv.getChuyenMon());
			ps.setString(5, gv.getEmail());
			ps.setString(6, gv.getSdt());
			ps.setString(7, gv.getDiaChi());

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(GiaoVien gv) {
		String sql = "UPDATE GiaoVien SET hoTen=?, ngaySinh=?, gioiTinh=?, chuyenMon=?, email=?, sdt=?, diaChi=? WHERE maGV=?";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, gv.getHoTen());
			ps.setDate(2, gv.getNgaySinh());
			ps.setString(3, gv.getGioiTinh());
			ps.setString(4, gv.getChuyenMon());
			ps.setString(5, gv.getEmail());
			ps.setString(6, gv.getSdt());
			ps.setString(7, gv.getDiaChi());
			ps.setInt(8, gv.getMaGV());

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(int maGV) {
		String sql = "DELETE FROM GiaoVien WHERE maGV=?";
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
		String sql = "SELECT * FROM GiaoVien WHERE maGV = ?";
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

	// Main test
	public static void main(String[] args) {
		GiaoVienDAO dao = new GiaoVienDAOImpl();

		System.out.println("--- TEST LIST (Page 1, 5 items) ---");
		List<GiaoVien> list = dao.findAndPaginate(null, 1, 5);
		for (GiaoVien gv : list) {
			System.out.println(gv.getMaGV() + " - " + gv.getHoTen() + " - " + gv.getChuyenMon());
		}

		System.out.println("--- TEST COUNT ---");
		System.out.println("Tổng số GV: " + dao.count(null));
	}
}