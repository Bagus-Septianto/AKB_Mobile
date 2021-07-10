package com.bagusseptianto.akbp3l.Model;

public class Pesanan {
    private int JUMLAH_PESANAN;
    private String NAMA_MENU, JENIS_MENU, HARGA_MENU, STATUS_PESANAN;

    public Pesanan(int JUMLAH_PESANAN, String NAMA_MENU, String JENIS_MENU, String HARGA_MENU, String STATUS_PESANAN) {
        this.JUMLAH_PESANAN = JUMLAH_PESANAN;
        this.NAMA_MENU = NAMA_MENU;
        this.JENIS_MENU = JENIS_MENU;
        this.HARGA_MENU = HARGA_MENU;
        this.STATUS_PESANAN = STATUS_PESANAN;
    }

    public int getJUMLAH_PESANAN() {
        return JUMLAH_PESANAN;
    }

    public void setJUMLAH_PESANAN(int JUMLAH_PESANAN) {
        this.JUMLAH_PESANAN = JUMLAH_PESANAN;
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

    public String getHARGA_MENU() {
        return HARGA_MENU;
    }

    public void setHARGA_MENU(String HARGA_MENU) {
        this.HARGA_MENU = HARGA_MENU;
    }

    public String getSTATUS_PESANAN() {
        return STATUS_PESANAN;
    }

    public void setSTATUS_PESANAN(String STATUS_PESANAN) {
        this.STATUS_PESANAN = STATUS_PESANAN;
    }
}
