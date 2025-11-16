package com.student.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.student.model.GiaoVien;

public class GiaoVienMapper {

	/**
	 * Ánh xạ một hàng của ResultSet sang đối tượng GiaoVien.
	 */
	public static GiaoVien mapRow(ResultSet rs) throws SQLException {
		GiaoVien gv = new GiaoVien();

		gv.setMaGV(rs.getInt("maGV"));
		gv.setHoTen(rs.getString("hoTen"));
		gv.setNgaySinh(rs.getDate("ngaySinh"));
		gv.setGioiTinh(rs.getString("gioiTinh"));
		gv.setChuyenMon(rs.getString("chuyenMon"));
		gv.setEmail(rs.getString("email"));
		gv.setSdt(rs.getString("sdt"));
		gv.setDiaChi(rs.getString("diaChi"));

		int userIdInt = rs.getInt("userID");
		if (rs.wasNull()) {
			gv.setUserID(null);
		} else {
			gv.setUserID(userIdInt);
		}

		return gv;
	}
}