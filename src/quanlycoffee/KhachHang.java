/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlycoffee;

/**
 *
 * @author Admin
 */
public class KhachHang {
    int MaKH;
    String CMND, TenKH, NgayDangKy, SoDT, Email, GiamGia;
    public KhachHang(){
        
    }

    public KhachHang(int MaKH, String CMND, String TenKH, String NgayDangKy, String SoDT, String Email, String GiamGia) {
        this.MaKH = MaKH;
        this.CMND = CMND;
        this.TenKH = TenKH;
        this.NgayDangKy = NgayDangKy;
        this.SoDT = SoDT;
        this.Email = Email;
        this.GiamGia = GiamGia;
    }

    public KhachHang(String CMND, String TenKH, String NgayDangKy, String SoDT, String Email, String GiamGia) {
        this.CMND = CMND;
        this.TenKH = TenKH;
        this.NgayDangKy = NgayDangKy;
        this.SoDT = SoDT;
        this.Email = Email;
        this.GiamGia = GiamGia;
    }

    public int getMaKH() {
        return MaKH;
    }

    public void setMaKH(int MaKH) {
        this.MaKH = MaKH;
    }

    public String getCMND() {
        return CMND;
    }

    public void setCMND(String CMND) {
        this.CMND = CMND;
    }

    public String getTenKH() {
        return TenKH;
    }

    public void setTenKH(String TenKH) {
        this.TenKH = TenKH;
    }

    public String getNgayDangKy() {
        return NgayDangKy;
    }

    public void setNgayDangKy(String NgayDangKy) {
        this.NgayDangKy = NgayDangKy;
    }

    public String getSoDT() {
        return SoDT;
    }

    public void setSoDT(String SoDT) {
        this.SoDT = SoDT;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getGiamGia() {
        return GiamGia;
    }

    public void setGiamGia(String GiamGia) {
        this.GiamGia = GiamGia;
    }
    
}
