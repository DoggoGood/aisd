package pl.edu.pw.ee.aisd2026zex3;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import org.junit.jupiter.api.Test;
import pl.edu.pw.ee.aisd2026zex3.services.HashTable;
import static pl.edu.pw.ee.aisd2026zex3.utils.AdvancedGetters.getNumOfElems;

public class HashLinearProbingTest {

    @Test
    public void should_ThrowException_WhenInitialSizeIsLowerThanOne() {
        int initialSize = 0;

        Throwable exceptionCaught = catchThrowable(() -> {
            new HashLinearProbing<>(initialSize);
        });

        String message = "Initial size of hash table cannot be lower than 1!";

        assertThat(exceptionCaught)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);
    }

    @Test
    public void should_CorrectlyAddNewElems_WhenNotExistInHashTable() {
        HashTable<String> unusedNames = new HashLinearProbing<>();
        String newElem = "Test";

        int nOfElemsBeforePut = getNumOfElems(unusedNames);
        unusedNames.put(newElem);
        int nOfElemsAfterPut = getNumOfElems(unusedNames);

        assertThat(nOfElemsBeforePut).isEqualTo(0);
        assertThat(nOfElemsAfterPut).isEqualTo(1);
    }

    @Test
    public void should_ReturnStoredElem_WhenElemExistsInHashTable() {
        HashTable<String> hashTable = new HashLinearProbing<>();
        String elem = "Test";

        hashTable.put(elem);

        assertThat(hashTable.get(elem)).isSameAs(elem);
    }

    @Test
    public void should_ReturnNull_WhenElemDoesNotExistInHashTable() {
        HashTable<String> hashTable = new HashLinearProbing<>();

        assertThat(hashTable.get("Test")).isNull();
    }

    @Test
    public void should_DeleteElem_WhenElemExistsInHashTable() {
        HashTable<String> hashTable = new HashLinearProbing<>();
        String elem = "Test";

        hashTable.put(elem);
        hashTable.delete(elem);

        assertThat(hashTable.get(elem)).isNull();
        assertThat(getNumOfElems(hashTable)).isZero();
    }

    @Test
    public void should_FindCollidingElem_WhenPreviousElemWasDeleted() {
        HashTable<String> hashTable = new HashLinearProbing<>();
        String firstElem = "Aa";
        String secondElem = "BB";

        hashTable.put(firstElem);
        hashTable.put(secondElem);
        hashTable.delete(firstElem);

        assertThat(hashTable.get(secondElem)).isSameAs(secondElem);
    }

    @Test
    public void should_ReuseDeletedSlot_WhenAddingNewElem() {
        HashTable<String> hashTable = new HashLinearProbing<>();
        String deletedElem = "Aa";
        String newElem = "BB";

        hashTable.put(deletedElem);
        hashTable.delete(deletedElem);
        hashTable.put(newElem);

        assertThat(hashTable.get(newElem)).isSameAs(newElem);
        assertThat(getNumOfElems(hashTable)).isEqualTo(1);
    }

    @Test
    public void should_ThrowException_WhenPuttingNull() {
        HashTable<String> hashTable = new HashLinearProbing<>();

        assertThat(catchThrowable(() -> hashTable.put(null)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Input elem cannot be null!");
    }

    @Test
    public void should_ThrowException_WhenGettingNull() {
        HashTable<String> hashTable = new HashLinearProbing<>();

        assertThat(catchThrowable(() -> hashTable.get(null)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Input elem cannot be null!");
    }

    @Test
    public void should_ThrowException_WhenDeletingNull() {
        HashTable<String> hashTable = new HashLinearProbing<>();

        assertThat(catchThrowable(() -> hashTable.delete(null)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Input elem cannot be null!");
    }

}
