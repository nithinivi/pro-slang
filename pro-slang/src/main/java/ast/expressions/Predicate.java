package ast.expressions;

import ast.Expression;
import ast.Tag;

public class Predicate extends Expression {     // eg. h(1,k(p,X))
    private String id;
    private Expression params;

    public Predicate(String id, Expression params) {
        this.id = id;
        this.params = params;
    }

    public Predicate() {

    }

    public void setId(String id) {
        this.id = id;
    }

    public void setParams(Expression params) {
        this.params = params;
    }

    public String getId() {
        return id;
    }

    public Expression getParams() {
        return params;
    }

    @Override
    public Tag tag() {
        return Tag.PREDICATE;
    }
}
