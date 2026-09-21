package model;

import util.Format;
import util.Validator;

/**
 * Jenis layanan yang ditawarkan barbershop.
 *
 * @author Aura
 */
public class Layanan {
    private final String idLayanan;
    private String namaLayanan;
    private int harga;
    private int durasiMenit;

    public Layanan(String idLayanan, String namaLayanan, int harga, int durasiMenit) {
        if (!Validator.isIdValid(idLayanan)) {
            throw new IllegalArgumentException("ID layanan harus 3-10 karakter huruf/angka tanpa spasi.");
        }
        this.idLayanan = idLayanan;
        setNamaLayanan(namaLayanan);
        setHarga(harga);
        setDurasiMenit(durasiMenit);
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

    public final void setNamaLayanan(String namaLayanan) {
        if (namaLayanan == null || namaLayanan.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama layanan tidak boleh kosong.");
        }
        this.namaLayanan = namaLayanan.trim();
    }

    public final void setHarga(int harga) {
        if (harga <= 0) {
            throw new IllegalArgumentException("Harga harus lebih dari 0.");
        }
        this.harga = harga;
    }

    public final void setDurasiMenit(int durasiMenit) {
        if (durasiMenit <= 0) {
            throw new IllegalArgumentException("Durasi harus lebih dari 0 menit.");
        }
        this.durasiMenit = durasiMenit;
    }

    public void tampilkanData() {
        System.out.println("ID Layanan   : " + idLayanan);
        System.out.println("Nama Layanan : " + namaLayanan);
        System.out.println("Harga        : " + Format.rupiah(harga));
        System.out.println("Durasi       : " + durasiMenit + " menit");
    }
}