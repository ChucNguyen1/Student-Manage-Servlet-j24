package com.student.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.student.dao.HocSinhDAO;
import com.student.mapper.HocSinhMapper;
import com.student.model.HocSinh;
import com.student.utils.DBConnection;

public class HocSinhDAOImpl implements HocSinhDAO {

	@Override
	public List<HocSinh> findAndPaginate(String searchKey, int pageNumber, int pageSize) {
		List<HocSinh> list = new ArrayList<>();
		String sql = "SELECT hs.*, lh.tenLop " + "FROM HocSinh hs " + "LEFT JOIN LopHoc lh ON hs.maLop = lh.maLop "
				+ "WHERE hs.trangThai = 1 ";

		if (searchKey != null && !searchKey.isEmpty()) {
			sql += " AND (hs.hoTen LIKE ? OR hs.email LIKE ?) ";
		}
		sql += " ORDER BY hs.maHS DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			int idx = 1;
			if (searchKey != null && !searchKey.isEmpty()) {
				ps.setString(idx++, "%" + searchKey + "%");
				ps.setString(idx++, "%" + searchKey + "%");
			}
			ps.setInt(idx++, (pageNumber - 1) * pageSize);
			ps.setInt(idx++, pageSize);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next())
					list.add(HocSinhMapper.mapRow(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int count(String searchKey) {
		String sql = "SELECT COUNT(*) FROM HocSinh WHERE trangThai = 1";
		if (searchKey != null && !searchKey.isEmpty())
			sql += " AND (hoTen LIKE ? OR email LIKE ?)";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			if (searchKey != null && !searchKey.isEmpty()) {
				ps.setString(1, "%" + searchKey + "%");
				ps.setString(2, "%" + searchKey + "%");
			}
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next())
					return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public boolean insert(HocSinh hs) {
		String sql = "INSERT INTO HocSinh (hoTen, ngaySinh, gioiTinh, noiSinh, danToc, tonGiao, diaChi, "
				+ "email, sdtCaNhan, hoTenCha, ngheNghiepCha, sdtCha, hoTenMe, ngheNghiepMe, sdtMe, "
				+ "maLop, trangThaiHocTap, trangThai) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1)";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			int i = 1;
			ps.setString(i++, hs.getHoTen());
			ps.setDate(i++, hs.getNgaySinh());
			ps.setString(i++, hs.getGioiTinh());
			ps.setString(i++, hs.getNoiSinh());
			ps.setString(i++, hs.getDanToc());
			ps.setString(i++, hs.getTonGiao());
			ps.setString(i++, hs.getDiaChi());
			ps.setString(i++, hs.getEmail());
			ps.setString(i++, hs.getSdtCaNhan());
			ps.setString(i++, hs.getHoTenCha());
			ps.setString(i++, hs.getNgheNghiepCha());
			ps.setString(i++, hs.getSdtCha());
			ps.setString(i++, hs.getHoTenMe());
			ps.setString(i++, hs.getNgheNghiepMe());
			ps.setString(i++, hs.getSdtMe());

			if (hs.getMaLop() > 0)
				ps.setInt(i++, hs.getMaLop());
			else
				ps.setNull(i++, Types.INTEGER);

			ps.setString(i++, hs.getTrangThaiHocTap()); 

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(HocSinh hs) {
		String sql = "UPDATE HocSinh SET hoTen=?, ngaySinh=?, gioiTinh=?, noiSinh=?, danToc=?, tonGiao=?, diaChi=?, "
				+ "email=?, sdtCaNhan=?, hoTenCha=?, ngheNghiepCha=?, sdtCha=?, hoTenMe=?, ngheNghiepMe=?, sdtMe=?, "
				+ "maLop=?, trangThaiHocTap=? WHERE maHS=?";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			int i = 1;
			ps.setString(i++, hs.getHoTen());
			ps.setDate(i++, hs.getNgaySinh());
			ps.setString(i++, hs.getGioiTinh());
			ps.setString(i++, hs.getNoiSinh());
			ps.setString(i++, hs.getDanToc());
			ps.setString(i++, hs.getTonGiao());
			ps.setString(i++, hs.getDiaChi());
			ps.setString(i++, hs.getEmail());
			ps.setString(i++, hs.getSdtCaNhan());
			ps.setString(i++, hs.getHoTenCha());
			ps.setString(i++, hs.getNgheNghiepCha());
			ps.setString(i++, hs.getSdtCha());
			ps.setString(i++, hs.getHoTenMe());
			ps.setString(i++, hs.getNgheNghiepMe());
			ps.setString(i++, hs.getSdtMe());

			if (hs.getMaLop() > 0)
				ps.setInt(i++, hs.getMaLop());
			else
				ps.setNull(i++, Types.INTEGER);

			ps.setString(i++, hs.getTrangThaiHocTap());
			ps.setInt(i++, hs.getMaHS());

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(int maHS) {
		// Xóa mềm
		String sql = "UPDATE HocSinh SET trangThai = 0 WHERE maHS=?";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, maHS);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public HocSinh findById(int maHS) {
		String sql = "SELECT hs.*, lh.tenLop FROM HocSinh hs LEFT JOIN LopHoc lh ON hs.maLop=lh.maLop WHERE maHS=?";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, maHS);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next())
					return HocSinhMapper.mapRow(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<HocSinh> findByLop(int maLop) {
		List<HocSinh> list = new ArrayList<>();
		String sql = "SELECT * FROM HocSinh WHERE maLop = ? AND trangThai = 1 ORDER BY hoTen";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, maLop);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next())
					list.add(HocSinhMapper.mapRow(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean updateProfile(int maHS, String email, String sdtCaNhan, String diaChi) {
		String sql = "UPDATE HocSinh SET email=?, sdtCaNhan=?, diaChi=? WHERE maHS=?";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, email);
			ps.setString(2, sdtCaNhan);
			ps.setString(3, diaChi);
			ps.setInt(4, maHS);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}