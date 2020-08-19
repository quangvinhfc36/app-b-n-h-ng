package com.t3h.appbanhang.model;

import java.io.Serializable;

public class SanPham implements Serializable {
    public int Id;
    public String Tensp;
    public Integer Giasp;
    public String Hinhanhsp;
    public String Motasp;
    public int IDsanpham;

    public SanPham(int id, String tensp, Integer giasp, String hinhanhsp, String motasp, int IDsanpham) {
        Id = id;
        Tensp = tensp;
        Giasp = giasp;
        Hinhanhsp = hinhanhsp;
        Motasp = motasp;
        this.IDsanpham = IDsanpham;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTensp() {
        return Tensp;
    }

    public void setTensp(String tensp) {
        Tensp = tensp;
    }

    public Integer getGiasp() {
        return Giasp;
    }

    public void setGiasp(Integer giasp) {
        Giasp = giasp;
    }

    public String getHinhanhsp() {
        return Hinhanhsp;
    }

    public void setHinhanhsp(String hinhanhsp) {
        Hinhanhsp = hinhanhsp;
    }

    public String getMotasp() {
        return Motasp;
    }

    public void setMotasp(String motasp) {
        Motasp = motasp;
    }

    public int getIDsanpham() {
        return IDsanpham;
    }

    public void setIDsanpham(int IDsanpham) {
        this.IDsanpham = IDsanpham;
    }
}
