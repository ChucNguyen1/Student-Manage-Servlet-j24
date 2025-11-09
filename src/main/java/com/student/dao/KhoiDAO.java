package com.student.dao;

import java.util.List;

import com.student.model.Khoi;

public interface KhoiDAO {
	/**
	 * Lấy tổng số lượng Khối (có thể kèm tìm kiếm).
	 * 
	 * @param searchKey Từ khóa tìm kiếm (nếu là null/rỗng, đếm tất cả)
	 * @return Tổng số Khối
	 */
	int count(String searchKey);

	/**
	 * Lấy danh sách Khối có phân trang và tìm kiếm.
	 * 
	 * @param searchKey  Từ khóa (null/rỗng nếu không tìm)
	 * @param pageNumber Trang hiện tại (bắt đầu từ 1)
	 * @param pageSize   Số lượng mục mỗi trang
	 * @return Danh sách Khối
	 */
	List<Khoi> findAndPaginate(String searchKey, int pageNumber, int pageSize);

	boolean insert(Khoi khoi);

	boolean delete(int maKhoi);

	boolean update(Khoi khoi);

	List<Khoi> getAll();

}