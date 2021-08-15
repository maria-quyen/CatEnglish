package com.uef.appenglish123.model;

public class NguPhap {
    private String MaNP;
    private String TenNP;
    private String NoiDung;
    private String MaMau;

    public NguPhap(){

    }

    public NguPhap(String maNP, String tenNP) {
        MaNP = maNP;
        TenNP = tenNP;
    }

    public String getMaMau() {
        return MaMau;
    }

    public void setMaMau(String maMau) {
        MaMau = maMau;
    }

    public String getMaNP() {
        return MaNP;
    }

    public void setMaNP(String maNP) {
        MaNP = maNP;
    }

    public String getTenNP() {
        return TenNP;
    }

    public void setTenNP(String tenNP) {
        TenNP = tenNP;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String noiDung) {
        NoiDung = noiDung;
    }
}
