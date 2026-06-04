package menu.pegawai;

import java.util.Scanner;

public class VerifikasiPembayaranMenu {

        private Scanner sc = new Scanner(System.in);

        public void show() {

                System.out.println(
                                "\n=== VERIFIKASI PEMBAYARAN ===");

                System.out.println(
                                "1. Toyota Avanza - Transfer Bank");

                System.out.print(
                                "Pilih transaksi: ");

                int pilih = Integer.parseInt(sc.nextLine());

                System.out.print(
                                "Approve pembayaran? (y/n): ");

                String confirm = sc.nextLine();

                if (confirm.equalsIgnoreCase("y")) {

                        System.out.println(
                                        "Pembayaran berhasil diverifikasi!");

                } else {

                        System.out.println(
                                        "Pembayaran ditolak!");
                }
        }
}