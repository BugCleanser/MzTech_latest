/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  junit.framework.Test
 *  junit.framework.TestCase
 *  junit.framework.TestSuite
 *  org.junit.Assert
 */
package bsh;

import bsh.Interpreter;
import bsh.KnownIssue;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Assert;

public class OldScriptsTest {
    private static final Set<String> KNOWN_FAILING_TESTS = new HashSet<String>();

    public static Test suite() throws Exception {
        TestSuite suite = new TestSuite();
        File baseDir = new File("tests/test-scripts");
        try {
            new TestBshScript(new File(baseDir, "Fail.bsh")).runTest();
            Assert.fail((String)"Fail.bsh should fail!");
        }
        catch (AssertionError assertionError) {
            // empty catch block
        }
        OldScriptsTest.addTests(baseDir, suite);
        return suite;
    }

    private static void addTests(File baseDir, TestSuite suite) {
        File[] files = baseDir.listFiles();
        if (files != null) {
            for (File file : files) {
                String name = file.getName();
                if (!file.isFile() || !name.endsWith(".bsh") || "TestHarness.bsh".equals(name) || "RunAllTests.bsh".equals(name) || "Assert.bsh".equals(name) || "Fail.bsh".equals(name)) continue;
                if (KnownIssue.SKIP_KOWN_ISSUES && KNOWN_FAILING_TESTS.contains(name)) {
                    System.out.println("skipping test " + file);
                    continue;
                }
                suite.addTest((Test)new TestBshScript(file));
            }
        }
    }

    static {
        KNOWN_FAILING_TESTS.add("class13.bsh");
        KNOWN_FAILING_TESTS.add("class3.bsh");
        KNOWN_FAILING_TESTS.add("classinterf1.bsh");
        KNOWN_FAILING_TESTS.add("commands.bsh");
        KNOWN_FAILING_TESTS.add("run.bsh");
    }

    static class TestBshScript
    extends TestCase {
        private File _file;

        public TestBshScript(File file) {
            this._file = file;
        }

        public String getName() {
            return this._file.getName();
        }

        public void runTest() throws Exception {
            System.out.println(((Object)((Object)this)).getClass().getResource("/bsh/commands/cd.bsh"));
            System.out.println("file is " + this._file.getAbsolutePath());
            Interpreter interpreter = new Interpreter();
            String path = '\"' + this._file.getParentFile().getAbsolutePath().replace('\\', '/') + '\"';
            interpreter.eval("path=" + path + ';');
            interpreter.eval("cd(" + path + ");");
            interpreter.eval(new FileReader(this._file));
            TestBshScript.assertEquals((String)"'test_completed' flag check", (Object)Boolean.TRUE, (Object)interpreter.get("test_completed"));
            TestBshScript.assertEquals((String)"'test_failed' flag check", (Object)Boolean.FALSE, (Object)interpreter.get("test_failed"));
        }
    }
}

