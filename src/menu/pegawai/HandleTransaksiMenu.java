package menu.pegawai;

import java.util.ArrayList;
import java.util.Scanner;

import model.Transaksi;
import service.TransaksiService;

public class HandleTransaksiMenu {

    private Scanner sc = new Scanner(System.in);
    private TransaksiService transaksiService = new TransaksiService();

    public void show() {

        ArrayList<Transaksi> list = transaksiService.getAllActivePeminjaman();

        if (list.isEmpty()) {
            System.out.println("\nBelum ada transaksi aktif.");
            return;
        }

        while (true) {

            System.out.println("\n=== TRANSAKSI YANG DIHANDLE ===");

            for (int i = 0; i < list.size(); i++) {
                Transaksi t = list.get(i);
                System.out.println(
                        (i + 1)
                        + ". "
                        + t.getNamaMobil()
                        + " - Member: "
                        + t.getNamaMember()
                        + " - Status: "
                        + t.getStatus()
                );
            }

            System.out.println("0. Kembali");
            System.out.print("Pilih transaksi: ");

            int pilih;
            try {
                pilih = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Input tidak valid!");
                continue;
            }

            if (pilih == 0) {
                return;
            }

            if (pilih < 1 || pilih > list.size()) {
                System.out.println("Pilihan tidak valid!");
                continue;
            }

            showDetail(list.get(pilih - 1));
        }
    }

    private void showDetail(Transaksi t) {

        System.out.println("\n===== DETAIL TRANSAKSI =====");
        System.out.println("Nama Member      : " + t.getNamaMember());
        System.out.println("Mobil            : " + t.getNamaMobil());
        System.out.println("Plat Mobil       : " + t.getPlatMobil());
        System.out.println("Biaya Sewa       : Rp" + t.getBiayaSewa());
        System.out.println("Total Hari Sewa  : " + t.getTotalHariSewa() + " Hari");
        System.out.println("Tanggal Pinjam   : " + t.getWaktuPinjam());
        System.out.println("Rencana Kembali  : " + t.getWaktuRencanaPengembalian());
        System.out.println("Status           : " + t.getStatus());

        System.out.println("\nTekan enter untuk kembali...");
        sc.nextLine();
    }
}