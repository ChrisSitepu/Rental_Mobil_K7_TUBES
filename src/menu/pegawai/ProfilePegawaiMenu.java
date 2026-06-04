package menu.pegawai;

import java.util.Scanner;

import model.User;

public class ProfilePegawaiMenu {

    private User user;

    private Scanner sc =
            new Scanner(System.in);

    public ProfilePegawaiMenu(User user){
        this.user = user;
    }

    public void show(){

        System.out.println(
                "\n===== PROFIL PEGAWAI ====="
        );

        System.out.println(
                "Nama       : "
                + user.getNama()
        );

        System.out.println(
                "Email      : "
                + user.getEmail()
        );

        System.out.println(
                "No Telp    : "
                + user.getNomorTelepon()
        );

        sc.nextLine();
    }
}