package io.mucahit.course.unittest.courserecord.model;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.logging.Logger;

/**
 * @author mucahitkurt
 * @since 26.04.2018
 */
public class TestLoggerExtension implements BeforeAllCallback, AfterAllCallback {

    private static Logger log = Logger.getLogger(TestLoggerExtension.class.getName());

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        log.info(String.format("Test is started...%s", context.getDisplayName()));
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        log.info(String.format("Test is ended...%s", context.getDisplayName()));

    }


}
