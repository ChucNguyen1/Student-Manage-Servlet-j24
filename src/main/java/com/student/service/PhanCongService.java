package com.student.service;

import java.util.List;

import com.student.model.PhanCong;

public interface PhanCongService {

	List<PhanCong> getPhanCongView(int maLop, int maHocKy);

	boolean savePhanCong(int maLop, int maMonHoc, int maHocKy, int maGV);
	
	boolean hasAnyPhanCong(int maLop, int maHocKy);
	
	int countPhanCong(int maLop, int maHocKy);
}