package executor;

import ast.Expression;
import ast.Tag;
import ast.expressions.List;
import ast.expressions.Negate;
import ast.expressions.Program;
import ast.expressions.Rule;

import static utils.PrintAST.printTree;

public class Executor {

    boolean satisfied;
    private MODE search;
    private int index;

    private Program prog;
    private List query;


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
                    printTree(prog.getQuery(), env);
                    System.out.println(" yes");
                    satisfied = true;
                }
            }

        } else {
            index++;
            if (query.tag() == Tag.LIST) {
                var lq = (List) query;
                proveLiteral(lq.getHd(), facts, env);
            }
            proveLiteral(query, facts, env);

        }

    }

    private void proveLiteral(Expression p, Expression f, Env env) {
        if (p.tag() == Tag.NEGATE) { // negation by failure: not p(...) succeeds iff p(...) fails
            var n = (Negate) p;
            if (!prove(MODE.SILENT, List.cons(n.getL(), null), f, env))
                proveList(query.getTl(), f, env);
        }
        if (f != null) {
            var unifier = new Unification();
            var facts = (List) f;
            var ruleHd = (Rule) facts.getHd();
            var ruleTl = (List) facts.getTl();

            if (unifier.unify(p, Renaming.rename(ruleHd.getLhs(), index), env)) {
                proveList(
                        List.append(
                                Renaming.rename(ruleHd.getRhs(), index),
                                ruleTl),
                        facts,
                        unifier.getEnv()
                );

            }

            proveList(p, ruleTl, env);
        }
    }

}
