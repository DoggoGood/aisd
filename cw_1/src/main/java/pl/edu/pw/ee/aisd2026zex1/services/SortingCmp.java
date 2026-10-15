package pl.edu.pw.ee.aisd2026zex1.services;

public interface SortingCmp<T extends Comparable<T>> {

    void sort(T[] nums);
    
}
