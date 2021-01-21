/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diarybook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Maulana Alirridlo
 */
public class conn {
    public static Connection con;
    public static Statement stm;
    public static void getConfig(){
        try{
            con = (Connection) DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\ASUS\\Documents\\NetBeansProjects\\DiaryBook\\src\\diarybook\\DiaryBook.accdb");
            stm = con.createStatement();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "ERROR : "+e.getMessage());
        }
    }
}
