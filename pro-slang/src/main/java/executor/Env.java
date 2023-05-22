package executor;

import ast.Expression;
import ast.expressions.Variable;

public class Env {
    private String id;
    private Integer index;
    private Env next;
    private Expression val;

    public Env() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Env getNext() {
        return next;
    }

    public void setNext(Env next) {
        this.next = next;
    }

    public Expression getVal() {
        return val;
    }

    public void setVal(Expression val) {
        this.val = val;
    }
}
