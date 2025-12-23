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
		
		// Chỉ set maTo nếu cột tồn tại trong ResultSet (từ LEFT JOIN)
		try {
			mh.setMaTo(rs.getInt("maTo"));
			// Thử lấy tenTo nếu có LEFT JOIN
			try {
				mh.setTenTo(rs.getString("tenTo"));
			} catch (SQLException ex) {
				// tenTo không có trong query
			}
		} catch (SQLException e) {
			// Cột maTo không tồn tại, giữ giá trị mặc định 0
		}
		
		return mh;
	}
}