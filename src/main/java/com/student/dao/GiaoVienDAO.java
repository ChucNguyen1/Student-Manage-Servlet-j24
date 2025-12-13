package com.student.dao;

import java.util.List;

import com.student.model.GiaoVien;

public interface GiaoVienDAO {

	// 1. Lấy danh sách có phân trang và tìm kiếm
	List<GiaoVien> findAndPaginate(String searchKey, int pageNumber, int pageSize);

	// 2. Đếm tổng số giáo viên (để tính tổng số trang)
	int count(String searchKey);

	// 3. Thêm mới
	boolean insert(GiaoVien gv);

	// 4. Cập nhật
	boolean update(GiaoVien gv);

	// 5. Xóa
	boolean delete(int maGV);

	// 6. Tìm theo ID (Dùng cho chức năng Sửa để hiển thị thông tin cũ)
	GiaoVien findById(int maGV);

	List<GiaoVien> findAll();

	List<GiaoVien> findByChuyenMon(int maMonHoc);
}