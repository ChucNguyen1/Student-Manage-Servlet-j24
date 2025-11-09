package com.student.model;

public class Khoi {
	private int maKhoi;
	private String tenKhoi;

	public Khoi() {
	}

	public Khoi(int maKhoi, String tenKhoi) {
		this.maKhoi = maKhoi;
		this.tenKhoi = tenKhoi;
	}

	public int getMaKhoi() {
		return maKhoi;
	}

	public void setMaKhoi(int maKhoi) {
		this.maKhoi = maKhoi;
	}

	public String getTenKhoi() {
		return tenKhoi;
	}

	public void setTenKhoi(String tenKhoi) {
		this.tenKhoi = tenKhoi;
	}

	@Override
	public String toString() {
		return "Khoi{" + "maKhoi=" + maKhoi + ", tenKhoi='" + tenKhoi + '\'' + '}';
	}
}