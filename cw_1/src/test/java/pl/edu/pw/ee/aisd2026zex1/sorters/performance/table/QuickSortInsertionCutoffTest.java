package pl.edu.pw.ee.aisd2026zex1.sorters.performance.table;

import static pl.edu.pw.ee.aisd2026zex1.sorters.utils.Generators.createRandomData;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.Test;
import pl.edu.pw.ee.aisd2026zex1.sorters.quicksort.iterative.improvements.QuickSortIterativeWithInSort;

public class QuickSortInsertionCutoffTest {

    private static final int WARMUP_ROUNDS = 20;
    private static final int DATA_SIZE = 262_144;
    private static final int MIN_CUTOFF = 1;
    private static final int MAX_CUTOFF = 256;
    private static final int RANGE_MIN_CUTOFF = 10;
    private static final int RANGE_MAX_CUTOFF = 30;

    private static final String RESULT_FILE = "QuickSortInsertionCutoff_performance.txt";

    @Test
    public void measureOptimalInsertionSortCutoff() {
        Double[] originalData = createRandomData(DATA_SIZE);

        warmUp(originalData);
        Map<Integer, Long> measurements = measureCutoffs(originalData);

        writeMeasurements(measurements);
    }

    private Map<Integer, Long> measureCutoffs(Double[] originalData) {
        Map<Integer, Long> measurements = new TreeMap<>();

        for (int cutoff = MIN_CUTOFF; cutoff <= MAX_CUTOFF; cutoff *= 2) {
            measureCutoff(originalData, cutoff, measurements);
        }
        for (int cutoff = RANGE_MIN_CUTOFF; cutoff <= RANGE_MAX_CUTOFF; cutoff++) {
            measureCutoff(originalData, cutoff, measurements);
        }

        return measurements;
    }

    private long measureCutoff(
            Double[] originalData,
            int cutoff,
            Map<Integer, Long> measurements) {
        if (measurements.containsKey(cutoff)) {
            return measurements.get(cutoff);
        }

        QuickSortIterativeWithInSort<Double> sorter = new QuickSortIterativeWithInSort<>(cutoff);

        Double[] dataToSort = Arrays.copyOf(
                originalData, originalData.length);

        TimeMeasureTask<Double> task = new TimeMeasureTask<>(sorter, dataToSort);

        task.run();
        long averageTime = task.getAverageTime();
        measurements.put(cutoff, averageTime);

        return averageTime;
    }

    private void writeMeasurements(Map<Integer, Long> measurements) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RESULT_FILE))) {
            writer.write("cutoff | average_time_ns");
            writer.newLine();

            for (Map.Entry<Integer, Long> measurement : measurements.entrySet()) {
                writer.write("%d | %d".formatted(
                        measurement.getKey(), measurement.getValue()));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new UncheckedIOException(
                    "Unable to write performance measurements to " + RESULT_FILE, e);
        }
    }

    private void warmUp(Double[] originalData) {
        QuickSortIterativeWithInSort<Double> sorter = new QuickSortIterativeWithInSort<>(20);
        Double[] buffer = new Double[originalData.length];

        for (int i = 0; i < WARMUP_ROUNDS; i++) {
            System.arraycopy(originalData, 0, buffer, 0, buffer.length);
            sorter.sort(buffer);
        }
    }

}
