package com.example.ungdunghoctap;

import java.io.Serializable;

public class User implements Serializable {

    private String MaND;
    private String MatKhau;
    private String Email1;
    private String Email2;
    private int DienThoai1;
    private int DienThoai2;

    public User(String MaND, String MatKhau, String Email2, int DienThoai1) {
        this.MaND = MaND;
        this.MatKhau = MatKhau;
        this.Email2 = Email2;
        this.DienThoai1 = DienThoai1;
    }

    public User(String MaND, String MatKhau, String Email1, String Email2, int DienThoai1, int DienThoai2) {
        this.MaND = MaND;
        this.MatKhau = MatKhau;
        this.Email1 = Email1;
        this.Email2 = Email2;
        this.DienThoai1 = DienThoai1;
        this.DienThoai2 = DienThoai2;
    }

    public User(String MaND, String MatKhau) {
        this.MaND = MaND;
        this.MatKhau = MatKhau;
    }

    public User() {

    }

    public String getMaND() {
        return MaND;
    }

    public void setMaND(String maND) {
        MaND = maND;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }

    public String getEmail1() {
        return Email1;
    }

    public void setEmail1(String email1) {
        Email1 = email1;
    }

    public String getEmail2() {
        return Email2;
    }

    public void setEmail2(String email2) {
        Email2 = email2;
    }

    public int getDienThoai1() {
        return DienThoai1;
    }

    public void setDienThoai1(int dienThoai1) {
        DienThoai1 = dienThoai1;
    }

    public int getDienThoai2() {
        return DienThoai2;
    }

    public void setDienThoai2(int dienThoai2) {
        DienThoai2 = dienThoai2;
    }

}
