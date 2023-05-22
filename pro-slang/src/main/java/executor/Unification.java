package executor;

import ast.Expression;
import ast.Tag;
import ast.expressions.*;

import java.util.Objects;

import static executor.Binding.bind;

public class Unification {

    private Env env; // newenv


    public boolean unify(Expression a, Expression b, Env oldEnv) {
        env = oldEnv;

        if (a == null || b == null)
            return a == b;

        else if (a.tag().equals(b.tag())) {
            switch (a.tag()) {
                case VARIABLE -> {
                    var va = (Variable) a;
                    var vb = (Variable) b;
                    var uniEnv = new Binding();

                    if (uniEnv.bound(a, oldEnv))
                        return unify(uniEnv.getVal(), b, oldEnv);

                    else if (uniEnv.bound(b, oldEnv))
                        return unify(a, uniEnv.getVal(), oldEnv);

                    else if (va.getVid().equals(vb.getVid()) && va.getIndex().equals(vb.getIndex()))
                        return true;

                    else {
                        env = bind(a, b, oldEnv);
                        return true;
                    }
                }
                case CONSTANT -> {
                    var ca = (Constant) a;
                    var cb = (Constant) b;
                    return Objects.equals(ca.getCid(), cb.getCid());
                }
                case INT -> {
                    var ca = (Intcon) a;
                    var cb = (Intcon) b;
                    return Objects.equals(ca.getN(), cb.getN());
                }
                case FUNC -> {
                    var fa = (Func) a;
                    var fb = (Func) b;
                    if (Objects.equals(fa.getId(), fb.getId()))
                        return unify(fa.getParams(), fb.getParams(), oldEnv);
                    else return
                            false;
                }
                case PREDICATE -> {
                    var pa = (Predicate) a;
                    var pb = (Predicate) b;
                    if (Objects.equals(pa.getId(), pb.getId()))
                        return unify(pa.getParams(), pb.getParams(), oldEnv);
                    else return
                            false;
                }
                case LIST -> {
                    var la = (List) a;
                    var lb = (List) b;
                     if (unify(la.getHd(), lb.getHd(), oldEnv))
                        return unify(la.getTl(), lb.getTl(), this.env);
                    else
                        return false;
                }
                default -> throw new IllegalStateException("Unexpected value whike unification: " + a.tag());
            }
        } else if (a.tag() == Tag.VARIABLE) {
            var uniEnv = new Binding();
            if (uniEnv.bound(a, oldEnv)) {
                return unify(uniEnv.getVal(), b, oldEnv);
            } else {
                this.env = bind(a, b, oldEnv);
                return true;
            }
        } else if (b.tag() == Tag.VARIABLE) {
            return unify(b, a, oldEnv);
        }
        return false;
    }

    public Env getEnv() {
        return env;
    }


}
