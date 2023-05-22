package executor;

import ast.Expression;
import ast.Tag;
import ast.expressions.List;
import ast.expressions.Negate;
import ast.expressions.Program;
import ast.expressions.Rule;

import static ast.expressions.List.cons;
import static executor.Renaming.rename;
import static utils.PrintAST.printTree;

public class Prove {
    private final Expression prog;
    private MODE search;
    private boolean satisfied;
    private Expression facts;
    private Expression query;

    private int index;

    Prove(int index, Expression prog) {
        this.index = index;
        this.prog = prog;
    }

    public boolean prove(MODE mode, Expression query, Expression facts, Env env) {
        this.search = mode;
        this.satisfied = false;
        this.query = query;
        this.facts = facts;
        proveList(query, facts, env);
        return satisfied;
    }

    private void proveList(Expression query, Expression facts, Env env) {
        if (query == null) {
            switch (search) {
                case PRINT_ALL -> {
                    System.out.print("Query: ");
                    var program = (Program) query;
                    printTree(program.getQuery(), env);
                    System.out.println(" yes");
                    satisfied = true;
                }
                case SILENT -> {

                    satisfied = true;
                }
            }
        } else {
            index++;
            var hd = ((List) query).getHd();
            proveLiteral(hd, facts, env);
        }
    }

    private void proveLiteral(Expression p, Expression f, Env env) {
        if (p.tag() == Tag.NEGATE) {
            var neg = (Negate) p;
            var newProof = new Prove(index, prog);
            if (!newProof.prove(MODE.SILENT, cons(neg.getL(), null), f, env)) {
                var tl = ((List) query).getTl();
                proveList(tl, f, env);
            }
        } else {
            if (f != null) {
                var uni = new Unification();
                var fl = (List) f;
                var lhs = ((Rule) fl.getHd()).getLhs();
                var rhs = ((Rule) (fl.getHd())).getRhs();
                var tl = fl.getTl();

                if (uni.unify(p, rename(lhs, index), env)) {
                    proveList(List.append(rename(rhs, index), tl), facts, uni.getEnv());
                }
                proveLiteral(p,tl, env);
            }
        }
    }

}
