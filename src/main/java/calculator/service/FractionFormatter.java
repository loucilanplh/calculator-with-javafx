package calculator.service;

public class FractionFormatter {
    public static String toFraction(double value) {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            return String.valueOf(value);
        }
        
        // If it's effectively an integer, return as integer string
        if (Math.abs(value - Math.round(value)) < 1.0E-9) {
            return String.valueOf((long)Math.round(value));
        }

        double error = 1.0E-6;
        int sign = (value < 0) ? -1 : 1;
        value = Math.abs(value);

        long h1 = 1, h2 = 0;
        long k1 = 0, k2 = 1;
        double b = value;

        do {
            long a = (long) Math.floor(b);
            long aux = h1;
            h1 = a * h1 + h2;
            h2 = aux;
            aux = k1;
            k1 = a * k1 + k2;
            k2 = aux;
            if (b - a < 1.0E-10) break;
            b = 1 / (b - a);
        } while (Math.abs(value - (double) h1 / k1) > value * error && k1 < 100000);

        if (k1 == 1) {
            return String.valueOf(sign * h1);
        }

        return (sign * h1) + "/" + k1;
    }
}
