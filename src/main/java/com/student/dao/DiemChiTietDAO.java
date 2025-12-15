package com.student.dao;

import java.util.List;

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
}