package com.uef.appenglish123.model;

public class TracNghiem {

    private String ID;
    private String CauHoi;
    private String CauA;
    private String CauB;
    private String CauC;
    private String CauD;
    private String MaBai;
    private String Correct;

    public TracNghiem(){
    }

    public TracNghiem(String cauHoi, String cauA, String cauB, String cauC, String cauD, String correct) {
        this.CauHoi = cauHoi;
        this.CauA = cauA;
        this.CauB = cauB;
        this.CauC = cauC;
        this.CauD = cauD;
        this.Correct = correct;
    }


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCauHoi() {
        return CauHoi;
    }

    public void setCauHoi(String cauHoi) {
        CauHoi = cauHoi;
    }

    public String getCauA() {
        return CauA;
    }

    public void setCauA(String cauA) {
        CauA = cauA;
    }

    public String getCauB() {
        return CauB;
    }

    public void setCauB(String cauB) {
        CauB = cauB;
    }

    public String getCauC() {
        return CauC;
    }

    public void setCauC(String cauC) {
        CauC = cauC;
    }

    public String getCauD() {
        return CauD;
    }

    public void setCauD(String cauD) {
        CauD = cauD;
    }

    public String getCorrect() {
        return Correct;
    }

    public void setCorrect(String correct) {
        Correct = correct;
    }

    public String getMaBai() {
        return MaBai;
    }

    public void setMaBai(String maBai) {
        MaBai = maBai;
    }
}
