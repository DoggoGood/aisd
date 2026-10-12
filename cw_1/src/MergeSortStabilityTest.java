package pl.edu.pw.ee.aisd2026zex1.sorters.mergesort;

import pl.edu.pw.ee.aisd2026zex1.sorters.mergesort.MergeSort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.pw.ee.aisd2026zex1.services.SortingCmp;

public class MergeSortStabilityTest {

    private SortingCmp<Integer> sorter;

    @BeforeEach
    public void setUp() {
        sorter = new MergeSort<>();
    }

    @Test
    public void should_SortStably_When_ValuesAreRepeated() {

    }

    // TODO: other tests
}
