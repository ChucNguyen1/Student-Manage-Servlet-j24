package com.student.dto;

public class LopThoiKhoaBieuDTO {
	private int maLop;
	private String tenLop;
	private int maHocKy;
	private String tenHocKy;
	private String maNH;
	private String tenNH;
	private int maKhoi;
	private String tenKhoi;
	private int tongSoTiet;         // Tổng số tiết trong tuần (30 tiết: 5 tiết x 6 ngày)
	private int soTietDaXep;        // Số tiết đã xếp TKB
	private int soTietChuaXep;      // Số tiết chưa xếp
	private boolean daXepDayDu;     // Đã xếp đầy đủ chưa
	
	public LopThoiKhoaBieuDTO() {
		this.tongSoTiet = 30; // Mặc định 5 tiết x 6 ngày
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

	public int getTongSoTiet() {
		return tongSoTiet;
	}

	public void setTongSoTiet(int tongSoTiet) {
		this.tongSoTiet = tongSoTiet;
	}

	public int getSoTietDaXep() {
		return soTietDaXep;
	}

	public void setSoTietDaXep(int soTietDaXep) {
		this.soTietDaXep = soTietDaXep;
		this.soTietChuaXep = this.tongSoTiet - soTietDaXep;
		this.daXepDayDu = (soTietDaXep >= tongSoTiet);
	}

	public int getSoTietChuaXep() {
		return soTietChuaXep;
	}

	public void setSoTietChuaXep(int soTietChuaXep) {
		this.soTietChuaXep = soTietChuaXep;
	}

	public boolean isDaXepDayDu() {
		return daXepDayDu;
	}

	public void setDaXepDayDu(boolean daXepDayDu) {
		this.daXepDayDu = daXepDayDu;
	}
	
	public int getPhanTramHoanThanh() {
		if (tongSoTiet == 0) return 0;
		return (int) ((soTietDaXep * 100.0) / tongSoTiet);
	}
}
