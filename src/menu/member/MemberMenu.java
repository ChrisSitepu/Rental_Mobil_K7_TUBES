package menu.member;

import java.util.Scanner;

import model.User;

public class MemberMenu {

    private Scanner sc = new Scanner(System.in);
    private User user;

    public MemberMenu(User user){
        this.user = user;
    }

    public void show(){

        boolean running = true;

        while(running){

            System.out.println("\n===== MEMBER MENU =====");
            System.out.println("1. Peminjaman");
            System.out.println("2. Pengembalian");
            System.out.println("3. Detail Transaksi");
            System.out.println("4. Profil");
            System.out.println("5. Logout");

            System.out.print("Pilih: ");

            int pilih =
                    Integer.parseInt(sc.nextLine());

            switch(pilih){

                case 1:
                    new PeminjamanMenu(user).show();
                    break;

                case 2:
                    new PengembalianMenu().show();
                    break;

                case 3:
                    new TransaksiMenu().show();
                    break;

                case 4:
                    new ProfileMenu(user).show();
                    break;

                case 5:
                    running = false;
                    break;
            }
        }
    }
}