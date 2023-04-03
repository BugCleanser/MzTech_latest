/*
 * Decompiled with CFR 0.152.
 */
package bsh;

import bsh.TestFilter;

public class NotSuitedFor_Java5_OrLower
implements TestFilter {
    public static boolean CURRENT_VM_IS_BELOW_v6 = "1.6".compareTo(System.getProperty("java.version").substring(0, 3)) > 0;

    @Override
    public boolean skip() {
        return CURRENT_VM_IS_BELOW_v6;
    }
}

