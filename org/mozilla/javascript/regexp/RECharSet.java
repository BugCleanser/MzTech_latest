/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.javascript.regexp;

import java.io.Serializable;

final class RECharSet
implements Serializable {
    private static final long serialVersionUID = 7931787979395898394L;
    final int length;
    final int startIndex;
    final int strlength;
    final boolean sense;
    volatile transient boolean converted;
    volatile transient byte[] bits;

    RECharSet(int length, int startIndex, int strlength, boolean sense) {
        this.length = length;
        this.startIndex = startIndex;
        this.strlength = strlength;
        this.sense = sense;
    }
}

