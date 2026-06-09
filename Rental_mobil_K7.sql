USE rental_mobil_kelompok7;
GO

DROP TABLE IF EXISTS Kondisi_Setelah_Sewa;
DROP TABLE IF EXISTS Bertugas_Di;
DROP TABLE IF EXISTS FotoKondisi;
DROP TABLE IF EXISTS Pembayaran;
DROP TABLE IF EXISTS KondisiMobil;
DROP TABLE IF EXISTS Peminjaman;
DROP TABLE IF EXISTS Mobil;
DROP TABLE IF EXISTS Pegawai;
DROP TABLE IF EXISTS Member;
DROP TABLE IF EXISTS Cabang;
DROP TABLE IF EXISTS Jabatan;
DROP TABLE IF EXISTS Users;

--Entitas USER
--Menyimpan data akun pengguna sistem, baik member, pegawai, maupun manager.
CREATE TABLE Users(
    id_user INT IDENTITY(1,1) PRIMARY KEY,
    nama VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    no_telp VARCHAR(20),
    alamat VARCHAR(255),
    password VARCHAR(100),
    role VARCHAR(20)
);

--Entitas Jabatan
--Menyimpan jenis jabatan pegawai, seperti Manager dan Pegawai.
CREATE TABLE Jabatan(
    id_jabatan INT PRIMARY KEY,
    nama_jabatan VARCHAR(50)
);

-- Entitas Cabang
--Menyimpan data cabang rental mobil.
CREATE TABLE Cabang(
    id_cabang INT IDENTITY(1,1) PRIMARY KEY,
    nama VARCHAR(100),
    jam_operasional TIME,
    email VARCHAR(100),
    no_telepon VARCHAR(20),
    alamat VARCHAR(100),
    status VARCHAR(20)
);

--Entitas Member
--Menyimpan data pelanggan yang melakukan penyewaan mobil.
CREATE TABLE Member(
    id_member INT IDENTITY(1,1) PRIMARY KEY,
    id_user INT UNIQUE,
    no_sim VARCHAR(30),
    tanggal_berlaku_sim DATE,
    tanggal_daftar DATE,
    status VARCHAR(20),

    FOREIGN KEY(id_user)
        REFERENCES Users(id_user)
);

--Entitas Pegawai
--Menyimpan data pegawai yang bekerja pada cabang tertentu.
CREATE TABLE Pegawai(
    id_pegawai INT IDENTITY(1,1) PRIMARY KEY,
    id_user INT UNIQUE,
    id_jabatan INT,
    id_cabang INT,
    status VARCHAR(20),

    FOREIGN KEY(id_user)
        REFERENCES Users(id_user),

    FOREIGN KEY(id_jabatan)
        REFERENCES Jabatan(id_jabatan),

    FOREIGN KEY(id_cabang)
        REFERENCES Cabang(id_cabang)
);

--Entitas Mobil
--Menyimpan data kendaraan yang tersedia pada setiap cabang.
CREATE TABLE Mobil(
	id_mobil INT PRIMARY KEY,
    nama VARCHAR(100),
    tarif_sewa DECIMAL(12,2),
    tarif_denda DECIMAL(12,2),
	id_cabang INT,
	no_plat VARCHAR(20),
	brand VARCHAR(50),
	tipe VARCHAR(50),
	warna VARCHAR(30),
	kapasitas INT,
	tahun_pembuatan INT,
	status VARCHAR(20),

	FOREIGN KEY (id_cabang)
        REFERENCES Cabang(id_cabang)
);

--Entitas Peminjaman
--Menyimpan transaksi peminjaman kendaraan oleh member.
CREATE TABLE Peminjaman(
	id_transaksi INT PRIMARY KEY,
	id_mobil INT,
	id_cabang INT,
	id_member INT,
	status VARCHAR(20),
	total_hari_sewa INT,
	catatan VARCHAR(255),
	biaya_keterlambatan INT,
	biaya_sewa INT,
	waktu_rencana_pengembalian DATETIME,
	waktu_aktual_pengembalian DATETIME,
	total INT,
	waktu_pinjam DATETIME,

	FOREIGN KEY (id_mobil)
        REFERENCES Mobil(id_mobil),

	FOREIGN KEY (id_cabang)
        REFERENCES Cabang(id_cabang),

	FOREIGN KEY (id_member)
        REFERENCES Member(id_member)
);

