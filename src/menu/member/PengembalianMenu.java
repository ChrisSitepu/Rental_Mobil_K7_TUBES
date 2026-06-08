package menu.member;

import java.util.ArrayList;
import java.util.Scanner;

import model.Transaksi;
import model.User;
import service.TransaksiService;

public class PengembalianMenu {

    private Scanner sc = new Scanner(System.in);

    private User user;

    private TransaksiService transaksiService =
            new TransaksiService();

    public PengembalianMenu(User user) {
        this.user = user;
    }

    public void show() {

        System.out.println(
                "\n=== STATUS PENGEMBALIAN ==="
        );

        ArrayList<Transaksi> list =
                transaksiService.getTransaksiHistory(user);

        if (list.isEmpty()) {

            System.out.println(
                    "Belum ada transaksi."
            );

            return;
        }

        for (Transaksi t : list) {

            System.out.println(
                    "\n=============================="
            );

            System.out.println(
                    "ID Transaksi : "
                    + t.getIdTransaksi()
            );

            System.out.println(
                    "Mobil        : "
                    + t.getNamaMobil()
            );

            System.out.println(
                    "Plat         : "
                    + t.getPlatMobil()
            );

            System.out.println(
                    "Status       : "
                    + t.getStatus()
            );

            System.out.println(
                    "Tanggal Pinjam : "
                    + t.getWaktuPinjam()
            );

            System.out.println(
                    "Rencana Kembali : "
                    + t.getWaktuRencanaPengembalian()
            );

            if (t.getWaktuAktualPengembalian() != null) {

                System.out.println(
                        "Aktual Kembali : "
                        + t.getWaktuAktualPengembalian()
                );

                System.out.println(
                        "Denda : Rp"
                        + t.getBiayaKeterlambatan()
                );
            }
        }

        System.out.println(
                "\nTekan Enter untuk kembali..."
        );

        sc.nextLine();
    }
}