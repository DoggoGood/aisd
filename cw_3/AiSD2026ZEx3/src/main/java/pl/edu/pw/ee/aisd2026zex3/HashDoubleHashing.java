package pl.edu.pw.ee.aisd2026zex3;

public class HashDoubleHashing<T extends Comparable<T>> extends HashOpenAddressing<T> {

    public HashDoubleHashing() {
        super();
    }

    public HashDoubleHashing(int size) {
        super(size);
    }

    @Override
    int hashFunc(int key, int i) {
        int m = getSize();
        key = key & Integer.MAX_VALUE;

        long h1 = key % m;
        long h2 = 1 + (key % (m - 1));
    
        return (int) ((h1 + i * h2) % m);
    }

}
