package ast;

import lexer.TOKEN;

import java.io.Serializable;

public abstract class Expression implements Serializable {
    public abstract Tag tag();
}
