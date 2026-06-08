package model;

public class Mobil {

    private int idMobil;
    private int idCabang;

    private String nama;
    private String brand;
    private String tipe;
    private String warna;
    private String plat;
    private String status;

    private int kapasitas;
    private int tahunPembuatan;

    private double tarifSewa;
    private double tarifDenda;

    public Mobil(
            int idMobil,
            int idCabang,
            String nama,
            String brand,
            String tipe,
            String warna,
            String plat,
            String status,
            int kapasitas,
            int tahunPembuatan,
            double tarifSewa,
            double tarifDenda
    ) {

        this.idMobil = idMobil;
        this.idCabang = idCabang;
        this.nama = nama;
        this.brand = brand;
        this.tipe = tipe;
        this.warna = warna;
        this.plat = plat;
        this.status = status;
        this.kapasitas = kapasitas;
        this.tahunPembuatan = tahunPembuatan;
        this.tarifSewa = tarifSewa;
        this.tarifDenda = tarifDenda;
    }

    public int getIdMobil() { return idMobil; }
    public int getIdCabang() { return idCabang; }
    public String getNama() { return nama; }
    public String getBrand() { return brand; }
    public String getTipe() { return tipe; }
    public String getWarna() { return warna; }
    public String getPlat() { return plat; }
    public String getStatus() { return status; }
    public int getKapasitas() { return kapasitas; }
    public int getTahunPembuatan() { return tahunPembuatan; }
    public double getTarifSewa() { return tarifSewa; }
    public double getTarifDenda() { return tarifDenda; }

    public void setTarifSewa(double tarifSewa) {
        this.tarifSewa = tarifSewa;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}