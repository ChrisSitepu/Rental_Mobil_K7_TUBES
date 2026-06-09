package menu.manager;

import config.SQLDatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class KelolaCabangMenu {

    private Scanner sc = new Scanner(System.in);

    public void show() {

        while (true) {

            System.out.println("\n=== KELOLA CABANG ===");

            tampilCabang();

            System.out.println("\nMenu:");
            System.out.println("1. Tambah Cabang");
            System.out.println("2. Edit Cabang");
            System.out.println("3. Kembali");

            System.out.print("Pilih: ");
            int pilih = sc.nextInt();
            sc.nextLine();

            switch (pilih) {

                case 1:
                    tambahCabang();
                    break;

                case 2:
                    editCabang();
                    break;

                case 3:
                    return;

                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }

    private List<Integer> tampilCabang() {

        String sql = "SELECT id_cabang, nama FROM Cabang ORDER BY id_cabang";

        List<Integer> idList = new ArrayList<>();

        try (
            Connection conn = SQLDatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            int no = 1;

            while (rs.next()) {

                int id = rs.getInt("id_cabang");
                String nama = rs.getString("nama");

                idList.add(id);

                System.out.println(no + ". " + nama);

                no++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idList;
    }

    private void tambahCabang() {

        try {

            System.out.println("Ketik 'cancel' kapan saja untuk batal.\n");

            String nama = input("Nama Cabang: ");
            String jam = input("Jam Operasional (HH:mm:ss): ");
            String email = input("Email: ");
            String telepon = input("No Telepon: ");
            String alamat = input("Alamat: ");

            String sql =
            "INSERT INTO Cabang(nama, jam_operasional, email, no_telepon, alamat) " +
            "VALUES (?, ?, ?, ?, ?)";

            try (
                Connection conn = SQLDatabaseConnection.getConnection();
                PreparedStatement ps =
                    conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ) {

                ps.setString(1, nama);
                ps.setString(2, jam);
                ps.setString(3, email);
                ps.setString(4, telepon);
                ps.setString(5, alamat);

                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {
                    System.out.println("Cabang berhasil ditambahkan dengan ID " + rs.getInt(1));
                }
            }

        } catch (RuntimeException e) {
            if (e.getMessage().equals("CANCELLED")) {
                System.out.println("Penambahan cabang dibatalkan.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void editCabang() {

        try {

            List<Integer> idList = tampilCabang();

            System.out.print("Pilih Cabang (nomor): ");
            int index = sc.nextInt();
            sc.nextLine();

            if (index < 1 || index > idList.size()) {
                System.out.println("Pilihan tidak valid!");
                return;
            }

            int id = idList.get(index - 1);

            tampilDetailCabang(id);

            System.out.println("\nField yang ingin diubah:");
            System.out.println("1. Nama");
            System.out.println("2. Jam Operasional");
            System.out.println("3. Email");
            System.out.println("4. No Telepon");
            System.out.println("5. Alamat");
            System.out.println("6. Edit Status Cabang");
            System.out.print("Pilih: ");

            int pilihan = sc.nextInt();
            sc.nextLine();

            String column = null;
            String newValue = null;

            switch (pilihan) {

                case 1 -> {
                    column = "nama";
                    newValue = input("Nama baru: ");
                }
                case 2 -> {
                    column = "jam_operasional";
                    newValue = input("Jam baru: ");
                }
                case 3 -> {
                    column = "email";
                    newValue = input("Email baru: ");
                }
                case 4 -> {
                    column = "no_telepon";
                    newValue = input("No telepon baru: ");
                }
                case 5 -> {
                    column = "alamat";
                    newValue = input("Alamat baru: ");
                }
                case 6 -> {
                    deleteCabang(id);
                    return;
                }
                default -> {
                    System.out.println("Pilihan tidak valid!");
                    return;
                }
            }

            if (newValue == null) {
                System.out.println("Field dilewati (tidak diubah).");
                return;
            }

            updateCabangDynamic(id, column, newValue);

        } catch (RuntimeException e) {
            if (e.getMessage().equals("CANCEL")) {
                System.out.println("Edit dibatalkan sepenuhnya.");
            }
        }
    }

    private void tampilDetailCabang(int id) {

        String sql = "SELECT * FROM Cabang WHERE id_cabang = ?";

        try (
            Connection conn = SQLDatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("\n--- DETAIL CABANG ---");
                System.out.println("Nama: " + rs.getString("nama"));
                System.out.println("Jam Operasional: " + rs.getString("jam_operasional"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Telepon: " + rs.getString("no_telepon"));
                System.out.println("Alamat: " + rs.getString("alamat"));
            } else {
                System.out.println("Cabang tidak ditemukan!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateCabangDynamic(int id, String column, String value) {

        String sql = "UPDATE Cabang SET " + column + " = ? WHERE id_cabang = ?";

        try (
            Connection conn = SQLDatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, value);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Cabang berhasil diupdate!");
            } else {
                System.out.println("Cabang tidak ditemukan!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteCabang(int id) {

        String sql = "UPDATE Cabang SET status = 'Nonaktif' WHERE id_cabang = ?";

        try (
            Connection conn = SQLDatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Cabang berhasil dinonaktifkan!");
            } else {
                System.out.println("Cabang tidak ditemukan!");
            }

        } catch (SQLException e) {
            System.out.println(
                "Cabang tidak dapat dinonaktifkan karena masih digunakan data lain."
            );
        }
    }
    
    private String input(String prompt) {

        System.out.print(prompt);
        String value = sc.nextLine();

        if (value.equalsIgnoreCase("cancel")) {
            throw new RuntimeException("CANCEL");
        }

        if (value.trim().isEmpty()) {
            return null; // skip update
        }

        return value;
    }
}