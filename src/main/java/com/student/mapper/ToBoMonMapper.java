package com.student.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.student.model.ToBoMon;

public class ToBoMonMapper {
	public static ToBoMon mapRow(ResultSet rs) throws SQLException {
		ToBoMon to = new ToBoMon();

		to.setMaTo(rs.getInt("maTo"));
		to.setTenTo(rs.getString("tenTo"));

		try {
			to.setMoTa(rs.getString("moTa"));
		} catch (Exception e) {
		}

		try {
			to.setMaToTruong(rs.getInt("maToTruong"));
		} catch (Exception e) {
		}

		try {

			to.setTenToTruong(rs.getString("tenToTruong"));
		} catch (Exception e) {
		}

		try {
			to.setSoLuongGiaoVien(rs.getInt("soLuongGiaoVien"));
		} catch (Exception e) {
			to.setSoLuongGiaoVien(0);
		}

		return to;
	}
}