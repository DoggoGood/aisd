package pl.edu.pw.ee.aisd2026zex4.performance;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class DataGeneratorTest {

    private final DataGenerator generator = new DataGenerator();

    @Test
    public void should_GenerateAscendingData() {
        // given
        int size = 5;

        // when
        String[] result = generator.generateAscData(size);

        // then
        assertThat(result)
                .containsExactly("0", "1", "2", "3", "4");
    }

    @Test
    public void should_GenerateDescendingData() {
        // given
        int size = 5;

        // when
        String[] result = generator.generateDescData(size);

        // then
        assertThat(result)
                .containsExactly("4", "3", "2", "1", "0");
    }

    @Test
    public void should_GenerateRequestedNumberOfRandomElements() {
        // given
        int size = 10;

        // when
        String[] result = generator.generateRandData(size);

        // then
        assertThat(result)
                .hasSize(size)
                .doesNotContainNull()
                .doesNotHaveDuplicates();
    }

    @Test
    public void should_GenerateEmptyArraysForZeroSize() {
        // when
        String[] ascending = generator.generateAscData(0);
        String[] descending = generator.generateDescData(0);
        String[] random = generator.generateRandData(0);

        // then
        assertThat(ascending).isEmpty();
        assertThat(descending).isEmpty();
        assertThat(random).isEmpty();
    }
}
