package com.student.service;

import java.util.List;

import com.student.model.DiemChiTiet;

public interface DiemChiTietService {

	/**
	 * Lấy danh sách bảng điểm đầy đủ của một lớp.
	 */
	List<DiemChiTiet> getBangDiemLop(int maLop, int maMonHoc, int maHocKy);

	/**
	 * Lưu điểm cho 1 học sinh. Tự động tính TBM và tự động quyết định Insert hay
	 * Update.
	 * 
	 * @param diem Đối tượng điểm
	 * @return true nếu thành công
	 */
	boolean saveDiem(DiemChiTiet diem);

	/**
	 * Hàm hỗ trợ tính điểm trung bình môn dựa trên các điểm thành phần.
	 */
	void calculateAndSetTBM(DiemChiTiet diem);
}