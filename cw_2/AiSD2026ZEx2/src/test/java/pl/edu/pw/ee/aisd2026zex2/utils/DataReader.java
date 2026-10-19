package pl.edu.pw.ee.aisd2026zex2.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataReader {

    private static final String DEFAULT_PATH_TO_FILE_WITH_WORDS = "src/main/resources/words.txt";

    public List<String> getAllWordsFromDefaultTxtFile() {
        return getAllWordsFromDefaultTxtFile(DEFAULT_PATH_TO_FILE_WITH_WORDS);
    }

    public List<String> getAllWordsFromDefaultTxtFile(String pathToFile) {
        List<String> words = new ArrayList<>();
        try (FileReader fileReader = new FileReader(pathToFile); BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String singleWord;

            while ((singleWord = bufferedReader.readLine()) != null) {
                words.add(singleWord);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return words;
    }

}
