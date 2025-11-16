package com.student.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.student.model.NamHoc;

public class NamHocMapper {
	public static NamHoc mapRow(ResultSet rs) throws SQLException {
		NamHoc nh = new NamHoc();
		nh.setMaNH(rs.getString("maNH"));
		nh.setTenNH(rs.getString("tenNH"));
		nh.setNgayBatDau(rs.getDate("ngayBatDau"));
		nh.setNgayKetThuc(rs.getDate("ngayKetThuc"));
		nh.setTrangThai(rs.getBoolean("trangThai"));
		return nh;
	}
}