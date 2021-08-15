package com.uef.appenglish123.model;

public class BinhLuan {
    private String MaNP, MaTK, NoiDung;

    public BinhLuan(String maNP, String maTK, String noiDung) {
        MaNP = maNP;
        MaTK = maTK;
        NoiDung = noiDung;
    }

    public BinhLuan(){

    }

    public String getMaNP() {
        return MaNP;
    }

    public void setMaNP(String maNP) {
        MaNP = maNP;
    }

    public String getMaTK() {
        return MaTK;
    }

    public void setMaTK(String maTK) {
        MaTK = maTK;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String noiDung) {
        NoiDung = noiDung;
    }
}
