/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.javascript.commonjs.module.provider;

import java.net.URLConnection;

public interface UrlConnectionExpiryCalculator {
    public long calculateExpiry(URLConnection var1);
}

