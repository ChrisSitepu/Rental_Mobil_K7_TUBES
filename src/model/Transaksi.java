package model;

import java.time.LocalDateTime;

public class Transaksi {
    private int idTransaksi;
    private int idMobil;
    private int idCabang;
    private int idMember;
    private String status;
    private int totalHariSewa;
    private String catatan;
    private int biayaKeterlambatan;
    private int biayaSewa;
    private LocalDateTime waktuRencanaPengembalian;
    private LocalDateTime waktuAktualPengembalian;
    private int total;
    private LocalDateTime waktuPinjam;

    // Additional fields for display
    private String namaMobil;
    private String platMobil;
    private String namaMember;

    public Transaksi(int idTransaksi, int idMobil, int idCabang, int idMember, String status, int totalHariSewa,
            int biayaSewa, int total, LocalDateTime waktuPinjam, LocalDateTime waktuRencanaPengembalian) {
        this.idTransaksi = idTransaksi;
        this.idMobil = idMobil;
        this.idCabang = idCabang;
        this.idMember = idMember;
        this.status = status;
        this.totalHariSewa = totalHariSewa;
        this.biayaSewa = biayaSewa;
        this.total = total;
        this.waktuPinjam = waktuPinjam;
        this.waktuRencanaPengembalian = waktuRencanaPengembalian;
    }

    public Transaksi(String string, String string2, int i, String string3, String string4, String string5,
            String string6, String string7, String string8) {
        // TODO Auto-generated constructor stub
    }

    // Getters and Setters
    public int getIdTransaksi() {
        return idTransaksi;
    }

    public int getIdMobil() {
        return idMobil;
    }

    public int getIdMember() {
        return idMember;
    }

    public String getStatus() {
        return status;
    }

    public int getTotalHariSewa() {
        return totalHariSewa;
    }

    public int getBiayaSewa() {
        return biayaSewa;
    }

    public int getBiayaKeterlambatan() {
        return biayaKeterlambatan;
    }

    public void setBiayaKeterlambatan(int biayaKeterlambatan) {
        this.biayaKeterlambatan = biayaKeterlambatan;
    }

    public LocalDateTime getWaktuRencanaPengembalian() {
        return waktuRencanaPengembalian;
    }

    public LocalDateTime getWaktuAktualPengembalian() {
        return waktuAktualPengembalian;
    }

    public void setWaktuAktualPengembalian(LocalDateTime waktuAktualPengembalian) {
        this.waktuAktualPengembalian = waktuAktualPengembalian;
    }

    public String getNamaMobil() {
        return namaMobil;
    }

    public void setNamaMobil(String namaMobil) {
        this.namaMobil = namaMobil;
    }

    public String getPlatMobil() {
        return platMobil;
    }

    public void setPlatMobil(String platMobil) {
        this.platMobil = platMobil;
    }

    public String getNamaMember() {
        return namaMember;
    }

    public void setNamaMember(String namaMember) {
        this.namaMember = namaMember;
    }

    public void setIdTransaksi(int idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public void setIdMobil(int idMobil) {
        this.idMobil = idMobil;
    }

    public int getIdCabang() {
        return idCabang;
    }

    public void setIdCabang(int idCabang) {
        this.idCabang = idCabang;
    }

    public void setIdMember(int idMember) {
        this.idMember = idMember;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTotalHariSewa(int totalHariSewa) {
        this.totalHariSewa = totalHariSewa;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public void setBiayaSewa(int biayaSewa) {
        this.biayaSewa = biayaSewa;
    }

    public void setWaktuRencanaPengembalian(LocalDateTime waktuRencanaPengembalian) {
        this.waktuRencanaPengembalian = waktuRencanaPengembalian;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public LocalDateTime getWaktuPinjam() {
        return waktuPinjam;
    }

    public void setWaktuPinjam(LocalDateTime waktuPinjam) {
        this.waktuPinjam = waktuPinjam;
    }

}
