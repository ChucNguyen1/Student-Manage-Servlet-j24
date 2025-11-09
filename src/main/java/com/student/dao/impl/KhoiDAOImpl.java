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

		// Dùng try-with-resources để tự động đóng Connection, PreparedStatement
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			// Thiết lập tham số cho dấu ?
			ps.setString(1, khoi.getTenKhoi());

			// Thực thi câu lệnh (INSERT, UPDATE, DELETE dùng executeUpdate())
			// Nó sẽ trả về số dòng bị ảnh hưởng
			int rowsAffected = ps.executeUpdate();

			return rowsAffected > 0; // Trả về true nếu ít nhất 1 dòng bị ảnh hưởng

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
			// Lỗi khóa ngoại (nếu Khối này đang được Lớp Học sử dụng)
			// sẽ bị bắt ở đây.
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
		// Bắt đầu câu SQL
		String sql = "SELECT COUNT(*) FROM Khoi";

		// Nếu có tìm kiếm, thêm mệnh đề WHERE
		if (searchKey != null && !searchKey.isEmpty()) {
			sql += " WHERE tenKhoi LIKE ?";
		}

		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			// Nếu có tìm kiếm, set tham số
			if (searchKey != null && !searchKey.isEmpty()) {
				ps.setString(1, "%" + searchKey + "%");
			}

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1); // Trả về giá trị của cột COUNT(*)
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0; // Trả về 0 nếu có lỗi
	}

	/**
	 * PHƯƠNG THỨC MỚI: Lấy dữ liệu phân trang
	 */
	@Override
	public List<Khoi> findAndPaginate(String searchKey, int pageNumber, int pageSize) {
		List<Khoi> results = new ArrayList<>();
		// Bắt đầu câu SQL
		String sql = "SELECT * FROM Khoi";

		// Nếu có tìm kiếm, thêm mệnh đề WHERE
		if (searchKey != null && !searchKey.isEmpty()) {
			sql += " WHERE tenKhoi LIKE ?";
		}

		// Thêm mệnh đề ORDER BY (BẮT BUỘC cho OFFSET/FETCH)
		sql += " ORDER BY maKhoi ASC"; // (hoặc tenKhoi)

		// Thêm mệnh đề Phân Trang (SQL Server 2012+)
		sql += " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			int paramIndex = 1;

			// Set tham số cho tìm kiếm (nếu có)
			if (searchKey != null && !searchKey.isEmpty()) {
				ps.setString(paramIndex++, "%" + searchKey + "%");
			}

			// Tính toán OFFSET
			int offset = (pageNumber - 1) * pageSize;

			// Set tham số cho OFFSET và FETCH
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

	// Main method để test
	public static void main(String[] args) {
		KhoiDAO khoiDAO = new KhoiDAOImpl();

	}

	@Override
	public List<Khoi> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
}