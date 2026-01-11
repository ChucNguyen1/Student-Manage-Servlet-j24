package com.student.service;

import java.util.List;

import com.student.dto.LopMonChuaNhapDiemDTO;
import com.student.model.DiemChiTiet;

public interface DiemChiTietService {

	List<DiemChiTiet> getBangDiemLop(int maLop, int maMonHoc, int maHocKy);

	/**
	 * @param diem Đối tượng điểm
	 * @return true nếu thành công
	 */
	boolean saveDiem(DiemChiTiet diem);

	void calculateAndSetTBM(DiemChiTiet diem);
	
	/**
	 * Lấy danh sách các lớp-môn học chưa nhập điểm hoặc nhập chưa đầy đủ.
	 */
	List<LopMonChuaNhapDiemDTO> getDanhSachLopMonChuaNhapDiem(String maNH, Integer maHocKy, 
			Integer maKhoi, Integer maLop, Integer maMonHoc);
}