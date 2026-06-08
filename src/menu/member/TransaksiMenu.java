package menu.member;

import java.util.ArrayList;
import java.util.Scanner;

import model.Transaksi;
import model.User;
import service.TransaksiService;

public class TransaksiMenu {

        private Scanner sc = new Scanner(System.in);

        public void show() {

                while (true) {

                        System.out.println(
                                        "\n===== TRANSAKSI =====");

                        System.out.println(
                                        "1. Belum Dibayar");

                        System.out.println(
                                        "2. Berlangsung");

                        System.out.println(
                                        "3. Histori");

                        System.out.println(
                                        "4. Kembali");

                        System.out.print("Pilih: ");

                        int pilih = Integer.parseInt(sc.nextLine());

                        switch (pilih) {

                                case 1:
                                        showBelumDibayar();
                                        break;

                                case 2:
                                        showBerlangsung();
                                        break;

                                case 3:
                                        showHistori();
                                        break;

                                case 4:
                                        return;

                                default:
                                        System.out.println(
                                                        "Menu tidak valid!");
                        }
                }
        }

        private void showBelumDibayar() {
                System.out.println("\nFitur belum diimplementasikan.");
        }

        private void showBerlangsung() {

                System.out.println("\n=== BERLANGSUNG ===");

                TransaksiService transaksiService = new TransaksiService();

                ArrayList<Transaksi> list = transaksiService.getTransaksiAktif(user);

                tampilkanDetail(list, "NONE");
        }

        private void showHistori() {
                System.out.println("\n=== HISTORI TRANSAKSI ===");

                TransaksiService transaksiService = new TransaksiService();
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

        private void tampilkanDetail(
                        ArrayList<Transaksi> list,
                        String aksi) {

                if (list.isEmpty()) {

                        System.out.println(
                                        "Tidak ada transaksi!");

                        return;
                }

                for (int i = 0; i < list.size(); i++) {

                        Transaksi t = list.get(i);

                        System.out.println(
                                        "\n===== TRANSAKSI "
                                                        + (i + 1)
                                                        + " =====");

                        System.out.println(
                                        "Mobil              : "
                                                        + t.getNamaMobil());

                        System.out.println(
                                        "Plat Mobil         : "
                                                        + t.getPlatMobil());

                        System.out.println(
                                        "Harga              : Rp"
                                                        + t.getBiayaSewa());

                        System.out.println(
                                        "Tanggal Sewa       : "
                                                        + t.getWaktuPinjam());

                        System.out.println(
                                        "Status             : "
                                                        + t.getStatus());

                        if (!aksi.equals("NONE")) {

                                if (aksi.equals("BATAL")) {

                                        System.out.println(
                                                        "\n1. Batalkan Transaksi");

                                } else if (aksi.equals("PENGEMBALIAN")) {

                                        System.out.println(
                                                        "\n1. Ajukan Pengembalian");
                                }

                                System.out.println(
                                                "2. Lewati");

                                System.out.print("Pilih: ");

                                int pilih = Integer.parseInt(sc.nextLine());

                                switch (pilih) {

                                        case 1:

                                                if (aksi.equals("BATAL")) {

                                                        t.setStatus(
                                                                        "DIBATALKAN");

                                                        System.out.println(
                                                                        "Transaksi berhasil dibatalkan!");

                                                } else if (aksi.equals("PENGEMBALIAN")) {

                                                        t.setStatus(
                                                                        "MENUNGGU PENGEMBALIAN");

                                                        System.out.println(
                                                                        "Pengembalian berhasil diajukan!");
                                                }

                                                break;

                                        case 2:
                                                break;

                                        default:

                                                System.out.println(
                                                                "Pilihan tidak valid!");
                                }
                        }
                }
        }

        private User user;

        public TransaksiMenu(User user) {
                this.user = user;
        }
}