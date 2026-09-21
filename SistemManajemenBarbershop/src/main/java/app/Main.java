/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import java.util.ArrayList;
import java.util.Scanner;
import model.Barber;
import model.Layanan;
import model.Pelanggan;
import model.Pelayanan;

/**
 *
 * @author Aura
 */
public class Main {
    private ArrayList<Pelanggan> daftarPelanggan;
    private ArrayList<Barber> daftarBarber;
    private ArrayList<Layanan> daftarLayanan;
    private ArrayList<Pelayanan> daftarPelayanan;
    private Scanner input;
    private int nomorAntreanBerikutnya;
    private int nomorPelayananBerikutnya;

    public Main() {
        daftarPelanggan = new ArrayList<>();
        daftarBarber = new ArrayList<>();
        daftarLayanan = new ArrayList<>();
        daftarPelayanan = new ArrayList<>();
        input = new Scanner(System.in);
        nomorAntreanBerikutnya = 1;
        nomorPelayananBerikutnya = 1;
        isiLayananAwal();
    }

    private void isiLayananAwal() {
        daftarLayanan.add(new Layanan("L001", "Regular Haircut", 35000, 30));
        daftarLayanan.add(new Layanan("L002", "Premium Haircut", 50000, 45));
        daftarLayanan.add(new Layanan("L003", "Haircut + Wash", 60000, 60));
        daftarLayanan.add(new Layanan("L004", "Haircut + Shaving", 70000, 60));
    }

    public void jalankanProgram() {
        String pilihan;
        boolean menu = true;

        while (menu) {
            System.out.println();
            System.out.println("SISTEM MANAJEMEN BARBERSHOP");
            System.out.println("1. Kelola Pelanggan");
            System.out.println("2. Kelola Barber");
            System.out.println("3. Lihat Daftar Layanan");
            System.out.println("4. Pelayanan Pelanggan");
            System.out.println("5. Cek Status Pelanggan");
            System.out.println("6. Lihat Status Barber");
            System.out.println("7. Ringkasan Barbershop");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");
            pilihan = input.nextLine();

            switch (pilihan) {
                case "1":
                    menuPelanggan();
                    break;
                case "2":
                    menuBarber();
                    break;
                case "3":
                    tampilkanLayanan();
                    break;
                case "4":
                    menuPelayanan();
                    break;
                case "5":
                    cekStatusPelanggan();
                    break;
                case "6":
                    tampilkanBarber();
                    break;
                case "0":
                    menu = false;
                    System.out.println("Program selesai.");
                    break;
                default:
                    System.out.println("Pilihan menu tidak tersedia.");
            }
        } 
    }

    private void menuPelanggan() {
        String pilihan;
        boolean menu = true;
        
        while (menu) {
            System.out.println();
            System.out.println("KELOLA PELANGGAN");
            System.out.println("1. Tambah Pelanggan");
            System.out.println("2. Tampilkan Pelanggan");
            System.out.println("3. Ubah Pelanggan");
            System.out.println("4. Hapus Pelanggan");
            System.out.println("0. Kembali");
            System.out.print("Pilih: ");
            pilihan = input.nextLine();
            switch (pilihan) {
                case "1":
                    tambahPelanggan();
                    break;
                case "2":
                    tampilkanPelanggan();
                    break;
                case "3":
                    ubahPelanggan();
                    break;
                case "4":
                    hapusPelanggan();
                    break;
                case "0":
                    menu = false;
                    break;
                default:
                    System.out.println(
                            "Pilihan tidak tersedia."
                    );
            }
        } 
    }

