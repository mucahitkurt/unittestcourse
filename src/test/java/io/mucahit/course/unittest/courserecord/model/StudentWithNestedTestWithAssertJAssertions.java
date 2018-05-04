package io.mucahit.course.unittest.courserecord.model;

import io.mucahit.course.unittest.courserecord.model.LecturerCourseRecord;
import io.mucahit.course.unittest.courserecord.model.Student;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author mucahitkurt
 * @since 12.04.2018
 */
//@ExtendWith(DropCourseConditionExtension.class)
@Tag("student")
@DisplayName("Student Test With Nested Tests")
public class StudentWithNestedTestWithAssertJAssertions {

//    @RegisterExtension
//    static TestLoggerExtension testLoggerExtension = new TestLoggerExtension();

    @Nested
    @DisplayName("Create Student")
    @Tag("createStudent")
    class CreateStudent {

        @Test
        @DisplayName("Test every student must have an id, name and surname")
        void shouldCreateStudentWithIdNameAndSurname() {

            /**
             * Assertions.*
             * assertEquals
             * assertEquals with message
             * assertNotEquals
             * assertTrue with lazily evaluated message
             * assertFalse with boolean supplier
             * assertNull
             * assertNotNull
             * assertArrayEquals
             * assertSame
             */

            final Student ahmet = new Student("2", "Ahmet", "Yilmaz");
            Student mucahit = new Student("1", "Mucahit", "Kurt");
            Student student = mucahit;

            assertThat(mucahit).as("Mucahit")
                    .isSameAs(student)
                    .isNotSameAs(ahmet)
                    .extracting(Student::getName)
                    .first()
                    .asString()
                    .isEqualTo("Mucahit")
                    .isNotEqualTo("Mucahitt")
                    .startsWith("M")
            ;

            assertThat(new Student("id1", "Mehmet", "Can").getName()).as("Mehmet")
                    .doesNotEndWith("M")
            ;

            assertThat(List.of(mucahit, ahmet))
                    .extracting(Student::getName)
                    .containsOnly("Mucahit", "Ahmet")
            ;
        }

        @Test
        @DisplayName("Test every student must have an id, name and surname with grouped assertions")
        void shouldCreateStudentWithIdNameAndSurnameWithGroupedAssertions() {

            /**
             * grouped assertions
             * failed grouped assertions
             * dependent assertions
             */

            // In a grouped assertion all assertions are executed,
            Student mucahit = new Student("1", "Mucahit", "Kurt");

            SoftAssertions softAssertions = new SoftAssertions();
            softAssertions.assertThat(mucahit.getName()).as("Mucahit's name").isEqualTo("Mucahit");
            softAssertions.assertThat(mucahit.getName()).as("Mucahitt's name").isNotEqualTo("Mucahitt");
            softAssertions.assertAll();

            // and any failures will be reported together.
            assertAll("Student's name character check",
                    () -> assertTrue(mucahit.getName().startsWith("M")),
                    () -> assertTrue(mucahit.getName().startsWith("M"), () -> "Student's name " + "starts with Mu"),
                    () -> assertFalse(() -> {
                        Student mehmet = new Student("id1", "Mehmet", "Can");
                        return mehmet.getName().endsWith("M");
                    }, () -> "Student's name " + "ends with M")
            );

            //dependent assertions
            assertAll(() -> {
                        final Student ahmet = new Student("2", "Ahmet", "Yilmaz");

                        assertArrayEquals(new String[]{"Mucahit", "Ahmet"}, Stream.of(mucahit, ahmet).map(Student::getName).toArray());
                    },
                    () -> {
                        Student student = mucahit;
                        final Student ahmet = new Student("2", "Ahmet", "Yilmaz");

                        assertSame(mucahit, student); // mucahit == student
                        assertNotSame(student, ahmet);
                    });
        }
    }

    @Nested
    @DisplayName("Add Course to Student")
    @Tag("addCourse")
    class AddCourseToStudent {
        @Test
        @DisplayName("Add course to a student less than 10ms")
        void addCourseToStudentWithATimeConstraint() {
            /**
             * timeoutNotExceeded
             * timeoutNotExceededWithResult
             * timeoutNotExceededWithMethod
             * timeoutExceeded
             * timeoutExceededWithPreemptiveTermination
             */

            assertTimeout(Duration.ofMillis(10), () -> {
                //nothing will be done and this code run under 10ms
            });

            final String result = assertTimeout(Duration.ofMillis(10), () -> {
                //return a string and this code run under 10ms
                return "some string result";
            });
            assertEquals("some string result", result);

            final Student student = new Student("1", "Ahmet", "Can");
            LecturerCourseRecord lecturerCourseRecord = new LecturerCourseRecord(null, null);
            assertTimeout(Duration.ofMillis(10), () -> student.addCourse(lecturerCourseRecord));

            assertTimeoutPreemptively(Duration.ofMillis(10), () -> student.addCourse(lecturerCourseRecord));
        }

        @Nested
        @DisplayName("Add Course to Student(Exceptional)")
        @Tag("exceptional")
        class AddCourseToStudentExceptionScenario {
            @Test
            @DisplayName("Got an exception when add a null lecturer course record to student")
            void throwsExceptionWhenAddToNullCourseToStudent() {

                final Student ahmet = new Student("1", "Ahmet", "Can");

                assertThatIllegalArgumentException().as("Throws IllegalArgumentException").isThrownBy(() -> ahmet.addCourse(null));

                final Throwable throwable = catchThrowable(() -> ahmet.addCourse(null));
                assertThat(throwable)
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("Can't add course with null lecturer course record")
                ;
            }
        }
    }

}
