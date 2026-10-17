package pl.edu.pw.ee.aisd2026zex1.sorters.insort;

import static java.util.Objects.isNull;

public class InsertionSortWithBounds<T extends Comparable<T>> {

    public void sort(T[] data, int start, int end) {
        validateParams(data);

        T currentVal;
        int j;

        for (int i = start + 1; i <= end; i++) {

            currentVal = data[i];

            for (j = i - 1;
                    j >= start && data[j].compareTo(currentVal) > 0;
                    j--) {
                data[j + 1] = data[j];
            }
            j++;

            data[j] = currentVal;
        }
    }

    private void validateParams(T[] data) {
        if (isNull(data)) {
            throw new RuntimeException("Input args (data) cannot be null!");
        }
    }

}
