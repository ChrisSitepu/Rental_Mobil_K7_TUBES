package model;

import java.time.LocalDateTime;

public class KondisiMobil {
    private int idCatatan;
    private int idPegawai;
    private int idTransaksi;
    private String tipePencatatan;
    private LocalDateTime waktuPencatatan;
    private String deskripsi;
    private int tingkatKondisi;

    public KondisiMobil(int idCatatan, int idPegawai, int idTransaksi, String tipePencatatan, 
                        LocalDateTime waktuPencatatan, String deskripsi, int tingkatKondisi) {
        this.idCatatan = idCatatan;
        this.idPegawai = idPegawai;
        this.idTransaksi = idTransaksi;
        this.tipePencatatan = tipePencatatan;
        this.waktuPencatatan = waktuPencatatan;
        this.deskripsi = deskripsi;
        this.tingkatKondisi = tingkatKondisi;
    }

    // Getters
    public int getIdCatatan() { return idCatatan; }
    public int getIdPegawai() { return idPegawai; }
    public int getIdTransaksi() { return idTransaksi; }
    public String getTipePencatatan() { return tipePencatatan; }
    public LocalDateTime getWaktuPencatatan() { return waktuPencatatan; }
    public String getDeskripsi() { return deskripsi; }
    public int getTingkatKondisi() { return tingkatKondisi; }
}
