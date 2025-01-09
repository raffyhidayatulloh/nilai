package dao;

import connection.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Kelas;

public class KelasDao {
    private final Connection conDB;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public KelasDao(){
        conDB = Koneksi.getKoneksi();
    }
    
    public ArrayList<Kelas> getAllKelas(){
        ArrayList<Kelas> listKelas = new ArrayList<>();
        
        try{
            String sqlAllKelas ="SELECT * FROM kelas ORDER BY kodejurusan AND kodekelas";
            ps = conDB.prepareStatement(sqlAllKelas);
            rs = ps.executeQuery();
            
            while(rs.next()){
                Kelas kel = new Kelas();
                kel.setKodejurusan(rs.getString("kodejurusan"));
                kel.setKodekelas(rs.getString("kodekelas"));
                kel.setNamakelas(rs.getString("namakelas"));
                
                listKelas.add(kel);
            }
        }
        catch(SQLException e){
            System.out.println("method arraylist error "+e.getMessage());
        }
        return listKelas;
    }
    
    public void simpanData(Kelas kel, String page){
        String sqlSimpan = null;
        if(page.equals("edit")){
            sqlSimpan = "UPDATE kelas SET namakelas=? WHERE kodejurusan=? AND kodekelas=?";
        }else if(page.equals("tambah")){
            sqlSimpan = "INSERT INTO kelas (namakelas,kodejurusan,kodekelas) VALUES (?,?,?)";
        }
        try{
            ps = conDB.prepareStatement(sqlSimpan);
            ps.setString(1,kel.getNamakelas());
            ps.setString(2,kel.getKodejurusan());
            ps.setString(3,kel.getKodekelas());
            
            ps.executeUpdate();
        }catch(SQLException e){
            System.out.println("Ada kesalahan di simpan data "+ e.getMessage());
        }
    }
    
    public void hapusData(String kodejurusan, String kodekelas){
        String sqlHapus = "DELETE FROM kelas WHERE kodejurusan=? AND kodekelas=?";
        try{
            ps = conDB.prepareStatement(sqlHapus);
            ps.setString(1, kodejurusan);
            ps.setString(2, kodekelas);
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("Method hapus data error " + e.getMessage());
        }
    }
    
    public static void main (String[] args){
        KelasDao keldao = new KelasDao();
        System.out.println(keldao.getAllKelas());
        
    }
    
    public Kelas getRecordByKodejurusankelas(String kodejurusan, String kodekelas){
        Kelas kel = new Kelas();
        String sqlSeacrh = "SELECT * FROM kelas WHERE kodejurusan=? AND kodekelas=?";
        
        try{
            ps=conDB.prepareStatement(sqlSeacrh);
            ps.setString(1, kodejurusan);
            ps.setString(2, kodekelas);
            rs = ps.executeQuery();
            
            if(rs.next()){
                kel.setKodejurusan(rs.getString("kodejurusan"));
                kel.setKodekelas(rs.getString("kodekelas"));
                kel.setNamakelas(rs.getString("namakelas"));
            }
        }catch(SQLException e){
            System.out.println("getRecord by ");
        }
        return kel;
    }
    
    public Kelas getForeignKodejurusan(String kodejurusan){
        Kelas kel = new Kelas();
        String sqlSeacrh = "SELECT * FROM kelas WHERE kodejurusan=?";
        
        try{
            ps=conDB.prepareStatement(sqlSeacrh);
            ps.setString(1, kodejurusan);
            rs = ps.executeQuery();
            
            if(rs.next()){
                kel.setKodejurusan(rs.getString("kodejurusan"));
            }
        }catch(SQLException e){
            System.out.println("getRecord by ");
        }
        return kel;
    }
}