package menu.member;

import java.util.ArrayList;
import java.util.Scanner;

import model.Transaksi;
import model.User;
import service.TransaksiService;

public class TransaksiMenu {

    private Scanner sc = new Scanner(System.in);
    private User user;
    private TransaksiService transaksiService = new TransaksiService();

    public TransaksiMenu(User user) {
        this.user = user;
    }

    public void show() {

        while (true) {

            System.out.println("\n===== TRANSAKSI =====");
            System.out.println("1. Berlangsung");
            System.out.println("2. Histori");
            System.out.println("3. Kembali");

            System.out.print("Pilih: ");

            int pilih;
            try {
                pilih = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Input tidak valid!");
                continue;
            }

            switch (pilih) {
                case 1:
                    showBerlangsung();
                    break;
                case 2:
                    showHistori();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Menu tidak valid!");
            }
        }
    }

    private void showBerlangsung() {
        System.out.println("\n=== BERLANGSUNG ===");
        ArrayList<Transaksi> list = transaksiService.getTransaksiAktif(user);
        tampilkanDetail(list);
    }

    private void showHistori() {
        System.out.println("\n=== HISTORI TRANSAKSI ===");
        ArrayList<Transaksi> list = transaksiService.getTransaksiHistory(user);

        if (list.isEmpty()) {
            System.out.println("Anda belum memiliki riwayat transaksi.");
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            Transaksi t = list.get(i);
            System.out.println("\n===== TRANSAKSI #" + t.getIdTransaksi() + " =====");
            System.out.println("Mobil              : " + t.getNamaMobil());
            System.out.println("Plat Mobil         : " + t.getPlatMobil());
            System.out.println("Tanggal Pinjam     : " + t.getWaktuPinjam());
            System.out.println("Rencana Kembali    : " + t.getWaktuRencanaPengembalian());

            if (t.getWaktuAktualPengembalian() != null) {
                System.out.println("Tanggal Kembali    : " + t.getWaktuAktualPengembalian());
            }

            System.out.println("Total Hari         : " + t.getTotalHariSewa() + " Hari");
            System.out.println("Biaya Sewa         : Rp" + t.getBiayaSewa());

            if (t.getBiayaKeterlambatan() > 0) {
                System.out.println("Denda              : Rp" + t.getBiayaKeterlambatan());
            }

            System.out.println("Total Bayar        : Rp" + t.getTotal());
            System.out.println("Status             : " + t.getStatus());

            if (t.getCatatan() != null && !t.getCatatan().isEmpty()) {
                System.out.println("Catatan            : " + t.getCatatan());
            }
        }
    }

    private void tampilkanDetail(ArrayList<Transaksi> list) {
        if (list.isEmpty()) {
            System.out.println("Tidak ada transaksi!");
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            Transaksi t = list.get(i);
            System.out.println("\n===== TRANSAKSI " + (i + 1) + " =====");
            System.out.println("Mobil              : " + t.getNamaMobil());
            System.out.println("Plat Mobil         : " + t.getPlatMobil());
            System.out.println("Harga              : Rp" + t.getBiayaSewa());
            System.out.println("Tanggal Pinjam     : " + t.getWaktuPinjam());
            System.out.println("Status             : " + t.getStatus());
        }
    }
}