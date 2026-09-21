package model;

import util.Format;

/**
 * Catatan satu kali pelayanan (antrean, proses, dan pembayaran).
 *
 * @author Aura
 */
public class Pelayanan {
    public static final String MENUNGGU = "Menunggu";
    public static final String DIPROSES = "Diproses";
    public static final String SELESAI = "Selesai";
    public static final String DIBATALKAN = "Dibatalkan";

    public static final String BELUM_BAYAR = "Belum Bayar";
    public static final String LUNAS = "Lunas";

    public static final String TUNAI = "Tunai";
    public static final String QRIS = "QRIS";

    private final String idPelayanan;
    private final String idPelanggan;
    private final String idBarber;
    private final String idLayanan;
    private final int nomorAntrean;
    private final int totalBayar;
    private String statusPelayanan;
    private String statusPembayaran;
    private String metodePembayaran;

    public Pelayanan(String idPelayanan, String idPelanggan, String idBarber,
                     String idLayanan, int nomorAntrean, int totalBayar) {
        if (nomorAntrean < 1) {
            throw new IllegalArgumentException("Nomor antrean minimal 1.");
        }
        if (totalBayar < 0) {
            throw new IllegalArgumentException("Total bayar tidak boleh negatif.");
        }
        this.idPelayanan = idPelayanan;
        this.idPelanggan = idPelanggan;
        this.idBarber = idBarber;
        this.idLayanan = idLayanan;
        this.nomorAntrean = nomorAntrean;
        this.totalBayar = totalBayar;
        this.statusPelayanan = MENUNGGU;
        this.statusPembayaran = BELUM_BAYAR;
        this.metodePembayaran = "-";
    }

    // ---------- Getter ----------
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

    // ---------- Setter (dengan validasi nilai yang diperbolehkan) ----------
    public void setStatusPelayanan(String statusPelayanan) {
        if (!MENUNGGU.equals(statusPelayanan) && !DIPROSES.equals(statusPelayanan)
                && !SELESAI.equals(statusPelayanan) && !DIBATALKAN.equals(statusPelayanan)) {
            throw new IllegalArgumentException("Status pelayanan tidak valid: " + statusPelayanan);
        }
        this.statusPelayanan = statusPelayanan;
    }

    public void setStatusPembayaran(String statusPembayaran) {
        if (!BELUM_BAYAR.equals(statusPembayaran) && !LUNAS.equals(statusPembayaran)) {
            throw new IllegalArgumentException("Status pembayaran tidak valid: " + statusPembayaran);
        }
        this.statusPembayaran = statusPembayaran;
    }

    public void setMetodePembayaran(String metodePembayaran) {
        if (!TUNAI.equals(metodePembayaran) && !QRIS.equals(metodePembayaran) && !"-".equals(metodePembayaran)) {
            throw new IllegalArgumentException("Metode pembayaran tidak valid: " + metodePembayaran);
        }
        this.metodePembayaran = metodePembayaran;
    }

    /** True jika pelayanan masih berjalan (Menunggu atau Diproses). */
    public boolean isAktif() {
        return MENUNGGU.equals(statusPelayanan) || DIPROSES.equals(statusPelayanan);
    }

    public void tampilkanData() {
        System.out.println("ID Pelayanan       : " + idPelayanan);
        System.out.println("ID Pelanggan       : " + idPelanggan);
        System.out.println("ID Barber          : " + idBarber);
        System.out.println("ID Layanan         : " + idLayanan);
        System.out.println("Nomor Antrean      : " + nomorAntrean);
        System.out.println("Total Bayar        : " + Format.rupiah(totalBayar));
        System.out.println("Status Pelayanan   : " + statusPelayanan);
        System.out.println("Status Pembayaran  : " + statusPembayaran);
        System.out.println("Metode Pembayaran  : " + metodePembayaran);
    }
}