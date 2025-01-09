/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author radit
 */
public class Koneksi {

    static Connection conDB;
    
    public static Connection getKoneksi(){
        MysqlDataSource data = new MysqlDataSource();
        data.setDatabaseName("dbnilai");
        data.setUser("root");
        data.setPassword("");
        
        if(conDB==null){
            try{
                conDB = data.getConnection();
                System.out.println("Koneksi Sukses");
            }
            catch(SQLException e){
                System.out.println("Koneksi Gagal " + e.getMessage());
            }
        }
        return conDB;
    }
    
    public static void main(String[] args) {
        getKoneksi();
    }
    
}
