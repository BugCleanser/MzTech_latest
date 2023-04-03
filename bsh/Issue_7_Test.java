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
import bsh.OldScriptsTest;
import java.io.File;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

@RunWith(value=FilteredTestRunner.class)
public class Issue_7_Test {
    @Test
    @Category(value={KnownIssue.class})
    public void run_script_class13() throws Exception {
        new OldScriptsTest.TestBshScript(new File("tests/test-scripts/class13.bsh")).runTest();
    }
}

