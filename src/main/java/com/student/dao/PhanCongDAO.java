package com.student.dao;

import java.util.List;

import com.student.model.PhanCong;

public interface PhanCongDAO {

	// Lấy danh sách phân công của 1 lớp trong 1 học kỳ
	List<PhanCong> findByLopAndHocKy(int maLop, int maHocKy);

	// Tìm một phân công cụ thể (để check tồn tại)
	PhanCong findByUniqueKey(int maLop, int maMonHoc, int maHocKy);

	boolean insert(PhanCong pc);

	boolean update(PhanCong pc);

	// Xóa phân công (Hủy phân công giáo viên đó)
	boolean delete(int maLop, int maMonHoc, int maHocKy);
}