    private void tambahPelanggan() {
        System.out.println();
        System.out.println("TAMBAH PELANGGAN");
        System.out.print("ID Pelanggan: ");
        String idPelanggan = input.nextLine();
        if (idPelanggan.trim().isEmpty()) {
            System.out.println("ID pelanggan tidak boleh kosong.");
            return;
        }

        if (cariPelanggan(idPelanggan) != null) {
            System.out.println("ID pelanggan sudah digunakan.");
            return;
        }

        System.out.print("Nama: ");
        String nama = input.nextLine();

        if (nama.trim().isEmpty()) {
            System.out.println("Nama tidak boleh kosong.");
            return;
        }

        System.out.print("No HP: ");
        String noHp = input.nextLine();

        if (noHp.trim().isEmpty()) {
            System.out.println("Nomor HP tidak boleh kosong.");
            return;
        }

        Pelanggan pelanggan = new Pelanggan(idPelanggan, nama, noHp);
        daftarPelanggan.add(pelanggan);
        System.out.println("Pelanggan berhasil ditambahkan.");

    }

    private void tampilkanPelanggan() {
        System.out.println();
        System.out.println("DAFTAR PELANGGAN");
        if (daftarPelanggan.isEmpty()) {
            System.out.println("Data pelanggan masih kosong.");
            return;
        }

        for (Pelanggan pelanggan : daftarPelanggan) {
            System.out.println("============================");
            pelanggan.tampilkanData();
            System.out.println("============================");
        }
    }

    private void ubahPelanggan() {
        System.out.print("Masukkan ID Pelanggan: ");
        String idPelanggan = input.nextLine();
        Pelanggan pelanggan = cariPelanggan(idPelanggan);

        if (pelanggan == null) {
            System.out.println("Pelanggan tidak ditemukan.");
            return;
        }

        System.out.print("Nama baru: ");
        String nama = input.nextLine();
        System.out.print("No HP baru: ");
        String noHp = input.nextLine();
        pelanggan.setNama(nama);
        pelanggan.setNoHp(noHp);
        System.out.println("Data pelanggan berhasil diubah."
        );
    }

    private void hapusPelanggan() {
        System.out.print("Masukkan ID Pelanggan: ");
        String idPelanggan = input.nextLine();
        Pelanggan pelanggan = cariPelanggan(idPelanggan);

        if (pelanggan == null) {
            System.out.println("Pelanggan tidak ditemukan.");
            return;
        }

        for (Pelayanan pelayanan : daftarPelayanan) {
            if (pelayanan.getIdPelanggan().equalsIgnoreCase(idPelanggan) && (pelayanan.getStatusPelayanan().equalsIgnoreCase("Menunggu") || pelayanan.getStatusPelayanan().equalsIgnoreCase("Diproses"))) {
                System.out.println("Pelanggan masih memiliki pelayanan aktif.");
                System.out.println("Data tidak dapat dihapus.");
                return;
            }
        }

        daftarPelanggan.remove(pelanggan);
        System.out.println("Pelanggan berhasil dihapus.");
    }
    
    private void menuBarber() {
        String pilihan;
        boolean menu = true;
        
        while (menu) {
            System.out.println();
            System.out.println("KELOLA BARBER");
            System.out.println("1. Tambah Barber");
            System.out.println("2. Tampilkan Barber");
            System.out.println("3. Ubah Barber");
            System.out.println("4. Hapus Barber");
            System.out.println("5. Ubah Status Kehadiran");
            System.out.println("0. Kembali");
            System.out.print("Pilih: ");
            pilihan = input.nextLine();

            switch (pilihan) {
                case "1":
                    tambahBarber();
                    break;
                case "2":
                    tampilkanBarber();
                    break;
                case "3":
                    ubahBarber();
                    break;
                case "4":
                    hapusBarber();
                    break;
                case "5":
                    ubahStatusBarber();
                    break;
                case "0":
                    menu = false;
                    break;
                default:
                    System.out.println("Pilihan tidak tersedia.");
            }
        } 
    }

