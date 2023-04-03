/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.junit.Assert
 *  org.junit.Test
 */
package bsh;

import bsh.EvalError;
import bsh.Interpreter;
import bsh.TestUtil;
import bsh.This;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Assert;
import org.junit.Test;

public class InterpreterConcurrencyTest {
    static final String script = "call(param) {\tclass Echo {\n\t\t\n   \tfinal Object _s;\n\t\t\n   \tEcho(Object s) {\n      \t_s = s;\n   \t}\n\t\t\n   \tpublic Object echo() {\n      \treturn param;\n   \t}\n\t\t\n\t}\n\t\n\treturn new Echo(param).echo();\n}return this;";

    @Test
    public void single_threaded() throws Exception {
        This callable = this.createCallable();
        Assert.assertEquals((Object)"foo", (Object)callable.invokeMethod("call", new Object[]{"foo"}));
        Assert.assertEquals((Object)42, (Object)callable.invokeMethod("call", new Object[]{42}));
    }

    @Test
    public void multi_threaded_callable() throws Exception {
        final AtomicInteger counter = new AtomicInteger();
        String script = "call(v) {\treturn v;}return this;";
        Interpreter interpreter = new Interpreter();
        final This callable = (This)interpreter.eval("call(v) {\treturn v;}return this;");
        Runnable runnable = new Runnable(){

            @Override
            public void run() {
                int value = counter.incrementAndGet();
                try {
                    Assert.assertEquals((Object)value, (Object)callable.invokeMethod("call", new Object[]{value}));
                }
                catch (EvalError evalError) {
                    throw new RuntimeException(evalError);
                }
            }
        };
        TestUtil.measureConcurrentTime(runnable, 30, 30, 100);
    }

    @Test
    public void multi_threaded_class_generation() throws Exception {
        final This callable = this.createCallable();
        final AtomicInteger counter = new AtomicInteger();
        Runnable runnable = new Runnable(){

            @Override
            public void run() {
                try {
                    int i = counter.incrementAndGet();
                    Object o = callable.invokeMethod("call", new Object[]{i});
                    Assert.assertEquals((Object)i, (Object)o);
                }
                catch (EvalError evalError) {
                    throw new RuntimeException(evalError);
                }
            }
        };
        TestUtil.measureConcurrentTime(runnable, 30, 30, 100);
    }

    private This createCallable() throws EvalError {
        Interpreter interpreter = new Interpreter();
        return (This)interpreter.eval(script);
    }
}

