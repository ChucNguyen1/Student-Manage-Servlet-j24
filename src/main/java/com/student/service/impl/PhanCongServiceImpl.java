package com.student.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.student.dao.PhanCongDAO;
import com.student.dao.impl.PhanCongDAOImpl;
import com.student.model.MonHoc;
import com.student.model.PhanCong;
import com.student.service.MonHocService;
import com.student.service.PhanCongService;

public class PhanCongServiceImpl implements PhanCongService {

	private PhanCongDAO phanCongDAO = new PhanCongDAOImpl();
	private MonHocService monHocService = new MonHocServiceImpl();

	@Override
	public List<PhanCong> getPhanCongView(int maLop, int maHocKy) {
		// 1. Lấy tất cả môn học trong trường (VD: 10 môn)
		List<MonHoc> listMonHoc = monHocService.findAll();

		// 2. Lấy danh sách đã phân công trong DB (VD: Mới phân công 3 môn)
		List<PhanCong> listDaPhanCong = phanCongDAO.findByLopAndHocKy(maLop, maHocKy);

		// 3. Ghép danh sách (Kết quả trả về phải đủ 10 dòng)
		List<PhanCong> result = new ArrayList<>();

		for (MonHoc mh : listMonHoc) {
			PhanCong pc = new PhanCong();

			// Thông tin cơ bản từ môn học
			pc.setMaMonHoc(mh.getMaMH());
			pc.setTenMonHoc(mh.getTenMH()); // DTO
			pc.setMaLop(maLop);
			pc.setMaHocKy(maHocKy);

			// Tìm xem môn này đã có trong listDaPhanCong chưa?
			PhanCong existing = findInList(listDaPhanCong, mh.getMaMH());

			if (existing != null) {
				// Đã phân công -> Lấy thông tin giáo viên điền vào
				pc.setMaGV(existing.getMaGV());
				pc.setTenGiaoVien(existing.getTenGiaoVien()); // DTO
				pc.setTrangThai(true);
			} else {
				// Chưa phân công -> maGV = 0
				pc.setMaGV(0);
				pc.setTenGiaoVien("-- Chưa chọn --");
				pc.setTrangThai(false);
			}

			result.add(pc);
		}

		return result;
	}

	// Hàm phụ để tìm kiếm trong List
	private PhanCong findInList(List<PhanCong> list, int maMonHoc) {
		for (PhanCong pc : list) {
			if (pc.getMaMonHoc() == maMonHoc) {
				return pc;
			}
		}
		return null;
	}

	@Override
	public boolean savePhanCong(int maLop, int maMonHoc, int maHocKy, int maGV) {
		// Trường hợp 1: Người dùng chọn "Chưa phân công" (maGV = 0)
		// -> Nghĩa là muốn XÓA phân công cũ nếu có
		if (maGV == 0) {
			return phanCongDAO.delete(maLop, maMonHoc, maHocKy);
		}

		// Trường hợp 2: Người dùng chọn Giáo viên A
		// Kiểm tra xem đã tồn tại chưa để Insert hay Update
		PhanCong exist = phanCongDAO.findByUniqueKey(maLop, maMonHoc, maHocKy);

		PhanCong pc = new PhanCong();
		pc.setMaLop(maLop);
		pc.setMaMonHoc(maMonHoc);
		pc.setMaHocKy(maHocKy);
		pc.setMaGV(maGV);

		if (exist != null) {
			return phanCongDAO.update(pc); // Đã có -> Update người dạy mới
		} else {
			return phanCongDAO.insert(pc); // Chưa có -> Insert mới
		}
	}
}