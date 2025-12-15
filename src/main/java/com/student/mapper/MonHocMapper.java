package com.student.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.student.model.MonHoc;

public class MonHocMapper {
	public static MonHoc mapRow(ResultSet rs) throws SQLException {
		MonHoc mh = new MonHoc();
		mh.setMaMH(rs.getInt("maMH"));
		mh.setTenMH(rs.getString("tenMH"));
		mh.setSoTiet(rs.getInt("soTiet"));
		mh.setTrangThai(rs.getBoolean("trangThai"));
		mh.setMaTo(rs.getInt("maTo"));
		return mh;
	}
}