package pl.edu.pw.ee.aisd2026zex1.sorters.quicksort.iterative.improvements;

import java.util.ArrayList;
import java.util.List;
import pl.edu.pw.ee.aisd2026zex1.services.SortingCmp;
import pl.edu.pw.ee.aisd2026zex1.sorters.insort.InsertionSortWithBounds;

public class QuickSortIterativeWithInSort<T extends Comparable<T>> implements SortingCmp<T> {
    
    private static final int CUTOFF = 20;

    private final int cutoff;
    private final InsertionSortWithBounds<T> insertionSort;

    public QuickSortIterativeWithInSort() {
        this.cutoff = CUTOFF;
        this.insertionSort = new InsertionSortWithBounds<>();
    }

    public QuickSortIterativeWithInSort(int cutoff) {
        if (cutoff < 1) {
            throw new IllegalArgumentException("Cutoff must be greater than 0!");
        }
        this.cutoff = cutoff;
        this.insertionSort = new InsertionSortWithBounds<>();
    }

    @Override
    public void sort(T[] data) {
        if (data == null) {
            throw new IllegalArgumentException("Input args (data) cannot be null!");
        }

        quicksort(data);
    }

    private void quicksort(T[] data) {
        List<Integer> starts = new ArrayList<>();
        List<Integer> ends = new ArrayList<>();

        Integer left = 0;
        Integer right = data.length - 1;

        starts.add(left);
        ends.add(right);

        int n = 1;
        int pivot;

        if (left < right) {

            while (n > 0) {
                n--;
                left = starts.remove(n);
                right = ends.remove(n);

                if (right - left + 1 < cutoff) {
                    insertionSort.sort(data, left, right);
                    continue;
                }

                pivot = partition(data, left, right);

                if (pivot > left) {
                    starts.add(left);
                    ends.add(pivot);
                    n++;
                }

                if (pivot + 1 < right) {
                    starts.add(pivot + 1);
                    ends.add(right);
                    n++;
                }
            }
        }
    }

    private int partition(T[] data, int start, int end) {
        T pivot = data[start];

        int left = start - 1;
        int right = end + 1;

        while (true) {

            while (data[++left].compareTo(pivot) < 0) {
            }

            while (data[--right].compareTo(pivot) > 0) {
            }

            if (left < right) {
                swap(data, left, right);
            } else {
                break;
            }

        }

        return right;
    }

    private void swap(T[] data, int firstId, int secondId) {
        if (firstId != secondId) {
            T firstValue = data[firstId];
            data[firstId] = data[secondId];
            data[secondId] = firstValue;
        }
    }

}
