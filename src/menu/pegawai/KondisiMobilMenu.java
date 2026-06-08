package menu.pegawai;

import service.TransaksiService;

public class KondisiMobilMenu {

    public void show() {

        System.out.println(
            "\n=== RIWAYAT PENCATATAN KONDISI MOBIL ==="
        );

        TransaksiService transaksiService =
            new TransaksiService();

        transaksiService.tampilSemuaKondisiMobil();
    }
}