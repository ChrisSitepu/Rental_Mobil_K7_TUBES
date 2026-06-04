package model;

public class Mobil {

    private int idMobil;
    private int idCabang;
    private String brand;
    private String tipe;
    private String warna;
    private String plat;
    private int kapasitas;
    private int tahunPembuatan;
    private boolean available;

    public Mobil(
            int idMobil,
            int idCabang,
            String brand,
            String tipe,
            String warna,
            String plat,
            int kapasitas,
            int tahunPembuatan,
            boolean available
    ) {
        this.idMobil = idMobil;
        this.idCabang = idCabang;
        this.brand = brand;
        this.tipe = tipe;
        this.warna = warna;
        this.plat = plat;
        this.kapasitas = kapasitas;
        this.tahunPembuatan = tahunPembuatan;
        this.available = available;
    }

    public int getIdMobil() { return idMobil; }
    public int getIdCabang() { return idCabang; }
    public String getNama() { return brand + " " + tipe; }
    public String getBrand() { return brand; }
    public String getTipe() { return tipe; }
    public String getWarna() { return warna; }
    public String getPlat() { return plat; }
    public int getKapasitas() { return kapasitas; }
    public int getTahunPembuatan() { return tahunPembuatan; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    
    // Default rates as hardcoded getters since they are not in DB
    public double getTarifSewa() { return 300000; }
    public double getTarifDenda() { return 50000; }
}
