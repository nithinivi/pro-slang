package ast.expressions;

import ast.Expression;
import ast.Tag;

public class List extends Expression {

    private Expression hd;
    private Expression tl;

    public List(Expression hd, Expression tl) {
        this.hd = hd;
        this.tl = tl;
    }
    public List() {
    }



    public Expression getHd() {
        return hd;
    }

    public void setHd(Expression hd) {
        this.hd = hd;
    }

    public Expression getTl() {
        return tl;
    }

    public void setTl(Expression tl) {
        this.tl = tl;
    }

    @Override
    public Tag tag() {
        return Tag.LIST;
    }

    public static List cons(Expression h, Expression t) {
        return new List(h, t);
    }

    public static Expression append(Expression a, Expression b) {
        if (a == null)
            return b;
        else {
            var al = (List) a;
            var bl = (List) b;
            return cons(al.getHd(), append(al.getTl(), bl));
        }
    }
}
