/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.junit.Assert
 *  org.junit.Test
 *  org.junit.runner.RunWith
 */
package bsh;

import bsh.EvalError;
import bsh.FilteredTestRunner;
import bsh.PreparsedScript;
import bsh.TestUtil;
import java.net.URL;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(value=FilteredTestRunner.class)
public class PreparsedScriptTest {
    private ClassLoader _classLoader = new ClassLoader(){

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            System.out.println("find class " + name);
            return super.findClass(name);
        }

        @Override
        protected URL findResource(String name) {
            System.out.println("find resource " + name);
            return super.findResource(name);
        }
    };

    @Test
    public void x() throws Exception {
        PreparsedScript preparsedScript = new PreparsedScript("", this._classLoader);
        preparsedScript.invoke(Collections.emptyMap());
    }

    @Test
    public void y() throws Exception {
        PreparsedScript f = new PreparsedScript("return x;", this._classLoader);
        Assert.assertEquals((Object)"hurz", (Object)f.invoke(Collections.singletonMap("x", "hurz")));
        Assert.assertEquals((Object)"foo", (Object)f.invoke(Collections.singletonMap("x", "foo")));
    }

    @Test
    public void z() throws Exception {
        PreparsedScript f = new PreparsedScript("import javax.crypto.*;import javax.crypto.interfaces.*;import javax.crypto.spec.*;if (foo != void) print (\"check\");class Echo {\n\n   public Object echo() {\n      return param;\n   }\n\n}\n\nreturn new Echo().echo();", this._classLoader);
        Assert.assertEquals((Object)"bla", (Object)f.invoke(Collections.singletonMap("param", "bla")));
        System.out.println("second call");
        Assert.assertEquals((Object)"blubb", (Object)f.invoke(Collections.singletonMap("param", "blubb")));
    }

    @Test
    public void multi_threaded() throws Exception {
        final AtomicInteger counter = new AtomicInteger();
        String script = "return v;";
        final PreparsedScript f = new PreparsedScript("return v;", this._classLoader);
        Assert.assertEquals((Object)"x", (Object)f.invoke(Collections.singletonMap("v", "x")));
        Runnable runnable = new Runnable(){

            @Override
            public void run() {
                int value = counter.incrementAndGet();
                try {
                    Assert.assertEquals((Object)value, (Object)f.invoke(Collections.singletonMap("v", value)));
                }
                catch (EvalError evalError) {
                    throw new RuntimeException(evalError);
                }
            }
        };
        long time = TestUtil.measureConcurrentTime(runnable, 30, 30, 1000);
        System.out.println(TimeUnit.NANOSECONDS.toMillis(time));
    }

    @Test
    public void param_with_name_result() throws Exception {
        AtomicInteger result = new AtomicInteger();
        PreparsedScript f = new PreparsedScript("result.set(result.get() + 42);", this._classLoader);
        f.invoke(Collections.singletonMap("result", result));
        Assert.assertEquals((long)42L, (long)result.get());
        f.invoke(Collections.singletonMap("result", result));
        Assert.assertEquals((long)84L, (long)result.get());
    }
}

