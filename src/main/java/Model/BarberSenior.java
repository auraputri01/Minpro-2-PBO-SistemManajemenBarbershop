package model;

/**
 * SUB-CLASS dari Barber.
 * Barber senior mampu menangani lebih banyak antrean (7),
 * tetapi menambahkan biaya Rp10.000 pada setiap pelayanan.
 *
 * @author Aura
 */
public class BarberSenior extends Barber {

    public BarberSenior(String idBarber, String namaBarber, int pengalaman) {
        super(idBarber, namaBarber, pengalaman);
    }

    @Override
    public int getKapasitas() {
        return 7;
    }

    @Override
    public int getBiayaTambahan() {
        return 10000;
    }

    @Override
    public String getPeran() {
        return "Barber Senior";
    }
}