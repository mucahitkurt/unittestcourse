package io.mucahit.course.unittest.courserecord.model;

import org.junit.jupiter.api.extension.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;

/**
 * @author mucahitkurt
 * @since 28.04.2018
 */
public class BenchmarkExtension implements BeforeAllCallback, AfterAllCallback, BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private static final String START_TIME = "StartTime";
    private static Logger logger = Logger.getLogger(BenchmarkExtension.class.getName());

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        getStoreForContainer(context).put(START_TIME, LocalDateTime.now());
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {

        final LocalDateTime startTime = getStoreForContainer(context).remove(START_TIME, LocalDateTime.class);
        final long runTime = startTime.until(LocalDateTime.now(), ChronoUnit.MILLIS);
        logger.info(String.format("Run time for test container<%s>: %s ms", context.getRequiredTestClass().getName(), runTime));

    }

    private ExtensionContext.Store getStoreForContainer(ExtensionContext context) {
        return context.getStore(ExtensionContext.Namespace.create(getClass(), context.getRequiredTestClass()));
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        getStoreForMethod(context).put(START_TIME, LocalDateTime.now());
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        final LocalDateTime startTime = getStoreForMethod(context).remove(START_TIME, LocalDateTime.class);
        final long runTime = startTime.until(LocalDateTime.now(), ChronoUnit.MILLIS);
        logger.info(String.format("Run time for test method<%s>: %s ms", context.getRequiredTestMethod().getName(), runTime));

    }

    private ExtensionContext.Store getStoreForMethod(ExtensionContext context) {
        return context.getStore(ExtensionContext.Namespace.create(getClass(), context.getRequiredTestMethod()));
    }
}
