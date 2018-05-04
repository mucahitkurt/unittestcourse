package io.mucahit.course.unittest.courserecord.model;

import io.mucahit.course.unittest.courserecord.model.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author mucahitkurt
 * @since 19.04.2018
 */
public class StudentTestWithTestLifecyle {

    private Student mehmet = new Student("id1", "Mehmet", "Yilmaz");

    @BeforeAll
    static void  setUp() {

    }

    @Test
    void stateCannotChangeWhenLifecyleIsPerMethod() {
        assertEquals("Mehmet", mehmet.getName());
        mehmet = new Student("id2", "Ahmet", "Yilmaz");
    }

    @Test
    void stateCanChangeWhenLifecyleIsPerClass() {
        assertEquals("Mehmet", mehmet.getName());
        mehmet = new Student("id2", "Ahmet", "Yilmaz");
    }

}
