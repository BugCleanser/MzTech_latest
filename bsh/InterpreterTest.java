/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.junit.Assert
 *  org.junit.Test
 */
package bsh;

import bsh.Interpreter;
import bsh.TestUtil;
import java.lang.ref.WeakReference;
import org.junit.Assert;
import org.junit.Test;

public class InterpreterTest {
    @Test(timeout=10000L)
    public void check_for_memory_leak() throws Exception {
        WeakReference<Object> reference = new WeakReference<Object>(new Interpreter().eval("x = new byte[1024 * 2024]; return x;"));
        while (reference.get() != null) {
            System.gc();
            Thread.sleep(100L);
        }
    }

    @Test
    public void check_system_object() throws Exception {
        TestUtil.eval("bsh.system.foo = \"test\";");
        Object result = TestUtil.eval("return bsh.system.foo;");
        Assert.assertEquals((Object)"test", (Object)result);
        Assert.assertNull((Object)TestUtil.eval("return bsh.system.shutdownOnExit;"));
        Interpreter.setShutdownOnExit(false);
        Assert.assertEquals((Object)Boolean.FALSE, (Object)TestUtil.eval("return bsh.system.shutdownOnExit;"));
    }
}

