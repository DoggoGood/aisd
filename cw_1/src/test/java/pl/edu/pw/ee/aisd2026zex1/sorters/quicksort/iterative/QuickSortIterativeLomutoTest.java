package pl.edu.pw.ee.aisd2026zex1.sorters.quicksort.iterative;

import pl.edu.pw.ee.aisd2026zex1.sorters.quicksort.iterative.QuickSortIterativeLomuto;
import pl.edu.pw.ee.aisd2026zex1.sorters.utils.GeneralSortTest;

public class QuickSortIterativeLomutoTest<T extends Comparable<T>> extends GeneralSortTest {

    public QuickSortIterativeLomutoTest() {
        super(new QuickSortIterativeLomuto<>());
    }

}
