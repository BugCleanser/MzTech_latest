/*
 * Decompiled with CFR 0.152.
 */
package com.sun.jna.ptr;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.ByReference;

public class NativeLongByReference
extends ByReference {
    public NativeLongByReference() {
        this(new NativeLong(0L));
    }

    public NativeLongByReference(NativeLong value) {
        super(NativeLong.SIZE);
        this.setValue(value);
    }

    public void setValue(NativeLong value) {
        this.getPointer().setNativeLong(0L, value);
    }

    public NativeLong getValue() {
        return this.getPointer().getNativeLong(0L);
    }

    @Override
    public String toString() {
        if (NativeLong.SIZE > 4) {
            return String.format("NativeLong@0x1$%x=0x%2$x (%2$d)", Pointer.nativeValue(this.getPointer()), this.getValue().longValue());
        }
        return String.format("NativeLong@0x1$%x=0x%2$x (%2$d)", Pointer.nativeValue(this.getPointer()), this.getValue().intValue());
    }
}

