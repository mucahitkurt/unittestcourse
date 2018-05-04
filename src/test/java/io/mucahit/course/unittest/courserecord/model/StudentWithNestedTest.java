package io.mucahit.course.unittest.courserecord.model;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

/**
 * @author mucahitkurt
 * @since 12.04.2018
 */
//@ExtendWith(DropCourseConditionExtension.class)
@Tag("student")
@DisplayName("Student Test With Nested Tests")
public class StudentWithNestedTest {

//    @RegisterExtension
//    static TestLoggerExtension testLoggerExtension = new TestLoggerExtension();

    @Nested
    @DisplayName("Create Student")
    @Tag("createStudent")
    class CreateStudent {

        @Test
        @DisplayName("Test every student must have an id, name and surname")
        void shouldCreateStudentWithIdNameAndSurname() {

            /**
             * Assertions.*
             * assertEquals
             * assertEquals with message
             * assertNotEquals
             * assertTrue with lazily evaluated message
             * assertFalse with boolean supplier
             * assertNull
             * assertNotNull
             * assertArrayEquals
             * assertSame
             */

            Student mucahit = new Student("1", "Mucahit", "Kurt");

            assertEquals("Mucahit", mucahit.getName()); // "Mucahit".equals(mucahit.getName())
            assertEquals("Mucahit", mucahit.getName(), "Student's name");
            assertNotEquals("Mucahitt", mucahit.getName(), "Student's name");

            assertTrue(mucahit.getName().startsWith("M"));
            assertTrue(mucahit.getName().startsWith("M"), () -> "Student's name " + "starts with Mu");
            assertFalse(() -> {
                Student mehmet = new Student("id1", "Mehmet", "Can");
                return mehmet.getName().endsWith("M");
            }, () -> "Student's name " + "ends with M");

            final Student ahmet = new Student("2", "Ahmet", "Yilmaz");

            assertArrayEquals(new String[]{"Mucahit", "Ahmet"}, Stream.of(mucahit, ahmet).map(Student::getName).toArray());

            Student student = mucahit;

            assertSame(mucahit, student); // mucahit == student
            assertNotSame(ahmet, student);
        }

        @Test
        @DisplayName("Test every student must have an id, name and surname with grouped assertions")
        void shouldCreateStudentWithIdNameAndSurnameWithGroupedAssertions() {

            /**
             * grouped assertions
             * failed grouped assertions
             * dependent assertions
             */

            // In a grouped assertion all assertions are executed,
            Student mucahit = new Student("1", "Mucahit", "Kurt");

            assertAll("Student's name check",
                    () -> assertEquals("Mucahit", mucahit.getName()),
                    () -> assertEquals("Mucahit", mucahit.getName(), "Student's name"),
                    () -> assertNotEquals("Mucahitt", mucahit.getName(), "Student's name")
            );

            // and any failures will be reported together.
            assertAll("Student's name character check",
                    () -> assertTrue(mucahit.getName().startsWith("M")),
                    () -> assertTrue(mucahit.getName().startsWith("M"), () -> "Student's name " + "starts with Mu"),
                    () -> assertFalse(() -> {
                        Student mehmet = new Student("id1", "Mehmet", "Can");
                        return mehmet.getName().endsWith("M");
                    }, () -> "Student's name " + "ends with M")
            );

            //dependent assertions
            assertAll(() -> {
                        final Student ahmet = new Student("2", "Ahmet", "Yilmaz");

                        assertArrayEquals(new String[]{"Mucahit", "Ahmet"}, Stream.of(mucahit, ahmet).map(Student::getName).toArray());
                    },
                    () -> {
                        Student student = mucahit;
                        final Student ahmet = new Student("2", "Ahmet", "Yilmaz");

                        assertSame(mucahit, student); // mucahit == student
                        assertNotSame(student, ahmet);
                    });
        }

        @Test
        @DisplayName("Test student creation at only development machine")
        void shouldCreateStudentWithNameAndSurnameAtDevelopmentMachine() {

            assumeTrue(System.getProperty("ENV") != null, "Aborting Test: System property ENV doesn't exist!");
            assumeTrue(System.getProperty("ENV").equals("dev"), "Aborting Test: Not on a developer machine!");

            final Student ahmet = new Student("1", "Ahmet", "Can");
            assertAll("Student Information",
                    () -> assertEquals("Ahmet", ahmet.getName()),
                    () -> assertEquals("Can", ahmet.getSurname()),
                    () -> assertEquals("1", ahmet.getId())
            );
        }

        @Test
        @DisplayName("Test student creation at different environments")
        void shouldCreateStudentWithNameAndSurnameWithSpecificEnvironment() {

            final Student ahmet = new Student("1", "Ahmet", "Can");

            final String env = System.getProperty("ENV");
            assumingThat(env != null && env.equals("dev"), () -> {
                LecturerCourseRecord lecturerCourseRecord = new LecturerCourseRecord(null, null);
                ahmet.addCourse(lecturerCourseRecord);
                assertEquals(1, ahmet.getStudentCourseRecords().size());
            });

            assertAll("Student Information",
                    () -> assertEquals("Ahmet", ahmet.getName()),
                    () -> assertEquals("Can", ahmet.getSurname()),
                    () -> assertEquals("1", ahmet.getId())
            );
        }

