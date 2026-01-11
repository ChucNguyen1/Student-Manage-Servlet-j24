package com.student.dao;

import java.util.List;

import com.student.dto.LopMonChuaPhanCongDTO;
import com.student.dto.LopPhanCongDTO;
import com.student.model.PhanCong;

public interface PhanCongDAO {

	List<PhanCong> findByLopAndHocKy(int maLop, int maHocKy);

	PhanCong findByUniqueKey(int maLop, int maMonHoc, int maHocKy);

	boolean insert(PhanCong pc);

	boolean update(PhanCong pc);

	boolean delete(int maLop, int maMonHoc, int maHocKy);
	
	List<PhanCong> findByGiaoVienAndHocKy(int maGV, int maHocKy);
	
	/**
	 * Lấy danh sách các lớp-môn học chưa phân công hoặc phân công chưa đầy đủ.
	 * 
	 * @param maNH Mã năm học (có thể null để lấy tất cả)
	 * @param maHocKy Mã học kỳ (có thể null để lấy tất cả)
	 * @param maKhoi Mã khối (có thể null để lấy tất cả)
	 * @param maLop Mã lớp (có thể null để lấy tất cả)
	 * @param maMonHoc Mã môn học (có thể null để lấy tất cả)
	 * @return Danh sách LopMonChuaPhanCongDTO
	 */
	List<LopMonChuaPhanCongDTO> getDanhSachLopMonChuaPhanCong(String maNH, Integer maHocKy, 
			Integer maKhoi, Integer maLop, Integer maMonHoc);
	
	/**
	 * Lấy danh sách các lớp có phân công (không hiển thị từng môn).
	 * Chỉ hiển thị thông tin tổng hợp theo lớp.
	 * 
	 * @param maNH Mã năm học (có thể null để lấy tất cả)
	 * @param maHocKy Mã học kỳ (có thể null để lấy tất cả)
	 * @param maKhoi Mã khối (có thể null để lấy tất cả)
	 * @param maLop Mã lớp (có thể null để lấy tất cả)
	 * @return Danh sách LopPhanCongDTO
	 */
	List<LopPhanCongDTO> getDanhSachLopPhanCong(String maNH, Integer maHocKy, 
			Integer maKhoi, Integer maLop);
}