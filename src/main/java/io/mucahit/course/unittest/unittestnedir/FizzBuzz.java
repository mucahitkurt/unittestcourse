package io.mucahit.course.unittest.unittestnedir;

/**
 * @author mucahitkurt
 * @since 7.04.2018
 */
public class FizzBuzz {
    public String stringFor(int i) {

        if (i < 1 || i > 100) {
            throw new IllegalArgumentException();
        }

        if (i % 15 == 0) {
            return "FizzBuzz";
        } else if (i % 3 == 0) {
            return "Fizz";
        } else if (i % 5 == 0) {
            return "Buzz";
        }
        return String.valueOf(i);
    }
}
