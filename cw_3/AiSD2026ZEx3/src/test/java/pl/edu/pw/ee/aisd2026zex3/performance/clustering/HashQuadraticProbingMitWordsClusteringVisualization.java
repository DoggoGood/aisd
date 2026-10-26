package pl.edu.pw.ee.aisd2026zex3.performance.clustering;

import pl.edu.pw.ee.aisd2026zex3.HashQuadraticProbing;

public class HashQuadraticProbingMitWordsClusteringVisualization extends MitWordsClusteringVisualization {

    private final static int INITIAL_SIZE = 133337; // prime: 133337 -> loadFactor = 0.75

    public HashQuadraticProbingMitWordsClusteringVisualization() {
        super(HashQuadraticProbing.class, INITIAL_SIZE);
    }

}
