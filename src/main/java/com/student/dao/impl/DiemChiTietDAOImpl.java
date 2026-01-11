package com.student.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.student.dao.DiemChiTietDAO;
import com.student.dto.LopMonChuaNhapDiemDTO;
import com.student.mapper.DiemChiTietMapper;
import com.student.model.DiemChiTiet;
import com.student.utils.DBConnection;

public class DiemChiTietDAOImpl implements DiemChiTietDAO {

	@Override
	public List<DiemChiTiet> getBangDiemLop(int maLop, int maMonHoc, int maHocKy) {
		List<DiemChiTiet> list = new ArrayList<>();

		// SQL:
		// 1. Lấy tất cả Học Sinh trong lớp (hs)
		// 2. LEFT JOIN với DiemChiTiet (d) theo maHS, maMonHoc, maHocKy

		String sql = "SELECT hs.maHS, hs.hoTen AS hoTenHS, d.* " + "FROM HocSinh hs "
				+ "LEFT JOIN DiemChiTiet d ON hs.maHS = d.maHS " + "    AND d.maMonHoc = ? AND d.maHocKy = ? "
				+ "WHERE hs.maLop = ? AND hs.trangThai = 1 " + "ORDER BY hs.hoTen ASC"; 

		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, maMonHoc);
			ps.setInt(2, maHocKy);
			ps.setInt(3, maLop);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					DiemChiTiet dt = DiemChiTietMapper.mapRow(rs);

					dt.setMaHS(rs.getInt("maHS")); 
					dt.setMaMonHoc(maMonHoc);
					dt.setMaHocKy(maHocKy);

