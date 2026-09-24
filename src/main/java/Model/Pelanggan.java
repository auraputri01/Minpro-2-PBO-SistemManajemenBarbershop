package model;

import util.Validator;

/**
 * SUB-CLASS dari Orang (sekaligus SUPER-CLASS dari PelangganMember).
 * Pelanggan reguler tidak mendapat diskon.
 *
 * @author Aura
 */
public class Pelanggan extends Orang {
    private String noHp;

    public Pelanggan(String idPelanggan, String nama, String noHp) {
        super(idPelanggan, nama);
        setNoHp(noHp);
    }

    public String getIdPelanggan() {
        return getId();
    }

    public String getNoHp() {
        return noHp;
    }

    public final void setNoHp(String noHp) {
        if (!Validator.isNoHpValid(noHp)) {
            throw new IllegalArgumentException("No HP tidak valid. Contoh: 081234567890 (10-13 digit).");
        }
        this.noHp = noHp;
    }

    /** Persentase diskon; di-override oleh PelangganMember. */
    public int getPersenDiskon() {
        return 0;
    }

    public int hitungDiskon(int harga) {
        return harga * getPersenDiskon() / 100;
    }

    @Override
    public String getPeran() {
        return "Pelanggan Reguler";
    }

    @Override
    public void tampilkanData() {
        super.tampilkanData();
        cetak("No HP", noHp);
        cetak("Diskon", getPersenDiskon() + "%");
    }
}