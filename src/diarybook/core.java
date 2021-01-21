/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diarybook;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Maulana Alirridlo
 */
public class core {
    Connection con = conn.con;
    static Statement stm = conn.stm ;
    static ResultSet rs;
    static String sql;
    
//    USER
    
    public static Boolean isLogin = false;
    public static String username;
    public static String password;
    
    public static Boolean userLogin(String username, String password){
        try{
            sql = "Select * From user where username = '"+username+"'AND password = '"+password+"'";
            rs = stm.executeQuery(sql);
            if(rs.next()){
                isLogin = true;
                core.username = rs.getString("username");
                core.password = rs.getString("password");
            }else{
                JOptionPane.showMessageDialog(null, "Username atau Password Salah !!","Error",JOptionPane.ERROR_MESSAGE);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return isLogin;
    }
    
    public static void updateUser(String username, String password){
        try{
            sql = "update user set username = '"+username+"', password = '"+password+"'";
            stm.executeUpdate(sql);
            core.username = username;
            core.password = password;
            JOptionPane.showMessageDialog(null,"Data berhasil diperbarui","Success",JOptionPane.INFORMATION_MESSAGE);
        }catch(HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(null,"Simpan data gagal\n"+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
//    DIARY
    
    public static String showId;
    public static String showTitle;
    public static String showContent;
    public static Date showTanggal;
    
    public static void addDiary(String judul, String content, Date tanggal){
        try{
            sql = "insert into diary (title, content, tanggal) values('"+judul+"', '"+content+"', '"+tanggal+"')";
            stm.executeUpdate(sql);
            JOptionPane.showMessageDialog(null,"Data berhasil ditambahkan","Success",JOptionPane.INFORMATION_MESSAGE);
        }catch(HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(null,"Simpan data gagal\n"+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static DefaultTableModel getTabelDiary(){
        loadTableDiary();
        return modelTabelDiary;
    }
    
    static DefaultTableModel modelTabelDiary = new DefaultTableModel();
    static void loadTableDiary(){
        modelTabelDiary.setRowCount(0);
        modelTabelDiary.setColumnCount(0);
        modelTabelDiary.addColumn("ID");
        modelTabelDiary.addColumn("Tanggal");
        modelTabelDiary.addColumn("Title");
        tableGetDiary(); 
    }


    static void tableGetDiary(){
        try {
            String sql = "select id, tanggal, title from diary order by tanggal desc, id desc";
            rs=stm.executeQuery(sql);
            while(rs.next()){
                SimpleDateFormat outputFormat = new SimpleDateFormat("EEEEE, dd MMMMM yyyy");
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

                Date date = inputFormat.parse(rs.getString("tanggal"));
                String outputText = outputFormat.format(date);
                modelTabelDiary.addRow(new Object[]{rs.getString("ID"), outputText, rs.getString("title")});
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
    public static DefaultTableModel searchTabelDiary(Date tanggal){
        loadSearchDiary(tanggal);
        return modelTabelDiary;
    }
    
    static void loadSearchDiary(Date tanggal){
        modelTabelDiary.setRowCount(0);
        modelTabelDiary.setColumnCount(0);
        modelTabelDiary.addColumn("Tanggal");
        modelTabelDiary.addColumn("Title");
        tableSearchDiary(tanggal);
    }
    
    static void tableSearchDiary(Date tanggal){
        try {
            String sql = "select * from diary where tanggal = '"+tanggal+"' order by tanggal desc, id desc";
            System.out.println(sql);
            rs=stm.executeQuery(sql);
            while(rs.next()){
                SimpleDateFormat outputFormat = new SimpleDateFormat("EEEEE, dd MMMMM yyyy");
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

                Date date = inputFormat.parse(rs.getString("tanggal"));
                String outputText = outputFormat.format(date);
                modelTabelDiary.addRow(new Object[]{outputText,rs.getString("title")});
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
    public static void getDiaryById(String id){
        try {
            String sql = "select * from diary where ID = '"+id+"'";
            rs=stm.executeQuery(sql);
            while(rs.next()){
                showId = rs.getString("ID");
                showTitle = rs.getString("title");
                showContent = rs.getString("content");
                Date date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(rs.getString("tanggal"));
                showTanggal = date;
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
    public static void updateDiary(String id, String title, String content, Date tanggal){
        try{
            sql = "update diary set title = '"+title+"', content = '"+content+"', tanggal = '"+tanggal+"' where ID = '"+id+"'";
            stm.executeUpdate(sql);
            showId = id;
            showTitle = title;
            showContent = content;
            showTanggal = tanggal;
            JOptionPane.showMessageDialog(null,"Data berhasil diperbarui","Success",JOptionPane.INFORMATION_MESSAGE);
        }catch(HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(null,"Simpan data gagal\n"+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void deleteDiary(String id){
        try{
            sql = "delete from diary where ID = '"+id+"'";
            stm.executeUpdate(sql);
            showId = "";
            showTitle = "";
            showContent = "";
            showTanggal = null;
            JOptionPane.showMessageDialog(null,"Data berhasil dihapus","Success",JOptionPane.INFORMATION_MESSAGE);
        }catch(HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(null,"Simpan data gagal\n"+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
}
