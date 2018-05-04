package io.mucahit.course.unittest.courserecord.application;

import io.mucahit.course.unittest.courserecord.model.Course;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author mucahitkurt
 * @since 3.05.2018
 */
class CourseServiceTest {

    @Test
    void findCourse() {
        final CourseService mock = Mockito.mock(CourseService.class);
        mock.findCourse(new Course());
        mock.findCourse(new Course());
        mock.findCourse(new Course());
        mock.findCourse(new Course());
    }

    @Test
    void anotherTestMethod() {
        String string = "Mucahit";
        System.out.println(string);
    }

}