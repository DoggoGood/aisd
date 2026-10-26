package pl.edu.pw.ee.aisd2026zex3.performance.clustering;

import pl.edu.pw.ee.aisd2026zex3.HashLinearProbing;

public class HashLinearProbingMitWordsClusteringVisualization extends MitWordsClusteringVisualization {

    private final static int INITIAL_SIZE = 133337; // prime: 133337 -> loadFactor = 0.75

    public HashLinearProbingMitWordsClusteringVisualization() {
        super(HashLinearProbing.class, INITIAL_SIZE);
    }

}
