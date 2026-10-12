package pl.edu.pw.ee.aisd2026zex1.sorters.quicksort.iterative;

import pl.edu.pw.ee.aisd2026zex1.sorters.quicksort.iterative.QuickSortIterativeHoare;
import pl.edu.pw.ee.aisd2026zex1.sorters.utils.GeneralSortTest;

public class QuickSortIterativeHoareTest<T extends Comparable<T>> extends GeneralSortTest<T> {

    public QuickSortIterativeHoareTest() {
        super(new QuickSortIterativeHoare<>());
    }

}