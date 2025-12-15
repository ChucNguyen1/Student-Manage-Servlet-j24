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

		try {
			hk.setTenNamHoc(rs.getString("tenNH"));
		} catch (SQLException e) {

		}
		return hk;
	}
}