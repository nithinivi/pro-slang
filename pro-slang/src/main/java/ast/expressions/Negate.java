package ast.expressions;

import ast.Expression;
import ast.Tag;

public class Negate extends Expression {  // eg. not p(7)
    private Expression l;

    public Negate(Expression l) {
        this.l = l;
    }

    public Negate() {
    }

    public void setL(Expression l) {
        this.l = l;
    }

    public Expression getL() {
        return l;
    }

    @Override
    public Tag tag() {
        return Tag.NEGATE;
    }
}
