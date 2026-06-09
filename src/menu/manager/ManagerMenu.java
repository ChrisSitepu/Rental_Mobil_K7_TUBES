package menu.manager;

import java.util.Scanner;
import model.User;

public class ManagerMenu {

    private Scanner sc = new Scanner(System.in);

    private User user;

    public ManagerMenu(User user){
        this.user = user;
    }

    public void show(){

        boolean running = true;

        while(running){

            System.out.println(
                    "\n===== MENU MANAGER ====="
            );

            System.out.println(
                    "1. Kelola Data Mobil"
            );

            System.out.println(
                    "2. Kelola Data Pegawai"
            );

            System.out.println(
                    "3. Kelola Data Member"
            );

            System.out.println(
                    "4. Kelola Data Cabang"
            );

            System.out.println(
                    "5. Akses Laporan"
            );

            System.out.println(
                    "6. Profil Manager"
            );

            System.out.println(
                    "7. Logout"
            );

            System.out.print("Pilih: ");

            int pilih =
                    Integer.parseInt(sc.nextLine());

            switch(pilih){

                case 1:
                    new KelolaMobilMenu().show();
                    break;

                case 2:
                    new KelolaPegawaiMenu().show();
                    break;

                case 3:
                    new KelolaMemberMenu().show();
                    break;

                case 4:
                    new KelolaCabangMenu().show();
                    break;

                case 5:
                    new LaporanMenu().show();
                    break;

                case 6:
                    new ProfileManagerMenu(user)
                            .show();
                    break;

                case 7:
                    running = false;
                    break;

                default:
                    System.out.println(
                            "Menu tidak valid!"
                    );
            }
        }
    }
}