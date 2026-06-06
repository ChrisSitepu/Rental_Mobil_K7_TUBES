package service;

import config.SQLDatabaseConnection;
import model.Cabang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;

public class CabangService {

    public ArrayList<Cabang> getAllCabang() {
        ArrayList<Cabang> list = new ArrayList<>();
        String sql = "SELECT * FROM Cabang";

        try (Connection conn = SQLDatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new Cabang(
                        rs.getInt("id_cabang"),
                        rs.getString("nama"),
                        rs.getTime("jam_operasional").toLocalTime(),
                        rs.getString("email"),
                        rs.getString("no_telepon"),
                        rs.getString("alamat")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addCabang(Cabang cabang) {
        String sql = "INSERT INTO Cabang (id_cabang, nama, jam_operasional, email, no_telepon, alamat) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = SQLDatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, cabang.getIdCabang());
            stmt.setString(2, cabang.getNama());
            stmt.setTime(3, java.sql.Time.valueOf(cabang.getJamOperasional()));
            stmt.setString(4, cabang.getEmail());
            stmt.setString(5, cabang.getNoTelepon());
            stmt.setString(6, cabang.getAlamat());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateCabang(Cabang cabang) {
        String sql = "UPDATE Cabang SET nama = ?, jam_operasional = ?, email = ?, no_telepon = ?, alamat = ? WHERE id_cabang = ?";
        try (Connection conn = SQLDatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cabang.getNama());
            stmt.setTime(2, java.sql.Time.valueOf(cabang.getJamOperasional()));
            stmt.setString(3, cabang.getEmail());
            stmt.setString(4, cabang.getNoTelepon());
            stmt.setString(5, cabang.getAlamat());
            stmt.setInt(6, cabang.getIdCabang());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteCabang(int idCabang) {
        String sql = "DELETE FROM Cabang WHERE id_cabang = ?";
        try (Connection conn = SQLDatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idCabang);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getNextId() {
        String sql = "SELECT MAX(id_cabang) FROM Cabang";
        try (Connection conn = SQLDatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1) + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
