package pl.edu.pw.ee.aisd2026zex3;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import org.junit.jupiter.api.Test;
import pl.edu.pw.ee.aisd2026zex3.services.HashTable;
import static pl.edu.pw.ee.aisd2026zex3.utils.AdvancedGetters.getNumOfElems;

public class HashLinearProbingTest {

    @Test
    public void should_ThrowException_WhenInitialSizeIsLowerThanOne() {
        // given
        int initialSize = 0;

        // when
        Throwable exceptionCaught = catchThrowable(() -> {
            new HashLinearProbing<>(initialSize);
        });

        // then
        String message = "Initial size of hash table cannot be lower than 1!";

        assertThat(exceptionCaught)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);
    }

    @Test
    public void should_CorrectlyAddNewElems_WhenNotExistInHashTable() {
        // given
        HashTable<String> unusedNames = new HashLinearProbing<>();
        String newElem = "P. Czarnek";

        // when
        int nOfElemsBeforePut = getNumOfElems(unusedNames);
        unusedNames.put(newElem);
        int nOfElemsAfterPut = getNumOfElems(unusedNames);

        // then
        assertThat(nOfElemsBeforePut).isEqualTo(0);
        assertThat(nOfElemsAfterPut).isEqualTo(1);
    }

}