-- Entitas Pembayaran 
--Menyimpan data pembayaran dari transaksi peminjaman.
CREATE TABLE Pembayaran(
	id_pembayaran INT PRIMARY KEY,
	id_transaksi INT,
	id_pegawai INT,
	waktu_pembayaran DATETIME,
	status VARCHAR(20),
	jumlah INT,
	Metode VARCHAR(20),

	FOREIGN KEY (id_transaksi)
        REFERENCES Peminjaman(id_transaksi),

	FOREIGN KEY (id_pegawai)
        REFERENCES Pegawai(id_pegawai)
);

-- Entitas Kondisi Mobil
--Menyimpan catatan kondisi kendaraan sebelum atau setelah disewa.
CREATE TABLE KondisiMobil (
    id_catatan INT PRIMARY KEY,
    id_mobil INT,
    id_pegawai INT,
    tipe_pencatatan VARCHAR(100),
    waktu_pencatatan DATETIME,
    deskripsi VARCHAR(255),
    tingkat_kondisi INT,

    FOREIGN KEY (id_mobil)
        REFERENCES Mobil(id_mobil),

    FOREIGN KEY (id_pegawai)
        REFERENCES Pegawai(id_pegawai)
);

--Entitas Foto Kondisi
--Menyimpan foto pendukung dari catatan kondisi mobil.
CREATE TABLE FotoKondisi(
	id_foto INT PRIMARY KEY,
	id_catatan INT,
	foto VARCHAR(255),

	FOREIGN KEY (id_catatan)
        REFERENCES KondisiMobil(id_catatan)
);

--Relasi Bertugas_di
--Menyimpan relasi pegawai dengan cabang tempat bertugas.
CREATE TABLE Bertugas_Di (
    id_pegawai INT,
    id_cabang INT,

    PRIMARY KEY (id_pegawai, id_cabang),

    FOREIGN KEY (id_pegawai)
        REFERENCES Pegawai(id_pegawai),

    FOREIGN KEY (id_cabang)
        REFERENCES Cabang(id_cabang)
);

--Relasi Kondisi_setelah_sewa
--Menyimpan relasi antara transaksi peminjaman dan catatan kondisi mobil setelah sewa.
CREATE TABLE Kondisi_Setelah_Sewa (
    id_transaksi INT,
    id_catatan INT,

    PRIMARY KEY (id_transaksi, id_catatan),

    FOREIGN KEY (id_transaksi)
        REFERENCES Peminjaman(id_transaksi),

    FOREIGN KEY (id_catatan)
        REFERENCES KondisiMobil(id_catatan)
);

-- Users
--dijalankan ketika terdapat pengguna baru yang dibuat pada sistem. Data user dapat berupa manager, pegawai, maupun member.
INSERT INTO Users (nama, email, no_telp, alamat, password, role)
VALUES
('manager', 'manager@gmail.com', '081234567890', 'Jakarta', 'admin', 'MANAGER'),
('pegawai', 'pegawai@gmail.com', '081398765432', 'Ciumbuleuit', 'pegawai', 'PEGAWAI'),
('Budi', 'budi@gmail.com', '08214211', 'Kemayoran', 'budi123', 'PEGAWAI'),
('Fajar', 'fajar@gmail.com', '08124613', 'Cimahi', 'fajar123', 'PEGAWAI'),
('Luna', 'luna@gmail.com', '081461456', 'Kiaracondong', 'luna123', 'PEGAWAI'),
('Citra', 'citra@gmail.com', '08124444', 'Bekasi', 'citra123', 'PEGAWAI'),
('member', 'member@gmail.com', '0812345662', 'Bandung', 'member', 'MEMBER'),
('Yosi', 'yosi@gmail.com', '0815337892', 'Cijerah', 'yosi123', 'MEMBER'),
('Kori', 'kori@gmail.com', '08124627', 'Antapani', 'andi123', 'MEMBER'),
('Dodo', 'dodo@gmail.com', '08263162', 'Cempaka Putih', 'dodo123', 'MEMBER'),
('Andi', 'andi@gmail.com', '08121111', 'Depok', 'andi123', 'MEMBER');

