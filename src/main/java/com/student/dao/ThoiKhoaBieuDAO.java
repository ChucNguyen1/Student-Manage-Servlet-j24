package com.student.dao;

import java.util.List;
import java.util.Map;

import com.student.model.ThoiKhoaBieu;

/**
 * DAO Interface: ThoiKhoaBieu (Thời Khóa Biểu)
 */
public interface ThoiKhoaBieuDAO {

	List<ThoiKhoaBieu> findByLopAndHocKy(int maLop, int maHocKy);

	List<ThoiKhoaBieu> findByGiaoVienAndHocKy(int maGV, int maHocKy);
	
	/**
	 * Lấy TKB tại một thời điểm cụ thể
	 */
	ThoiKhoaBieu findByUnique(int maLop, int maHocKy, int thu, int tiet);
	
	/**
	 * Kiểm tra xem Giáo viên có bị trùng lịch không
	 * 
	 * @param excludeMaLop: Nếu đang update, truyền maLop hiện tại để bỏ qua
	 *                      Nếu đang insert mới, truyền 0
	 * @return true nếu BỊ trùng (conflict), ngược lại false
	 */
	boolean checkGiaoVienConflict(int maGV, int maHocKy, int thu, int tiet, int excludeMaLop);
	
	/**
	 * Lấy danh sách lớp bị conflict
	 * @return Map: key = "conflictCount", value = số lượng
	 *              key = "danhSachLop", value = "10A, 10B, ..."
	 */
	Map<String, Object> getGiaoVienConflictDetail(int maGV, int maHocKy, int thu, int tiet, int excludeMaLop);
	
	boolean insert(ThoiKhoaBieu tkb);

	boolean update(ThoiKhoaBieu tkb);
	
	boolean delete(int maLop, int maHocKy, int thu, int tiet);

	boolean deleteAll(int maLop, int maHocKy);
	
	int countScheduled(int maLop, int maHocKy);
}
