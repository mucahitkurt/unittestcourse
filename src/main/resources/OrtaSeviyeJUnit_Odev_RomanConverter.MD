#1, 2, 3 icin
##Test Kodu
```
@ParameterizedTest(name = "Convert {0} to Roman")
@MethodSource
void convertToRoman(int number, String expectation) {
    assertEquals(expectation, RomanConverter.convert(number));
}

static Stream<Arguments> convertToRoman() {
    return Stream.of(
            Arguments.of(1, "I"),
            Arguments.of(2, "II"),
            Arguments.of(3, "III")
    );
}
```


##Production Kodu
```
public static String convert(int number) {

    StringBuilder result = new StringBuilder();
    while (number > 0) {
        result.append("I");
        number--;
    }
    return result.toString();
}
```

#5,6,7,8
##Production Kodu
```
public static String convert(int number) {

    StringBuilder result = new StringBuilder();

    if (number >= 5) {
        result.append("V");
        number -= 5;
    }

    while (number > 0) {
        result.append("I");
        number--;
    }
    return result.toString();
}
```

#10, 15, 20
##Production Kodu
```
public static String convert(int number) {

    StringBuilder result = new StringBuilder();

    while (number >= 10) {
        result.append("X");
        number -= 10;
    }

    if (number >= 5) {
        result.append("V");
        number -= 5;
    }

    while (number > 0) {
        result.append("I");
        number--;
    }
    return result.toString();
}
```

#10, 15, 20
##Production Kodu
```
private static Map<Integer, String> romanDigitMap = Map.of(
        10, "X",
        5, "V",
        1, "I"
);

public static String convert(int number) {

    StringBuilder result = new StringBuilder();
    AtomicInteger atomicNumber = new AtomicInteger(number);
    romanDigitMap.keySet().stream().sorted(Comparator.reverseOrder())
            .forEach(numberForRomanDigit -> {
                while (atomicNumber.get() >= numberForRomanDigit) {
                    result.append(romanDigitMap.get(numberForRomanDigit));
                    atomicNumber.set(atomicNumber.get() - numberForRomanDigit);
                }
            });
    return result.toString();
}
```

#4
##Production Kodu
```
private static Map<Integer, String> romanDigitMap = Map.of(
        10, "X",
        5, "V",
        4, "IV",
        1, "I"
);

public static String convert(int number) {

    StringBuilder result = new StringBuilder();
    AtomicInteger atomicNumber = new AtomicInteger(number);
    romanDigitMap.keySet().stream().sorted(Comparator.reverseOrder())
            .forEach(numberForRomanDigit -> {
                while (atomicNumber.get() >= numberForRomanDigit) {
                    result.append(romanDigitMap.get(numberForRomanDigit));
                    atomicNumber.set(atomicNumber.get() - numberForRomanDigit);
                }
            });
    return result.toString();
}
```

#9, 50
##Production Kodu
```
private static Map<Integer, String> romanDigitMap = Map.of(
        50, "L",
        10, "X",
        9, "IX",
        5, "V",
        4, "IV",
        1, "I"
);

public static String convert(int number) {

    StringBuilder result = new StringBuilder();
    AtomicInteger atomicNumber = new AtomicInteger(number);
    romanDigitMap.keySet().stream().sorted(Comparator.reverseOrder())
            .forEach(numberForRomanDigit -> {
                while (atomicNumber.get() >= numberForRomanDigit) {
                    result.append(romanDigitMap.get(numberForRomanDigit));
                    atomicNumber.set(atomicNumber.get() - numberForRomanDigit);
                }
            });
    return result.toString();
}
```

#Nihai
##Test Kodu
```
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
```
##Production Kodu
```
private static Map<Integer, String> romanDigitMap = Map.of(
        50, "L",
        40, "XL",
        10, "X",
        9, "IX",
        5, "V",
        4, "IV",
        1, "I"
);

public static String convert(int number) {

    StringBuilder result = new StringBuilder();
    AtomicInteger atomicNumber = new AtomicInteger(number);
    romanDigitMap.keySet().stream().sorted(Comparator.reverseOrder())
            .forEach(numberForRomanDigit -> {
                while (atomicNumber.get() >= numberForRomanDigit) {
                    result.append(romanDigitMap.get(numberForRomanDigit));
                    atomicNumber.set(atomicNumber.get() - numberForRomanDigit);
                }
            });
    return result.toString();
}
```