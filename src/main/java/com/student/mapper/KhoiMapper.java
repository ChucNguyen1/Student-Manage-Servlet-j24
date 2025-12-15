package com.student.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.student.model.Khoi;

public class KhoiMapper {

	/**
	 * @param rs ResultSet đang trỏ đến hàng cần ánh xạ
	 * @return Đối tượng Khoi
	 * @throws SQLException
	 */
	public static Khoi mapRow(ResultSet rs) throws SQLException {
		Khoi khoi = new Khoi();
		khoi.setMaKhoi(rs.getInt("maKhoi"));
		khoi.setTenKhoi(rs.getString("tenKhoi"));
		return khoi;
	}
}