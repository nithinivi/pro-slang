package lexer;

import org.junit.jupiter.api.Test;

class LexerTest {

    @Test
    void when_variableName_then_parseTOKEN_as_LC_WORD() {

        var luther = new Lexer("nithin");
        TOKEN symbol = luther.getSymbol();
        System.out.println(symbol);


    }

}