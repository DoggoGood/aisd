package pl.edu.pw.ee.aisd2026zex4.service;

public interface MapInterface<K extends Comparable<K>, V> {

    public void setValue(K key, V value);

    public V getValue(K key);
}
