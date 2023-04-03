/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  junit.framework.Assert
 *  org.junit.Test
 */
package bsh;

import bsh.BshClassManager;
import bsh.CallStack;
import bsh.NameSpace;
import bsh.TestUtil;
import junit.framework.Assert;
import org.junit.Test;

public class CallStackTest {
    @Test
    public void callStack_should_be_serializable() throws Exception {
        NameSpace nameSpace = new NameSpace(null, new BshClassManager(), "test");
        nameSpace.setLocalVariable("test", "test", false);
        CallStack stack = TestUtil.serDeser(new CallStack(nameSpace));
        Assert.assertEquals((Object)"test", (Object)stack.top().get("test", null));
    }
}

