package com.student.service;

import java.util.List;

import com.student.model.GiaoVien;

public interface GiaoVienService {

	// Lấy danh sách phân trang & tìm kiếm
	List<GiaoVien> findAndPaginate(String searchKey, int pageNumber, int pageSize);

	// Đếm tổng số (để tính trang)
	int count(String searchKey);

	// Thêm mới
	boolean insert(GiaoVien gv);

	// Cập nhật
	boolean update(GiaoVien gv);

	// Xóa
	boolean delete(int maGV);

	// Tìm theo ID (để hiển thị lên form sửa)
	GiaoVien findById(int maGV);

	List<GiaoVien> findAll();

	List<GiaoVien> findByChuyenMon(int maMonHoc);
}