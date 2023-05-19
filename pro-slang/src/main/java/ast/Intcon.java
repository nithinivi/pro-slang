package ast;

public class Intcon extends Expression {
    private final Integer n;

    public Intcon(Integer n) {
        this.n = n;
    }

    public Integer getN() {
        return n;
    }
}
