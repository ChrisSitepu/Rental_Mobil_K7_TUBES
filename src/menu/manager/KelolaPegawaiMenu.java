package menu.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

import config.SQLDatabaseConnection;

class Pegawai {

    int idUser;
    int idPegawai;
    int idCabang;

    String nama;
    String email;
    String alamat;
    String noHp;

    boolean aktif;

    public Pegawai(
            int idUser,
            int idPegawai,
            int idCabang,
            String nama,
            String email,
            String alamat,
            String noHp,
            boolean aktif) {

        this.idUser = idUser;
        this.idPegawai = idPegawai;
        this.idCabang = idCabang;

        this.nama = nama;
        this.email = email;
        this.alamat = alamat;
        this.noHp = noHp;
        this.aktif = aktif;
    }
}

public class KelolaPegawaiMenu {
    Scanner sc = new Scanner(System.in);

    private ArrayList<Pegawai> getAllPegawai() {

        ArrayList<Pegawai> list = new ArrayList<>();

        String sql = """
            SELECT
                u.id_user,
                p.id_pegawai,
                b.id_cabang,
                u.nama,
                u.email,
                u.alamat,
                u.no_telp,
                p.status
            FROM Users u
            JOIN Pegawai p
                ON u.id_user = p.id_user
            LEFT JOIN Bertugas_Di b
                ON p.id_pegawai = b.id_pegawai
            WHERE u.role = 'PEGAWAI'
        """;

        try (
            Connection conn = SQLDatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

            boolean aktif = "Aktif".equalsIgnoreCase(rs.getString("status"));

                list.add(
                    new Pegawai(
                        rs.getInt("id_user"),
                        rs.getInt("id_pegawai"),
                        rs.getInt("id_cabang"),
                        rs.getString("nama"),
                        rs.getString("email"),
                        rs.getString("alamat"),
                        rs.getString("no_telp"),
                        aktif
                    )
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void show() {

        while (true) {

            ArrayList<Pegawai> pegawaiList = getAllPegawai();

            System.out.println("\n=== KELOLA DATA PEGAWAI ===");

            if (pegawaiList.isEmpty()) {
                System.out.println("Tidak ada data pegawai.");
            }

            for (int i = 0; i < pegawaiList.size(); i++) {

                Pegawai p = pegawaiList.get(i);

                String status = p.aktif ? "Aktif" : "Nonaktif";

                System.out.println(
                    (i + 1) + ". " + p.nama + " (" + status + ")"
                );
            }

            System.out.println("99. Tambah Pegawai");
            System.out.println("0. Kembali");

            System.out.print("Pilih: ");
            int pilih = sc.nextInt();
            sc.nextLine();

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

    private void detailPegawai(Pegawai p) {

        while (true) {

            System.out.println("\n=== DETAIL PEGAWAI ===");

            System.out.println("Nama     : " + p.nama);
            System.out.println("Email    : " + p.email);
            System.out.println("Alamat   : " + p.alamat);
            System.out.println("No HP    : " + p.noHp);
            System.out.println("Status   : " + (p.aktif ? "Aktif" : "Nonaktif"));

            System.out.println("\n1. Edit Pegawai");
            System.out.println("2. Nonaktifkan Pegawai");
            System.out.println("3. Kembali");

            System.out.print("Pilih: ");
            int pilih = sc.nextInt();
            sc.nextLine();

            switch (pilih) {

                case 1:
                    editPegawai(p);
                    return; // balik ke list biar refresh data
                case 2:
                    updateStatusPegawai(p.idUser, false);
                    System.out.println("Pegawai dinonaktifkan!");
                    return;
                case 3:
                    return;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }

        private void updateStatusPegawai(int idUser, boolean aktif) {

        String sql = "UPDATE Pegawai SET status = ? WHERE id_user = ?";

        try (
            Connection conn = SQLDatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, aktif ? "Aktif" : "Nonaktif");
            ps.setInt(2, idUser);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        private void editPegawai(Pegawai p) {

            try (Connection conn = SQLDatabaseConnection.getConnection()) {

                conn.setAutoCommit(false);

                System.out.println("\n=== EDIT PEGAWAI ===");
                System.out.println("Kosongkan jika tidak ingin mengubah");

                System.out.print("Nama baru (" + p.nama + "): ");
                String nama = sc.nextLine();

                System.out.print("No Telp baru (" + p.noHp + "): ");
                String noTelp = sc.nextLine();

                System.out.print("Alamat baru (" + p.alamat + "): ");
                String alamat = sc.nextLine();

                System.out.print("Status (Aktif/Nonaktif) (" + (p.aktif ? "Aktif" : "Nonaktif") + "): ");
                String status = sc.nextLine();

                // fallback ke data lama jika kosong
                if (nama.isBlank()) nama = p.nama;
                if (noTelp.isBlank()) noTelp = p.noHp;
                if (alamat.isBlank()) alamat = p.alamat;

                if (status.isBlank()) {
                    status = p.aktif ? "Aktif" : "Nonaktif";
                } else if (!status.equalsIgnoreCase("Aktif") && !status.equalsIgnoreCase("Nonaktif")) {
                    System.out.println("Status tidak valid!");
                    return;
                }

                // ===== UPDATE USERS =====
                String sqlUser = """
                    UPDATE Users
                    SET nama = ?, no_telp = ?, alamat = ?
                    WHERE id_user = ?
                """;

                try (PreparedStatement ps = conn.prepareStatement(sqlUser)) {
                    ps.setString(1, nama);
                    ps.setString(2, noTelp);
                    ps.setString(3, alamat);
                    ps.setInt(4, p.idUser);
                    ps.executeUpdate();
                }

                // ===== UPDATE PEGAWAI =====
                String sqlPegawai = """
                    UPDATE Pegawai
                    SET status = ?
                    WHERE id_user = ?
                """;

                try (PreparedStatement ps = conn.prepareStatement(sqlPegawai)) {
                    ps.setString(1, status);
                    ps.setInt(2, p.idUser);
                    ps.executeUpdate();
                }

                conn.commit();

                System.out.println("Data pegawai berhasil diupdate!");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void tambahPegawai() {

    try (Connection conn = SQLDatabaseConnection.getConnection()) {

        conn.setAutoCommit(false);

        System.out.println("\n=== TAMBAH PEGAWAI ===");

        System.out.print("Nama : ");
        String nama = sc.nextLine();

        System.out.print("Email : ");
        String email = sc.nextLine();

        System.out.print("No Telp : ");
        String noTelp = sc.nextLine();

        System.out.print("Alamat : ");
        String alamat = sc.nextLine();

        System.out.print("Password : ");
        String password = sc.nextLine();

        ArrayList<Integer> idCabangList = new ArrayList<>();

        System.out.println("\n=== DAFTAR CABANG ===");

        String sqlCabang = """
            SELECT id_cabang, nama
            FROM Cabang
            ORDER BY id_cabang
        """;

        try (
            PreparedStatement ps = conn.prepareStatement(sqlCabang);
            ResultSet rs = ps.executeQuery()
        ) {

            int no = 1;

            while (rs.next()) {

                idCabangList.add(rs.getInt("id_cabang"));

                System.out.println(
                    no + ". " + rs.getString("nama")
                );

                no++;
            }
        }

        System.out.print("Pilih cabang: ");
        int pilihanCabang = sc.nextInt();
        sc.nextLine();

        if (pilihanCabang < 1 || pilihanCabang > idCabangList.size()) {
            System.out.println("Cabang tidak valid!");
            return;
        }

        int idCabang = idCabangList.get(pilihanCabang - 1);

        // USERS
        String sqlUser = """
            INSERT INTO Users
            (nama,email,no_telp,alamat,password,role)
            VALUES (?,?,?,?,?,'PEGAWAI')
        """;

        int idUser;

        try (
            PreparedStatement ps = conn.prepareStatement(
                sqlUser,
                PreparedStatement.RETURN_GENERATED_KEYS
            )
        ) {

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

        // PEGAWAI
        String sqlPegawai = """
            INSERT INTO Pegawai
            (id_user,id_jabatan,status)
            VALUES (?,2,'Aktif')
        """;

        int idPegawai;

        try (
            PreparedStatement ps = conn.prepareStatement(
                sqlPegawai,
                PreparedStatement.RETURN_GENERATED_KEYS
            )
        ) {

            ps.setInt(1, idUser);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();

            idPegawai = rs.getInt(1);
        }

        // BERTUGAS_DI
        String sqlBertugas = """
            INSERT INTO Bertugas_Di
            (id_pegawai,id_cabang)
            VALUES (?,?)
        """;

        try (PreparedStatement ps = conn.prepareStatement(sqlBertugas)) {

            ps.setInt(1, idPegawai);
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