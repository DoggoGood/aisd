package pl.edu.pw.ee.aisd2026zex2.performance;

import static java.lang.String.format;
import java.util.List;
import static java.util.logging.Level.INFO;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import pl.edu.pw.ee.aisd2026zex2.HashListChainingModularHashing;
import pl.edu.pw.ee.aisd2026zex2.services.HashTable;
import pl.edu.pw.ee.aisd2026zex2.utils.DataReader;
import pl.edu.pw.ee.aisd2026zex2.utils.HashDataTestUtils;

abstract class PerformanceTest {

    private static final Logger LOG = Logger.getLogger(PerformanceTest.class.getName());

    private static final int N_OF_REPETITIONS = 30;
    private static final int NUM_OF_AVG_VALUES = 10;

    private static final Class DEFAULT_CLASS = HashListChainingModularHashing.class;

    static final int N_VARIANTS = 10;
    static final int INIT_SIZE = 4096;

    private final Class hashListClazz;

    public PerformanceTest() {
        this(DEFAULT_CLASS);

        LOG.log(INFO, "Setting default hash class: {0}", DEFAULT_CLASS);
    }

    public PerformanceTest(Class hashListClazz) {
        this.hashListClazz = hashListClazz;
    }

    @Test
    public void runPerformanceTestOnNVariantsOfSize() {
        int[] hashSizes = getAllHashSizes();
        DataReader reader = new DataReader();
        List<String> words = reader.getAllWordsFromDefaultTxtFile();
        HashTable<String> hash;
        long averageTime;

        System.out.println("Average time for");

        System.gc();
        for (int i = 0; i < N_VARIANTS; i++) {
            hash = prepareHashWithSizeAndFillIt(hashSizes[i], words);
            averageTime = measureAverageTimeOfGettingWords(words, hash);

            System.out.println(format("\t size = %7d | avg_time = %9d", hashSizes[i], averageTime));
        }

        assert true;
    }

    abstract int[] getAllHashSizes();

    private HashTable<String> prepareHashWithSizeAndFillIt(int size, List<String> words) {
        return new HashDataTestUtils().prepareHashWithSizeAndFillIt(hashListClazz, size, words);
    }

    private long measureAverageTimeOfGettingWords(List<String> words, HashTable hash) {
        long[] results = new long[N_OF_REPETITIONS];

        for (int i = 0; i < N_OF_REPETITIONS; i++) {
            results[i] = measureTimeOfGettingWords(words, hash);
        }

        long avgMeasuredTime = countAverageFromCenter10Values(results);

        return avgMeasuredTime;
    }

    private long measureTimeOfGettingWords(List<String> words, HashTable hash) {
        int n = words.size();
        long startTime = System.nanoTime();

        for (int i = 0; i < n; i++) {
            hash.get(words.get(i));
        }

        long totalTime = System.nanoTime() - startTime;

        return totalTime;
    }

    private long countAverageFromCenter10Values(long[] results) {
        int startId = (N_OF_REPETITIONS - NUM_OF_AVG_VALUES) / 2;
        int endId = startId + NUM_OF_AVG_VALUES;

        long avgValue = 0;

        for (int i = startId; i < endId; i++) {
            avgValue += results[i];
        }

        avgValue /= NUM_OF_AVG_VALUES;

        return avgValue;
    }

}
