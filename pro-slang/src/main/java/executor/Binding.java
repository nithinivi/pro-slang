package executor;

import ast.Expression;
import ast.expressions.Variable;

import java.util.Objects;

public class Binding {
    private Expression val = null;

    public boolean bound(Expression x, Env e) {
        var vx = (Variable) x;
        if (e == null)
            return false;

        else if (Objects.equals(e.getId(), vx.getVid())
                && Objects.equals(e.getIndex(), vx.getIndex())) {
            this.val =  e.getVal();
            return true;
        } else
            return bound(x, e.getNext());
    }

    public Expression getVal() {
        return val;
    }
}
