package menu.manager;

import config.SQLDatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;
import model.Role;
import model.User;

public class KelolaPegawaiMenu {
    Scanner sc = new Scanner(System.in);

    private ArrayList<User> getAllPegawai() {
        ArrayList<User> list = new ArrayList<>();
        String sql = """
            SELECT
                u.id_user,
                p.id_pegawai,
                p.id_cabang,
                u.nama,
                u.email,
                u.alamat,
                u.no_telp,
                p.status
            FROM Users u
            JOIN Pegawai p
                ON u.id_user = p.id_user
            WHERE u.role = 'PEGAWAI'
        """;

        try (
            Connection conn = SQLDatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                list.add(new User(
                    rs.getInt("id_user"),
                    0, // idMember
                    rs.getInt("id_pegawai"),
                    rs.getInt("id_cabang"),
                    rs.getString("nama"),
                    rs.getString("email"),
                    "", // password
                    rs.getString("no_telp"),
                    rs.getString("alamat"),
                    "-", // noSim
                    Role.PEGAWAI
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void show() {
        while (true) {
            ArrayList<User> pegawaiList = getAllPegawai();
            System.out.println("\n=== KELOLA DATA PEGAWAI ===");

            if (pegawaiList.isEmpty()) {
                System.out.println("Tidak ada data pegawai.");
            }

            for (int i = 0; i < pegawaiList.size(); i++) {
                User p = pegawaiList.get(i);
                System.out.println((i + 1) + ". " + p.getNama() + " (Cabang ID: " + p.getCabang() + ")");
            }

            System.out.println("99. Tambah Pegawai");
            System.out.println("0. Kembali");

            System.out.print("Pilih: ");
            int pilih;
            try {
                pilih = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                continue;
            }

            if (pilih == 99) {
                tambahPegawai();
                continue;
            }

            if (pilih == 0) {
                return;
            }

            int index = pilih - 1;
            if (index < 0 || index >= pegawaiList.size()) {
                System.out.println("Pegawai tidak ditemukan!");
                continue;
            }

            detailPegawai(pegawaiList.get(index));
        }
    }

    private void detailPegawai(User p) {
        while (true) {
            System.out.println("\n=== DETAIL PEGAWAI ===");
            System.out.println("Nama      : " + p.getNama());
            System.out.println("Email     : " + p.getEmail());
            System.out.println("Alamat    : " + p.getAlamat());
            System.out.println("No HP     : " + p.getNomorTelepon());
            System.out.println("ID Cabang : " + p.getCabang());

            System.out.println("\n1. Edit Cabang");
            System.out.println("2. Hapus Pegawai");
            System.out.println("3. Kembali");

            System.out.print("Pilih: ");
            int pilih;
            try {
                pilih = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                continue;
            }

            switch (pilih) {
                case 1:
                    editCabang(p);
                    return;
                case 2:
                    hapusPegawai(p);
                    return;
                case 3:
                    return;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }

    private void editCabang(User p) {
        System.out.println("\n=== EDIT CABANG PEGAWAI ===");
        System.out.print("Masukkan ID Cabang baru: ");
        int newCabang = Integer.parseInt(sc.nextLine());

        String sql = "UPDATE Pegawai SET id_cabang = ? WHERE id_pegawai = ?";
        try (
            Connection conn = SQLDatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, newCabang);
            ps.setInt(2, p.getIdPegawai());
            if (ps.executeUpdate() > 0) {
                System.out.println("Cabang berhasil diperbarui!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hapusPegawai(User p) {
        String sqlPegawai = "UPDATE Pegawai SET status = 'Nonaktif' WHERE id_pegawai = ?";
        try (Connection conn = SQLDatabaseConnection.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(sqlPegawai)) {
                ps.setInt(1, p.getIdPegawai());
                int rows = ps.executeUpdate();
                if (rows > 0) {
                    System.out.println("Pegawai berhasil dinonaktifkan!");
                } else {
                    System.out.println("Pegawai tidak ditemukan!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void tambahPegawai() {
        try (Connection conn = SQLDatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            System.out.println("\n=== TAMBAH PEGAWAI ===");
            System.out.print("Nama : "); String nama = sc.nextLine();
            System.out.print("Email : "); String email = sc.nextLine();
            System.out.print("No Telp : "); String noTelp = sc.nextLine();
            System.out.print("Alamat : "); String alamat = sc.nextLine();
            System.out.print("Password : "); String password = sc.nextLine();
            System.out.print("ID Cabang : "); int idCabang = Integer.parseInt(sc.nextLine());

            String sqlUser = "INSERT INTO Users (nama,email,no_telp,alamat,password,role) VALUES (?,?,?,?,?, 'PEGAWAI')";
            int idUser;
            try (PreparedStatement ps = conn.prepareStatement(sqlUser, PreparedStatement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, nama);
                ps.setString(2, email);
                ps.setString(3, noTelp);
                ps.setString(4, alamat);
                ps.setString(5, password);
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                idUser = rs.getInt(1);
            }

            String sqlPegawai = "INSERT INTO Pegawai (id_user, id_jabatan, id_cabang, status) VALUES (?, 2, ?, 'Aktif')";
            try (PreparedStatement ps = conn.prepareStatement(sqlPegawai)) {
                ps.setInt(1, idUser);
                ps.setInt(2, idCabang);
                ps.executeUpdate();
            }

            conn.commit();
            System.out.println("Pegawai berhasil ditambahkan!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}