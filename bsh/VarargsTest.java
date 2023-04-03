/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.junit.Assert
 *  org.junit.Test
 */
package bsh;

import bsh.Interpreter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class VarargsTest {
    @Test
    public void calling_java_varargs_method_should_be_possible() throws Exception {
        Interpreter interpreter = new Interpreter();
        interpreter.set("helper", new ClassWithVarargMethods());
        List list = (List)interpreter.eval("helper.list(1,2,3)");
        Assert.assertEquals(Arrays.asList(1, 2, 3), (Object)list);
    }

    @Test
    public void calling_java_varargs_wit_old_syntax_should_be_possible() throws Exception {
        Interpreter interpreter = new Interpreter();
        interpreter.set("helper", new ClassWithVarargMethods());
        List list = (List)interpreter.eval("helper.list(new Object[] {1,2,3})");
        Assert.assertEquals(Arrays.asList(1, 2, 3), (Object)list);
    }

    public static class ClassWithVarargMethods {
        public List<Object> list(Object ... args) {
            return new ArrayList<Object>(Arrays.asList(args));
        }

        public List<Object> list(List<Object> list, Object ... args) {
            list.addAll(Arrays.asList(args));
            return list;
        }
    }
}

