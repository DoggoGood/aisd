package pl.edu.pw.ee.aisd2026zex2.performance.utils;

public class HashSizeGenerator {

    public static int[] generateHashSizesPowerOf2(int nVariants, int initialSize) {
        int[] hashSizes = new int[nVariants];
        int initSize = initialSize;
        int multiplier;

        for (int i = 0; i < nVariants; i++) {
            multiplier = (int) Math.pow(2, i);
            hashSizes[i] = initSize * multiplier;
        }

        return hashSizes;
    }

    public static int[] generateHashSizePrimeNums(int nVariants) {
        int[] hashSizes = {
            5101,
            8297,
            16493,
            32909,
            65651,
            131111,
            263023
            // 524309,
            // 1048583,
            // 2097169
        };

        assert hashSizes.length == nVariants;

        return hashSizes;
    }
}
