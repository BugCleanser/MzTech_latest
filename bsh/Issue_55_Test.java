/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  junit.framework.Assert
 *  org.junit.Test
 *  org.junit.experimental.categories.Category
 *  org.junit.runner.RunWith
 */
package bsh;

import bsh.BshScriptEngineFactory;
import bsh.ExternalNameSpace;
import bsh.FilteredTestRunner;
import bsh.Interpreter;
import bsh.NotSuitedFor_Java5_OrLower;
import bsh.Primitive;
import javax.script.ScriptException;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

@RunWith(value=FilteredTestRunner.class)
public class Issue_55_Test {
    @Category(value={NotSuitedFor_Java5_OrLower.class})
    @Test
    public void check_BshScriptEngineFactory() throws Exception {
        String script = "a = null; return \"a=\" + a;\n";
        Object interpreterResult = new Interpreter().eval("a = null; return \"a=\" + a;\n");
        Object scriptEngineResult = new BshScriptEngineFactory().getScriptEngine().eval("a = null; return \"a=\" + a;\n");
        Assert.assertEquals((Object)interpreterResult, (Object)scriptEngineResult);
    }

    @Test
    public void check_ExternalNameSpace() throws Exception {
        ExternalNameSpace externalNameSpace = new ExternalNameSpace();
        externalNameSpace.setVariable("a", Primitive.NULL, false);
        Assert.assertTrue((String)"map should contain variable 'a'", (boolean)externalNameSpace.getMap().containsKey("a"));
        Assert.assertNull((String)"variable 'a' should have value <NULL>", (Object)externalNameSpace.getMap().get("a"));
    }

    @Category(value={NotSuitedFor_Java5_OrLower.class})
    @Test
    public void issue_67() throws Exception {
        String script = "print(\"test\";";
        try {
            new BshScriptEngineFactory().getScriptEngine().eval("print(\"test\";");
            Assert.fail((String)"expected script exception");
        }
        catch (ScriptException e) {
            Assert.assertEquals((int)1, (int)e.getLineNumber());
        }
    }
}

