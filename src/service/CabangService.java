package service;

import java.util.ArrayList;

import model.Cabang;

public class CabangService {

    private static ArrayList<Cabang> cabangList =
            new ArrayList<>();

    static {

        cabangList.add(
                new Cabang("Bandung")
        );

        cabangList.add(
                new Cabang("Jakarta")
        );

        cabangList.add(
                new Cabang("Surabaya")
        );
    }

    public ArrayList<Cabang> getAllCabang(){
        return cabangList;
    }
}