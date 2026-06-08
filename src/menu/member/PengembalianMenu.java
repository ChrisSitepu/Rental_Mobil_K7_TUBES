package menu.member;

import java.util.ArrayList;
import java.util.Scanner;
import model.User;
import model.Transaksi;
import model.Cabang;
import service.TransaksiService;
import service.CabangService;

public class PengembalianMenu {

    private Scanner sc = new Scanner(System.in);
    private User user;
    private TransaksiService transaksiService = new TransaksiService();
    private CabangService cabangService = new CabangService();

    public PengembalianMenu(User user) {
        this.user = user;
    }

    public void show() {

        System.out.println(
                "\n=== PENGEMBALIAN MOBIL ==="
        );

        // Mengambil transaksi aktif dari database
        ArrayList<Transaksi> transaksiAktif = transaksiService.getTransaksiAktif(user);

        if (transaksiAktif.isEmpty()) {

            System.out.println(
                    "Tidak ada transaksi aktif!"
            );

            return;
        }

        System.out.println(
                "\nTransaksi Berlangsung:"
        );

        for (int i = 0; i < transaksiAktif.size(); i++) {
            Transaksi t = transaksiAktif.get(i);
            System.out.println(
                    (i + 1)
                    + ". "
                    + t.getNamaMobil() + " [" + t.getPlatMobil() + "]"
            );
        }

        System.out.print(
                "\nPilih transaksi: "
        );

        int pilih;
        try {
            pilih = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Input harus berupa angka!");
            return;
        }

        if (pilih < 1 || pilih > transaksiAktif.size()) {

            System.out.println(
                    "Pilihan tidak valid!"
            );

            return;
        }

        Transaksi selectedTransaksi = transaksiAktif.get(pilih - 1);

        // Mengambil daftar cabang dari database
        ArrayList<Cabang> cabangList = cabangService.getAllCabang();

        System.out.println(
                "\nPilih Cabang Pengembalian:"
        );

        for (int i = 0; i < cabangList.size(); i++) {
            System.out.println(
                    (i + 1)
                    + ". "
                    + cabangList.get(i).getNama()
            );
        }

        System.out.print(
                "Pilih cabang: "
        );

        int cabangPilih;
        try {
            cabangPilih = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Input harus berupa angka!");
            return;
        }

        if (cabangPilih < 1 || cabangPilih > cabangList.size()) {

            System.out.println(
                    "Cabang tidak valid!"
            );

            return;
        }

        Cabang selectedCabang = cabangList.get(cabangPilih - 1);

        System.out.print(
                "Tanggal pengembalian (YYYY-MM-DD): "
        );

        String tanggal = sc.nextLine();

        System.out.println(
                "\n=== DETAIL PENGEMBALIAN ==="
        );

        System.out.println(
                "Mobil   : " + selectedTransaksi.getNamaMobil()
        );

        System.out.println(
                "Plat    : " + selectedTransaksi.getPlatMobil()
        );

        System.out.println(
                "Cabang  : " + selectedCabang.getNama()
        );

        System.out.println(
                "Tanggal : " + tanggal
        );

        System.out.println(
                "\nStatus : Menunggu Verifikasi Pegawai"
        );
        
        System.out.println("Silahkan serahkan mobil ke cabang tersebut.");
    }
}