package com.student.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.student.model.LopHoc;

public class LopHocMapper {
	public static LopHoc mapRow(ResultSet rs) throws SQLException {
		LopHoc lh = new LopHoc();
		lh.setMaLop(rs.getInt("maLop"));
		lh.setTenLop(rs.getString("tenLop"));
		lh.setMaKhoi(rs.getInt("maKhoi"));
		lh.setMaNH(rs.getString("maNH"));
		lh.setMaGVCN(rs.getInt("maGVCN"));
		lh.setTrangThai(rs.getBoolean("trangThai"));

		// Map các trường phụ (Nếu câu SQL có JOIN)
		try {
			lh.setTenKhoi(rs.getString("tenKhoi"));
		} catch (Exception e) {
		}
		try {
			lh.setTenNamHoc(rs.getString("tenNH"));
		} catch (Exception e) {
		}
		try {
			lh.setTenGVCN(rs.getString("tenGVCN"));
		} catch (Exception e) {
		}

		return lh;
	}
}