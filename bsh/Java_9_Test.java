/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.junit.Test
 *  org.junit.runner.RunWith
 */
package bsh;

import bsh.EvalError;
import bsh.FilteredTestRunner;
import bsh.Interpreter;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(value=FilteredTestRunner.class)
public class Java_9_Test {
    @Test
    public void do_not_access_non_open_methods() throws EvalError {
        String script = "import java.net.URL;\nurl = new URL(\"https://github.com/beanshell\");\nurlConnection = url.openConnection();\nstream = urlConnection.getInputStream();\nstream.close();\n";
        new Interpreter().eval(script);
    }
}

