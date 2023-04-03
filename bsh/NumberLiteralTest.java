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
import bsh.TestUtil;
import java.math.BigInteger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(value=FilteredTestRunner.class)
public class NumberLiteralTest {
    @Test
    public void integer_literal_enhancements_hex() throws Exception {
        Assert.assertEquals((String)"0x99", (Object)153, (Object)TestUtil.eval("return 0x99;"));
    }

    @Test
    public void integer_literal_enhancements_octal() throws Exception {
        Assert.assertEquals((String)"0231", (Object)153, (Object)TestUtil.eval("return 0231;"));
    }

    @Test
    public void integer_literal_enhancements_binary() throws Exception {
        Assert.assertEquals((String)"0b10011001", (Object)153, (Object)TestUtil.eval("return 0b10011001;"));
    }

    @Test
    public void parsing_hex_literal() throws Exception {
        Assert.assertEquals((Object)0xFF0000, (Object)TestUtil.eval("return 0xff0000;"));
    }

    @Test
    public void parsing_large_hex_literal() throws Exception {
        Assert.assertEquals((Object)0xFF000000L, (Object)TestUtil.eval("return 0xff000000;"));
    }

    @Test
    public void parsing_very_large_hex_literal() throws Exception {
        Assert.assertEquals((Object)new BigInteger("ff00000000000000", 16), (Object)TestUtil.eval("return 0xff00000000000000;"));
    }

    @Test
    public void parse_long_hex_literal() throws Exception {
        Assert.assertEquals((Object)1L, (Object)TestUtil.eval("return 0x0000000001L;"));
    }
}

