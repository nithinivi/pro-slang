import ast.Expression;
import executor.Executor;
import executor.MODE;
import parser.Parser;
import utils.PrintAST;


public class ProSlang {
    public static void main(String[] args) throws Exception {
        String data = """
                                
                parent(chacko, wilson).
                parent(wilson, kuttan).
                                
                grandfather(X, Y) <= parent(X, Z) and parent(Z,Y).
                              
                ?grandfather(chacko, kuttan).
                                   
                """;
        Parser parser = new Parser(data);
        Expression program = parser.parse();
        PrintAST.printTree(program, null);

        var executor = new Executor();
        var result = executor.execute(MODE.PRINT_ALL, program);
        System.out.println(result);
    }
}
