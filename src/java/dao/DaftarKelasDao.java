package dao;

import connection.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.DaftarKelas;

public class DaftarKelasDao {
    private final Connection conDB;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public DaftarKelasDao(){
        conDB = Koneksi.getKoneksi();
    }
    
    public ArrayList<DaftarKelas> getAllDaftarKelas(){
        ArrayList<DaftarKelas> listDaftarKelas = new ArrayList<>();
        
        try{
            String sqlAllDaftarKelas ="SELECT * FROM daftarkelas ORDER BY nis";
            ps = conDB.prepareStatement(sqlAllDaftarKelas);
            rs = ps.executeQuery();
            
            while(rs.next()){
                DaftarKelas dk = new DaftarKelas();
                dk.setNis(rs.getString("nis"));
                dk.setKodekelas(rs.getString("kodekelas"));
                dk.setTahunpel(rs.getString("tahunpel"));
                
                listDaftarKelas.add(dk);
            }
        }
        catch(SQLException e){
            System.out.println("method arraylist error "+e.getMessage());
        }
        return listDaftarKelas;
    }
    
    public void simpanData(DaftarKelas dk, String page){
        String sqlSimpan = null;
        if(page.equals("edit")){
            sqlSimpan = "UPDATE daftarkelas SET tahunpel=? WHERE nis=? AND kodekelas=?";
        }else if(page.equals("tambah")){
            sqlSimpan = "INSERT INTO daftarkelas(tahunpel,nis,kodekelas) VALUES (?,?,?)";
        }
        try{
            ps = conDB.prepareStatement(sqlSimpan);
            ps.setString(1,dk.getTahunpel());
            ps.setString(2,dk.getNis());
            ps.setString(3,dk.getKodekelas());
            
            ps.executeUpdate();
        }catch(SQLException e){
            System.out.println("Ada kesalahan di simpan data "+ e.getMessage());
        }
    }
    
    public void hapusData(String nis, String kodekelas, String tahunpel){
        String sqlHapus = "DELETE FROM daftarkelas WHERE nis=? AND kodekelas=? AND tahunpel=?";
        try{
            ps = conDB.prepareStatement(sqlHapus);
            ps.setString(1, nis);
            ps.setString(2, kodekelas);
            ps.setString(3, tahunpel);
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("Method hapus data error " + e.getMessage());
        }
    }
    
    public static void main (String[] args){
        DaftarKelasDao dkdao = new DaftarKelasDao();
        System.out.println(dkdao.getAllDaftarKelas());
        
    }
    
    public DaftarKelas getRecordByNiskodekelas(String nis, String kodekelas){
        DaftarKelas dk = new DaftarKelas();
        String sqlSeacrh = "SELECT * FROM daftarkelas WHERE nis=? AND kodekelas=?";
        
        try{
            ps=conDB.prepareStatement(sqlSeacrh);
            ps.setString(1, nis);
            ps.setString(2, kodekelas);
            rs = ps.executeQuery();
            
            if(rs.next()){
                dk.setNis(rs.getString("nis"));
                dk.setKodekelas(rs.getString("kodekelas"));
                dk.setTahunpel(rs.getString("tahunpel"));
            }
        }catch(SQLException e){
            System.out.println("getRecord by ");
        }
        return dk;
    }
    
    public DaftarKelas getCekNis(String nis){
        DaftarKelas dk = new DaftarKelas();
        String sqlSeacrh = "SELECT nis FROM siswa WHERE nis=?";
        
        try{
            ps=conDB.prepareStatement(sqlSeacrh);
            ps.setString(1, nis);
            rs = ps.executeQuery();
            
            if(rs.next()){
                dk.setNis(rs.getString("nis"));
            }
        }catch(SQLException e){
            System.out.println("getRecord by ");
        }
        return dk;
    }
    
    public DaftarKelas getCekKodekelas(String kodekelas){
        DaftarKelas dk = new DaftarKelas();
        String sqlSeacrh = "SELECT kodekelas FROM kelas WHERE kodekelas=?";
        
        try{
            ps=conDB.prepareStatement(sqlSeacrh);
            ps.setString(1, kodekelas);
            rs = ps.executeQuery();
            
            if(rs.next()){
                dk.setKodekelas(rs.getString("kodekelas"));
            }
        }catch(SQLException e){
            System.out.println("getRecord by ");
        }
        return dk;
    }
    
    public DaftarKelas getCekData(String nis, String kodekelas, String tahunpel){
        DaftarKelas dk = new DaftarKelas();
        String sqlSeacrh = "SELECT * FROM daftarkelas WHERE nis=? AND kodekelas=? AND tahunpel=?";
        
        try{
            ps=conDB.prepareStatement(sqlSeacrh);
            ps.setString(1, nis);
            ps.setString(2, kodekelas);
            ps.setString(3, tahunpel);
            rs = ps.executeQuery();
            
            if(rs.next()){
                dk.setNis(rs.getString("nis"));
                dk.setKodekelas(rs.getString("kodekelas"));
                dk.setTahunpel(rs.getString("tahunpel"));
            }
        }catch(SQLException e){
            System.out.println("getRecord by ");
        }
        return dk;
    }
    
    // -- CEK FOREIGN KEY PADA DATA SISWA
    public DaftarKelas getForeignNis(String nis){
        DaftarKelas dk = new DaftarKelas();
        String sqlSeacrh = "SELECT * FROM daftarkelas WHERE nis=?";
        
        try{
            ps=conDB.prepareStatement(sqlSeacrh);
            ps.setString(1, nis);
            rs = ps.executeQuery();
            
            if(rs.next()){
                dk.setNis(rs.getString("nis"));
            }
        }catch(SQLException e){
            System.out.println("getRecord by ");
        }
        return dk;
    }
    
    // -- GET ALL TAHUNPEL
    public ArrayList<DaftarKelas> getAllTahunPelajaran(){
        ArrayList<DaftarKelas> listTahunPelajaran = new ArrayList<>();

        try{
            String sqlAllTahunPelajaran ="SELECT DISTINCT tahunpel FROM daftarkelas ORDER BY tahunpel";
            ps = conDB.prepareStatement(sqlAllTahunPelajaran);
            rs = ps.executeQuery();

            while(rs.next()){
                DaftarKelas dk = new DaftarKelas();
                dk.setTahunpel(rs.getString("tahunpel"));
                listTahunPelajaran.add(dk);
            }
        }
        catch(SQLException e){
            System.out.println("method arraylist error "+e.getMessage());
        }
        return listTahunPelajaran;
    }
}
