package parser;

import ast.*;
import lexer.Lexer;
import lexer.TOKEN;

import static lexer.TOKEN.*;

public class Parser extends Lexer {
    private TOKEN token = TOKEN.EOF;

    public Parser(String expr) {
        super(expr);
    }

    public Expression parse() throws Exception {
        return prog();
    }


    private Expression prog() throws Exception {

        Expression facts = parseRules();
        checkTokenAndThrowError(QUESTION, "? expected");

        Expression parseLiteral = parseLiteral();
        Expression query = sequence(parseLiteral, AND);
        checkTokenAndThrowError(DOT, "missing . ");

        return new Program(facts, query);
    }

    private Expression parseRules() throws Exception {
        if (!isToken(LC_WORD))
            return null;
        return new Cons(pRule(), parseRules());

    }

    private Expression pRule() throws Exception {
        Rule rule = new Rule();
        rule.setLhs(parseAtom());
        if (isToken(IMPLIED_BY))
            rule.setRhs(sequence(parseLiteral(), AND));
        else
            rule.setRhs(null);
        checkTokenAndThrowError(DOT, ". expected");
        return rule;
    }

    private Expression parseLiteral() throws Exception {
        if (isToken(NOT))
            return new Negate(parseAtom());
        else
            return parseAtom();
    }

    private Expression parseAtom() throws Exception {
        if (isToken(LC_WORD)) {
            String word = getVariableName();
            parseToken();
            return new Predicate(word, parseTerms());

        }

        error("no predicate");

        return null;
    }


    private void checkTokenAndThrowError(TOKEN token, String s) throws Exception {
        if (this.token != token)
            error(s);
    }

    private boolean isToken(TOKEN s) {
        return s == token;
    }


    private void parseToken() throws Exception {
        this.token = getSymbol();
    }


    private Expression parseTerm() throws Exception {
        Expression term = null;
        switch (token) {
            case LC_WORD -> {
                String id = getVariableName();
                parseToken();
                if (isToken(OPEN))
                    term = new Func(id, parseTerms());
                else
                    term = new Constant(id);
            }
            case UC_WORD -> {
                term = new Variable(getVariableName(), 0);
                parseToken();
            }
            case NUMERAL -> {
                term = new Intcon(getNumber());
                parseToken();
            }
            default -> error("no term   ");
        }
        return term;
    }

    private Expression parseTerms() throws Exception {
        if (!isToken(OPEN))
            return null;

        Expression p = sequence(parseTerm(), COMMA);
        checkTokenAndThrowError(CLOSE, ") excepted");
        return p;
    }


    private Expression sequence(Expression expression, TOKEN sep) {
        return new Cons(expression, rest(expression, sep));
    }

    private Expression rest(Expression expression, TOKEN sep) {
        if (isToken(sep))
            return new Cons(expression, rest(expression, sep));
        else
            return null;
    }




}
