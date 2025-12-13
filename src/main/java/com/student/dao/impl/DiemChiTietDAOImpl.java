package com.student.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.student.dao.DiemChiTietDAO;
import com.student.mapper.DiemChiTietMapper;
import com.student.model.DiemChiTiet;
import com.student.utils.DBConnection;

public class DiemChiTietDAOImpl implements DiemChiTietDAO {

	@Override
	public List<DiemChiTiet> getBangDiemLop(int maLop, int maMonHoc, int maHocKy) {
		List<DiemChiTiet> list = new ArrayList<>();

		// SQL PHỨC TẠP:
		// 1. Lấy tất cả Học Sinh trong lớp (hs)
		// 2. LEFT JOIN với DiemChiTiet (d) theo maHS, maMonHoc, maHocKy
		// 3. Kết quả: Luôn có tên học sinh. Nếu chưa có điểm, các cột điểm sẽ là NULL.

		String sql = "SELECT hs.maHS, hs.hoTen AS hoTenHS, d.* " + "FROM HocSinh hs "
				+ "LEFT JOIN DiemChiTiet d ON hs.maHS = d.maHS " + "    AND d.maMonHoc = ? AND d.maHocKy = ? "
				+ "WHERE hs.maLop = ? AND hs.trangThai = 1 " + "ORDER BY hs.hoTen ASC"; // Sắp xếp theo tên A-Z

		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, maMonHoc);
			ps.setInt(2, maHocKy);
			ps.setInt(3, maLop);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					DiemChiTiet dt = DiemChiTietMapper.mapRow(rs);

					// Vì Mapper lấy maMonHoc, maHocKy từ bảng DiemChiTiet (có thể null do Left
					// Join)
					// Ta nên gán lại giá trị từ tham số đầu vào để object luôn đủ thông tin
					dt.setMaHS(rs.getInt("maHS")); // Lấy từ bảng HocSinh (luôn có)
					dt.setMaMonHoc(maMonHoc);
					dt.setMaHocKy(maHocKy);

					list.add(dt);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean checkExist(int maHS, int maMonHoc, int maHocKy) {
		String sql = "SELECT COUNT(*) FROM DiemChiTiet WHERE maHS=? AND maMonHoc=? AND maHocKy=?";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, maHS);
			ps.setInt(2, maMonHoc);
			ps.setInt(3, maHocKy);
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
	public boolean insert(DiemChiTiet d) {
		String sql = "INSERT INTO DiemChiTiet (maHS, maMonHoc, maHocKy, " + "diemMieng_1, diemMieng_2, diemMieng_3, "
				+ "diem15p_1, diem15p_2, diem15p_3, " + "diem1Tiet_1, diem1Tiet_2, diemThi, diemTBM) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, d.getMaHS());
			ps.setInt(2, d.getMaMonHoc());
			ps.setInt(3, d.getMaHocKy());

			// Hàm setDouble xử lý null: Nếu null thì setNull, ngược lại setDouble
			setDoubleOrNull(ps, 4, d.getDiemMieng1());
			setDoubleOrNull(ps, 5, d.getDiemMieng2());
			setDoubleOrNull(ps, 6, d.getDiemMieng3());

			setDoubleOrNull(ps, 7, d.getDiem15p1());
			setDoubleOrNull(ps, 8, d.getDiem15p2());
			setDoubleOrNull(ps, 9, d.getDiem15p3());

			setDoubleOrNull(ps, 10, d.getDiem1Tiet1());
			setDoubleOrNull(ps, 11, d.getDiem1Tiet2());

			setDoubleOrNull(ps, 12, d.getDiemThi());
			setDoubleOrNull(ps, 13, d.getDiemTBM());

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(DiemChiTiet d) {
		String sql = "UPDATE DiemChiTiet SET " + "diemMieng_1=?, diemMieng_2=?, diemMieng_3=?, "
				+ "diem15p_1=?, diem15p_2=?, diem15p_3=?, " + "diem1Tiet_1=?, diem1Tiet_2=?, diemThi=?, diemTBM=? "
				+ "WHERE maHS=? AND maMonHoc=? AND maHocKy=?";

		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			setDoubleOrNull(ps, 1, d.getDiemMieng1());
			setDoubleOrNull(ps, 2, d.getDiemMieng2());
			setDoubleOrNull(ps, 3, d.getDiemMieng3());

			setDoubleOrNull(ps, 4, d.getDiem15p1());
			setDoubleOrNull(ps, 5, d.getDiem15p2());
			setDoubleOrNull(ps, 6, d.getDiem15p3());

			setDoubleOrNull(ps, 7, d.getDiem1Tiet1());
			setDoubleOrNull(ps, 8, d.getDiem1Tiet2());

			setDoubleOrNull(ps, 9, d.getDiemThi());
			setDoubleOrNull(ps, 10, d.getDiemTBM());

			// WHERE clause
			ps.setInt(11, d.getMaHS());
			ps.setInt(12, d.getMaMonHoc());
			ps.setInt(13, d.getMaHocKy());

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// --- Helper Method: Để xử lý setDouble khi giá trị là null ---
	private void setDoubleOrNull(PreparedStatement ps, int index, Double value) throws SQLException {
		if (value == null) {
			ps.setNull(index, java.sql.Types.FLOAT);
		} else {
			ps.setDouble(index, value);
		}
	}
}