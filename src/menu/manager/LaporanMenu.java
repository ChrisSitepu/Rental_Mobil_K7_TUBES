package menu.manager;

import config.SQLDatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import service.LaporanService;

public class LaporanMenu {

    private void mobilPerCabang() {
        String sql = """
            SELECT c.nama, COUNT(m.id_mobil) AS total
            FROM Cabang c
            LEFT JOIN Mobil m ON c.id_cabang = m.id_cabang
            GROUP BY c.nama
        """;

        try (
            Connection conn = SQLDatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                System.out.println(rs.getString("nama") + " : " + rs.getInt("total") + " mobil");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void pegawaiPerCabang() {
        String sql = """
            SELECT c.nama, COUNT(p.id_pegawai) AS total
            FROM Cabang c
            LEFT JOIN Pegawai p ON c.id_cabang = p.id_cabang
            GROUP BY c.nama
        """;

        try (
            Connection conn = SQLDatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                System.out.println(rs.getString("nama") + " : " + rs.getInt("total") + " pegawai");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void show() {
        LaporanService laporanService = new LaporanService();
        System.out.println("\n========== LAPORAN RENTAL ==========");
        System.out.println("\n--- LAPORAN UMUM ---");
        System.out.println("Total Transaksi            : " + laporanService.getTotalTransaksi());
        System.out.println("Mobil Tersedia             : " + laporanService.getMobilTersedia());
        System.out.println("Total Pendapatan           : Rp" + laporanService.getTotalPendapatan());

        System.out.println("\n--- LAPORAN PENJUALAN ---");
        System.out.println("Pendapatan Bulan Ini       : Rp" + laporanService.getPendapatanBulanIni());
        System.out.println("Jumlah Transaksi Bulan Ini : " + laporanService.getJumlahTransaksiBulanIni());

        System.out.println("\n--- LAPORAN MOBIL ---");
        System.out.println("Mobil Paling Banyak Disewa : " + laporanService.getMobilPalingBanyakDisewa());

        System.out.println("\nMobil per Cabang:");
        mobilPerCabang();

        System.out.println("\n--- LAPORAN CABANG ---");
        System.out.println("Cabang Paling Ramai        : " + laporanService.getCabangPalingRamai());

        System.out.println("\nJumlah Pegawai per Cabang:");
        pegawaiPerCabang();
        System.out.println("\n====================================");
    }
}