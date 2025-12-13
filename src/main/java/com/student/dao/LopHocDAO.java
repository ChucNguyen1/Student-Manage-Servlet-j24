package com.student.dao;

import java.util.List;

import com.student.model.LopHoc;

public interface LopHocDAO {
	List<LopHoc> findAndPaginate(String searchKey, int pageNumber, int pageSize);

	List<LopHoc> findAll(); // Dùng cho Dropdown bên quản lý điểm

	int count(String searchKey);

	boolean insert(LopHoc lh);

	boolean update(LopHoc lh);

	boolean delete(int maLop); // Xóa cứng (có kiểm tra)

	boolean updateStatus(int maLop, boolean status); // Xóa mềm

	boolean isUsed(int maLop); // Kiểm tra ràng buộc

	// Kiểm tra tên lớp trùng trong cùng năm học
	boolean checkDuplicate(String tenLop, String maNH);

	// Lấy danh sách lớp theo Năm học và Khối (Dùng cho dropdown nhập điểm)
	List<LopHoc> findByNamHocAndKhoi(String maNH, int maKhoi);
}