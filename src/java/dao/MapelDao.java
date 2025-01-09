package dao;

import connection.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Mapel;

public class MapelDao {
    private final Connection conDB;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public MapelDao(){
        conDB = Koneksi.getKoneksi();
    }
    
    public ArrayList<Mapel> getAllMapel(){
        ArrayList<Mapel> listMapel = new ArrayList<>();
        
        try{
            String sqlAllMapel ="SELECT * FROM mapel ORDER BY idmapel";
            ps = conDB.prepareStatement(sqlAllMapel);
            rs = ps.executeQuery();
            
            while(rs.next()){
                Mapel map = new Mapel();
                map.setIdmapel(rs.getString("idmapel"));
                map.setNamamapel(rs.getString("namamapel"));
                
                listMapel.add(map);
            }
        }
        catch(SQLException e){
            System.out.println("method arraylist error "+e.getMessage());
        }
        return listMapel;
    }
    
    public void simpanData(Mapel map, String page){
        String sqlSimpan = null;
        if(page.equals("edit")){
            sqlSimpan = "UPDATE mapel SET namamapel=? WHERE idmapel=?";
        }else if(page.equals("tambah")){
            sqlSimpan = "INSERT INTO mapel (namamapel, idmapel) VALUES (?,?)";
        }
        try{
            ps = conDB.prepareStatement(sqlSimpan);
            ps.setString(1,map.getNamamapel());
            ps.setString(2,map.getIdmapel());
            
            ps.executeUpdate();
        }catch(SQLException e){
            System.out.println("Ada kesalahan di simpan data "+ e.getMessage());
        }
    }
    
    public void hapusData(String idmapel){
        String sqlHapus = "DELETE FROM mapel WHERE idmapel=?";
        try{
            ps = conDB.prepareStatement(sqlHapus);
            ps.setString(1, idmapel);
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("Method hapus data error " + e.getMessage());
        }
    }
    
    public static void main (String[] args){
        MapelDao mapdao = new MapelDao();
        System.out.println(mapdao.getAllMapel());
        //jurdao.hapusData("522");
        
    }
    
    public Mapel getRecordByIdmapel(String idmapel){
        Mapel map = new Mapel();
        String sqlSeacrh = "SELECT * FROM mapel WHERE idmapel=?";
        
        try{
            ps=conDB.prepareStatement(sqlSeacrh);
            ps.setString(1, idmapel);
            rs = ps.executeQuery();
            
            if(rs.next()){
                map.setNamamapel(rs.getString("namamapel"));
                map.setIdmapel(rs.getString("idmapel"));
            }
        }catch(SQLException e){
            System.out.println("getRecord by ");
        }
        return map;
    }
}