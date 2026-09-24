package controller;

import java.util.ArrayList;
import model.Barber;
import model.BarberSenior;
import model.Layanan;
import model.Pelanggan;
import model.PelangganMember;
import model.Pelayanan;

/**
 * CONTROLLER: menyimpan seluruh data (ArrayList) dan seluruh aturan bisnis
 * Sistem Manajemen Barbershop (validasi lanjutan, perhitungan, pencarian,
 * dan perubahan status). Class ini tidak melakukan input/output apa pun
 * (tidak ada Scanner atau System.out) - itu tugas View (Main.java).
 *
 * @author Aura
 */
public final class BarbershopController {

    private final ArrayList<Pelanggan> daftarPelanggan = new ArrayList<>();
    private final ArrayList<Barber> daftarBarber = new ArrayList<>();
    private final ArrayList<Layanan> daftarLayanan = new ArrayList<>();
    private final ArrayList<Pelayanan> daftarPelayanan = new ArrayList<>();

    private int nomorAntreanBerikutnya = 1;
    private int nomorPelayananBerikutnya = 1;

    public BarbershopController() {
        isiDataAwal();
    }

    // DUMMY DATA AWAL (langsung tersedia saat controller dibuat)
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
        Pelayanan pl1 = buatPelayanan(cariPelanggan("P001"), cariBarber("B001"), cariLayanan("L002"));
        pl1.setStatusPelayanan(Pelayanan.DIPROSES);

        buatPelayanan(cariPelanggan("P002"), cariBarber("B002"), cariLayanan("L001"));

