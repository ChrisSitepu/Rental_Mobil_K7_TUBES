package menu.manager;

import java.util.ArrayList;
import java.util.Scanner;

public class KelolaCabangMenu {

    private ArrayList<String> cabangList = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);

    public KelolaCabangMenu() {
        cabangList.add("Bandung");
        cabangList.add("Jakarta");
        cabangList.add("Surabaya");
    }

    public void show() {

        while (true) {

            System.out.println("\n=== KELOLA CABANG ===");

            for (int i = 0; i < cabangList.size(); i++) {
                System.out.println((i + 1) + ". " + cabangList.get(i));
            }

            System.out.println("\nMenu:");
            System.out.println("1. Tambah Cabang");
            System.out.println("2. Edit Cabang");
            System.out.println("3. Kembali");

            System.out.print("Pilih: ");
            int pilih = sc.nextInt();
            sc.nextLine();

            switch (pilih) {

                case 1:
                    tambahCabang();
                    break;

                case 2:
                    editCabang();
                    break;

                case 3:
                    return;

                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }

    private void tambahCabang() {

        System.out.print("Masukkan nama cabang baru: ");
        String nama = sc.nextLine();

        cabangList.add(nama);

        System.out.println("Cabang berhasil ditambahkan!");
    }

    private void editCabang() {

        System.out.println("\n=== EDIT CABANG ===");

        for (int i = 0; i < cabangList.size(); i++) {
            System.out.println((i + 1) + ". " + cabangList.get(i));
        }

        System.out.print("Pilih cabang: ");
        int index = sc.nextInt() - 1;
        sc.nextLine();

        if (index < 0 || index >= cabangList.size()) {
            System.out.println("Cabang tidak ditemukan!");
            return;
        }

        System.out.println("\n1. Ubah Nama");
        System.out.println("2. Hapus Cabang");

        System.out.print("Pilih aksi: ");
        int aksi = sc.nextInt();
        sc.nextLine();

        switch (aksi) {

            case 1:

                System.out.print("Nama baru: ");
                String namaBaru = sc.nextLine();

                cabangList.set(index, namaBaru);

                System.out.println("Cabang berhasil diubah!");
                break;

            case 2:

                cabangList.remove(index);

                System.out.println("Cabang berhasil dihapus!");
                break;

            default:
                System.out.println("Pilihan tidak valid!");
        }
    }
}