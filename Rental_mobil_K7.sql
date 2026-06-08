USE rental_mobil_kelompok7;

--Entitas USER
CREATE TABLE Users(
	id_user INT PRIMARY KEY,
	nama VARCHAR(100),
	email VARCHAR(100),
	no_telp VARCHAR(20),
	alamat VARCHAR (255),
	password VARCHAR(100)
);
--Entitas Member
CREATE TABLE Member (
    id_member INT IDENTITY(1,1) PRIMARY KEY,
    id_user INT NOT NULL UNIQUE,
    no_sim VARCHAR(30),
    tanggal_berlaku_sim DATE,
    tanggal_daftar DATE,
    status VARCHAR(20),

    FOREIGN KEY (id_user) REFERENCES Users(id_user)
);


--Entitas Jabatan

CREATE TABLE Jabatan(
	id_jabatan INT PRIMARY KEY,
	nama_jabatan VARCHAR(50)
);

--Entitas Pegawai
CREATE TABLE Pegawai (
	id_pegawai INT IDENTITY(1,1) PRIMARY KEY,
	id_user INT,
	id_jabatan INT,
	status VARCHAR(20),
	id_manager INT NULL

	FOREIGN KEY (id_user) REFERENCES Users(id_user),
	FOREIGN KEY (id_jabatan) REFERENCES Jabatan(id_jabatan)
);



-- Entitas Cabang
CREATE TABLE Cabang(
	id_cabang INT PRIMARY KEY,
	nama VARCHAR(100),
	jam_operasional TIME,
	email VARCHAR(100),
	no_telepon VARCHAR(20),
	alamat VARCHAR(100),
);

--Entitas Mobil
CREATE TABLE Mobil(
	id_mobil INT PRIMARY KEY,
	id_cabang INT,
	no_plat VARCHAR(20),
	brand VARCHAR(50),
	tipe VARCHAR(50),
	warna VARCHAR(30),
	kapasitas INT,
	tahun_pembuatan INT,
	status VARCHAR(20)
	harga_sewa INT,

	FOREIGN KEY (id_cabang) REFERENCES Cabang(id_cabang)
);


--Entitas Peminjaman
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

	FOREIGN KEY (id_mobil) REFERENCES Mobil(id_mobil),
	FOREIGN KEY (id_cabang) REFERENCES Cabang(id_cabang),
	FOREIGN KEY (id_member) REFERENCES Member(id_member)
);

-- Entitas Pembayaran 
CREATE TABLE Pembayaran(
	id_pembayaran INT PRIMARY KEY,
	id_transaksi INT,
	id_pegawai INT,
	waktu_pembayaran DATETIME,
	status VARCHAR(20),
	jumlah INT,
	Metode VARCHAR(20),

	FOREIGN KEY (id_transaksi) REFERENCES Peminjaman(id_transaksi),
	FOREIGN KEY (id_pegawai) REFERENCES Pegawai (id_pegawai)
);

-- Entitas Kondisi Mobil
CREATE TABLE KondisiMobil(
	id_catatan INT PRIMARY KEY,
	id_pegawai INT,
	id_transaksi INT,
	tipe_pencatatan VARCHAR(100),
	waktu_pencatatan DATETIME,
	deskripsi VARCHAR(255),
	tingkat_kondisi INT,

	FOREIGN KEY (id_pegawai) REFERENCES Pegawai (id_pegawai),
	FOREIGN KEY (id_transaksi) REFERENCES Peminjaman (id_transaksi)
);

--Entitas Foto Kondisi
CREATE TABLE FotoKondisi(
	id_foto INT PRIMARY KEY,
	id_catatan INT,
	foto VARCHAR(255),

	FOREIGN KEY (id_catatan) REFERENCES KondisiMobil (id_catatan)
);


-- Users
INSERT INTO Users 
VALUES 
	(1, 'Manager', 'manager@gmail', '0812345662', 'Bandung', 'Manager'),
	(2, 'Admin1', 'admin1@gmail.com', '08124858', 'Jakarta', 'admin123'),
	(3, 'Admin2', 'admin2@gmail.com', '081233215', 'Semarang', 'admin123');

-- Jabatan 
INSERT INTO Jabatan 
VALUES
	(1, 'Manager'),
	(2, 'Pegawai');

