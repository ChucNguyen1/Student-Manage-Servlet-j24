package com.student.dto;

public class LopPhanCongDTO {
	private int maLop;
	private String tenLop;
	private int maHocKy;
	private String tenHocKy;
	private String maNH;
	private String tenNH;
	private int maKhoi;
	private String tenKhoi;
	private int tongSoMon;        // Tổng số môn học của lớp
	private int soMonDaPhanCong;  // Số môn đã phân công
	private int soMonChuaPhanCong; // Số môn chưa phân công
	
	public LopPhanCongDTO() {
	}

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

	public int getMaHocKy() {
		return maHocKy;
	}

	public void setMaHocKy(int maHocKy) {
		this.maHocKy = maHocKy;
	}

	public String getTenHocKy() {
		return tenHocKy;
	}

	public void setTenHocKy(String tenHocKy) {
		this.tenHocKy = tenHocKy;
	}

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

	public int getTongSoMon() {
		return tongSoMon;
	}

	public void setTongSoMon(int tongSoMon) {
		this.tongSoMon = tongSoMon;
	}

	public int getSoMonDaPhanCong() {
		return soMonDaPhanCong;
	}

	public void setSoMonDaPhanCong(int soMonDaPhanCong) {
		this.soMonDaPhanCong = soMonDaPhanCong;
	}

	public int getSoMonChuaPhanCong() {
		return soMonChuaPhanCong;
	}

	public void setSoMonChuaPhanCong(int soMonChuaPhanCong) {
		this.soMonChuaPhanCong = soMonChuaPhanCong;
	}

	public boolean isDaPhanCongDayDu() {
		return soMonChuaPhanCong == 0 && tongSoMon > 0;
	}
}
