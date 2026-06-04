package menu.member;

import java.util.ArrayList;
import java.util.Scanner;

import model.Transaksi;

public class TransaksiMenu {

    private Scanner sc = new Scanner(System.in);

    public void show(){

        while(true){

            System.out.println(
                    "\n===== TRANSAKSI ====="
            );

            System.out.println(
                    "1. Belum Dibayar"
            );

            System.out.println(
                    "2. Berlangsung"
            );

            System.out.println(
                    "3. Histori"
            );

            System.out.println(
                    "4. Kembali"
            );

            System.out.print("Pilih: ");

            int pilih =
                    Integer.parseInt(sc.nextLine());

            switch(pilih){

                case 1:
                    showBelumDibayar();
                    break;

                case 2:
                    showBerlangsung();
                    break;

                case 3:
                    showHistori();
                    break;

                case 4:
                    return;

                default:
                    System.out.println(
                            "Menu tidak valid!"
                    );
            }
        }
    }

    private void showBelumDibayar(){

        System.out.println(
                "\n=== BELUM DIBAYAR ==="
        );

        ArrayList<Transaksi> list =
                new ArrayList<>();

        list.add(
                new Transaksi(
                        "Toyota Avanza",
                        "SUV",
                        300000,
                        "21 Mei 2026",
                        "Transfer Bank",
                        "BELUM DIBAYAR",
                        "member@gmail.com",
                        "Body depan terdapat goresan kecil.",
                        "https://imgur.com/avanza-condition"
                )
        );

        tampilkanDetail(list, "BATAL");
    }

    private void showBerlangsung(){
        System.out.println(
                "\n=== BERLANGSUNG ==="
        );

        ArrayList<Transaksi> list =
                new ArrayList<>();

        list.add(
                new Transaksi(
                        "Honda Civic",
                        "Sedan",
                        500000,
                        "19 Mei 2026",
                        "QRIS",
                        "DIPINJAM",
                        "member@gmail.com",
                        "Kondisi mobil sangat baik.",
                        "https://imgur.com/civic-condition"
                )
        );

        tampilkanDetail(list, "NONE");
    }

    private void showHistori(){

        System.out.println(
                "\n=== HISTORI ==="
        );

        ArrayList<Transaksi> list =
                new ArrayList<>();

        list.add(
                new Transaksi(
                        "Pajero Sport",
                        "SUV",
                        700000,
                        "10 Mei 2026",
                        "Cash",
                        "SELESAI",
                        "member@gmail.com",
                        "Interior bersih dan mesin normal.",
                        "https://imgur.com/pajero-condition"
                )
        );

        tampilkanDetail(list, "NONE");
    }

    private void tampilkanDetail(
            ArrayList<Transaksi> list,
            String aksi
    ){

        if(list.isEmpty()){

            System.out.println(
                    "Tidak ada transaksi!"
            );

            return;
        }

        for(int i = 0; i < list.size(); i++){

            Transaksi t = list.get(i);

            System.out.println(
                    "\n===== TRANSAKSI "
                    + (i + 1)
                    + " ====="
            );

            System.out.println(
                    "Mobil              : "
                    + t.getNamaMobil()
            );

            System.out.println(
                    "Tipe Mobil         : "
                    + t.getTipeMobil()
            );

            System.out.println(
                    "Harga              : Rp"
                    + t.getHarga()
            );

            System.out.println(
                    "Tanggal Sewa       : "
                    + t.getTanggalSewa()
            );

            System.out.println(
                    "Metode Pembayaran  : "
                    + t.getMetodePembayaran()
            );

            System.out.println(
                    "Status             : "
                    + t.getStatus()
            );

            System.out.println(
                    "Catatan Kondisi   : "
                    + t.getCatatanKondisi()
            );

            System.out.println(
                    "Foto Kondisi URL  : "
                    + t.getFotoKondisiUrl()
            );

            if(!aksi.equals("NONE")){

                if(aksi.equals("BATAL")){

                    System.out.println(
                            "\n1. Batalkan Transaksi"
                    );

                } else if(aksi.equals("PENGEMBALIAN")){

                    System.out.println(
                            "\n1. Ajukan Pengembalian"
                    );
                }

                System.out.println(
                        "2. Lewati"
                );

                System.out.print("Pilih: ");

                int pilih =
                        Integer.parseInt(sc.nextLine());

                switch (pilih){

                    case 1:

                        if(aksi.equals("BATAL")){

                            t.setStatus(
                                    "DIBATALKAN"
                            );

                            System.out.println(
                                    "Transaksi berhasil dibatalkan!"
                            );

                        } else if(aksi.equals("PENGEMBALIAN")){

                            t.setStatus(
                                    "MENUNGGU PENGEMBALIAN"
                            );

                            System.out.println(
                                    "Pengembalian berhasil diajukan!"
                            );
                        }

                        break;

                    case 2:
                        break;

                    default:

                        System.out.println(
                                "Pilihan tidak valid!"
                        );
                }
            }
        }
    }
}