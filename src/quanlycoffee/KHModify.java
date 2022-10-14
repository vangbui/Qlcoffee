/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlycoffee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class KHModify {
    public static List<KhachHang> findAll(){
         List<KhachHang> khachhangList = new ArrayList<>();
       Connection connection = null;
       Statement  statement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/qlqcf", "root", "2509");

            String sql = "select * from qlqcf.khachhang";
            statement=connection.createStatement();
            ResultSet resultSet= statement.executeQuery(sql);
            while(resultSet.next()){
                KhachHang kh = new KhachHang (resultSet.getInt("MaKH"),
                    resultSet.getString("CMND"),
                    resultSet.getString("TenKH"),
                    resultSet.getString("NgayDangKy"),
                    resultSet.getString("SoDT"),
                    resultSet.getString("Email"),
                    resultSet.getString("GiamGia"));
                khachhangList.add(kh);
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
        return khachhangList;
    }
    
     
    public static void insert (KhachHang kh){
        Connection connection = null;
       PreparedStatement statement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/qlqcf", "root", "2509");

            String sql = "insert into qlqcf.khachhang( CMND, TenKH,  NgayDangKy, SoDT, Email, GiamGia) values(  ?, ?, ?, ?, ?, ? )";
            statement= connection.prepareCall(sql);
            statement.setString(1, kh.getCMND());
            statement.setString(2, kh.getTenKH());
            statement.setString(3, kh.getNgayDangKy());
            statement.setString(4, kh.getSoDT());
            statement.setString(5, kh.getEmail());
            statement.setString(6, kh.getGiamGia());
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(KHModify.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            if(statement != null){
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KHModify.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public static void update(KhachHang kh){
        Connection connection = null;
       PreparedStatement statement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/qlqcf", "root", "2509");

            String sql = " update qlqcf.khachhang set CMND=?, TenKH=?, NgayDangKy=?,SoDT=?,Email=?, GiamGia=? where MaKH=? "; 
            statement= connection.prepareCall(sql);
           
            statement.setString(1, kh.getCMND());
            statement.setString(2, kh.getTenKH());
            statement.setString(3, kh.getNgayDangKy());
            statement.setString(4, kh.getSoDT());
            statement.setString(5, kh.getEmail());
            statement.setString(6, kh.getGiamGia());
            statement.setInt(7, kh.getMaKH());
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(KHModify.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            if(statement != null){
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KHModify.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public static void delete( int MaKH){
        Connection connection = null;
       PreparedStatement statement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/qlqcf", "root", "2509");

            String sql = "delete from qlqcf.khachhang where MaKH= ? "; 
            statement= connection.prepareCall(sql);
            statement.setInt(1, MaKH);
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(KHModify.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            if(statement != null){
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KHModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KHModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public static List<KhachHang> findByTenKH(String TenKH){
        List<KhachHang>khachhangList = new ArrayList<>();
       Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/qlqcf", "root", "2509");

            String sql = "select * from qlqcf.khachhang where TenKH = '"+ TenKH + "'";
            statement=connection.prepareCall(sql);
             //statement.setString(1, );
            ResultSet resultSet= statement.executeQuery(sql);
            while(resultSet.next()){
                KhachHang kh = new KhachHang (resultSet.getInt("MaKH"),
                    resultSet.getString("CMND"),
                    resultSet.getString("TenKH"),
                    resultSet.getString("NgayDangKy"),
                    resultSet.getString("SoDT"),
                    resultSet.getString("Email"),
                    resultSet.getString("GiamGia"));
                khachhangList.add(kh);
            }
        } catch (SQLException ex) {
            Logger.getLogger(KHModify.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            if(statement != null){
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(KHModify.class.getName()).log(Level.SEVERE, null, ex);
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
        return khachhangList;
    }

}
