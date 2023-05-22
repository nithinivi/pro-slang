package utils;

import ast.Expression;
import ast.expressions.*;
import executor.Env;
import executor.Binding;

public class PrintAST {
    Expression ast;

    public static void printId(String id) {
        int i = 0;
        while (i < id.length()) {
            if (id.charAt(i) == ' ')
                break;
            else {
                System.out.print(id.charAt(i));
                i++;
            }
        }
    }


    public static void printTail(Expression tree) {
        if (tree == null)
            return;
        var list = (List) tree;
        System.out.print(",  ");
        printTree(list.getHd(), null);
        printTail((List) list.getTl());

    }

    public static void printTree(Expression tree, Env e) {
        if (tree == null)
            return;

        switch (tree.tag()) {

            case VARIABLE -> {
                Variable v = (Variable) tree;
                var uni = new Binding();
                if (uni.bound(tree, e))
                    printTree(tree, e);

                else {
                    System.out.print(v.getVid());
                    if (v.getIndex() != 0)
                        System.out.print("-" + v.getIndex());

                }
            }
            case CONSTANT -> {
                Constant v = (Constant) tree;
                printId(v.getCid());
            }
            case INT -> {
                Intcon v = (Intcon) tree;
                System.out.print(v.getN());
            }
            case NEGATE -> {
                Negate v = (Negate) tree;
                System.out.print(" not ");
                printTree(v.getL(), e);

            }
            case LIST -> {
                List v = (List) tree;
                printTree(v.getHd(), e);
                printTail(v.getTl());
            }
            case PREDICATE -> {
                Predicate v = (Predicate) tree;
                printId(v.getId());
                if (v.getParams() != null) {
                    System.out.print("(");
                    printTree(v.getParams(), e);
                    System.out.print(")");
                }
            }
            case FUNC -> {
                Func v = (Func) tree;
                printId(v.getId());
                if (v.getParams() != null) {
                    System.out.print("(");
                    printTree(v.getParams(), e);
                    System.out.print(")");
                }

            }
            case PROGRAM -> {
                Program v = (Program) tree;
                printTree(v.getFacts(), e);
                System.out.println();
                System.out.print("?");
                printTree(v.getQuery(), e);
                System.out.println(".");
            }
            case RULE -> {
                Rule v = (Rule) tree;
                printTree(v.getLhs(), e);
                if (v.getRhs() != null) {
                    System.out.print(" <= ");
                    printTree(v.getRhs(), e);
                }
                System.out.println(".");
            }
        }
    }
}
