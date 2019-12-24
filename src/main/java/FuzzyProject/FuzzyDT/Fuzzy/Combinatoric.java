package main.java.FuzzyProject.FuzzyDT.Fuzzy;

import java.math.BigInteger;

public class Combinatoric {
    public Combinatoric() {
    }

    public static BigInteger c(int n, int m) throws CombinatoricException {
        check(n, m);
        int r = Math.min(m, n - m);
        return p(n, r).divide(factorial(r));
    }

    static void check(int n, int m) throws CombinatoricException {
        if (n < 0) {
            throw new CombinatoricException("n, the number of items, must be greater than 0");
        } else if (n < m) {
            throw new CombinatoricException("n, the number of items, must be >= m, the number selected");
        } else if (m < 0) {
            throw new CombinatoricException("m, the number of selected items, must be >= 0");
        }
    }

    public static BigInteger factorial(int n) throws CombinatoricException {
        if (n < 0) {
            throw new CombinatoricException("n must be >= 0");
        } else {
            BigInteger factorial = new BigInteger(new byte[]{1});

            for(int i = n; i > 1; --i) {
                factorial = factorial.multiply(new BigInteger(new byte[]{(byte)i}));
            }

            return factorial;
        }
    }

    public static BigInteger p(int n) throws CombinatoricException {
        return factorial(n);
    }

    public static BigInteger p(int n, int m) throws CombinatoricException {
        check(n, m);
        BigInteger product = new BigInteger(new byte[]{1});

        for(int i = n; i > n - m; --i) {
            product = product.multiply(new BigInteger(new byte[]{(byte)i}));
        }

        return product;
    }
}

