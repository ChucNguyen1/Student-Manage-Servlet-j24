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
		List<MonHoc> listMonHoc = monHocService.findAll();
		List<PhanCong> listDaPhanCong = phanCongDAO.findByLopAndHocKy(maLop, maHocKy);
		List<PhanCong> result = new ArrayList<>();

		for (MonHoc mh : listMonHoc) {
			PhanCong pc = new PhanCong();

			pc.setMaMonHoc(mh.getMaMH());
			pc.setTenMonHoc(mh.getTenMH()); 
			pc.setMaLop(maLop);
			pc.setMaHocKy(maHocKy);
			pc.setMaTo(mh.getMaTo());

			PhanCong existing = findInList(listDaPhanCong, mh.getMaMH());

			if (existing != null) {
				pc.setMaGV(existing.getMaGV());
				pc.setTenGiaoVien(existing.getTenGiaoVien());
				pc.setTrangThai(true);
			} else {
				pc.setMaGV(0);
				pc.setTenGiaoVien("-- Chưa chọn --");
				pc.setTrangThai(false);
			}

			result.add(pc);
		}

		return result;
	}

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

		if (maGV == 0) {
			return phanCongDAO.delete(maLop, maMonHoc, maHocKy);
		}

		PhanCong exist = phanCongDAO.findByUniqueKey(maLop, maMonHoc, maHocKy);

		PhanCong pc = new PhanCong();
		pc.setMaLop(maLop);
		pc.setMaMonHoc(maMonHoc);
		pc.setMaHocKy(maHocKy);
		pc.setMaGV(maGV);

		if (exist != null) {
			return phanCongDAO.update(pc); 
		} else {
			return phanCongDAO.insert(pc); 
		}
	}

	@Override
	public boolean hasAnyPhanCong(int maLop, int maHocKy) {
		List<PhanCong> list = phanCongDAO.findByLopAndHocKy(maLop, maHocKy);
		return list != null && !list.isEmpty();
	}

	@Override
	public int countPhanCong(int maLop, int maHocKy) {
		List<PhanCong> list = phanCongDAO.findByLopAndHocKy(maLop, maHocKy);
		return (list != null) ? list.size() : 0;
	}
}