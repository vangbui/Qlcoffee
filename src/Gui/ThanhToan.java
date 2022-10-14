/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import static java.lang.Thread.sleep;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class ThanhToan extends javax.swing.JFrame {
ResultSet rs, rsEmp, rsIDOrder;
    Vector vec, rowHis;
    PreparedStatement ps;
    DefaultComboBoxModel cbModel;
    DefaultTableModel tblModel, tblModelHis;
    Connection con ;
    SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
    SimpleDateFormat ftnow = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat ftNgay = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("#,###");
    List<String> lsTenSP = new ArrayList<>();
    /**
     * Creates new form ThanhToan
     */
    public ThanhToan() {
        initComponents();
        clock();
        PanelOnOff(false);
        setText(false);
        tf_mahd.setEnabled(false);
        bt_in.setEnabled(false);
        //tf_tennv.setText(TenNV);
        sp_soluong.setValue(1);
        
        tblModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblModel.addColumn("Mã");
        tblModel.addColumn("Tên sản phẩm");
        tblModel.addColumn("Nhóm");
        tblModel.addColumn("Kích thước");
        tblModel.addColumn("Đơn giá (VNĐ)");
        tblModel.addColumn("Số lượng (ly)");
        tblModel.addColumn("Thành tiền (VNĐ)");
        j_thanhtoan.setModel(tblModel);
        ReloadCombobox();
        cb_sp();
    }
    void cb_sp(){
        lsTenSP.add("CAPPUCCINO");
        lsTenSP.add("Cam ép");
        lsTenSP.add("Cà phê đá");
        lsTenSP.add("Cà phê sửa đá");
        lsTenSP.add("ESPRESSO MILK");
        lsTenSP.add("Sinh tố thập cẩm");
        lsTenSP.add("Trà đào");
        cb_tensp.removeAllItems();
        for(String tensp: lsTenSP){
            cb_tensp.addItem(tensp);
        }
    }
    public void clock() {
        Thread clock = new Thread() {
            public void run() {
                try {
                    while (true) {
                        Date t = new Date();
                        lb_time.setText(ft.format(t));
                        sleep(1000);
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        clock.start();
    }
    public void intomoney() {
        int price, totalprice = 0;
        int count = j_thanhtoan.getRowCount();
        for (int i = 0; i < count; i++) {
            price = Integer.parseInt((String)j_thanhtoan.getValueAt(i, 6));
            totalprice += price;
        }
        tf_tong.setText(formatter.format(totalprice));
    }
    public void LoadTblFromDB() {
        try {
            String url = "Select * from qlqcf.sanpham Join loaisanpham on sanpham.MaLSP=loaisanpham.MaLSP where sanpham.TenSP=? and loaisanpham.KichThuoc=?";
            ps = con.prepareStatement(url);
            ps.setString(1, (String) cb_tensp.getSelectedItem());
            ps.setString(2, (String) cb_size.getSelectedItem());
            rs = ps.executeQuery();
            int price, quantity, into;
            if (rs.next()) {
                vec = new Vector();
                price = Integer.parseInt(rs.getString("GíaSP"));
                quantity = Integer.parseInt(sp_soluong.getValue().toString());
                into = price * quantity;
                vec.add(rs.getString("MaSP"));
                vec.add(rs.getString("TenSP"));
                vec.add(rs.getString("TenLSP"));
                vec.add(rs.getString("KichThuoc"));
                vec.add(rs.getString("GíaSP"));
                vec.add(sp_soluong.getValue());
                vec.add(String.valueOf(into));
                tblModel.addRow(vec);
            } else {
                JOptionPane.showMessageDialog(null, "Lỗi:: Không tìm thấy sản phẩm");
                 cb_tensp.grabFocus();
            }
            j_thanhtoan.setModel(tblModel);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi 101:: Không thể kết nối đến máy chủ");
        }
    }
    public void setText(boolean b) {
        tf_tienkd.setEnabled(b);
    }
    public void UpdatetxtDis1() {
        int Dis;
        NumberFormat formatter = new DecimalFormat("#,###");
        //tính Discount
        String Order = tf_tong.getText().replaceAll(",", "");
        Dis = (Integer.parseInt(tf_chietkhau.getText()) * Integer.parseInt(Order)) / 100;
        tf_ck1.setText(formatter.format(Dis));
        //tính total
        int total = Integer.parseInt(Order) - Dis;
        tf_thanhtien.setText(formatter.format(total));
    }
    public void ReloadCombobox() {
        cb_ctkm.removeAllItems();
        cb_ctkm.addItem("Không có");
        cb_ctkm.addItem("Khách hàng VIP");
        try {
            Date now = new Date();
            ps = con.prepareStatement("select * from qlqcf.kuyenmai where NgayGiamGia <= ? and NgayKetThuc >= ?");
            ps.setString(1, ftnow.format(now));
            ps.setString(2, ftnow.format(now));
            rs = ps.executeQuery();
            while (rs.next()) {
                cb_ctkm.addItem(rs.getString(2));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi 101:: Không thể kết nối đến máy chủ");
        }
    }
    public void PanelOnOff(boolean b) {
        lb_nhap.setVisible(b);
        tf_nhapma.setVisible(b);
        lb_loi.setVisible(b);
        pn_thongtin.setVisible(b);
    }
     public void PressPrintandSave(String Name) {
        int line = j_thanhtoan.getRowCount();
        //Thêm từng value vào trong database
        try {
            ps = con.prepareStatement("Insert into qlqcf.dathang values(?,convert(varchar(20),getdate(),103),convert(varchar(20),getdate(),108),?)");
            ps.setString(1, tf_mahd.getText());
            ps.setString(2, Name);
            ps.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi 101:: Không thể kết nối đến máy chủ");
        }
        for (int i = 0; i < line; i++) {
            String IDProduct = (String) tblModel.getValueAt(i, 0);
            String quantity = String.valueOf(tblModel.getValueAt(i, 5));
            try {
                String in = "Insert into qlqcf.chitietdathang values(?,?,?,?,?)";
                ps = con.prepareStatement(in);
                ps.setString(1, tf_mahd.getText());
                ps.setString(2, IDProduct);
                if (cb_ctkm.getSelectedItem().equals("Khách hàng VIP")) {
                    ps.setString(3, tf_makh.getText());
                } else {
                    ps.setString(3, "Khách vãng lai");
                }
                ps.setInt(4, Integer.parseInt(quantity));
                ps.setString(5, (String) cb_ctkm.getSelectedItem());
                ps.executeUpdate();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi 101:: Không thể kết nối đến máy chủ");
            }
        }
        //kiểm tra số tiền ngày hôm nay và set lại giá trị
        String pay = tf_thanhtien.getText().replaceAll(",", "");
        try {
            ps = con.prepareStatement("SELECT * from qlqcf.doanhthu where NgayNhap=convert(varchar(20),getdate(),103)");
            rs = ps.executeQuery();
            if (rs.next()) {
                int money1 = Integer.parseInt(rs.getString("TongTien"));
                int money2 = money1 + Integer.parseInt(pay);
                ps = con.prepareStatement("update qlqcf.doanhthu set TongTien=" + money2 + " where NgayNhap=convert(varchar(20),getdate(),103)");
                ps.executeUpdate();
            } else {
                ps = con.prepareStatement("insert into qlqcf.doanhthu values(convert(varchar(20),getdate(),103)," + pay + ")");
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi 101:: Không thể kết nối đến máy chủ");
        }
        //Viết vào file txt
        int guest = Integer.parseInt(tf_tienkd.getText());
        try {
            Date now = new Date();
            Writer bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("History//" + tf_mahd.getText().trim() + ".txt"), "UTF8"));
            bw.write("\t\t\tTHE WIND COFFEE\r\n\r\n");
            bw.write("\t\t590 Địa chỉ: 32 Thanh Xuân, Thủ Dầu Một, Bình Dương\r\n");
            bw.write("\t\t\tSĐT:  024876209\r\n\r\n");
            bw.write("\t\t\tHÓA ĐƠN BÁN HÀNG\r\n\r\n");
            bw.write("Mã hóa đơn: " + tf_mahd.getText() + "\r\n");
            bw.write("Thời gian: " + ft.format(now) + "\r\n");
            bw.write("NHÂN VIÊN: " + tf_tennv.getText() + "\r\n");
            bw.write("------------------------------------------------------------\r\n");
            bw.write("Mã\tKích thước\tSố lượng\tĐơn giá\tThành tiền\r\n");
            bw.write("-----------------------------------------------------------\r\n");
            
            int quantotal = 0;
            for (int i = 0; i < line; i++) {
                String id = (String) tblModel.getValueAt(i, 0);
                String name = (String) tblModel.getValueAt(i, 1);
                String size = (String) tblModel.getValueAt(i, 3);
                String price = String.valueOf(tblModel.getValueAt(i, 4));
                String quantity = String.valueOf(tblModel.getValueAt(i, 5));
                String intomoney = String.valueOf(tblModel.getValueAt(i, 6));
                bw.write((i + 1) + ". " + name + "\r\n");
                bw.write(id + "\t" + size + "\t\t" + quantity + "\t\t" + price + "\t" + intomoney + "\r\n\r\n");
                quantotal += Integer.parseInt(quantity);
            }
            bw.write("------------------------------------------------------------\r\n");
            bw.write("Tổng cộng:\t\t" + quantotal + "\t\t\t" + tf_tong.getText() + " VNĐ\r\n");
            bw.write("\t\tChiết khấu:\t" + tf_chietkhau.getText() + "%\t\t-" + tf_ck1.getText() + " VNĐ\r\n");
            bw.write("\t\t--------------------------------------------\r\n");
            bw.write("\t\tThành tiền:\t\t\t" + tf_thanhtien.getText() + " VNĐ\r\n");
            bw.write("\t\t--------------------------------------------\r\n");
            bw.write("\t\tTiền khách đưa:\t\t\t" + formatter.format(guest) + " VNĐ\r\n");
            bw.write("\t\tTiền trả lại:\t\t\t" + tf_tientra.getText() + " VNĐ\r\n");
            bw.write("------------------------------------------------------------\r\n");
            bw.write("Chương trình khuyến mãi: ");
            if (cb_ctkm.getSelectedItem().equals("Không có")) {
                bw.write("Không có.\r\n");
            } else if (cb_ctkm.getSelectedItem().equals("Khách hàng VIP")) {
                bw.write("Thành viên quán.\r\n");
                bw.write("-----Thông tin thành viên-----\r\n");
                bw.write("Mã thẻ: " + tf_makh.getText() + "\r\n");
                bw.write("Tên thành viên: " + tf_tenkh.getText() + "\r\n");
                bw.write("Ngày đăng ký: " + tf_ngaydk.getText() + "\r\n");
                bw.write("Số lượng cũ: " + tf_soly.getText() + " ly.\r\n");
                bw.write("Số ly mới mua: " + quantotal + " ly.\r\n");
                bw.write("Chiết khấu (tính theo số lượng cũ): " + tf_giam.getText() + "\r\n");
            } else {
                bw.write((String) cb_ctkm.getSelectedItem() + "\r\n");
            }
            bw.write("------------------------------------------------------------\r\n");
            bw.write("Mật khẩu Wifi: motdentam\r\n");
            bw.write("---------------------CÁM ƠN QUÝ KHÁCH!----------------------");
            bw.close();
            //update chiết khấu vào bảng customer
            int ck = Integer.parseInt(tf_chietkhau.getText()) + quantotal;
            if (ck  < 5) {
                try {
                    ps = con.prepareStatement("Update qlqcf.khachhang set GianGia=? where MaKH=?");
                   
                    ps.setInt(1, 0);
                    ps.setString(2, tf_makh.getText());
                    ps.executeUpdate();
                } catch (Exception e) {
                }
            } else if (ck >= 5 && ck  < 10) {
                try {
                    ps = con.prepareStatement("Update qlqcf.khachhang set GianGia=? where MaKH=?");
                   
                    ps.setInt(1, 5);
                    ps.setString(3, tf_makh.getText());
                    ps.executeUpdate();
                } catch (Exception e) {
                }
            } else if (ck>= 10 && ck < 15) {
                try {
                    ps = con.prepareStatement("Update qlqcf.khachhang set GianGia=? where MaKH=?");
                    ps.setInt(1, 10);
                    ps.setString(2, tf_makh.getText());
                    ps.executeUpdate();
                } catch (Exception e) {
                }
            } else if (ck >= 15 && ck < 25) {
                try {
                    ps = con.prepareStatement("Update qlqcf.khachhang set GianGia=? where MaKH=?");
                    ps.setInt(1, 15);
                    ps.setString(2, tf_makh.getText());
                    ps.executeUpdate();
                } catch (Exception e) {
                }
            } 
        } catch (Exception e) {
        }
        //Mở file txt
        Runtime run = Runtime.getRuntime();
        try {
            run.exec("notepad History//" + tf_mahd.getText().trim() + ".txt");
        } catch (IOException e) {
        }

        // set lại bảng, combobox và textbox
        tblModel.getDataVector().removeAllElements();
        j_thanhtoan.revalidate();
        setText(false);
        tf_mahd.setEnabled(false);
        cb_ctkm.setSelectedIndex(0);
        tf_thanhtien.setText("0");
        tf_tong.setText("0");
        tf_tienkd.setText("0");
        tf_tientra.setText("0");
        ResetPnInfor();
        tf_nhapma.setText("");
        lb_loi.setText("");
        bt_in.setEnabled(false);
    }


    
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tf_chietkhau = new javax.swing.JTextField();
        tf_mahd = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        tf_tong = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        tf_tennv = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        tf_tientra = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        j_thanhtoan = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        tf_tienkd = new javax.swing.JTextField();
        tf_thanhtien = new javax.swing.JTextField();
        bt_in = new javax.swing.JButton();
        cb_tensp = new javax.swing.JComboBox<>();
        cb_size = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        lb_nhap = new javax.swing.JLabel();
        tf_nhapma = new javax.swing.JTextField();
        pn_thongtin = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        tf_makh = new javax.swing.JLabel();
        tf_tenkh = new javax.swing.JLabel();
        tf_ngaydk = new javax.swing.JLabel();
        tf_soly = new javax.swing.JLabel();
        tf_giam = new javax.swing.JLabel();
        cb_ctkm = new javax.swing.JComboBox<>();
        lb_loi = new javax.swing.JLabel();
        lb_ngaykm = new javax.swing.JLabel();
        sp_soluong = new javax.swing.JSpinner();
        tf_ck1 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        bt_them = new javax.swing.JButton();
        bt_xoa = new javax.swing.JButton();
        lb_time = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Thanh Toán"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 153));
        jLabel1.setText("Tổng Cộng:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 153));
        jLabel2.setText("Chiếc Khấu:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 102, 102));
        jLabel3.setText("Thành Tiền:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 153, 153));
        jLabel4.setText("Tiền Khách Đưa:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 153, 153));
        jLabel5.setText("Tiền Khách Trả Lại:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 153, 153));
        jLabel6.setText("Mã Hóa Đơn:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 153, 153));
        jLabel7.setText("Tên Sản Phẩm:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 153, 153));
        jLabel8.setText("Tên NV:");

        j_thanhtoan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(j_thanhtoan);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 153, 153));
        jLabel9.setText("Số Lượng:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 102, 102));
        jLabel10.setText("Kích Thước:");

        bt_in.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        bt_in.setForeground(new java.awt.Color(255, 0, 0));
        bt_in.setText("IN");
        bt_in.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_inActionPerformed(evt);
            }
        });

        cb_tensp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cb_size.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nhỏ ", "Vừa", "Lớn" }));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 0, 0));
        jLabel11.setText("Chương Trình Khuyến Mãi");

        lb_nhap.setText("Nhập Mã KH:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(153, 153, 0));
        jLabel13.setText("Thông Tin Khách Hàng");

        jLabel14.setText("MaKH:");

        jLabel15.setText("Họ Tên:");

        jLabel16.setText("Ngày Đăng Ký:");

        jLabel17.setText("Số Ly đã mua:");

        jLabel18.setText("Được Giảm :");

        tf_makh.setText("...");

        tf_tenkh.setText("...");

        tf_ngaydk.setText("...");

        tf_soly.setText("...");

        tf_giam.setText("...");

        javax.swing.GroupLayout pn_thongtinLayout = new javax.swing.GroupLayout(pn_thongtin);
        pn_thongtin.setLayout(pn_thongtinLayout);
        pn_thongtinLayout.setHorizontalGroup(
            pn_thongtinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_thongtinLayout.createSequentialGroup()
                .addGroup(pn_thongtinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_thongtinLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel14)
                        .addGap(18, 18, 18)
                        .addComponent(tf_makh))
                    .addGroup(pn_thongtinLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tf_tenkh))
                    .addGroup(pn_thongtinLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_ngaydk))
                    .addGroup(pn_thongtinLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tf_soly))
                    .addGroup(pn_thongtinLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_giam))
                    .addGroup(pn_thongtinLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pn_thongtinLayout.setVerticalGroup(
            pn_thongtinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_thongtinLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_thongtinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(tf_makh))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_thongtinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(tf_tenkh))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_thongtinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(tf_ngaydk))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_thongtinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(tf_soly))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pn_thongtinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(tf_giam))
                .addContainerGap())
        );

        cb_ctkm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cb_ctkm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_ctkmActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(cb_ctkm, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pn_thongtin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lb_nhap)
                                .addComponent(tf_nhapma, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                                .addComponent(lb_loi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(lb_ngaykm, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cb_ctkm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_ngaykm, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_nhap)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tf_nhapma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(lb_loi, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pn_thongtin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        tf_ck1.setText("0");

        jLabel12.setText("%");

        bt_them.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        bt_them.setForeground(new java.awt.Color(255, 0, 0));
        bt_them.setText("Thêm");
        bt_them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_themActionPerformed(evt);
            }
        });

        bt_xoa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        bt_xoa.setForeground(new java.awt.Color(255, 0, 51));
        bt_xoa.setText("Xóa");
        bt_xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_xoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tf_ck1))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel6)
                                .addComponent(jLabel7)
                                .addComponent(jLabel9)
                                .addComponent(jLabel10)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cb_size, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(sp_soluong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(63, 63, 63)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(57, 57, 57)
                                        .addComponent(tf_tong, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(tf_tennv, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                                        .addComponent(tf_tienkd)
                                        .addComponent(tf_tientra)
                                        .addComponent(tf_thanhtien))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(cb_tensp, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(tf_mahd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tf_chietkhau, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(63, 63, 63)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel3))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lb_time, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(143, 143, 143)
                .addComponent(bt_them, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(87, 87, 87)
                .addComponent(bt_xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97)
                .addComponent(bt_in, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(16, 16, 16)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(tf_tong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel7)
                                            .addComponent(cb_tensp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jLabel1)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(25, 25, 25)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(tf_chietkhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel2)
                                            .addComponent(tf_ck1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel12))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(tf_tennv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel8))
                                        .addGap(18, 18, 18)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tf_mahd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel4)
                                    .addComponent(tf_tienkd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(tf_tientra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tf_thanhtien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(sp_soluong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(11, 11, 11)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(cb_size, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(22, 22, 22)))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_in, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_them, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_time, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bt_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_themActionPerformed
       int line = j_thanhtoan.getRowCount();
        for (int i = 0; i < line; i++) {
            if (j_thanhtoan.getValueAt(i, 1).equals(cb_tensp.getSelectedItem()) && j_thanhtoan.getValueAt(i, 3).equals(cb_size.getSelectedItem())) {
                int quanCu = (int) j_thanhtoan.getValueAt(i, 5);
                int quanMoi = (int) sp_soluong.getValue();
                int quanTotal = quanCu + quanMoi;
               sp_soluong.setValue(quanTotal);
                tblModel.removeRow(i);
                break;
            }
        }
        LoadTblFromDB();
        cb_tensp.setSelectedIndex(-1);
        sp_soluong.setValue(1);
        cb_size.setSelectedIndex(0);
        intomoney();
        UpdatetxtDis1();
        if (j_thanhtoan.getRowCount() > 0) {
            setText(true);
        } else {
            setText(false);
        }
        bt_in.setEnabled(false);
        tf_mahd.setEnabled(false);
    }//GEN-LAST:event_bt_themActionPerformed

    private void bt_xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_xoaActionPerformed
        int line = j_thanhtoan.getSelectedRow();
        tblModel.removeRow(line);
        intomoney();
        UpdatetxtDis1();
        if (j_thanhtoan.getRowCount() > 0) {
            setText(true);
        } else {
            setText(false);
        }
        tf_thanhtien.setText("0");
        tf_tong.setText("0");
        tf_tienkd.setText("0");
        tf_tientra.setText("0");
        bt_in.setEnabled(false);
        tf_mahd.setEnabled(false);
    }//GEN-LAST:event_bt_xoaActionPerformed

    private void bt_inActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_inActionPerformed
       if (cb_ctkm.getSelectedItem().equals("Khách hàng VIP")) {
            while (true) {
                if (tf_nhapma.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "Mã thẻ VIP không được để trống!");
                    tf_nhapma.grabFocus();
                    return;
                } else if(!tf_nhapma.getText().trim().equals("") && !lb_loi.getText().equals("Thành công.")) {
                    JOptionPane.showMessageDialog(null, "Mã thẻ VIP chưa đúng, vui lòng nhập lại!");
                    tf_nhapma.grabFocus();
                    return;
                } else{
                    break;
                }
            }
        }
        while (true) {
            if (tf_mahd.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(null, "Mã hóa đơn không được để trống.");
               tf_mahd.grabFocus();
                return;
            } else if (! tf_mahd.getText().trim().matches("HD[0-9]{4}")) {
                JOptionPane.showMessageDialog(null, "Mã hóa đơn có dạng HDxxxx, trong đó xxxx là số nguyên.");
                 tf_mahd.grabFocus();
                return;
            } else {
                break;
            }
        }
        try {
            ps = con.prepareStatement("select dathang.MaHD from qlqcf.dathang where MaHD=?");
            ps.setString(1,  tf_mahd.getText().trim());
            rsIDOrder = ps.executeQuery();
            if (!rsIDOrder.next()) {
                ps = con.prepareStatement("Select * from qlqcf.nhanvien where TenNV=?");
                ps.setString(1, tf_tennv.getText());
                rsEmp = ps.executeQuery();
                if (rsEmp.next()) {
                    PressPrintandSave(rsEmp.getString(1));
                } else{
                    JOptionPane.showMessageDialog(null, "Tên nhân viên không tồn tại.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Mã hóa đơn đã tồn tại, vui lòng chọn mã mới.");
                 tf_mahd.grabFocus();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi 101:: Không thể kết nối đến máy chủ");
        }
    }//GEN-LAST:event_bt_inActionPerformed

    private void cb_ctkmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_ctkmActionPerformed
       bt_in.setEnabled(false);
        tf_mahd.setEnabled(false);
        if (cb_ctkm.getSelectedItem().equals("Không có")) {
            tf_chietkhau.setText("0");
            lb_ngaykm.setText("");
            PanelOnOff(false);
            UpdatetxtDis1();
            ResetPnInfor();
            tf_nhapma.setText("");
            lb_loi.setText("");
        } else if (cb_ctkm.getSelectedItem().equals("Khách hàng VIP")) {
            tf_chietkhau.setText("0");
            lb_ngaykm.setText("");
            UpdatetxtDis1();
            PanelOnOff(true);
        } else {
            PanelOnOff(false);
            ResetPnInfor();
            tf_nhapma.setText("");
            lb_loi.setText("");
            try {
                ps = con.prepareStatement("Select * from qlqcf.khuyenmai where TenKM=?");
                ps.setString(1, (String) cb_ctkm.getSelectedItem());
                rs = ps.executeQuery();
                if (rs.next()) {
                    Date start = ftnow.parse(rs.getString(4));
                    Date end = ftnow.parse(rs.getString(5));
                    tf_chietkhau.setText(rs.getString(3));
                    lb_ngaykm.setText(ftNgay.format(start) + " - " + ftNgay.format(end));
                    UpdatetxtDis1();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi 101:: Không thể kết nối đến máy chủ");
            } catch (ParseException ex) {
                Logger.getLogger(ThanhToan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_cb_ctkmActionPerformed
 private void ResetPnInfor() {
        tf_makh.setText("...");
        tf_tenkh.setText("...");
        tf_ngaydk.setText("...");
        tf_soly.setText("...");
        tf_giam.setText("...");
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ThanhToan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ThanhToan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ThanhToan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ThanhToan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ThanhToan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_in;
    private javax.swing.JButton bt_them;
    private javax.swing.JButton bt_xoa;
    private javax.swing.JComboBox<String> cb_ctkm;
    private javax.swing.JComboBox<String> cb_size;
    private javax.swing.JComboBox<String> cb_tensp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable j_thanhtoan;
    private javax.swing.JLabel lb_loi;
    private javax.swing.JLabel lb_ngaykm;
    private javax.swing.JLabel lb_nhap;
    private javax.swing.JLabel lb_time;
    private javax.swing.JPanel pn_thongtin;
    private javax.swing.JSpinner sp_soluong;
    private javax.swing.JTextField tf_chietkhau;
    private javax.swing.JTextField tf_ck1;
    private javax.swing.JLabel tf_giam;
    private javax.swing.JTextField tf_mahd;
    private javax.swing.JLabel tf_makh;
    private javax.swing.JLabel tf_ngaydk;
    private javax.swing.JTextField tf_nhapma;
    private javax.swing.JLabel tf_soly;
    private javax.swing.JLabel tf_tenkh;
    private javax.swing.JTextField tf_tennv;
    private javax.swing.JTextField tf_thanhtien;
    private javax.swing.JTextField tf_tienkd;
    private javax.swing.JTextField tf_tientra;
    private javax.swing.JTextField tf_tong;
    // End of variables declaration//GEN-END:variables
}
