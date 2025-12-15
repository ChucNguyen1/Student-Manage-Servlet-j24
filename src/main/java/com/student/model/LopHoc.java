package com.student.model;

public class LopHoc {
	private int maLop;
	private String tenLop;
	private int maKhoi;
	private String maNH;
	private int maGVCN; 
	private boolean trangThai;
	private String tenKhoi;
	private String tenNamHoc;
	private String tenGVCN;

	public LopHoc() {
	}

	// Getters & Setters
	public int getMaLop() {
		return maLop;
	}

	public void setMaLop(int maLop) {
		this.maLop = maLop;
	}

	public String getTenLop() {
		return tenLop;
	}

	public void setTenLop(String tenLop) {
		this.tenLop = tenLop;
	}

	public int getMaKhoi() {
		return maKhoi;
	}

	public void setMaKhoi(int maKhoi) {
		this.maKhoi = maKhoi;
	}

	public String getMaNH() {
		return maNH;
	}

	public void setMaNH(String maNH) {
		this.maNH = maNH;
	}

	public int getMaGVCN() {
		return maGVCN;
	}

	public void setMaGVCN(int maGVCN) {
		this.maGVCN = maGVCN;
	}

	public boolean isTrangThai() {
		return trangThai;
	}

	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}

	// DTO Getters/Setters
	public String getTenKhoi() {
		return tenKhoi;
	}

	public void setTenKhoi(String tenKhoi) {
		this.tenKhoi = tenKhoi;
	}

	public String getTenNamHoc() {
		return tenNamHoc;
	}

	public void setTenNamHoc(String tenNamHoc) {
		this.tenNamHoc = tenNamHoc;
	}

	public String getTenGVCN() {
		return tenGVCN;
	}

	public void setTenGVCN(String tenGVCN) {
		this.tenGVCN = tenGVCN;
	}
}