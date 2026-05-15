package calculator.service;

public class CalculatorEngine {
    
    public static double evaluate(final String str) {
        return new Object() {
            int pos = -1, ch;
            boolean lastWasPercentage = false;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected character: " + (char)ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if (eat('+')) {
                        double y = parseTerm();
                        if (lastWasPercentage) {
                            x += x * y;
                        } else {
                            x += y;
                        }
                    } else if (eat('-')) {
                        double y = parseTerm();
                        if (lastWasPercentage) {
                            x -= x * y;
                        } else {
                            x -= y;
                        }
                    } else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) {
                        double divisor = parseFactor();
                        if (divisor == 0) throw new ArithmeticException("Division by zero");
                        x /= divisor; // division
                    }
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                    lastWasPercentage = false;
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    if (func.equals("pi")) {
                        x = Math.PI;
                    } else if (func.equals("e")) {
                        x = Math.E;
                    } else {
                        x = parseFactor(); // parse argument for function
                        if (func.equals("sqrt")) x = Math.sqrt(x);
                        else if (func.equals("sin")) x = Math.sin(Math.toRadians(x)); 
                        else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                        else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                        else if (func.equals("log")) x = Math.log10(x);
                        else if (func.equals("ln")) x = Math.log(x);
                        else throw new RuntimeException("Unknown function: " + func);
                        lastWasPercentage = false;
                    }
                } else {
                    throw new RuntimeException("Unexpected character: " + (char)ch);
                }

                if (eat('^')) { // exponentiation
                    x = Math.pow(x, parseFactor());
                    lastWasPercentage = false;
                }

                while (eat('%')) {
                    x /= 100.0;
                    lastWasPercentage = true;
                }

                return x;
            }
        }.parse();
    }
}
