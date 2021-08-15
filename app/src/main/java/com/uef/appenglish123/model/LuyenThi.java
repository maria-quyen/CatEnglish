package com.uef.appenglish123.model;

public class LuyenThi {
    private String MaBai, TenBai, Coin;

    public LuyenThi(String maBai, String tenBai, String coin) {
        MaBai = maBai;
        TenBai = tenBai;
        Coin = coin;
    }

    public String getMaBai() {
        return MaBai;
    }

    public void setMaBai(String maBai) {
        MaBai = maBai;
    }

    public String getTenBai() {
        return TenBai;
    }

    public void setTenBai(String tenBai) {
        TenBai = tenBai;
    }

    public String getCoin() {
        return Coin;
    }

    public void setCoin(String coin) {
        Coin = coin;
    }
}