--Pegawai
INSERT INTO Pegawai(id_user, id_jabatan, status, id_manager)
VALUES 
	(1,1,'Aktif', NULL),
	


INSERT INTO Pegawai (id_user, id_jabatan, status)
VALUES (2, 1, 'Aktif');

-- Member 
INSERT INTO Member 
VALUES 
	(1, 3, 'B123456', '2028-01-01', '2026-05-24', 'Aktif');

--Cabang
INSERT INTO Cabang 
VALUES
	(1,'Cabang Jakarta', '08:00:00', 'cabang1@gmail.com', '0811111', 'Jakarta');

--Mobil
INSERT INTO Mobil
VALUES 
	(1, 1, 'B1234CD', 'Toyota', 'Avanza', 'Hitam', 7, 2022, 'Tersedia');


-- TES
SELECT *
FROM
	Mobil

UPDATE Users
SET
	nama = 'Manager',
	email = 'Manager@gmailcom',
	password = 'manager123'
WHERE id_user = 1;

UPDATE Pegawai
SET
	id_manager = 1
WHERE id_jabatan = 2




UPDATE Users
SET
	email = 'manager@gmail.com'
WHERE 
	id_user = 1;


--INSERT USERS untuk master
INSERT INTO Users 
VALUES
(5, 'Pegawai1', 'pegawai1@gmail.com', '0811111111', 'Bandung', 'pegawai123'),
(6, 'Pegawai2', 'pegawai2@gmail.com', '0822222222', 'Jakarta', 'pegawai123');

-- INSERT PEGAWAI
INSERT INTO Pegawai (id_user, id_jabatan, status, id_manager)
VALUES
	(5, 2, 'Aktif', 1),
	(6, 2, 'Aktif', 1);
-- INSERT MOBIL
--Dieksekusi saat data akan ditambahkan oleh manager ke dalam sistem
INSERT INTO Mobil
VALUES
	(3, 2, 'D1111AA', 'Daihatsu', 'Xenia', 'Putih', 7, 2021, 'Tersedia', 280000),
	(4, 2, 'D2222BB', 'Toyota', 'Innova', 'Silver', 7, 2022, 'Tersedia', 450000),
	(5, 3, 'H3333CC', 'Honda', 'Brio', 'Merah', 5, 2020, 'Tersedia', 250000);

-- INSERT MEMBER
-- Dieksekusi saat pelanggan melakukan pendaftaran member
INSERT INTO Users
--
VALUES
	(8, 'Member3', 'member3@gmail.com', '0833333333', 'Semarang', 'member123'),
	(9, 'Member4', 'member4@gmail.com', '0834444444', 'Surabaya', 'member123'),
	(10, 'Member5', 'member5@gmail.com', '0835555555', 'Yogyakarta', 'member123');

INSERT INTO Member (id_user, no_sim, tanggal_berlaku_sim, tanggal_daftar, status)
VALUES
	(8, 'SIM003', '2028-03-01', '2024-01-03', 'Aktif'),
	(9, 'SIM004', '2028-04-01', '2024-01-04', 'Aktif'),
	(10, 'SIM005', '2028-05-01', '2024-01-05', 'Aktif');
-- INSERT CABANG
-- Dieksekusi saat data cabang rental ditambahkan oleh manager
INSERT INTO Cabang
VALUES
(1, 'Cabang Bandung', '08:00:00', 'bandung@rental.com', '022111111', 'Jl. Merdeka No. 10 Bandung'),
(2, 'Cabang Jakarta', '08:00:00', 'jakarta@rental.com', '021222222', 'Jl. Sudirman No. 20 Jakarta'),
(3, 'Cabang Semarang', '08:00:00', 'semarang@rental.com', '024333333', 'Jl. Pemuda No. 30 Semarang');


-- INSERT Peminjaman
-- Dieksekusi saat member melakukan peminjaman kendaraan

