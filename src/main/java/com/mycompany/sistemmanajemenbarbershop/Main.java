package com.mycompany.sistemmanajemenbarbershop;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Predicate;
import model.Barber;
import model.BarberSenior;
import model.Layanan;
import model.Pelanggan;
import model.PelangganMember;
import model.Pelayanan;
import util.Format;
import util.Validator;

/**
 * Titik masuk program: antarmuka konsol Sistem Manajemen Barbershop.
 *
 * @author Aura
 */
public class Main {
    private final ArrayList<Pelanggan> daftarPelanggan = new ArrayList<>();
    private final ArrayList<Barber> daftarBarber = new ArrayList<>();
    private final ArrayList<Layanan> daftarLayanan = new ArrayList<>();
    private final ArrayList<Pelayanan> daftarPelayanan = new ArrayList<>();
    private final Scanner input = new Scanner(System.in);
    private int nomorAntreanBerikutnya = 1;
    private int nomorPelayananBerikutnya = 1;

    /** Dilempar saat pengguna mengetik "batal" di tengah pengisian data. */
    private static class InputBatalException extends RuntimeException {
        private static final long serialVersionUID = 1L;
    }

    public Main() {
        isiDataAwal();
    }

    // ==================================================================
    // DUMMY DATA AWAL (langsung tampil saat fitur read dijalankan)
    // ==================================================================
    private void isiDataAwal() {
        // --- Layanan ---
        daftarLayanan.add(new Layanan("L001", "Regular Haircut", 35000, 30));
        daftarLayanan.add(new Layanan("L002", "Premium Haircut", 50000, 45));
        daftarLayanan.add(new Layanan("L003", "Haircut + Wash", 60000, 60));
        daftarLayanan.add(new Layanan("L004", "Haircut + Shaving", 70000, 60));

        // --- Pelanggan (reguler & member) ---
        daftarPelanggan.add(new PelangganMember("P001", "Andi Pratama", "081234567801"));
        daftarPelanggan.add(new Pelanggan("P002", "Budi Santoso", "081234567802"));
        daftarPelanggan.add(new Pelanggan("P003", "Citra Lestari", "081234567803"));
        daftarPelanggan.add(new PelangganMember("P004", "Dedi Kurniawan", "081234567804"));

        // --- Barber (senior & biasa) ---
        daftarBarber.add(new BarberSenior("B001", "Rizky Ramadhan", 8));
        daftarBarber.add(new Barber("B002", "Dimas Saputra", 3));
        daftarBarber.add(new Barber("B003", "Fajar Nugroho", 1));

        // --- Pelayanan dengan status berbeda-beda ---
        // PL001: Andi (member) - Rizky (senior) - sedang DIPROSES
        Pelayanan pl1 = buatPelayanan(cariPelanggan("P001"), cariBarber("B001"), cariLayanan("L002"));
        pl1.setStatusPelayanan(Pelayanan.DIPROSES);

        // PL002: Budi - Dimas - MENUNGGU
        buatPelayanan(cariPelanggan("P002"), cariBarber("B002"), cariLayanan("L001"));

        // PL003: Citra - Dimas - SELESAI dan sudah LUNAS (QRIS)
        Barber dimas = cariBarber("B002");
        Pelayanan pl3 = buatPelayanan(cariPelanggan("P003"), dimas, cariLayanan("L003"));
        pl3.setStatusPelayanan(Pelayanan.SELESAI);
        dimas.kurangiPelanggan();
        pl3.setMetodePembayaran(Pelayanan.QRIS);
        pl3.setStatusPembayaran(Pelayanan.LUNAS);
    }

    // ==================================================================
    // MENU UTAMA
    // ==================================================================
    public void jalankanProgram() {
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
            String pilihan = input.nextLine().trim();

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
                    jalankan(this::cekStatusPelanggan);
                    break;
                case "6":
                    tampilkanBarber();
                    break;
                case "7":
                    tampilkanRingkasan();
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

    // ==================================================================
    // KELOLA PELANGGAN
    // ==================================================================
    private void menuPelanggan() {
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
            String pilihan = input.nextLine().trim();

            switch (pilihan) {
                case "1":
                    jalankan(this::tambahPelanggan);
                    break;
                case "2":
                    tampilkanPelanggan();
                    break;
                case "3":
                    jalankan(this::ubahPelanggan);
                    break;
                case "4":
                    jalankan(this::hapusPelanggan);
                    break;
                case "0":
                    menu = false;
                    break;
                default:
                    System.out.println("Pilihan tidak tersedia.");
            }
        }
    }

