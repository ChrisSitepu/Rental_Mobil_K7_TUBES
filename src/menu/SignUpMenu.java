package menu;

import java.util.Scanner;

import service.AuthService;

public class SignUpMenu {

    private Scanner sc = new Scanner(System.in);
    private AuthService authService = new AuthService();

    public void show() {

        System.out.print("Nama : ");
        String nama = sc.nextLine();

        System.out.print("Email Baru : ");
        String email = sc.nextLine();

        System.out.print("Password Baru : ");
        String password = sc.nextLine();

        System.out.print("No Telp : ");
        String noHp = sc.nextLine();

        System.out.print("Alamat : ");
        String alamat = sc.nextLine();

        System.out.print("No SIM : ");
        String sim = sc.nextLine();

        boolean berhasil =
                authService.register(
                        nama,
                        email,
                        password,
                        noHp,
                        alamat,
                        sim
                );

        if(berhasil) {
            System.out.println("Akun berhasil dibuat!");
        } else {
            System.out.println("Email sudah digunakan!");
        }
    }
}