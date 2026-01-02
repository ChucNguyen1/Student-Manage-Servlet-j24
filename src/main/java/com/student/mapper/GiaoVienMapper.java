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

		try {
			gv.setMaTo(rs.getInt("maTo"));
		} catch (SQLException e) {
		}

		
		try {
			gv.setTenTo(rs.getString("tenTo"));
		} catch (SQLException e) {
			
		}

		try {
			gv.setMaMonHocChuyenMon(rs.getInt("maMonHocChuyenMon"));
			if (rs.wasNull()) {
				gv.setMaMonHocChuyenMon(null);
			}
		} catch (SQLException e) {
			
		}

		try {
			gv.setTenMonHocChuyenMon(rs.getString("tenMonHocChuyenMon"));
		} catch (SQLException e) {
			
		}

		int uid = rs.getInt("userID");
		if (!rs.wasNull()) {
			gv.setUserID(uid);
		}

		return gv;
	}
}