INSERT INTO Peminjaman (
    id_transaksi,
    id_mobil,
    id_cabang,
    id_member,
    status,
    total_hari_sewa,
    catatan,
    biaya_keterlambatan,
    biaya_sewa,
    waktu_rencana_pengembalian,
    waktu_aktual_pengembalian,
    total,
    waktu_pinjam
)
VALUES
	(4, 4, 2, 4, 'KEMBALI', 2, 'Peminjaman normal', 0, 900000, '2024-01-06 13:00:00', '2024-01-06 12:00:00', 900000, '2024-01-04 13:00:00'),
	(5, 5, 3, 5, 'KEMBALI', 2, 'Peminjaman normal', 0, 500000, '2024-01-07 14:00:00', '2024-01-07 13:00:00', 500000, '2024-01-05 14:00:00'),
	(6, 1, 1, 1, 'KEMBALI', 3, 'Peminjaman normal', 0, 900000, '2024-01-10 10:00:00', '2024-01-10 09:00:00', 900000, '2024-01-07 10:00:00'),
	(7, 2, 1, 2, 'KEMBALI', 1, 'Peminjaman harian', 0, 300000, '2024-01-09 11:00:00', '2024-01-09 10:30:00', 300000, '2024-01-08 11:00:00'),
	(8, 3, 2, 3, 'KEMBALI', 4, 'Peminjaman normal', 0, 1120000, '2024-01-14 09:00:00', '2024-01-14 08:45:00', 1120000, '2024-01-10 09:00:00'),
	(9, 4, 2, 4, 'KEMBALI', 2, 'Peminjaman normal', 0, 900000, '2024-01-13 13:00:00', '2024-01-13 12:30:00', 900000, '2024-01-11 13:00:00'),
	(10, 5, 3, 5, 'KEMBALI', 3, 'Peminjaman normal', 0, 750000, '2024-01-16 14:00:00', '2024-01-16 13:30:00', 750000, '2024-01-13 14:00:00'),

	(11, 1, 1, 2, 'KEMBALI', 1, 'Peminjaman harian', 0, 300000, '2024-02-02 10:00:00', '2024-02-02 09:50:00', 300000, '2024-02-01 10:00:00'),
	(12, 2, 1, 3, 'KEMBALI', 2, 'Peminjaman normal', 0, 600000, '2024-02-04 11:00:00', '2024-02-04 10:45:00', 600000, '2024-02-02 11:00:00'),
	(13, 3, 2, 4, 'KEMBALI', 2, 'Peminjaman normal', 0, 560000, '2024-02-05 09:00:00', '2024-02-05 08:30:00', 560000, '2024-02-03 09:00:00'),
	(14, 4, 2, 5, 'KEMBALI', 3, 'Peminjaman normal', 0, 1350000, '2024-02-07 13:00:00', '2024-02-07 12:30:00', 1350000, '2024-02-04 13:00:00'),
	(15, 5, 3, 1, 'KEMBALI', 1, 'Peminjaman harian', 0, 250000, '2024-02-06 14:00:00', '2024-02-06 13:20:00', 250000, '2024-02-05 14:00:00'),

	(16, 1, 1, 2, 'KEMBALI', 4, 'Terlambat 1 hari', 50000, 1200000, '2024-02-12 10:00:00', '2024-02-13 10:00:00', 1250000, '2024-02-08 10:00:00'),
	(17, 2, 1, 3, 'KEMBALI', 2, 'Peminjaman normal', 0, 600000, '2024-02-12 11:00:00', '2024-02-12 10:00:00', 600000, '2024-02-10 11:00:00'),
	(18, 3, 2, 4, 'KEMBALI', 3, 'Peminjaman normal', 0, 840000, '2024-02-15 09:00:00', '2024-02-15 08:40:00', 840000, '2024-02-12 09:00:00'),
	(19, 4, 2, 5, 'KEMBALI', 1, 'Peminjaman harian', 0, 450000, '2024-02-14 13:00:00', '2024-02-14 12:45:00', 450000, '2024-02-13 13:00:00'),
	(20, 5, 3, 1, 'KEMBALI', 2, 'Peminjaman normal', 0, 500000, '2024-02-17 14:00:00', '2024-02-17 13:30:00', 500000, '2024-02-15 14:00:00'),

	(21, 1, 1, 2, 'DIPINJAM', 2, 'Sedang dipinjam', 0, 600000, '2024-03-03 10:00:00', NULL, 600000, '2024-03-01 10:00:00'),
	(22, 2, 1, 3, 'DIPINJAM', 3, 'Sedang dipinjam', 0, 900000, '2024-03-05 11:00:00', NULL, 900000, '2024-03-02 11:00:00'),
	(23, 3, 2, 4, 'KEMBALI', 2, 'Peminjaman normal', 0, 560000, '2024-03-05 09:00:00', '2024-03-05 08:50:00', 560000, '2024-03-03 09:00:00'),
	(24, 4, 2, 5, 'KEMBALI', 2, 'Peminjaman normal', 0, 900000, '2024-03-06 13:00:00', '2024-03-06 12:30:00', 900000, '2024-03-04 13:00:00'),
	(25, 5, 3, 1, 'KEMBALI', 3, 'Peminjaman normal', 0, 750000, '2024-03-08 14:00:00', '2024-03-08 13:45:00', 750000, '2024-03-05 14:00:00'),

	(26, 1, 1, 2, 'KEMBALI', 1, 'Peminjaman harian', 0, 300000, '2024-03-08 10:00:00', '2024-03-08 09:30:00', 300000, '2024-03-07 10:00:00'),
	(27, 2, 1, 3, 'KEMBALI', 2, 'Peminjaman normal', 0, 600000, '2024-03-10 11:00:00', '2024-03-10 10:45:00', 600000, '2024-03-08 11:00:00'),
	(28, 3, 2, 4, 'KEMBALI', 4, 'Terlambat 1 hari', 50000, 1120000, '2024-03-13 09:00:00', '2024-03-14 09:00:00', 1170000, '2024-03-09 09:00:00'),
	(29, 4, 2, 5, 'KEMBALI', 1, 'Peminjaman harian', 0, 450000, '2024-03-11 13:00:00', '2024-03-11 12:50:00', 450000, '2024-03-10 13:00:00'),
	(30, 5, 3, 1, 'KEMBALI', 2, 'Peminjaman normal', 0, 500000, '2024-03-13 14:00:00', '2024-03-13 13:30:00', 500000, '2024-03-11 14:00:00');


