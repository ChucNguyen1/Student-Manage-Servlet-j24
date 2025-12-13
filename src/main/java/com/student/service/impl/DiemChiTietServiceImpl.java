package com.student.service.impl;

import java.util.List;

import com.student.dao.DiemChiTietDAO;
import com.student.dao.impl.DiemChiTietDAOImpl;
import com.student.model.DiemChiTiet;
import com.student.service.DiemChiTietService;

public class DiemChiTietServiceImpl implements DiemChiTietService {

	private DiemChiTietDAO diemDAO = new DiemChiTietDAOImpl();

	@Override
	public List<DiemChiTiet> getBangDiemLop(int maLop, int maMonHoc, int maHocKy) {
		return diemDAO.getBangDiemLop(maLop, maMonHoc, maHocKy);
	}

	@Override
	public boolean saveDiem(DiemChiTiet diem) {
		// 1. Tự động tính toán điểm trung bình trước khi lưu
		calculateAndSetTBM(diem);

		// 2. Kiểm tra xem điểm này đã tồn tại trong DB chưa
		boolean exists = diemDAO.checkExist(diem.getMaHS(), diem.getMaMonHoc(), diem.getMaHocKy());

		// 3. Quyết định Insert hay Update
		if (exists) {
			return diemDAO.update(diem);
		} else {
			return diemDAO.insert(diem);
		}
	}

	@Override
	public void calculateAndSetTBM(DiemChiTiet d) {
		double tongDiem = 0;
		int tongHeSo = 0;

		// --- HỆ SỐ 1 (Miệng, 15p) ---
		if (d.getDiemMieng1() != null) {
			tongDiem += d.getDiemMieng1();
			tongHeSo += 1;
		}
		if (d.getDiemMieng2() != null) {
			tongDiem += d.getDiemMieng2();
			tongHeSo += 1;
		}
		if (d.getDiemMieng3() != null) {
			tongDiem += d.getDiemMieng3();
			tongHeSo += 1;
		}

		if (d.getDiem15p1() != null) {
			tongDiem += d.getDiem15p1();
			tongHeSo += 1;
		}
		if (d.getDiem15p2() != null) {
			tongDiem += d.getDiem15p2();
			tongHeSo += 1;
		}
		if (d.getDiem15p3() != null) {
			tongDiem += d.getDiem15p3();
			tongHeSo += 1;
		}

		// --- HỆ SỐ 2 (1 Tiết) ---
		if (d.getDiem1Tiet1() != null) {
			tongDiem += d.getDiem1Tiet1() * 2;
			tongHeSo += 2;
		}
		if (d.getDiem1Tiet2() != null) {
			tongDiem += d.getDiem1Tiet2() * 2;
			tongHeSo += 2;
		}

		// --- HỆ SỐ 3 (Thi) ---
		if (d.getDiemThi() != null) {
			tongDiem += d.getDiemThi() * 3;
			tongHeSo += 3;
		}

		// --- TÍNH TOÁN ---
		if (tongHeSo > 0) {
			double tbm = tongDiem / tongHeSo;
			// Làm tròn 1 chữ số thập phân (VD: 8.56 -> 8.6)
			tbm = Math.round(tbm * 10.0) / 10.0;
			d.setDiemTBM(tbm);
		} else {
			d.setDiemTBM(null); // Chưa có điểm nào
		}
	}
}