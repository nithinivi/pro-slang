import ast.Expression;
import executor.MODE;
import executor.Executor;
import parser.Parser;
import utils.PrintAST;

public class ProSlang {
    public static void main(String[] args) throws Exception {
        String data = """
                mother(anne,    bridget).
                mother(abigail, bridget).
                mother(bridget, carol).
                             
                grandmother(C, GM) <= mother(C, M) and mother(M, GM).
                             
                ? grandmother(anne, carol).   
                """;
        Parser parser = new Parser(data);
        Expression parse = parser.parse();
        PrintAST.printTree(parse, null);

        var prover = new Executor();
        prover.execute(MODE.PRINT_ALL, parse);
    }
}
