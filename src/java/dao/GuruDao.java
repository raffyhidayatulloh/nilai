package dao;

import connection.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Guru;

public class GuruDao {

    private final Connection conDB;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public GuruDao(){
        conDB = Koneksi.getKoneksi();
    }
    
    public ArrayList<Guru> getAllGuru(){
        ArrayList<Guru> listGuru = new ArrayList<>();
        
        try{
            String sqlAllGuru ="SELECT * FROM guru ORDER BY kodeguru";
            ps = conDB.prepareStatement(sqlAllGuru);
            rs = ps.executeQuery();
            
            while(rs.next()){
                Guru gur = new Guru();
                gur.setKodeguru(rs.getString("kodeguru"));
                gur.setNamaguru(rs.getString("namaguru"));
                gur.setIdmapel(rs.getString("idmapel"));
                
                listGuru.add(gur);
            }
        }
        catch(SQLException e){
            System.out.println("method arraylist error "+e.getMessage());
        }
        return listGuru;
    }
    
    public void simpanData(Guru gur, String page){
        String sqlSimpan = null;
        if(page.equals("edit")){
            sqlSimpan = "UPDATE guru SET namaguru=?, idmapel=? WHERE kodeguru=?";
        }else if(page.equals("tambah")){
            sqlSimpan = "INSERT INTO guru (namaguru,idmapel,kodeguru) VALUES (?,?,?)";
        }
        try{
            ps = conDB.prepareStatement(sqlSimpan);
            ps.setString(1,gur.getNamaguru());
            ps.setString(2,gur.getIdmapel());
            ps.setString(3,gur.getKodeguru());
            
            ps.executeUpdate();
        }catch(SQLException e){
            System.out.println("Ada kesalahan di simpan data "+ e.getMessage());
        }
    }
    
    public void hapusData(String kodeguru){
        String sqlHapus = "DELETE FROM guru WHERE kodeguru=?";
        try{
            ps = conDB.prepareStatement(sqlHapus);
            ps.setString(1, kodeguru);
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("Method hapus data error " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        GuruDao gurdao = new GuruDao();
        System.out.println(gurdao.getAllGuru());
    }
    
    public Guru getRecordByKodeguru(String kodeguru){
        Guru gur = new Guru();
        String sqlSeacrh = "SELECT * FROM guru WHERE kodeguru=?";
        
        try{
            ps=conDB.prepareStatement(sqlSeacrh);
            ps.setString(1, kodeguru);
            rs = ps.executeQuery();
            
            if(rs.next()){
                gur.setKodeguru(rs.getString("kodeguru"));
                gur.setNamaguru(rs.getString("namaguru"));
                gur.setIdmapel(rs.getString("idmapel"));
            }
        }catch(SQLException e){
            System.out.println("getRecord by ");
        }
        return gur;
    }
    
    public Guru getRecordByAllValidasi(String kodeguru, String namaguru, String idmapel){
        Guru gur = new Guru();
        String sqlSeacrh = "SELECT * FROM guru WHERE kodeguru=? AND namaguru=? AND idmapel=?";
        
        try{
            ps=conDB.prepareStatement(sqlSeacrh);
            ps.setString(1, kodeguru);
            ps.setString(2, namaguru);
            ps.setString(3, idmapel);
            rs = ps.executeQuery();
            
            if(rs.next()){
                gur.setKodeguru(rs.getString("kodeguru"));
                gur.setNamaguru(rs.getString("namaguru"));
                gur.setIdmapel(rs.getString("idmapel"));
            }
        }catch(SQLException e){
            System.out.println("getRecord by ");
        }
        return gur;
    }
    
    public Guru getForeignIdmapel(String idmapel){
        Guru gur = new Guru();
        String sqlSeacrh = "SELECT * FROM guru WHERE idmapel=?";
        
        try{
            ps=conDB.prepareStatement(sqlSeacrh);
            ps.setString(1, idmapel);
            rs = ps.executeQuery();
            
            if(rs.next()){
                gur.setIdmapel(rs.getString("idmapel"));
            }
        }catch(SQLException e){
            System.out.println("getRecord by ");
        }
        return gur;
    }
    
    // -- GET ALL TAHUNPEL
    public ArrayList<Guru> getAllKodeguru(){
        ArrayList<Guru> listKodeguru = new ArrayList<>();

        try{
            String sqlAllKodeguru ="SELECT DISTINCT kodeguru FROM guru ORDER BY kodeguru";
            ps = conDB.prepareStatement(sqlAllKodeguru);
            rs = ps.executeQuery();

            while(rs.next()){
                Guru gur = new Guru();
                gur.setKodeguru(rs.getString("kodeguru"));
                listKodeguru.add(gur);
            }
        }
        catch(SQLException e){
            System.out.println("method arraylist error "+e.getMessage());
        }
        return listKodeguru;
    }
}
