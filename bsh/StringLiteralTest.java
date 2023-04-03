/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.junit.Assert
 *  org.junit.Test
 */
package bsh;

import bsh.EvalError;
import bsh.Interpreter;
import org.junit.Assert;
import org.junit.Test;

public class StringLiteralTest {
    private static final String ESCAPE_CHAR = "\\";

    @Test
    public void parse_string_literal() throws Exception {
        this.assertStringParsing("test", DelimiterMode.SINGLE_LINE);
    }

    @Test
    public void parse_long_string_literal_singleline() throws Exception {
        this.assertStringParsing("test", DelimiterMode.MULTI_LINE);
    }

    @Test
    public void parse_string_literal_with_escaped_chars() throws Exception {
        this.assertStringParsing("\\\n\t\r\"'", "\\\\\\n\\t\\r\\\"\\'", DelimiterMode.SINGLE_LINE);
    }

    @Test
    public void parse_string_literal_with_special_chars_multiline() throws Exception {
        this.assertStringParsing("\t\n\\\"'", "\t\n\\\"'", DelimiterMode.MULTI_LINE);
    }

    @Test
    public void parse_unicode_literals() throws Exception {
        this.assertStringParsing("\u00ff", "\\u00FF", DelimiterMode.SINGLE_LINE);
    }

    @Test
    public void parse_long_string_literal_multiline() throws Exception {
        this.assertStringParsing("test\ntest", DelimiterMode.MULTI_LINE);
    }

    private void assertStringParsing(String s, DelimiterMode mode) throws EvalError {
        this.assertStringParsing(s, s, mode);
    }

    private void assertStringParsing(String expected, String source, DelimiterMode mode) throws EvalError {
        Interpreter interpreter = new Interpreter();
        Assert.assertEquals((Object)expected, (Object)interpreter.eval("return " + mode.delimiter() + source + mode.delimiter() + ""));
    }

    private static enum DelimiterMode {
        SINGLE_LINE("\""),
        MULTI_LINE("\"\"\"");

        private final String _delimiter;

        private DelimiterMode(String delimiter) {
            this._delimiter = delimiter;
        }

        public String delimiter() {
            return this._delimiter;
        }
    }
}

