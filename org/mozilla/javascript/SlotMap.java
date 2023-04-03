/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.javascript;

import org.mozilla.javascript.ScriptableObject;

public interface SlotMap
extends Iterable<ScriptableObject.Slot> {
    public int size();

    public boolean isEmpty();

    public ScriptableObject.Slot get(Object var1, int var2, ScriptableObject.SlotAccess var3);

    public ScriptableObject.Slot query(Object var1, int var2);

    public void addSlot(ScriptableObject.Slot var1);

    public void remove(Object var1, int var2);
}

