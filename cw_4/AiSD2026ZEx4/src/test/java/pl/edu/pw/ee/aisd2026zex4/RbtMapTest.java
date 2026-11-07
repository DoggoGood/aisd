package pl.edu.pw.ee.aisd2026zex4;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.pw.ee.aisd2026zex4.service.MapInterface;

public class RbtMapTest {

    private MapInterface<Integer, String> students;

    @BeforeEach
    public void setup() {
        students = new RbtMap<>();
    }

    @Test
    public void should_ThrowException_When_PuttingNullKey() {
        // given
        Integer studentId = null;
        String studentFullName = "Miś Uszatek";

        // when
        Throwable thrown = catchThrowable(() -> {
            students.setValue(studentId, studentFullName);
        });

        // then
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Params (key, value) cannot be null.");
    }

}
