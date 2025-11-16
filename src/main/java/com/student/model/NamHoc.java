package com.student.model;

import java.sql.Date;

public class NamHoc {
	private String maNH; // Khóa chính là String
	private String tenNH;
	private Date ngayBatDau;
	private Date ngayKetThuc;
	private boolean trangThai;

	public NamHoc() {
	}

	public NamHoc(String maNH, String tenNH, Date ngayBatDau, Date ngayKetThuc, boolean trangThai) {
		this.maNH = maNH;
		this.tenNH = tenNH;
		this.ngayBatDau = ngayBatDau;
		this.ngayKetThuc = ngayKetThuc;
		this.trangThai = trangThai;
	}

	// Getters and Setters
	public String getMaNH() {
		return maNH;
	}

	public void setMaNH(String maNH) {
		this.maNH = maNH;
	}

	public String getTenNH() {
		return tenNH;
	}

	public void setTenNH(String tenNH) {
		this.tenNH = tenNH;
	}

	public Date getNgayBatDau() {
		return ngayBatDau;
	}

	public void setNgayBatDau(Date ngayBatDau) {
		this.ngayBatDau = ngayBatDau;
	}

	public Date getNgayKetThuc() {
		return ngayKetThuc;
	}

	public void setNgayKetThuc(Date ngayKetThuc) {
		this.ngayKetThuc = ngayKetThuc;
	}

	public boolean isTrangThai() {
		return trangThai;
	}

	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}
}