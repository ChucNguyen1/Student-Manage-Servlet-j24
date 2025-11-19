package com.student.model;

import java.util.Date;

public class ThongBao {

    private int maTB;
    private String tieuDe;
    private String noiDung;
    private Date ngayDang;
    private int maNguoiTao;
    
    // Thuộc tính phụ (Không có trong bảng ThongBao, nhưng cần để hiển thị tên người đăng)
    private String tenNguoiTao; 


    public ThongBao() {
    }


    public int getMaTB() { return maTB; }
    public void setMaTB(int maTB) { this.maTB = maTB; }

    public String getTieuDe() { return tieuDe; }
    public void setTieuDe(String tieuDe) { this.tieuDe = tieuDe; }

    public String getNoiDung() { return noiDung; }
    public void setNoiDung(String noiDung) { this.noiDung = noiDung; }

    public Date getNgayDang() { return ngayDang; }
    public void setNgayDang(Date ngayDang) { this.ngayDang = ngayDang; }

    public int getMaNguoiTao() { return maNguoiTao; }
    public void setMaNguoiTao(int maNguoiTao) { this.maNguoiTao = maNguoiTao; }

    public String getTenNguoiTao() { return tenNguoiTao; }
    public void setTenNguoiTao(String tenNguoiTao) { this.tenNguoiTao = tenNguoiTao; }
}