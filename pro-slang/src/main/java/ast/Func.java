package ast;

public class Func extends Expression{
    private final String id;
    private final Expression params;

    public Func(String id, Expression params) {
        this.id = id;
        this.params = params;
    }

    public String getId() {
        return id;
    }

    public Expression getParams() {
        return params;
    }
}
