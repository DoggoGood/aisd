package pl.edu.pw.ee.aisd2026zex1.sorters.quicksort.iterative.improvements;

import pl.edu.pw.ee.aisd2026zex1.sorters.utils.GeneralSortTest;

public class QuickSortIterativeWithInSortTest<T extends Comparable<T>>
        extends GeneralSortTest<T> {

    public QuickSortIterativeWithInSortTest() {
        super(new QuickSortIterativeWithInSort<>());
    }
}
