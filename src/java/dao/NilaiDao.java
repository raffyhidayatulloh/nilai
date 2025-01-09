package dao;

import connection.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Nilai;

public class NilaiDao {

    private final Connection conDB;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public NilaiDao(){
        conDB = Koneksi.getKoneksi();
    }
    
    public ArrayList<Nilai> getAllNilai(){
        ArrayList<Nilai> listNilai = new ArrayList<>();
        
        try{
            String sqlAllNilai ="SELECT * FROM nilai ORDER BY kodejurusan AND tahunpel AND kodekelas AND nis AND idmapel";
            ps = conDB.prepareStatement(sqlAllNilai);
            rs = ps.executeQuery();
            
            while(rs.next()){
                Nilai nil = new Nilai();
                nil.setKodejurusan(rs.getString("kodejurusan"));
                nil.setTahunpel(rs.getString("tahunpel"));
                nil.setKodekelas(rs.getString("kodekelas"));
                nil.setNis(rs.getString("nis"));
                nil.setIdmapel(rs.getString("idmapel"));
                nil.setNilaisiswa(rs.getString("nilaisiswa"));
                
                listNilai.add(nil);
            }
        }
        catch(SQLException e){
            System.out.println("method arraylist error "+e.getMessage());
        }
        return listNilai;
    }
    
