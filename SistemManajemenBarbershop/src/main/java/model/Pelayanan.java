/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Aura
 */
public class Pelayanan {
    private String idPelayanan;
    private String idPelanggan;
    private String idBarber;
    private String idLayanan;
    private int nomorAntrean;
    private int totalBayar;
    private String statusPelayanan;
    private String statusPembayaran;
    private String metodePembayaran;

    public Pelayanan(String idPelayanan, String idPelanggan, String idBarber, String idLayanan, int nomorAntrean, int totalBayar) {
        this.idPelayanan = idPelayanan;
        this.idPelanggan = idPelanggan;
        this.idBarber = idBarber;
        this.idLayanan = idLayanan;
        this.nomorAntrean = nomorAntrean;
        this.totalBayar = totalBayar;
        this.statusPelayanan = "Menunggu";
        this.statusPembayaran = "Belum Bayar";
        this.metodePembayaran = "-";
    }

    public String getIdPelayanan() {
        return idPelayanan;
    }

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public String getIdBarber() {
        return idBarber;
    }

    public String getIdLayanan() {
        return idLayanan;
    }

    public int getNomorAntrean() {
        return nomorAntrean;
    }

    public int getTotalBayar() {
        return totalBayar;
    }

    public String getStatusPelayanan() {
        return statusPelayanan;
    }

    public String getStatusPembayaran() {
        return statusPembayaran;
    }

    public String getMetodePembayaran() {
        return metodePembayaran;
    }

    public void setStatusPelayanan(String statusPelayanan) {
        this.statusPelayanan = statusPelayanan;
    }

    public void setStatusPembayaran(String statusPembayaran) {
        this.statusPembayaran = statusPembayaran;
    }

    public void setMetodePembayaran(String metodePembayaran) {
        this.metodePembayaran = metodePembayaran;
    }

    public void tampilkanData() {
        System.out.println("ID Pelayanan       : " + idPelayanan);
        System.out.println("ID Pelanggan       : " + idPelanggan);
        System.out.println("ID Barber          : " + idBarber);
        System.out.println("ID Layanan         : " + idLayanan);
        System.out.println("Nomor Antrean      : " + nomorAntrean);
        System.out.println("Total Bayar        : Rp. " + totalBayar);
        System.out.println("Status Pelayanan   : " + statusPelayanan);
        System.out.println("Status Pembayaran  : " + statusPembayaran);
        System.out.println("Metode Pembayaran  : " + metodePembayaran);
    }
}
