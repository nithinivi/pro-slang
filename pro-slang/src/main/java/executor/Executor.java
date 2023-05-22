package executor;

import ast.Expression;
import ast.expressions.List;
import ast.expressions.Program;

public class Executor {

    boolean satisfied;
    private MODE search;
    private int index;

    private Program prog;
    private List query;
    private List facts;


    public boolean execute(MODE search, Expression program) {
        index = 0;
        this.search = search;

        this.prog = (Program) program;
        this.query = (List) prog.getQuery();
        this.facts = (List) prog.getQuery();
        var prover = new Prove(0, program);
        return prover.prove(MODE.PRINT_ALL, query, prog.getFacts(), null);
        }
    }
