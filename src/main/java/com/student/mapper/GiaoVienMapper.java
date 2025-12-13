package com.student.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.student.model.GiaoVien;

public class GiaoVienMapper {
	public static GiaoVien mapRow(ResultSet rs) throws SQLException {
		GiaoVien gv = new GiaoVien();
		gv.setMaGV(rs.getInt("maGV"));
		gv.setHoTen(rs.getString("hoTen"));
		gv.setNgaySinh(rs.getDate("ngaySinh"));
		gv.setGioiTinh(rs.getString("gioiTinh"));
		gv.setSdt(rs.getString("sdt"));
		gv.setEmail(rs.getString("email"));
		gv.setDiaChi(rs.getString("diaChi"));
		gv.setTrangThai(rs.getBoolean("trangThai"));

		// Map khóa ngoại Môn học
		gv.setMaMonHocChuyenMon(rs.getInt("maMonHocChuyenMon"));

		// Map tên môn học (Lấy từ bảng MonHoc đã JOIN)
		// Dùng try-catch để tránh lỗi nếu câu SQL không join
		try {
			gv.setTenMonHocChuyenMon(rs.getString("tenMH"));
		} catch (SQLException e) {
			// Không có cột tenMH thì thôi (để null)
		}

		// Map userID (xử lý null)
		int uid = rs.getInt("userID");
		if (!rs.wasNull()) {
			gv.setUserID(uid);
		}

		return gv;
	}
}