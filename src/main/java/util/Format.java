package util;

public class Format {

    public static String rupiah(int nominal) {
        return String.format("Rp %,d", nominal).replace(',', '.');
    }
}