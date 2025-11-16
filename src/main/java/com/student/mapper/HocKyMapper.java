package com.student.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.student.model.HocKy;

public class HocKyMapper {
	public static HocKy mapRow(ResultSet rs) throws SQLException {
		HocKy hk = new HocKy();
		hk.setMaHK(rs.getInt("maHK"));
		hk.setTenHK(rs.getString("tenHK"));
		hk.setHeSo(rs.getInt("heSo"));
		hk.setMaNH(rs.getString("maNH"));
		hk.setTrangThai(rs.getBoolean("trangThai"));

		// Kiểm tra xem trong câu SQL có join lấy tenNH không để map vào
		try {
			hk.setTenNamHoc(rs.getString("tenNH"));
		} catch (SQLException e) {
			// Không có cột tenNH trong result set thì bỏ qua
		}
		return hk;
	}
}