    public ArrayList<Nilai> getNilaiByIdMapel(String idmapel) {
        ArrayList<Nilai> listNilai = new ArrayList<>();
        try {
            String sql = "SELECT * FROM nilai WHERE idmapel = ? ORDER BY idmapel";
            PreparedStatement ps = conDB.prepareStatement(sql);
            ps.setString(1, idmapel);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Nilai nilai = new Nilai();
                nilai.setKodejurusan(rs.getString("kodejurusan"));
                nilai.setTahunpel(rs.getString("tahunpel"));
                nilai.setKodekelas(rs.getString("kodekelas"));
                nilai.setNis(rs.getString("nis"));
                nilai.setIdmapel(rs.getString("idmapel"));
                nilai.setNilaisiswa(rs.getString("nilaisiswa"));
                listNilai.add(nilai);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listNilai;
    }
    
    public ArrayList<Nilai> getNilaiByNis(String nis) {
        ArrayList<Nilai> listNilai = new ArrayList<>();
        try {
            String sql = "SELECT * FROM nilai WHERE nis = ? ORDER BY tahunpel";
            PreparedStatement ps = conDB.prepareStatement(sql);
            ps.setString(1, nis);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Nilai nilai = new Nilai();
                nilai.setKodejurusan(rs.getString("kodejurusan"));
                nilai.setTahunpel(rs.getString("tahunpel"));
                nilai.setKodekelas(rs.getString("kodekelas"));
                nilai.setNis(rs.getString("nis"));
                nilai.setIdmapel(rs.getString("idmapel"));
                nilai.setNilaisiswa(rs.getString("nilaisiswa"));
                listNilai.add(nilai);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listNilai;
    }
    
    public void simpanData(Nilai nil, String page){
        String sqlSimpan = null;
        if(page.equals("edit")){
            sqlSimpan = "UPDATE nilai SET nilaisiswa=? WHERE kodejurusan=? AND tahunpel=? AND kodekelas=? AND nis=? AND idmapel=?";
        }else if(page.equals("tambah")){
            sqlSimpan = "INSERT INTO nilai(nilaisiswa,kodejurusan,tahunpel,kodekelas,nis,idmapel) VALUES (?,?,?,?,?,?)";
        }
        try{
            ps = conDB.prepareStatement(sqlSimpan);
            ps.setString(1,nil.getNilaisiswa());
            ps.setString(2,nil.getKodejurusan());
            ps.setString(3,nil.getTahunpel());
            ps.setString(4,nil.getKodekelas());
            ps.setString(5,nil.getNis());
            ps.setString(6,nil.getIdmapel());
            
            ps.executeUpdate();
        }catch(SQLException e){
            System.out.println("Ada kesalahan di simpan data "+ e.getMessage());
        }
    }
    
    public void hapusData(String kodejurusan, String tahunpel, String kodekelas, String nis, String idmapel){
        String sqlHapus = "DELETE FROM nilai WHERE kodejurusan=? AND tahunpel=? AND kodekelas=? AND nis=? AND idmapel=?";
        try{
            ps = conDB.prepareStatement(sqlHapus);
            ps.setString(1, kodejurusan);
            ps.setString(2, tahunpel);
            ps.setString(3, kodekelas);
            ps.setString(4, nis);
            ps.setString(5, idmapel);
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("Method hapus data error " + e.getMessage());
        }
    }
    
    public static void main (String[] args){
        NilaiDao nildao = new NilaiDao();
        System.out.println(nildao.getAllNilai());
        
    }
    
    public Nilai getRecordByLotsNilai(String kodejurusan, String tahunpel, String kodekelas, String nis, String idmapel){
        Nilai nil = new Nilai();
        String sqlSeacrh = "SELECT * FROM nilai WHERE kodejurusan=? AND tahunpel=? AND kodekelas=? AND nis=? AND idmapel=?";
        
        try{
            ps=conDB.prepareStatement(sqlSeacrh);
            ps.setString(1, kodejurusan);
            ps.setString(2, tahunpel);
            ps.setString(3, kodekelas);
            ps.setString(4, nis);
            ps.setString(5, idmapel);
            rs = ps.executeQuery();
            
            if(rs.next()){
                nil.setKodejurusan(rs.getString("kodejurusan"));
                nil.setTahunpel(rs.getString("tahunpel"));
                nil.setKodekelas(rs.getString("kodekelas"));
                nil.setNis(rs.getString("nis"));
                nil.setIdmapel(rs.getString("idmapel"));
                nil.setNilaisiswa(rs.getString("nilaisiswa"));
            }
        }catch(SQLException e){
            System.out.println("getRecord by ");
        }
        return nil;
    }
    
    public Nilai getCekKodejurusan(String kodejurusan){
        Nilai nil = new Nilai();
        String sqlSeacrh = "SELECT * FROM jurusan WHERE kodejurusan=?";
        
        try{
            ps=conDB.prepareStatement(sqlSeacrh);
            ps.setString(1, kodejurusan);
            rs = ps.executeQuery();
            
            if(rs.next()){
                nil.setKodejurusan(rs.getString("kodejurusan"));
            }
        }catch(SQLException e){
            System.out.println("getRecord by ");
        }
        return nil;
    }
    public Nilai getCekKodekelas(String kodekelas, String kodejurusan){
        Nilai nil = new Nilai();
        String sqlSeacrh = "SELECT * FROM kelas WHERE kodekelas=? AND kodejurusan=?";
        
        try{
            ps=conDB.prepareStatement(sqlSeacrh);
            ps.setString(1, kodekelas);
            ps.setString(2, kodejurusan);
            rs = ps.executeQuery();
            
            if(rs.next()){
                nil.setKodekelas(rs.getString("kodekelas"));
            }
        }catch(SQLException e){
            System.out.println("getRecord by ");
        }
        return nil;
    }
    public Nilai getCekNis(String nis){
        Nilai nil = new Nilai();
        String sqlSeacrh = "SELECT * FROM siswa WHERE nis=?";
        
        try{
            ps=conDB.prepareStatement(sqlSeacrh);
            ps.setString(1, nis);
            rs = ps.executeQuery();
            
            if(rs.next()){
                nil.setNis(rs.getString("nis"));
            }
        }catch(SQLException e){
            System.out.println("getRecord by ");
        }
        return nil;
    }
    public Nilai getCekIdmapel(String idmapel){
        Nilai nil = new Nilai();
        String sqlSeacrh = "SELECT * FROM mapel WHERE idmapel=?";
        
        try{
            ps=conDB.prepareStatement(sqlSeacrh);
            ps.setString(1, idmapel);
            rs = ps.executeQuery();
            
            if(rs.next()){
                nil.setIdmapel(rs.getString("idmapel"));
            }
        }catch(SQLException e){
            System.out.println("getRecord by ");
        }
        return nil;
    }
    
    public Nilai getForeignIdmapel(String idmapel){
        Nilai nil = new Nilai();
        String sqlSeacrh = "SELECT * FROM nilai WHERE idmapel=?";
        
        try{
            ps=conDB.prepareStatement(sqlSeacrh);
            ps.setString(1, idmapel);
            rs = ps.executeQuery();
            
            if(rs.next()){
                nil.setIdmapel(rs.getString("idmapel"));
            }
        }catch(SQLException e){
            System.out.println("getRecord by ");
        }
        return nil;
    }
}
