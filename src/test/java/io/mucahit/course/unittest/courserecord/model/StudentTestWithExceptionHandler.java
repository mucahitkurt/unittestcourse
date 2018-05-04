package io.mucahit.course.unittest.courserecord.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * @author mucahitkurt
 * @since 28.04.2018
 */
public class StudentTestWithExceptionHandler {

    @ExtendWith(IllegalArgumentExceptionHandlerExtension.class)
    @Nested
    @DisplayName("Add Course to Student(Exceptional)")
    @Tag("exceptional")
    class AddCourseToStudentExceptionScenario {
        @Test
        @DisplayName("Got an exception when add a null lecturer course record to student")
        void throwsExceptionWhenAddToNullCourseToStudent() {

            final Student ahmet = new Student("1", "Ahmet", "Can");
            ahmet.addCourse(null);
        }
    }
}
