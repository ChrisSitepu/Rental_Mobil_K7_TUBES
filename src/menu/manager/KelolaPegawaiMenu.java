package menu.manager;

import java.util.ArrayList;
import java.util.Scanner;
import model.User;
import model.Role;
import service.UserService;

public class KelolaPegawaiMenu {

    private UserService userService = new UserService();
    private Scanner sc = new Scanner(System.in);

    public void show() {

        while (true) {

            System.out.println("\n=== KELOLA DATA PEGAWAI ===");

            ArrayList<User> pegawaiList = userService.getAllByRole(Role.PEGAWAI);

            for (int i = 0; i < pegawaiList.size(); i++) {

                User p = pegawaiList.get(i);

                System.out.println(
                        (i + 1) + ". " + p.getNama() + " (" + p.getEmail() + ")"
                );
            }

            System.out.println("0. Kembali");

            System.out.print("Pilih pegawai: ");
            int pilih = sc.nextInt();
            sc.nextLine();

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

        System.out.println("\n=== DETAIL PEGAWAI ===");

        System.out.println("Nama     : " + p.getNama());
        System.out.println("Email    : " + p.getEmail());
        System.out.println("Alamat   : " + p.getAlamat());
        System.out.println("No HP    : " + p.getNomorTelepon());

        System.out.println("\n1. Hapus Pegawai");
        System.out.println("2. Kembali");

        System.out.print("Pilih: ");
        int pilih = sc.nextInt();
        sc.nextLine();

        switch (pilih) {

            case 1:
                if (userService.deleteUserByEmail(p.getEmail())) {
                    System.out.println("Pegawai berhasil dihapus dari database!");
                }
                break;

            case 2:
                return;

            default:
                System.out.println("Pilihan tidak valid!");
        }
    }
}