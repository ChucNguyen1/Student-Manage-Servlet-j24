package com.student.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.student.model.DiemChiTiet;

public class DiemChiTietMapper {

	public static DiemChiTiet mapRow(ResultSet rs) throws SQLException {
		DiemChiTiet diem = new DiemChiTiet();

		diem.setMaDiem(rs.getInt("maDiem"));
		diem.setMaHS(rs.getInt("maHS"));
		diem.setMaMonHoc(rs.getInt("maMonHoc"));
		diem.setMaHocKy(rs.getInt("maHocKy"));

		// Lấy điểm (getObject để xử lý null an toàn)
		diem.setDiemMieng1((Double) rs.getObject("diemMieng_1"));
		diem.setDiemMieng2((Double) rs.getObject("diemMieng_2"));
		diem.setDiemMieng3((Double) rs.getObject("diemMieng_3"));

		diem.setDiem15p1((Double) rs.getObject("diem15p_1"));
		diem.setDiem15p2((Double) rs.getObject("diem15p_2"));
		diem.setDiem15p3((Double) rs.getObject("diem15p_3"));

		diem.setDiem1Tiet1((Double) rs.getObject("diem1Tiet_1"));
		diem.setDiem1Tiet2((Double) rs.getObject("diem1Tiet_2"));

		diem.setDiemThi((Double) rs.getObject("diemThi"));
		diem.setDiemTBM((Double) rs.getObject("diemTBM"));

		// Map các trường phụ (nếu câu SQL có JOIN)
		try {
			diem.setTenHocSinh(rs.getString("hoTenHS")); // Alias trong SQL
		} catch (SQLException e) {
		}

		try {
			diem.setTenMonHoc(rs.getString("tenMH"));
		} catch (SQLException e) {
		}

		return diem;
	}
}