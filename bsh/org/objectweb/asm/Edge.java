/*
 * Decompiled with CFR 0.152.
 */
package bsh.org.objectweb.asm;

import bsh.org.objectweb.asm.Label;

class Edge {
    int stackSize;
    Label successor;
    Edge next;
    Edge poolNext;

    Edge() {
    }
}

