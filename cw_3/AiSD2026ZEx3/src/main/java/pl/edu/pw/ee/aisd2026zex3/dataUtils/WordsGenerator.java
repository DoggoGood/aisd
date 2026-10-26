package pl.edu.pw.ee.aisd2026zex3.dataUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import static java.util.logging.Level.SEVERE;
import java.util.logging.Logger;

public class WordsGenerator {

    private static final String WORDS_FILEPATH = "src/main/resources/wordlist_100_000.txt";

    private static final Logger LOG = Logger.getLogger(WordsGenerator.class.getName());

    public static String[] prepareWordsFromFile() {
        List<String> words = new ArrayList<>();
        String line;
        int counter = 0;
        int maxNumWords = 100_000;

        try (FileReader fReader = new FileReader(WORDS_FILEPATH); BufferedReader buffReader = new BufferedReader(fReader)) {

            while ((line = buffReader.readLine()) != null && counter < maxNumWords) {
                words.add(line);
                counter++;
            }

        } catch (IOException e) {
            LOG.log(SEVERE, "[Error] An error ocurred during preparing words.", e);
        }

        return words.toArray(String[]::new);
    }

    public static String[] prepareRandomWords() {
        Set<String> words = new HashSet<>();
        int maxNumOfWords = 100_000;
        int seed = 123;
        Random rand = new Random(seed);

        while (words.size() < maxNumOfWords) {
            words.add(String.valueOf(rand.nextInt()));
        }

        return words.toArray(String[]::new);
    }

}
