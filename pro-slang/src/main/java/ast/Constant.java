package ast;

public class Constant extends Expression {

    private final String cid;

    public Constant(String cid) {
        this.cid = cid;
    }

    public String getCid() {
        return cid;
    }
}
