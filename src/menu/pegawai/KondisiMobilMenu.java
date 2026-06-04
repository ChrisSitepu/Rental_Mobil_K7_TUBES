package menu.pegawai;

import java.util.Scanner;

public class KondisiMobilMenu {

    private Scanner sc = new Scanner(System.in);

    public void show(){

        System.out.println(
                "\n=== KONDISI MOBIL ==="
        );

        System.out.print(
                "Nama Mobil: "
        );

        String mobil = sc.nextLine();

        System.out.println(
                "\nTipe Pencatatan:"
        );

        System.out.println(
                "1. Sebelum Peminjaman"
        );

        System.out.println(
                "2. Sesudah Pengembalian"
        );

        System.out.print(
                "Pilih: "
        );

        int pilih =
                Integer.parseInt(sc.nextLine());

        String tipePencatatan;

        switch (pilih){

            case 1:
                tipePencatatan =
                        "SEBELUM PEMINJAMAN";
                break;

            case 2:
                tipePencatatan =
                        "SESUDAH PENGEMBALIAN";
                break;

            default:

                System.out.println(
                        "Pilihan tidak valid!"
                );

                return;
        }

        System.out.print(
                "Catatan kondisi: "
        );

        String kondisi = sc.nextLine();

        System.out.print(
                "URL Foto Kondisi: "
        );

        String fotoUrl = sc.nextLine();

        System.out.println(
                "\nKondisi berhasil dicatat!"
        );

        System.out.println(
                "Mobil            : " + mobil
        );

        System.out.println(
                "Tipe Pencatatan  : " + tipePencatatan
        );

        System.out.println(
                "Catatan          : " + kondisi
        );

        System.out.println(
                "Foto URL         : " + fotoUrl
        );
    }
}