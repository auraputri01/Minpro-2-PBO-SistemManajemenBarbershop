package util;

public class Validator {

    public static boolean isNoHpValid(String noHp) {
        return noHp != null && noHp.matches("08\\d{8,11}");
    }

    public static boolean isIdValid(String id) {
        return id != null && !id.trim().isEmpty();
    }

   public static boolean isNamaValid(String nama) {
    if (nama == null) {
        return false;
    }
    String n = nama.trim();
    return n.length() >= 2 && n.length() <= 50
            && Character.isLetter(n.charAt(0))
            && !n.matches(".*\\d.*");
    }
    public static boolean isPengalamanValid(int pengalaman) {
        return pengalaman >= 0;
    }
    public static boolean isAngka(String teks) {
    return teks != null && teks.trim().matches("\\d+");
    }
}
