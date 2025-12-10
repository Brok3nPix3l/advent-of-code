package Util;

public class Misc {
    public static Integer hashCodeForBooleanArray(boolean[] array) {
        int hash = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i]) {
                hash |= (1 << (array.length - 1 - i));
            }
        }
        return hash;
    }
}