    private void tambahPelanggan() {
        System.out.println();
        System.out.println("TAMBAH PELANGGAN (ketik 'batal' untuk membatalkan)");

        String id = bacaIdBaru("ID Pelanggan (3-10 huruf/angka): ", kode -> cariPelanggan(kode) != null);
        String nama = bacaNama("Nama: ");
        String noHp = bacaNoHp("No HP: ");

        System.out.println("Jenis Pelanggan:");
        System.out.println("1. Reguler (tanpa diskon)");
        System.out.println("2. Member (diskon 10%)");
        int jenis = bacaPilihan("Pilih: ", 1, 2);

        try {
            Pelanggan pelanggan = (jenis == 1)
                    ? new Pelanggan(id, nama, noHp)
                    : new PelangganMember(id, nama, noHp);
            daftarPelanggan.add(pelanggan);
            System.out.println("Pelanggan berhasil ditambahkan sebagai " + pelanggan.getPeran() + ".");
        } catch (IllegalArgumentException e) {
            System.out.println("Gagal: " + e.getMessage());
        }
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
        System.out.println();
        System.out.println("UBAH PELANGGAN (ketik 'batal' untuk membatalkan)");
        Pelanggan pelanggan = cariPelanggan(bacaInput("Masukkan ID Pelanggan: "));

        if (pelanggan == null) {
            System.out.println("Pelanggan tidak ditemukan.");
            return;
        }

        String nama = bacaNama("Nama baru: ");
        String noHp = bacaNoHp("No HP baru: ");

        try {
            pelanggan.setNama(nama);
            pelanggan.setNoHp(noHp);
            System.out.println("Data pelanggan berhasil diubah.");
        } catch (IllegalArgumentException e) {
            System.out.println("Gagal: " + e.getMessage());
        }
    }

    private void hapusPelanggan() {
        System.out.println();
        System.out.println("HAPUS PELANGGAN (ketik 'batal' untuk membatalkan)");
        Pelanggan pelanggan = cariPelanggan(bacaInput("Masukkan ID Pelanggan: "));

        if (pelanggan == null) {
            System.out.println("Pelanggan tidak ditemukan.");
            return;
        }

        if (punyaPelayananAktif(pelanggan.getIdPelanggan())) {
            System.out.println("Pelanggan masih memiliki pelayanan aktif.");
            System.out.println("Data tidak dapat dihapus.");
            return;
        }

        if (!konfirmasi("Yakin menghapus " + pelanggan.getNama() + "? (y/n): ")) {
            System.out.println("Penghapusan dibatalkan.");
            return;
        }

        daftarPelanggan.remove(pelanggan);
        System.out.println("Pelanggan berhasil dihapus.");
    }

