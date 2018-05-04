package io.mucahit.course.unittest.courserecord.model;

import org.assertj.core.api.AbstractAssert;

import java.util.Objects;

/**
 * @author mucahitkurt
 * @since 29.04.2018
 */
public class StudentAssert extends AbstractAssert<StudentAssert, Student> {

    public StudentAssert(Student student) {
        super(student, StudentAssert.class);
    }

    public static StudentAssert assertThat(Student actual) {
        return new StudentAssert(actual);
    }

    public StudentAssert hasName(String name) {
        isNotNull();

        if (!Objects.equals(name, actual.getName())) {
            failWithMessage("Expected student's name %s but was found %s", name, actual.getName());
        }

        return this;
    }

}
