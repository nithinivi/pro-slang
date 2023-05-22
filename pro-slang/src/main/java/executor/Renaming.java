package executor;

import ast.Expression;
import ast.expressions.*;

import java.io.*;

public class Renaming {

    private Renaming() {
    }

    public static <T, SerializableClass> T deepClone(T obj) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(obj);

        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream in = new ObjectInputStream(bis);
        SerializableClass copied = (SerializableClass) in.readObject();

        return (T) copied;
    }

    public static <T> CopyReturnObject<T> copyNode(Expression tree, Class<T> clazz) {
        Expression copy;

        try {
            copy = deepClone(tree);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return new CopyReturnObject<>(
                clazz.cast(tree),
                clazz.cast(copy)
        );
    }


    public static Expression rename(Expression tree, int index) {
        if (tree == null) return null;
        switch (tree.tag()) {
            case VARIABLE -> {
                var copyObj = copyNode(tree, Variable.class);
                var variable = copyObj.copynode;
                variable.setIndex(index);
                return variable;
            }
            case CONSTANT, INT -> {
                return tree;
            }
            case PREDICATE -> {
                var copyObj = copyNode(tree, Predicate.class);
                var predicate = copyObj.copynode;
                predicate.setParams(rename(copyObj.tree.getParams(), index));
                return predicate;
            }
            case FUNC -> {
                var copyObj = copyNode(tree, Func.class);
                var function = copyObj.copynode;
                function.setParams(rename(copyObj.tree.getParams(), index));
                return function;
            }
            case NEGATE -> {
                var copyObj = copyNode(tree, Negate.class);
                var negate = copyObj.copynode;
                negate.setL(rename(copyObj.tree.getL(), index));
                return negate;
            }
            case RULE -> {
                var copyObj = copyNode(tree, Rule.class);
                var rule = copyObj.copynode;
                rule.setLhs(rename(copyObj.tree.getLhs(), index));
                rule.setRhs(rename(copyObj.tree.getRhs(), index));
                return rule;
            }
            case LIST -> {
                var copyObj = copyNode(tree, List.class);
                var list = copyObj.copynode;
                list.setHd(rename(copyObj.tree.getHd(), index));
                list.setTl(rename(copyObj.tree.getTl(), index));
                return list;
            }
            default ->  throw new IllegalStateException("Unexpected value: " + tree.tag());
        }

    }

    static class CopyReturnObject<T> {
        T tree;
        T copynode;

        public CopyReturnObject(T tree, T copynode) {
            this.tree = tree;
            this.copynode = copynode;
        }

    }


}
