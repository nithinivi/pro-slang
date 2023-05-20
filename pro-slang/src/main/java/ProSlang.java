import ast.Expression;
import parser.Parser;
import utils.PrintAST;

public class ProSlang {
    public static void main(String[] args) throws Exception {
        String data = """
                witch(X)  <= burns(X) and female(X).
                burns(X)  <= wooden(X).
                wooden(X) <= floats(X).
                floats(X) <= sameweight(duck, X).
                                
                female(girl).     
                sameweight(duck,girl).
                                
                ? witch(girl).      
                """;
        Parser parser = new Parser(data);
        Expression parse = parser.parse();
        PrintAST.printTree(parse, null);


    }
}
