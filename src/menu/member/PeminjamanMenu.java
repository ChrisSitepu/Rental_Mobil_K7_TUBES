package menu.member;

import java.util.ArrayList;
import java.util.Scanner;

import model.Mobil;
import model.User;
import service.MobilService;
import service.TransaksiService;

public class PeminjamanMenu {

    private Scanner sc = new Scanner(System.in);
    private User user;
    private MobilService mobilService = new MobilService();
    private TransaksiService transaksiService = new TransaksiService();

    public PeminjamanMenu(User user) {
        this.user = user;
    }

    public void show() {
        System.out.println("\n=== KATALOG MOBIL ===");
        ArrayList<Mobil> mobilList = mobilService.getAvailableMobil();

        if (mobilList.isEmpty()) {
            System.out.println("Tidak ada mobil tersedia!");
            return;
        }

        for (int i = 0; i < mobilList.size(); i++) {
            Mobil m = mobilList.get(i);
            System.out.println((i + 1) + ". " + m.getNama() + " | " + m.getBrand() + " | Rp" + m.getTarifSewa());
        }

        System.out.print("\nPilih nomor mobil: ");
        try {
            int pilih = Integer.parseInt(sc.nextLine());
            if (pilih < 1 || pilih > mobilList.size()) {
                System.out.println("Pilihan tidak valid!");
                return;
            }
            showDetailMobil(mobilList.get(pilih - 1));
        } catch (Exception e) {
            System.out.println("Input harus berupa angka!");
        }
    }

    private void showDetailMobil(Mobil mobil) {
        System.out.println("\n=== DETAIL MOBIL ===");
        System.out.println("Nama Mobil      : " + mobil.getNama());
        System.out.println("Brand           : " + mobil.getBrand());
        System.out.println("Tipe Mobil      : " + mobil.getTipe());
        System.out.println("Warna           : " + mobil.getWarna());
        System.out.println("Plat Nomor      : " + mobil.getPlat());
        System.out.println("Kapasitas       : " + mobil.getKapasitas() + " orang");
        System.out.println("Tahun           : " + mobil.getTahunPembuatan());
        System.out.println("Status          : " + (mobil.isAvailable() ? "Tersedia" : "Tidak Tersedia"));
        System.out.println("Tarif Sewa      : Rp" + mobil.getTarifSewa());
        // System.out.println("Tarif Denda : Rp" + mobil.getTarifDenda()); -> harus
        // diperbaiki

        System.out.println("\n1. Lanjut Pembayaran");
        System.out.println("2. Kembali");
        System.out.print("Pilih: ");

        try {
            int pilih = Integer.parseInt(sc.nextLine());
            switch (pilih) {
                case 1:
                    prosesPembayaran(mobil);
                    break;
                case 2:
                    return;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } catch (Exception e) {
            System.out.println("Input harus berupa angka!");
        }
    }

    private void prosesPembayaran(Mobil mobil) {
        System.out.println("\n=== PEMBAYARAN ===");
        System.out.print("Lama sewa (hari): ");
        try {
            int lamaSewa = Integer.parseInt(sc.nextLine());
            double totalBayar = mobil.getTarifSewa() * lamaSewa;

            System.out.println("Mobil       : " + mobil.getNama());
            System.out.println("Total Bayar : Rp" + totalBayar);

            System.out.print("Konfirmasi pembayaran? (y/n): ");
            String confirm = sc.nextLine();

            if (confirm.equalsIgnoreCase("y")) {
                if (transaksiService.createPeminjaman(user, mobil, lamaSewa)) {
                    System.out.println("\nPembayaran berhasil!");
                    System.out.println("Peminjaman berhasil diajukan!");
                } else {
                    System.out.println("\nTerjadi kesalahan saat memproses peminjaman.");
                }
            } else {
                System.out.println("\nPembayaran dibatalkan!");
            }
        } catch (Exception e) {
            System.out.println("Lama sewa harus berupa angka!");
        }
    }
}