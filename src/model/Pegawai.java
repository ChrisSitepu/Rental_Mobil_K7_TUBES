package model;

public class Pegawai extends User {
    private int idPegawai;
    private int idJabatan;
    private String status;

    public Pegawai(int idUser, int idMember, int idPegawai, int idJabatan, String nama, String email, 
                   String password, String nomorTelepon, String alamat, String status) {
        super(idUser, idMember, idPegawai, nama, email, password, nomorTelepon, alamat, "-", Role.PEGAWAI);
        this.idPegawai = idPegawai;
        this.idJabatan = idJabatan;
        this.status = status;
    }

    public int getIdPegawai() { return idPegawai; }
    public int getIdJabatan() { return idJabatan; }
    public String getStatus() { return status; }
}
