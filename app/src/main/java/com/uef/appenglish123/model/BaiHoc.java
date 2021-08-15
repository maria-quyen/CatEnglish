package com.uef.appenglish123.model;

public class BaiHoc {
    private String MaBai;
    private String MaNhom;
    private String TenBai;


    public BaiHoc(String maBai, String tenBai) {
        this.MaBai = maBai;
        this.TenBai = tenBai;
    }


    public String getMaBai() {
        return MaBai;
    }

    public void setMaBai(String maBai) {
        MaBai = maBai;
    }

    public String getMaNhom() {
        return MaNhom;
    }

    public void setMaNhom(String maNhom) {
        MaNhom = maNhom;
    }

    public String getTenBai() {
        return TenBai;
    }

    public void setTenBai(String tenBai) {
        TenBai = tenBai;
    }
}
