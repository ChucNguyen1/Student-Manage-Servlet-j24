package com.student.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.student.dao.PhanCongDAO;
import com.student.mapper.PhanCongMapper;
import com.student.model.PhanCong;
import com.student.utils.DBConnection;

public class PhanCongDAOImpl implements PhanCongDAO {

	@Override
	public List<PhanCong> findByLopAndHocKy(int maLop, int maHocKy) {
		List<PhanCong> list = new ArrayList<>();
		String sql = "SELECT pc.*, gv.hoTen AS tenGiaoVien, mh.tenMH AS tenMonHoc " + "FROM PhanCong pc "
				+ "JOIN GiaoVien gv ON pc.maGV = gv.maGV " + "JOIN MonHoc mh ON pc.maMonHoc = mh.maMH "
				+ "WHERE pc.maLop = ? AND pc.maHocKy = ? AND pc.trangThai = 1";

		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, maLop);
			ps.setInt(2, maHocKy);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next())
					list.add(PhanCongMapper.mapRow(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public PhanCong findByUniqueKey(int maLop, int maMonHoc, int maHocKy) {
		String sql = "SELECT * FROM PhanCong WHERE maLop=? AND maMonHoc=? AND maHocKy=?";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, maLop);
			ps.setInt(2, maMonHoc);
			ps.setInt(3, maHocKy);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next())
					return PhanCongMapper.mapRow(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean insert(PhanCong pc) {
		String sql = "INSERT INTO PhanCong (maGV, maLop, maMonHoc, maHocKy, trangThai) VALUES (?, ?, ?, ?, 1)";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, pc.getMaGV());
			ps.setInt(2, pc.getMaLop());
			ps.setInt(3, pc.getMaMonHoc());
			ps.setInt(4, pc.getMaHocKy());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(PhanCong pc) {
		String sql = "UPDATE PhanCong SET maGV=?, trangThai=1 WHERE maLop=? AND maMonHoc=? AND maHocKy=?";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, pc.getMaGV());
			ps.setInt(2, pc.getMaLop());
			ps.setInt(3, pc.getMaMonHoc());
			ps.setInt(4, pc.getMaHocKy());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(int maLop, int maMonHoc, int maHocKy) {
		String sql = "DELETE FROM PhanCong WHERE maLop=? AND maMonHoc=? AND maHocKy=?";
		try (Connection conn = DBConnection.getNewConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, maLop);
			ps.setInt(2, maMonHoc);
			ps.setInt(3, maHocKy);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<PhanCong> findByGiaoVienAndHocKy(int maGV, int maHocKy) {
		List<PhanCong> list = new ArrayList<>();
		String sql = "SELECT pc.*, " + 
				"lh.tenLop, " + 
				"mh.tenMH AS tenMonHoc, " + 
				"hk.tenHK AS tenHocKy, " +
				"gv.hoTen AS tenGiaoVien " +
				"FROM PhanCong pc " + 
				"JOIN LopHoc lh ON pc.maLop = lh.maLop " + 
				"JOIN MonHoc mh ON pc.maMonHoc = mh.maMH " + 
				"JOIN HocKy hk ON pc.maHocKy = hk.maHK " +
				"JOIN GiaoVien gv ON pc.maGV = gv.maGV " + 
				"WHERE pc.maGV = ? AND pc.maHocKy = ? AND pc.trangThai = 1";

		try (Connection conn = DBConnection.getNewConnection(); 
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, maGV);
			ps.setInt(2, maHocKy);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					list.add(PhanCongMapper.mapRow(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}