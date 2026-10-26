package pl.edu.pw.ee.aisd2026zex3.utils;

import java.lang.reflect.Field;
import pl.edu.pw.ee.aisd2026zex3.services.HashTable;

public class AdvancedGetters {

    public static int getNumOfElems(HashTable<?> hash) {
        String fieldNumOfElems = "nElems";

        try {
            Field field = hash.getClass().getSuperclass().getDeclaredField(fieldNumOfElems);
            field.setAccessible(true);

            int numOfElems = field.getInt(hash);

            return numOfElems;

        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T extends Comparable<T>> T[] getHashElems(HashTable<T> hash) {
        try {
            String fieldHashElems = "hashElems";

            Field field = hash.getClass().getSuperclass().getDeclaredField(fieldHashElems);
            field.setAccessible(true);

            T[] hashElems = (T[]) field.get(hash);

            return hashElems;

        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
