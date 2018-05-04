package io.mucahit.course.unittest.courserecord.application;

import io.mucahit.course.unittest.courserecord.model.Course;
import io.mucahit.course.unittest.courserecord.model.Lecturer;
import io.mucahit.course.unittest.courserecord.model.LecturerRepository;
import io.mucahit.course.unittest.courserecord.model.Semester;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author mucahitkurt
 * @since 30.04.2018
 */
class LecturerServiceTest {

    @Test
    void findLecturer() {
        final Course course = new Course("101");
        final Semester semester = new Semester();

        final LecturerRepository lecturerRepository = Mockito.mock(LecturerRepository.class);
        final Lecturer lecturer = new Lecturer();
        Mockito.when(lecturerRepository.findByCourseAndSemester(course, semester)).thenReturn(Optional.of(lecturer));

        final LecturerService lecturerService = new LecturerServiceImpl(lecturerRepository);
        final Optional<Lecturer> lecturerOpt = lecturerService.findLecturer(course, semester);
        assertThat(lecturerOpt)
                .isPresent()
                .get()
                .isSameAs(lecturer)
        ;

        Mockito.verify(lecturerRepository).findByCourseAndSemester(course, semester);
    }
}