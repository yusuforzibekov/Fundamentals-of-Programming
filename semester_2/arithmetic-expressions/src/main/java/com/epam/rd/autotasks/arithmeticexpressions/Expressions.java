package com.epam.rd.autotasks.arithmeticexpressions;

public class Expressions {

    public static Variable var(String name, int value) {
        return new Variable(name, value);
    }

    public static Expression val(int value) {
        return new Expression() {
            @Override
            public int evaluate() {
                return value;
            }

            @Override
            public String toExpressionString() {
                if (value < 0)
                    return "(" + value + ")";
                return String.valueOf(value);
            }
        };
    }

    public static Expression sum(Expression... members) {
        return new Expression() {
            @Override
            public int evaluate() {
                int sum = 0;
                for (Expression member : members) {

                    sum += member.evaluate();

                }
                return sum;
            }

            @Override
            public String toExpressionString() {
                int i = 0;
                StringBuilder builder = new StringBuilder();
                builder.append("(");

                while (i < members.length) {
                    builder.append(members[i++].toExpressionString());
                    if (i == members.length) {
                        builder.append(")");
                        break;
                    }
                    builder.append(" + ");
                }

                return builder.toString();
            }
        };
    }

    public static Expression product(Expression... members) {
        return new Expression() {

            @Override
            public int evaluate() {
                int product = 1;
                for (Expression member : members) {
                    product *= member.evaluate();
                }
                return product;
            }

            @Override
            public String toExpressionString() {
                StringBuilder builder = new StringBuilder();
                int i = 0;
                builder.append("(");
                while (i < members.length) {
                    builder.append(members[i++].toExpressionString());
                    if (i == members.length) {
                        builder.append(")");
                        break;
                    }
                    builder.append(" * ");

                }
                return builder.toString();
            }
        };
    }

    public static Expression difference(Expression minuend, Expression subtrahend) {
        return new Expression() {
            @Override
            public int evaluate() {
                return minuend.evaluate() - subtrahend.evaluate();
            }

            @Override
            public String toExpressionString() {
                return "(" + minuend.toExpressionString() + " - " + subtrahend.toExpressionString() + ")";
            }
        };
    }

    public static Expression fraction(Expression dividend, Expression divisor) {
        return new Expression() {
            @Override
            public int evaluate() {
                return dividend.evaluate() / divisor.evaluate();
            }

            @Override
            public String toExpressionString() {
                return "(" + dividend.toExpressionString() + " / " + divisor.toExpressionString() + ")";
            }
        };
    }

}