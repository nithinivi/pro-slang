package ast;

public class Rule extends Expression { // eg. p <= q and r.

    private final Expression lhs;
    private final Expression rhs;

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
}
