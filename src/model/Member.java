package model;

import java.time.LocalDate;

public class Member extends User {

    private LocalDate tanggalBerlakuSim;

    public Member(
            String nama,
            String email,
            String password,
            String nomorTelepon,
            String alamat,
            String noSim,
            LocalDate tanggalBerlakuSim
    ) {
        super(
                nama,
                email,
                password,
                nomorTelepon,
                alamat,
                noSim,
                Role.MEMBER
        );

        this.tanggalBerlakuSim = tanggalBerlakuSim;
    }

    public LocalDate getTanggalBerlakuSim() {
        return tanggalBerlakuSim;
    }

    public void setTanggalBerlakuSim(LocalDate tanggalBerlakuSim) {
        this.tanggalBerlakuSim = tanggalBerlakuSim;
    }
}