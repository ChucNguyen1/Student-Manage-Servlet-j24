package com.student.dao;

import java.util.List;

import com.student.dto.LopMonChuaNhapDiemDTO;
import com.student.model.DiemChiTiet;

public interface DiemChiTietDAO {

	/**
	 * Lấy bảng điểm của một lớp theo môn và học kỳ. 
	 * 
	 * @param maLop    
	 * @param maMonHoc 
	 * @param maHocKy  
	 * @return Danh sách DiemChiTiet (chứa thông tin học sinh và điểm)
	 */
	List<DiemChiTiet> getBangDiemLop(int maLop, int maMonHoc, int maHocKy);

	/**
	 * Kiểm tra xem điểm của học sinh này, môn này, kỳ này đã tồn tại chưa.
	 * 
	 * @return true nếu có dữ liệu, false nếu chưa
	 */
	boolean checkExist(int maHS, int maMonHoc, int maHocKy);

	boolean insert(DiemChiTiet diem);

	boolean update(DiemChiTiet diem);

	/**
	 * Lấy bảng điểm cá nhân của một học sinh theo học kỳ.
	 * 
	 * @param maHS    
	 * @param maHocKy 
	 * @return Danh sách DiemChiTiet (chứa thông tin môn học và điểm)
	 */
	List<DiemChiTiet> getBangDiemCaNhan(int maHS, int maHocKy);
	
	/**
	 * Lấy danh sách các lớp-môn học chưa nhập điểm hoặc nhập chưa đầy đủ.
	 * 
	 * @param maNH Mã năm học (có thể null để lấy tất cả)
	 * @param maHocKy Mã học kỳ (có thể null để lấy tất cả)
	 * @param maKhoi Mã khối (có thể null để lấy tất cả)
	 * @param maLop Mã lớp (có thể null để lấy tất cả)
	 * @param maMonHoc Mã môn học (có thể null để lấy tất cả)
	 * @return Danh sách LopMonChuaNhapDiemDTO
	 */
	List<LopMonChuaNhapDiemDTO> getDanhSachLopMonChuaNhapDiem(String maNH, Integer maHocKy, 
			Integer maKhoi, Integer maLop, Integer maMonHoc);
}