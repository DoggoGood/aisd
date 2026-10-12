package pl.edu.pw.ee.aisd2026zex1.sorters.quicksort.recursive;

import pl.edu.pw.ee.aisd2026zex1.sorters.quicksort.recursive.QuickSortRecursiveHoare;
import pl.edu.pw.ee.aisd2026zex1.sorters.utils.GeneralSortTest;

public class QuickSortRecursiveHoareTest<T extends Comparable<T>> extends GeneralSortTest<T> {

    public QuickSortRecursiveHoareTest() {
        super(new QuickSortRecursiveHoare<>());
    }

}
