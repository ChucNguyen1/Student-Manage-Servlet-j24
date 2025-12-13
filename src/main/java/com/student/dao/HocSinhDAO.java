package com.student.dao;

import java.util.List;

import com.student.model.HocSinh;

public interface HocSinhDAO {
	List<HocSinh> findAndPaginate(String searchKey, int pageNumber, int pageSize);

	int count(String searchKey);

	boolean insert(HocSinh hs);

	boolean update(HocSinh hs);

	boolean delete(int maHS); // Xóa mềm

	HocSinh findById(int maHS);

	// Thêm hàm lấy HS theo Lớp (Để dùng cho nhập điểm sau này)
	List<HocSinh> findByLop(int maLop);
}