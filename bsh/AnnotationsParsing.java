/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.junit.Test
 *  org.junit.experimental.categories.Category
 *  org.junit.runner.RunWith
 */
package bsh;

import bsh.FilteredTestRunner;
import bsh.KnownIssue;
import bsh.TestUtil;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

@RunWith(value=FilteredTestRunner.class)
public class AnnotationsParsing {
    @Test
    @Category(value={KnownIssue.class})
    public void annotation_on_method_declaration() throws Exception {
        TestUtil.eval("public int myMethod(final int i) {", "\treturn i * 7;", "}", "return myMethod(6);");
    }
}

