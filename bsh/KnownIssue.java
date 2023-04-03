/*
 * Decompiled with CFR 0.152.
 */
package bsh;

import bsh.TestFilter;

public class KnownIssue
implements TestFilter {
    static final boolean SKIP_KOWN_ISSUES = System.getProperties().containsKey("skip_known_issues");

    @Override
    public boolean skip() {
        return SKIP_KOWN_ISSUES;
    }
}

