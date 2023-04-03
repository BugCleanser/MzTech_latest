/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.junit.Assert
 *  org.junit.Test
 *  org.junit.experimental.categories.Category
 *  org.junit.runner.RunWith
 */
package bsh;

import bsh.EvalError;
import bsh.FilteredTestRunner;
import bsh.Interpreter;
import bsh.ParseException;
import bsh.ProjectCoinFeature;
import bsh.TestUtil;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

@RunWith(value=FilteredTestRunner.class)
@Category(value={ProjectCoinFeature.class})
public class Project_Coin_Test {
    @Test
    @Category(value={ProjectCoinFeature.class})
    public void integer_literal_enhancements() throws Exception {
        Interpreter interpreter = new Interpreter();
        Assert.assertEquals((String)"0x99", (Object)153, (Object)interpreter.eval("return 0x99;"));
        Assert.assertEquals((String)"0231", (Object)153, (Object)interpreter.eval("return 0231;"));
        Assert.assertEquals((String)"0b10011001", (Object)153, (Object)interpreter.eval("return 0b10011001;"));
        Assert.assertEquals((String)"0b_1001_1001", (Object)153, (Object)interpreter.eval("return 0b_1001_1001;"));
        Assert.assertEquals((String)"0x_9_9", (Object)153, (Object)interpreter.eval("return 0x_9_9;"));
        Assert.assertEquals((String)"15_500_000_000L", (Object)15500000000L, (Object)interpreter.eval("return 15_500_000_000L;"));
    }

    @Test
    @Category(value={ProjectCoinFeature.class})
    public void diamond_operator() throws Exception {
        TestUtil.eval("List<String> list = new ArrayList<>()");
        Object anagrams = TestUtil.eval("Map<String, List<String>> anagrams = new HashMap<>();return anagrams;");
        Assert.assertNotNull((Object)anagrams);
        Assert.assertTrue((String)anagrams.getClass().getName(), (boolean)(anagrams instanceof HashMap));
    }

    @Test
    @Category(value={Project_Coin_Test.class})
    public void try_with_resource_parsing() throws Exception {
        TestUtil.eval("try {", "  ByteArrayOutputStream x = new ByteArrayOutputStream();", "} catch (Exception e) {", "}\n");
        TestUtil.eval("try {", "  ByteArrayOutputStream x = new ByteArrayOutputStream(); ByteArrayOutputStream y = new ByteArrayOutputStream();", "} catch (Exception e) {", "}\n");
        TestUtil.eval("try {", "  x = new ByteArrayOutputStream(); y = new ByteArrayOutputStream();", "} catch (Exception e) {", "}\n");
    }

    @Test
    @Category(value={Project_Coin_Test.class})
    public void try_with_resource() throws Exception {
        Interpreter interpreter = new Interpreter();
        final AtomicBoolean closed = new AtomicBoolean(false);
        final IOException fromWrite = new IOException("exception from write");
        final IOException fromClose = new IOException("exception from close");
        OutputStream autoclosable = new OutputStream(){

            @Override
            public void write(int b) throws IOException {
                throw fromWrite;
            }

            @Override
            public void close() throws IOException {
                closed.set(true);
                throw fromClose;
            }
        };
        try {
            interpreter.set("autoclosable", autoclosable);
            interpreter.eval("try {\n   x = new BufferedOutputStream(autoclosable);\n\tx.write(42);\n} catch (Exception e) {\n\tthrownException = e;\n}\n");
            Assert.fail((String)"expected exception");
        }
        catch (EvalError evalError) {
            if (evalError instanceof ParseException) {
                throw evalError;
            }
            Throwable e = evalError.getCause();
            Assert.assertSame((Object)fromWrite, (Object)e);
            interpreter.set("exception", e);
            Object suppressed = interpreter.eval("return exception.getSuppressed();");
            Assert.assertSame((Object)fromClose, (Object)suppressed);
        }
        Assert.assertTrue((String)"stream should be closed", (boolean)closed.get());
    }

    @Test
    @Category(value={Project_Coin_Test.class})
    public void switch_on_strings() throws Exception {
        Object result = TestUtil.eval("switch(\"hurz\") {\n", "\tcase \"bla\": return 1;", "\tcase \"foo\": return 2;", "\tcase \"hurz\": return 3;", "\tcase \"igss\": return 4;", "\tdefault: return 5;", "}\n");
        Assert.assertEquals((Object)result, (Object)3);
    }
}

