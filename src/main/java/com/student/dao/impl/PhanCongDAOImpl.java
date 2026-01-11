package com.student.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.student.dao.PhanCongDAO;
import com.student.dto.LopMonChuaPhanCongDTO;
import com.student.dto.LopPhanCongDTO;
import com.student.mapper.PhanCongMapper;
import com.student.model.PhanCong;
import com.student.utils.DBConnection;

public class PhanCongDAOImpl implements PhanCongDAO {

	@Override
	public List<PhanCong> findByLopAndHocKy(int maLop, int maHocKy) {
		List<PhanCong> list = new ArrayList<>();
		String sql = "SELECT pc.*, gv.hoTen AS tenGiaoVien, mh.tenMH AS tenMonHoc " + "FROM PhanCong pc "
				+ "JOIN GiaoVien gv ON pc.maGV = gv.maGV " + "JOIN MonHoc mh ON pc.maMonHoc = mh.maMH "
				+ "WHERE pc.maLop = ? AND pc.maHocKy = ?";

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
		String sql = "INSERT INTO PhanCong (maGV, maLop, maMonHoc, maHocKy) VALUES (?, ?, ?, ?)";
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
		String sql = "UPDATE PhanCong SET maGV=? WHERE maLop=? AND maMonHoc=? AND maHocKy=?";
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
				"WHERE pc.maGV = ? AND pc.maHocKy = ?";

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
	
	@Override
	public List<LopMonChuaPhanCongDTO> getDanhSachLopMonChuaPhanCong(String maNH, Integer maHocKy, 
			Integer maKhoi, Integer maLop, Integer maMonHoc) {
		List<LopMonChuaPhanCongDTO> list = new ArrayList<>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("  l.maLop, l.tenLop, ");
		sql.append("  mh.maMH, mh.tenMH, ");
		sql.append("  hk.maHK, hk.tenHK, ");
		sql.append("  nh.maNH, nh.tenNH, ");
		sql.append("  k.maKhoi, k.tenKhoi, ");
		sql.append("  CASE WHEN pc.maGV IS NOT NULL THEN 1 ELSE 0 END AS daPhanCong, ");
		sql.append("  ISNULL(pc.maGV, 0) AS maGV, ");
		sql.append("  ISNULL(gv.hoTen, '') AS tenGiaoVien ");
		sql.append("FROM LopHoc l ");
		sql.append("CROSS JOIN MonHoc mh ");
		sql.append("CROSS JOIN HocKy hk ");
		sql.append("INNER JOIN NamHoc nh ON hk.maNH = nh.maNH ");
		sql.append("INNER JOIN Khoi k ON l.maKhoi = k.maKhoi ");
		sql.append("LEFT JOIN PhanCong pc ON l.maLop = pc.maLop AND mh.maMH = pc.maMonHoc AND hk.maHK = pc.maHocKy ");
		sql.append("LEFT JOIN GiaoVien gv ON pc.maGV = gv.maGV ");
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
		
		sql.append("ORDER BY nh.maNH DESC, hk.maHK, k.tenKhoi, l.tenLop, mh.tenMH");
		
		try (Connection conn = DBConnection.getNewConnection(); 
			 PreparedStatement ps = conn.prepareStatement(sql.toString())) {
			
			// Set parameters
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 1, params.get(i));
			}
			
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					LopMonChuaPhanCongDTO dto = new LopMonChuaPhanCongDTO();
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
					dto.setDaPhanCong(rs.getInt("daPhanCong") == 1);
					dto.setMaGV(rs.getInt("maGV"));
					dto.setTenGiaoVien(rs.getString("tenGiaoVien"));
					
					list.add(dto);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	@Override
	public List<LopPhanCongDTO> getDanhSachLopPhanCong(String maNH, Integer maHocKy, 
			Integer maKhoi, Integer maLop) {
		List<LopPhanCongDTO> list = new ArrayList<>();

StringBuilder sql = new StringBuilder();
sql.append("SELECT ");
sql.append("  l.maLop, l.tenLop, ");
sql.append("  hk.maHK, hk.tenHK, ");
sql.append("  nh.maNH, nh.tenNH, ");
sql.append("  k.maKhoi, k.tenKhoi, ");
sql.append("  COUNT(DISTINCT mh.maMH) AS tongSoMon, ");
sql.append("  COUNT(DISTINCT pc.maMonHoc) AS soMonDaPhanCong ");
sql.append("FROM LopHoc l ");
sql.append("CROSS JOIN MonHoc mh ");
sql.append("CROSS JOIN HocKy hk ");
sql.append("INNER JOIN NamHoc nh ON hk.maNH = nh.maNH ");
sql.append("INNER JOIN Khoi k ON l.maKhoi = k.maKhoi ");
sql.append("LEFT JOIN PhanCong pc ON l.maLop = pc.maLop AND mh.maMH = pc.maMonHoc AND hk.maHK = pc.maHocKy ");
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

sql.append("GROUP BY l.maLop, l.tenLop, hk.maHK, hk.tenHK, nh.maNH, nh.tenNH, k.maKhoi, k.tenKhoi ");
sql.append("ORDER BY nh.maNH DESC, hk.maHK, k.tenKhoi, l.tenLop");

try (Connection conn = DBConnection.getNewConnection(); 
 PreparedStatement ps = conn.prepareStatement(sql.toString())) {

for (int i = 0; i < params.size(); i++) {
ps.setObject(i + 1, params.get(i));
}

try (ResultSet rs = ps.executeQuery()) {
while (rs.next()) {
LopPhanCongDTO dto = new LopPhanCongDTO();
dto.setMaLop(rs.getInt("maLop"));
dto.setTenLop(rs.getString("tenLop"));
dto.setMaHocKy(rs.getInt("maHK"));
dto.setTenHocKy(rs.getString("tenHK"));
dto.setMaNH(rs.getString("maNH"));
dto.setTenNH(rs.getString("tenNH"));
dto.setMaKhoi(rs.getInt("maKhoi"));
dto.setTenKhoi(rs.getString("tenKhoi"));

int tongSoMon = rs.getInt("tongSoMon");
int soMonDaPhanCong = rs.getInt("soMonDaPhanCong");

dto.setTongSoMon(tongSoMon);
dto.setSoMonDaPhanCong(soMonDaPhanCong);
dto.setSoMonChuaPhanCong(tongSoMon - soMonDaPhanCong);

list.add(dto);
}
}
} catch (SQLException e) {
e.printStackTrace();
}

return list;
}
}