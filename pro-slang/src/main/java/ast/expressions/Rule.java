package ast.expressions;

import ast.Expression;
import ast.Tag;

public class Rule extends Expression { // eg. p <= q and r.

    private Expression lhs;
    private Expression rhs;

    public Rule() {
    }

    public Rule(Expression lhs, Expression rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public Expression getRhs() {
        return rhs;
    }

    public Expression getLhs() {
        return lhs;
    }

    public void setLhs(Expression lhs) {
        this.lhs = lhs;
    }

    public void setRhs(Expression rhs) {
        this.rhs = rhs;
    }

    @Override
    public Tag tag() {
        return Tag.RULE;
    }
}
