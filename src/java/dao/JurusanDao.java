package dao;

import connection.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Jurusan;

public class JurusanDao {
    private final Connection conDB;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public JurusanDao(){
        conDB = Koneksi.getKoneksi();
    }
    
    public ArrayList<Jurusan> getAllJurusan(){
        ArrayList<Jurusan> listJurusan = new ArrayList<>();
        
        try{
            String sqlAllJurusan ="SELECT * FROM jurusan ORDER BY kodejurusan";
            ps = conDB.prepareStatement(sqlAllJurusan);
            rs = ps.executeQuery();
            
            while(rs.next()){
                Jurusan jur = new Jurusan();
                jur.setKodejurusan(rs.getString("kodejurusan"));
                jur.setNamajurusan(rs.getString("namajurusan"));
                
                listJurusan.add(jur);
            }
        }
        catch(SQLException e){
            System.out.println("method arraylist error "+e.getMessage());
        }
        return listJurusan;
    }
    
    public void simpanData(Jurusan jur, String page){
        String sqlSimpan = null;
        if(page.equals("edit")){
            sqlSimpan = "UPDATE jurusan SET namajurusan=? WHERE kodejurusan=?";
        }else if(page.equals("tambah")){
            sqlSimpan = "INSERT INTO jurusan (namajurusan, kodejurusan) VALUES (?,?)";
        }
        try{
            ps = conDB.prepareStatement(sqlSimpan);
            ps.setString(1,jur.getNamajurusan());
            ps.setString(2,jur.getKodejurusan());
            
            ps.executeUpdate();
        }catch(SQLException e){
            System.out.println("Ada kesalahan di simpan data "+ e.getMessage());
        }
    }
    
    public void hapusData(String kodejurusan){
        String sqlHapus = "DELETE FROM jurusan WHERE kodejurusan=?";
        try{
            ps = conDB.prepareStatement(sqlHapus);
            ps.setString(1, kodejurusan);
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("Method hapus data error " + e.getMessage());
        }
    }
    
    public static void main (String[] args){
        JurusanDao jurdao = new JurusanDao();
        System.out.println(jurdao.getAllJurusan());
        //jurdao.hapusData("522");
        
    }
    
    public Jurusan getRecordByKodejurusan(String kodejurusan){
        Jurusan jur = new Jurusan();
        String sqlSeacrh = "SELECT * FROM jurusan WHERE kodejurusan=?";
        
        try{
            ps=conDB.prepareStatement(sqlSeacrh);
            ps.setString(1, kodejurusan);
            rs = ps.executeQuery();
            
            if(rs.next()){
                jur.setNamajurusan(rs.getString("namajurusan"));
                jur.setKodejurusan(rs.getString("kodejurusan"));
            }
        }catch(SQLException e){
            System.out.println("getRecord by ");
        }
        return jur;
    }
}
