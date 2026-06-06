package service;

import config.SQLDatabaseConnection;
import java.sql.*;

public class LaporanService {

    public int getTotalTransaksi() {
        String sql = "SELECT COUNT(*) FROM Peminjaman";

        try (
            Connection conn = SQLDatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int getMobilTersedia() {
        String sql = "SELECT COUNT(*) FROM Mobil WHERE status = 'Tersedia'";

        try (
            Connection conn = SQLDatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int getTotalPendapatan() {
        String sql = "SELECT ISNULL(SUM(total),0) FROM Peminjaman";

        try (
            Connection conn = SQLDatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}