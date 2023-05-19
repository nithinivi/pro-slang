package ast;

public class Variable extends Expression {
    private final  String vid;
    private final Integer index;

    public Variable(String vid, Integer index) {
        this.vid = vid;
        this.index = index;
    }

    public String getVid() {
        return vid;
    }

    public Integer getIndex() {
        return index;
    }
}
