package com.uef.appenglish123.model;

public class DsTuVung {
    private String ID;
    private String MaBai;
    private String TuVung;
    private String PhienAm;
    private String LoaiTu;
    private String Nghia;
    private String NghiaTD;
    private String PhatAm;
    private String Slow;
    private String ViDu;
    private String Hinh;



    public DsTuVung(String id, String maBai, String tuVung, String phienAm, String loaiTu, String nghia, String phatAm, String slow, String viDu, String hinh) {
        ID = id;
        MaBai = maBai;
        TuVung = tuVung;
        PhienAm = phienAm;
        LoaiTu = loaiTu;
        Nghia = nghia;
        PhatAm = phatAm;
        Slow=slow;
        ViDu = viDu;
        Hinh = hinh;
    }
    public DsTuVung(String id, String maBai, String tuVung, String phienAm, String loaiTu, String nghia, String hinh) {
        ID = id;
        MaBai = maBai;
        TuVung = tuVung;
        PhienAm = phienAm;
        LoaiTu = loaiTu;
        Nghia = nghia;
        Hinh = hinh;
    }

    public DsTuVung(String id, String tuVung, String nghiaTD, String phatAm, String hinh) {
        ID = id;
        TuVung = tuVung;
        NghiaTD=nghiaTD;
        PhatAm = phatAm;
        Hinh = hinh;
    }

    public DsTuVung(String tuVung, String nghia, String hinh) {
        TuVung = tuVung;
        Nghia=nghia;
        Hinh = hinh;
    }

    public DsTuVung(String tuVung, String phienAm, String nghia, String phatAm) {
        TuVung = tuVung;
        PhienAm = phienAm;
        Nghia = nghia;
        PhatAm = phatAm;

    }

    public DsTuVung(){

    }

    public DsTuVung(String ma, String tv){
        this.ID=ma;
        this.TuVung=tv;
    }




    public String getSlow() {
        return Slow;
    }

    public void setSlow(String slow) {
        Slow = slow;
    }

    public String getNghiaTD() {
        return NghiaTD;
    }

    public void setNghiaTD(String nghiaTD) {
        NghiaTD = nghiaTD;
    }

    public String getLoaiTu() {
        return LoaiTu;
    }

    public void setLoaiTu(String loaiTu) {
        LoaiTu = loaiTu;
    }

    public String getNghia() {
        return Nghia;
    }

    public void setNghia(String nghia) {
        Nghia = nghia;
    }

    public String getHinh() {
        return Hinh;
    }

    public void setHinh(String hinh) {
        Hinh = hinh;
    }

    public String getPhienAm() {
        return PhienAm;
    }

    public void setPhienAm(String phienAm) {
        PhienAm = phienAm;
    }

    public String getTuVung() {
        return TuVung;
    }

    public void setTuVung(String tuVung) {
        TuVung = tuVung;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getMaBai() {
        return MaBai;
    }

    public void setMaBai(String maBai) {
        MaBai = maBai;
    }

    public String getPhatAm() {
        return PhatAm;
    }

    public void setPhatAm(String phatAm) {
        PhatAm = phatAm;
    }

    public String getViDu() {
        return ViDu;
    }

    public void setViDu(String viDu) {
        ViDu = viDu;
    }
}
