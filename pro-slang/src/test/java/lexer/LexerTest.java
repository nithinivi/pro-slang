package lexer;

import org.junit.jupiter.api.Test;

class LexerTest {

    @Test
    void when_variableName_then_parseTOKEN_as_LC_WORD() throws Exception {
        var luther = new Lexer("nithin");
        TOKEN symbol = luther.getToken();
        assert symbol == TOKEN.LC_WORD;
    }


    @Test
    void when_variableName_then_parseTOKEN_as_UC_WORD() throws Exception {
        var luther = new Lexer("Nithin");
        TOKEN symbol = luther.getToken();
        assert symbol == TOKEN.UC_WORD;
    }


    @Test
    void given_and_when_getSymbol_then_parseTOKEN_as_AND() throws Exception {
        var luther = new Lexer("and");
        TOKEN symbol = luther.getToken();
        assert symbol == TOKEN.AND;
    }

    @Test
    void given_and_when_getSymbol_then_parseTOKEN_as_NOT() throws Exception {
        var luther = new Lexer("not");
        TOKEN symbol = luther.getToken();
        assert symbol == TOKEN.NOT;
    }


    @Test
    void given_and_when_getSymbol_then_parseTOKEN_as_NUMERAL() throws Exception {
        var luther = new Lexer("123");
        TOKEN symbol = luther.getToken();
        assert symbol == TOKEN.NUMERAL;

    }
}