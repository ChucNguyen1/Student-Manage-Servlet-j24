package com.student.service;

import java.util.List;

import com.student.model.NamHoc;

public interface NamHocService {

	/**
	 * Lấy danh sách năm học có phân trang và tìm kiếm.
	 * 
	 * @param searchKey  Từ khóa tìm kiếm (Mã hoặc Tên năm học)
	 * @param pageNumber Trang hiện tại
	 * @param pageSize   Số lượng bản ghi trên mỗi trang
	 * @return Danh sách NamHoc
	 */
	List<NamHoc> findAndPaginate(String searchKey, int pageNumber, int pageSize);

	/**
	 * Đếm tổng số năm học khớp với từ khóa tìm kiếm.S
	 * 
	 * @param searchKey Từ khóa tìm kiếm
	 * @return Số lượng bản ghi
	 */
	int count(String searchKey);

	/**
	 * Thêm mới một năm học.
	 * 
	 * @param nh Đối tượng NamHoc cần thêm
	 * @return true nếu thêm thành công
	 */
	boolean insert(NamHoc nh);

	/**
	 * Cập nhật thông tin năm học.
	 * 
	 * @param nh Đối tượng NamHoc với thông tin mới
	 * @return true nếu cập nhật thành công
	 */
	boolean update(NamHoc nh);

	/**
	 * Xóa (Soft delete) một năm học.
	 * 
	 * @param maNH Mã năm học cần xóa (Kiểu String)
	 * @return true nếu xóa thành công
	 */
	String delete(String maNH);

	/**
	 * Tìm kiếm năm học theo Mã (dùng cho chức năng Sửa).
	 * 
	 * @param maNH Mã năm học
	 * @return Đối tượng NamHoc hoặc null nếu không tìm thấy
	 */
	NamHoc findById(String maNH);

	boolean updateStatus(String maNH, boolean newStatus);

	List<NamHoc> findAll();

}