package com.student.dao;

import java.util.List;

import com.student.model.DiemChiTiet;

public interface DiemChiTietDAO {

	/**
	 * Lấy bảng điểm của một lớp theo môn và học kỳ. Sử dụng LEFT JOIN để lấy cả
	 * những học sinh chưa có điểm.
	 * 
	 * @param maLop    ID Lớp
	 * @param maMonHoc ID Môn
	 * @param maHocKy  ID Học kỳ
	 * @return Danh sách DiemChiTiet (chứa thông tin học sinh và điểm)
	 */
	List<DiemChiTiet> getBangDiemLop(int maLop, int maMonHoc, int maHocKy);

	/**
	 * Kiểm tra xem điểm của học sinh này, môn này, kỳ này đã tồn tại chưa.
	 * 
	 * @return true nếu đã có dòng dữ liệu (để ta Update), false nếu chưa (để ta
	 *         Insert)
	 */
	boolean checkExist(int maHS, int maMonHoc, int maHocKy);

	/**
	 * Thêm mới dòng điểm (cho học sinh chưa có điểm).
	 */
	boolean insert(DiemChiTiet diem);

	/**
	 * Cập nhật điểm (cho học sinh đã có điểm).
	 */
	boolean update(DiemChiTiet diem);
}