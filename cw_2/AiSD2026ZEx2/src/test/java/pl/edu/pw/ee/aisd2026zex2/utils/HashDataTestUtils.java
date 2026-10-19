package pl.edu.pw.ee.aisd2026zex2.utils;

import java.util.List;
import pl.edu.pw.ee.aisd2026zex2.services.HashTable;
import static pl.edu.pw.ee.aisd2026zex2.utils.AdvancedConstructors.createHashInstance;

public class HashDataTestUtils {

    public HashTable<String> prepareHashWithSizeAndFillIt(Class hashListClazz, int size, List<String> words) {
        HashTable<String> hash = createHashInstance(size, hashListClazz);
        for (String word : words) {
            hash.add(word);
        }
        return hash;
    }

}
