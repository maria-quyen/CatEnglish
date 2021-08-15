package com.uef.appenglish123.model;

public class TaiKhoan {
    private String Ma, Ho, Ten, GioiTinh, SDT, Email, MatKhau, Quyen, Hinh, Status, Hinhnen;

    public TaiKhoan(){

    }
    public TaiKhoan(String ten, String gioiTinh, String sdt, String email, String pass, String hinh, String status, String hinhnen) {
        this.Ten = ten;
        this.GioiTinh = gioiTinh;
        this.SDT = sdt;
        this.Email = email;
        this.MatKhau = pass;
        this.Hinh = hinh;
        this.Status=status;
        this.Hinhnen=hinhnen;
    }

    public TaiKhoan(String ten, String email, String hinh, String status){
        this.Ten = ten;
        this.Email = email;
        this.Hinh=hinh;
        this.Status=status;
    }

    public String getHinhnen() {
        return Hinhnen;
    }

    public void setHinhnen(String hinhnen) {
        Hinhnen = hinhnen;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMa() {
        return Ma;
    }

    public void setMa(String ma) {
        Ma = ma;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getHo() {
        return Ho;
    }

    public void setHo(String ho) {
        Ho = ho;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getQuyen() {
        return Quyen;
    }

    public void setQuyen(String quyen) {
        Quyen = quyen;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        GioiTinh = gioiTinh;
    }

    public String getHinh() {
        return Hinh;
    }

    public void setHinh(String hinh) {
        Hinh = hinh;
    }


}