-- Jabatan 
--digunakan untuk memasukkan jenis jabatan yang tersedia pada sistem
INSERT INTO Jabatan
VALUES
(1, 'Manager'),
(2, 'Pegawai');

--Cabang
--digunakan untuk menambahkan data cabang rental mobil
INSERT INTO Cabang
(nama, jam_operasional, email, no_telepon, alamat, status)
VALUES
('Cabang Jakarta', '08:00', 'cabang1@gmail.com', '0811111', 'Jakarta', 'Aktif'),
('Cabang Bekasi', '09:00', 'bekasi@gmail.com', '082222', 'Bekasi', 'Aktif'),
('Cabang Medan', '10:00', 'medan@gmail.com', '0833333', 'Medan', 'Aktif'),
('Cabang Cimahi', '09:00', 'cimahi@gmail.com', '0844444', 'Cimahi', 'Aktif'),
('Cabang Bandung', '11:00', 'bandung@gmail.com', '0855555', 'Bandung', 'Aktif');

--Pegawai
--digunakan untuk memasukkan data pegawai beserta jabatan dan cabang tempat pegawai bekerja
INSERT INTO Pegawai (id_user, id_jabatan, id_cabang, status)
VALUES 
(1, 1, 1, 'Aktif'),
(2, 2, 1, 'Aktif'),
(3, 2, 2, 'Nonaktif'),
(4, 2, 3, 'Aktif'),
(5, 2, 4, 'Aktif'),
(6, 2, 5, 'Nonaktif');

-- Member 
--dijalankan setelah user berhasil didaftarkan sebagai member.
INSERT INTO Member (id_user, no_sim, tanggal_berlaku_sim, tanggal_daftar, status)
VALUES 
(7, 'B123456', '2028-01-01', '2023-05-24', 'Aktif'),
(8, 'D889900', '2028-03-07', '2025-03-10', 'Aktif'),
(9, 'K902342', '2030-05-06', '2026-06-10', 'Nonaktif'),
(10, 'F667788', '2026-12-21', '2023-10-12', 'Aktif'),
(11, 'J124989', '2027-08-27', '2024-12-10', 'Nonaktif');

