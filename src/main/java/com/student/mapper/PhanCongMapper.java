package com.student.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.student.model.PhanCong;

public class PhanCongMapper {
	public static PhanCong mapRow(ResultSet rs) throws SQLException {
		PhanCong pc = new PhanCong();
		// Bảng PhanCong không có maPhanCong (dùng composite PK: maLop, maMonHoc, maHocKy)
		pc.setMaGV(rs.getInt("maGV"));
		pc.setMaLop(rs.getInt("maLop"));
		pc.setMaMonHoc(rs.getInt("maMonHoc"));
		pc.setMaHocKy(rs.getInt("maHocKy"));

		try {
			pc.setTenGiaoVien(rs.getString("tenGiaoVien"));
		} catch (Exception e) {
		}
		try {
			pc.setTenLop(rs.getString("tenLop"));
		} catch (Exception e) {
		}
		try {
			pc.setTenMonHoc(rs.getString("tenMonHoc"));
		} catch (Exception e) {
		}
		try {
			pc.setTenHocKy(rs.getString("tenHocKy"));
		} catch (Exception e) {
		}

		return pc;
	}
}