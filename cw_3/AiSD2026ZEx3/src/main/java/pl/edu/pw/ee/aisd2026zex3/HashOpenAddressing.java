package pl.edu.pw.ee.aisd2026zex3;

import pl.edu.pw.ee.aisd2026zex3.services.HashTable;

public abstract class HashOpenAddressing<T extends Comparable<T>> implements HashTable<T> {

    private final T nil = null;
    private int size;
    private int nElems;
    private T[] hashElems;
    private boolean[] deleted;
    private final double correctLoadFactor;

    HashOpenAddressing() {
        this(2039); // initial size as random prime number
    }

    HashOpenAddressing(int size) {
        validateHashInitSize(size);

        this.size = size;
        this.hashElems = createTable(this.size);
        this.deleted = new boolean[this.size];
        this.correctLoadFactor = 0.75;
    }

    @Override
    public void put(T newElem) {
        validateInputElem(newElem);
        resizeIfNeeded();

        int key = newElem.hashCode();
        int i = 0;
        int hashId = hashFunc(key, i);

        while (hashElems[hashId] != nil && !deleted[hashId]) {
            if (i + 1 == size) {
                doubleResize();
                i = -1;
            }
            i = (i + 1) % size;
            hashId = hashFunc(key, i);
        }

        hashElems[hashId] = newElem;
        deleted[hashId] = false;
        nElems++;
    }

    @Override
    public T get(T elem) {
        validateInputElem(elem);

        int key = elem.hashCode();
        int i = 0;
        int hashId = hashFunc(key, i);

        while (hashElems[hashId] != nil || deleted[hashId]) {
            if (hashElems[hashId] != nil && hashElems[hashId].equals(elem)) {
                return hashElems[hashId];
            }
            i = (i + 1) % size;
            hashId = hashFunc(key, i);
        }

        return null;
    }

    @Override
    public void delete(T elem) {
        validateInputElem(elem);

        int key = elem.hashCode();
        int i = 0;
        int hashId = hashFunc(key, i);

        while (hashElems[hashId] != nil || deleted[hashId]) {
            if (hashElems[hashId] != nil && hashElems[hashId].equals(elem)) {
                hashElems[hashId] = nil;
                deleted[hashId] = true;
                nElems--;
                return;
            }
            i = (i + 1) % size;
            hashId = hashFunc(key, i);
        }

    }

    private void validateHashInitSize(int initialSize) {
        if (initialSize < 1) {
            throw new IllegalArgumentException("Initial size of hash table cannot be lower than 1!");
        }
    }

    private void validateInputElem(T newElem) {
        if (newElem == null) {
            throw new IllegalArgumentException("Input elem cannot be null!");
        }
    }

    abstract int hashFunc(int key, int i);

    int getSize() {
        return size;
    }

    private T[] createTable(int size) {
        return (T[]) new Comparable[size];
    }

    private void resizeIfNeeded() {
        double loadFactor = countLoadFactor();

        if (loadFactor >= correctLoadFactor) {
            doubleResize();
        }
    }

    private double countLoadFactor() {
        return (double) nElems / size;
    }

    private void doubleResize() {
        T[] oldElems = hashElems;
        this.size *= 2;
        hashElems = createTable(size);
        deleted = new boolean[size];
        nElems = 0;

        int i = 0;
        while (i < oldElems.length) {
            if (oldElems[i] != nil) {
                put(oldElems[i]);
            }
            i++;
        }
    }
}
