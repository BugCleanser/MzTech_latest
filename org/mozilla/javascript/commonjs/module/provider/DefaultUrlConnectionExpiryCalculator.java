/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.javascript.commonjs.module.provider;

import java.io.Serializable;
import java.net.URLConnection;
import org.mozilla.javascript.commonjs.module.provider.UrlConnectionExpiryCalculator;

public class DefaultUrlConnectionExpiryCalculator
implements UrlConnectionExpiryCalculator,
Serializable {
    private static final long serialVersionUID = 1L;
    private final long relativeExpiry;

    public DefaultUrlConnectionExpiryCalculator() {
        this(60000L);
    }

    public DefaultUrlConnectionExpiryCalculator(long relativeExpiry) {
        if (relativeExpiry < 0L) {
            throw new IllegalArgumentException("relativeExpiry < 0");
        }
        this.relativeExpiry = relativeExpiry;
    }

    @Override
    public long calculateExpiry(URLConnection urlConnection) {
        return System.currentTimeMillis() + this.relativeExpiry;
    }
}