--Mobil
--digunakan untuk memasukkan data kendaraan yang tersedia pada cabang tertentu
INSERT INTO Mobil (
    id_mobil,
    nama,
    tarif_sewa,
    tarif_denda,
    id_cabang,
    no_plat,
    brand,
    tipe,
    warna,
    kapasitas,
    tahun_pembuatan,
    status
)
VALUES
(1, 'Toyota Innova', 500000, 75000, 1, 'B5555DD', 'Toyota', 'MPV', 'Hitam', 8, 2021, 'Tidak Tersedia'),
(2, 'Honda Jazz', 350000, 50000, 2, 'D6666EE', 'Honda', 'Hatchback', 'Kuning', 5, 2019, 'Tersedia'),
(3, 'Wuling Almaz', 450000, 60000, 2, 'A1111CC', 'Wuling', 'SUV', 'Putih', 4, 2022, 'Tersedia'),
(4, 'Mitsubishi Xpander', 550000, 75000, 3, 'L7777FF', 'Mitsubishi', 'MPV', 'Putih', 7, 2024, 'Tidak Tersedia'),
(5, 'Suzuki Carry', 250000, 40000, 1, 'B8888GG', 'Suzuki', 'Minivan', 'Silver', 2, 2018, 'Tidak Tersedia'),
(6, 'Wuling Air EV', 300000, 50000, 2, 'Z3333KK', 'Wuling', 'Hatchback', 'Putih', 2, 2023, 'Tersedia'),
(7, 'Mazda Hatchback', 600000, 80000, 4, 'W0000DD', 'Mazda', 'Hatchback', 'Merah', 8, 2021, 'Tersedia'),
(8, 'Toyota Fortuner', 850000, 100000, 3, 'D9999HH', 'Toyota', 'SUV', 'Hitam', 7, 2025, 'Tersedia'),
(9, 'Toyota Fortuner', 850000, 100000, 3, 'O2222WW', 'Toyota', 'SUV', 'Hitam', 7, 2025, 'Tersedia'),
(10, 'Toyota Fortuner', 850000, 100000, 4, 'E3333RR', 'Toyota', 'SUV', 'Hitam', 7, 2025, 'Tidak Tersedia'),
(11, 'Toyota Fortuner', 850000, 100000, 1, 'T7777KK', 'Toyota', 'SUV', 'Hitam', 7, 2025, 'Tersedia'),
(12, 'Honda Brio', 300000, 50000, 5, 'D1111AA', 'Honda', 'Hatchback', 'Merah', 5, 2022, 'Tersedia'),
(13, 'Toyota Avanza', 400000, 60000, 1, 'B2222BB', 'Toyota', 'MPV', 'Silver', 7, 2023, 'Tersedia'),
(14, 'Daihatsu Xenia', 380000, 55000, 2, 'D3333CC', 'Daihatsu', 'MPV', 'Hitam', 7, 2021, 'Tidak Tersedia'),
(15, 'Toyota Rush', 500000, 70000, 3, 'BK4444DD', 'Toyota', 'SUV', 'Putih', 7, 2024, 'Tersedia'),
(16, 'Suzuki Ertiga', 420000, 60000, 4, 'F5555EE', 'Suzuki', 'MPV', 'Abu-Abu', 7, 2022, 'Tersedia'),
(17, 'Honda HR-V', 650000, 85000, 5, 'B6666FF', 'Honda', 'SUV', 'Hitam', 5, 2024, 'Tersedia'),
(18, 'Toyota Raize', 550000, 75000, 1, 'D7777GG', 'Toyota', 'SUV', 'Kuning', 5, 2023, 'Tidak Tersedia'),
(19, 'Hyundai Creta', 700000, 90000, 2, 'BK8888HH', 'Hyundai', 'SUV', 'Putih', 5, 2025, 'Tersedia'),
(20, 'Kia Sonet', 680000, 90000, 3, 'L9999II', 'Kia', 'SUV', 'Biru', 5, 2024, 'Tersedia'),
(21, 'Mitsubishi Pajero', 950000, 120000, 4, 'B1234PJ', 'Mitsubishi', 'SUV', 'Hitam', 7, 2025, 'Tidak Tersedia'),
(22, 'Toyota Alphard', 1500000, 200000, 5, 'D5678AL', 'Toyota', 'MPV', 'Putih', 7, 2025, 'Tersedia'),
(23, 'BMW X1', 1800000, 250000, 1, 'B9876BM', 'BMW', 'SUV', 'Abu-Abu', 5, 2024, 'Tersedia'),
(24, 'Mercedes GLA', 2200000, 300000, 2, 'D5432MC', 'Mercedes', 'SUV', 'Hitam', 5, 2025, 'Tersedia'),
(25, 'Tesla Model 3', 2500000, 350000, 3, 'B7777TS', 'Tesla', 'Sedan', 'Putih', 5, 2025, 'Tidak Tersedia');


--digunakan untuk mencatat pegawai yang bertugas di cabang tertentu
INSERT INTO Bertugas_Di (id_pegawai, id_cabang)
VALUES
(1,1),
(2,1),
(3,2),
(4,3),
(5,4),
(6,5);

