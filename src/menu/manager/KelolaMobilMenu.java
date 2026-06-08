package menu.manager;

import java.util.ArrayList;
import java.util.Scanner;
import model.Mobil;
import model.Cabang;
import service.CabangService;
import service.MobilService;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class KelolaMobilMenu {

    

    private MobilService mobilService = new MobilService();
    private Scanner sc = new Scanner(System.in);

    public void show() {

        while (true) {

            System.out.println("\n=== KELOLA DATA MOBIL ===");

            ArrayList<Mobil> mobilList = mobilService.getAllMobil();

            ArrayList<Mobil> displayList = new ArrayList<>();

            if (mobilList.isEmpty()) {

                System.out.println("(Tidak ada data mobil)");

            } else {

                Map<String, List<Mobil>> grouped =
                        new LinkedHashMap<>();

                for (Mobil m : mobilList) {

                    grouped.computeIfAbsent(
                            m.getBrand(),
                            k -> new ArrayList<>())
                            .add(m);
                }

                int nomor = 1;

                for (String brand : grouped.keySet()) {

                    System.out.println(
                        "\n=== " +
                        brand.toUpperCase() +
                        " ==="
                    );

                    for (Mobil m : grouped.get(brand)) {

                        displayList.add(m);

                        System.out.println(
                            nomor +
                            ". " +
                            m.getNama() +
                            " [" +
                            m.getPlat() +
                            "]"
                        );

                        nomor++;
                    }
                }
            }

            System.out.println("\nMenu:");
            System.out.println("T. Tambah Mobil Baru");
            System.out.println("K. Kembali");
            System.out.print("Pilih (Nomor/T/K): ");
            
            String input = sc.nextLine().toUpperCase();

            if (input.equals("K")) {
                return;
            }

            if (input.equals("T")) {
                tambahMobil();
                continue;
            }

            try {
                int index = Integer.parseInt(input) - 1;

                if (index >= 0 && index < displayList.size()) {
                    editMobil(displayList.get(index));
                } else {
                    System.out.println("Nomor mobil tidak valid!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Pilihan tidak valid!");
            }
        }
    }

    private void tambahMobil() {

    System.out.println("\n=== TAMBAH MOBIL BARU ===");

    try {

        System.out.print("Nama Mobil : ");
        String nama = sc.nextLine();

        System.out.print("Brand : ");
        String brand = sc.nextLine();

        System.out.print("Tipe (SUV/MPV/Sedan/Hatchback/Minivan/Coupe) : ");
        String tipe = sc.nextLine();

        System.out.print("Warna : ");
        String warna = sc.nextLine();

        System.out.print("Plat : ");
        String plat = sc.nextLine();

        CabangService cabangService = new CabangService();

        ArrayList<Cabang> cabangList =
                cabangService.getAllCabang();

        System.out.println("\n=== DAFTAR CABANG ===");

        for (int i = 0; i < cabangList.size(); i++) {

            System.out.println(
                (i + 1) + ". " +
                cabangList.get(i).getNama()
            );
        }

        System.out.print("Pilih Cabang : ");

        int pilihanCabang =
                Integer.parseInt(sc.nextLine());

        if (pilihanCabang < 1 ||
            pilihanCabang > cabangList.size()) {

            System.out.println("Cabang tidak valid!");
            return;
        }

    int idCabang =
            cabangList
                .get(pilihanCabang - 1)
                .getIdCabang();

        System.out.print("Kapasitas : ");
        int kapasitas = Integer.parseInt(sc.nextLine());

        System.out.print("Tahun : ");
        int tahun = Integer.parseInt(sc.nextLine());

        System.out.print("Tarif Sewa : ");
        int tarifSewa = Integer.parseInt(sc.nextLine());

        System.out.print("Tarif Denda : ");
        int tarifDenda = Integer.parseInt(sc.nextLine());

        System.out.print("Status (Tersedia/Tidak Tersedia): ");
        String status = sc.nextLine();

        Mobil mobil = new Mobil(
            0,
            idCabang,
            nama,
            brand,
            tipe,
            warna,
            plat,
            status,
            kapasitas,
            tahun,
            tarifSewa,
            tarifDenda
        );

        mobilService.addMobil(mobil);

    } catch (Exception e) {
        System.out.println("Input tidak valid!");
    }
}

    private void editMobil(Mobil m) {

        while (true) {

            System.out.println("\n=== DETAIL MOBIL ===");
            System.out.println("ID Mobil        : " + m.getIdMobil());
            System.out.println("Nama            : " + m.getNama());
            System.out.println("Brand           : " + m.getBrand());
            System.out.println("Tipe            : " + m.getTipe());
            System.out.println("Warna           : " + m.getWarna());
            System.out.println("Plat            : " + m.getPlat());
            System.out.println("Status          : " + m.getStatus());
            System.out.println("Kapasitas       : " + m.getKapasitas());
            System.out.println("Tahun           : " + m.getTahunPembuatan());
            System.out.println("Tarif Sewa      : Rp" + m.getTarifSewa());
            System.out.println("Tarif Denda     : Rp" + m.getTarifDenda());
            System.out.println("ID Cabang       : " + m.getIdCabang());

            System.out.println("\nMenu Edit:");
            System.out.println("1. Ubah Status (Tersedia/Tidak Tersedia)");
            System.out.println("2. Ubah Harga Sewa");
            System.out.println("0. Kembali");

            System.out.print("Pilih aksi: ");
            String input = sc.nextLine();
            int pilih;
            try {
                pilih = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Pilihan tidak valid!");
                continue;
            }

            switch (pilih) {
                case 1:
                    try {
                        String status = input(
                            "Status Baru: "
                        );
                        if (status == null) {
                            System.out.println(
                                "Status tidak diubah."
                            );
                            break;
                        }
                        if (mobilService.updateMobilStatus(
                                m.getIdMobil(),
                                status)) {

                            m.setStatus(status);

                            System.out.println(
                                "Status berhasil diperbarui!"
                            );
                        }
                    } catch (RuntimeException e) {
                        if ("CANCEL".equals(e.getMessage())) {
                            System.out.println(
                                "Edit dibatalkan."
                            );
                            return;
                        }
                        throw e;
                    }
                    break;
                    
                case 2:
                    try {
                        String hargaInput = input(
                            "Harga Baru: "
                        );
                        if (hargaInput == null) {
                            System.out.println(
                                "Harga tidak diubah."
                            );
                            break;
                        }
                        int newRate =
                            Integer.parseInt(hargaInput);

                        if (mobilService.updateMobilRate(
                                m.getIdMobil(),
                                newRate)) {

                            m.setTarifSewa(newRate);

                            System.out.println(
                                "Harga berhasil diperbarui!"
                            );
                        }
                    } catch (RuntimeException e) {

                        if ("CANCEL".equals(e.getMessage())) {
                            System.out.println(
                                "Edit dibatalkan."
                            );
                            return;
                        }
                        throw e;

                    }

                    break;
                case 0:
                    return;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private String input(String prompt) {

        System.out.print(prompt);
        String value = sc.nextLine();

        if (value.equalsIgnoreCase("cancel")) {
            throw new RuntimeException("CANCEL");
        }

        if (value.trim().isEmpty()) {
            return null;
        }

        return value;
    }
}