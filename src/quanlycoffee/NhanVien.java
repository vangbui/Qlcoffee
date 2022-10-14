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
public class NhanVien {
    int SoTaiKhoan;
    String  MatKhau, TenNV, GioiTinh, NgaySinh, SoDT, Email, DiaChi; 
    public NhanVien(){
        
    }
    public NhanVien(int  SoTaiKhoan, String MatKhau, String TenNV, String GioiTinh, String NgaySinh, String SoDT, String Email, String DiaChi) {
        this.SoTaiKhoan = SoTaiKhoan;
        this.MatKhau = MatKhau;
        this.TenNV = TenNV;
        this.GioiTinh = GioiTinh;
        this.NgaySinh = NgaySinh;
        this.SoDT = SoDT;
        this.Email = Email;
        this.DiaChi = DiaChi;
    }

    public NhanVien(String MatKhau, String TenNV, String GioiTinh, String NgaySinh, String SoDT, String Email, String DiaChi) {
        this.MatKhau = MatKhau;
        this.TenNV = TenNV;
        this.GioiTinh = GioiTinh;
        this.NgaySinh = NgaySinh;
        this.SoDT = SoDT;
        this.Email = Email;
        this.DiaChi = DiaChi;
    }

    public int getSoTaiKhoan() {
        return SoTaiKhoan;
    }

    public void setSoTaiKhoan(int SoTaiKhoan) {
        this.SoTaiKhoan = SoTaiKhoan;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String MatKhau) {
        this.MatKhau = MatKhau;
    }

    public String getTenNV() {
        return TenNV;
    }

    public void setTenNV(String TenNV) {
        this.TenNV = TenNV;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String GioiTinh) {
        this.GioiTinh = GioiTinh;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(String NgaySinh) {
        this.NgaySinh = NgaySinh;
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

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }
    

   
    
    
}
