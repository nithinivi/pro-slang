package ast;

public class Cons extends Expression {

    private  Expression hd;
    private  Expression tl;

    public Cons(Expression hd, Expression tl) {
        this.hd = hd;
        this.tl = tl;
    }

    public Expression getHd() {
        return hd;
    }

    public Expression getTl() {
        return tl;
    }


    public void setHd(Expression hd) {
        this.hd = hd;
    }

    public void setTl(Expression tl) {
        this.tl = tl;
    }
}
