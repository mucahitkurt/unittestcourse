package io.mucahit.course.unittest.courserecord.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author mucahitkurt
 * @since 28.04.2018
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StudentTestWithTemplate {

    private Student student;

    @BeforeAll
    void setUp() {
        student = new Student("id1", "Ahmet", "Yilmaz");
    }

    @ExtendWith(RepeatedStudentTestTemplateInvocationContextProvider.class)
    @TestTemplate
    void addCourseToStudent(LecturerCourseRecord lecturerCourseRecord, int numberOfInvocation) {

        student.addCourse(lecturerCourseRecord);
        assertEquals(numberOfInvocation, student.getStudentCourseRecords().size());
    }
}