-- INSERT Pembayaran
-- Dieksekusi saat member melakukan pembayaran transaksi peminjaman kendaraan

INSERT INTO Pembayaran (
    id_pembayaran,
    id_transaksi,
    id_pegawai,
    waktu_pembayaran,
    status,
    jumlah,
    Metode
)
VALUES
	(1, 1, 2, '2024-01-01 10:10:00', 'LUNAS', 600000, 'Tunai'),
	(2, 2, 3, '2024-01-02 11:10:00', 'LUNAS', 900000, 'Transfer'),
	(3, 3, 4, '2024-01-03 09:10:00', 'LUNAS', 280000, 'Tunai'),
	(4, 4, 5, '2024-01-04 13:10:00', 'LUNAS', 900000, 'Transfer'),
	(5, 5, 2, '2024-01-05 14:10:00', 'LUNAS', 500000, 'Tunai'),

	(6, 6, 3, '2024-01-07 10:15:00', 'LUNAS', 900000, 'Transfer'),
	(7, 7, 4, '2024-01-08 11:15:00', 'LUNAS', 300000, 'Tunai'),
	(8, 8, 5, '2024-01-10 09:15:00', 'LUNAS', 1120000, 'Transfer'),
	(9, 9, 2, '2024-01-11 13:15:00', 'LUNAS', 900000, 'Tunai'),
	(10, 10, 3, '2024-01-13 14:15:00', 'LUNAS', 750000, 'Transfer'),

	(11, 11, 4, '2024-02-01 10:15:00', 'LUNAS', 300000, 'Tunai'),
	(12, 12, 5, '2024-02-02 11:15:00', 'LUNAS', 600000, 'Transfer'),
	(13, 13, 2, '2024-02-03 09:15:00', 'LUNAS', 560000, 'Tunai'),
	(14, 14, 3, '2024-02-04 13:15:00', 'LUNAS', 1350000, 'Transfer'),
	(15, 15, 4, '2024-02-05 14:15:00', 'LUNAS', 250000, 'Tunai'),

	(16, 16, 5, '2024-02-08 10:15:00', 'LUNAS', 1250000, 'Transfer'),
	(17, 17, 2, '2024-02-10 11:15:00', 'LUNAS', 600000, 'Tunai'),
	(18, 18, 3, '2024-02-12 09:15:00', 'LUNAS', 840000, 'Transfer'),
	(19, 19, 4, '2024-02-13 13:15:00', 'LUNAS', 450000, 'Tunai'),
	(20, 20, 5, '2024-02-15 14:15:00', 'LUNAS', 500000, 'Transfer'),

	(21, 21, 2, '2024-03-01 10:15:00', 'LUNAS', 600000, 'Tunai'),
	(22, 22, 3, '2024-03-02 11:15:00', 'LUNAS', 900000, 'Transfer'),
	(23, 23, 4, '2024-03-03 09:15:00', 'LUNAS', 560000, 'Tunai'),
	(24, 24, 5, '2024-03-04 13:15:00', 'LUNAS', 900000, 'Transfer'),
	(25, 25, 2, '2024-03-05 14:15:00', 'LUNAS', 750000, 'Tunai'),

	(26, 26, 3, '2024-03-07 10:15:00', 'LUNAS', 300000, 'Transfer'),
	(27, 27, 4, '2024-03-08 11:15:00', 'LUNAS', 600000, 'Tunai'),
	(28, 28, 5, '2024-03-09 09:15:00', 'LUNAS', 1170000, 'Transfer'),
	(29, 29, 2, '2024-03-10 13:15:00', 'LUNAS', 450000, 'Tunai'),
	(30, 30, 3, '2024-03-11 14:15:00', 'LUNAS', 500000, 'Transfer');



