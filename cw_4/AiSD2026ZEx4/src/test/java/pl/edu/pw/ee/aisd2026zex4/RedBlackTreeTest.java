package pl.edu.pw.ee.aisd2026zex4;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RedBlackTreeTest {

    private RedBlackTree<Integer, String> tree;

    @BeforeEach
    public void setup() {
        tree = new RedBlackTree<>();
    }

    @Test
    public void should_ThrowException_When_PuttingNullKey() {
        // given
        Integer key = null;
        String value = "value";

        // when
        Throwable thrown = catchThrowable(() -> {
            tree.put(key, value);
        });

        // then
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Input params (key, value) cannot be null.");
    }

    @Test
    public void should_ThrowException_When_PuttingNullValue() {
        // given
        Integer key = 1;
        String value = null;

        // when
        Throwable thrown = catchThrowable(() -> {
            tree.put(key, value);
        });

        // then
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Input params (key, value) cannot be null.");
    }

    @Test
    public void should_ThrowException_When_GettingNullKey() {
        // given
        Integer key = null;

        // when
        Throwable thrown = catchThrowable(() -> {
            tree.get(key);
        });

        // then
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Key cannot be null.");
    }

    @Test
    public void should_DeleteMinFromEmptyTreeWithoutException() {
        // given
        Integer missingKey = 1;

        // when
        tree.deleteMin();

        // then
        assertThat(tree.get(missingKey)).isNull();
    }

    @Test
    public void should_ReturnInsertedValue() {
        // given
        Integer key = 1;
        String value = "value";

        // when
        tree.put(key, value);

        // then
        assertThat(tree.get(key)).isEqualTo(value);
    }

    @Test
    public void should_FindValueInLeftSubtree() {
        // given
        tree.put(2, "middle");
        tree.put(1, "left");

        // when
        String result = tree.get(1);

        // then
        assertThat(result).isEqualTo("left");
    }

    @Test
    public void should_FindValueInRightSubtree() {
        // given
        tree.put(1, "left");
        tree.put(2, "right");

        // when
        String result = tree.get(2);

        // then
        assertThat(result).isEqualTo("right");
    }

    @Test
    public void should_ReturnNull_When_KeyIsMissingAfterTraversal() {
        // given
        tree.put(2, "middle");
        tree.put(1, "left");
        tree.put(3, "right");

        // when
        String result = tree.get(4);

        // then
        assertThat(result).isNull();
    }

    @Test
    public void should_UpdateValue_When_KeyAlreadyExists() {
        // given
        Integer key = 1;
        tree.put(key, "old value");

        // when
        tree.put(key, "new value");

        // then
        assertThat(tree.get(key)).isEqualTo("new value");
    }

    @Test
    public void should_RecolorTree_WhenBothChildrenAreRed() {
        // given
        tree.put(2, "middle");
        tree.put(1, "left");

        // when
        tree.put(3, "right");

        // then
        assertThat(tree.get(1)).isEqualTo("left");
        assertThat(tree.get(2)).isEqualTo("middle");
        assertThat(tree.get(3)).isEqualTo("right");
    }

    @Test
    public void should_RotateRight_WhenLeftLeftPathIsCreated() {
        // given
        tree.put(3, "three");
        tree.put(2, "two");

        // when
        tree.put(1, "one");

        // then
        assertThat(tree.get(1)).isEqualTo("one");
        assertThat(tree.get(2)).isEqualTo("two");
        assertThat(tree.get(3)).isEqualTo("three");
    }
}
