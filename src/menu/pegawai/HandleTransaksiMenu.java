// package menu.pegawai;

// import java.util.ArrayList;
// import java.util.Scanner;

// import model.Transaksi;

// public class HandleTransaksiMenu {

//     private Scanner sc = new Scanner(System.in);

// //     public void show(){

// //         ArrayList<Transaksi> list =
// //                 new ArrayList<>();

// //         list.add(
// //         new Transaksi(
// //                 "Toyota Avanza",
// //                 "SUV",
// //                 300000,
// //                 "21 Mei 2026",
// //                 "Transfer Bank",
// //                 "DIPROSES",
// //                 "kei@gmail.com",
// //                 "Body depan terdapat goresan kecil di bumper kiri.",
// //                 "https://imgur.com/avanza-condition"
// //         )
// //     );

// //     list.add(
// //             new Transaksi(
// //                     "Honda Civic",
// //                     "Sedan",
// //                     500000,
// //                     "20 Mei 2026",
// //                     "QRIS",
// //                     "MENUNGGU VERIFIKASI",
// //                     "sinta@gmail.com",
// //                     "Kondisi bagus, interior bersih.",
// //                     "https://imgur.com/civic-condition"
// //             )
// //     );

// //         while(true){

// //             System.out.println(
// //                     "\n=== TRANSAKSI YANG DIHANDLE ==="
// //             );

// //             for(int i = 0; i < list.size(); i++){

// //                 Transaksi t = list.get(i);

// //                 System.out.println(
// //                         (i + 1)
// //                         + ". "
// //                         + t.getNamaMobil()
// //                         + " - "
// //                         + t.getStatus()
// //                 );
// //             }

// //             System.out.println(
// //                     "0. Kembali"
// //             );

// //             System.out.print(
// //                     "Pilih transaksi: "
// //             );

// //             int pilih =
// //                     Integer.parseInt(sc.nextLine());

// //             if(pilih == 0){
// //                 return;
// //             }

// //             if(pilih < 1 || pilih > list.size()){

// //                 System.out.println(
// //                         "Pilihan tidak valid!"
// //                 );

// //                 continue;
// //             }

// //             Transaksi t =
// //                     list.get(pilih - 1);

// //             showDetail(t);
// //         }
// //     }

// //     private void showDetail(Transaksi t){

// //         System.out.println(
// //                 "\n===== DETAIL TRANSAKSI ====="
// //         );

// //         System.out.println(
// //                 "Email Member      : "
// //                 + t.getEmailMember()
// //         );

// //         System.out.println(
// //                 "Mobil             : "
// //                 + t.getNamaMobil()
// //         );

// //         System.out.println(
// //                 "Tipe Mobil        : "
// //                 + t.getTipeMobil()
// //         );

// //         System.out.println(
// //                 "Harga             : Rp"
// //                 + t.getHarga()
// //         );

// //         System.out.println(
// //                 "Tanggal Sewa      : "
// //                 + t.getTanggalSewa()
// //         );

// //         System.out.println(
// //                 "Metode Pembayaran : "
// //                 + t.getMetodePembayaran()
// //         );

// //         System.out.println(
// //                 "Status            : "
// //                 + t.getStatus()
// //         );

// //         System.out.println(
// //         "\n=== KONDISI MOBIL SEBELUM DIPINJAM ==="
// //     );

// //         System.out.println(
// //                 "Deskripsi : "
// //                 + t.getCatatanKondisi()
// //         );

// //         System.out.println(
// //                 "Foto URL  : "
// //                 + t.getFotoKondisiUrl()
// //         );

// //         System.out.println(
// //                 "\nTekan enter untuk kembali..."
// //         );

// //         sc.nextLine();

        
// //     }
// }
package menu.pegawai;

import java.util.ArrayList;
import java.util.Scanner;

import model.Transaksi;
import service.TransaksiService;

public class HandleTransaksiMenu {

    private Scanner sc = new Scanner(System.in);
    private TransaksiService transaksiService = new TransaksiService();

    public void show() {
        while (true) {

        ArrayList<Transaksi> list =
                transaksiService.getPendingPeminjaman();

        if (list.isEmpty()) {
                System.out.println(
                        "\nTidak ada transaksi yang menunggu verifikasi."
                );
                return;
        }

        System.out.println(
                "\n=== TRANSAKSI YANG DIHANDLE ==="
        );

        for (int i = 0; i < list.size(); i++) {

            for (int i = 0; i < list.size(); i++) {
                Transaksi t = list.get(i);
                System.out.println(
                        (i + 1)
                        + ". "
                        + t.getNamaMobil()
                        + " - "
                        + t.getNamaMember()
                        + " - "
                        + t.getStatus()
                );
        }

        System.out.println("0. Kembali");

        System.out.print("Pilih transaksi: ");

        int pilih = Integer.parseInt(sc.nextLine());

        if (pilih == 0) {
                return;
        }

        if (pilih < 1 || pilih > list.size()) {
                System.out.println("Pilihan tidak valid!");
                continue;
        }

        showDetail(list.get(pilih - 1));
        }

        }


    private void showDetail(Transaksi t) {

        System.out.println(
                "\n===== DETAIL TRANSAKSI ====="
        );

        System.out.println(
                "Nama Member      : "
                + t.getNamaMember()
        );

        System.out.println(
                "Mobil            : "
                + t.getNamaMobil()
        );

        System.out.println(
                "Plat Mobil       : "
                + t.getPlatMobil()
        );

        System.out.println(
                "Biaya Sewa       : Rp"
                + t.getBiayaSewa()
        );

        System.out.println(
                "Total Hari Sewa  : "
                + t.getTotalHariSewa()
        );

        System.out.println(
                "Tanggal Pinjam   : "
                + t.getWaktuPinjam()
        );

        System.out.println(
                "Rencana Kembali  : "
                + t.getWaktuRencanaPengembalian()
        );

        System.out.println(
                "Status           : "
                + t.getStatus()
        );

        System.out.println("\n1. Approve");
        
        System.out.println("2. Kembali");

        System.out.print("Pilih: ");

        int pilih =
                Integer.parseInt(sc.nextLine());

        switch (pilih) {

        case 1:

                System.out.println("\n=== KONDISI MOBIL SEBELUM SEWA ===");

                System.out.print("Deskripsi kondisi mobil : ");
                String deskripsi = sc.nextLine();

                System.out.print("Tingkat kondisi (1-100) : ");
                int tingkatKondisi =
                        Integer.parseInt(sc.nextLine());

                System.out.print("URL Foto kondisi : ");
                String fotoUrl = sc.nextLine();

                if (
                        transaksiService
                        .approvePeminjaman(
                                t,
                                2,
                                deskripsi,
                                tingkatKondisi,
                                fotoUrl
                        )
                ) {

                System.out.println(
                        "\nPeminjaman berhasil disetujui!"
                );

                } else {

                System.out.println(
                        "\nGagal approve peminjaman!"
                );
                }

                break;

        case 2:
                return;

        default:
                System.out.println(
                "Pilihan tidak valid!"
                );
        }

        System.out.println(
                "\nTekan enter untuk kembali..."
        );

        System.out.println("\nTekan enter untuk kembali...");
        sc.nextLine();
    }
}