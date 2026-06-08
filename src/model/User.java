package model;

public class User {

    private int idUser;
    private int idMember;
    private int idPegawai;

    private String nama;
    private String email;
    private String password;
    private String nomorTelepon;
    private String alamat;
    private String noSim;

    private Role role;
    private int idPegawai;
    private int idMember;
    private int idCabang;

    public User(
            int idUser,
            int idMember,
            int idPegawai,
            int idCabang,
            String nama,
            String email,
            String password,
            String nomorTelepon,
            Role role) {

        this.nama = nama;
        this.email = email;
        this.password = password;
        this.nomorTelepon = nomorTelepon;
        this.role = role;
    }

    // Constructor yang dipakai UserService
    public User(
            int idUser,
            int idMember,
            int idPegawai,
            String nama,
            String email,
            String password,
            String nomorTelepon,
            String alamat,
            String noSim,
            Role role) {

        this.idUser = idUser;
        this.idMember = idMember;
        this.idPegawai = idPegawai;
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.nomorTelepon = nomorTelepon;
        this.alamat = alamat;
        this.noSim = noSim;
        this.role = role;
    }

    public int getIdUser() {
        return idUser;
    }

    public int getIdPegawai() {
        return idPegawai;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getNoSim() {
        return noSim;
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

    public String getAlamat() {
        return alamat;
    }

    public String getNoSim() {
        return noSim;
    }

    public Role getRole() {
        return role;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNomorTelepon(String nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setNoSim(String noSim) {
        this.noSim = noSim;
    }

    public void setIdUser(int idUser){
        this.idUser = idUser;
    }

    public void setIdMember(int idMember){
        this.idMember = idMember;
    }

    public int getIdMember(){
        return idMember;
    }

    public void setIdPegawai(int idPegawai) {
        this.idPegawai = idPegawai;
    }
}