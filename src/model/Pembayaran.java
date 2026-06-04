package model;

import java.time.LocalDateTime;

public class Pembayaran {
    private int idPembayaran;
    private int idTransaksi;
    private int idPegawai;
    private LocalDateTime waktuPembayaran;
    private String status;
    private int jumlah;
    private String metode;

    public Pembayaran(int idPembayaran, int idTransaksi, int idPegawai, LocalDateTime waktuPembayaran, 
                     String status, int jumlah, String metode) {
        this.idPembayaran = idPembayaran;
        this.idTransaksi = idTransaksi;
        this.idPegawai = idPegawai;
        this.waktuPembayaran = waktuPembayaran;
        this.status = status;
        this.jumlah = jumlah;
        this.metode = metode;
    }

    // Getters
    public int getIdPembayaran() { return idPembayaran; }
    public int getIdTransaksi() { return idTransaksi; }
    public int getIdPegawai() { return idPegawai; }
    public LocalDateTime getWaktuPembayaran() { return waktuPembayaran; }
    public String getStatus() { return status; }
    public int getJumlah() { return jumlah; }
    public String getMetode() { return metode; }
}
