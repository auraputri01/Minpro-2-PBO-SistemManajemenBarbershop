package util;

/**
 * Kumpulan aturan validasi input yang dipakai bersama oleh Main dan class model.
 * Constructor private + final class: hanya berisi method static (tidak perlu dibuat objek).
 *
 * @author Aura
 */
public final class Validator {

    private Validator() {
    }

    /** ID: 3-10 karakter, hanya huruf/angka, tanpa spasi. Contoh: P001, B002. */
    public static boolean isIdValid(String id) {
        return id != null && id.matches("[A-Za-z0-9]{3,10}");
    }

    /** Nama: 2-50 karakter, diawali huruf, boleh huruf, spasi, titik, apostrof, dan strip. */
    public static boolean isNamaValid(String nama) {
        return nama != null && nama.trim().matches("[A-Za-z][A-Za-z .'-]{1,49}");
    }

    /** No HP Indonesia: diawali 08 / 628 / +628, total 10-13 digit. */
    public static boolean isNoHpValid(String noHp) {
        return noHp != null && noHp.matches("(\\+62|62|0)8[0-9]{8,11}");
    }

    /** Hanya digit (1-9 digit) agar aman dikonversi ke int. */
    public static boolean isAngka(String teks) {
        return teks != null && teks.matches("[0-9]{1,9}");
    }

    public static boolean isPengalamanValid(int tahun) {
        return tahun >= 0 && tahun <= 50;
    }
}