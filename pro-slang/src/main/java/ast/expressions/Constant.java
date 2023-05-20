package ast.expressions;

import ast.Expression;
import ast.Tag;

public class Constant extends Expression {

    private final String cid;

    public Constant(String cid) {
        this.cid = cid;
    }

    public String getCid() {
        return cid;
    }

    @Override
    public Tag tag() {
        return Tag.CONSTANT;
    }
}
