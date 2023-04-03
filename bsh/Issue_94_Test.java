/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  junit.framework.Assert
 *  org.junit.Test
 */
package bsh;

import bsh.TestUtil;
import junit.framework.Assert;
import org.junit.Test;

public class Issue_94_Test {
    @Test
    public void final_in_method_parameter() throws Exception {
        Object result = TestUtil.eval("String test(final String text){", "   return text;", "}", "return test(\"abc\");");
        Assert.assertEquals((Object)"abc", (Object)result);
    }
}

