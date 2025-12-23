package com.student.model;

import java.util.Date;

public class DocThongBao {
    private int maDocTB;
    private int maHS;
    private int maTB;
    private Date ngayDoc;

    public DocThongBao() {
    }

    public int getMaDocTB() {
        return maDocTB;
    }

    public void setMaDocTB(int maDocTB) {
        this.maDocTB = maDocTB;
    }

    public int getMaHS() {
        return maHS;
    }

    public void setMaHS(int maHS) {
        this.maHS = maHS;
    }

    public int getMaTB() {
        return maTB;
    }

    public void setMaTB(int maTB) {
        this.maTB = maTB;
    }

    public Date getNgayDoc() {
        return ngayDoc;
    }

    public void setNgayDoc(Date ngayDoc) {
        this.ngayDoc = ngayDoc;
    }
}
