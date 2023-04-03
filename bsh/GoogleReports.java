/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.junit.Assert
 *  org.junit.Test
 *  org.junit.runner.RunWith
 */
package bsh;

import bsh.Capabilities;
import bsh.FilteredTestRunner;
import bsh.Interpreter;
import bsh.ParseException;
import bsh.TestUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(value=FilteredTestRunner.class)
public class GoogleReports {
    @Test
    public void while_loop() throws Exception {
        int loopCount = 0;
        if (++loopCount > 100) {
            // empty if block
        }
        Assert.assertEquals((long)1L, (long)loopCount);
        loopCount = (Integer)TestUtil.eval("int loopCount = 0;", "do{", "\tloopCount++;", "\tif (loopCount > 100) break;", "\tif (true) continue;", "} while (false);", "return loopCount");
        Assert.assertEquals((long)1L, (long)loopCount);
        loopCount = (Integer)TestUtil.eval("int loopCount = 0;", "while (loopCount < 1) {", "\tloopCount++;", "\tif (loopCount > 100) return loopCount;", "\tif (true) continue;", "}", "return loopCount");
        Assert.assertEquals((long)1L, (long)loopCount);
        Assert.assertEquals((Object)Boolean.TRUE, (Object)TestUtil.eval("while(true) { break; return false; } return true;"));
        Assert.assertEquals((Object)Boolean.TRUE, (Object)TestUtil.eval("do { break; return false; } while(true); return true;"));
        loopCount = (Integer)TestUtil.eval("int loopCount = 0;", "while (++loopCount < 2);", "return loopCount");
        Assert.assertEquals((long)2L, (long)loopCount);
        loopCount = (Integer)TestUtil.eval("int loopCount = 0;", "do { } while (++loopCount < 2);", "return loopCount");
        Assert.assertEquals((long)2L, (long)loopCount);
    }

    @Test
    public void accessibility_issue_a() throws Exception {
        Interpreter interpreter = new Interpreter();
        interpreter.set("x", this);
        Capabilities.setAccessibility(true);
        Assert.assertEquals((Object)"private-Integer", (Object)interpreter.eval("return x.issue6(new Integer(9));"));
        Capabilities.setAccessibility(false);
        Assert.assertEquals((Object)"public-Number", (Object)interpreter.eval("return x.issue6(new Integer(9));"));
    }

    @Test
    public void accessibility_issue_b() throws Exception {
        Interpreter interpreter = new Interpreter();
        interpreter.set("x", this);
        Assert.assertEquals((Object)"public-Number", (Object)interpreter.eval("return x.issue6(new Integer(9));"));
        Capabilities.setAccessibility(true);
        Assert.assertEquals((Object)"private-Integer", (Object)interpreter.eval("return x.issue6(new Integer(9));"));
    }

    @Test(expected=ParseException.class)
    public void parse_error() throws Exception {
        TestUtil.eval("\u0001;");
    }

    @Test
    public void return_in_try_block_does_not_return() throws Exception {
        Assert.assertEquals((Object)"in try block", (Object)TestUtil.eval("try {", "   return \"in try block\";", "} finally {}return \"after try block\";"));
    }

    @Test
    public void override_method() throws Exception {
        Assert.assertEquals((Object)"changed", (Object)TestUtil.eval("foo() { return \"original\";}", "foo() { return \"changed\";}", "return foo();"));
    }

    @Test
    public void binary_operator_or() throws Exception {
        Assert.assertEquals((Object)true, (Object)TestUtil.eval("return true | true"));
        Assert.assertEquals((Object)true, (Object)TestUtil.eval("return true | false"));
        Assert.assertEquals((Object)true, (Object)TestUtil.eval("return false | true"));
        Assert.assertEquals((Object)false, (Object)TestUtil.eval("return false | false"));
    }

    @Test
    public void binary_operator_and() throws Exception {
        Assert.assertEquals((Object)true, (Object)TestUtil.eval("return true & true"));
        Assert.assertEquals((Object)false, (Object)TestUtil.eval("return true & false"));
        Assert.assertEquals((Object)false, (Object)TestUtil.eval("return false & true"));
        Assert.assertEquals((Object)false, (Object)TestUtil.eval("return false & false"));
    }

    @Test
    public void binary_operator_xor() throws Exception {
        Assert.assertEquals((Object)false, (Object)TestUtil.eval("return true ^ true"));
        Assert.assertEquals((Object)true, (Object)TestUtil.eval("return true ^ false"));
        Assert.assertEquals((Object)true, (Object)TestUtil.eval("return false ^ true"));
        Assert.assertEquals((Object)false, (Object)TestUtil.eval("return false ^ false"));
    }

    private static String issue6(Integer ignored) {
        return "private-Integer";
    }

    public static String issue6(Number ignored) {
        return "public-Number";
    }
}