--digunakan untuk mencatat kondisi kendaraan
INSERT INTO KondisiMobil
(id_catatan,id_mobil,id_pegawai,tipe_pencatatan,waktu_pencatatan,deskripsi,tingkat_kondisi)
VALUES
(1,1,2,'Sebelum Sewa','2025-01-01 08:00','Kondisi sangat baik',95),
(2,4,4,'Sebelum Sewa','2025-01-02 09:00','Body mulus',90),
(3,5,2,'Sebelum Sewa','2025-01-03 10:00','Ban baru diganti',92),
(4,10,5,'Sebelum Sewa','2025-01-04 08:30','Interior bersih',96),
(5,14,2,'Sebelum Sewa','2025-01-05 09:30','Ada goresan kecil',85),
(6,18,4,'Sebelum Sewa','2025-01-06 10:00','Mesin normal',94),
(7,21,5,'Sebelum Sewa','2025-01-07 08:00','Cat masih bagus',91),
(8,25,4,'Sebelum Sewa','2025-01-08 09:00','Mobil sangat prima',98);

--digunakan untuk menyimpan foto dokumentasi kondisi mobil
INSERT INTO FotoKondisi
(id_foto,id_catatan,foto)
VALUES
(1,1,'innova1.jpg'),
(2,2,'xpander1.jpg'),
(3,3,'carry1.jpg'),
(4,4,'fortuner1.jpg'),
(5,5,'xenia1.jpg'),
(6,6,'raize1.jpg'),
(7,7,'pajero1.jpg'),
(8,8,'tesla1.jpg');

--digunakan untuk mencatat transaksi peminjaman mobil oleh member
INSERT INTO Peminjaman
(id_transaksi,id_mobil,id_cabang,id_member,status,total_hari_sewa,
catatan,biaya_keterlambatan,biaya_sewa,
waktu_rencana_pengembalian,waktu_aktual_pengembalian,total,waktu_pinjam)
VALUES
(1,1,1,1,'Selesai',3,'-',0,1500000,'2025-01-04','2025-01-04',1500000,'2025-01-01'),
(2,4,3,2,'Selesai',2,'-',0,1100000,'2025-01-05','2025-01-05',1100000,'2025-01-03'),
(3,5,1,4,'Selesai',5,'-',40000,1250000,'2025-01-10','2025-01-11',1290000,'2025-01-05'),
(4,10,4,1,'Selesai',2,'-',0,1700000,'2025-01-08','2025-01-08',1700000,'2025-01-06'),
(5,14,2,2,'Selesai',4,'-',55000,1520000,'2025-01-12','2025-01-13',1575000,'2025-01-08'),

(6,2,2,1,'Selesai',2,'-',0,700000,'2025-02-03','2025-02-03',700000,'2025-02-01'),
(7,3,2,2,'Selesai',3,'-',0,1350000,'2025-02-05','2025-02-05',1350000,'2025-02-02'),
(8,6,2,4,'Selesai',1,'-',0,300000,'2025-02-04','2025-02-04',300000,'2025-02-03'),
(9,7,4,1,'Selesai',4,'-',80000,2400000,'2025-02-10','2025-02-11',2480000,'2025-02-06'),
(10,8,3,2,'Selesai',2,'-',0,1700000,'2025-02-08','2025-02-08',1700000,'2025-02-06'),

(11,9,3,4,'Selesai',2,'-',0,1700000,'2025-02-09','2025-02-09',1700000,'2025-02-07'),
(12,11,1,1,'Selesai',3,'-',100000,2550000,'2025-02-12','2025-02-13',2650000,'2025-02-09'),
(13,12,5,2,'Selesai',4,'-',0,1200000,'2025-02-15','2025-02-15',1200000,'2025-02-11'),
(14,13,1,4,'Selesai',3,'-',0,1200000,'2025-02-14','2025-02-14',1200000,'2025-02-11'),
(15,15,3,1,'Selesai',2,'-',70000,1000000,'2025-02-16','2025-02-17',1070000,'2025-02-14'),

