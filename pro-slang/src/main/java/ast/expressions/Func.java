package ast.expressions;

import ast.Expression;
import ast.Tag;

public class Func extends Expression {
    private String id;
    private Expression params;

    public Func(String id, Expression params) {
        this.id = id;
        this.params = params;
    }

    public Func() {
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
        return Tag.FUNC;
    }
}
