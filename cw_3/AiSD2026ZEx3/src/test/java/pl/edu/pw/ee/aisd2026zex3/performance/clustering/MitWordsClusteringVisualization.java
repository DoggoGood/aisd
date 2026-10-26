package pl.edu.pw.ee.aisd2026zex3.performance.clustering;

import pl.edu.pw.ee.aisd2026zex3.HashOpenAddressing;
import static pl.edu.pw.ee.aisd2026zex3.dataUtils.WordsGenerator.prepareWordsFromFile;

public abstract class MitWordsClusteringVisualization extends ClusteringVisualization {

    public MitWordsClusteringVisualization(Class<? extends HashOpenAddressing> hashClass, int initialSize) {
        super(hashClass, initialSize);
    }

    @Override
    String[] prepareWords() {
        return prepareWordsFromFile();
    }

}