(16,16,4,2,'Selesai',5,'-',0,2100000,'2025-03-01','2025-03-01',2100000,'2025-02-24'),
(17,17,5,4,'Selesai',3,'-',85000,1950000,'2025-03-03','2025-03-04',2035000,'2025-02-28'),
(18,19,2,1,'Selesai',2,'-',0,1400000,'2025-03-05','2025-03-05',1400000,'2025-03-03'),
(19,20,3,2,'Selesai',4,'-',90000,2720000,'2025-03-09','2025-03-10',2810000,'2025-03-05'),
(20,22,5,4,'Selesai',2,'-',0,3000000,'2025-03-08','2025-03-08',3000000,'2025-03-06'),

(21,23,1,1,'Selesai',3,'-',0,5400000,'2025-03-12','2025-03-12',5400000,'2025-03-09'),
(22,24,2,2,'Selesai',2,'-',300000,4400000,'2025-03-11','2025-03-12',4700000,'2025-03-09'),
(23,25,3,4,'Selesai',1,'-',0,2500000,'2025-03-10','2025-03-10',2500000,'2025-03-09'),
(24,2,2,1,'Selesai',2,'-',0,700000,'2025-04-03','2025-04-03',700000,'2025-04-01'),
(25,3,2,2,'Selesai',3,'-',0,1350000,'2025-04-05','2025-04-05',1350000,'2025-04-02'),

(26,6,2,4,'Selesai',4,'-',50000,1200000,'2025-04-08','2025-04-09',1250000,'2025-04-04'),
(27,7,4,1,'Selesai',2,'-',0,1200000,'2025-04-07','2025-04-07',1200000,'2025-04-05'),
(28,8,3,2,'Selesai',5,'-',100000,4250000,'2025-04-12','2025-04-13',4350000,'2025-04-07'),
(29,11,1,4,'Selesai',3,'-',0,2550000,'2025-04-10','2025-04-10',2550000,'2025-04-07'),
(30,12,5,1,'Selesai',2,'-',0,600000,'2025-04-12','2025-04-12',600000,'2025-04-10'),

(31,13,1,2,'Diproses',4,'-',0,1600000,'2026-06-15',NULL,1600000,'2026-06-11'),
(32,15,3,4,'Diproses',3,'-',0,1500000,'2026-06-14',NULL,1500000,'2026-06-11'),
(33,17,5,1,'Diproses',2,'-',0,1300000,'2026-06-13',NULL,1300000,'2026-06-11'),
(34,20,3,2,'Diproses',2,'-',0,1360000,'2026-06-13',NULL,1360000,'2026-06-11'),
(35,22,5,4,'Diproses',1,'-',0,1500000,'2026-06-12',NULL,1500000,'2026-06-11');

--digunakan untuk mencatat pembayaran transaksi peminjaman
INSERT INTO Pembayaran
(id_pembayaran,id_transaksi,id_pegawai,waktu_pembayaran,status,jumlah,Metode)
SELECT
id_transaksi,
id_transaksi,
CASE
    WHEN id_transaksi % 4 = 0 THEN 4
    WHEN id_transaksi % 3 = 0 THEN 5
    ELSE 2
END,
DATEADD(HOUR,2,waktu_pinjam),
'Lunas',
total,
CASE
    WHEN id_transaksi % 3 = 0 THEN 'Transfer'
    WHEN id_transaksi % 2 = 0 THEN 'QRIS'
    ELSE 'Cash'
END
FROM Peminjaman;

--digunakan untuk menghubungkan transaksi peminjaman dengan catatan kondisi mobi
INSERT INTO Kondisi_Setelah_Sewa
(id_transaksi,id_catatan)
VALUES
(1,1),(2,2),(3,3),(4,4),(5,5),
(6,6),(7,7),(8,8),(9,1),(10,2),
(11,3),(12,4),(13,5),(14,6),(15,7),
(16,8),(17,1),(18,2),(19,3),(20,4),
(21,5),(22,6),(23,7),(24,8),(25,1),
(26,2),(27,3),(28,4),(29,5),(30,6),
(31,7),(32,8),(33,1),(34,2),(35,3);