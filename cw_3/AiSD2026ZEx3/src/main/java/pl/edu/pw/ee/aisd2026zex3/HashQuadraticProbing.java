package pl.edu.pw.ee.aisd2026zex3;

public class HashQuadraticProbing<T extends Comparable<T>> extends HashOpenAddressing<T> {

    private static final long a = 4001;
    private static final long b = 7919;

    public HashQuadraticProbing() {
        super();
    }

    public HashQuadraticProbing(int size) {
        super(size);
    }

    @Override
    int hashFunc(int key, int i) {
        int m = getSize();
        key = key & Integer.MAX_VALUE;

        long h = key % m;

        return (int) (h + a * i + b * i * i) % m;

    }

}
