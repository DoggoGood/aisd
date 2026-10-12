package pl.edu.pw.ee.aisd2026zex1.sorters.mergesort;

import pl.edu.pw.ee.aisd2026zex1.sorters.mergesort.MergeSort;
import pl.edu.pw.ee.aisd2026zex1.sorters.utils.GeneralSortTest;

public class MergeSortTest<T extends Comparable<T>> extends GeneralSortTest<T> {

    public MergeSortTest() {
        super(new MergeSort<>());
    }

}