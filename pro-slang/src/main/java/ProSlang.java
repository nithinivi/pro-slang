import ast.Expression;
import executor.Executor;
import executor.MODE;
import parser.Parser;
import utils.PrintAST;


public class ProSlang {
    public static void main(String[] args) throws Exception {
        String data = """
                                
                parent(john, jim).
                                
                grandparent(X, Z) <= parent(X, Z).
                
                ?grandparent(john, jim).
                
                """;
        Parser parser = new Parser(data);
        Expression program = parser.parse();
        PrintAST.printTree(program, null);

        var executor = new Executor();
        var result = executor.execute(MODE.PRINT_ALL, program);
        System.out.println(result);
    }
}
