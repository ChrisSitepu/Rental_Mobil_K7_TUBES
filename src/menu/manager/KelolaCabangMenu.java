package menu.manager;

import model.Cabang;
import service.CabangService;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class KelolaCabangMenu {

    private CabangService cabangService = new CabangService();
    private Scanner sc = new Scanner(System.in);

    public KelolaCabangMenu() {
    }

    public void show() {

        while (true) {

            System.out.println("\n=== KELOLA CABANG ===");

            ArrayList<Cabang> cabangList = cabangService.getAllCabang();
            for (int i = 0; i < cabangList.size(); i++) {
                System.out.println((i + 1) + ". " + cabangList.get(i).getNama() + " (" + cabangList.get(i).getAlamat() + ")");
            }

            System.out.println("\nMenu:");
            System.out.println("1. Tambah Cabang");
            System.out.println("2. Edit Cabang");
            System.out.println("3. Kembali");

            System.out.print("Pilih: ");
            int pilih = 0;
            try {
                pilih = sc.nextInt();
            } catch (Exception e) {
                sc.nextLine();
                System.out.println("Input harus angka!");
                continue;
            }
            sc.nextLine();

            switch (pilih) {

                case 1:
                    tambahCabang();
                    break;

                case 2:
                    editCabang(cabangList);
                    break;

                case 3:
                    return;

                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }

    private void tambahCabang() {

        System.out.println("\n=== TAMBAH CABANG ===");
        System.out.print("Masukkan nama cabang: ");
        String nama = sc.nextLine();
        
        LocalTime jamOperasional = null;
        while (jamOperasional == null) {
            System.out.print("Masukkan jam operasional (HH:mm): ");
            String jamStr = sc.nextLine();
            try {
                jamOperasional = LocalTime.parse(jamStr);
            } catch (DateTimeParseException e) {
                System.out.println("Format jam tidak valid! Gunakan HH:mm (contoh: 08:00)");
            }
        }

        System.out.print("Masukkan email: ");
        String email = sc.nextLine();

        System.out.print("Masukkan no telepon: ");
        String noTelp = sc.nextLine();

        System.out.print("Masukkan alamat: ");
        String alamat = sc.nextLine();

        int id = cabangService.getNextId();
        Cabang newCabang = new Cabang(id, nama, jamOperasional, email, noTelp, alamat);

        if (cabangService.addCabang(newCabang)) {
            System.out.println("Cabang berhasil ditambahkan!");
        } else {
            System.out.println("Gagal menambahkan cabang!");
        }
    }

    private void editCabang(ArrayList<Cabang> cabangList) {

        if (cabangList.isEmpty()) {
            System.out.println("Belum ada cabang.");
            return;
        }

        System.out.println("\n=== EDIT CABANG ===");

        for (int i = 0; i < cabangList.size(); i++) {
            System.out.println((i + 1) + ". " + cabangList.get(i).getNama());
        }

        System.out.print("Pilih cabang: ");
        int index = 0;
        try {
            index = sc.nextInt() - 1;
        } catch (Exception e) {
            sc.nextLine();
            System.out.println("Input harus angka!");
            return;
        }
        sc.nextLine();

        if (index < 0 || index >= cabangList.size()) {
            System.out.println("Cabang tidak ditemukan!");
            return;
        }

        Cabang selectedCabang = cabangList.get(index);

        System.out.println("\nDetail Cabang: " + selectedCabang.getNama());
        System.out.println("1. Ubah Data");
        System.out.println("2. Hapus Cabang");
        System.out.println("3. Kembali");

        System.out.print("Pilih aksi: ");
        int aksi = 0;
        try {
            aksi = sc.nextInt();
        } catch (Exception e) {
            sc.nextLine();
            System.out.println("Input harus angka!");
            return;
        }
        sc.nextLine();

        switch (aksi) {

            case 1:
                System.out.print("Nama baru [" + selectedCabang.getNama() + "]: ");
                String nama = sc.nextLine();
                if (nama.isEmpty()) nama = selectedCabang.getNama();

                LocalTime jamOperasional = selectedCabang.getJamOperasional();
                System.out.print("Jam operasional baru [" + jamOperasional + "] (HH:mm): ");
                String jamStr = sc.nextLine();
                if (!jamStr.isEmpty()) {
                    try {
                        jamOperasional = LocalTime.parse(jamStr);
                    } catch (DateTimeParseException e) {
                        System.out.println("Format jam tidak valid! Menggunakan nilai lama.");
                    }
                }

                System.out.print("Email baru [" + selectedCabang.getEmail() + "]: ");
                String email = sc.nextLine();
                if (email.isEmpty()) email = selectedCabang.getEmail();

                System.out.print("No telepon baru [" + selectedCabang.getNoTelepon() + "]: ");
                String noTelp = sc.nextLine();
                if (noTelp.isEmpty()) noTelp = selectedCabang.getNoTelepon();

                System.out.print("Alamat baru [" + selectedCabang.getAlamat() + "]: ");
                String alamat = sc.nextLine();
                if (alamat.isEmpty()) alamat = selectedCabang.getAlamat();

                Cabang updatedCabang = new Cabang(selectedCabang.getIdCabang(), nama, jamOperasional, email, noTelp, alamat);

                if (cabangService.updateCabang(updatedCabang)) {
                    System.out.println("Cabang berhasil diubah!");
                } else {
                    System.out.println("Gagal mengubah cabang!");
                }
                break;

            case 2:
                System.out.print("Apakah Anda yakin ingin menghapus cabang ini? (y/n): ");
                String confirm = sc.nextLine();
                if (confirm.equalsIgnoreCase("y")) {
                    if (cabangService.deleteCabang(selectedCabang.getIdCabang())) {
                        System.out.println("Cabang berhasil dihapus!");
                    } else {
                        System.out.println("Gagal menghapus cabang!");
                    }
                }
                break;

            case 3:
                break;

            default:
                System.out.println("Pilihan tidak valid!");
        }
    }
}