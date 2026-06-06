package service;

import config.SQLDatabaseConnection;
import java.sql.*;

public class LaporanService {

    public int getTotalTransaksi() {
        String sql = "SELECT COUNT(*) FROM Peminjaman";

        try (
                Connection conn = SQLDatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
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
                ResultSet rs = stmt.executeQuery()) {
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
                ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int getPendapatanBulanIni() {
        String sql = "SELECT ISNULL(SUM(total),0) " +
                "FROM Peminjaman " +
                "WHERE MONTH(waktu_pinjam)=MONTH(GETDATE()) " +
                "AND YEAR(waktu_pinjam)=YEAR(GETDATE())";

        try (
                Connection conn = SQLDatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int getJumlahTransaksiBulanIni() {
        String sql = "SELECT COUNT(*) " +
                "FROM Peminjaman " +
                "WHERE MONTH(waktu_pinjam)=MONTH(GETDATE()) " +
                "AND YEAR(waktu_pinjam)=YEAR(GETDATE())";

        try (
                Connection conn = SQLDatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public String getMobilPalingBanyakDisewa() {

        String sql = "SELECT TOP 1 m.brand + ' ' + m.tipe " +
                "FROM Peminjaman p " +
                "JOIN Mobil m ON p.id_mobil = m.id_mobil " +
                "GROUP BY m.brand, m.tipe " +
                "ORDER BY COUNT(*) DESC";

        try (
                Connection conn = SQLDatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "-";
    }

    public String getCabangPalingRamai() {

        String sql = "SELECT TOP 1 c.nama " +
                "FROM Peminjaman p " +
                "JOIN Cabang c ON p.id_cabang = c.id_cabang " +
                "GROUP BY c.nama " +
                "ORDER BY COUNT(*) DESC";

        try (
                Connection conn = SQLDatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "-";
    }
}