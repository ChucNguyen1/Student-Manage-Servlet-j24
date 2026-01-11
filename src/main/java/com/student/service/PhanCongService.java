package com.student.service;

import java.util.List;

import com.student.dto.LopMonChuaPhanCongDTO;
import com.student.dto.LopPhanCongDTO;
import com.student.model.PhanCong;

public interface PhanCongService {

	List<PhanCong> getPhanCongView(int maLop, int maHocKy);

	boolean savePhanCong(int maLop, int maMonHoc, int maHocKy, int maGV);
	
	boolean hasAnyPhanCong(int maLop, int maHocKy);
	
	int countPhanCong(int maLop, int maHocKy);
	
	/**
	 * Lấy danh sách các lớp-môn học chưa phân công.
	 */
	List<LopMonChuaPhanCongDTO> getDanhSachLopMonChuaPhanCong(String maNH, Integer maHocKy, 
			Integer maKhoi, Integer maLop, Integer maMonHoc);
	
	/**
	 * L?y danh s�ch c�c l?p d� ph�n c�ng (nh�m theo l?p, kh�ng hi?n th? t?ng m�n).
	 */
	List<LopPhanCongDTO> getDanhSachLopPhanCong(String maNH, Integer maHocKy, 
			Integer maKhoi, Integer maLop);
}