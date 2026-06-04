package menu.manager;

import java.util.ArrayList;
import java.util.Scanner;
import model.User;
import model.Role;
import service.UserService;

public class KelolaMemberMenu {

    private UserService userService = new UserService();
    private Scanner sc = new Scanner(System.in);

    public void show() {

        while (true) {

            System.out.println("\n=== KELOLA DATA MEMBER ===");

            ArrayList<User> memberList = userService.getAllByRole(Role.MEMBER);

            for (int i = 0; i < memberList.size(); i++) {
                System.out.println((i + 1) + ". " + memberList.get(i).getNama() + " (" + memberList.get(i).getEmail() + ")");
            }

            System.out.println("\n0. Kembali");

            System.out.print("Pilih member: ");
            int pilih = sc.nextInt();
            sc.nextLine();

            if (pilih == 0) {
                return;
            }

            int index = pilih - 1;

            if (index < 0 || index >= memberList.size()) {
                System.out.println("Member tidak ditemukan!");
                continue;
            }

            detailMember(memberList.get(index));
        }
    }

    private void detailMember(User m) {

        System.out.println("\n=== DETAIL MEMBER ===");
        System.out.println("Nama    : " + m.getNama());
        System.out.println("Email   : " + m.getEmail());
        System.out.println("No SIM  : " + m.getNoSim());
        System.out.println("Alamat  : " + m.getAlamat());
        System.out.println("No HP   : " + m.getNomorTelepon());

        System.out.println("\n1. Hapus Member");
        System.out.println("2. Kembali");

        System.out.print("Pilih: ");
        int pilih = sc.nextInt();
        sc.nextLine();

        switch (pilih) {

            case 1:
                if (userService.deleteUserByEmail(m.getEmail())) {
                    System.out.println("Member berhasil dihapus dari database!");
                }
                break;

            case 2:
                return;

            default:
                System.out.println("Pilihan tidak valid!");
        }
    }
}