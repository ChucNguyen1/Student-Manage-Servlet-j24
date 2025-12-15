package com.student.dao;

import java.util.List;

import com.student.model.PhanCong;

public interface PhanCongDAO {

	List<PhanCong> findByLopAndHocKy(int maLop, int maHocKy);

	PhanCong findByUniqueKey(int maLop, int maMonHoc, int maHocKy);

	boolean insert(PhanCong pc);

	boolean update(PhanCong pc);

	boolean delete(int maLop, int maMonHoc, int maHocKy);
	
	List<PhanCong> findByGiaoVienAndHocKy(int maGV, int maHocKy);
}