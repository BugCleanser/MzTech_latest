/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  junit.framework.Assert
 *  org.junit.Test
 */
package bsh;

import bsh.TestUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import junit.framework.Assert;
import org.junit.Test;

public class Issue_93_Test {
    @Test
    public void try_catch_finally() throws Exception {
        ArrayList calls = new ArrayList();
        Object result = TestUtil.eval(Collections.singletonMap("calls", calls), "calls.add(\"start\");", "try {", "\tcalls.add(\"try\");", "} catch (Exception e) {", "\tcalls.add(\"catch\");", "} finally {", "\tcalls.add(\"finally\");", "}", "calls.add(\"after\");", "return \"return after try..catch..finally\";");
        Assert.assertEquals((Object)"return after try..catch..finally", (Object)result);
        Assert.assertEquals((String)("calls are :" + ((Object)calls).toString()), Arrays.asList("start", "try", "finally", "after"), calls);
    }

    @Test
    public void execute_finally_when_try_block_contains_return() throws Exception {
        ArrayList calls = new ArrayList();
        Object result = TestUtil.eval(Collections.singletonMap("calls", calls), "calls.add(\"start\");", "try {", "\tcalls.add(\"try\");", "\treturn \"return from try\";", "} catch (Exception e) {", "\tcalls.add(\"catch\");", "} finally {", "\tcalls.add(\"finally\");", "}", "calls.add(\"after\");", "return \"return after try..catch..finally\";");
        Assert.assertEquals((Object)"return from try", (Object)result);
        Assert.assertEquals((String)("calls are :" + ((Object)calls).toString()), Arrays.asList("start", "try", "finally"), calls);
    }

    @Test
    public void execute_finally_block_when_catch_block_throws_exception() throws Exception {
        ArrayList calls = new ArrayList();
        Object result = TestUtil.eval(Collections.singletonMap("calls", calls), "calls.add(\"start\");", "try {", "\tcalls.add(\"try\");", "\tthrow new Exception(\"inside try\");", "} catch (Exception e) {", "\tcalls.add(\"catch\");", "\tthrow new Exception(\"inside catch\");", "} finally {", "\tcalls.add(\"finally\");", "\treturn \"return from finally\";", "}", "calls.add(\"after\");", "return \"return after try..catch..finally\";");
        Assert.assertEquals((Object)"return from finally", (Object)result);
        Assert.assertEquals((String)("calls are :" + ((Object)calls).toString()), Arrays.asList("start", "try", "catch", "finally"), calls);
    }

    @Test
    public void execute_finally_block_when_catch_block_contains_return_statement() throws Exception {
        ArrayList calls = new ArrayList();
        Object result = TestUtil.eval(Collections.singletonMap("calls", calls), "calls.add(\"start\");", "try {", "\tcalls.add(\"try\");", "\tthrow new Exception(\"inside try\");", "} catch (Exception e) {", "\tcalls.add(\"catch\");", "\treturn \"return from catch\";", "} finally {", "\tcalls.add(\"finally\");", "\treturn \"return from finally\";", "}", "calls.add(\"after\");", "return \"return after try..catch..finally\";");
        Assert.assertEquals((Object)"return from finally", (Object)result);
        Assert.assertEquals((String)("calls are :" + ((Object)calls).toString()), Arrays.asList("start", "try", "catch", "finally"), calls);
    }

    @Test
    public void execute_finally_block_when_try_block_contains_return_statement() throws Exception {
        Object result = TestUtil.eval("try {", "\treturn \"return from try\";", "} finally {", "\treturn \"return from finally\";", "}", "return \"return after try..finally\";");
        Assert.assertEquals((Object)"return from finally", (Object)result);
    }
}