    private void tambahBarber() {
        System.out.println();
        System.out.println("TAMBAH BARBER");

        System.out.print("ID Barber: ");
        String idBarber = input.nextLine();

        if (idBarber.trim().isEmpty()) {
            System.out.println("ID barber tidak boleh kosong.");
            return;
        }

        if (cariBarber(idBarber) != null) {
            System.out.println("ID barber sudah digunakan.");
            return;
        }

        System.out.print("Nama Barber: ");
        String nama = input.nextLine();

        if (nama.trim().isEmpty()) {
            System.out.println("Nama barber tidak boleh kosong.");
            return;
        }

        System.out.print("Pengalaman Kerja (tahun): ");
        String inputPengalaman = input.nextLine();

        if (!inputPengalaman.matches("\\d+")) {
            System.out.println("Pengalaman harus berupa angka.");
            return;
        }

        int pengalaman = Integer.parseInt(inputPengalaman);

        Barber barber = new Barber(idBarber, nama, pengalaman);
        daftarBarber.add(barber);

        System.out.println("Barber berhasil ditambahkan.");
    }

    private void tampilkanBarber() {
        System.out.println();
        System.out.println("DAFTAR BARBER");

        if (daftarBarber.isEmpty()) {
            System.out.println("Data barber masih kosong.");
            return;
        }

        for (Barber barber : daftarBarber) {
            System.out.println("==========================");
            barber.tampilkanData();
            System.out.println("==========================");
        }
    }

    private void ubahBarber() {
        System.out.print("Masukkan ID Barber: ");
        String idBarber = input.nextLine();

        Barber barber = cariBarber(idBarber);

        if (barber == null) {
            System.out.println("Barber tidak ditemukan.");
            return;
        }

        System.out.print("Nama baru: ");
        String nama = input.nextLine();

        if (nama.trim().isEmpty()) {
            System.out.println("Nama tidak boleh kosong.");
            return;
        }

        System.out.print("Pengalaman baru (tahun): ");
        String inputPengalaman = input.nextLine();

        if (!inputPengalaman.matches("\\d+")) {
            System.out.println("Pengalaman harus berupa angka.");
            return;
        }

        int pengalaman = Integer.parseInt(inputPengalaman);

        barber.setNamaBarber(nama);
        barber.setPengalaman(pengalaman);

        System.out.println("Data barber berhasil diubah.");
    }

    private void hapusBarber() {
        System.out.print("Masukkan ID Barber: ");
        String idBarber = input.nextLine();
        Barber barber = cariBarber(idBarber);
        if (barber == null) {
            System.out.println("Barber tidak ditemukan.");
            return;
        }

        if (barber.getJumlahPelangganAktif() > 0) {
            System.out.println("Barber masih memiliki pelanggan aktif.");
            System.out.println("Barber tidak dapat dihapus.");
            return;
        }

        daftarBarber.remove(barber);
        System.out.println("Barber berhasil dihapus.");
    }

    private void ubahStatusBarber() {
        System.out.print("Masukkan ID Barber: ");
        String idBarber = input.nextLine();
        Barber barber = cariBarber(idBarber);
        
        if (barber == null) {
            System.out.println("Barber tidak ditemukan.");
            return;
        }

        System.out.println();
        System.out.println("1. Aktif");
        System.out.println("2. Tidak Tersedia");
        System.out.print("Pilih status: ");
        String pilihan = input.nextLine();

        switch (pilihan) {
            case "1":
                barber.setStatusKehadiran("Aktif");
                System.out.println("Barber sekarang aktif.");
                break;

            case "2":
                if (barber.getJumlahPelangganAktif() > 0) {
                    System.out.println("Barber masih memiliki pelanggan aktif.");
                    System.out.println("Selesaikan atau batalkan pelayanan terlebih dahulu.");
                    return;
                }
                
                barber.setStatusKehadiran("Tidak Tersedia");
                System.out.println("Barber sekarang tidak tersedia.");
                break;

            default:
                System.out.println("Pilihan tidak tersedia.");
        }
    }

    private void tampilkanLayanan() {
        System.out.println();
        System.out.println("DAFTAR LAYANAN");

        for (int i = 0; i < daftarLayanan.size();i++) {
            Layanan layanan = daftarLayanan.get(i);
            System.out.println((i + 1) + ". " + layanan.getNamaLayanan() + " | Rp. " + layanan.getHarga() + " | " + layanan.getDurasiMenit() + " menit");
        }
    }

