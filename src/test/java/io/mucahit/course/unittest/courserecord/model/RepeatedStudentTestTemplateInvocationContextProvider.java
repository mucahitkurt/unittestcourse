package io.mucahit.course.unittest.courserecord.model;

import org.junit.jupiter.api.extension.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author mucahitkurt
 * @since 28.04.2018
 */
public class RepeatedStudentTestTemplateInvocationContextProvider implements TestTemplateInvocationContextProvider {

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return context.getRequiredTestClass() == StudentTestWithTemplate.class;
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
        return Stream.of(
                testTemplateInvocationContext("101", 1),
                testTemplateInvocationContext("103", 2),
                testTemplateInvocationContext("105", 3),
                testTemplateInvocationContext("107", 4),
                testTemplateInvocationContext("109", 5)

        );
    }

    private TestTemplateInvocationContext testTemplateInvocationContext(String courseCode, int numberOfInvocation) {

        return new TestTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invocationIndex) {
                return "Add Course to Student ==> Add one course to student and student has " + invocationIndex + " courses";
            }

            @Override
            public List<Extension> getAdditionalExtensions() {
                return Collections.singletonList(new ParameterResolver() {
                    @Override
                    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {

                        if (parameterContext.getIndex() == 0 && parameterContext.getParameter().getType() == LecturerCourseRecord.class) {
                            return true;
                        }

                        return parameterContext.getIndex() == 1 && parameterContext.getParameter().getType() == int.class;
                    }

                    @Override
                    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {

                        if (parameterContext.getParameter().getType() == LecturerCourseRecord.class) {
                           return new LecturerCourseRecord(new Course(courseCode), new Semester());
                        }

                        if (parameterContext.getParameter().getType() == int.class) {
                            return numberOfInvocation;
                        }

                        throw new IllegalArgumentException("not supporter parameter");
                    }
                });
            }
        };
    }
}
