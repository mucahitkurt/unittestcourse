package io.mucahit.course.unittest.junit.basic.fibonacci;

/**
 * @author mucahitkurt
 * @since 15.04.2018
 */
public class FibonacciNumber {
    public int find(int order) {

        if (order <= 0) {
            throw new IllegalArgumentException();
        }

        if (order == 1 || order == 2) {
            return 1;
        }
        return find(order - 2) + find(order - 1);
    }
}
