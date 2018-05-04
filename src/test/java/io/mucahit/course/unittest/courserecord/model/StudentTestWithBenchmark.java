package io.mucahit.course.unittest.courserecord.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author mucahitkurt
 * @since 28.04.2018
 */
public class StudentTestWithBenchmark {

    @ExtendWith(BenchmarkExtension.class)
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
                assertThrows(IllegalArgumentException.class, () -> ahmet.addCourse(null));
                assertThrows(IllegalArgumentException.class, () -> ahmet.addCourse(null), "Throws IllegalArgumentException");
                final IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> ahmet.addCourse(null));
                assertEquals("Can't add course with null lecturer course record", illegalArgumentException.getMessage());
            }
        }
    }
}
