package service;

import config.SQLDatabaseConnection;
import model.Mobil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MobilService {

    public ArrayList<Mobil> getAllMobil() {
        ArrayList<Mobil> list = new ArrayList<>();
        String sql = "SELECT * FROM Mobil";

        try (Connection conn = SQLDatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new Mobil(
                        rs.getInt("id_mobil"),
                        rs.getInt("id_cabang"),
                        rs.getString("brand"),
                        rs.getString("tipe"),
                        rs.getString("warna"),
                        rs.getString("no_plat"),
                        rs.getInt("kapasitas"),
                        rs.getInt("tahun_pembuatan"),
                        rs.getString("status").equalsIgnoreCase("Tersedia"),
                        rs.getInt("harga_sewa")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addMobil(Mobil m, int idCabang) {
        String sql = "INSERT INTO Mobil (id_mobil, id_cabang, no_plat, brand, tipe, warna, kapasitas, tahun_pembuatan, status, harga_sewa) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = SQLDatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            int nextId = getNextMobilId(conn);
            stmt.setInt(1, nextId);
            stmt.setInt(2, idCabang);
            stmt.setString(3, m.getPlat());
            stmt.setString(4, m.getBrand());
            stmt.setString(5, m.getTipe());
            stmt.setString(6, m.getWarna());
            stmt.setInt(7, m.getKapasitas());
            stmt.setInt(8, m.getTahunPembuatan());
            stmt.setString(9, m.isAvailable() ? "Tersedia" : "Dipinjam");
            stmt.setInt(10, m.getTarifSewa());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateMobilRate(String plat, int newRate) {
        String sql = "UPDATE Mobil SET harga_sewa = ? WHERE no_plat = ?";
        try (Connection conn = SQLDatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, newRate);
            stmt.setString(2, plat);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateMobilStatus(String plat, String status) {
        String sql = "UPDATE Mobil SET status = ? WHERE no_plat = ?";
        try (Connection conn = SQLDatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setString(2, plat);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteMobil(String plat) {
        String sql = "DELETE FROM Mobil WHERE no_plat = ?";
        try (Connection conn = SQLDatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, plat);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private int getNextMobilId(Connection conn) throws SQLException {
        String sql = "SELECT MAX(id_mobil) FROM Mobil";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) + 1;
            }
        }
        return 1;
    }

    public ArrayList<Mobil> getAvailableMobil() {
        ArrayList<Mobil> list = new ArrayList<>();
        for (Mobil m : getAllMobil()) {
            if (m.isAvailable()) {
                list.add(m);
            }
        }
        return list;
    }
}