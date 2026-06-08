// package service;

// import model.Mobil;
// import model.Role;
// import model.User;

// public class TesPeminjaman {

//     public static void main(String[] args) {
//         UserService userService = new UserService();
//         MobilService mobilService = new MobilService();
//         TransaksiService transaksiService = new TransaksiService();

//         // 1. Ambil user member (id_member: 1, id_user: 1)
//         // Kita simulasikan user yang sudah login
//         User dummyMember = new User(
//             1, 1, 0, "Dodo", "dodo@gmail.com", "123", "0812345662", "Bandung", "B123456", Role.MEMBER
//         );

//         // 2. Ambil mobil yang tersedia
//         System.out.println("Mencari mobil tersedia...");
//         java.util.ArrayList<Mobil> listMobil = mobilService.getAvailableMobil();
        
//         if (listMobil.isEmpty()) {
//             System.out.println("Tidak ada mobil tersedia untuk ditest!");
//             return;
//         }

//         Mobil targetMobil = listMobil.get(0);
//         System.out.println("Mencoba meminjam mobil: " + targetMobil.getNama() + " (ID: " + targetMobil.getIdMobil() + ")");

//         // 3. Lakukan peminjaman selama 3 hari
//         int lamaSewa = 3;
//         boolean sukses = transaksiService.createPeminjaman(dummyMember, targetMobil, lamaSewa);

//         if (sukses) {
//             System.out.println("BERHASIL: Data peminjaman telah tercatat di database SQL Server!");
//             System.out.println("Status mobil " + targetMobil.getNama() + " sekarang seharusnya 'Dipinjam'.");
//         } else {
//             System.out.println("GAGAL: Terjadi kesalahan saat menyimpan ke database.");
//         }
//     }
// }