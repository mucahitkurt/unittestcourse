package io.mucahit.course.unittest.courserecord.application;

import io.mucahit.course.unittest.courserecord.model.*;
import io.mucahit.course.unittest.mockito.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.mucahit.course.unittest.courserecord.model.StudentCourseRecord.Grade.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

/**
 * @author mucahitkurt
 * @since 30.04.2018
 */
@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private CourseService courseService;

    @Mock
    private LecturerService lecturerService;

    @Mock
    private Lecturer lecturer;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Captor
    private ArgumentCaptor<Student> studentArgumentCaptor;

    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    @Test
    void addCourse() {

        final Course course = new Course("101");
        final Semester semester = new Semester();
        when(courseService.findCourse(any())).thenReturn(Optional.of(course));
        when(lecturer.lecturerCourseRecord(course, semester)).thenReturn(new LecturerCourseRecord(course, semester));
        when(lecturerService.findLecturer(course, semester)).thenReturn(Optional.of(lecturer));
        final Student studentAhmet = new Student("id1", "Ahmet", "Yilmaz");
        when(studentRepository.findById(anyString()))
                .thenReturn(Optional.of(studentAhmet)).
                thenThrow(new IllegalArgumentException("Can't find a student"))
                .thenReturn(Optional.of(studentAhmet));

        studentService.addCourse("id1", course, semester);

        assertThatThrownBy(() -> studentService.findStudent("id1")).isInstanceOf(IllegalArgumentException.class);

        final Optional<Student> studentOptional = studentService.findStudent("id1");

        assertThat(studentOptional).as("Student")
                .isPresent()
                .get()
                .matches(student -> student.isTakeCourse(course))
        ;

        verify(courseService).findCourse(course);
        verify(courseService, times(1)).findCourse(course);
        verify(courseService, atLeast(1)).findCourse(course);
        verify(courseService, atMost(1)).findCourse(course);

        verify(studentRepository, times(3)).findById("id1");

        verify(lecturerService).findLecturer(any(Course.class), any(Semester.class));

        verify(lecturer).lecturerCourseRecord(argThat(argument -> argument.getCode().equals("101")), any(Semester.class));
        verify(lecturer).lecturerCourseRecord(argThat(new MyCourseArgumentMatcher()), any(Semester.class));

    }

    @Test
    void dropCourse() {

        final Course course = new Course("101");

        when(courseService.findCourse(course))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(course));
        final LecturerCourseRecord lecturerCourseRecord = new LecturerCourseRecord(course, new Semester());
        when(lecturer.lecturerCourseRecord(eq(course), any(Semester.class))).thenReturn(lecturerCourseRecord);
        when(lecturerService.findLecturer(eq(course), any(Semester.class)))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(lecturer));
        final Student student = mock(Student.class);
        when(studentRepository.findById("id1"))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(student));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> studentService.dropCourse("id1", course))
                .withMessageContaining("Can't find a student");

        assertThatIllegalArgumentException()
                .isThrownBy(() -> studentService.dropCourse("id1", course))
                .withMessageContaining("Can't find a course");

        assertThatIllegalArgumentException()
                .isThrownBy(() -> studentService.dropCourse("id1", course))
                .withMessageContaining("Can't find a lecturer");

        studentService.dropCourse("id1", course);

        InOrder inOrder = inOrder(studentRepository, courseService, lecturerService, lecturer, student);

        inOrder.verify(studentRepository, times(2)).findById("id1");
        inOrder.verify(courseService, times(1)).findCourse(course);

        inOrder.verify(studentRepository, times(1)).findById("id1");
        inOrder.verify(courseService, times(1)).findCourse(course);
        inOrder.verify(lecturerService, times(1)).findLecturer(eq(course), any(Semester.class));

        inOrder.verify(studentRepository, times(1)).findById("id1");
        inOrder.verify(courseService, times(1)).findCourse(course);
        inOrder.verify(lecturerService, times(1)).findLecturer(eq(course), any(Semester.class));
        inOrder.verify(lecturer).lecturerCourseRecord(argThat(argument -> argument.getCode().equals("101")), any(Semester.class));
        inOrder.verify(student).dropCourse(lecturerCourseRecord);
        inOrder.verify(studentRepository).save(student);

        verifyNoMoreInteractions(studentRepository, courseService, lecturerService, lecturer, student);
        verifyZeroInteractions(studentRepository, courseService, lecturerService, lecturer, student);
    }

    @Test
    void deleteStudent() {

        final Student student = new Student("id1", "Ahmet", "Yilmaz");
        when(studentRepository.findById("id1")).thenAnswer(invocation -> Optional.of(student));

        doNothing()
                .doThrow(new IllegalArgumentException("There is no student in repo"))
                .doAnswer(invocation -> {
                    final Student studentAhmet = invocation.getArgument(0);
                    System.out.println(String.format("Student<%s> will be deleted!", studentAhmet));
                    return null;
                })
                .when(studentRepository).delete(student);

        studentService.deleteStudent("id1");
        assertThatIllegalArgumentException().isThrownBy(() -> studentService.deleteStudent("id1"))
                .withMessageContaining("no student");
        studentService.deleteStudent("id1");

        verify(studentRepository, times(3)).findById(stringArgumentCaptor.capture());
        verify(studentRepository, times(3)).delete(studentArgumentCaptor.capture());

        assertThat(stringArgumentCaptor.getAllValues())
                .hasSize(3)
                .containsOnly("id1", "id1", "id1");

        assertThat(studentArgumentCaptor.getAllValues())
                .hasSize(3)
                .extracting(Student::getId)
                .containsOnly("id1", "id1", "id1")
        ;
    }

    @Test
    void getTranscriptOfAStudent() {

        final List<StudentCourseRecord.Grade> grades = Arrays.asList(StudentCourseRecord.Grade.values());

        final Student student = mock(Student.class);
        when(studentRepository.findById("id1")).thenReturn(Optional.of(student));
        when(student.getStudentCourseRecords()).thenAnswer(invocation -> {
            final Semester semester = new Semester(LocalDate.of(2015, 1, 1));
            return Stream.of("101", "103", "105", "107", "109")
                    .map(Course::new)
                    .map(course -> new LecturerCourseRecord(course, semester))
                    .peek(lecturerCourseRecord -> lecturerCourseRecord.setCredit(new Random().nextInt(3)))
                    .map(StudentCourseRecord::new)
                    .peek(studentCourseRecord -> {
                        Collections.shuffle(grades);
                        studentCourseRecord.setGrade(grades.get(0));
                    })
                    .collect(Collectors.toSet());
        });

        assertThat(studentService.transcript("id1"))
                .hasSize(5)
                .extracting(TranscriptItem::getCourse)
                .extracting(Course::getCode)
                .containsOnly("101", "103", "105", "107", "109")
        ;

        assertThat(studentService.transcript("id1"))
                .extracting(TranscriptItem::getCredit)
                .containsAnyOf(0, 1, 2, 3)
        ;

        assertThat(studentService.transcript("id1"))
                .extracting(TranscriptItem::getGrade)
                .containsAnyOf(A1, A2, B1, B2, C, D, E, F)
        ;

        assertThat(studentService.transcript("id1"))
                .filteredOn(transcriptItem -> transcriptItem.getCourse().getCode().equals("101"))
                .extracting(TranscriptItem::getCourse, TranscriptItem::getSemester)
                .containsOnly(
                        tuple(new Course("101"), new Semester(LocalDate.of(2015, 1, 1)))
                );

        verify(studentRepository, times(4)).findById("id1");
        verify(student, times(4)).getStudentCourseRecords();

    }

    @Test
    void addCourseWithSpyStudent() {

        final Course course = new Course("101");
        final Semester semester = new Semester();
        when(courseService.findCourse(any())).thenReturn(Optional.of(course));
        when(lecturer.lecturerCourseRecord(course, semester)).thenReturn(new LecturerCourseRecord(course, semester));
        when(lecturerService.findLecturer(course, semester)).thenReturn(Optional.of(lecturer));
        final Student studentReal = new Student("id1", "Ahmet", "Yilmaz");
        final Student studentAhmet = spy(studentReal);

//        doThrow(new IllegalArgumentException("Spy failed!")).when(studentAhmet).addCourse(any(LecturerCourseRecord.class));

        doReturn(BigDecimal.ONE).when(studentAhmet).gpa();

        when(studentRepository.findById(anyString()))
                .thenReturn(Optional.of(studentAhmet)).
                thenThrow(new IllegalArgumentException("Can't find a student"))
                .thenReturn(Optional.of(studentAhmet));

        studentService.addCourse("id1", course, semester);

        assertThat(studentAhmet)
                .matches(student -> student.isTakeCourse(course));

        assertThat(studentReal)
                .matches(student -> student.isTakeCourse(course));

        studentAhmet.setBirthDate(LocalDate.of(1990, 1, 1));
        assertThat(studentAhmet.getBirthDate()).isNotNull();
        assertThat(studentReal.getBirthDate()).isNull();

        assertThatThrownBy(() -> studentService.findStudent("id1")).isInstanceOf(IllegalArgumentException.class);

        final Optional<Student> studentOptional = studentService.findStudent("id1");

        assertThat(studentOptional).as("Student")
                .isPresent()
                .get()
                .matches(student -> student.isTakeCourse(course))
        ;

        verify(courseService).findCourse(course);
        verify(courseService, times(1)).findCourse(course);
        verify(courseService, atLeast(1)).findCourse(course);
        verify(courseService, atMost(1)).findCourse(course);

        verify(studentRepository, times(3)).findById("id1");

        verify(lecturerService).findLecturer(any(Course.class), any(Semester.class));

        verify(lecturer).lecturerCourseRecord(argThat(argument -> argument.getCode().equals("101")), any(Semester.class));
        verify(lecturer).lecturerCourseRecord(argThat(new MyCourseArgumentMatcher()), any(Semester.class));
    }

    @Test
    void addCourseWithPartialMock() {

        final Course course = new Course("101");
        final Semester semester = new Semester();
        when(courseService.findCourse(any())).thenReturn(Optional.of(course));
        when(lecturer.lecturerCourseRecord(course, semester)).thenReturn(new LecturerCourseRecord(course, semester));
        when(lecturerService.findLecturer(course, semester)).thenReturn(Optional.of(lecturer));
        final Student studentAhmet = mock(Student.class);
        when(studentAhmet.isTakeCourse(any(Course.class))).thenReturn(true);
        when(studentRepository.findById(anyString()))
                .thenReturn(Optional.of(studentAhmet)).
                thenThrow(new IllegalArgumentException("Can't find a student"))
                .thenReturn(Optional.of(studentAhmet));

        assertThat(studentAhmet.gpa()).isNull();
        doCallRealMethod().when(studentAhmet).gpa();
//        when().thenCallRealMethod();
        assertThatNullPointerException().isThrownBy(studentAhmet::gpa);

        studentService.addCourse("id1", course, semester);

        assertThatThrownBy(() -> studentService.findStudent("id1")).isInstanceOf(IllegalArgumentException.class);

        final Optional<Student> studentOptional = studentService.findStudent("id1");

        assertThat(studentOptional).as("Student")
                .isPresent()
                .get()
                .matches(student -> student.isTakeCourse(course))
        ;

        verify(courseService).findCourse(course);
        verify(courseService, times(1)).findCourse(course);
        verify(courseService, atLeast(1)).findCourse(course);
        verify(courseService, atMost(1)).findCourse(course);

        verify(studentRepository, times(3)).findById("id1");

        verify(lecturerService).findLecturer(any(Course.class), any(Semester.class));

        verify(lecturer).lecturerCourseRecord(argThat(argument -> argument.getCode().equals("101")), any(Semester.class));
        verify(lecturer).lecturerCourseRecord(argThat(new MyCourseArgumentMatcher()), any(Semester.class));

    }

    @Test
    void addCourseWithBDD() {

        final Course course = new Course("101");
        final Semester semester = new Semester();
        given(courseService.findCourse(any())).willReturn(Optional.of(course));
        given(lecturer.lecturerCourseRecord(course, semester)).willReturn(new LecturerCourseRecord(course, semester));
        given(lecturerService.findLecturer(course, semester)).willReturn(Optional.of(lecturer));
        final Student studentAhmet = new Student("id1", "Ahmet", "Yilmaz");
        given(studentRepository.findById(anyString()))
                .willReturn(Optional.of(studentAhmet)).
                willThrow(new IllegalArgumentException("Can't find a student"))
                .willReturn(Optional.of(studentAhmet));

        studentService.addCourse("id1", course, semester);

        assertThatThrownBy(() -> studentService.findStudent("id1")).isInstanceOf(IllegalArgumentException.class);

        final Optional<Student> studentOptional = studentService.findStudent("id1");

        assertThat(studentOptional).as("Student")
                .isPresent()
                .get()
                .matches(student -> student.isTakeCourse(course))
        ;

        then(courseService).should().findCourse(course);
        then(courseService).should(times(1)).findCourse(course);
        then(courseService).should(atLeast(1)).findCourse(course);
        then(courseService).should(atMost(1)).findCourse(course);

        then(studentRepository).should(times(3)).findById("id1");

        then(lecturerService).should().findLecturer(any(Course.class), any(Semester.class));

        then(lecturer).should().lecturerCourseRecord(argThat(argument -> argument.getCode().equals("101")), any(Semester.class));
        then(lecturer).should().lecturerCourseRecord(argThat(new MyCourseArgumentMatcher()), any(Semester.class));
    }

    class MyCourseArgumentMatcher implements ArgumentMatcher<Course> {
        @Override
        public boolean matches(Course course) {
            return course.getCode().equals("101");
        }
    }

}