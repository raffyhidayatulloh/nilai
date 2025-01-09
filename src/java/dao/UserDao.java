package dao;

import connection.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.User;

public class UserDao {

    private final Connection conDB;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public UserDao(){
        conDB = Koneksi.getKoneksi();
    }
    
    public ArrayList<User> getAllUser(){
        ArrayList<User> listUser = new ArrayList<>();
        
        try{
            String sqlAllUser ="SELECT * FROM user ORDER BY username";
            ps = conDB.prepareStatement(sqlAllUser);
            rs = ps.executeQuery();
            
            while(rs.next()){
                User us = new User();
                us.setUsername(rs.getString("username"));
                us.setPassword(rs.getString("password"));
                us.setLevel(rs.getString("level"));
                
                listUser.add(us);
            }
        }
        catch(SQLException e){
            System.out.println("method arraylist error "+e.getMessage());
        }
        return listUser;
    }
    
    public void simpanData(User us, String page){
        String sqlSimpan = null;
        if(page.equals("edit")){
            sqlSimpan = "UPDATE user SET password=?, level=? WHERE username=?";
        }else if(page.equals("tambah")){
            sqlSimpan = "INSERT INTO user (password,level,username) VALUES (?,?,?)";
        }
        try{
            ps = conDB.prepareStatement(sqlSimpan);
            ps.setString(1,us.getPassword());
            ps.setString(2,us.getLevel());
            ps.setString(3,us.getUsername());
            
            ps.executeUpdate();
        }catch(SQLException e){
            System.out.println("Ada kesalahan di simpan data "+ e.getMessage());
        }
    }
    
    public void hapusData(String username){
        String sqlHapus = "DELETE FROM user WHERE username=?";
        try{
            ps = conDB.prepareStatement(sqlHapus);
            ps.setString(1, username);
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("Method hapus data error " + e.getMessage());
        }
    }
    
    public static void main (String[] args){
        UserDao usdao = new UserDao();
        System.out.println(usdao.getAllUser());
        
    }
    
    public User getRecordByUsername(String username){
        User us = new User();
        String sqlSeacrh = "SELECT * FROM user WHERE username=?";
        
        try{
            ps=conDB.prepareStatement(sqlSeacrh);
            ps.setString(1, username);
            rs = ps.executeQuery();
            
            if(rs.next()){
                us.setUsername(rs.getString("username"));
                us.setPassword(rs.getString("password"));
                us.setLevel(rs.getString("level"));
            }
        }catch(SQLException e){
            System.out.println("getRecord by ");
        }
        return us;
    }
}
