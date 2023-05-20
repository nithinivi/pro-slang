package executor;

import ast.Expression;
import ast.expressions.*;

public class Rename {

    public static Expression rename(Expression tree, int index) {
        if (tree == null) return null;
        switch (tree.tag()) {
            case VARIABLE -> {
                var v = (Variable) tree;
                var variable = new Variable();
                variable.setIndex(v.getIndex());
                return variable;
            }
            case CONSTANT, INT -> {
                return tree;
            }
            case PREDICATE -> {
                var p = (Predicate) tree;
                var predicate = new Predicate();
                predicate.setParams(rename(p.getParams(), index));
                return predicate;
            }
            case FUNC -> {
                var p = (Func) tree;
                var func = new Func();
                func.setParams(rename(p.getParams(), index));
                return func;
            }
            case NEGATE -> {
                var n = (Negate) tree;
                var neg = new Negate();
                neg.setL(rename(n.getL(), index));
                return neg;
            }
            case RULE -> {
                var r = (Rule) tree;
                var rule = new Rule();
                rule.setLhs(rename(r.getLhs(), index));
                rule.setRhs(rename(r.getRhs(), index));
                return rule;
            }
            case LIST -> {
                var c = new List();
                var list = new List();
                list.setHd(rename(c.getHd(), index));
                list.setTl(rename(c.getTl(), index));
            }
        }
        throw new IllegalStateException("Unexpected value: " + tree.tag());

    }

}
