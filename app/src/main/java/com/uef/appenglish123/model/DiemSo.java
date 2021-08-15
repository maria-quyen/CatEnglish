package com.uef.appenglish123.model;

public class DiemSo {
    private String MaDiem, MaTK, MaBai;
    private int Diem;

    public DiemSo(String maTK, String maBai, int diem) {
        MaTK = maTK;
        MaBai = maBai;
        Diem = diem;
    }

    public DiemSo() {
    }

    public String getMaDiem() {
        return MaDiem;
    }

    public void setMaDiem(String maDiem) {
        MaDiem = maDiem;
    }

    public String getMaTK() {
        return MaTK;
    }

    public void setMaTK(String maTK) {
        MaTK = maTK;
    }

    public String getMaBai() {
        return MaBai;
    }

    public void setMaBai(String maBai) {
        MaBai = maBai;
    }

    public int getDiem() {
        return Diem;
    }

    public void setDiem(int diem) {
        Diem = diem;
    }
}