-- INSERT KondisiMobil
-- Dieksekusi saat pegawai mencatat kondisi mobil sebelum atau sesudah peminjaman

INSERT INTO KondisiMobil (
    id_catatan,
    id_pegawai,
    id_transaksi,
    tipe_pencatatan,
    waktu_pencatatan,
    deskripsi,
    tingkat_kondisi
)
VALUES
	(1, 2, 1, 'SESUDAH PENGEMBALIAN', '2024-01-03 09:40:00', 'Mobil kembali dalam kondisi baik', 8),
	(2, 3, 2, 'SESUDAH PENGEMBALIAN', '2024-01-05 10:20:00', 'Mobil kembali tanpa kerusakan', 8),
	(3, 4, 3, 'SESUDAH PENGEMBALIAN', '2024-01-04 08:45:00', 'Mobil dalam kondisi baik', 9),
	(4, 5, 4, 'SESUDAH PENGEMBALIAN', '2024-01-06 12:20:00', 'Mobil kembali normal', 8),
	(5, 2, 5, 'SESUDAH PENGEMBALIAN', '2024-01-07 13:20:00', 'Mobil bersih dan baik', 9),

	(6, 3, 6, 'SESUDAH PENGEMBALIAN', '2024-01-10 09:20:00', 'Mobil kembali dalam kondisi baik', 8),
	(7, 4, 7, 'SESUDAH PENGEMBALIAN', '2024-01-09 10:45:00', 'Mobil tidak mengalami kerusakan', 9),
	(8, 5, 8, 'SESUDAH PENGEMBALIAN', '2024-01-14 09:00:00', 'Mobil kembali normal', 8),
	(9, 2, 9, 'SESUDAH PENGEMBALIAN', '2024-01-13 12:45:00', 'Mobil dalam kondisi baik', 8),
	(10, 3, 10, 'SESUDAH PENGEMBALIAN', '2024-01-16 13:45:00', 'Mobil kembali bersih', 9),

	(11, 4, 11, 'SESUDAH PENGEMBALIAN', '2024-02-02 10:05:00', 'Mobil dalam kondisi baik', 8),
	(12, 5, 12, 'SESUDAH PENGEMBALIAN', '2024-02-04 11:00:00', 'Mobil kembali normal', 8),
	(13, 2, 13, 'SESUDAH PENGEMBALIAN', '2024-02-05 08:45:00', 'Mobil tidak ada kerusakan', 9),
	(14, 3, 14, 'SESUDAH PENGEMBALIAN', '2024-02-07 12:45:00', 'Mobil dalam kondisi baik', 8),
	(15, 4, 15, 'SESUDAH PENGEMBALIAN', '2024-02-06 13:35:00', 'Mobil kembali bersih', 9),

	(16, 5, 16, 'SESUDAH PENGEMBALIAN', '2024-02-13 10:20:00', 'Mobil terlambat dikembalikan namun kondisi baik', 7),
	(17, 2, 17, 'SESUDAH PENGEMBALIAN', '2024-02-12 10:20:00', 'Mobil kembali normal', 8),
	(18, 3, 18, 'SESUDAH PENGEMBALIAN', '2024-02-15 09:00:00', 'Mobil dalam kondisi baik', 8),
	(19, 4, 19, 'SESUDAH PENGEMBALIAN', '2024-02-14 13:00:00', 'Mobil tidak mengalami kerusakan', 9),
	(20, 5, 20, 'SESUDAH PENGEMBALIAN', '2024-02-17 13:45:00', 'Mobil kembali bersih', 9),

	(21, 2, 21, 'SEBELUM PEMINJAMAN', '2024-03-01 09:30:00', 'Mobil dicek sebelum dipinjam', 8),
	(22, 3, 22, 'SEBELUM PEMINJAMAN', '2024-03-02 10:30:00', 'Mobil siap digunakan', 8),
	(23, 4, 23, 'SESUDAH PENGEMBALIAN', '2024-03-05 09:10:00', 'Mobil kembali dalam kondisi baik', 8),
	(24, 5, 24, 'SESUDAH PENGEMBALIAN', '2024-03-06 12:45:00', 'Mobil kembali normal', 8),
	(25, 2, 25, 'SESUDAH PENGEMBALIAN', '2024-03-08 14:00:00', 'Mobil bersih dan baik', 9),

	(26, 3, 26, 'SESUDAH PENGEMBALIAN', '2024-03-08 09:45:00', 'Mobil tidak mengalami kerusakan', 9),
	(27, 4, 27, 'SESUDAH PENGEMBALIAN', '2024-03-10 11:00:00', 'Mobil kembali normal', 8),
	(28, 5, 28, 'SESUDAH PENGEMBALIAN', '2024-03-14 09:20:00', 'Mobil terlambat dikembalikan namun kondisi baik', 7),
	(29, 2, 29, 'SESUDAH PENGEMBALIAN', '2024-03-11 13:05:00', 'Mobil dalam kondisi baik', 8),
	(30, 3, 30, 'SESUDAH PENGEMBALIAN', '2024-03-13 13:45:00', 'Mobil kembali bersih', 9);

