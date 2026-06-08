package menu.pegawai;

import java.util.Scanner;

import model.User;

public class PegawaiMenu {

    private Scanner sc = new Scanner(System.in);

    private User user;

    public PegawaiMenu(User user){
        this.user = user;
    }

    public void show(){

        boolean running = true;

        while(running){

            System.out.println(
                    "\n===== MENU PEGAWAI ====="
            );

            System.out.println(
                    "1. Lihat Transaksi Handle"
            );

            System.out.println(
                    "2. Verifikasi Pembayaran"
            );

            System.out.println(
                    "3. Catat Kondisi Mobil"
            );

            System.out.println(
                    "4. Verifikasi Pengembalian"
            );

            System.out.println(
                    "5. Profil"
            );

            System.out.println(
                    "6. Logout"
            );

            System.out.print("Pilih: ");

            int pilih =
                    Integer.parseInt(sc.nextLine());

            switch(pilih){

                case 1:
                    new HandleTransaksiMenu()
                            .show();
                    break;

                case 2:
                    new VerifikasiPembayaranMenu(user)
                            .show();
                    break;

                case 3:
                    new KondisiMobilMenu()
                            .show();
                    break;

                case 4:
                    new VerifikasiPengembalianMenu(user)
                            .show();
                    break;

                case 5:
                    new ProfilePegawaiMenu(user)
                            .show();
                    break;

                case 6:
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