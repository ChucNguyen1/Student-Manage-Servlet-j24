package com.student.service;

import java.util.List;

import com.student.model.PhanCong;

public interface PhanCongService {

	/**
	 * Lấy danh sách hiển thị đầy đủ (Merge giữa Môn học và Phân công). Môn nào chưa
	 * được phân công thì maGV sẽ = 0.
	 */
	List<PhanCong> getPhanCongView(int maLop, int maHocKy);

	/**
	 * Lưu phân công cho 1 dòng (1 môn). - Nếu maGV > 0: Insert hoặc Update. - Nếu
	 * maGV = 0: Xóa phân công (Hủy dạy).
	 */
	boolean savePhanCong(int maLop, int maMonHoc, int maHocKy, int maGV);
}