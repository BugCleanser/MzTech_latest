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
import org.junit.Assert;
import org.junit.Test;

public class BshSerializationTest {
    @Test
    public void testNullValueSerialization() throws Exception {
        Interpreter origInterpreter = new Interpreter();
        origInterpreter.eval("myNull = null;");
        Assert.assertNull((Object)origInterpreter.eval("myNull"));
        Interpreter deserInterpreter = TestUtil.serDeser(origInterpreter);
        Assert.assertNull((Object)deserInterpreter.eval("myNull"));
    }

    @Test
    public void testSpecialNullSerialization() throws Exception {
        Interpreter originalInterpreter = new Interpreter();
        originalInterpreter.eval("myNull = null;");
        Assert.assertTrue((boolean)((Boolean)originalInterpreter.eval("myNull == null")));
        Interpreter deserInterpreter = TestUtil.serDeser(originalInterpreter);
        Assert.assertTrue((boolean)((Boolean)deserInterpreter.eval("myNull == null")));
    }

    @Test
    public void testMethodSerialization() throws Exception {
        Interpreter origInterpreter = new Interpreter();
        origInterpreter.eval("int method() { return 1337; }");
        Assert.assertEquals((Object)1337, (Object)origInterpreter.eval("method()"));
        Interpreter deserInterpreter = TestUtil.serDeser(origInterpreter);
        Assert.assertEquals((Object)1337, (Object)deserInterpreter.eval("method()"));
    }
}