    private void menuPelayanan() {
        String pilihan;
        boolean menu = true;

        while (menu) {
            System.out.println();
            System.out.println("PELAYANAN PELANGGAN");
            System.out.println("1. Daftarkan Pelayanan");
            System.out.println("2. Tampilkan Semua Pelayanan");
            System.out.println("3. Mulai Pelayanan");
            System.out.println("4. Selesaikan Pelayanan"            );
            System.out.println("5. Pembayaran");
            System.out.println("6. Batalkan Pelayanan");
            System.out.println("0. Kembali");
            System.out.print("Pilih: ");
            pilihan = input.nextLine();

            switch (pilihan) {
                case "1":
                    daftarPelayananBaru();
                    break;
                case "2":
                    tampilkanSemuaPelayanan();
                    break;
                case "3":
                    mulaiPelayanan();
                    break;
                case "4":
                    selesaikanPelayanan();
                    break;
                case "5":
                    pembayaran();
                    break;
                case "6":
                    batalkanPelayanan();
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Pilihan tidak tersedia.");
            }
        }
    }

    private void daftarPelayananBaru() {
        System.out.println();
        System.out.println("DAFTARKAN PELAYANAN");

        if (daftarPelanggan.isEmpty()) {
            System.out.println("Belum ada data pelanggan.");
            return;
        }

        if (daftarBarber.isEmpty()) {
            System.out.println("Belum ada data barber.");
            return;
        }

        System.out.print("Masukkan ID Pelanggan: ");
        String idPelanggan = input.nextLine();
        Pelanggan pelanggan = cariPelanggan(idPelanggan);

        if (pelanggan == null) {
            System.out.println("Pelanggan tidak ditemukan.");
            return;
        }

        for (Pelayanan pelayanan : daftarPelayanan) {
            if (pelayanan.getIdPelanggan().equalsIgnoreCase(idPelanggan) && (pelayanan.getStatusPelayanan().equalsIgnoreCase("Menunggu") || pelayanan.getStatusPelayanan().equalsIgnoreCase("Diproses"))) {
                System.out.println("Pelanggan masih memiliki pelayanan aktif.");
                return;
            }
        }

        System.out.println();
        System.out.println("Daftar Barber:");

        for (Barber barber : daftarBarber) {
            System.out.println(barber.getIdBarber() + " | " + barber.getNamaBarber() + " | " + barber.getJumlahPelangganAktif() + "/5" + " | " + barber.getStatusBarber());
        }

        System.out.print("Pilih ID Barber: ");
        String idBarber = input.nextLine();
        Barber barber = cariBarber(idBarber);

        if (barber == null) {
            System.out.println("Barber tidak ditemukan.");
            return;
        }

        if (barber.getStatusBarber().equalsIgnoreCase("Penuh")) {
            System.out.println("Barber sudah penuh.");
            System.out.println("Silakan pilih barber lain.");
            return;
        }

        if (barber.getStatusBarber().equalsIgnoreCase("Tidak Tersedia")) {
            System.out.println("Barber sedang tidak tersedia.");
            return;
        }

        tampilkanLayanan();
        System.out.print("Pilih nomor layanan: ");
        String inputPilihan = input.nextLine();

        if (!inputPilihan.matches("\\d+")) {
            System.out.println("Pilihan layanan harus berupa angka.");
            return;
        }

        int pilihanLayanan = Integer.parseInt(inputPilihan);

        if (pilihanLayanan < 1 || pilihanLayanan > daftarLayanan.size()) {
            System.out.println("Pilihan layanan tidak tersedia.");
            return;
        }

        Layanan layanan = daftarLayanan.get(pilihanLayanan - 1);
        String idPelayanan = "PL" + String.format("%03d", nomorPelayananBerikutnya);
        int nomorAntrean = nomorAntreanBerikutnya;
        Pelayanan pelayanan = new Pelayanan(idPelayanan, pelanggan.getIdPelanggan(), barber.getIdBarber(), layanan.getIdLayanan(), nomorAntrean, layanan.getHarga());
        daftarPelayanan.add(pelayanan);
        barber.tambahPelanggan();
        nomorPelayananBerikutnya++;
        nomorAntreanBerikutnya++;
        System.out.println();
        System.out.println("Pelayanan berhasil didaftarkan.");
        System.out.println("ID Pelayanan  : " + idPelayanan);
        System.out.println("Pelanggan     : " + pelanggan.getNama());
        System.out.println("Barber        : " + barber.getNamaBarber());
        System.out.println("Layanan       : " + layanan.getNamaLayanan());
        System.out.println("Nomor Antrean : " + nomorAntrean);
        System.out.println("Total         : Rp" + layanan.getHarga());
        System.out.println("Status        : Menunggu");
    }

