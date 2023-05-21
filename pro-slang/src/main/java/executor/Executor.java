package executor;

import ast.Expression;
import ast.Tag;
import ast.expressions.*;

import static utils.PrintAST.printTree;

public class Executor {

    boolean satisfied;
    private MODE search;
    private int index;

    private Program prog;
    private List query;

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
                return list;
            }
        }
        throw new IllegalStateException("Unexpected value: " + tree.tag());

    }

    public void excute(MODE search, Expression program) {
        index = 0;
        this.search = search;

        this.prog = (Program) program;
        this.query = (List) prog.getQuery();
        var proof = prove(MODE.PRINT_ALL, query, prog.getFacts(), null);
        if (!proof) {
            System.out.println(" no ");
        }
    }

    public boolean prove(MODE printAll, Expression query, Expression facts, Env env) {
        satisfied = false;
        proveList(query, facts, env);
        return satisfied;
    }

    private void proveList(Expression query, Expression facts, Env env) {
        if (query == null) {
            switch (search) {
                case SILENT -> satisfied = true;
                case PRINT_ALL -> {
                    printTree(query, env);
                    System.out.println(" yes");
                    satisfied = true;
                }
            }

        } else {
            index++;
            var lq = (List) query;
            proveLiteral(lq.getHd(), facts, env);
        }

    }

    private void proveLiteral(Expression p, Expression f, Env env) {
        if (p.tag() == Tag.NEGATE) { // negation by failure: not p(...) succeeds iff p(...) fails
            var n = (Negate) p;
            if (!prove(MODE.SILENT, List.cons(n.getL(), null), f, env))
                proveLiteral(query.getTl(), f, env);
        }
        if (f != null) {
            var unifier = new Unify();
            var facts = (List) f;
            var ruleHd = (Rule) facts.getHd();
            var ruleTl = (List) facts.getTl();

            if (unifier.unify(p, rename(ruleHd.getRhs(), index), env)) {
                proveList(
                        List.append(
                                rename(ruleHd.getRhs(), index),
                                ruleTl),
                        facts,
                        env
                );
                proveList(p, ruleTl, env);
            }

        }
    }

}
