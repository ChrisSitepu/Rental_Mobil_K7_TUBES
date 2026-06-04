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
}
