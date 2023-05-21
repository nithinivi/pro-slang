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

    private Expression rename(Expression tree, int index) {
        if (tree == null) return null;
        switch (tree.tag()) {
            case VARIABLE -> {
                var copynode = (Variable) tree;
                var variable = new Variable();
                variable.setIndex(copynode.getIndex());
                return variable;
            }
            case CONSTANT, INT -> {
                return tree;
            }
            case PREDICATE -> {
                var predicate = (Predicate) tree;
                var copynode = new Predicate();
                copynode.setParams(rename(predicate.getParams(), index));
                return copynode;
            }
            case FUNC -> {
                var function = (Func) tree;
                var copynode = new Func();
                copynode.setParams(rename(function.getParams(), index));
                return copynode;
            }
            case NEGATE -> {
                var negate = (Negate) tree;
                var copynode = new Negate();
                copynode.setL(rename(negate.getL(), index));
                return copynode;
            }
            case RULE -> {
                var rule = (Rule) tree;
                var copynode = new Rule();
                copynode.setLhs(rename(rule.getLhs(), index));
                copynode.setRhs(rename(rule.getRhs(), index));
                return copynode;
            }
            case LIST -> {
                var copynode = new List();
                var tl = (List) tree;
                copynode.setHd(rename(tl.getHd(), index));
                copynode.setTl(rename(tl.getTl(), index));
                return copynode;
            }
        }
        throw new IllegalStateException("Unexpected value: " + tree.tag());

    }

    public void execute(MODE search, Expression program) {
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
