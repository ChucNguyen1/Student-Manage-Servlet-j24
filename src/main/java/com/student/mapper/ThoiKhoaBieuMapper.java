package com.student.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.student.model.ThoiKhoaBieu;

/**
 * Mapper: Chuyển đổi ResultSet sang Object ThoiKhoaBieu
 */
public class ThoiKhoaBieuMapper {

	public static ThoiKhoaBieu mapRow(ResultSet rs) throws SQLException {
		ThoiKhoaBieu tkb = new ThoiKhoaBieu();
		
		// Database fields
		tkb.setMaTKB(rs.getInt("maTKB"));
		tkb.setMaLop(rs.getInt("maLop"));
		tkb.setMaHocKy(rs.getInt("maHocKy"));
		tkb.setMaMonHoc(rs.getInt("maMonHoc"));
		tkb.setMaGV(rs.getInt("maGV"));
		tkb.setThu(rs.getInt("thu"));
		tkb.setTiet(rs.getInt("tiet"));
		tkb.setPhongHoc(rs.getString("phongHoc"));
		tkb.setCreatedAt(rs.getTimestamp("createdAt"));
		tkb.setUpdatedAt(rs.getTimestamp("updatedAt"));
		
		try {
			tkb.setTenLop(rs.getString("tenLop"));
		} catch (SQLException e) {
		}
		
		try {
			tkb.setTenHocKy(rs.getString("tenHK"));
		} catch (SQLException e) {

		}
		
		try {
			tkb.setTenMonHoc(rs.getString("tenMH"));
		} catch (SQLException e) {

		}
		
		try {
			tkb.setTenGiaoVien(rs.getString("tenGV"));
		} catch (SQLException e) {

		}
		
		tkb.setTenThu(ThoiKhoaBieu.getTenThuFromNumber(tkb.getThu()));
		
		return tkb;
	}
}
