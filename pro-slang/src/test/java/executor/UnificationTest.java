package executor;

import ast.expressions.Variable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnificationTest {

    @Test
    void testUnification(){
        var uni = new Unification();
        var a = new Variable("X", 2);
        var b = new Variable("X", 2);
        assertTrue(uni.unify(a, b, null));
    }
}