    // ==================================================================
    // KELOLA BARBER
    // ==================================================================
    private void menuBarber() {
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
            String pilihan = input.nextLine().trim();

            switch (pilihan) {
                case "1":
                    jalankan(this::tambahBarber);
                    break;
                case "2":
                    tampilkanBarber();
                    break;
                case "3":
                    jalankan(this::ubahBarber);
                    break;
                case "4":
                    jalankan(this::hapusBarber);
                    break;
                case "5":
                    jalankan(this::ubahStatusBarber);
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
        System.out.println("TAMBAH BARBER (ketik 'batal' untuk membatalkan)");

        String id = bacaIdBaru("ID Barber (3-10 huruf/angka): ", kode -> cariBarber(kode) != null);
        String nama = bacaNama("Nama Barber: ");
        int pengalaman = bacaPengalaman("Pengalaman Kerja (0-50 tahun): ");

        System.out.println("Jenis Barber:");
        System.out.println("1. Barber (maks 5 antrean, tanpa biaya tambahan)");
        System.out.println("2. Barber Senior (maks 7 antrean, biaya tambahan " + Format.rupiah(10000) + ")");
        int jenis = bacaPilihan("Pilih: ", 1, 2);

        try {
            Barber barber = (jenis == 1)
                    ? new Barber(id, nama, pengalaman)
                    : new BarberSenior(id, nama, pengalaman);
            daftarBarber.add(barber);
            System.out.println("Barber berhasil ditambahkan sebagai " + barber.getPeran() + ".");
        } catch (IllegalArgumentException e) {
            System.out.println("Gagal: " + e.getMessage());
        }
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
        System.out.println();
        System.out.println("UBAH BARBER (ketik 'batal' untuk membatalkan)");
        Barber barber = cariBarber(bacaInput("Masukkan ID Barber: "));

        if (barber == null) {
            System.out.println("Barber tidak ditemukan.");
            return;
        }

        String nama = bacaNama("Nama baru: ");
        int pengalaman = bacaPengalaman("Pengalaman baru (0-50 tahun): ");

        try {
            barber.setNamaBarber(nama);
            barber.setPengalaman(pengalaman);
            System.out.println("Data barber berhasil diubah.");
        } catch (IllegalArgumentException e) {
            System.out.println("Gagal: " + e.getMessage());
        }
    }

    private void hapusBarber() {
        System.out.println();
        System.out.println("HAPUS BARBER (ketik 'batal' untuk membatalkan)");
        Barber barber = cariBarber(bacaInput("Masukkan ID Barber: "));

        if (barber == null) {
            System.out.println("Barber tidak ditemukan.");
            return;
        }

        if (barber.getJumlahPelangganAktif() > 0) {
            System.out.println("Barber masih memiliki pelanggan aktif.");
            System.out.println("Barber tidak dapat dihapus.");
            return;
        }

        if (!konfirmasi("Yakin menghapus " + barber.getNamaBarber() + "? (y/n): ")) {
            System.out.println("Penghapusan dibatalkan.");
            return;
        }

        daftarBarber.remove(barber);
        System.out.println("Barber berhasil dihapus.");
    }

    private void ubahStatusBarber() {
        System.out.println();
        System.out.println("UBAH STATUS KEHADIRAN (ketik 'batal' untuk membatalkan)");
        Barber barber = cariBarber(bacaInput("Masukkan ID Barber: "));

        if (barber == null) {
            System.out.println("Barber tidak ditemukan.");
            return;
        }

        System.out.println("1. Aktif");
        System.out.println("2. Tidak Tersedia");
        int pilihan = bacaPilihan("Pilih status: ", 1, 2);

        if (pilihan == 1) {
            barber.setStatusKehadiran(Barber.HADIR_AKTIF);
            System.out.println("Barber sekarang aktif.");
        } else {
            if (barber.getJumlahPelangganAktif() > 0) {
                System.out.println("Barber masih memiliki pelanggan aktif.");
                System.out.println("Selesaikan atau batalkan pelayanan terlebih dahulu.");
                return;
            }
            barber.setStatusKehadiran(Barber.HADIR_TIDAK_TERSEDIA);
            System.out.println("Barber sekarang tidak tersedia.");
        }
    }

    // ==================================================================
    // LAYANAN
    // ==================================================================
    private void tampilkanLayanan() {
        System.out.println();
        System.out.println("DAFTAR LAYANAN");

        for (int i = 0; i < daftarLayanan.size(); i++) {
            Layanan layanan = daftarLayanan.get(i);
            System.out.println((i + 1) + ". " + layanan.getNamaLayanan()
                    + " | " + Format.rupiah(layanan.getHarga())
                    + " | " + layanan.getDurasiMenit() + " menit");
        }
    }

    // ==================================================================
    // PELAYANAN PELANGGAN
    // ==================================================================
    private void menuPelayanan() {
        boolean menu = true;

        while (menu) {
            System.out.println();
            System.out.println("PELAYANAN PELANGGAN");
            System.out.println("1. Daftarkan Pelayanan");
            System.out.println("2. Tampilkan Semua Pelayanan");
            System.out.println("3. Mulai Pelayanan");
            System.out.println("4. Selesaikan Pelayanan");
            System.out.println("5. Pembayaran");
            System.out.println("6. Batalkan Pelayanan");
            System.out.println("0. Kembali");
            System.out.print("Pilih: ");
            String pilihan = input.nextLine().trim();

            switch (pilihan) {
                case "1":
                    jalankan(this::daftarPelayananBaru);
                    break;
                case "2":
                    tampilkanSemuaPelayanan();
                    break;
                case "3":
                    jalankan(this::mulaiPelayanan);
                    break;
                case "4":
                    jalankan(this::selesaikanPelayanan);
                    break;
                case "5":
                    jalankan(this::pembayaran);
                    break;
                case "6":
                    jalankan(this::batalkanPelayanan);
                    break;
                case "0":
                    menu = false;
                    break;
                default:
                    System.out.println("Pilihan tidak tersedia.");
            }
        }
    }

    private void daftarPelayananBaru() {
        System.out.println();
        System.out.println("DAFTARKAN PELAYANAN (ketik 'batal' untuk membatalkan)");

        if (daftarPelanggan.isEmpty()) {
            System.out.println("Belum ada data pelanggan.");
            return;
        }

        if (daftarBarber.isEmpty()) {
            System.out.println("Belum ada data barber.");
            return;
        }

        Pelanggan pelanggan = cariPelanggan(bacaInput("Masukkan ID Pelanggan: "));

        if (pelanggan == null) {
            System.out.println("Pelanggan tidak ditemukan.");
            return;
        }

        if (punyaPelayananAktif(pelanggan.getIdPelanggan())) {
            System.out.println("Pelanggan masih memiliki pelayanan aktif.");
            return;
        }

        System.out.println();
        System.out.println("Daftar Barber:");
        for (Barber b : daftarBarber) {
            System.out.println(b.getIdBarber() + " | " + b.getNamaBarber() + " | " + b.getPeran()
                    + " | " + b.getJumlahPelangganAktif() + "/" + b.getKapasitas()
                    + " | " + b.getStatusBarber());
        }

        Barber barber = cariBarber(bacaInput("Pilih ID Barber: "));

        if (barber == null) {
            System.out.println("Barber tidak ditemukan.");
            return;
        }

        if (barber.getStatusBarber().equals(Barber.PENUH)) {
            System.out.println("Barber sudah penuh.");
            System.out.println("Silakan pilih barber lain.");
            return;
        }

        if (barber.getStatusBarber().equals(Barber.TIDAK_TERSEDIA)) {
            System.out.println("Barber sedang tidak tersedia.");
            return;
        }

        tampilkanLayanan();
        int nomorLayanan = bacaPilihan("Pilih nomor layanan: ", 1, daftarLayanan.size());
        Layanan layanan = daftarLayanan.get(nomorLayanan - 1);

        Pelayanan pelayanan = buatPelayanan(pelanggan, barber, layanan);

        // Rincian biaya (polimorfisme: nilai tergantung jenis barber & pelanggan)
        int biayaBarber = barber.getBiayaTambahan();
        int diskon = pelanggan.hitungDiskon(layanan.getHarga() + biayaBarber);

        System.out.println();
        System.out.println("Pelayanan berhasil didaftarkan.");
        System.out.println("ID Pelayanan  : " + pelayanan.getIdPelayanan());
        System.out.println("Pelanggan     : " + pelanggan.getNama() + " (" + pelanggan.getPeran() + ")");
        System.out.println("Barber        : " + barber.getNamaBarber() + " (" + barber.getPeran() + ")");
        System.out.println("Layanan       : " + layanan.getNamaLayanan());
        System.out.println("Nomor Antrean : " + pelayanan.getNomorAntrean());
        System.out.println("Harga Layanan : " + Format.rupiah(layanan.getHarga()));
        System.out.println("Biaya Barber  : " + Format.rupiah(biayaBarber));
        System.out.println("Diskon        : -" + Format.rupiah(diskon));
        System.out.println("Total         : " + Format.rupiah(pelayanan.getTotalBayar()));
        System.out.println("Status        : " + pelayanan.getStatusPelayanan());
    }

    /** Membuat pelayanan baru, menghitung total, memperbarui antrean & beban barber. */
    private Pelayanan buatPelayanan(Pelanggan pelanggan, Barber barber, Layanan layanan) {
        int subtotal = layanan.getHarga() + barber.getBiayaTambahan();
        int total = subtotal - pelanggan.hitungDiskon(subtotal);

        String idPelayanan = "PL" + String.format("%03d", nomorPelayananBerikutnya);
        Pelayanan pelayanan = new Pelayanan(idPelayanan, pelanggan.getIdPelanggan(),
                barber.getIdBarber(), layanan.getIdLayanan(), nomorAntreanBerikutnya, total);

        barber.tambahPelanggan();
        daftarPelayanan.add(pelayanan);
        nomorPelayananBerikutnya++;
        nomorAntreanBerikutnya++;
        return pelayanan;
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
        System.out.println();
        Pelayanan pelayanan = cariPelayanan(bacaInput("Masukkan ID Pelayanan: "));

        if (pelayanan == null) {
            System.out.println("Pelayanan tidak ditemukan.");
            return;
        }

        if (!pelayanan.getStatusPelayanan().equals(Pelayanan.MENUNGGU)) {
            System.out.println("Pelayanan tidak dapat dimulai.");
            return;
        }

        pelayanan.setStatusPelayanan(Pelayanan.DIPROSES);
        System.out.println("Pelayanan sedang diproses.");
    }

    private void selesaikanPelayanan() {
        System.out.println();
        Pelayanan pelayanan = cariPelayanan(bacaInput("Masukkan ID Pelayanan: "));

        if (pelayanan == null) {
            System.out.println("Pelayanan tidak ditemukan.");
            return;
        }

        if (!pelayanan.getStatusPelayanan().equals(Pelayanan.DIPROSES)) {
            System.out.println("Pelayanan belum dalam proses.");
            return;
        }

        Barber barber = cariBarber(pelayanan.getIdBarber());
        pelayanan.setStatusPelayanan(Pelayanan.SELESAI);

        if (barber != null) {
            barber.kurangiPelanggan();
        }

        System.out.println("Pelayanan selesai.");
        System.out.println("Silakan lakukan pembayaran.");
    }

    private void pembayaran() {
        System.out.println();
        Pelayanan pelayanan = cariPelayanan(bacaInput("Masukkan ID Pelayanan: "));

        if (pelayanan == null) {
            System.out.println("Pelayanan tidak ditemukan.");
            return;
        }

        if (!pelayanan.getStatusPelayanan().equals(Pelayanan.SELESAI)) {
            System.out.println("Pembayaran belum dapat dilakukan.");
            System.out.println("Pelayanan belum selesai.");
            return;
        }

        if (pelayanan.getStatusPembayaran().equals(Pelayanan.LUNAS)) {
            System.out.println("Pelayanan sudah dibayar.");
            return;
        }

        System.out.println("Total Pembayaran : " + Format.rupiah(pelayanan.getTotalBayar()));
        System.out.println("Metode Pembayaran:");
        System.out.println("1. Tunai");
        System.out.println("2. QRIS");
        int pilihan = bacaPilihan("Pilih: ", 1, 2);

        pelayanan.setMetodePembayaran(pilihan == 1 ? Pelayanan.TUNAI : Pelayanan.QRIS);
        pelayanan.setStatusPembayaran(Pelayanan.LUNAS);

        System.out.println();
        System.out.println("Pembayaran berhasil.");
        System.out.println("Total  : " + Format.rupiah(pelayanan.getTotalBayar()));
        System.out.println("Metode : " + pelayanan.getMetodePembayaran());
        System.out.println("Status : " + pelayanan.getStatusPembayaran());
    }

    private void batalkanPelayanan() {
        System.out.println();
        Pelayanan pelayanan = cariPelayanan(bacaInput("Masukkan ID Pelayanan: "));

        if (pelayanan == null) {
            System.out.println("Pelayanan tidak ditemukan.");
            return;
        }

        if (!pelayanan.getStatusPelayanan().equals(Pelayanan.MENUNGGU)) {
            System.out.println("Hanya pelayanan berstatus Menunggu yang dapat dibatalkan.");
            return;
        }

        Barber barber = cariBarber(pelayanan.getIdBarber());
        pelayanan.setStatusPelayanan(Pelayanan.DIBATALKAN);

        if (barber != null) {
            barber.kurangiPelanggan();
        }

        System.out.println("Pelayanan berhasil dibatalkan.");
    }

    private void cekStatusPelanggan() {
        System.out.println();
        Pelanggan pelanggan = cariPelanggan(bacaInput("Masukkan ID Pelanggan: "));

        if (pelanggan == null) {
            System.out.println("Pelanggan tidak ditemukan.");
            return;
        }

        boolean ada = false;
        System.out.println();
        System.out.println("STATUS PELANGGAN: " + pelanggan.getNama() + " (" + pelanggan.getPeran() + ")");

        for (Pelayanan pelayanan : daftarPelayanan) {
            if (pelayanan.getIdPelanggan().equalsIgnoreCase(pelanggan.getIdPelanggan())) {
                System.out.println("-----------------------------");
                tampilkanDetailPelayanan(pelayanan);
                ada = true;
            }
        }

        if (!ada) {
            System.out.println("Pelanggan belum memiliki pelayanan.");
        }
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
        System.out.println("Total             : " + Format.rupiah(pelayanan.getTotalBayar()));
        System.out.println("Status Pelayanan  : " + pelayanan.getStatusPelayanan());
        System.out.println("Pembayaran        : " + pelayanan.getStatusPembayaran());
        System.out.println("Metode            : " + pelayanan.getMetodePembayaran());
    }

    // ==================================================================
    // RINGKASAN
    // ==================================================================
    private void tampilkanRingkasan() {
        System.out.println();
        System.out.println("RINGKASAN BARBERSHOP");
        System.out.println("============================");

        int jumlahMember = 0;
        for (Pelanggan pelanggan : daftarPelanggan) {
            if (pelanggan instanceof PelangganMember) {
                jumlahMember++;
            }
        }

        int jumlahSenior = 0;
        for (Barber barber : daftarBarber) {
            if (barber instanceof BarberSenior) {
                jumlahSenior++;
            }
        }

        System.out.println("Total Pelanggan      : " + daftarPelanggan.size()
                + " (Member: " + jumlahMember + ", Reguler: " + (daftarPelanggan.size() - jumlahMember) + ")");
        System.out.println("Total Barber         : " + daftarBarber.size()
                + " (Senior: " + jumlahSenior + ", Biasa: " + (daftarBarber.size() - jumlahSenior) + ")");
        System.out.println("Total Layanan        : " + daftarLayanan.size());

        int tersedia = 0;
        int melayani = 0;
        int penuh = 0;
        int tidakTersedia = 0;

        for (Barber barber : daftarBarber) {
            switch (barber.getStatusBarber()) {
                case Barber.TERSEDIA:
                    tersedia++;
                    break;
                case Barber.MELAYANI:
                    melayani++;
                    break;
                case Barber.PENUH:
                    penuh++;
                    break;
                default:
                    tidakTersedia++;
            }
        }

        System.out.println();
        System.out.println("Status Barber");
        System.out.println("  Tersedia           : " + tersedia);
        System.out.println("  Melayani           : " + melayani);
        System.out.println("  Penuh              : " + penuh);
        System.out.println("  Tidak Tersedia     : " + tidakTersedia);

        int menunggu = 0;
        int diproses = 0;
        int selesai = 0;
        int dibatalkan = 0;
        int pendapatan = 0;
        int pendapatanTunai = 0;
        int pendapatanQris = 0;
        int belumDibayar = 0;

        for (Pelayanan pelayanan : daftarPelayanan) {
            String status = pelayanan.getStatusPelayanan();

            if (status.equals(Pelayanan.MENUNGGU)) {
                menunggu++;
            } else if (status.equals(Pelayanan.DIPROSES)) {
                diproses++;
            } else if (status.equals(Pelayanan.SELESAI)) {
                selesai++;
            } else if (status.equals(Pelayanan.DIBATALKAN)) {
                dibatalkan++;
            }

            if (pelayanan.getStatusPembayaran().equals(Pelayanan.LUNAS)) {
                pendapatan += pelayanan.getTotalBayar();
                if (pelayanan.getMetodePembayaran().equals(Pelayanan.TUNAI)) {
                    pendapatanTunai += pelayanan.getTotalBayar();
                } else if (pelayanan.getMetodePembayaran().equals(Pelayanan.QRIS)) {
                    pendapatanQris += pelayanan.getTotalBayar();
                }
            } else if (status.equals(Pelayanan.SELESAI)) {
                belumDibayar += pelayanan.getTotalBayar();
            }
        }

        System.out.println();
        System.out.println("Status Pelayanan (total " + daftarPelayanan.size() + ")");
        System.out.println("  Menunggu           : " + menunggu);
        System.out.println("  Diproses           : " + diproses);
        System.out.println("  Selesai            : " + selesai);
        System.out.println("  Dibatalkan         : " + dibatalkan);

        System.out.println();
        System.out.println("Pendapatan");
        System.out.println("  Total Diterima     : " + Format.rupiah(pendapatan));
        System.out.println("    - Tunai          : " + Format.rupiah(pendapatanTunai));
        System.out.println("    - QRIS           : " + Format.rupiah(pendapatanQris));
        System.out.println("  Belum Dibayar      : " + Format.rupiah(belumDibayar));
        System.out.println("============================");
    }

    // ==================================================================
    // PENCARIAN (private: hanya dipakai di dalam class ini)
    // ==================================================================
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
        for (Pelayanan pelayanan : daftarPelayanan) {
            if (pelayanan.getIdPelayanan().equalsIgnoreCase(id)) {
                return pelayanan;
            }
        }
        return null;
    }

