package menu.manager;

import java.util.ArrayList;
import java.util.Scanner;
import model.User;
import model.Role;
import model.Cabang;
import service.UserService;
import service.CabangService;

public class KelolaPegawaiMenu {

    private UserService userService = new UserService();
    private CabangService cabangService = new CabangService();
    private Scanner sc = new Scanner(System.in);

    public void show() {

        while (true) {

            System.out.println("\n=== KELOLA DATA PEGAWAI ===");

            ArrayList<User> pegawaiList = userService.getAllByRole(Role.PEGAWAI);

            for (int i = 0; i < pegawaiList.size(); i++) {

                User p = pegawaiList.get(i);

                System.out.println(
                        (i + 1) + ". " + p.getNama() + " (" + p.getEmail() + ")"
                );
            }

            System.out.println("0. Kembali");

            System.out.print("Pilih pegawai: ");
            int pilih = sc.nextInt();
            sc.nextLine();

            if (pilih == 0) {
                return;
            }

            int index = pilih - 1;

            if (index < 0 || index >= pegawaiList.size()) {
                System.out.println("Pegawai tidak ditemukan!");
                continue;
            }

            detailPegawai(pegawaiList.get(index));
        }
    }

    private void detailPegawai(User p) {

        while (true) {
            System.out.println("\n=== DETAIL PEGAWAI ===");

            System.out.println("Nama      : " + p.getNama());
            System.out.println("Email     : " + p.getEmail());
            System.out.println("Alamat    : " + p.getAlamat());
            System.out.println("No HP     : " + p.getNomorTelepon());
            System.out.println("ID Cabang : " + (p.getIdCabang() == 0 ? "Belum Ditentukan" : p.getIdCabang()));

            System.out.println("\n1. Ubah Cabang");
            System.out.println("2. Hapus Pegawai");
            System.out.println("3. Kembali");

            System.out.print("Pilih: ");
            int pilih = sc.nextInt();
            sc.nextLine();

            switch (pilih) {

                case 1:
                    ubahCabangPegawai(p);
                    return; // Return to list to refresh data

                case 2:
                    if (userService.deleteUserByEmail(p.getEmail())) {
                        System.out.println("Pegawai berhasil dihapus dari database!");
                    }
                    return;

                case 3:
                    return;

                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }

    private void ubahCabangPegawai(User p) {
        System.out.println("\n=== UBAH CABANG PEGAWAI ===");
        ArrayList<Cabang> cabangList = cabangService.getAllCabang();

        if (cabangList.isEmpty()) {
            System.out.println("Tidak ada cabang tersedia!");
            return;
        }

        for (int i = 0; i < cabangList.size(); i++) {
            Cabang c = cabangList.get(i);
            System.out.println((i + 1) + ". " + c.getNama() + " (ID: " + c.getIdCabang() + ")");
        }

        System.out.print("Pilih cabang baru: ");
        int pilih = sc.nextInt();
        sc.nextLine();

        if (pilih < 1 || pilih > cabangList.size()) {
            System.out.println("Pilihan tidak valid!");
            return;
        }

        Cabang selected = cabangList.get(pilih - 1);

        if (userService.updatePegawaiCabang(p.getIdPegawai(), selected.getIdCabang())) {
            System.out.println("Cabang pegawai berhasil diperbarui!");
            p.setIdCabang(selected.getIdCabang());
        } else {
            System.out.println("Gagal memperbarui cabang pegawai.");
        }
    }
}