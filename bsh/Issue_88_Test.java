/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  junit.framework.Assert
 *  org.junit.Test
 *  org.junit.runner.RunWith
 */
package bsh;

import bsh.Capabilities;
import bsh.FilteredTestRunner;
import bsh.Interpreter;
import bsh.TestUtil;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(value=FilteredTestRunner.class)
public class Issue_88_Test {
    @Test
    public void call_of_public_inherited_method_from_non_public_class_without_accessibilty() throws Exception {
        Capabilities.setAccessibility(false);
        Interpreter interpreter = new Interpreter();
        interpreter.set("x", new Implementation());
        Assert.assertEquals((Object)"public String", (Object)interpreter.eval("x.method(\"foo\");"));
    }

    @Test
    public void call_of_public_inherited_method_from_non_public_class_with_accessibilty() throws Exception {
        Capabilities.setAccessibility(true);
        Interpreter interpreter = new Interpreter();
        interpreter.set("x", new Implementation());
        Assert.assertEquals((Object)"public String", (Object)interpreter.eval("x.method(\"foo\");"));
    }

    @Test
    public void community_test_cases() throws Exception {
        Assert.assertEquals((Object)0, (Object)TestUtil.eval("Collections.unmodifiableList(new ArrayList()).size();"));
        Assert.assertEquals((Object)0, (Object)TestUtil.eval("new HashMap().entrySet().size();"));
        Assert.assertEquals((Object)Boolean.FALSE, (Object)TestUtil.eval("new HashMap().keySet().iterator().hasNext();"));
    }

    public class Implementation
    extends AbstractImplementation {
        public Object method(CharSequence param) {
            return "public CharSequence";
        }

        public Object method(Object param) {
            return "public Object";
        }
    }

    private abstract class AbstractImplementation
    implements PublicWithoutMethod {
        private AbstractImplementation() {
        }

        @Override
        public Object method(String param) {
            return "public String";
        }

        private Object method(Object param) {
            return "private Object";
        }
    }

    public static interface PublicWithoutMethod
    extends Public {
    }

    public static interface Public {
        public Object method(String var1);
    }
}