    private boolean punyaPelayananAktif(String idPelanggan) {
        for (Pelayanan pelayanan : daftarPelayanan) {
            if (pelayanan.getIdPelanggan().equalsIgnoreCase(idPelanggan) && pelayanan.isAktif()) {
                return true;
            }
        }
        return false;
    }

    // ==================================================================
    // INPUT & VALIDASI (semua berulang sampai input benar, atau ketik "batal")
    // ==================================================================
    /** Menjalankan satu aksi menu; jika pengguna mengetik "batal", aksi dihentikan. */
    private void jalankan(Runnable aksi) {
        try {
            aksi.run();
        } catch (InputBatalException e) {
            System.out.println("Proses dibatalkan.");
        }
    }

    private String bacaInput(String label) {
        System.out.print(label);
        String teks = input.nextLine().trim();
        if (teks.equalsIgnoreCase("batal")) {
            throw new InputBatalException();
        }
        return teks;
    }

    private String bacaIdBaru(String label, Predicate<String> sudahDipakai) {
        while (true) {
            String id = bacaInput(label);
            if (!Validator.isIdValid(id)) {
                System.out.println("  ! ID harus 3-10 karakter, hanya huruf/angka, tanpa spasi.");
            } else if (sudahDipakai.test(id)) {
                System.out.println("  ! ID sudah digunakan.");
            } else {
                return id;
            }
        }
    }

