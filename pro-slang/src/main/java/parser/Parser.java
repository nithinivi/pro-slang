package parser;

import ast.Expression;
import ast.expressions.*;
import lexer.Lexer;
import lexer.TOKEN;

import java.util.function.Supplier;

import static lexer.TOKEN.*;

public class Parser extends Lexer {
    private TOKEN token = TOKEN.EOF;

    public Parser(String expr) {
        super(expr);
    }

    public Expression parse() throws Exception {
        parseToken();
        return parseprog();
    }

    private boolean isToken(TOKEN s) throws Exception {
        if (s != token)
            return false;
        token = getToken();
        return true;
    }


    private void checkTokenAndThrowError(TOKEN t, String s) throws Exception {
        if (this.token != t)
            error(s);
        parseToken();
    }


    private void parseToken() throws Exception {
        this.token = getToken();
    }


    private Expression parseprog() throws Exception {

        Expression facts = parseRules();
        checkTokenAndThrowError(QUESTION, "? expected");
        Expression query = sequence(lazyParseLiteral, AND);
        checkTokenAndThrowError(DOT, "missing . ");

        return new Program(facts, query);
    }

    private Expression parseRules() throws Exception {
        if (token != LC_WORD)
            return null;
        List cons = List.cons(parseRule(), null);
        cons.setTl(parseRules());
        return cons;

    }

    private Expression parseRule() throws Exception {
        Rule rule = new Rule();
        rule.setLhs(parseAtom());
        if (isToken(IMPLIED_BY))
            rule.setRhs(sequence(lazyParseLiteral, AND));
        else
            rule.setRhs(null);
        checkTokenAndThrowError(DOT, ". expected");
        return rule;
    }

    private Expression parseLiteral() throws Exception {
        if (isToken(NOT))
            return new Negate(parseAtom());
        else return parseAtom();
    }

    private Expression parseAtom() throws Exception {
        if (token != LC_WORD)
            error("no predicate");

        String word = getVariableName();
        parseToken();
        var expression = parseTerms();
        return new Predicate(word, expression);
    }

    private Expression parseTerms() throws Exception {
        if (!isToken(OPEN))
            return null;
        Expression p = sequence(lazyParseTerm, COMMA);
        checkTokenAndThrowError(CLOSE, ") excepted");
        return p;
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


    private Expression sequence(Supplier<Expression> expressionSupplier, TOKEN sep) throws Exception {
        return List.cons(expressionSupplier.get(), rest(expressionSupplier, sep));
    }

    private Expression rest(Supplier<Expression> expression, TOKEN sep) throws Exception {
        if (isToken(sep))
            return List.cons(expression.get(), rest(expression, sep));
        else return null;
    }


    Supplier<Expression> lazyParseTerm = () -> {
        try {
            return parseTerm();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    };

    Supplier<Expression> lazyParseLiteral = () -> {
        try {
            return parseLiteral();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    };

}
