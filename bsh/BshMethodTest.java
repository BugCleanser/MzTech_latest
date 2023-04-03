/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  junit.framework.Assert
 *  org.junit.Test
 */
package bsh;

import bsh.BSHBlock;
import bsh.BshMethod;
import bsh.Modifiers;
import bsh.NameSpace;
import junit.framework.Assert;
import org.junit.Test;

public class BshMethodTest {
    @Test
    public void testEqualsObject_subclassEquality() {
        String name = "testMethod";
        class SubMethod
        extends BshMethod {
            public SubMethod(String name, Class returnType, String[] paramNames, Class[] paramTypes, BSHBlock methodBody, NameSpace declaringNameSpace, Modifiers modifiers) {
                super(name, returnType, paramNames, paramTypes, methodBody, declaringNameSpace, modifiers);
            }
        }
        SubMethod subInst = new SubMethod("testMethod", Integer.class, new String[0], new Class[0], null, null, null);
        BshMethod supInst = new BshMethod("testMethod", Integer.class, new String[0], new Class[0], null, null, null);
        Assert.assertFalse((String)"Subclasses should not be equal to super classes", (boolean)supInst.equals(subInst));
    }

    @Test
    public void testHashCode_contract() {
        String name = "testMethod";
        BshMethod method1 = new BshMethod("testMethod", Integer.class, new String[0], new Class[0], null, null, null);
        BshMethod method2 = new BshMethod("testMethod", Integer.class, new String[0], new Class[0], null, null, null);
        Assert.assertTrue((String)"precondition check for test failed.", (boolean)method2.equals(method1));
        Assert.assertEquals((String)"Equal classes should have equal hashcodes", (int)method2.hashCode(), (int)method1.hashCode());
    }
}

