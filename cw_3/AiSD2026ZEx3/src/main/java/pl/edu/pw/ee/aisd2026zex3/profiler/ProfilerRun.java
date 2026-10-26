package pl.edu.pw.ee.aisd2026zex3.profiler;

import pl.edu.pw.ee.aisd2026zex3.HashLinearProbing;
import static pl.edu.pw.ee.aisd2026zex3.dataUtils.WordsGenerator.prepareWordsFromFile;
import pl.edu.pw.ee.aisd2026zex3.services.HashTable;

public class ProfilerRun {

    public static void main(String[] args) {
        int size = 16_493; // {5101, 8297, 16_493, 32_909, 65_651, 131_111, 263_023, 524_309};

        String[] words = prepareWordsFromFile(); // prepareRandomWords();

        HashTable<String> hash = new HashLinearProbing<>(size); // new HashDoubleHashing<>(size);

        putWordsIntoHash(hash, words);
    }

    private static void putWordsIntoHash(HashTable<String> hash, String[] words) {
        int n = words.length;

        for (int i = 0; i < n; i++) {
            hash.put(words[i]);
        }
    }
}
