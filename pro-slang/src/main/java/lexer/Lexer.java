package lexer;

import java.util.function.Supplier;

import static lexer.TOKEN.EOF;
import static lexer.TOKEN.NUMERAL;

public class Lexer {

    String expr;
    int index;
    double number;
    Supplier<Character> ch = () -> this.expr.charAt(this.index);
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
        var bld = new StringBuilder();
        while (index < length && Character.isDigit(ch.get())) {
            bld.append(ch.get());
            index++;
        }

        this.number = Double.parseDouble(bld.toString());
        return NUMERAL;
    }

    private TOKEN getVariableSymbol() {
        if (!Character.isLetter(ch.get()))
            return EOF;

        var bld = new StringBuilder();
        while (index < length && (Character.isAlphabetic(ch.get()) || Character.isDigit(ch.get()))) {
            bld.append(ch.get());
            index++;
        }
        variableName = bld.toString();
        if (Character.isLowerCase(variableName.charAt(0)))
            return TOKEN.LC_WORD;
        else
            return TOKEN.UC_WORD;


    }

    private TOKEN getKeyWordSymbol() {
        String possibleKeyWord = "";
        int tempIndex = index;
        TOKEN tokenFromKeyWord = KeyWordTable.symbol(possibleKeyWord);

        while (KeyWordTable.keywordMatchCount(possibleKeyWord) > 0) {
            if (KeyWordTable.symbol(possibleKeyWord) != EOF) {
                tokenFromKeyWord = KeyWordTable.symbol(possibleKeyWord);
                this.index = tempIndex;
            }
            if (tempIndex < length)
                possibleKeyWord += expr.charAt(tempIndex);
            else
                break;
            tempIndex++;
        }
        return tokenFromKeyWord;
    }

    protected void error(String s) throws Exception {
        String errorMessage = "error: " + s + " lineno=" + lineNumber +
                " ch=<" + ch.get() + ">(" + index + ") " +
                " last word=<" + variableName + ">";
        throw new Exception(errorMessage);
    }

    protected String getVariableName() {
        return variableName;
    }

    protected Integer getNumber() {
        return (int) number;
    }
}
