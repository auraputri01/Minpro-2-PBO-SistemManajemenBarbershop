/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Aura
 */
public class Layanan {
    private String idLayanan;
    private String namaLayanan;
    private int harga;
    private int durasiMenit;

    public Layanan(String idLayanan, String namaLayanan, int harga, int durasiMenit) {
        this.idLayanan = idLayanan;
        this.namaLayanan = namaLayanan;
        this.harga = harga;
        this.durasiMenit = durasiMenit;
    }

    public String getIdLayanan() {
        return idLayanan;
    }

    public String getNamaLayanan() {
        return namaLayanan;
    }

    public int getHarga() {
        return harga;
    }

    public int getDurasiMenit() {
        return durasiMenit;
    }

    public void setNamaLayanan(String namaLayanan) {
        this.namaLayanan = namaLayanan;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public void setDurasiMenit(int durasiMenit) {
        this.durasiMenit = durasiMenit;
    }

    public void tampilkanData() {
        System.out.println("ID Layanan : " + idLayanan);
        System.out.println("Nama       : " + namaLayanan);
        System.out.println("Harga      : Rp. " + harga);
        System.out.println("Durasi     : " + durasiMenit + " menit");
    }
}
