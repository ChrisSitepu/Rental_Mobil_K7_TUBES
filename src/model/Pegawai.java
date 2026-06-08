package model;

public class Pegawai extends User {
    private int idPegawai;
    private int idJabatan;
    private String status;
    private int id_manager;
    private int idCabang;

    public Pegawai(int idUser, int idMember, int idPegawai, int idJabatan, int idCabang, String nama, String email, 
                   String password, String nomorTelepon, String alamat, String status, int id_manager) {
        super(idUser, idMember, idPegawai, idCabang, nama, email, password, nomorTelepon, alamat, "-", Role.PEGAWAI);
        this.idPegawai = idPegawai;
        this.idJabatan = idJabatan;
        this.idCabang = idCabang;
        this.status = status;
        this.id_manager = id_manager;
    }

    public int getIdPegawai() { return idPegawai; }
    public int getIdJabatan() { return idJabatan; }
    public String getStatus() { return status; }
    public int getidManager() {return id_manager;}
    public int getIdCabang() { return idCabang; }

    public void setIdManager(int newID){
        id_manager = newID;
    }

    public void setIdCabang(int idCabang) {
        this.idCabang = idCabang;
    }
}
