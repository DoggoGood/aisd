package pl.edu.pw.ee.aisd2026zex4.performance;

import java.util.Random;

public class DataGenerator {

    private static final long RANDOM_SEED = 123L;

    public String[] generateRandData(int size) {
        String[] data = generateAscData(size);
        Random random = new Random(RANDOM_SEED);

        for (int i = data.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            String temporary = data[i];
            data[i] = data[j];
            data[j] = temporary;
        }

        return data;
    }

    public String[] generateAscData(int size) {
        String[] data = new String[size];
        int width = numberWidth(size);

        for (int i = 0; i < size; i++) {
            data[i] = formatNumber(i, width);
        }

        return data;
    }

    public String[] generateDescData(int size) {
        String[] data = new String[size];
        int width = numberWidth(size);

        for (int i = 0; i < size; i++) {
            data[i] = formatNumber(size - i - 1, width);
        }

        return data;
    }

    private int numberWidth(int size) {
        return Integer.toString(Math.max(size - 1, 0)).length();
    }

    private String formatNumber(int number, int width) {
        return String.format("%0" + width + "d", number);
    }
}
