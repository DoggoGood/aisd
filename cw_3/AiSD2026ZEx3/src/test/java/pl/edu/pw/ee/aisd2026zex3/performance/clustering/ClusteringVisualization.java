package pl.edu.pw.ee.aisd2026zex3.performance.clustering;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_BYTE_GRAY;
import java.io.File;
import java.io.IOException;
import static java.lang.String.format;
import static java.util.logging.Level.SEVERE;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.Test;
import pl.edu.pw.ee.aisd2026zex3.HashOpenAddressing;
import pl.edu.pw.ee.aisd2026zex3.services.HashTable;
import static pl.edu.pw.ee.aisd2026zex3.utils.AdvancedConstructors.createHashInstance;
import static pl.edu.pw.ee.aisd2026zex3.utils.AdvancedGetters.getHashElems;

public abstract class ClusteringVisualization {

    private static final Logger LOG = Logger.getLogger(ClusteringVisualization.class.getName());

    private final Class<? extends HashOpenAddressing> hashClass;

    private final int initialSize;
    private HashTable<String> hash;

    public ClusteringVisualization(Class<? extends HashOpenAddressing> hashClass, int initialSize) {
        this.hashClass = hashClass;
        this.initialSize = initialSize;
    }

    @Test
    public void visualizeGroupingOfOpenAddressing() {
        createFilledHash();

        BufferedImage bufImg = createImg();
        saveImg(bufImg);
    }

    abstract String[] prepareWords();

    private void createFilledHash() {
        hash = createHashInstance(initialSize, hashClass);

        String[] data = prepareWords();

        for (String word : data) {
            hash.put(word);
        }
    }

    private BufferedImage createImg() {
        Comparable[] hashElems = getHashElems(hash);

        int hashSize = hashElems.length;
        int width = 400;
        int height = Math.ceilDiv(hashSize, width);

        BufferedImage bufImg = new BufferedImage(width, height, TYPE_BYTE_GRAY);
        filledImageWithWhitePixels(bufImg, hashElems, height, width);

        return bufImg;
    }

    private void filledImageWithWhitePixels(
            BufferedImage bufImg, Comparable[] hashElems, int height, int width) {

        int hashSize = hashElems.length;

        Graphics image = bufImg.getGraphics();

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int index = row * width + col;

                if (index < hashSize && hashElems[index] != null) {
                    fillSingleWhitePixel(image, col, row);
                }
            }
        }
    }

    private void fillSingleWhitePixel(Graphics image, int col, int row) {
        image.setColor(Color.white);
        image.fillRect(col, row, 1, 1);
    }

    private void saveImg(BufferedImage bufImg) {
        String imgType = "png";
        int width = bufImg.getWidth();
        int height = bufImg.getHeight();
        File outputFile = prepareOutputFile(width, height);

        try {
            ImageIO.write(bufImg, imgType, outputFile);

        } catch (IOException e) {
            LOG.log(SEVERE, "Caught exception while saving visualization!", e);
        }
    }

    private File prepareOutputFile(int width, int height) {
        String clsName = this.getClass().getSimpleName();

        String fileName = format("visualization_%s_%d_x_%d.png", clsName, width, height);

        File outputFile = new File(fileName);

        return outputFile;
    }

}
