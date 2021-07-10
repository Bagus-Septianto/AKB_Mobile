package com.bagusseptianto.akbp3l.Model;

import java.io.Serializable;

public class Menu implements Serializable {
    private int ID_MENU, ID_BAHAN, HARGA_MENU;
    private String NAMA_MENU, JENIS_MENU, DESKRIPSI_MENU, UNIT_MENU, gambar;

    public Menu(int ID_MENU, int ID_BAHAN, int HARGA_MENU, String NAMA_MENU, String JENIS_MENU, String DESKRIPSI_MENU, String UNIT_MENU, String gambar) {
        this.ID_MENU = ID_MENU;
        this.ID_BAHAN = ID_BAHAN;
        this.HARGA_MENU = HARGA_MENU;
        this.NAMA_MENU = NAMA_MENU;
        this.JENIS_MENU = JENIS_MENU;
        this.DESKRIPSI_MENU = DESKRIPSI_MENU;
        this.UNIT_MENU = UNIT_MENU;
        this.gambar = gambar;
    }

    public int getID_MENU() {
        return ID_MENU;
    }

    public void setID_MENU(int ID_MENU) {
        this.ID_MENU = ID_MENU;
    }

    public int getID_BAHAN() {
        return ID_BAHAN;
    }

    public void setID_BAHAN(int ID_BAHAN) {
        this.ID_BAHAN = ID_BAHAN;
    }

    public int getHARGA_MENU() {
        return HARGA_MENU;
    }

    public void setHARGA_MENU(int HARGA_MENU) {
        this.HARGA_MENU = HARGA_MENU;
    }

    public String getNAMA_MENU() {
        return NAMA_MENU;
    }

    public void setNAMA_MENU(String NAMA_MENU) {
        this.NAMA_MENU = NAMA_MENU;
    }

    public String getJENIS_MENU() {
        return JENIS_MENU;
    }

    public void setJENIS_MENU(String JENIS_MENU) {
        this.JENIS_MENU = JENIS_MENU;
    }

    public String getDESKRIPSI_MENU() {
        return DESKRIPSI_MENU;
    }

    public void setDESKRIPSI_MENU(String DESKRIPSI_MENU) {
        this.DESKRIPSI_MENU = DESKRIPSI_MENU;
    }

    public String getUNIT_MENU() {
        return UNIT_MENU;
    }

    public void setUNIT_MENU(String UNIT_MENU) {
        this.UNIT_MENU = UNIT_MENU;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
