package lexer;

import java.util.function.Supplier;

import static lexer.TOKEN.EOF;

public class Lexer {

    String expr;
    int index;
    double number;
    Supplier<Character> ch = () -> this.expr.charAt(this.index);
    String quotedString;
    String variableName;
    Integer lineNumber;
    private int length;


    public Lexer(String expr) {
        this.expr = expr;
        this.length = expr.length();
        index = 0;
        lineNumber = 1;

    }

    public TOKEN getSymbol() throws Exception {
        TOKEN tok = null;
        while (index < length && ch.get() == ' ' || index < length && ch.get() == '\t')
            index++;

        // if end of string
        if (index >= length) {
            return EOF;
        }
        switch (ch.get()) {

            case '{' -> {                       // COMMENT
                while (ch.get() != '{') {
                    if (ch.get() == '\n')
                        lineNumber++;
                    index++;
                }
                index++;
                tok = getSymbol();
            }
            case '\n' -> lineNumber++;
            case '\r', '\t', ' ' -> {
                index++;
                tok = getSymbol();
            }
            case '<' -> {
                index++;
                if (ch.get() == '=') {
                    index++;
                    tok = TOKEN.IMPLIED_BY;
                } else
                    error("not <=    ");
            }
            case ':' -> {
                index++;
                if (ch.get() == '-') {
                    index++;
                    tok = TOKEN.IMPLIED_BY;
                } else
                    tok = TOKEN.COLON;
            }
            case '?' -> {
                tok = TOKEN.QUESTION;
                index++;
            }
            case '.' -> {
                tok = TOKEN.DOT;
                index++;
            }
            case ',' -> {
                tok = TOKEN.COMMA;
                index++;
            }
            case '(' -> {
                tok = TOKEN.OPEN;
                index++;
            }
            case ')' -> {
                tok = TOKEN.CLOSE;
                index++;
            }
            case '[' -> {
                tok = TOKEN.SQ_OPEN;
                index++;
            }
            case ']' -> {
                tok = TOKEN.SQ_CLOSE;
                index++;
            }
            case '&' -> {
                tok = TOKEN.AND;
                index++;
            }
            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> tok = getDoubleFromString();


            default -> {
                tok = getKeyWordSymbol();
                if (tok != EOF)
                    break;

                tok = getVariableSymbol();
                if (tok != EOF)
                    break;

                throw new IllegalStateException("Unexpected char found : " + ch.get());
            }
        }

        return tok;
    }

    private TOKEN getDoubleFromString() {
    }

    private TOKEN getVariableSymbol() {
    }

    private TOKEN getKeyWordSymbol() {
    }

    private void error(String s) {
    }
}
