import ast.Expression;
import parser.Parser;

public class ProSlang {
    public static void main(String[] args) throws Exception {
        String data = """
                ? witch(girl).
                                
                """;
        Parser parser = new Parser(data);
        Expression parse = parser.parse();


    }
}
