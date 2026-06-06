package menu.manager;

import java.util.ArrayList;
import java.util.Scanner;
import model.Mobil;
import service.MobilService;

public class KelolaMobilMenu {

    private MobilService mobilService = new MobilService();
    private Scanner sc = new Scanner(System.in);

    public void show() {

        while (true) {

            System.out.println("\n=== KELOLA DATA MOBIL ===");

            ArrayList<Mobil> mobilList = mobilService.getAllMobil();

            if (mobilList.isEmpty()) {
                System.out.println("(Tidak ada data mobil)");
            } else {
                for (int i = 0; i < mobilList.size(); i++) {
                    System.out.println((i + 1) + ". " + mobilList.get(i).getNama() + " [" + mobilList.get(i).getPlat() + "]");
                }
            }

            System.out.println("\nMenu:");
            System.out.println("T. Tambah Mobil Baru");
            System.out.println("K. Kembali");
            System.out.print("Pilih (Nomor/T/K): ");
            
            String input = sc.nextLine().toUpperCase();

            if (input.equals("K")) {
                return;
            }

            if (input.equals("T")) {
                tambahMobil();
                continue;
            }

            try {
                int index = Integer.parseInt(input) - 1;
                if (index >= 0 && index < mobilList.size()) {
                    editMobil(mobilList.get(index));
                } else {
                    System.out.println("Nomor mobil tidak valid!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Pilihan tidak valid!");
            }
        }
    }

    private void tambahMobil() {
        System.out.println("\n=== TAMBAH MOBIL BARU ===");
        try {
            System.out.print("Brand: "); String brand = sc.nextLine();
            System.out.print("Tipe: "); String tipe = sc.nextLine();
            System.out.print("Warna: "); String warna = sc.nextLine();
            System.out.print("Plat: "); String plat = sc.nextLine();
            System.out.print("Kapasitas: "); int kap = Integer.parseInt(sc.nextLine());
            System.out.print("Tahun: "); int thn = Integer.parseInt(sc.nextLine());
            System.out.print("Harga Sewa: "); int harga = Integer.parseInt(sc.nextLine());

            // Default id_cabang is 1 for now, or you could prompt for it
            Mobil newMobil = new Mobil(0, 1, brand, tipe, warna, plat, kap, thn, true, harga);
            
            if (mobilService.addMobil(newMobil, 1)) {
                System.out.println("Mobil berhasil ditambahkan ke database!");
            } else {
                System.out.println("Gagal menambahkan mobil.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Input angka tidak valid!");
        }
    }

    private void editMobil(Mobil m) {

        while (true) {

            System.out.println("\n=== DETAIL MOBIL ===");
            System.out.println("Brand           : " + m.getBrand());
            System.out.println("Tipe            : " + m.getTipe());
            System.out.println("Warna           : " + m.getWarna());
            System.out.println("Plat            : " + m.getPlat());
            System.out.println("Status          : " + (m.isAvailable() ? "Tersedia" : "Dipinjam/Rusak"));
            System.out.println("Kapasitas       : " + m.getKapasitas());
            System.out.println("Tahun           : " + m.getTahunPembuatan());
            System.out.println("Harga Sewa      : Rp" + m.getTarifSewa());

            System.out.println("\nMenu Edit:");
            System.out.println("1. Ubah Status");
            System.out.println("2. Ubah Harga Sewa");
            System.out.println("3. Hapus Mobil");
            System.out.println("0. Kembali");

            System.out.print("Pilih aksi: ");
            String input = sc.nextLine();
            int pilih;
            try {
                pilih = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Pilihan tidak valid!");
                continue;
            }

            switch (pilih) {
                case 1:
                    System.out.print("Status Baru (Tersedia/Dipinjam/Rusak): ");
                    String status = sc.nextLine();
                    if (mobilService.updateMobilStatus(m.getPlat(), status)) {
                        System.out.println("Status berhasil diperbarui!");
                        // We return to show() to refresh the list
                        return; 
                    }
                    break;
                case 2:
                    try {
                        System.out.print("Masukkan Harga Sewa Baru: ");
                        int newRate = Integer.parseInt(sc.nextLine());
                        if (mobilService.updateMobilRate(m.getPlat(), newRate)) {
                            System.out.println("Harga sewa berhasil diperbarui!");
                            m.setHargaSewa(newRate);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Input harga tidak valid!");
                    }
                    break;
                case 3:
                    System.out.print("Yakin ingin menghapus mobil ini? (y/n): ");
                    if (sc.nextLine().equalsIgnoreCase("y")) {
                        if (mobilService.deleteMobil(m.getPlat())) {
                            System.out.println("Mobil berhasil dihapus!");
                            return;
                        }
                    }
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }
}