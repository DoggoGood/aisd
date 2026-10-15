package pl.edu.pw.ee.aisd2026zex1.sorters.quicksort.iterative.improvements;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pw.ee.aisd2026zex1.services.SortingCmp;

public class QuickSortIterativeMedian3<T extends Comparable<T>> implements SortingCmp<T> {

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
                medianAsPivot(data, left, right);
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

    private void medianAsPivot(T[] data, int start, int end) {
        int mid = start+(start-end)/2; 
        if (data[start].compareTo(data[mid]) > 0) {
            swap(data, start, mid);
        }
        if (data[start].compareTo(data[end]) > 0) {
            swap(data, start, end);
        }
        if (data[mid].compareTo(data[end]) > 0) {
            swap(data, mid, end);
        }
        swap(data, mid, start);

    }

}
