package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import config.SQLDatabaseConnection;
import model.Mobil;

public class MobilService {
    public ArrayList<Mobil> getAllMobil() {

        ArrayList<Mobil> mobilList = new ArrayList<>();

        String sql = "SELECT *\n" + //
                        "FROM Mobil\n" + //
                        "ORDER BY brand, nama;";

        try (
            Connection conn = SQLDatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Mobil m = new Mobil(
                        rs.getInt("id_mobil"),
                        rs.getInt("id_cabang"),
                        rs.getString("nama"),
                        rs.getString("brand"),
                        rs.getString("tipe"),
                        rs.getString("warna"),
                        rs.getString("no_plat"),
                        rs.getString("status"),
                        rs.getInt("kapasitas"),
                        rs.getInt("tahun_pembuatan"),
                        rs.getDouble("tarif_sewa"),
                        rs.getDouble("tarif_denda")
                );

                mobilList.add(m);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mobilList;
    }

    public ArrayList<Mobil> getAvailableMobil() {

        ArrayList<Mobil> available = new ArrayList<>();

        for (Mobil m : getAllMobil()) {

            if ("Tersedia".equalsIgnoreCase(m.getStatus())) {
                available.add(m);
            }
        }

        return available;
    }

    public boolean addMobil(Mobil mobil) {

        String sql =
            "INSERT INTO Mobil " +
            "(id_mobil,nama,tarif_sewa,tarif_denda,id_cabang,no_plat,brand,tipe,warna,kapasitas,tahun_pembuatan,status) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

        try (
            Connection conn = SQLDatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, mobil.getIdMobil());
            ps.setString(2, mobil.getNama());
            ps.setDouble(3, mobil.getTarifSewa());
            ps.setDouble(4, mobil.getTarifDenda());
            ps.setInt(5, mobil.getIdCabang());
            ps.setString(6, mobil.getPlat());
            ps.setString(7, mobil.getBrand());
            ps.setString(8, mobil.getTipe());
            ps.setString(9, mobil.getWarna());
            ps.setInt(10, mobil.getKapasitas());
            ps.setInt(11, mobil.getTahunPembuatan());
            ps.setString(12, mobil.getStatus());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateMobilStatus(
            int idMobil,
            String status) {

        String sql =
            "UPDATE Mobil SET status=? WHERE id_mobil=?";

        try (
            Connection conn = SQLDatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, status);
            ps.setInt(2, idMobil);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateMobilRate(
            int idMobil,
            double tarifSewa) {

        String sql =
            "UPDATE Mobil SET tarif_sewa=? WHERE id_mobil=?";

        try (
            Connection conn = SQLDatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setDouble(1, tarifSewa);
            ps.setInt(2, idMobil);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}