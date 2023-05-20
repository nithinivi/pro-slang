package ast.expressions;

import ast.Expression;
import ast.Tag;

public class Program extends Expression {

    private final Expression facts;
    private final Expression query;

    public Program(Expression facts, Expression query) {
        this.facts = facts;
        this.query = query;
    }

    public Expression getFacts() {
        return facts;
    }

    public Expression getQuery() {
        return query;
    }

    @Override
    public Tag tag() {
        return Tag.PROGRAM;
    }
}
