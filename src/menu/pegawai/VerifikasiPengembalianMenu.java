package menu.pegawai;

import java.util.ArrayList;
import java.util.Scanner;
import model.Transaksi;
import model.User;
import service.TransaksiService;

public class VerifikasiPengembalianMenu {

    private Scanner sc = new Scanner(System.in);
    private User user;
    private TransaksiService transaksiService = new TransaksiService();

    public VerifikasiPengembalianMenu(User user) {
        this.user = user;
    }

    public void show() {
        System.out.println("\n=== VERIFIKASI PENGEMBALIAN ===");
        ArrayList<Transaksi> activeTrans = transaksiService.getAllActivePeminjaman();

        if (activeTrans.isEmpty()) {
            System.out.println("Tidak ada peminjaman aktif.");
            return;
        }

        for (int i = 0; i < activeTrans.size(); i++) {
            Transaksi t = activeTrans.get(i);
            System.out.println((i + 1) + ". " + t.getNamaMobil() + " (" + t.getPlatMobil() + ") - Member: " + t.getNamaMember());
        }

        System.out.println("0. Kembali");
        System.out.print("Pilih transaksi: ");
        
        try {
            int pilih = Integer.parseInt(sc.nextLine());
            if (pilih == 0) return;
            if (pilih < 1 || pilih > activeTrans.size()) {
                System.out.println("Pilihan tidak valid!");
                return;
            }

            prosesReturn(activeTrans.get(pilih - 1));
        } catch (Exception e) {
            System.out.println("Input harus berupa angka!");
        }
    }

    private void prosesReturn(Transaksi t) {
        System.out.println("\n=== DETAIL PENGEMBALIAN ===");
        System.out.println("Mobil         : " + t.getNamaMobil());
        System.out.println("Plat          : " + t.getPlatMobil());
        System.out.println("Member        : " + t.getNamaMember());
        System.out.println("Rencana Kembali: " + t.getWaktuRencanaPengembalian());

        System.out.print("\nCatatan kondisi mobil: ");
        String deskripsi = sc.nextLine();

        System.out.print("Tingkat kondisi (1-10, 10 paling baik): ");
        int tingkat = Integer.parseInt(sc.nextLine());

        System.out.print("URL Foto Kondisi: ");
        String fotoUrl = sc.nextLine();

        System.out.print("\nKonfirmasi pengembalian? (y/n): ");
        String confirm = sc.nextLine();

        if (confirm.equalsIgnoreCase("y")) {
            if (transaksiService.processReturn(t, user.getIdPegawai(), deskripsi, tingkat, fotoUrl)) {
                System.out.println("\nPengembalian berhasil diproses!");
                System.out.println("Data kondisi dan denda (jika ada) telah tercatat.");
            } else {
                System.out.println("\nGagal memproses pengembalian.");
            }
        } else {
            System.out.println("\nProses dibatalkan.");
        }
    }
}
