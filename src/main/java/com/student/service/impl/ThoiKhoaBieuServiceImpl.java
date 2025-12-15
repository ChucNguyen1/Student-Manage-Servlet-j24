package com.student.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.student.dao.PhanCongDAO;
import com.student.dao.ThoiKhoaBieuDAO;
import com.student.dao.impl.PhanCongDAOImpl;
import com.student.dao.impl.ThoiKhoaBieuDAOImpl;
import com.student.model.PhanCong;
import com.student.model.ThoiKhoaBieu;
import com.student.service.ThoiKhoaBieuService;

public class ThoiKhoaBieuServiceImpl implements ThoiKhoaBieuService {

	private ThoiKhoaBieuDAO tkbDAO = new ThoiKhoaBieuDAOImpl();
	private PhanCongDAO phanCongDAO = new PhanCongDAOImpl();

	@Override
	public List<ThoiKhoaBieu> getTKBByLop(int maLop, int maHocKy) {
		return tkbDAO.findByLopAndHocKy(maLop, maHocKy);
	}

	@Override
	public Map<String, ThoiKhoaBieu> getTKBGrid(int maLop, int maHocKy) {
		Map<String, ThoiKhoaBieu> grid = new HashMap<>();
		
		List<ThoiKhoaBieu> list = tkbDAO.findByLopAndHocKy(maLop, maHocKy);
		
		for (ThoiKhoaBieu tkb : list) {
			String key = tkb.getThu() + "_" + tkb.getTiet();
			grid.put(key, tkb);
		}
		
		return grid;
	}

	@Override
	public List<ThoiKhoaBieu> getTKBByGiaoVien(int maGV, int maHocKy) {
		return tkbDAO.findByGiaoVienAndHocKy(maGV, maHocKy);
	}

	@Override
	public List<PhanCong> getAvailableMonHoc(int maLop, int maHocKy) {
		List<PhanCong> dsPhanCong = phanCongDAO.findByLopAndHocKy(maLop, maHocKy);
		List<PhanCong> available = new ArrayList<>();
		for (PhanCong pc : dsPhanCong) {
			if (pc.getMaGV() > 0) {
				available.add(pc);
			}
		}
		
		return available;
	}

	@Override
	public Map<String, Object> saveTiet(ThoiKhoaBieu tkb) {
		Map<String, Object> result = new HashMap<>();

		Map<String, Object> validateResult = validate(tkb);
		if (!(boolean) validateResult.get("valid")) {
			result.put("success", false);
			result.put("message", "Dữ liệu không hợp lệ: " + validateResult.get("errors"));
			return result;
		}

		boolean conflict = tkbDAO.checkGiaoVienConflict(
			tkb.getMaGV(), 
			tkb.getMaHocKy(), 
			tkb.getThu(), 
			tkb.getTiet(), 
			tkb.getMaLop()
		);
		
		if (conflict) {
			Map<String, Object> conflictDetail = tkbDAO.getGiaoVienConflictDetail(
				tkb.getMaGV(), 
				tkb.getMaHocKy(), 
				tkb.getThu(), 
				tkb.getTiet(), 
				tkb.getMaLop()
			);
			
			result.put("success", false);
			result.put("message", "Giáo viên bị trùng lịch! Đang dạy lớp: " + conflictDetail.get("danhSachLop"));
			result.put("conflictDetail", conflictDetail);
			return result;
		}

		ThoiKhoaBieu existing = tkbDAO.findByUnique(
			tkb.getMaLop(), 
			tkb.getMaHocKy(), 
			tkb.getThu(), 
			tkb.getTiet()
		);
		
		boolean success;
		if (existing != null) {

			success = tkbDAO.update(tkb);
			result.put("message", success ? "Cập nhật tiết học thành công!" : "Lỗi khi cập nhật!");
		} else {

			success = tkbDAO.insert(tkb);
			result.put("message", success ? "Thêm tiết học thành công!" : "Lỗi khi thêm tiết!");
		}
		
		result.put("success", success);
		return result;
	}

	@Override
	public boolean deleteTiet(int maLop, int maHocKy, int thu, int tiet) {
		return tkbDAO.delete(maLop, maHocKy, thu, tiet);
	}

	@Override
	public boolean resetTKB(int maLop, int maHocKy) {
		return tkbDAO.deleteAll(maLop, maHocKy);
	}

	@Override
	public int countScheduled(int maLop, int maHocKy) {
		return tkbDAO.countScheduled(maLop, maHocKy);
	}

	@Override
	public Map<String, Object> validate(ThoiKhoaBieu tkb) {
		Map<String, Object> result = new HashMap<>();
		List<String> errors = new ArrayList<>();

		if (tkb.getMaLop() <= 0) {
			errors.add("Mã lớp không hợp lệ");
		}
		if (tkb.getMaHocKy() <= 0) {
			errors.add("Mã học kỳ không hợp lệ");
		}
		if (tkb.getMaMonHoc() <= 0) {
			errors.add("Mã môn học không hợp lệ");
		}
		if (tkb.getMaGV() <= 0) {
			errors.add("Mã giáo viên không hợp lệ");
		}

		if (tkb.getThu() < 2 || tkb.getThu() > 7) {
			errors.add("Thứ phải trong khoảng 2-7");
		}

		if (tkb.getTiet() < 1 || tkb.getTiet() > 5) {
			errors.add("Tiết phải trong khoảng 1-5");
		}
		
		result.put("valid", errors.isEmpty());
		result.put("errors", errors);
		
		return result;
	}
}
