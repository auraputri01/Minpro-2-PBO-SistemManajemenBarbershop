package model;

/**
 * SUB-CLASS dari Pelanggan.
 * Pelanggan member mendapat diskon 10% untuk setiap pelayanan.
 *
 * @author Aura
 */
public class PelangganMember extends Pelanggan {

    public PelangganMember(String idPelanggan, String nama, String noHp) {
        super(idPelanggan, nama, noHp);
    }

    @Override
    public int getPersenDiskon() {
        return 10;
    }

    @Override
    public String getPeran() {
        return "Pelanggan Member";
    }
}