package pl.edu.pw.ee.aisd2026zex3.performance.clustering;

import pl.edu.pw.ee.aisd2026zex3.HashLinearProbing;

public class HashLinearProbingRandWordsClusteringVisualization extends RandWordsClusteringVisualization {

    private final static int INITIAL_SIZE = 133337; // prime: 133337 -> loadFactor = 0.75

    public HashLinearProbingRandWordsClusteringVisualization() {
        super(HashLinearProbing.class, INITIAL_SIZE);
    }

}
