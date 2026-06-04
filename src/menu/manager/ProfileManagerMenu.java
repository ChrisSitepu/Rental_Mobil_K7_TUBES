package menu.manager;

import model.User;

import java.util.Scanner;

public class ProfileManagerMenu {

    private User user;
    private Scanner sc = new Scanner(System.in);

    public ProfileManagerMenu(User user){
        this.user = user;
    }

    public void show(){

        while (true){

            System.out.println("\n===== PROFIL MANAGER =====");

            System.out.println("1. Nama   : " + user.getNama());
            System.out.println("2. Email  : " + user.getEmail());
            System.out.println("3. No HP  : " + user.getNomorTelepon());

            System.out.println("0. Kembali");

            System.out.print("Pilih data yang ingin diedit: ");
            int pilih = sc.nextInt();
            sc.nextLine();

            switch (pilih){

                case 1:

                    System.out.print("Masukkan nama baru: ");
                    String namaBaru = sc.nextLine();

                    user.setNama(namaBaru);

                    System.out.println("Nama berhasil diubah!");
                    break;

                case 2:

                    System.out.print("Masukkan email baru: ");
                    String emailBaru = sc.nextLine();

                    user.setEmail(emailBaru);

                    System.out.println("Email berhasil diubah!");
                    break;

                case 3:

                    System.out.print("Masukkan nomor HP baru: ");
                    String hpBaru = sc.nextLine();

                    user.setNomorTelepon(hpBaru);

                    System.out.println("Nomor HP berhasil diubah!");
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }
}