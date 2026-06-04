package menu.member;

import java.util.Scanner;
import model.User;

public class ProfileMenu {

    private User user;
    private Scanner sc = new Scanner(System.in);

    public ProfileMenu(User user) {
        this.user = user;
    }

    public void show() {

        System.out.println("\n===== PROFIL MEMBER =====");

        System.out.println("Nama          : " + user.getNama());
        System.out.println("Email         : " + user.getEmail());
        System.out.println("No Telepon    : " + user.getNomorTelepon());
        System.out.println("Role          : " + user.getRole());

        System.out.println("\n0. Kembali");
        System.out.print("Pilih: ");

        int kembali = Integer.parseInt(sc.nextLine());

        if (kembali == 0) {
            return;
        }
    }
}