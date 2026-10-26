package pl.edu.pw.ee.aisd2026zex3.utils;

import static java.lang.String.format;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import static java.util.Objects.isNull;
import pl.edu.pw.ee.aisd2026zex3.HashOpenAddressing;
import pl.edu.pw.ee.aisd2026zex3.services.HashTable;

public class AdvancedConstructors {

    public static HashTable<String> createHashInstance(int size, Class<? extends HashOpenAddressing> hashClazz) {

        Class<?>[] typeOfArgs = {int.class};
        Object[] args = {size};
        HashTable<String> hashInstance;

        Constructor<?> constructor;

        try {
            constructor = hashClazz.getConstructor(typeOfArgs);
            hashInstance = (HashTable<String>) constructor.newInstance(args);

        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException("[Error] Not found constructor by reflection!", e);

        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throwIllegalArgExceptionIfIsCauseOfException(e);

            String errMessage = format("Cannot create instance by reflection! {}", hashClazz.getSimpleName());
            throw new RuntimeException(errMessage, e);
        }

        return hashInstance;
    }

    private static void throwIllegalArgExceptionIfIsCauseOfException(Exception e) {
        if (!isNull(e.getCause()) && e.getCause() instanceof IllegalArgumentException) {
            throw new IllegalArgumentException(e.getCause().getMessage());
        }
    }

}