-- INSERT FotoKondisi
-- Dieksekusi saat pegawai mengunggah foto bukti kondisi mobil

INSERT INTO FotoKondisi (
    id_foto,
    id_catatan,
    foto
)
VALUES
	(1, 1, 'foto_kondisi_001.jpg'),
	(2, 2, 'foto_kondisi_002.jpg'),
	(3, 3, 'foto_kondisi_003.jpg'),
	(4, 4, 'foto_kondisi_004.jpg'),
	(5, 5, 'foto_kondisi_005.jpg'),

	(6, 6, 'foto_kondisi_006.jpg'),
	(7, 7, 'foto_kondisi_007.jpg'),
	(8, 8, 'foto_kondisi_008.jpg'),
	(9, 9, 'foto_kondisi_009.jpg'),
	(10, 10, 'foto_kondisi_010.jpg'),

	(11, 11, 'foto_kondisi_011.jpg'),
	(12, 12, 'foto_kondisi_012.jpg'),
	(13, 13, 'foto_kondisi_013.jpg'),
	(14, 14, 'foto_kondisi_014.jpg'),
	(15, 15, 'foto_kondisi_015.jpg'),

	(16, 16, 'foto_kondisi_016.jpg'),
	(17, 17, 'foto_kondisi_017.jpg'),
	(18, 18, 'foto_kondisi_018.jpg'),
	(19, 19, 'foto_kondisi_019.jpg'),
	(20, 20, 'foto_kondisi_020.jpg'),

	(21, 21, 'foto_kondisi_021.jpg'),
	(22, 22, 'foto_kondisi_022.jpg'),
	(23, 23, 'foto_kondisi_023.jpg'),
	(24, 24, 'foto_kondisi_024.jpg'),
	(25, 25, 'foto_kondisi_025.jpg'),

	(26, 26, 'foto_kondisi_026.jpg'),
	(27, 27, 'foto_kondisi_027.jpg'),
	(28, 28, 'foto_kondisi_028.jpg'),
	(29, 29, 'foto_kondisi_029.jpg'),
	(30, 30, 'foto_kondisi_030.jpg');


	