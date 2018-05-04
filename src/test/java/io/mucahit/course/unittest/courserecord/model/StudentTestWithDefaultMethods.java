package io.mucahit.course.unittest.courserecord.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author mucahitkurt
 * @since 22.04.2018
 */
public class StudentTestWithDefaultMethods implements CreateDomain<Student>, TestLifecycleReporter {

    @Override
    public Student createDomain() {
        return new Student("id1","Ahmet","Yilmaz");
    }

    @Test
    void createStudent() {
        final Student student = createDomain();

        assertAll("Student",
                () -> assertEquals("id1", student.getId()),
                () -> assertEquals("Ahmet", student.getName()),
                () -> assertEquals("Yilmaz", student.getSurname())
                );
    }
}
