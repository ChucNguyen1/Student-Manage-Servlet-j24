package com.student.service;

import java.util.List;
import java.util.Map;

import com.student.dto.LopThoiKhoaBieuDTO;
import com.student.model.PhanCong;
import com.student.model.ThoiKhoaBieu;

public interface ThoiKhoaBieuService {


	List<ThoiKhoaBieu> getTKBByLop(int maLop, int maHocKy);

	Map<String, ThoiKhoaBieu> getTKBGrid(int maLop, int maHocKy);
	
	List<ThoiKhoaBieu> getTKBByGiaoVien(int maGV, int maHocKy);
	
	List<PhanCong> getAvailableMonHoc(int maLop, int maHocKy);
	
	/**
	 * Lưu/Cập nhật 1 tiết vào TKB
	 * 
	 * @param tkb: Thông tin tiết cần lưu
	 * @return Map với key:
	 *   - "success": true/false
	 *   - "message": Thông báo chi tiết
	 *   - "conflictDetail": Danh sách lớp bị trùng 
	 */
	Map<String, Object> saveTiet(ThoiKhoaBieu tkb);
	
	boolean deleteTiet(int maLop, int maHocKy, int thu, int tiet);
	
	boolean resetTKB(int maLop, int maHocKy);
	
	int countScheduled(int maLop, int maHocKy);
	
	/**
	 * Validate trước khi lưu
	 * @return Map với key: "valid" (true/false), "errors" (List<String>)
	 */
	Map<String, Object> validate(ThoiKhoaBieu tkb);
	
	/**
	 * Lấy danh sách lớp với thống kê TKB
	 */
	List<LopThoiKhoaBieuDTO> getDanhSachLopTKB(String maNH, Integer maHocKy, 
			Integer maKhoi, Integer maLop);
}
