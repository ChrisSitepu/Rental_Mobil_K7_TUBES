package service;

import config.SQLDatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Cabang;

public class CabangService {

    public ArrayList<Cabang> getAllCabang() {
        ArrayList<Cabang> cabangList = new ArrayList<>();

        String sql = "SELECT id_cabang, nama FROM Cabang";

    try (
        Connection conn = SQLDatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()
    ) {

        while(rs.next()) {

            cabangList.add(
                new Cabang(
                    rs.getInt("id_cabang"),
                    rs.getString("nama")
                )
            );
        }

    } catch(Exception e) {
        e.printStackTrace();
    }

    return cabangList;
}
}