    private void tampilkanSemuaPelayanan() {
        System.out.println();
        System.out.println("DAFTAR PELAYANAN");

        if (daftarPelayanan.isEmpty()) {
            System.out.println("Belum ada pelayanan.");
            return;
        }

        for (Pelayanan pelayanan : daftarPelayanan) {
            System.out.println("=============================");
            tampilkanDetailPelayanan(pelayanan);
            System.out.println("=============================");
        }

    }

    private void mulaiPelayanan() {
        System.out.print("Masukkan ID Pelayanan: ");
        String id = input.nextLine();
        Pelayanan pelayanan = cariPelayanan(id);

        if (pelayanan == null) {
            System.out.println("Pelayanan tidak ditemukan.");
            return;
        }

        if (!pelayanan.getStatusPelayanan().equalsIgnoreCase("Menunggu")) {
            System.out.println("Pelayanan tidak dapat dimulai.");
            return;
        }

        pelayanan.setStatusPelayanan("Diproses");
        System.out.println("Pelayanan sedang diproses.");

    }

    private void selesaikanPelayanan() {
        System.out.print("Masukkan ID Pelayanan: ");
        String id = input.nextLine();
        Pelayanan pelayanan = cariPelayanan(id);

        if (pelayanan == null) {
            System.out.println("Pelayanan tidak ditemukan.");
            return;
        }

        if (!pelayanan.getStatusPelayanan().equalsIgnoreCase("Diproses")) {
            System.out.println("Pelayanan belum dalam proses.");
            return;
        }

        Barber barber = cariBarber(pelayanan.getIdBarber());
        pelayanan.setStatusPelayanan("Selesai");

        if (barber != null) {
            barber.kurangiPelanggan();
        }

        System.out.println("Pelayanan selesai.");
        System.out.println("Silakan lakukan pembayaran.");
    }

    private void pembayaran() {
        System.out.print("Masukkan ID Pelayanan: ");
        String id = input.nextLine();
        Pelayanan pelayanan = cariPelayanan(id);

        if (pelayanan == null) {
            System.out.println("Pelayanan tidak ditemukan.");
            return;
        }

        if (!pelayanan.getStatusPelayanan().equalsIgnoreCase("Selesai")) {
            System.out.println("Pembayaran belum dapat dilakukan.");
            System.out.println("Pelayanan belum selesai.");
            return;
        }

        if (pelayanan.getStatusPembayaran().equalsIgnoreCase("Lunas")) {
            System.out.println("Pelayanan sudah dibayar.");
            return;
        }

        System.out.println("Total Pembayaran : Rp" + pelayanan.getTotalBayar());
        System.out.println("Metode Pembayaran:");
        System.out.println("1. Tunai");
        System.out.println("2. QRIS");
        System.out.print("Pilih: ");
        String pilihan = input.nextLine();

        switch (pilihan) {
            case "1":
                pelayanan.setMetodePembayaran("Tunai");
                break;

            case "2":
                pelayanan.setMetodePembayaran("QRIS");
                break;

            default:
                System.out.println("Metode pembayaran tidak tersedia.");
                return;
        }

        pelayanan.setStatusPembayaran("Lunas");
        System.out.println();
        System.out.println("Pembayaran berhasil.");
        System.out.println("Total  : Rp" + pelayanan.getTotalBayar());
        System.out.println("Metode : " + pelayanan.getMetodePembayaran());
        System.out.println("Status : Lunas");
    }

