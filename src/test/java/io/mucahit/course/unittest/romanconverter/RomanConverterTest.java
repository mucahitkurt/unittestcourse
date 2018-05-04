package io.mucahit.course.unittest.romanconverter;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author mucahitkurt
 * @since 18.03.2018
 */
public class RomanConverterTest {

    @ParameterizedTest(name = "Convert {0} to Roman")
    @MethodSource
    void convertToRoman(int number, String expectation) {
        assertEquals(expectation, RomanConverter.convert(number));
    }

    static Stream<Arguments> convertToRoman() {
        return Stream.of(
                Arguments.of(1, "I")
                ,Arguments.of(2, "II")
                ,Arguments.of(3, "III")
                ,Arguments.of(4, "IV")
                ,Arguments.of(5, "V")
                ,Arguments.of(6, "VI")
                ,Arguments.of(7, "VII")
                ,Arguments.of(8, "VIII")
                ,Arguments.of(9, "IX")
                ,Arguments.of(10, "X")
                ,Arguments.of(15, "XV")
                ,Arguments.of(20, "XX")
                ,Arguments.of(23, "XXIII")
                ,Arguments.of(25, "XXV")
                ,Arguments.of(40, "XL")
                ,Arguments.of(50, "L")
                ,Arguments.of(89, "LXXXIX")
        );
    }
}
