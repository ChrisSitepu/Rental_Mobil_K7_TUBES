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

            for (int i = 0; i < mobilList.size(); i++) {
                System.out.println((i + 1) + ". " + mobilList.get(i).getNama() + " (" + mobilList.get(i).getPlat() + ")");
            }

            System.out.println("\n11. Tambah Mobil Baru");
            System.out.println("0. Kembali");

            System.out.print("Pilih (angka): ");
            int pilih = sc.nextInt();
            sc.nextLine();

            if (pilih == 0) {
                return;
            }

            if (pilih == 11) {
                tambahMobil();
                continue;
            }

            int index = pilih - 1;

            if (index < 0 || index >= mobilList.size()) {
                System.out.println("Mobil tidak ditemukan!");
                continue;
            }

            editMobil(mobilList.get(index));
        }
    }

    private void tambahMobil() {
        System.out.println("\n=== TAMBAH MOBIL BARU ===");
        System.out.print("Brand: "); String brand = sc.nextLine();
        System.out.print("Tipe: "); String tipe = sc.nextLine();
        System.out.print("Warna: "); String warna = sc.nextLine();
        System.out.print("Plat: "); String plat = sc.nextLine();
        System.out.print("Kapasitas: "); int kap = sc.nextInt();
        System.out.print("Tahun: "); int thn = sc.nextInt(); sc.nextLine();

        Mobil newMobil = new Mobil(brand + " " + tipe, brand, tipe, warna, plat, kap, thn, 300000, 50000, true);
        
        if (mobilService.addMobil(newMobil, 1)) {
            System.out.println("Mobil berhasil ditambahkan ke database!");
        } else {
            System.out.println("Gagal menambahkan mobil.");
        }
    }

    private void editMobil(Mobil m) {

        while (true) {

            System.out.println("\n=== DETAIL MOBIL ===");

            System.out.println("1. Nama            : " + m.getNama());
            System.out.println("2. Brand           : " + m.getBrand());
            System.out.println("3. Tipe            : " + m.getTipe());
            System.out.println("4. Warna           : " + m.getWarna());
            System.out.println("5. Plat            : " + m.getPlat());
            System.out.println("6. Status          : " + (m.isAvailable() ? "Tersedia" : "Dipinjam/Rusak"));
            System.out.println("7. Kapasitas       : " + m.getKapasitas());
            System.out.println("8. Tahun           : " + m.getTahunPembuatan());

            System.out.println("\n9. Hapus Mobil");
            System.out.println("0. Kembali");

            System.out.print("Pilih aksi: ");
            int pilih = sc.nextInt();
            sc.nextLine();

            switch (pilih) {
                case 6:
                    System.out.print("Ubah status (Tersedia/Dipinjam/Rusak): ");
                    String status = sc.nextLine();
                    if (mobilService.updateMobilStatus(m.getPlat(), status)) {
                        System.out.println("Status berhasil diperbarui di database!");
                    }
                    return;
                case 9:
                    if (mobilService.deleteMobil(m.getPlat())) {
                        System.out.println("Mobil berhasil dihapus dari database!");
                        return;
                    }
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Aksi tidak didukung dalam mode ini.");
            }
        }
    }
}