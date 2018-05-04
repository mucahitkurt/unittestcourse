package io.mucahit.course.unittest.courserecord.model;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

import java.util.logging.Logger;

/**
 * @author mucahitkurt
 * @since 28.04.2018
 */
public class IllegalArgumentExceptionHandlerExtension implements TestExecutionExceptionHandler {

    private static Logger logger = Logger.getLogger(IllegalArgumentExceptionHandlerExtension.class.getName());

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {

        logger.severe(String.format("IllegalArgumentException was thrown by a method %s with description: %s", context.getRequiredTestMethod().getName(), throwable.getMessage()));
        throw throwable;
    }
}
