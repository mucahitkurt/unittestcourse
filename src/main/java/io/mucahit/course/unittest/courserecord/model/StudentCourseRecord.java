package io.mucahit.course.unittest.courserecord.model;

import java.math.BigDecimal;

/**
 * @author mucahitkurt
 * @since 12.04.2018
 */
public class StudentCourseRecord {

    private final LecturerCourseRecord lecturerCourseRecord;
    private Grade grade;
    private CourseReview courseReview;
    private Student student;

    public StudentCourseRecord(LecturerCourseRecord lecturerCourseRecord) {
        this.lecturerCourseRecord = lecturerCourseRecord;
    }

    public LecturerCourseRecord getLecturerCourseRecord() {
        return lecturerCourseRecord;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Grade getGrade() {
        return grade;
    }

    public enum Grade {

        A1(BigDecimal.valueOf(4)),
        A2(BigDecimal.valueOf(3.5)),
        B1(BigDecimal.valueOf(3)),
        B2(BigDecimal.valueOf(2.5)),
        C(BigDecimal.valueOf(2)),
        D(BigDecimal.valueOf(1.5)),
        E(BigDecimal.ONE),
        F(BigDecimal.ZERO);

        private BigDecimal gradeInNumber;

        Grade(BigDecimal gradeInNumber) {
            this.gradeInNumber = gradeInNumber;
        }

        public BigDecimal getGradeInNumber() {
            return gradeInNumber;
        }
    }
}
