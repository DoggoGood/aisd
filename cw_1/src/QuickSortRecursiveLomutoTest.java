package pl.edu.pw.ee.aisd2026zex1.sorters.quicksort.recursive;

import pl.edu.pw.ee.aisd2026zex1.sorters.quicksort.recursive.QuickSortRecursiveLomuto;
import pl.edu.pw.ee.aisd2026zex1.sorters.utils.GeneralSortTest;

public class QuickSortRecursiveLomutoTest<T extends Comparable<T>> extends GeneralSortTest<T> {

    public QuickSortRecursiveLomutoTest() {
        super(new QuickSortRecursiveLomuto<>());
    }

}
