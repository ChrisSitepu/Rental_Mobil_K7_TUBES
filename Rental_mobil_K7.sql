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
	()


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
