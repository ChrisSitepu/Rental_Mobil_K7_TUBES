package menu.pegawai;

import java.util.ArrayList;
import java.util.Scanner;
import model.User;
import model.Pembayaran;
import service.TransaksiService;

public class VerifikasiPembayaranMenu {

    private Scanner sc = new Scanner(System.in);
    private User employee;
    private TransaksiService transaksiService = new TransaksiService();

    public VerifikasiPembayaranMenu(User employee) {
        this.employee = employee;
    }

    public void show() {

        System.out.println(
                "\n=== VERIFIKASI PEMBAYARAN ===");

        ArrayList<Pembayaran> pendingList = transaksiService.getPendingPembayaran();

        if (pendingList.isEmpty()) {
            System.out.println("Tidak ada pembayaran yang perlu diverifikasi.");
            return;
        }

        for (int i = 0; i < pendingList.size(); i++) {
            Pembayaran p = pendingList.get(i);
            System.out.println(
                    (i + 1) + ". Transaksi ID: " + p.getIdTransaksi() + 
                    " | Jumlah: Rp" + p.getJumlah() + 
                    " | Metode: " + p.getMetode()
            );
        }

        System.out.print("\nPilih pembayaran (0 untuk kembali): ");
        int pilih;
        try {
            pilih = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Input harus berupa angka!");
            return;
        }

        if (pilih == 0) return;

        if (pilih < 1 || pilih > pendingList.size()) {
            System.out.println("Pilihan tidak valid!");
            return;
        }

        Pembayaran selected = pendingList.get(pilih - 1);

        System.out.print("Approve pembayaran? (y/n): ");
        String confirm = sc.nextLine();

        boolean approved = confirm.equalsIgnoreCase("y");
        
        // Melakukan verifikasi dan mencatat ID Pegawai yang melakukan verifikasi
        if (transaksiService.verifyPembayaran(selected.getIdPembayaran(), employee.getIdPegawai(), approved)) {
            if (approved) {
                System.out.println("Pembayaran berhasil diverifikasi oleh " + employee.getNama() + "!");
            } else {
                System.out.println("Pembayaran ditolak oleh " + employee.getNama() + ".");
            }
        } else {
            System.out.println("Gagal memproses verifikasi pembayaran.");
        }
    }
}