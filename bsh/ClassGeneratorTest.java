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

import bsh.Capabilities;
import bsh.FilteredTestRunner;
import bsh.KnownIssue;
import bsh.TestUtil;
import java.util.concurrent.Callable;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

@RunWith(value=FilteredTestRunner.class)
public class ClassGeneratorTest {
    @Test
    public void create_class_with_default_constructor() throws Exception {
        TestUtil.eval("class X1 {}");
    }

    @Test
    public void creating_class_should_not_set_accessibility() throws Exception {
        Assert.assertFalse((String)"pre: no accessibility should be set", (boolean)Capabilities.haveAccessibility());
        TestUtil.eval("class X1 {}");
        Assert.assertFalse((String)"post: no accessibility should be set", (boolean)Capabilities.haveAccessibility());
    }

    @Test
    public void create_instance() throws Exception {
        Assert.assertNotNull((Object)TestUtil.eval("class X2 {}", "return new X2();"));
    }

    @Test
    public void constructor_args() throws Exception {
        Object[] oa = (Object[])TestUtil.eval("class X3 implements java.util.concurrent.Callable {", "Object _instanceVar;", "public X3(Object arg) { _instanceVar = arg; }", "public Object call() { return _instanceVar; }", "}", "return new Object[] { new X3(0), new X3(1) } ");
        Assert.assertEquals((Object)0, ((Callable)oa[0]).call());
        Assert.assertEquals((Object)1, ((Callable)oa[1]).call());
    }

    @Test
    public void outer_namespace_visibility() throws Exception {
        Callable callable = (Callable)TestUtil.eval("class X4 implements java.util.concurrent.Callable {", "public Object call() { return var; }", "}", "var = 0;", "a = new X4();", "var = 1;", "return a;");
        Assert.assertEquals((Object)1, callable.call());
    }

    @Test
    public void static_fields_should_be_frozen() throws Exception {
        Callable callable = (Callable)TestUtil.eval("var = 0;", "class X5 implements java.util.concurrent.Callable {", "static final Object VAR = var;", "public Object call() { return VAR; }", "}", "a = new X5();", "var = 1;", "return a;");
        Assert.assertEquals((Object)0, callable.call());
    }

    @Test
    @Category(value={KnownIssue.class})
    public void define_interface_with_constants() throws Exception {
        TestUtil.eval("interface Test { public static final int x = 1; }");
        TestUtil.eval("interface Test { static final int x = 1; }");
        TestUtil.eval("interface Test { final int x = 1; }");
        TestUtil.eval("interface Test { public static int x = 1; }");
        TestUtil.eval("interface Test { static int x = 1; }");
        TestUtil.eval("interface Test { int x = 1; }");
    }
}

