package com.student.service;

import java.util.List;

import com.student.model.MonHoc;

public interface MonHocService {

	// Lấy danh sách phân trang & tìm kiếm
	List<MonHoc> findAndPaginate(String searchKey, int pageNumber, int pageSize);

	// Đếm tổng số (để tính trang)
	int count(String searchKey);

	// Thêm mới (có validate)
	boolean insert(MonHoc mh);

	// Cập nhật (có validate)
	boolean update(MonHoc mh);

	// Xóa: Trả về String ("SUCCESS", "FOREIGN_KEY_ERROR", "SYSTEM_ERROR")
	// Để Controller biết hiển thị thông báo lỗi phù hợp
	String delete(int maMH);

	// Cập nhật trạng thái (Switch Ẩn/Hiện)
	boolean updateStatus(int maMH, boolean status);

	// Lấy tất cả (dùng cho dropdown sau này)
	List<MonHoc> findAll();
}