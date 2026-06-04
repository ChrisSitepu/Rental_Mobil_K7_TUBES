package menu;

import java.time.LocalDate;
import java.util.Scanner;

import model.Member;
import service.UserService;

public class SignUpMenu {

    private Scanner sc = new Scanner(System.in);
    private UserService userService = new UserService();

    public void show() {

        System.out.println("\n===== SIGN UP MEMBER =====");

        System.out.print("Nama Lengkap  : ");
        String nama = sc.nextLine();

        System.out.print("Email Baru    : ");
        String email = sc.nextLine();

        System.out.print("Password Baru : ");
        String password = sc.nextLine();

        System.out.print("No Telp       : ");
        String no_hp = sc.nextLine();

        System.out.print("Alamat        : ");
        String alamat = sc.nextLine();

        System.out.print("No SIM        : ");
        String sim = sc.nextLine();

        System.out.print("Tanggal SIM Berlaku (yyyy-MM-dd) : ");
        String inputTanggalSim = sc.nextLine();

        LocalDate tanggalSim = null;
        try {
            tanggalSim = LocalDate.parse(inputTanggalSim);
        } catch (Exception e) {
            System.out.println("\nFormat tanggal salah! Gunakan format yyyy-MM-dd (contoh: 2028-12-31).");
            return;
        }

        Member newUser = new Member(
            nama,
            email,
            password,
            no_hp,
            alamat,
            sim,
            tanggalSim
        );

        if (userService.registerMember(newUser)) {
            System.out.println("\nRegistrasi berhasil untuk " + nama + "!");
            System.out.println("Akun member Anda telah aktif.");
        } else {
            System.out.println("\nRegistrasi gagal! Silakan coba lagi.");
        }

        System.out.println("Kembali ke login...");
    }
}