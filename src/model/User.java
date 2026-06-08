package model;

public class User {

    private String nama;

    private String email;

    private String password;

    private String nomorTelepon;

    private Role role;

    public User(
            String nama,
            String email,
            String password,
            String nomorTelepon,
            Role role
    ) {

        this.nama = nama;
        this.email = email;
        this.password = password;
        this.nomorTelepon = nomorTelepon;
        this.role = role;
    }

    public int getIdCabang() {
        return idCabang;
    }

    public void setIdCabang(int idCabang) {
        this.idCabang = idCabang;
    }

    public String getNama() {
        return nama;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public Role getRole() {
        return role;
    }

    public void setNama(String nama){
    this.nama = nama;
}

    public void setEmail(String email){
        this.email = email;
    }

    public void setNomorTelepon(String nomorTelepon){
        this.nomorTelepon = nomorTelepon;
    }
}