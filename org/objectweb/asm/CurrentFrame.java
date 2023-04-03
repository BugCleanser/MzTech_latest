/*
 * Decompiled with CFR 0.152.
 */
package org.objectweb.asm;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Frame;
import org.objectweb.asm.Item;

class CurrentFrame
extends Frame {
    CurrentFrame() {
    }

    void a(int n, int n2, ClassWriter classWriter, Item item) {
        super.a(n, n2, classWriter, item);
        Frame frame = new Frame();
        this.a(classWriter, frame, 0);
        this.b(frame);
        this.b.f = 0;
    }
}

