package com.uef.appenglish123.model;

public class NhomTuVung {
    private String ID;
    private String TenNhomTuVung;

    public NhomTuVung(String id, String tenNhomTuVung) {
        ID = id;
        TenNhomTuVung = tenNhomTuVung;
    }

    public String getTenNhomTuVung() {
        return TenNhomTuVung;
    }

    public void setTenNhomTuVung(String tenNhomTuVung) {
        TenNhomTuVung = tenNhomTuVung;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
