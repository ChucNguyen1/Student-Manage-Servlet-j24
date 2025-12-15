package com.student.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.student.model.HocSinh;

public class HocSinhMapper {
	public static HocSinh mapRow(ResultSet rs) throws SQLException {
		HocSinh hs = new HocSinh();
		hs.setMaHS(rs.getInt("maHS"));
		hs.setHoTen(rs.getString("hoTen"));
		hs.setNgaySinh(rs.getDate("ngaySinh"));
		hs.setGioiTinh(rs.getString("gioiTinh"));
		hs.setNoiSinh(rs.getString("noiSinh"));
		hs.setDanToc(rs.getString("danToc"));
		hs.setTonGiao(rs.getString("tonGiao"));
		hs.setDiaChi(rs.getString("diaChi"));

		hs.setEmail(rs.getString("email"));
		hs.setSdtCaNhan(rs.getString("sdtCaNhan"));

		hs.setHoTenCha(rs.getString("hoTenCha"));
		hs.setNgheNghiepCha(rs.getString("ngheNghiepCha"));
		hs.setSdtCha(rs.getString("sdtCha"));

		hs.setHoTenMe(rs.getString("hoTenMe"));
		hs.setNgheNghiepMe(rs.getString("ngheNghiepMe"));
		hs.setSdtMe(rs.getString("sdtMe"));

		hs.setMaLop(rs.getInt("maLop"));
		hs.setTrangThaiHocTap(rs.getString("trangThaiHocTap"));
		hs.setTrangThai(rs.getBoolean("trangThai"));

		int uid = rs.getInt("userID");
		if (!rs.wasNull())
			hs.setUserID(uid);

		try {
			hs.setTenLop(rs.getString("tenLop"));
		} catch (Exception e) {
		}

		return hs;
	}
}