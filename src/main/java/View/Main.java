package View;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Predicate;
import controller.BarbershopController;
import model.Barber;
import model.Layanan;
import model.Pelanggan;
import model.Pelayanan;
import util.Format;
import util.Validator;

/**
 * VIEW: satu-satunya class yang melakukan input/output (Scanner, System.out).
 * Semua data dan aturan bisnis (ArrayList, perhitungan, validasi status)
 * ada di {@link BarbershopController} - class ini hanya menampilkan menu,
 * membaca input, memanggil Controller, lalu menampilkan hasilnya.
 *
 * @author Aura
 */
public class Main {
    private final BarbershopController controller = new BarbershopController();
    private final Scanner input = new Scanner(System.in);

    /** Dilempar saat pengguna mengetik "batal" di tengah pengisian data. */
    private static class InputBatalException extends RuntimeException {
        private static final long serialVersionUID = 1L;
    }

    // MENU UTAMA
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

    // KELOLA PELANGGAN
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

        String id = bacaIdBaru("ID Pelanggan (3-10 huruf/angka): ", controller::idPelangganSudahAda);
        String nama = bacaNama("Nama: ");
        String noHp = bacaNoHp("No HP: ");

        System.out.println("Jenis Pelanggan:");
        System.out.println("1. Reguler (tanpa diskon)");
        System.out.println("2. Member (diskon 10%)");
        int jenis = bacaPilihan("Pilih: ", 1, 2);

