package pl.edu.pw.ee.aisd2026zex3.performance.time;

import pl.edu.pw.ee.aisd2026zex3.HashOpenAddressing;
import static pl.edu.pw.ee.aisd2026zex3.dataUtils.WordsGenerator.prepareWordsFromFile;

public abstract class MitWordsPerformanceTest extends WordsPerformanceTest {

    public MitWordsPerformanceTest(Class<? extends HashOpenAddressing> hashClass) {
        super(hashClass);
    }

    @Override
    String[] prepareWords() {
        return prepareWordsFromFile();
    }

}
