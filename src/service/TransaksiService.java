package service;

import config.SQLDatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import model.Mobil;
import model.Transaksi;
import model.User;

public class TransaksiService {

    public boolean createPeminjaman(User member, Mobil mobil, int totalHariSewa) {

        String sql =
            "INSERT INTO Peminjaman " +
            "(id_transaksi, id_mobil, id_cabang, id_member, status, total_hari_sewa, biaya_sewa, total, waktu_pinjam, waktu_rencana_pengembalian) " +
            "VALUES (?, ?, ?, ?, 'MENUNGGU VERIFIKASI', ?, ?, ?, GETDATE(), ?)";

        try (
            Connection conn = SQLDatabaseConnection.getConnection()
        ) {

            int nextId = getNextId(
                conn,
                "SELECT MAX(id_transaksi) FROM Peminjaman"
            );

            double biayaSewa = mobil.getTarifSewa();
            double total = biayaSewa * totalHariSewa;

            LocalDateTime rencanaKembali =
                LocalDateTime.now().plusDays(totalHariSewa);

            try (
                PreparedStatement stmt =
                    conn.prepareStatement(sql)
            ) {

                stmt.setInt(1, nextId);
                stmt.setInt(2, mobil.getIdMobil());
                stmt.setInt(3, mobil.getIdCabang());
                stmt.setInt(4, member.getIdMember());
                stmt.setInt(5, totalHariSewa);
                stmt.setDouble(6, biayaSewa);
                stmt.setDouble(7, total);
                stmt.setTimestamp(
                    8,
                    Timestamp.valueOf(rencanaKembali)
                );

                return stmt.executeUpdate() > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public ArrayList<Transaksi> getAllActivePeminjaman() {
        ArrayList<Transaksi> list = new ArrayList<>();
        String sql = "SELECT p.*, m.brand + ' ' + m.tipe as nama_mobil, m.no_plat, u.nama as nama_member " +
                "FROM Peminjaman p " +
                "JOIN Mobil m ON p.id_mobil = m.id_mobil " +
                "JOIN Member mem ON p.id_member = mem.id_member " +
                "JOIN Users u ON mem.id_user = u.id_user " +
                "WHERE p.status = 'DIPINJAM'";

        try (Connection conn = SQLDatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Transaksi t = new Transaksi(
                        rs.getInt("id_transaksi"),
                        rs.getInt("id_mobil"),
                        rs.getInt("id_cabang"),
                        rs.getInt("id_member"),
                        rs.getString("status"),
                        rs.getInt("total_hari_sewa"),
                        rs.getInt("biaya_sewa"),
                        rs.getInt("total"),
                        rs.getTimestamp("waktu_pinjam").toLocalDateTime(),
                        rs.getTimestamp("waktu_rencana_pengembalian").toLocalDateTime());
                t.setNamaMobil(rs.getString("nama_mobil"));
                t.setPlatMobil(rs.getString("no_plat"));
                t.setNamaMember(rs.getString("nama_member"));
                list.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Transaksi> getPendingPeminjaman() {

        ArrayList<Transaksi> list = new ArrayList<>();

        String sql =
            "SELECT p.*, " +
            "m.brand + ' ' + m.tipe AS nama_mobil, " +
            "m.no_plat, " +
            "u.nama AS nama_member " +
            "FROM Peminjaman p " +
            "JOIN Mobil m ON p.id_mobil = m.id_mobil " +
            "JOIN Member mem ON p.id_member = mem.id_member " +
            "JOIN Users u ON mem.id_user = u.id_user " +
            "WHERE p.status = 'MENUNGGU VERIFIKASI'";

        try (
            Connection conn = SQLDatabaseConnection.getConnection();
            PreparedStatement stmt =
                conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {

                Transaksi t = new Transaksi(
                    rs.getInt("id_transaksi"),
                    rs.getInt("id_mobil"),
                    rs.getInt("id_cabang"),
                    rs.getInt("id_member"),
                    rs.getString("status"),
                    rs.getInt("total_hari_sewa"),
                    rs.getInt("biaya_sewa"),
                    rs.getInt("total"),
                    rs.getTimestamp("waktu_pinjam")
                        .toLocalDateTime(),
                    rs.getTimestamp(
                        "waktu_rencana_pengembalian"
                    ).toLocalDateTime()
                );

                t.setNamaMobil(
                    rs.getString("nama_mobil")
                );

                t.setPlatMobil(
                    rs.getString("no_plat")
                );

                t.setNamaMember(
                    rs.getString("nama_member")
                );

                list.add(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean processReturn(Transaksi t, int idPegawai, String deskripsi, int tingkatKondisi, String fotoUrl) {
        LocalDateTime now = LocalDateTime.now();
        long daysLate = java.time.Duration.between(t.getWaktuRencanaPengembalian(), now).toDays();
        int denda = 0;
        if (daysLate > 0) {
            denda = (int) (daysLate * (0.5 * t.getBiayaSewa()));
        }

        String sqlUpdatePeminjaman = "UPDATE Peminjaman SET status = 'KEMBALI', waktu_aktual_pengembalian = ?, biaya_keterlambatan = ?, total = total + ? WHERE id_transaksi = ?";
        String sqlUpdateMobil = "UPDATE Mobil SET status = 'Tersedia' WHERE id_mobil = ?";
        String sqlInsertKondisi = "INSERT INTO KondisiMobil (id_catatan, id_mobil, id_pegawai, tipe_pencatatan, waktu_pencatatan, deskripsi, tingkat_kondisi) VALUES (?, ?, ?, 'SESUDAH PENGEMBALIAN', GETDATE(), ?, ?)";
        String sqlInsertFoto = "INSERT INTO FotoKondisi (id_foto, id_catatan, foto) VALUES (?, ?, ?)";

        Connection conn = null;
        try {
            conn = SQLDatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // 1. Update Peminjaman
            try (PreparedStatement stmt = conn.prepareStatement(sqlUpdatePeminjaman)) {
                stmt.setTimestamp(1, Timestamp.valueOf(now));
                stmt.setInt(2, denda);
                stmt.setInt(3, denda);
                stmt.setInt(4, t.getIdTransaksi());
                stmt.executeUpdate();
            }

            // 2. Update Mobil
            try (PreparedStatement stmt = conn.prepareStatement(sqlUpdateMobil)) {
                stmt.setInt(1, t.getIdMobil());
                stmt.executeUpdate();
            }

            // 3. Insert Kondisi
            int idCatatan =
                getNextId(
                    conn,
                    "SELECT MAX(id_catatan) FROM KondisiMobil"
                );

            try (
                PreparedStatement stmt =
                    conn.prepareStatement(sqlInsertKondisi)
            ) {

                stmt.setInt(
                    1,
                    idCatatan
                );

                stmt.setInt(
                    2,
                    t.getIdMobil()
                );

                stmt.setInt(
                    3,
                    idPegawai
                );

                stmt.setString(
                    4,
                    deskripsi
                );

                stmt.setInt(
                    5,
                    tingkatKondisi
                );

                stmt.executeUpdate();
            }

            String sqlRelasi =
                "INSERT INTO Kondisi_Setelah_Sewa " +
                "(id_transaksi,id_catatan) " +
                "VALUES (?, ?)";

            try (
                PreparedStatement stmt =
                    conn.prepareStatement(sqlRelasi)
            ) {

                stmt.setInt(
                    1,
                    t.getIdTransaksi()
                );

                stmt.setInt(
                    2,
                    idCatatan
                );

                stmt.executeUpdate();
            }

            // 4. Insert Foto
            int idFoto = getNextId(conn, "SELECT MAX(id_foto) FROM FotoKondisi");
            try (PreparedStatement stmt = conn.prepareStatement(sqlInsertFoto)) {
                stmt.setInt(1, idFoto);
                stmt.setInt(2, idCatatan);
                stmt.setString(3, fotoUrl);
                stmt.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private int getNextId(Connection conn, String sql) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) + 1;
            }
        }
        return 1;
    }

    public ArrayList<Transaksi> getTransaksiAktif(User user) {

        ArrayList<Transaksi> list = new ArrayList<>();

        String sql = "SELECT p.*, " +
                "m.brand + ' ' + m.tipe AS nama_mobil, " +
                "m.no_plat " +
                "FROM Peminjaman p " +
                "JOIN Mobil m ON p.id_mobil = m.id_mobil " +
                "WHERE p.id_member = ? " +
                "AND p.status = 'DIPINJAM'";

        try (
                Connection conn = SQLDatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, user.getIdMember());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Transaksi t = new Transaksi(
                        rs.getInt("id_transaksi"),
                        rs.getInt("id_mobil"),
                        rs.getInt("id_cabang"),
                        rs.getInt("id_member"),
                        rs.getString("status"),
                        rs.getInt("total_hari_sewa"),
                        rs.getInt("biaya_sewa"),
                        rs.getInt("total"),
                        rs.getTimestamp("waktu_pinjam").toLocalDateTime(),
                        rs.getTimestamp("waktu_rencana_pengembalian").toLocalDateTime());

                t.setNamaMobil(rs.getString("nama_mobil"));
                t.setPlatMobil(rs.getString("no_plat"));

                list.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<Transaksi> getTransaksiHistory(User user) {
        ArrayList<Transaksi> list = new ArrayList<>();

        String sql = "SELECT p.*, m.brand + ' ' + m.tipe AS nama_mobil, m.no_plat " +
                "FROM Peminjaman p " +
                "JOIN Mobil m ON p.id_mobil = m.id_mobil " +
                "WHERE p.id_member = ? " +
                "ORDER BY p.waktu_pinjam DESC";

        try (Connection conn = SQLDatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, user.getIdMember());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Transaksi t = new Transaksi(
                            rs.getInt("id_transaksi"),
                            rs.getInt("id_mobil"),
                            rs.getInt("id_cabang"),
                            rs.getInt("id_member"),
                            rs.getString("status"),
                            rs.getInt("total_hari_sewa"),
                            rs.getInt("biaya_sewa"),
                            rs.getInt("total"),
                            rs.getTimestamp("waktu_pinjam").toLocalDateTime(),
                            rs.getTimestamp("waktu_rencana_pengembalian").toLocalDateTime());

                    t.setNamaMobil(rs.getString("nama_mobil"));
                    t.setPlatMobil(rs.getString("no_plat"));

                    Timestamp actualReturn = rs.getTimestamp("waktu_aktual_pengembalian");
                    if (actualReturn != null) {
                        t.setWaktuAktualPengembalian(actualReturn.toLocalDateTime());
                    }
                    t.setBiayaKeterlambatan(rs.getInt("biaya_keterlambatan"));
                    t.setCatatan(rs.getString("catatan"));

                    list.add(t);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public boolean approvePeminjaman(
        Transaksi t,
        int idPegawai,
        String deskripsi,
        int tingkatKondisi,
        String fotoUrl) {

        String sqlUpdatePeminjaman =
            "UPDATE Peminjaman " +
            "SET status='DIPINJAM' " +
            "WHERE id_transaksi=?";

        String sqlUpdateMobil =
            "UPDATE Mobil " +
            "SET status='Dipinjam' " +
            "WHERE id_mobil=?";

        String sqlPembayaran =
            "INSERT INTO Pembayaran " +
            "(id_pembayaran,id_transaksi,id_pegawai,waktu_pembayaran,status,jumlah,Metode) " +
            "VALUES (?, ?, ?, GETDATE(), 'MENUNGGU PEMBAYARAN', ?, 'Tunai')";

        String sqlKondisi =
            "INSERT INTO KondisiMobil " +
            "(id_catatan,id_mobil,id_pegawai,tipe_pencatatan,waktu_pencatatan,deskripsi,tingkat_kondisi) " +
            "VALUES (?, ?, ?, 'SEBELUM SEWA', GETDATE(), ?, ?)";

        String sqlFoto =
            "INSERT INTO FotoKondisi " +
            "(id_foto,id_catatan,foto) " +
            "VALUES (?, ?, ?)";

        Connection conn = null;

        try {

            conn = SQLDatabaseConnection.getConnection();

            conn.setAutoCommit(false);

            try (
                PreparedStatement ps =
                    conn.prepareStatement(sqlUpdatePeminjaman)
            ) {

                ps.setInt(
                    1,
                    t.getIdTransaksi()
                );

                ps.executeUpdate();
            }

            try (
                PreparedStatement ps =
                    conn.prepareStatement(sqlUpdateMobil)
            ) {

                ps.setInt(
                    1,
                    t.getIdMobil()
                );

                ps.executeUpdate();
            }

            int idCatatan =
                getNextId(
                    conn,
                    "SELECT MAX(id_catatan) FROM KondisiMobil"
                );

            try (
                PreparedStatement ps =
                    conn.prepareStatement(sqlKondisi)
            ) {

                ps.setInt(
                    1,
                    idCatatan
                );

                ps.setInt(
                    2,
                    t.getIdMobil()
                );

                ps.setInt(
                    3,
                    idPegawai
                );

                ps.setString(
                    4,
                    deskripsi
                );

                ps.setInt(
                    5,
                    tingkatKondisi
                );

                ps.executeUpdate();
            }

            int idFoto =
                getNextId(
                    conn,
                    "SELECT MAX(id_foto) FROM FotoKondisi"
                );

            try (
                PreparedStatement ps =
                    conn.prepareStatement(sqlFoto)
            ) {

                ps.setInt(
                    1,
                    idFoto
                );

                ps.setInt(
                    2,
                    idCatatan
                );

                ps.setString(
                    3,
                    fotoUrl
                );

                ps.executeUpdate();
            }

            int nextIdPembayaran =
                getNextId(
                    conn,
                    "SELECT MAX(id_pembayaran) FROM Pembayaran"
                );

            try (
                PreparedStatement ps =
                    conn.prepareStatement(sqlPembayaran)
            ) {

                ps.setInt(
                    1,
                    nextIdPembayaran
                );

                ps.setInt(
                    2,
                    t.getIdTransaksi()
                );

                ps.setInt(
                    3,
                    idPegawai
                );

                ps.setInt(
                    4,
                    t.getTotal()
                );

                ps.executeUpdate();
            }

            conn.commit();

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            try {
                if (conn != null)
                    conn.rollback();
            } catch (Exception ex) {
            }

        }

        return false;
    }

    public void tampilSemuaKondisiMobil() {

        String sql =
            "SELECT km.*, " +
            "m.brand + ' ' + m.tipe AS nama_mobil, " +
            "m.no_plat " +
            "FROM KondisiMobil km " +
            "JOIN Mobil m ON km.id_mobil = m.id_mobil " +
            "ORDER BY km.waktu_pencatatan DESC";

        try (
            Connection conn = SQLDatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                System.out.println("\n==========================");

                System.out.println(
                    "Mobil : "
                    + rs.getString("nama_mobil")
                );

                System.out.println(
                    "Plat : "
                    + rs.getString("no_plat")
                );

                System.out.println(
                    "Tipe Pencatatan : "
                    + rs.getString("tipe_pencatatan")
                );

                System.out.println(
                    "Waktu : "
                    + rs.getTimestamp("waktu_pencatatan")
                );

                System.out.println(
                    "Deskripsi : "
                    + rs.getString("deskripsi")
                );

                System.out.println(
                    "Tingkat Kondisi : "
                    + rs.getInt("tingkat_kondisi")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}