    private void batalkanPelayanan() {
        System.out.print("Masukkan ID Pelayanan: ");
        String id = input.nextLine();
        Pelayanan pelayanan = cariPelayanan(id);

        if (pelayanan == null) {
            System.out.println("Pelayanan tidak ditemukan.");
            return;
        }

        if (!pelayanan.getStatusPelayanan().equalsIgnoreCase("Menunggu")) {
            System.out.println("Hanya pelayanan berstatus Menunggu yang dapat dibatalkan.");
            return;
        }

        Barber barber = cariBarber(pelayanan.getIdBarber());
        pelayanan.setStatusPelayanan("Dibatalkan");

        if (barber != null) {
            barber.kurangiPelanggan();
        }

        System.out.println("Pelayanan berhasil dibatalkan.");
    }

    private void cekStatusPelanggan() {
        System.out.print("Masukkan ID Pelanggan: ");
        String id = input.nextLine();
        Pelanggan pelanggan = cariPelanggan(id);

        if (pelanggan == null) {
            System.out.println("Pelanggan tidak ditemukan.");
            return;
        }

        Pelayanan ditemukan = null;

        for (Pelayanan pelayanan : daftarPelayanan) {
            if (pelayanan.getIdPelanggan().equalsIgnoreCase(id)) {
                ditemukan = pelayanan;
            }
        }

        if (ditemukan == null) {
            System.out.println("Pelanggan belum memiliki pelayanan.");
            return;
        }

        System.out.println();
        System.out.println("STATUS PELANGGAN");
        tampilkanDetailPelayanan(ditemukan);
    }

    private void tampilkanDetailPelayanan(Pelayanan pelayanan) {
        Pelanggan pelanggan = cariPelanggan(pelayanan.getIdPelanggan());
        Barber barber = cariBarber(pelayanan.getIdBarber());
        Layanan layanan = cariLayanan(pelayanan.getIdLayanan());
        System.out.println("ID Pelayanan      : " + pelayanan.getIdPelayanan());
        if (pelanggan != null) {
        System.out.println("Pelanggan         : " + pelanggan.getNama());
        }
        if (barber != null) {
        System.out.println("Barber            : " + barber.getNamaBarber());
        }
        if (layanan != null) {
        System.out.println("Layanan           : " + layanan.getNamaLayanan());
        }
        System.out.println("Nomor Antrean     : " + pelayanan.getNomorAntrean());
        System.out.println("Total             : Rp" + pelayanan.getTotalBayar());
        System.out.println("Status Pelayanan  : " + pelayanan.getStatusPelayanan());
        System.out.println("Pembayaran        : " + pelayanan.getStatusPembayaran());
        System.out.println("Metode            : "+ pelayanan.getMetodePembayaran());
    }

    private Pelanggan cariPelanggan(String id) {
        for (Pelanggan pelanggan : daftarPelanggan) {
            if (pelanggan.getIdPelanggan().equalsIgnoreCase(id)) {
                return pelanggan;
            }
        }
        return null;
    }

    private Barber cariBarber(String id) {
        for (Barber barber : daftarBarber) {
            if (barber.getIdBarber().equalsIgnoreCase(id)) {
                return barber;
            }
        }
        return null;
    }

    private Layanan cariLayanan(String id) {
        for (Layanan layanan : daftarLayanan) {
            if (layanan.getIdLayanan().equalsIgnoreCase(id)) {
                return layanan;
            }
        }
        return null;
    }

    private Pelayanan cariPelayanan(String id) {
        for (Pelayanan pelayanan :
                daftarPelayanan) {

            if (pelayanan.getIdPelayanan().equalsIgnoreCase(id)) {
                return pelayanan;
            }
        }
        return null;
    }
    
    public static void main(String[] args) {
        Main program = new Main();
        program.jalankanProgram();
    }
}
