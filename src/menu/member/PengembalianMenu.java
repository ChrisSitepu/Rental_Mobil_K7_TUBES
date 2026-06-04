package menu.member;

import java.util.ArrayList;
import java.util.Scanner;

public class PengembalianMenu {

    private Scanner sc = new Scanner(System.in);

    public void show(){

        System.out.println(
                "\n=== PENGEMBALIAN MOBIL ==="
        );

        // dummy transaksi aktif
        ArrayList<String> transaksiAktif =
                new ArrayList<>();

        transaksiAktif.add("Toyota Avanza");
        transaksiAktif.add("Honda Civic");

        if(transaksiAktif.isEmpty()){

            System.out.println(
                    "Tidak ada transaksi aktif!"
            );

            return;
        }

        System.out.println(
                "\nTransaksi Berlangsung:"
        );

        for(int i = 0;
            i < transaksiAktif.size();
            i++){

            System.out.println(
                    (i + 1)
                    + ". "
                    + transaksiAktif.get(i)
            );
        }

        System.out.print(
                "\nPilih transaksi: "
        );

        int pilih =
                Integer.parseInt(sc.nextLine());

        if(
            pilih < 1
            ||
            pilih > transaksiAktif.size()
        ){

            System.out.println(
                    "Pilihan tidak valid!"
            );

            return;
        }

        String mobil =
                transaksiAktif.get(pilih - 1);

        // dummy cabang
        ArrayList<String> cabangList =
                new ArrayList<>();

        cabangList.add("Bandung");
        cabangList.add("Jakarta");
        cabangList.add("Surabaya");

        System.out.println(
                "\nPilih Cabang Pengembalian:"
        );

        for(int i = 0;
            i < cabangList.size();
            i++){

            System.out.println(
                    (i + 1)
                    + ". "
                    + cabangList.get(i)
            );
        }

        System.out.print(
                "Pilih cabang: "
        );

        int cabangPilih =
                Integer.parseInt(sc.nextLine());

        if(
            cabangPilih < 1
            ||
            cabangPilih > cabangList.size()
        ){

            System.out.println(
                    "Cabang tidak valid!"
            );

            return;
        }

        String cabang =
                cabangList.get(cabangPilih - 1);

        System.out.print(
                "Hari pengembalian: "
        );

        String hari = sc.nextLine();

        System.out.println(
                "\n=== DETAIL PENGEMBALIAN ==="
        );

        System.out.println(
                "Mobil   : " + mobil
        );

        System.out.println(
                "Cabang  : " + cabang
        );

        System.out.println(
                "Hari    : " + hari
        );

        System.out.println(
                "\nStatus : Menunggu Diproses"
        );
    }
}