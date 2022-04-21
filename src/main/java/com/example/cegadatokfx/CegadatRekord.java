package com.example.cegadatokfx;

import java.sql.Date;

public class CegadatRekord {
    private int az;
    private String nev;
    private int nem;
    private Date szuldatum;
    private String varos;
    private String cim;
    private String telefon;
    private int kereset;

//add constructor
    public CegadatRekord(int az, String nev, int nem, Date szuldatum, String varos, String cim, String telefon, int kereset) {
        this.az = az;
        this.nev = nev;
        this.nem = nem;
        this.szuldatum =szuldatum;
        this.varos = varos;
        this.cim = cim;
        this.telefon = telefon;
        this.kereset = kereset;
    }

    //generate override, return átírása
    @Override
    public String toString() {
        return "CegadatRekord{" +
                "az=" + az +
                ", nev='" + nev + '\'' +
                ", nem='" + nem + '\'' +
                ", szuldatum=" + szuldatum +'\'' +
                ", varos=" + varos +'\'' +
                ", cim='" + cim+ '\'' +
                ", telefon='" + telefon+ '\'' +
                ", kereset='" + kereset+ '\'' +
                '}';
    }
// generate getter
    public int getAz() {
        return az;
    }

    public String getNev() {
        return nev;
    }

    public int getNem() {
        return nem;
    }

    public Date getSzuldatum() {
        return szuldatum;
    }

    public String getVaros() {
        return varos;
    }

    public String getCim() {
        return cim;
    }

    public String getTelefon() {
        return telefon;
    }

    public int getKereset() {
        return kereset;
    }

}