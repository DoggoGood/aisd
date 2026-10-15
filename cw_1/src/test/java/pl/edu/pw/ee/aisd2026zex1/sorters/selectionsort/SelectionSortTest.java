package pl.edu.pw.ee.aisd2026zex1.sorters.selectionsort;

import pl.edu.pw.ee.aisd2026zex1.sorters.selectionsort.SelectionSort;
import pl.edu.pw.ee.aisd2026zex1.sorters.utils.GeneralSortTest;

public class SelectionSortTest<T extends Comparable<T>> extends GeneralSortTest<T> {

    public SelectionSortTest() {
        super(new SelectionSort<>());
    }

}