					list.add(dt);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean checkExist(int maHS, int maMonHoc, int maHocKy) {
		String sql = "SELECT COUNT(*) FROM DiemChiTiet WHERE maHS=? AND maMonHoc=? AND maHocKy=?";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, maHS);
			ps.setInt(2, maMonHoc);
			ps.setInt(3, maHocKy);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next())
					return rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean insert(DiemChiTiet d) {
		String sql = "INSERT INTO DiemChiTiet (maHS, maMonHoc, maHocKy, " + "diemMieng_1, diemMieng_2, diemMieng_3, "
				+ "diem15p_1, diem15p_2, diem15p_3, " + "diem1Tiet_1, diem1Tiet_2, diemThi, diemTBM) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, d.getMaHS());
			ps.setInt(2, d.getMaMonHoc());
			ps.setInt(3, d.getMaHocKy());
			setDoubleOrNull(ps, 4, d.getDiemMieng1());
			setDoubleOrNull(ps, 5, d.getDiemMieng2());
			setDoubleOrNull(ps, 6, d.getDiemMieng3());

			setDoubleOrNull(ps, 7, d.getDiem15p1());
			setDoubleOrNull(ps, 8, d.getDiem15p2());
			setDoubleOrNull(ps, 9, d.getDiem15p3());

			setDoubleOrNull(ps, 10, d.getDiem1Tiet1());
			setDoubleOrNull(ps, 11, d.getDiem1Tiet2());

			setDoubleOrNull(ps, 12, d.getDiemThi());
			setDoubleOrNull(ps, 13, d.getDiemTBM());

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(DiemChiTiet d) {
		String sql = "UPDATE DiemChiTiet SET " + "diemMieng_1=?, diemMieng_2=?, diemMieng_3=?, "
				+ "diem15p_1=?, diem15p_2=?, diem15p_3=?, " + "diem1Tiet_1=?, diem1Tiet_2=?, diemThi=?, diemTBM=? "
				+ "WHERE maHS=? AND maMonHoc=? AND maHocKy=?";

		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			setDoubleOrNull(ps, 1, d.getDiemMieng1());
			setDoubleOrNull(ps, 2, d.getDiemMieng2());
			setDoubleOrNull(ps, 3, d.getDiemMieng3());

			setDoubleOrNull(ps, 4, d.getDiem15p1());
			setDoubleOrNull(ps, 5, d.getDiem15p2());
			setDoubleOrNull(ps, 6, d.getDiem15p3());

			setDoubleOrNull(ps, 7, d.getDiem1Tiet1());
			setDoubleOrNull(ps, 8, d.getDiem1Tiet2());

			setDoubleOrNull(ps, 9, d.getDiemThi());
			setDoubleOrNull(ps, 10, d.getDiemTBM());

			// WHERE clause
			ps.setInt(11, d.getMaHS());
			ps.setInt(12, d.getMaMonHoc());
			ps.setInt(13, d.getMaHocKy());

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// --- xử lý setDouble khi giá trị là null ---
	private void setDoubleOrNull(PreparedStatement ps, int index, Double value) throws SQLException {
		if (value == null) {
			ps.setNull(index, java.sql.Types.FLOAT);
		} else {
			ps.setDouble(index, value);
		}
	}

	@Override
	public List<DiemChiTiet> getBangDiemCaNhan(int maHS, int maHocKy) {
		List<DiemChiTiet> list = new ArrayList<>();

		// SQL:
		// 1. Lấy tất cả Môn Học (mh)
		// 2. LEFT JOIN với DiemChiTiet (d) theo maMonHoc, maHS, maHocKy

		String sql = "SELECT mh.maMH, mh.tenMH, d.* " + "FROM MonHoc mh "
				+ "LEFT JOIN DiemChiTiet d ON mh.maMH = d.maMonHoc " + "    AND d.maHS = ? AND d.maHocKy = ? "
				+ "WHERE mh.trangThai = 1 " 
				+ "ORDER BY mh.tenMH ASC"; 

		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, maHS);
			ps.setInt(2, maHocKy);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					DiemChiTiet dt = DiemChiTietMapper.mapRow(rs);
					dt.setMaHS(maHS);
					dt.setMaMonHoc(rs.getInt("maMH"));
					dt.setMaHocKy(maHocKy);
					dt.setTenMonHoc(rs.getString("tenMH")); 

					list.add(dt);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public List<LopMonChuaNhapDiemDTO> getDanhSachLopMonChuaNhapDiem(String maNH, Integer maHocKy, 
			Integer maKhoi, Integer maLop, Integer maMonHoc) {
		List<LopMonChuaNhapDiemDTO> list = new ArrayList<>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("  l.maLop, l.tenLop, ");
		sql.append("  mh.maMH, mh.tenMH, ");
		sql.append("  hk.maHK, hk.tenHK, ");
		sql.append("  nh.maNH, nh.tenNH, ");
		sql.append("  k.maKhoi, k.tenKhoi, ");
		sql.append("  COUNT(DISTINCT hs.maHS) AS soHocSinh, ");
		sql.append("  COUNT(DISTINCT CASE WHEN d.maHS IS NOT NULL THEN hs.maHS END) AS soHocSinhDaNhap ");
		sql.append("FROM LopHoc l ");
		sql.append("CROSS JOIN MonHoc mh ");
		sql.append("CROSS JOIN HocKy hk ");
		sql.append("INNER JOIN NamHoc nh ON hk.maNH = nh.maNH ");
		sql.append("INNER JOIN Khoi k ON l.maKhoi = k.maKhoi ");
		sql.append("LEFT JOIN HocSinh hs ON l.maLop = hs.maLop AND hs.trangThai = 1 ");
		sql.append("LEFT JOIN DiemChiTiet d ON hs.maHS = d.maHS AND mh.maMH = d.maMonHoc AND hk.maHK = d.maHocKy ");
		sql.append("WHERE l.trangThai = 1 AND mh.trangThai = 1 ");
		
		List<Object> params = new ArrayList<>();
		
		if (maNH != null && !maNH.isEmpty()) {
			sql.append("AND nh.maNH = ? ");
			params.add(maNH);
		}
		
		if (maHocKy != null) {
			sql.append("AND hk.maHK = ? ");
			params.add(maHocKy);
		}
		
		if (maKhoi != null) {
			sql.append("AND k.maKhoi = ? ");
			params.add(maKhoi);
		}
		
		if (maLop != null) {
			sql.append("AND l.maLop = ? ");
			params.add(maLop);
		}
		
		if (maMonHoc != null) {
			sql.append("AND mh.maMH = ? ");
			params.add(maMonHoc);
		}
		
		sql.append("GROUP BY l.maLop, l.tenLop, mh.maMH, mh.tenMH, hk.maHK, hk.tenHK, ");
		sql.append("         nh.maNH, nh.tenNH, k.maKhoi, k.tenKhoi ");
		sql.append("HAVING COUNT(DISTINCT hs.maHS) > 0 ");
		sql.append("   AND COUNT(DISTINCT hs.maHS) > COUNT(DISTINCT CASE WHEN d.maHS IS NOT NULL THEN hs.maHS END) ");
		sql.append("ORDER BY nh.maNH DESC, hk.maHK, k.tenKhoi, l.tenLop, mh.tenMH");
		
		try (Connection conn = DBConnection.getNewConnection(); 
			 PreparedStatement ps = conn.prepareStatement(sql.toString())) {
			
			// Set parameters
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 1, params.get(i));
			}
			
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					LopMonChuaNhapDiemDTO dto = new LopMonChuaNhapDiemDTO();
					dto.setMaLop(rs.getInt("maLop"));
					dto.setTenLop(rs.getString("tenLop"));
					dto.setMaMonHoc(rs.getInt("maMH"));
					dto.setTenMonHoc(rs.getString("tenMH"));
					dto.setMaHocKy(rs.getInt("maHK"));
					dto.setTenHocKy(rs.getString("tenHK"));
					dto.setMaNH(rs.getString("maNH"));
					dto.setTenNH(rs.getString("tenNH"));
					dto.setMaKhoi(rs.getInt("maKhoi"));
					dto.setTenKhoi(rs.getString("tenKhoi"));
					dto.setSoHocSinh(rs.getInt("soHocSinh"));
					dto.setSoHocSinhDaNhap(rs.getInt("soHocSinhDaNhap"));
					dto.setDaHoanThanh(dto.getSoHocSinh() == dto.getSoHocSinhDaNhap());
					
					list.add(dto);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
}