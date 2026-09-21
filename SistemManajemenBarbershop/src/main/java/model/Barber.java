/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Aura
 */
public class Barber {
    private String idBarber;
    private String namaBarber;
    private int pengalaman;
    private int jumlahPelangganAktif;
    private String statusKehadiran;

    public Barber(String idBarber, String namaBarber, int pengalaman) {
        this.idBarber = idBarber;
        this.namaBarber = namaBarber;
        this.pengalaman = pengalaman;
        this.jumlahPelangganAktif = 0;
        this.statusKehadiran = "Aktif";
    }

    public String getIdBarber() {
        return idBarber;
    }

    public String getNamaBarber() {
        return namaBarber;
    }

    public int getPengalaman() {
        return pengalaman;
    }

    public int getJumlahPelangganAktif() {
        return jumlahPelangganAktif;
    }

    public String getStatusKehadiran() {
        return statusKehadiran;
    }

    public void setNamaBarber(String namaBarber) {
        this.namaBarber = namaBarber;
    }

    public void setPengalaman(int pengalaman) {
        this.pengalaman = pengalaman;
    }

    public void setStatusKehadiran(String statusKehadiran) {
        this.statusKehadiran = statusKehadiran;
    }

    public void tambahPelanggan() {
        jumlahPelangganAktif++;
    }

    public void kurangiPelanggan() {
        if (jumlahPelangganAktif > 0) {
            jumlahPelangganAktif--;
        }
    }

    public String getStatusBarber() {
        if (statusKehadiran.equalsIgnoreCase("Tidak Tersedia")) {
            return "Tidak Tersedia";
        }

        if (jumlahPelangganAktif == 0) {
            return "Tersedia";
        }

        if (jumlahPelangganAktif < 5) {
            return "Melayani";
        }
        return "Penuh";
    }

    public void tampilkanData() {
        System.out.println("ID Barber           : " + idBarber);
        System.out.println("Nama Barber         : " + namaBarber);
        System.out.println("Pengalaman          : " + pengalaman + " tahun");
        System.out.println("Pelanggan Dilayani  : " + jumlahPelangganAktif);
        System.out.println("Kehadiran           : " + statusKehadiran);
        System.out.println("Status Barber       : " + getStatusBarber());
    }
}
