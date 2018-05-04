package io.mucahit.course.unittest.courserecord.model;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.List;

/**
 * @author mucahitkurt
 * @since 27.04.2018
 */
public class DropCourseConditionExtension implements ExecutionCondition {

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {

        if (List.of("student").containsAll(context.getTags()) || List.of("student", "dropCourse").containsAll(context.getTags())) {
            return ConditionEvaluationResult.enabled("Drop course is enabled");
        }

        return ConditionEvaluationResult.disabled("Only drop course allowed to run");
    }
}
