package pl.edu.pw.ee.aisd2026zex3.performance.clustering;

import pl.edu.pw.ee.aisd2026zex3.HashDoubleHashing;

public class HashDoubleHashingMitWordsClusteringVisualization extends MitWordsClusteringVisualization {
    
    private final static int INITIAL_SIZE = 133337; // prime: 133337 -> loadFactor = 0.75

    public HashDoubleHashingMitWordsClusteringVisualization() {
        super(HashDoubleHashing.class, INITIAL_SIZE);
    }

}
