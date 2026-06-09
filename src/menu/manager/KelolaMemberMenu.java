package menu.manager;

import config.SQLDatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

class Member {

    int idUser;
    String nama;
    String email;
    String noSim;
    String alamat;
    String noHp;
    String status;

    public Member(int idUser, String nama, String email, String noSim, String alamat, String noHp, String status) {
        this.idUser = idUser;
        this.nama = nama;
        this.email = email;
        this.noSim = noSim;
        this.alamat = alamat;
        this.noHp = noHp;
        this.status = status;
    }
}

public class KelolaMemberMenu {

    Scanner sc = new Scanner(System.in);

    private ArrayList<Member> getAllMember() {

        ArrayList<Member> list = new ArrayList<>();

        String sql = """
            SELECT 
                u.id_user,
                u.nama,
                u.email,
                u.no_telp,
                u.alamat,
                m.no_sim,
                m.status
            FROM Users u
            JOIN Member m ON u.id_user = m.id_user
        """;

        try (
            Connection conn = SQLDatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                list.add(new Member(
                    rs.getInt("id_user"),
                    rs.getString("nama"),
                    rs.getString("email"),
                    rs.getString("no_sim"),
                    rs.getString("alamat"),
                    rs.getString("no_telp"),
                    rs.getString("status")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void show() {

        while (true) {

            ArrayList<Member> memberList = getAllMember();

            System.out.println("\n=== KELOLA DATA MEMBER ===");

            for (int i = 0; i < memberList.size(); i++) {

                Member m = memberList.get(i);

                System.out.println((i + 1) + ". " + m.nama + " [" + m.status + "]");
            }

            System.out.println("\n0. Kembali");

            System.out.print("Pilih member: ");
            int pilih = sc.nextInt();
            sc.nextLine();

            if (pilih == 0) return;

            int index = pilih - 1;

            if (index < 0 || index >= memberList.size()) {
                System.out.println("Member tidak ditemukan!");
                continue;
            }

            detailMember(memberList.get(index));
        }
    }

    private void detailMember(Member m) {

        System.out.println("\n=== DETAIL MEMBER ===");
        System.out.println("Nama    : " + m.nama);
        System.out.println("Email   : " + m.email);
        System.out.println("No SIM  : " + m.noSim);
        System.out.println("Alamat  : " + m.alamat);
        System.out.println("No HP   : " + m.noHp);
        System.out.println("Status  : " + m.status);

        System.out.println("\n1. Ubah Status (Aktif / Nonaktif)");
        System.out.println("2. Kembali");

        System.out.print("Pilih: ");
        int pilih = sc.nextInt();
        sc.nextLine();

        if (pilih == 1) {
            ubahStatus(m.idUser, m.status);
        }
    }

    private void ubahStatus(int idUser, String currentStatus) {

        String newStatus = currentStatus.equalsIgnoreCase("aktif")
                ? "nonaktif"
                : "aktif";

        String sql = "UPDATE Member SET status = ? WHERE id_user = ?";

        try (
            Connection conn = SQLDatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, newStatus);
            ps.setInt(2, idUser);
            ps.executeUpdate();

            System.out.println("Status berhasil diubah menjadi: " + newStatus);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}