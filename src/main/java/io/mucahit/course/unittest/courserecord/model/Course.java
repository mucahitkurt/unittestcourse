package io.mucahit.course.unittest.courserecord.model;

/**
 * @author mucahitkurt
 * @since 12.04.2018
 */
public class Course {

    private String code;
    private String name;
    private String description;
    private CourseType courseType;
    private int credit;
    private Department department;


    public Course() {
    }

    public Course(String code) {
        this.code = code;
    }

    public static Course createNewCourse(String courseCode) {
        return new Course(courseCode);
    }

    public enum CourseType {
        MANDATORY,
        ELECTIVE
    }

    public static CourseBuilder newCourse() {
        return new CourseBuilder(new Course());
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object obj) {

        if (!Course.class.isInstance(obj)) {
            return false;
        }

        final Course course2 = (Course) obj;
        return course2.getCode().equals(this.getCode());
    }

    @Override
    public int hashCode() {
        return 31 * this.getCode().hashCode();
    }

    @Override
    public String toString() {
        return code + "-" + name;
    }
}

