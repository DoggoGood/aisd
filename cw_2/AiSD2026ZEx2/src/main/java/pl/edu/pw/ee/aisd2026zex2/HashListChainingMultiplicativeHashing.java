package pl.edu.pw.ee.aisd2026zex2;

public class HashListChainingMultiplicativeHashing<T extends Comparable<T>> extends HashListChaining<T> {

    public HashListChainingMultiplicativeHashing() {
        super();
    }

    public HashListChainingMultiplicativeHashing(int size) {
        super(size);
    }

    @Override
    int countHashId(T value) {
        throw new UnsupportedOperationException("Not supported yet. TODO!");
    }
}
