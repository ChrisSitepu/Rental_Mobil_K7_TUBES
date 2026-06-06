//  package menu.manager;

// public class LaporanMenu {

//     public void show() {

//         System.out.println("\n========== LAPORAN RENTAL ==========");

//         System.out.println("\n--- LAPORAN UMUM ---");

//         System.out.println("Total Transaksi            : 25");
//         System.out.println("Mobil Tersedia             : 10");
//         System.out.println("Total Pendapatan           : Rp15.000.000");

//         System.out.println("\n--- LAPORAN PENJUALAN ---");

//         System.out.println("Rata-rata Pendapatan/Bulan : Rp5.000.000");
//         System.out.println("Jumlah Transaksi/Bulan     : 8 transaksi");

//         System.out.println("\n--- LAPORAN MOBIL ---");

//         System.out.println("Mobil Paling Banyak Disewa : Toyota Avanza");

//         System.out.println("\nMobil per Cabang:");
//         System.out.println("- Bandung  : 5 mobil");
//         System.out.println("- Jakarta  : 4 mobil");
//         System.out.println("- Surabaya : 6 mobil");

//         System.out.println("\n--- LAPORAN CABANG ---");

//         System.out.println("Cabang Paling Ramai        : Bandung");

//         System.out.println("\nJumlah Pegawai per Cabang:");
//         System.out.println("- Bandung  : 3 pegawai");
//         System.out.println("- Jakarta  : 2 pegawai");
//         System.out.println("- Surabaya : 4 pegawai");

//         System.out.println("\n====================================");
//     }
// }
package menu.manager;
import service.LaporanService;

public class LaporanMenu {

    public void show() {

        LaporanService laporanService = new LaporanService();

        System.out.println("\n========== LAPORAN RENTAL ==========");

        System.out.println("\n--- LAPORAN UMUM ---");

        System.out.println(
                "Total Transaksi            : "
                        + laporanService.getTotalTransaksi());

        System.out.println(
                "Mobil Tersedia             : "
                        + laporanService.getMobilTersedia());

        System.out.println(
                "Total Pendapatan           : Rp"
                        + laporanService.getTotalPendapatan());

        System.out.println("\n--- LAPORAN PENJUALAN ---");

        System.out.println(
                "Pendapatan Bulan Ini       : Rp"
                        + laporanService.getPendapatanBulanIni());

        System.out.println(
                "Jumlah Transaksi Bulan Ini : "
                        + laporanService.getJumlahTransaksiBulanIni());

        System.out.println("\n--- LAPORAN MOBIL ---");

        System.out.println(
                "Mobil Paling Banyak Disewa : "
                        + laporanService.getMobilPalingBanyakDisewa());

        System.out.println("\n--- LAPORAN CABANG ---");

        System.out.println(
                "Cabang Paling Ramai        : "
                        + laporanService.getCabangPalingRamai());

        System.out.println("\n====================================");
    }
}