    private String bacaNama(String label) {
        while (true) {
            String nama = bacaInput(label);
            if (Validator.isNamaValid(nama)) {
                return nama;
            }
            System.out.println("  ! Nama harus 2-50 karakter, diawali huruf, dan tidak boleh berisi angka.");
        }
    }

    private String bacaNoHp(String label) {
        while (true) {
            String noHp = bacaInput(label);
            if (Validator.isNoHpValid(noHp)) {
                return noHp;
            }
            System.out.println("  ! No HP tidak valid. Contoh: 081234567890 (10-13 digit).");
        }
    }

    private int bacaPengalaman(String label) {
        while (true) {
            String teks = bacaInput(label);
            if (Validator.isAngka(teks) && Validator.isPengalamanValid(Integer.parseInt(teks))) {
                return Integer.parseInt(teks);
            }
            System.out.println("  ! Pengalaman harus angka antara 0 sampai 50.");
        }
    }

    private int bacaPilihan(String label, int min, int max) {
        while (true) {
            String teks = bacaInput(label);
            if (Validator.isAngka(teks)) {
                int nilai = Integer.parseInt(teks);
                if (nilai >= min && nilai <= max) {
                    return nilai;
                }
            }
            System.out.println("  ! Masukkan angka antara " + min + " dan " + max + ".");
        }
    }

    private boolean konfirmasi(String label) {
        while (true) {
            String jawab = bacaInput(label);
            if (jawab.equalsIgnoreCase("y")) {
                return true;
            }
            if (jawab.equalsIgnoreCase("n")) {
                return false;
            }
            System.out.println("  ! Ketik y atau n.");
        }
    }

    public static void main(String[] args) {
        Main program = new Main();
        try {
            program.jalankanProgram();
        } catch (NoSuchElementException e) {
            System.out.println("\nInput berakhir. Program dihentikan.");
        }
    }
}