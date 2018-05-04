package io.mucahit.course.unittest.courserecord.application;

import io.mucahit.course.unittest.courserecord.model.Course;
import io.mucahit.course.unittest.courserecord.model.Lecturer;
import io.mucahit.course.unittest.courserecord.model.Semester;

import java.util.Optional;

/**
 * @author mucahitkurt
 * @since 30.04.2018
 */
public interface LecturerService {

    Optional<Lecturer> findLecturer(Course course, Semester semester);
}
