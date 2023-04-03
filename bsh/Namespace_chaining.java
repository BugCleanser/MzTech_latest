/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.junit.Assert
 *  org.junit.Test
 *  org.junit.runner.RunWith
 */
package bsh;

import bsh.FilteredTestRunner;
import bsh.Interpreter;
import bsh.NameSpace;
import bsh.UtilEvalError;
import java.io.StringReader;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(value=FilteredTestRunner.class)
public class Namespace_chaining {
    @Test
    public void namespace_nesting() throws UtilEvalError {
        NameSpace root = new NameSpace((NameSpace)null, "root");
        NameSpace child = new NameSpace(root, "child");
        root.setLocalVariable("bar", 42, false);
        Assert.assertEquals((Object)42, (Object)child.getVariable("bar"));
        child.setLocalVariable("bar", 4711, false);
        Assert.assertEquals((Object)4711, (Object)child.getVariable("bar"));
        Assert.assertEquals((Object)42, (Object)root.getVariable("bar"));
    }

    @Test
    public void jdownloader_test_case() throws Exception {
        Interpreter root = new Interpreter();
        Interpreter child = new Interpreter(new StringReader(""), System.out, System.err, false, new NameSpace(root.getNameSpace(), "child"));
        child.eval("int bar=4711;");
        root.eval("bar;");
        child.eval("bar;");
        root.eval("void foo() { System.out.println(\"bar is \" + bar + \". Namespace is \" + this.namespace + \". Parent namespace is \" + this.namespace.getParent()); }");
        root.eval("foo();");
        System.out.println("child.get(\"bar\") -> " + child.get("bar"));
        child.eval("foo();");
    }
}

