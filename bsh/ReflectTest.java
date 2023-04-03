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
import bsh.Reflect;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(value=FilteredTestRunner.class)
public class ReflectTest {
    @Test
    public void findMostSpecificSignature() {
        int value = Reflect.findMostSpecificSignature(new Class[]{null}, new Class[][]{{Double.TYPE}, {char[].class}, {String.class}, {Object.class}});
        Assert.assertEquals((String)"most specific String class", (long)2L, (long)value);
        value = Reflect.findMostSpecificSignature(new Class[]{null}, new Class[][]{{Double.TYPE}, {char[].class}, {Object.class}, {String.class}});
        Assert.assertEquals((String)"most specific String class", (long)3L, (long)value);
        value = Reflect.findMostSpecificSignature(new Class[]{null}, new Class[][]{{Double.TYPE}, {char[].class}, {Integer.class}, {String.class}});
        Assert.assertEquals((String)"most specific String class", (long)3L, (long)value);
        value = Reflect.findMostSpecificSignature(new Class[]{null}, new Class[][]{{Double.TYPE}, {char[].class}, {Number.class}, {Integer.class}});
        Assert.assertEquals((String)"most specific Integer class", (long)3L, (long)value);
        value = Reflect.findMostSpecificSignature(new Class[]{null}, new Class[][]{{Double.TYPE}, {char[].class}, {Object.class}, {Boolean.TYPE}});
        Assert.assertEquals((String)"most specific Object class", (long)2L, (long)value);
        value = Reflect.findMostSpecificSignature(new Class[]{null}, new Class[][]{{Double.TYPE}, {char[].class}, {Boolean.TYPE}});
        Assert.assertEquals((String)"most specific char[] class", (long)1L, (long)value);
    }
}