        Barber dimas = cariBarber("B002");
        Pelayanan pl3 = buatPelayanan(cariPelanggan("P003"), dimas, cariLayanan("L003"));
        pl3.setStatusPelayanan(Pelayanan.SELESAI);
        dimas.kurangiPelanggan();
        pl3.setMetodePembayaran(Pelayanan.QRIS);
        pl3.setStatusPembayaran(Pelayanan.LUNAS);
    }

    // GETTER DAFTAR (dipakai View untuk menampilkan data)
    public ArrayList<Pelanggan> getDaftarPelanggan() {
        return daftarPelanggan;
    }

    public ArrayList<Barber> getDaftarBarber() {
        return daftarBarber;
    }

    public ArrayList<Layanan> getDaftarLayanan() {
        return daftarLayanan;
    }

    public ArrayList<Pelayanan> getDaftarPelayanan() {
        return daftarPelayanan;
    }

    // PENCARIAN
    public Pelanggan cariPelanggan(String id) {
        for (Pelanggan pelanggan : daftarPelanggan) {
            if (pelanggan.getIdPelanggan().equalsIgnoreCase(id)) {
                return pelanggan;
            }
        }
        return null;
    }

    public Barber cariBarber(String id) {
        for (Barber barber : daftarBarber) {
            if (barber.getIdBarber().equalsIgnoreCase(id)) {
                return barber;
            }
        }
        return null;
    }

    public Layanan cariLayanan(String id) {
        for (Layanan layanan : daftarLayanan) {
            if (layanan.getIdLayanan().equalsIgnoreCase(id)) {
                return layanan;
            }
        }
        return null;
    }

    public Pelayanan cariPelayanan(String id) {
        for (Pelayanan pelayanan : daftarPelayanan) {
            if (pelayanan.getIdPelayanan().equalsIgnoreCase(id)) {
                return pelayanan;
            }
        }
        return null;
    }

    public boolean idPelangganSudahAda(String id) {
        return cariPelanggan(id) != null;
    }

    public boolean idBarberSudahAda(String id) {
        return cariBarber(id) != null;
    }

    public boolean punyaPelayananAktif(String idPelanggan) {
        for (Pelayanan pelayanan : daftarPelayanan) {
            if (pelayanan.getIdPelanggan().equalsIgnoreCase(idPelanggan) && pelayanan.isAktif()) {
                return true;
            }
        }
        return false;
    }

    // KELOLA PELANGGAN
    /**
     * OVERLOADING 1/2: menambah pelanggan reguler (tanpa perlu menyebut jenis).
     */
    public Pelanggan tambahPelanggan(String id, String nama, String noHp) {
        return tambahPelanggan(id, nama, noHp, false);
    }

    /**
     * OVERLOADING 2/2: menambah pelanggan sekaligus menentukan jenisnya
     * (reguler atau member) lewat parameter tambahan.
     */
    public Pelanggan tambahPelanggan(String id, String nama, String noHp, boolean member) {
        if (idPelangganSudahAda(id)) {
            throw new IllegalArgumentException("ID sudah digunakan.");
        }
        Pelanggan pelanggan = member
                ? new PelangganMember(id, nama, noHp)
                : new Pelanggan(id, nama, noHp);
        daftarPelanggan.add(pelanggan);
        return pelanggan;
    }

    public void ubahPelanggan(Pelanggan pelanggan, String namaBaru, String noHpBaru) {
        pelanggan.setNama(namaBaru);
        pelanggan.setNoHp(noHpBaru);
    }

    public void hapusPelanggan(Pelanggan pelanggan) {
        if (punyaPelayananAktif(pelanggan.getIdPelanggan())) {
            throw new IllegalStateException("Pelanggan masih memiliki pelayanan aktif. Data tidak dapat dihapus.");
        }
        daftarPelanggan.remove(pelanggan);
    }

    // KELOLA BARBER
    /**
     * OVERLOADING 1/2: menambah barber reguler (tanpa perlu menyebut jenis).
     */
    public Barber tambahBarber(String id, String nama, int pengalaman) {
        return tambahBarber(id, nama, pengalaman, false);
    }

    /**
     * OVERLOADING 2/2: menambah barber sekaligus menentukan jenisnya
     * (reguler atau senior) lewat parameter tambahan.
     */
    public Barber tambahBarber(String id, String nama, int pengalaman, boolean senior) {
        if (idBarberSudahAda(id)) {
            throw new IllegalArgumentException("ID sudah digunakan.");
        }
        Barber barber = senior
                ? new BarberSenior(id, nama, pengalaman)
                : new Barber(id, nama, pengalaman);
        daftarBarber.add(barber);
        return barber;
    }

    public void ubahBarber(Barber barber, String namaBaru, int pengalamanBaru) {
        barber.setNamaBarber(namaBaru);
        barber.setPengalaman(pengalamanBaru);
    }

    public void hapusBarber(Barber barber) {
        if (barber.getJumlahPelangganAktif() > 0) {
            throw new IllegalStateException("Barber masih memiliki pelanggan aktif. Barber tidak dapat dihapus.");
        }
        daftarBarber.remove(barber);
    }

    public void ubahStatusBarber(Barber barber, boolean aktif) {
        if (!aktif && barber.getJumlahPelangganAktif() > 0) {
            throw new IllegalStateException(
                    "Barber masih memiliki pelanggan aktif. Selesaikan atau batalkan pelayanan terlebih dahulu.");
        }
        barber.setStatusKehadiran(aktif ? Barber.HADIR_AKTIF : Barber.HADIR_TIDAK_TERSEDIA);
    }

    // PELAYANAN
    public Pelayanan buatPelayanan(Pelanggan pelanggan, Barber barber, Layanan layanan) {
        if (punyaPelayananAktif(pelanggan.getIdPelanggan())) {
            throw new IllegalStateException("Pelanggan masih memiliki pelayanan aktif.");
        }
        if (barber.getStatusBarber().equals(Barber.PENUH)) {
            throw new IllegalStateException("Barber sudah penuh. Silakan pilih barber lain.");
        }
        if (barber.getStatusBarber().equals(Barber.TIDAK_TERSEDIA)) {
            throw new IllegalStateException("Barber sedang tidak tersedia.");
        }

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

    public void mulaiPelayanan(Pelayanan pelayanan) {
        if (!pelayanan.getStatusPelayanan().equals(Pelayanan.MENUNGGU)) {
            throw new IllegalStateException("Pelayanan tidak dapat dimulai.");
        }
        pelayanan.setStatusPelayanan(Pelayanan.DIPROSES);
    }

    public void selesaikanPelayanan(Pelayanan pelayanan) {
        if (!pelayanan.getStatusPelayanan().equals(Pelayanan.DIPROSES)) {
            throw new IllegalStateException("Pelayanan belum dalam proses.");
        }
        Barber barber = cariBarber(pelayanan.getIdBarber());
        pelayanan.setStatusPelayanan(Pelayanan.SELESAI);
        if (barber != null) {
            barber.kurangiPelanggan();
        }
    }

    public void batalkanPelayanan(Pelayanan pelayanan) {
        if (!pelayanan.getStatusPelayanan().equals(Pelayanan.MENUNGGU)) {
            throw new IllegalStateException("Hanya pelayanan berstatus Menunggu yang dapat dibatalkan.");
        }
        Barber barber = cariBarber(pelayanan.getIdBarber());
        pelayanan.setStatusPelayanan(Pelayanan.DIBATALKAN);
        if (barber != null) {
            barber.kurangiPelanggan();
        }
    }

    public void bayarPelayanan(Pelayanan pelayanan, String metode) {
        if (!pelayanan.getStatusPelayanan().equals(Pelayanan.SELESAI)) {
            throw new IllegalStateException("Pembayaran belum dapat dilakukan. Pelayanan belum selesai.");
        }
        if (pelayanan.getStatusPembayaran().equals(Pelayanan.LUNAS)) {
            throw new IllegalStateException("Pelayanan sudah dibayar.");
        }
        pelayanan.setMetodePembayaran(metode);
        pelayanan.setStatusPembayaran(Pelayanan.LUNAS);
    }

    // RINGKASAN (dihitung di sini, hanya diformat/dicetak oleh View)
    public int getJumlahPelangganMember() {
        int jumlah = 0;
        for (Pelanggan pelanggan : daftarPelanggan) {
            if (pelanggan instanceof PelangganMember) {
                jumlah++;
            }
        }
        return jumlah;
    }

    public int getJumlahBarberSenior() {
        int jumlah = 0;
        for (Barber barber : daftarBarber) {
            if (barber instanceof BarberSenior) {
                jumlah++;
            }
        }
        return jumlah;
    }

    public int getJumlahBarberDenganStatus(String status) {
        int jumlah = 0;
        for (Barber barber : daftarBarber) {
            if (barber.getStatusBarber().equals(status)) {
                jumlah++;
            }
        }
        return jumlah;
    }

    public int getJumlahPelayananDenganStatus(String status) {
        int jumlah = 0;
        for (Pelayanan pelayanan : daftarPelayanan) {
            if (pelayanan.getStatusPelayanan().equals(status)) {
                jumlah++;
            }
        }
        return jumlah;
    }

    public int getTotalPendapatan() {
        int total = 0;
        for (Pelayanan pelayanan : daftarPelayanan) {
            if (pelayanan.getStatusPembayaran().equals(Pelayanan.LUNAS)) {
                total += pelayanan.getTotalBayar();
            }
        }
        return total;
    }

    public int getPendapatanBerdasarkanMetode(String metode) {
        int total = 0;
        for (Pelayanan pelayanan : daftarPelayanan) {
            if (pelayanan.getStatusPembayaran().equals(Pelayanan.LUNAS)
                    && pelayanan.getMetodePembayaran().equals(metode)) {
                total += pelayanan.getTotalBayar();
            }
        }
        return total;
    }

    public int getTotalBelumDibayar() {
        int total = 0;
        for (Pelayanan pelayanan : daftarPelayanan) {
            if (pelayanan.getStatusPelayanan().equals(Pelayanan.SELESAI)
                    && !pelayanan.getStatusPembayaran().equals(Pelayanan.LUNAS)) {
                total += pelayanan.getTotalBayar();
            }
        }
        return total;
    }
}