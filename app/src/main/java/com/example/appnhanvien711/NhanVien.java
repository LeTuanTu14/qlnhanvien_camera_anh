package com.example.appnhanvien711;

import android.net.Uri;

import java.net.URI;
import java.net.URL;

public class NhanVien {
    private int maso;
    private String hoten;
    private String gioitinh;
    private String donvi;
    private Uri url;

    public int getMaso() {
        return maso;
    }

    public String getHoten() {
        return hoten;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public String getDonvi() {
        return donvi;
    }

    public void setMaso(int maso) {
        this.maso = maso;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public void setDonvi(String donvi) {
        this.donvi = donvi;
    }

    public Uri getUrl() {
        return url;
    }

    public void setUrl(Uri url) {
        this.url = url;
    }

    public NhanVien(int maso, String hoten, String gioitinh, String donvi, Uri url) {
        this.maso = maso;
        this.hoten = hoten;
        this.gioitinh = gioitinh;
        this.donvi = donvi;
        this.url = url;
    }

    public NhanVien(int maso, String hoten, String gioitinh, String donvi) {
        this.maso = maso;
        this.hoten = hoten;
        this.gioitinh = gioitinh;
        this.donvi = donvi;
    }

    public NhanVien() {
    }

    @Override
    public String toString() {
        return "NhanVien{" +
                maso +
                ", " + hoten +
                ", " + gioitinh +
                ", " + donvi +
                '}';
    }
}
