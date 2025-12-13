package com.student.service;

import java.util.List;

import com.student.model.LopHoc;

public interface LopHocService {
	List<LopHoc> findAndPaginate(String searchKey, int pageNumber, int pageSize);

	List<LopHoc> findAll(); // Dùng cho các module khác (VD: Học sinh, Phân công)

	int count(String searchKey);

	boolean insert(LopHoc lh);

	boolean update(LopHoc lh);

	String delete(int maLop); // Trả về String mã lỗi

	boolean updateStatus(int maLop, boolean status);

	// Lấy danh sách lớp theo Năm học và Khối (Dùng cho dropdown nhập điểm)
	List<LopHoc> findByNamHocAndKhoi(String maNH, int maKhoi);
}