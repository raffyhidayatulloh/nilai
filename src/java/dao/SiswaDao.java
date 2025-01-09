package dao;

import connection.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Siswa;

public class SiswaDao {
    private final Connection conDB;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public SiswaDao(){
        conDB = Koneksi.getKoneksi();
    }
    
    public ArrayList<Siswa> getAllSiswa(){
        ArrayList<Siswa> listSiswa = new ArrayList<>();
        
        try{
            String sqlAllSiswa ="SELECT * FROM siswa ORDER BY nis";
            ps = conDB.prepareStatement(sqlAllSiswa);
            rs = ps.executeQuery();
            
            while(rs.next()){
                Siswa sis = new Siswa();
                sis.setNis(rs.getString("nis"));
                sis.setNama(rs.getString("nama"));
                if(rs.getString("jenkel") != null){
                    if(rs.getString("jenkel").equals("L"))
                        sis.setJenkel("Laki-laki");
                    else sis.setJenkel("Perempuan"); 
                }else{
                    sis.setJenkel("");
                }
                if(rs.getString("alamat") != null){
                    sis.setAlamat(rs.getString("alamat"));
                }else{
                    sis.setAlamat("");
                }
                sis.setTelepon(rs.getString("telepon"));
                
                listSiswa.add(sis);
            }
        }
        catch(SQLException e){
            System.out.println("method arraylist error "+e.getMessage());
        }
        return listSiswa;
    }
    
    public void simpanData(Siswa sis, String page){
        String sqlSimpan = null;
        if(page.equals("edit")){
            sqlSimpan = "UPDATE siswa SET nama=?, jenkel=?, telepon=?, alamat=? WHERE nis=?";
        }else if(page.equals("tambah")){
            sqlSimpan = "INSERT INTO siswa (nama,jenkel,telepon,alamat,nis) VALUES (?,?,?,?,?)";
        }
        try{
            ps = conDB.prepareStatement(sqlSimpan);
            ps.setString(1,sis.getNama());
            if(sis.getJenkel().equals("")) ps.setString(2, null);
            else ps.setString(2, sis.getJenkel());
            ps.setString(3,sis.getTelepon());
            if(sis.getAlamat().equals("")) ps.setString(4, null);
            else ps.setString(4, sis.getAlamat());
            ps.setString(5,sis.getNis());
            
            ps.executeUpdate();
        }catch(SQLException e){
            System.out.println("Ada kesalahan di simpan data "+ e.getMessage());
        }
    }
    
    public void hapusData(String nis){
        String sqlHapus = "DELETE FROM siswa WHERE nis=?";
        try{
            ps = conDB.prepareStatement(sqlHapus);
            ps.setString(1, nis);
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("Method hapus data error " + e.getMessage());
        }
    }
    
    public static void main (String[] args){
        SiswaDao sisdao = new SiswaDao();
        System.out.println(sisdao.getAllSiswa());
        
    }
    
    public Siswa getRecordByNis(String nis){
        Siswa sis = new Siswa();
        String sqlSeacrh = "SELECT * FROM siswa WHERE nis=?";
        
        try{
            ps=conDB.prepareStatement(sqlSeacrh);
            ps.setString(1, nis);
            rs = ps.executeQuery();
            
            if(rs.next()){
                sis.setNama(rs.getString("nama"));
                sis.setNis(rs.getString("nis"));
                sis.setAlamat(rs.getString("alamat"));
                sis.setJenkel(rs.getString("jenkel"));
                sis.setTelepon(rs.getString("telepon"));
            }
        }catch(SQLException e){
            System.out.println("getRecord by ");
        }
        return sis;
    }
    
    public Siswa getForeignKeyNis(String nis){
        Siswa sis = new Siswa();
        String sqlSeacrh = "SELECT * FROM daftarkelas WHERE nis=?";
        
        try{
            ps=conDB.prepareStatement(sqlSeacrh);
            ps.setString(1, nis);
            rs = ps.executeQuery();
            
            if(rs.next()){
                sis.setNis(rs.getString("nis"));
            }
        }catch(SQLException e){
            System.out.println("getRecord by ");
        }
        return sis;
    }
}