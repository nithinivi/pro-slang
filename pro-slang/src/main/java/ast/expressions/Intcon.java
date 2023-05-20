package ast.expressions;

import ast.Expression;
import ast.Tag;

public class Intcon extends Expression {
    private final Integer n;

    public Intcon(Integer n) {
        this.n = n;
    }

    public Integer getN() {
        return n;
    }

    @Override
    public Tag tag() {
        return Tag.INT;
    }
}
