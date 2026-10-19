package pl.edu.pw.ee.aisd2026zex2.stats;

import java.util.List;
import java.util.Map;
import static java.util.Objects.isNull;
import java.util.TreeMap;
import org.junit.jupiter.api.Test;
import pl.edu.pw.ee.aisd2026zex2.services.HashTable;
import static pl.edu.pw.ee.aisd2026zex2.utils.AdvancedGetters.getHashElemByIds;
import static pl.edu.pw.ee.aisd2026zex2.utils.AdvancedGetters.getHashSize;
import pl.edu.pw.ee.aisd2026zex2.utils.DataReader;
import pl.edu.pw.ee.aisd2026zex2.utils.HashDataTestUtils;

public abstract class ChainLengthStatsTest {

    private final Class hashListClazz;

    public ChainLengthStatsTest(Class hashListClazz) {
        this.hashListClazz = hashListClazz;
    }

    @Test
    public void runChainLengthStatsTest() {
        int[] hashSizes = getHashTabSizes();

        performTestOnAllSizes(hashSizes);
    }

    private int[] getHashTabSizes() {
        int[] sizes = new int[]{5101, 2097169};
        return sizes;
    }

    private void performTestOnAllSizes(int[] hashSizes) {
        for (int size : hashSizes) {
            performTestOnSize(size);
        }
    }

    private void performTestOnSize(int hashSize) {
        HashTable<String> hash = prepareFilledHash(hashSize);

        countAndPrintLengthOfChains(hash);
    }

    private HashTable<String> prepareFilledHash(int hashSize) {
        List<String> data = loadData();
        HashTable<String> hash = new HashDataTestUtils()
                .prepareHashWithSizeAndFillIt(hashListClazz, hashSize, data);

        return hash;
    }

    private List<String> loadData() {
        DataReader reader = new DataReader();
        List<String> words = reader.getAllWordsFromDefaultTxtFile();
        return words;
    }

    private void countAndPrintLengthOfChains(HashTable<String> hash) {
        Map<Integer, Integer> counters = countLengthOfChains(hash);

        displayLengthOfChainsStats(counters);
    }

    private Map<Integer, Integer> countLengthOfChains(HashTable<String> hash) {
        Map<Integer, Integer> counters = new TreeMap<>();

        int size = getHashSize(hash);
        int idInChain;
        Object chainElem;
        int chainLength;

        for (int i = 0; i < size; i++) {
            idInChain = 0;
            chainLength = 0;

            while (true) {
                chainElem = getHashElemByIds(hash, i, idInChain);
                if (isNull(chainElem)) {
                    break;
                }
                idInChain++;
                chainLength++;
            }

            int counterVal = counters.getOrDefault(chainLength, 0) + 1;
            counters.put(chainLength, counterVal);
        }

        return counters;
    }

    private void displayLengthOfChainsStats(Map<Integer, Integer> counters) {
        int nOfChains;

        for (int key : counters.keySet()) {
            nOfChains = counters.get(key);

            System.out.println(String.format("LengthOfChain: %4d | nOfChains: %8d", key, nOfChains));
        }
    }

}
