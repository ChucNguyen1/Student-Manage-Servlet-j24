package com.student.model;

public class PhanCong {
	private int maPhanCong;

	private int maGV;
	private int maLop;
	private int maMonHoc;
	private int maHocKy;
	private int maTo;
	private boolean trangThai;
	private String tenGiaoVien;
	private String tenLop;
	private String tenMonHoc;
	private String tenHocKy;

	public PhanCong() {
	}

	// --- GETTERS & SETTERS ---

	public int getMaPhanCong() {
		return maPhanCong;
	}

	public void setMaPhanCong(int maPhanCong) {
		this.maPhanCong = maPhanCong;
	}

	public int getMaGV() {
		return maGV;
	}

	public void setMaGV(int maGV) {
		this.maGV = maGV;
	}

	public int getMaLop() {
		return maLop;
	}

	public void setMaLop(int maLop) {
		this.maLop = maLop;
	}

	public int getMaMonHoc() {
		return maMonHoc;
	}

	public void setMaMonHoc(int maMonHoc) {
		this.maMonHoc = maMonHoc;
	}

	public int getMaHocKy() {
		return maHocKy;
	}

	public void setMaHocKy(int maHocKy) {
		this.maHocKy = maHocKy;
	}

	public boolean isTrangThai() {
		return trangThai;
	}

	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}

	public String getTenGiaoVien() {
		return tenGiaoVien;
	}

	public void setTenGiaoVien(String tenGiaoVien) {
		this.tenGiaoVien = tenGiaoVien;
	}

	public String getTenLop() {
		return tenLop;
	}

	public void setTenLop(String tenLop) {
		this.tenLop = tenLop;
	}

	public String getTenMonHoc() {
		return tenMonHoc;
	}

	public void setTenMonHoc(String tenMonHoc) {
		this.tenMonHoc = tenMonHoc;
	}

	public String getTenHocKy() {
		return tenHocKy;
	}

	public void setTenHocKy(String tenHocKy) {
		this.tenHocKy = tenHocKy;
	}

	public int getMaTo() {
		return maTo;
	}

	public void setMaTo(int maTo) {
		this.maTo = maTo;
	}
}