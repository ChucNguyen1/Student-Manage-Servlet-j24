package com.student.dao;

import java.util.List;

import com.student.model.HocKy;

public interface HocKyDAO {

	/**
	 * Lấy danh sách học kỳ có phân trang và tìm kiếm. Sẽ JOIN với bảng NamHoc để
	 * lấy tên năm học.
	 * 
	 * @param searchKey  Từ khóa tìm kiếm (Tên học kỳ hoặc Tên năm học)
	 * @param pageNumber Trang hiện tại
	 * @param pageSize   Số lượng bản ghi/trang
	 * @return Danh sách HocKy
	 */
	List<HocKy> findAndPaginate(String searchKey, int pageNumber, int pageSize);

	/**
	 * Đếm tổng số học kỳ khớp với từ khóa tìm kiếm.
	 */
	int count(String searchKey);

	/**
	 * Thêm mới học kỳ.
	 */
	boolean insert(HocKy hk);

	/**
	 * Cập nhật thông tin học kỳ.
	 */
	boolean update(HocKy hk);

	/**
	 * Xóa cứng (Hard Delete) học kỳ khỏi CSDL. Lưu ý: Chỉ xóa được khi học kỳ chưa
	 * có dữ liệu điểm số/phân công.
	 */
	boolean delete(int maHK);

	/**
	 * Cập nhật trạng thái (Hiển thị/Ẩn). Dùng cho nút Switch.
	 */
	boolean updateStatus(int maHK, boolean status);

	/**
	 * Tìm học kỳ theo ID. Dùng để đổ dữ liệu vào Modal Sửa.
	 */
	HocKy findById(int maHK);

	/**
	 * Kiểm tra học kỳ có đang được sử dụng không (trong bảng BangDiem,
	 * PhanCong...). Dùng để chặn việc Xóa cứng nếu dữ liệu còn ràng buộc.
	 */
	boolean isUsed(int maHK);
}