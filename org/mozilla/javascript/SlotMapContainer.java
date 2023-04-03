/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.javascript;

import java.util.Iterator;
import org.mozilla.javascript.EmbeddedSlotMap;
import org.mozilla.javascript.HashSlotMap;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.SlotMap;

class SlotMapContainer
implements SlotMap {
    private static final int LARGE_HASH_SIZE = 2000;
    protected SlotMap map;

    SlotMapContainer(int initialSize) {
        this.map = initialSize > 2000 ? new HashSlotMap() : new EmbeddedSlotMap();
    }

    @Override
    public int size() {
        return this.map.size();
    }

    public int dirtySize() {
        return this.map.size();
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public ScriptableObject.Slot get(Object key, int index, ScriptableObject.SlotAccess accessType) {
        if (accessType != ScriptableObject.SlotAccess.QUERY) {
            this.checkMapSize();
        }
        return this.map.get(key, index, accessType);
    }

    @Override
    public ScriptableObject.Slot query(Object key, int index) {
        return this.map.query(key, index);
    }

    @Override
    public void addSlot(ScriptableObject.Slot newSlot) {
        this.checkMapSize();
        this.map.addSlot(newSlot);
    }

    @Override
    public void remove(Object key, int index) {
        this.map.remove(key, index);
    }

    @Override
    public Iterator<ScriptableObject.Slot> iterator() {
        return this.map.iterator();
    }

    public long readLock() {
        return 0L;
    }

    public void unlockRead(long stamp) {
    }

    protected void checkMapSize() {
        if (this.map instanceof EmbeddedSlotMap && this.map.size() >= 2000) {
            HashSlotMap newMap = new HashSlotMap();
            for (ScriptableObject.Slot s : this.map) {
                newMap.addSlot(s);
            }
            this.map = newMap;
        }
    }
}

