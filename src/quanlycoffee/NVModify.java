/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlycoffee;

import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Admin
 */
public class NVModify {
    public static List<NhanVien> findAll(){
        List<NhanVien>nhanvienList = new ArrayList<>();
       Connection connection = null;
       Statement  statement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/qlqcf", "root", "2509");

            String sql = "select * from qlqcf.nhanvien";
            statement=connection.createStatement();
            ResultSet resultSet= statement.executeQuery(sql);
            while(resultSet.next()){
                NhanVien nv= new NhanVien(resultSet.getInt("SoTaiKhoan"),
                    resultSet.getString("MatKhau"),
                    resultSet.getString("TenNV"),
                    resultSet.getString("GioiTinh"),
                    resultSet.getString("NgaySinh"),
                    resultSet.getString("SoDT"),
                    resultSet.getString("Email"),
                    resultSet.getString("DiaChi"));
                nhanvienList.add(nv);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NVModify.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            if(statement != null){
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NVModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NVModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return nhanvienList;
    }
    
    
    public static void insert (NhanVien nv){
        Connection connection = null;
       PreparedStatement statement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/qlqcf", "root", "2509");

            String sql = "insert into qlqcf.nhanvien( MatKhau, TenNV, GioiTinh, NgaySinh, SoDT, Email, DiaChi) values( ?, ?, ?, ?, ?, ?, ? )";
            statement= connection.prepareCall(sql);
            statement.setString(1, nv.getMatKhau());
            statement.setString(2, nv.getTenNV());
            statement.setString(3, nv.getGioiTinh());
            statement.setString(4, nv.getNgaySinh());
            statement.setString(5, nv.getSoDT());
            statement.setString(6, nv.getEmail());
            statement.setString(7, nv.getDiaChi());
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(NVModify.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            if(statement != null){
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NVModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NVModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public static void update(NhanVien nv){
        Connection connection = null;
       PreparedStatement statement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/qlqcf", "root", "2509");

            String sql = " update qlqcf.nhanvien set MatKhau=?, TenNV=?, GioiTinh=?, NgaySinh=?,SoDT=?,Email=?, DiaChi=? where SoTaiKhoan=? "; 
            statement= connection.prepareCall(sql);
           
            statement.setString(1, nv.getMatKhau());
            statement.setString(2, nv.getTenNV());
            statement.setString(3, nv.getGioiTinh());
            statement.setString(4, nv.getNgaySinh());
            statement.setString(5, nv.getSoDT());
            statement.setString(6, nv.getEmail());
            statement.setString(7, nv.getDiaChi());
            statement.setInt(8, nv.getSoTaiKhoan());
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(NVModify.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            if(statement != null){
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NVModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NVModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    public static void delete( int SoTaiKhoan){
        Connection connection = null;
       PreparedStatement statement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/qlqcf", "root", "2509");

            String sql = "delete from qlqcf.nhanvien where SoTaiKhoan= ? "; 
            statement= connection.prepareCall(sql);
            statement.setInt(1, SoTaiKhoan);
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(NVModify.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            if(statement != null){
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NVModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NVModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public static List<NhanVien> findBYTenNV(String TenNV){
        List<NhanVien>nhanvienList = new ArrayList<>();
       Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/qlqcf", "root", "2509");

            String sql = "select * from qlqcf.nhanvien where TenNV = '"+ TenNV + "'";
            statement=connection.prepareCall(sql);
             //statement.setString(1, );
            ResultSet resultSet= statement.executeQuery(sql);
            while(resultSet.next()){
                NhanVien nv= new NhanVien(resultSet.getInt("SoTaiKhoan"),
                    resultSet.getString("MatKhau"),
                    resultSet.getString("TenNV"),
                    resultSet.getString("GioiTinh"),
                    resultSet.getString("NgaySinh"),
                    resultSet.getString("SoDT"),
                    resultSet.getString("Email"),
                    resultSet.getString("DiaChi"));
                nhanvienList.add(nv);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NVModify.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            if(statement != null){
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NVModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NVModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return nhanvienList;
    }
    

    
}
