package pl.edu.pw.ee.aisd2026zex2;

public class HashListChainingMultiplicativeHashing<T extends Comparable<T>> extends HashListChaining<T> {

    // private static final double A = (Math.sqrt(5) - 1) / 2;
    // private static final double A = 0.5;
    private static final double A = Math.PI-3;

    public HashListChainingMultiplicativeHashing() {
        super();
    }

    public HashListChainingMultiplicativeHashing(int size) {
        super(size);
    }

    @Override
    int countHashId(T value) {
        int hashCode = value.hashCode();
        return (int) (Math.floor(size * ((hashCode * A)-Math.floor(hashCode * A))));
    }
}
