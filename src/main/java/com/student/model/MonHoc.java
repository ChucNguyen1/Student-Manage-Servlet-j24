package com.student.model;

public class MonHoc {
	private int maMH;
	private String tenMH;
	private int soTiet;
	private boolean trangThai;

	public MonHoc() {
	}

	public MonHoc(int maMH, String tenMH, int soTiet, boolean trangThai) {
		this.maMH = maMH;
		this.tenMH = tenMH;
		this.soTiet = soTiet;
		this.trangThai = trangThai;
	}

	// Getters & Setters
	public int getMaMH() {
		return maMH;
	}

	public void setMaMH(int maMH) {
		this.maMH = maMH;
	}

	public String getTenMH() {
		return tenMH;
	}

	public void setTenMH(String tenMH) {
		this.tenMH = tenMH;
	}

	public int getSoTiet() {
		return soTiet;
	}

	public void setSoTiet(int soTiet) {
		this.soTiet = soTiet;
	}

	public boolean isTrangThai() {
		return trangThai;
	}

	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}
}