package com.student.model;

public class HocKy {
	private int maHK;
	private String tenHK;
	private int heSo;
	private String maNH; // Khóa ngoại lưu trong DB
	private boolean trangThai;

	// Thuộc tính phụ (DTO) để hiển thị tên năm học ra bảng
	private String tenNamHoc;

	public HocKy() {
	}

	// Getters & Setters
	public int getMaHK() {
		return maHK;
	}

	public void setMaHK(int maHK) {
		this.maHK = maHK;
	}

	public String getTenHK() {
		return tenHK;
	}

	public void setTenHK(String tenHK) {
		this.tenHK = tenHK;
	}

	public int getHeSo() {
		return heSo;
	}

	public void setHeSo(int heSo) {
		this.heSo = heSo;
	}

	public String getMaNH() {
		return maNH;
	}

	public void setMaNH(String maNH) {
		this.maNH = maNH;
	}

	public boolean isTrangThai() {
		return trangThai;
	}

	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}

	public String getTenNamHoc() {
		return tenNamHoc;
	}

	public void setTenNamHoc(String tenNamHoc) {
		this.tenNamHoc = tenNamHoc;
	}
}