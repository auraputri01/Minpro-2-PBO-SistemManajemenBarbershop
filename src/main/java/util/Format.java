package util;

import java.util.Locale;

/**
 * Format tampilan mata uang rupiah.
 *
 * @author Aura
 */
public final class Format {

    private Format() {
    }

    /** 125000 -> Rp125.000 */
    public static String rupiah(long nilai) {
        return "Rp" + String.format(Locale.US, "%,d", nilai).replace(',', '.');
    }
}