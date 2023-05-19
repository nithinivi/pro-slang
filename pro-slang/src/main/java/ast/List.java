package ast;

public class List extends Expression {

    private final Expression hd;
    private final Expression tl;

    public List(Expression hd, Expression tl) {
        this.hd = hd;
        this.tl = tl;
    }

    public Expression getHd() {
        return hd;
    }

    public Expression getTl() {
        return tl;
    }
}
