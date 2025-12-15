package com.student.model;

import java.sql.Timestamp;


public class ThoiKhoaBieu {
	
	
	private int maTKB;           
	private int maLop;           
	private int maHocKy;        
	private int maMonHoc;        
	private int maGV;            
	private int thu;             
	private int tiet;            
	private String phongHoc;    
	private Timestamp createdAt;
	private Timestamp updatedAt;
	private String tenLop;      
	private String tenHocKy;     
	private String tenMonHoc;    
	private String tenGiaoVien;  
	private String tenThu;       
	
	public ThoiKhoaBieu() {
	}
	
	public ThoiKhoaBieu(int maLop, int maHocKy, int maMonHoc, int maGV, int thu, int tiet) {
		this.maLop = maLop;
		this.maHocKy = maHocKy;
		this.maMonHoc = maMonHoc;
		this.maGV = maGV;
		this.thu = thu;
		this.tiet = tiet;
	}
	
	// ============ GETTERS & SETTERS ============
	
	public int getMaTKB() {
		return maTKB;
	}

	public void setMaTKB(int maTKB) {
		this.maTKB = maTKB;
	}

	public int getMaLop() {
		return maLop;
	}

	public void setMaLop(int maLop) {
		this.maLop = maLop;
	}

	public int getMaHocKy() {
		return maHocKy;
	}

	public void setMaHocKy(int maHocKy) {
		this.maHocKy = maHocKy;
	}

	public int getMaMonHoc() {
		return maMonHoc;
	}

	public void setMaMonHoc(int maMonHoc) {
		this.maMonHoc = maMonHoc;
	}

	public int getMaGV() {
		return maGV;
	}

	public void setMaGV(int maGV) {
		this.maGV = maGV;
	}

	public int getThu() {
		return thu;
	}

	public void setThu(int thu) {
		this.thu = thu;
	}

	public int getTiet() {
		return tiet;
	}

	public void setTiet(int tiet) {
		this.tiet = tiet;
	}

	public String getPhongHoc() {
		return phongHoc;
	}

	public void setPhongHoc(String phongHoc) {
		this.phongHoc = phongHoc;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	// DTO Getters & Setters
	
	public String getTenLop() {
		return tenLop;
	}

	public void setTenLop(String tenLop) {
		this.tenLop = tenLop;
	}

	public String getTenHocKy() {
		return tenHocKy;
	}

	public void setTenHocKy(String tenHocKy) {
		this.tenHocKy = tenHocKy;
	}

	public String getTenMonHoc() {
		return tenMonHoc;
	}

	public void setTenMonHoc(String tenMonHoc) {
		this.tenMonHoc = tenMonHoc;
	}

	public String getTenGiaoVien() {
		return tenGiaoVien;
	}

	public void setTenGiaoVien(String tenGiaoVien) {
		this.tenGiaoVien = tenGiaoVien;
	}

	public String getTenThu() {
		return tenThu;
	}

	public void setTenThu(String tenThu) {
		this.tenThu = tenThu;
	}
	
	
	public String getTimeKey() {
		return thu + "_" + tiet;
	}
	
	
	public boolean isWeekend() {
		return thu == 7;
	}
	
	public static String getTenThuFromNumber(int thu) {
		switch (thu) {
			case 2: return "Thứ Hai";
			case 3: return "Thứ Ba";
			case 4: return "Thứ Tư";
			case 5: return "Thứ Năm";
			case 6: return "Thứ Sáu";
			case 7: return "Thứ Bảy";
			default: return "Không xác định";
		}
	}
	
	@Override
	public String toString() {
		return String.format("TKB[%s - %s - Tiết %d - %s]", 
			tenLop != null ? tenLop : "Lớp " + maLop,
			getTenThuFromNumber(thu),
			tiet,
			tenMonHoc != null ? tenMonHoc : "Môn " + maMonHoc
		);
	}
}