        try {
            // Memakai overloading di Controller: dengan/tanpa parameter "member"
            Pelanggan pelanggan = (jenis == 1)
                    ? controller.tambahPelanggan(id, nama, noHp)
                    : controller.tambahPelanggan(id, nama, noHp, true);
            System.out.println("Pelanggan berhasil ditambahkan sebagai " + pelanggan.getPeran() + ".");
        } catch (IllegalArgumentException e) {
            System.out.println("Gagal: " + e.getMessage());
        }
    }

    private void tampilkanPelanggan() {
        System.out.println();
        System.out.println("DAFTAR PELANGGAN");
        if (controller.getDaftarPelanggan().isEmpty()) {
            System.out.println("Data pelanggan masih kosong.");
            return;
        }

        for (Pelanggan pelanggan : controller.getDaftarPelanggan()) {
            System.out.println("============================");
            pelanggan.tampilkanData();
            System.out.println("============================");
        }
    }

    private void ubahPelanggan() {
        System.out.println();
        System.out.println("UBAH PELANGGAN (ketik 'batal' untuk membatalkan)");
        Pelanggan pelanggan = controller.cariPelanggan(bacaInput("Masukkan ID Pelanggan: "));

        if (pelanggan == null) {
            System.out.println("Pelanggan tidak ditemukan.");
            return;
        }

        String nama = bacaNama("Nama baru: ");
        String noHp = bacaNoHp("No HP baru: ");

        try {
            controller.ubahPelanggan(pelanggan, nama, noHp);
            System.out.println("Data pelanggan berhasil diubah.");
        } catch (IllegalArgumentException e) {
            System.out.println("Gagal: " + e.getMessage());
        }
    }

    private void hapusPelanggan() {
        System.out.println();
        System.out.println("HAPUS PELANGGAN (ketik 'batal' untuk membatalkan)");
        Pelanggan pelanggan = controller.cariPelanggan(bacaInput("Masukkan ID Pelanggan: "));

        if (pelanggan == null) {
            System.out.println("Pelanggan tidak ditemukan.");
            return;
        }

        if (!konfirmasi("Yakin menghapus " + pelanggan.getNama() + "? (y/n): ")) {
            System.out.println("Penghapusan dibatalkan.");
            return;
        }

        try {
            controller.hapusPelanggan(pelanggan);
            System.out.println("Pelanggan berhasil dihapus.");
        } catch (IllegalStateException e) {
            System.out.println("Gagal: " + e.getMessage());
        }
    }

    // KELOLA BARBER
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

        String id = bacaIdBaru("ID Barber (3-10 huruf/angka): ", controller::idBarberSudahAda);
        String nama = bacaNama("Nama Barber: ");
        int pengalaman = bacaPengalaman("Pengalaman Kerja (0-50 tahun): ");

        System.out.println("Jenis Barber:");
        System.out.println("1. Barber (maks 5 antrean, tanpa biaya tambahan)");
        System.out.println("2. Barber Senior (maks 7 antrean, biaya tambahan " + Format.rupiah(10000) + ")");
        int jenis = bacaPilihan("Pilih: ", 1, 2);

        try {
            // Memakai overloading di Controller: dengan/tanpa parameter "senior"
            Barber barber = (jenis == 1)
                    ? controller.tambahBarber(id, nama, pengalaman)
                    : controller.tambahBarber(id, nama, pengalaman, true);
            System.out.println("Barber berhasil ditambahkan sebagai " + barber.getPeran() + ".");
        } catch (IllegalArgumentException e) {
            System.out.println("Gagal: " + e.getMessage());
        }
    }

    private void tampilkanBarber() {
        System.out.println();
        System.out.println("DAFTAR BARBER");

        if (controller.getDaftarBarber().isEmpty()) {
            System.out.println("Data barber masih kosong.");
            return;
        }

        for (Barber barber : controller.getDaftarBarber()) {
            System.out.println("==========================");
            barber.tampilkanData();
            System.out.println("==========================");
        }
    }

    private void ubahBarber() {
        System.out.println();
        System.out.println("UBAH BARBER (ketik 'batal' untuk membatalkan)");
        Barber barber = controller.cariBarber(bacaInput("Masukkan ID Barber: "));

        if (barber == null) {
            System.out.println("Barber tidak ditemukan.");
            return;
        }

        String nama = bacaNama("Nama baru: ");
        int pengalaman = bacaPengalaman("Pengalaman baru (0-50 tahun): ");

        try {
            controller.ubahBarber(barber, nama, pengalaman);
            System.out.println("Data barber berhasil diubah.");
        } catch (IllegalArgumentException e) {
            System.out.println("Gagal: " + e.getMessage());
        }
    }

    private void hapusBarber() {
        System.out.println();
        System.out.println("HAPUS BARBER (ketik 'batal' untuk membatalkan)");
        Barber barber = controller.cariBarber(bacaInput("Masukkan ID Barber: "));

        if (barber == null) {
            System.out.println("Barber tidak ditemukan.");
            return;
        }

        if (!konfirmasi("Yakin menghapus " + barber.getNamaBarber() + "? (y/n): ")) {
            System.out.println("Penghapusan dibatalkan.");
            return;
        }

        try {
            controller.hapusBarber(barber);
            System.out.println("Barber berhasil dihapus.");
        } catch (IllegalStateException e) {
            System.out.println("Gagal: " + e.getMessage());
        }
    }

    private void ubahStatusBarber() {
        System.out.println();
        System.out.println("UBAH STATUS KEHADIRAN (ketik 'batal' untuk membatalkan)");
        Barber barber = controller.cariBarber(bacaInput("Masukkan ID Barber: "));

        if (barber == null) {
            System.out.println("Barber tidak ditemukan.");
            return;
        }

        System.out.println("1. Aktif");
        System.out.println("2. Tidak Tersedia");
        int pilihan = bacaPilihan("Pilih status: ", 1, 2);

        try {
            controller.ubahStatusBarber(barber, pilihan == 1);
            System.out.println(pilihan == 1 ? "Barber sekarang aktif." : "Barber sekarang tidak tersedia.");
        } catch (IllegalStateException e) {
            System.out.println("Gagal: " + e.getMessage());
        }
    }

    // LAYANAN
    private void tampilkanLayanan() {
        System.out.println();
        System.out.println("DAFTAR LAYANAN");

        for (int i = 0; i < controller.getDaftarLayanan().size(); i++) {
            Layanan layanan = controller.getDaftarLayanan().get(i);
            System.out.println((i + 1) + ". " + layanan.getNamaLayanan()
                    + " | " + Format.rupiah(layanan.getHarga())
                    + " | " + layanan.getDurasiMenit() + " menit");
        }
    }

    // PELAYANAN PELANGGAN
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

        if (controller.getDaftarPelanggan().isEmpty()) {
            System.out.println("Belum ada data pelanggan.");
            return;
        }
        if (controller.getDaftarBarber().isEmpty()) {
            System.out.println("Belum ada data barber.");
            return;
        }

        Pelanggan pelanggan = controller.cariPelanggan(bacaInput("Masukkan ID Pelanggan: "));
        if (pelanggan == null) {
            System.out.println("Pelanggan tidak ditemukan.");
            return;
        }

        System.out.println();
        System.out.println("Daftar Barber:");
        for (Barber b : controller.getDaftarBarber()) {
            System.out.println(b.getIdBarber() + " | " + b.getNamaBarber() + " | " + b.getPeran()
                    + " | " + b.getJumlahPelangganAktif() + "/" + b.getKapasitas()
                    + " | " + b.getStatusBarber());
        }

        Barber barber = controller.cariBarber(bacaInput("Pilih ID Barber: "));
        if (barber == null) {
            System.out.println("Barber tidak ditemukan.");
            return;
        }

        tampilkanLayanan();
        int nomorLayanan = bacaPilihan("Pilih nomor layanan: ", 1, controller.getDaftarLayanan().size());
        Layanan layanan = controller.getDaftarLayanan().get(nomorLayanan - 1);

        try {
            Pelayanan pelayanan = controller.buatPelayanan(pelanggan, barber, layanan);

            // Rincian biaya untuk ditampilkan (polimorfisme: nilai tergantung jenis objek)
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
        } catch (IllegalStateException e) {
            System.out.println("Gagal: " + e.getMessage());
        }
    }

    private void tampilkanSemuaPelayanan() {
        System.out.println();
        System.out.println("DAFTAR PELAYANAN");

        if (controller.getDaftarPelayanan().isEmpty()) {
            System.out.println("Belum ada pelayanan.");
            return;
        }

        for (Pelayanan pelayanan : controller.getDaftarPelayanan()) {
            System.out.println("=============================");
            tampilkanDetailPelayanan(pelayanan);
            System.out.println("=============================");
        }
    }

    private void mulaiPelayanan() {
        System.out.println();
        Pelayanan pelayanan = controller.cariPelayanan(bacaInput("Masukkan ID Pelayanan: "));

        if (pelayanan == null) {
            System.out.println("Pelayanan tidak ditemukan.");
            return;
        }

        try {
            controller.mulaiPelayanan(pelayanan);
            System.out.println("Pelayanan sedang diproses.");
        } catch (IllegalStateException e) {
            System.out.println("Gagal: " + e.getMessage());
        }
    }

    private void selesaikanPelayanan() {
        System.out.println();
        Pelayanan pelayanan = controller.cariPelayanan(bacaInput("Masukkan ID Pelayanan: "));

        if (pelayanan == null) {
            System.out.println("Pelayanan tidak ditemukan.");
            return;
        }

        try {
            controller.selesaikanPelayanan(pelayanan);
            System.out.println("Pelayanan selesai.");
            System.out.println("Silakan lakukan pembayaran.");
        } catch (IllegalStateException e) {
            System.out.println("Gagal: " + e.getMessage());
        }
    }

    private void pembayaran() {
        System.out.println();
        Pelayanan pelayanan = controller.cariPelayanan(bacaInput("Masukkan ID Pelayanan: "));

        if (pelayanan == null) {
            System.out.println("Pelayanan tidak ditemukan.");
            return;
        }

        if (!pelayanan.getStatusPelayanan().equals(Pelayanan.SELESAI)) {
            System.out.println("Pembayaran belum dapat dilakukan. Pelayanan belum selesai.");
            return;
        }

        System.out.println("Total Pembayaran : " + Format.rupiah(pelayanan.getTotalBayar()));
        System.out.println("Metode Pembayaran:");
        System.out.println("1. Tunai");
        System.out.println("2. QRIS");
        int pilihan = bacaPilihan("Pilih: ", 1, 2);
        String metode = (pilihan == 1) ? Pelayanan.TUNAI : Pelayanan.QRIS;

        try {
            controller.bayarPelayanan(pelayanan, metode);
            System.out.println();
            System.out.println("Pembayaran berhasil.");
            System.out.println("Total  : " + Format.rupiah(pelayanan.getTotalBayar()));
            System.out.println("Metode : " + pelayanan.getMetodePembayaran());
            System.out.println("Status : " + pelayanan.getStatusPembayaran());
        } catch (IllegalStateException e) {
            System.out.println("Gagal: " + e.getMessage());
        }
    }

    private void batalkanPelayanan() {
        System.out.println();
        Pelayanan pelayanan = controller.cariPelayanan(bacaInput("Masukkan ID Pelayanan: "));

        if (pelayanan == null) {
            System.out.println("Pelayanan tidak ditemukan.");
            return;
        }

        try {
            controller.batalkanPelayanan(pelayanan);
            System.out.println("Pelayanan berhasil dibatalkan.");
        } catch (IllegalStateException e) {
            System.out.println("Gagal: " + e.getMessage());
        }
    }

    private void cekStatusPelanggan() {
        System.out.println();
        Pelanggan pelanggan = controller.cariPelanggan(bacaInput("Masukkan ID Pelanggan: "));

        if (pelanggan == null) {
            System.out.println("Pelanggan tidak ditemukan.");
            return;
        }

        boolean ada = false;
        System.out.println();
        System.out.println("STATUS PELANGGAN: " + pelanggan.getNama() + " (" + pelanggan.getPeran() + ")");

        for (Pelayanan pelayanan : controller.getDaftarPelayanan()) {
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
        Pelanggan pelanggan = controller.cariPelanggan(pelayanan.getIdPelanggan());
        Barber barber = controller.cariBarber(pelayanan.getIdBarber());
        Layanan layanan = controller.cariLayanan(pelayanan.getIdLayanan());

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

    // RINGKASAN
    private void tampilkanRingkasan() {
        System.out.println();
        System.out.println("RINGKASAN BARBERSHOP");
        System.out.println("============================");

        int totalPelanggan = controller.getDaftarPelanggan().size();
        int totalBarber = controller.getDaftarBarber().size();
        int jumlahMember = controller.getJumlahPelangganMember();
        int jumlahSenior = controller.getJumlahBarberSenior();

        System.out.println("Total Pelanggan      : " + totalPelanggan
                + " (Member: " + jumlahMember + ", Reguler: " + (totalPelanggan - jumlahMember) + ")");
        System.out.println("Total Barber         : " + totalBarber
                + " (Senior: " + jumlahSenior + ", Biasa: " + (totalBarber - jumlahSenior) + ")");
        System.out.println("Total Layanan        : " + controller.getDaftarLayanan().size());

        System.out.println();
        System.out.println("Status Barber");
        System.out.println("  Tersedia           : " + controller.getJumlahBarberDenganStatus(Barber.TERSEDIA));
        System.out.println("  Melayani           : " + controller.getJumlahBarberDenganStatus(Barber.MELAYANI));
        System.out.println("  Penuh              : " + controller.getJumlahBarberDenganStatus(Barber.PENUH));
        System.out.println("  Tidak Tersedia     : " + controller.getJumlahBarberDenganStatus(Barber.TIDAK_TERSEDIA));

        System.out.println();
        System.out.println("Status Pelayanan (total " + controller.getDaftarPelayanan().size() + ")");
        System.out.println("  Menunggu           : " + controller.getJumlahPelayananDenganStatus(Pelayanan.MENUNGGU));
        System.out.println("  Diproses           : " + controller.getJumlahPelayananDenganStatus(Pelayanan.DIPROSES));
        System.out.println("  Selesai            : " + controller.getJumlahPelayananDenganStatus(Pelayanan.SELESAI));
        System.out.println("  Dibatalkan         : " + controller.getJumlahPelayananDenganStatus(Pelayanan.DIBATALKAN));

        System.out.println();
        System.out.println("Pendapatan");
        System.out.println("  Total Diterima     : " + Format.rupiah(controller.getTotalPendapatan()));
        System.out.println("    - Tunai          : " + Format.rupiah(controller.getPendapatanBerdasarkanMetode(Pelayanan.TUNAI)));
        System.out.println("    - QRIS           : " + Format.rupiah(controller.getPendapatanBerdasarkanMetode(Pelayanan.QRIS)));
        System.out.println("  Belum Dibayar      : " + Format.rupiah(controller.getTotalBelumDibayar()));
        System.out.println("============================");
    }

    // INPUT & VALIDASI (semua berulang sampai input benar, atau ketik "batal")
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