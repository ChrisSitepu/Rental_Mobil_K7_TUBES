package service;

import config.SQLDatabaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TesMobil {

    public void tampilkanMobil() {
        String sql = "SELECT * FROM Mobil";

        try (
            Connection conn = SQLDatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)
        ) {
            System.out.println("\n=== DATA MOBIL ===");

            while (rs.next()) {
                System.out.println("ID Mobil   : " + rs.getInt("id_mobil"));
                System.out.println("ID Cabang  : " + rs.getInt("id_cabang"));
                System.out.println("No Plat    : " + rs.getString("no_plat"));
                System.out.println("Brand      : " + rs.getString("brand"));
                System.out.println("Tipe       : " + rs.getString("tipe"));
                System.out.println("Warna      : " + rs.getString("warna"));
                System.out.println("Kapasitas  : " + rs.getInt("kapasitas"));
                System.out.println("Tahun      : " + rs.getInt("tahun_pembuatan"));
                System.out.println("Status     : " + rs.getString("status"));
                System.out.println("-----------------------------");
            }

        } catch (Exception e) {
            System.out.println("Gagal menampilkan data mobil.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TesMobil tes = new TesMobil();
        tes.tampilkanMobil();
    }
}