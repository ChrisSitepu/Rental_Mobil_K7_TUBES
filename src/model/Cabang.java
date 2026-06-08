package model;
import java.time.LocalTime;

public class Cabang {

    private int idCabang;
    private String nama;
    private LocalTime jamOperasional;
    private String email;
    private String noTelepon;
    private String alamat;

    public Cabang(
            int idCabang,
            String nama,
            LocalTime jamOperasional,
            String email,
            String noTelepon,
            String alamat) {

        this.idCabang = idCabang;
        this.nama = nama;
        this.jamOperasional = jamOperasional;
        this.email = email;
        this.noTelepon = noTelepon;
        this.alamat = alamat;
    }

    public Cabang(int idCabang, String nama) {
        this.idCabang = idCabang;
        this.nama = nama;
    }

    public int getIdCabang() { return idCabang; }
    public String getNama() { return nama; }
    public LocalTime getJamOperasional() { return jamOperasional; }
    public String getEmail() { return email; }
    public String getNoTelepon() { return noTelepon; }
    public String getAlamat() { return alamat; }
}