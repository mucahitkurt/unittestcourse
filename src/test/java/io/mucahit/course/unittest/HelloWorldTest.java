package io.mucahit.course.unittest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author mucahitkurt
 * @since 4.04.2018
 */
public class HelloWorldTest {

    @Test
    void shouldSayHelloToWorld() {
        HelloWorld helloWorld = new HelloWorld();
        Assertions.assertEquals("Hello World", helloWorld.sayHelloToWorld());
    }
}
