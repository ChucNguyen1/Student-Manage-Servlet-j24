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
		calculateAndSetTBM(diem);
		boolean exists = diemDAO.checkExist(diem.getMaHS(), diem.getMaMonHoc(), diem.getMaHocKy());
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

		if (d.getDiem1Tiet1() != null) {
			tongDiem += d.getDiem1Tiet1() * 2;
			tongHeSo += 2;
		}
		if (d.getDiem1Tiet2() != null) {
			tongDiem += d.getDiem1Tiet2() * 2;
			tongHeSo += 2;
		}

		if (d.getDiemThi() != null) {
			tongDiem += d.getDiemThi() * 3;
			tongHeSo += 3;
		}

		if (tongHeSo > 0) {
			double tbm = tongDiem / tongHeSo;
			tbm = Math.round(tbm * 10.0) / 10.0;
			d.setDiemTBM(tbm);
		} else {
			d.setDiemTBM(null); 
		}
	}
}