package model;

public class Mobil {

    private int idMobil;
    private int idCabang;
    private String nama;
    private String brand;
    private String tipe;
    private String warna;
    private String plat;

    private int kapasitas;
    private int tahunPembuatan;

    private double tarifSewa;
    private double tarifDenda;

    private boolean available;

    public Mobil(
            int idMobil,
            int idCabang,
            String nama,
            String brand,
            String tipe,
            String warna,
            String plat,
            int kapasitas,
            int tahunPembuatan,
            double tarifSewa,
            double tarifDenda,
            boolean available
    ) {
        this.idMobil = idMobil;
        this.idCabang = idCabang;
        this.nama = nama;
        this.brand = brand;
        this.tipe = tipe;
        this.warna = warna;
        this.plat = plat;
        this.kapasitas = kapasitas;
        this.tahunPembuatan = tahunPembuatan;
        this.tarifSewa = tarifSewa;
        this.tarifDenda = tarifDenda;
        this.available = available;
    }

    public int getIdMobil() {
        return idMobil;
    }

    public int getIdCabang() {
        return idCabang;
    }

    public String getNama() {
        return nama;
    }

    public String getBrand() {
        return brand;
    }

    public String getTipe() {
        return tipe;
    }

    public String getWarna() {
        return warna;
    }

    public String getPlat() {
        return plat;
    }

    public int getKapasitas() {
        return kapasitas;
    }

    public int getTahunPembuatan() {
        return tahunPembuatan;
    }

    public double getTarifSewa() {
        return tarifSewa;
    }

    public double getTarifDenda() {
        return tarifDenda;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}