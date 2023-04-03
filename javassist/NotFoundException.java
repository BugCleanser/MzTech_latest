/*
 * Decompiled with CFR 0.152.
 */
package javassist;

public class NotFoundException
extends Exception {
    private static final long serialVersionUID = 1L;

    public NotFoundException(String msg) {
        super(msg);
    }

    public NotFoundException(String msg, Exception e) {
        super(String.valueOf(msg) + " because of " + e.toString());
    }
}

