/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.javascript.regexp;

class RENode {
    byte op;
    RENode next;
    RENode kid;
    RENode kid2;
    int parenIndex;
    int min;
    int max;
    int parenCount;
    boolean greedy;
    int startIndex;
    int kidlen;
    int bmsize;
    int index;
    boolean sense;
    char chr;
    int length;
    int flatIndex;

    RENode(byte op) {
        this.op = op;
    }
}

