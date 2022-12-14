/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import static sun.net.www.MimeTable.loadTable;

/**
 *
 * @author Admin
 */
public class DoanhThu extends javax.swing.JFrame {
        ResultSet rs;
        Connection con;
        PreparedStatement ps;
        DefaultTableModel tblModel;
        Vector row;
        SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        NumberFormat formatter = new DecimalFormat("#,###");
    /**
     * Creates new form DoanhThu
     */
    public DoanhThu() {
        initComponents();
        tblModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblModel.addColumn("Mã Doanh Thu");
        tblModel.addColumn("Ngày/tháng/năm");
        tblModel.addColumn("Tiền thu (VNĐ)");
        j_doanhthu.setModel(tblModel);
        loadTable();
        bt_IN.setEnabled(false);
    }
    public void loadTable() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/qlqcf", "root", "2509");
            ps = con.prepareStatement("select * from qlqcf.doanhthu order by MaDT DESC");
            rs = ps.executeQuery();
            while (rs.next()) {
                row = new Vector();
                row.add(rs.getString(1));
                row.add(rs.getString(2));
                row.add(formatter.format(rs.getInt(3)));
                tblModel.addRow(row);
            }
            j_doanhthu.setModel(tblModel);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi 101:: Không thể kết nối đến máy chủ");
        }
        int line = j_doanhthu.getRowCount();
        int tong = 0;
        for (int i = 0; i < line; i++) {
            String price = (String) j_doanhthu.getValueAt(i, 2);
            tong += Integer.parseInt(price.replaceAll(",", ""));
        }
        lbTong.setText(formatter.format(tong) + " VNĐ");
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tf_ngay = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        j_doanhthu = new javax.swing.JTable();
        lbTong = new javax.swing.JLabel();
        bt_lm = new javax.swing.JButton();
        bt_IN = new javax.swing.JButton();
        lbLoi = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Thống Kê Doanh Thu");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 153, 0));
        jLabel2.setText("Tổng Số Tiền:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(153, 153, 0));
        jLabel3.setText("Tìm Kiếm Theo Ngày: ");

        tf_ngay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_ngayActionPerformed(evt);
            }
        });

        j_doanhthu.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(j_doanhthu);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(182, 182, 182))
        );

        lbTong.setText("0:VNĐ");

        bt_lm.setForeground(new java.awt.Color(204, 0, 0));
        bt_lm.setText("Làm Mới");
        bt_lm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_lmActionPerformed(evt);
            }
        });

        bt_IN.setForeground(new java.awt.Color(204, 0, 0));
        bt_IN.setText("IN");
        bt_IN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_INActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(lbTong)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tf_ngay, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(lbLoi, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(44, 44, 44)
                                .addComponent(bt_lm))
                            .addComponent(bt_IN, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(tf_ngay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbLoi, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(bt_lm)
                        .addGap(26, 26, 26)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lbTong)
                    .addComponent(bt_IN))
                .addGap(24, 24, 24)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(60, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tf_ngayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_ngayActionPerformed
        while (true) {
            if (!tf_ngay.getText().trim().matches("([0-9]{0,2}/)?([0-9]{0,2}/)?[0-9]{4}")) {
                lbLoi.setText("Nhập đúng định dạng để tìm kiếm: dd/MM/yyyy, MM/yyyy hoặc yyyy.");
                bt_IN.setEnabled(false);
                return;
            } else {
                lbLoi.setText("");
                break;
            }
        }
        tblModel.getDataVector().removeAllElements();
        try {
            ps = con.prepareStatement("select * from qlqcf.doanhthu where NgayNhap like ?");
            ps.setString(1, "%" + (String) tf_ngay.getText().trim());
            rs = ps.executeQuery();
            if (rs.next()) {
                ps = con.prepareStatement("select *  from qlqcf.doanhthu where NgayNhap like ?");
                ps.setString(1, "%" + (String) tf_ngay.getText().trim());
                rs = ps.executeQuery();
                while (rs.next()) {
                    row = new Vector();
                    row.add(rs.getString(1));
                    row.add(rs.getString(2));
                    row.add(formatter.format(rs.getInt(3)));
                    tblModel.addRow(row);
                }
                j_doanhthu.setModel(tblModel);
                bt_IN.setEnabled(true);
            } else {
                lbLoi.setText("Không tìm thấy dữ liệu!");
                j_doanhthu.removeAll();
                bt_IN.setEnabled(false);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi 101:: Không thể kết nối đến máy chủ");
        }
        int line = j_doanhthu.getRowCount();
        int tong = 0;
        for (int i = 0; i < line; i++) {
            String price = (String) j_doanhthu.getValueAt(i, 2);
            tong += Integer.parseInt(price.replaceAll(",", ""));
        }
        lbTong.setText(formatter.format(tong) + " VNĐ");
    }//GEN-LAST:event_tf_ngayActionPerformed

    private void bt_lmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_lmActionPerformed
        tblModel.getDataVector().removeAllElements();
        tf_ngay.setText(" ");
        loadTable();
        bt_IN.setEnabled(false);
    }//GEN-LAST:event_bt_lmActionPerformed

    private void bt_INActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_INActionPerformed
      File file = new File("Doanhthu.txt");
        file.delete();
        //Viết vào file txt
        try {
            Writer b = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Doanhthu.txt"), "UTF8"));
            String a;
            Date now = new Date();

            b.write("THE WIND\r\n\r\n");
            b.write("Địa chỉ: 32 Thanh Xuân, Thủ Dầu Một, Bình Dương\r\n");
            b.write("SĐT: 024876209\r\n");
            b.write("Thời gian: " + ft.format(now) + "\r\n\r\n");
            if (tf_ngay.getText().trim().matches("[0-9]{0,2}/[0-9]{0,2}/[0-9]{4}")) {
                a = "ngày " + tf_ngay.getText().trim();
                b.write("\tBẢNG THỐNG KÊ DOANH THU (theo " + a + ")\r\n\r\n");
            } else if (tf_ngay.getText().trim().matches("[0-9]{0,2}/[0-9]{4}")) {
                a = "tháng " + tf_ngay.getText().trim();
                b.write("\tBẢNG THỐNG KÊ DOANH THU (theo " + a + ")\r\n\r\n");
            } else if (tf_ngay.getText().trim().matches("[0-9]{4}")) {
                a = "năm " + tf_ngay.getText().trim();
                b.write("\tBẢNG THỐNG KÊ DOANH THU (theo " + a + ")\r\n\r\n");
            }
            b.write("\t---------------------------------\r\n");
            b.write("\tID\tNgày thu\tSố tiền\r\n");
            b.write("\t---------------------------------\r\n");
            int line = j_doanhthu.getRowCount();
            for (int i = 0; i < line; i++) {
                String id = (String) j_doanhthu.getValueAt(i, 0);
                String date = (String)j_doanhthu.getValueAt(i, 1);
                String money = (String)j_doanhthu.getValueAt(i, 2);
                b.write("\t" + id + "\t" + date + "\t" + money + "\r\n");
            }
            b.write("\t---------------------------------\r\n");
            b.write("\tTổng tiền:\t\t" + lbTong.getText().trim() + "\r\n\r\n\r\n");
            b.write("Người lập (Ký và ghi rõ họ tên)");
            b.close();
        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        //Mở file txt
        Runtime run = Runtime.getRuntime();
        try {
            run.exec("notepad Doanhthu.txt");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e);
        }
       bt_lmActionPerformed(evt);
    }//GEN-LAST:event_bt_INActionPerformed

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
            java.util.logging.Logger.getLogger(DoanhThu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DoanhThu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DoanhThu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DoanhThu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DoanhThu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_IN;
    private javax.swing.JButton bt_lm;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable j_doanhthu;
    private javax.swing.JLabel lbLoi;
    private javax.swing.JLabel lbTong;
    private javax.swing.JTextField tf_ngay;
    // End of variables declaration//GEN-END:variables
}