        @Disabled("No more valid scenario")
        @Test
        @DisplayName("Test that student must have only number id")
        void shouldCreateStudentWithNumberId() {
            assertThrows(IllegalArgumentException.class, () -> new Student("id", "Ahmet", "Can"));
        }
    }

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

    @Nested
    @DisplayName("Drop Course from Student")
    @Tag("dropCourse")
    class DropCourseFromStudent {

        //throws illegal argument exception for null lecturer course record
        //throws illegal argument exception if the student did't register course before
        //throws not active semester exception if the semester is not active
        //throws not active semester exception if the add drop period is closed for the semester
        //drop course from student

        @TestFactory
        Stream<DynamicTest> dropCourseFromStudentFactory() {
            final Student studentAhmet = new Student("id1", "Ahmet", "Yilmaz");

            return Stream.of(
                    dynamicTest("throws illegal argument exception for null lecturer course record", () -> {
                        assertThrows(IllegalArgumentException.class, () -> studentAhmet.dropCourse(null));
                    }),
                    dynamicTest("throws illegal argument exception if the student did't register course before", () -> {
                        final LecturerCourseRecord lecturerCourseRecord = new LecturerCourseRecord(new Course("101"), new Semester());
                        assertThrows(IllegalArgumentException.class, () -> studentAhmet.dropCourse(lecturerCourseRecord));
                    }),
                    dynamicTest("throws not active semester exception if the semester is not active", () -> {
                        final Semester notActiveSemester = notActiveSemester();
                        assumeTrue(!notActiveSemester.isActive());
                        final LecturerCourseRecord lecturerCourseRecord = new LecturerCourseRecord(new Course("101"), notActiveSemester);
                        Student studentMehmet = new Student("id1", "Mehmet", "Yilmaz", Set.of(new StudentCourseRecord(lecturerCourseRecord)));
                        assertThrows(NotActiveSemesterException.class, () -> studentMehmet.dropCourse(lecturerCourseRecord));
                    }),
                    dynamicTest("throws not active semester exception if the add drop period is closed for the semester", () -> {
                        final Semester addDropPeriodClosedSemester = addDropPeriodClosedSemester();
                        assumeTrue(!addDropPeriodClosedSemester.isAddDropAllowed());
                        final LecturerCourseRecord lecturerCourseRecord = new LecturerCourseRecord(new Course("101"), addDropPeriodClosedSemester);
                        Student studentMehmet = new Student("id1", "Mehmet", "Yilmaz", Set.of(new StudentCourseRecord(lecturerCourseRecord)));
                        assertThrows(NotActiveSemesterException.class, () -> studentMehmet.dropCourse(lecturerCourseRecord));
                    }),
                    dynamicTest("drop course from student", () -> {
                        final Semester addDropPeriodOpenSemester = addDropPeriodOpenSemester();
                        assumeTrue(addDropPeriodOpenSemester.isAddDropAllowed());
                        final LecturerCourseRecord lecturerCourseRecord = new LecturerCourseRecord(new Course("101"), addDropPeriodOpenSemester);
                        Student studentMehmet = new Student("id1", "Mehmet", "Yilmaz", Set.of(new StudentCourseRecord(lecturerCourseRecord)));
                        assertEquals(1, studentMehmet.getStudentCourseRecords().size());
                        studentMehmet.dropCourse(lecturerCourseRecord);
                        assertTrue(studentMehmet.getStudentCourseRecords().isEmpty());
                    })
            );
        }

        private Semester addDropPeriodOpenSemester() {
            final Semester activeSemester = new Semester();
            final LocalDate semesterDate = LocalDate.of(activeSemester.getYear(), activeSemester.getTerm().getStartMonth(), 1);
            final LocalDate now = LocalDate.now();
            activeSemester.setAddDropPeriodInWeek(Long.valueOf(semesterDate.until(now, ChronoUnit.WEEKS) + 1).intValue());
            return activeSemester;
        }

        private Semester addDropPeriodClosedSemester() {
            final Semester activeSemester = new Semester();
            activeSemester.setAddDropPeriodInWeek(0);
            if (LocalDate.now().getDayOfMonth() == 1) {
                activeSemester.setAddDropPeriodInWeek(-1);
            }
            return activeSemester;
        }

        private Semester notActiveSemester() {
            final Semester activeSemester = new Semester();
            return new Semester(LocalDate.of(activeSemester.getYear() - 1, 1, 1));
        }

    }

    @ExtendWith(ParameterResolverForGpaCalculation.class)
    @Nested
    @DisplayName("Calculate Gpa for a Student")
    @Tag("calculateGpa")
    class CalculateGpa {

        @Test
        @DisplayName("Calculate Gpa for a Student")
        void calculateGpa(Student student, Map<LecturerCourseRecord, StudentCourseRecord.Grade> lecturerCourseRecordGradeMap, BigDecimal expectedGpa) {

            lecturerCourseRecordGradeMap.forEach((lecturerCourseRecord, grade) -> {
                student.addCourse(lecturerCourseRecord);
                student.addGrade(lecturerCourseRecord, grade);
            });
            assertEquals(expectedGpa, student.gpa());
        }
    }

}
