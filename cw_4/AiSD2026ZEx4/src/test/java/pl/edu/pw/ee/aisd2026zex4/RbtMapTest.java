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


    @Test
    public void should_ThrowException_When_PuttingNullValue() {
        // given
        Integer studentId = 1;
        String studentFullName = null;

        // when
        Throwable thrown = catchThrowable(() -> {
            students.setValue(studentId, studentFullName);
        });

        // then
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Params (key, value) cannot be null.");
    }

    @Test
    public void should_ReturnInsertedValue() {
        // given
        Integer studentId = 1;
        String studentFullName = "Miś Uszatek";
        students.setValue(studentId, studentFullName);

        // when
        String retrievedValue = students.getValue(studentId);

        // then
        assertThat(retrievedValue).isEqualTo(studentFullName);
    }

    @Test
    public void should_ReturnNullFromMissingKey() {
        //given
        Integer studentId = 1;

        //when
        String retrievedValue = students.getValue(studentId);

        //then
        assertThat(retrievedValue).isNull();
    }

    @Test
    public void should_UpdateValue_When_KeyAlreadyExists() {
        // given
        Integer studentId = 1;
        String studentFullName = "Miś Uszatek";
        students.setValue(studentId, studentFullName);

        // when
        String updatedStudentFullName = "Miś Uszatek 2";
        students.setValue(studentId, updatedStudentFullName);
        String retrievedValue = students.getValue(studentId);

        // then
        assertThat(retrievedValue).isEqualTo(updatedStudentFullName);
    }

    @Test
    public void should_StoreMultipleValues() {
        // given
        Integer studentId1 = 1;
        String studentFullName1 = "Miś Uszatek";
        students.setValue(studentId1, studentFullName1);

        Integer studentId2 = 2;
        String studentFullName2 = "Miś Uszatek 2";
        students.setValue(studentId2, studentFullName2);

        // when
        String retrievedValue1 = students.getValue(studentId1);
        String retrievedValue2 = students.getValue(studentId2);

        // then
        assertThat(retrievedValue1).isEqualTo(studentFullName1);
        assertThat(retrievedValue2).isEqualTo(studentFullName2);
    }

    @Test
    public void shoukd_ThrowException_When_GettingValueByNullKey() {
        //given
        Integer studentId = null;

        //when
        Throwable thrown = catchThrowable(() -> {
            students.getValue(studentId);
        });

        //assert
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cannot get value by null key.");

    }
}
