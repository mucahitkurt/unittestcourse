Production Kodunuzun aşağıdaki aşamalardan geçmesini bekliyoruz;

#1 icin bulur
```
public class PrimeFactors {

    public static List<Integer> generate(int i) {
        return Collections.EMPTY_LIST;
    }
}
```
#2 icin bulur
```
public class PrimeFactors {

    public static List<Integer> generate(int i) {
        if (i <= 1) {
            return Collections.EMPTY_LIST;
        }
        return List.of(2);
    }
}
```

#3 icin bulur
```
public class PrimeFactors {

    public static List<Integer> generate(int i) {
        if (i <= 1) {
            return Collections.EMPTY_LIST;
        }

        List<Integer> primeFactors = new ArrayList<>();
        primeFactors.add(i);

        return primeFactors;
    }
}
```
#4, 5, 6, 7 icin bulur
```
public class PrimeFactors {

    public static List<Integer> generate(int i) {
        if (i <= 1) {
            return Collections.EMPTY_LIST;
        }

        List<Integer> primeFactors = new ArrayList<>();
        if (i % 2 == 0) {
            primeFactors.add(2);
            i /= 2;
        }
        if (i > 1) {
            primeFactors.add(i);
        }

        return primeFactors;
    }
}
```
#8 icin bulur
```
public class PrimeFactors {

    public static List<Integer> generate(int i) {
        if (i <= 1) {
            return Collections.EMPTY_LIST;
        }

        List<Integer> primeFactors = new ArrayList<>();
        while (i % 2 == 0) {
            primeFactors.add(2);
            i /= 2;
        }
        if (i > 1) {
            primeFactors.add(i);
        }

        return primeFactors;
    }
}
```
#9 icin bulur
```
public class PrimeFactors {

    public static List<Integer> generate(int i) {
        if (i <= 1) {
            return Collections.EMPTY_LIST;
        }

        List<Integer> primeFactors = new ArrayList<>();
        int candidate = 2;
        while (i > 1) {
            while (i % candidate == 0) {
                primeFactors.add(candidate);
                i /= candidate;
            }
            candidate++;
        }

        if (i > 1) {
            primeFactors.add(i);
        }

        return primeFactors;
    }
}
```
#refactor 1
```
public class PrimeFactors {

    public static List<Integer> generate(int i) {
        if (i <= 1) {
            return Collections.EMPTY_LIST;
        }

        List<Integer> primeFactors = new ArrayList<>();
        int candidate = 2;
        while (i > 1) {
            while (i % candidate == 0) {
                primeFactors.add(candidate);
                i /= candidate;
            }
            candidate++;
        }
        return primeFactors;
    }
}
```
#refactor 2
```
public class PrimeFactors {

    public static List<Integer> generate(int i) {
        if (i <= 1) {
            return Collections.EMPTY_LIST;
        }

        List<Integer> primeFactors = new ArrayList<>();
        for (int candidate = 2; i > 1; candidate++) {
            for (; i % candidate == 0; i /= candidate) {
                primeFactors.add(candidate);
            }
        }
        return primeFactors;
    }
}
```