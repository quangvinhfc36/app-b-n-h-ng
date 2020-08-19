package com.t3h.appbanhang.model;

public class LoaiSanPham {
    public int Id;
    public String TenLoaisp;
    public String Hinhloaisp;

    public LoaiSanPham(int id, String tenLoaisp, String hinhloaisp) {
        Id = id;
        TenLoaisp = tenLoaisp;
        Hinhloaisp = hinhloaisp;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTenLoaisp() {
        return TenLoaisp;
    }

    public void setTenLoaisp(String tenLoaisp) {
        TenLoaisp = tenLoaisp;
    }

    public String getHinhloaisp() {
        return Hinhloaisp;
    }

    public void setHinhloaisp(String hinhloaisp) {
        Hinhloaisp = hinhloaisp;
    }
}
