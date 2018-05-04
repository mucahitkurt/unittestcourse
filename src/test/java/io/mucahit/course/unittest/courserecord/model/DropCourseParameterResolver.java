package io.mucahit.course.unittest.courserecord.model;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author mucahitkurt
 * @since 28.04.2018
 */
public class DropCourseParameterResolver implements ParameterResolver {

    private static Logger logger = Logger.getLogger(DropCourseParameterResolver.class.getName());

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {

        if (parameterContext.getParameter().getType() == Student.class && parameterContext.getIndex() == 1) {
            return true;
        }

        return parameterContext.getParameter().getType() == Semester.class && List.of(2, 3, 4).contains(parameterContext.getIndex());
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {

        logger.info("Provide parameter " + parameterContext.getParameter().getName());

        if (parameterContext.getParameter().getType() == Student.class) {
            return new Student("id1", "Ahmet", "Yilmaz");
        }

        if (parameterContext.getParameter().getType() == Semester.class) {
            switch (parameterContext.getIndex()) {
                case 2:
                    return addDropPeriodOpenSemester();
                case 3:
                    return addDropPeriodClosedSemester();
                case 4:
                    return notActiveSemester();
            }
        }

        throw new IllegalArgumentException("Could't create not supported parameter");
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
