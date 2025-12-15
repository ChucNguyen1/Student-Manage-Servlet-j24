package com.student.service;

import java.util.List;

import com.student.model.DiemChiTiet;

public interface DiemChiTietService {

	List<DiemChiTiet> getBangDiemLop(int maLop, int maMonHoc, int maHocKy);

	/**
	 * @param diem Đối tượng điểm
	 * @return true nếu thành công
	 */
	boolean saveDiem(DiemChiTiet diem);

	void calculateAndSetTBM(DiemChiTiet diem);
}