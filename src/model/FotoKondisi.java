package model;

public class FotoKondisi {
    private int idFoto;
    private int idCatatan;
    private String foto;

    public FotoKondisi(int idFoto, int idCatatan, String foto) {
        this.idFoto = idFoto;
        this.idCatatan = idCatatan;
        this.foto = foto;
    }

    // Getters
    public int getIdFoto() { return idFoto; }
    public int getIdCatatan() { return idCatatan; }
    public String getFoto() { return foto; }
}
