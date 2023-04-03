/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.javascript;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.SlotMap;

public class EmbeddedSlotMap
implements SlotMap {
    private ScriptableObject.Slot[] slots;
    private ScriptableObject.Slot firstAdded;
    private ScriptableObject.Slot lastAdded;
    private int count;
    private static final int INITIAL_SLOT_SIZE = 4;

    @Override
    public int size() {
        return this.count;
    }

    @Override
    public boolean isEmpty() {
        return this.count == 0;
    }

    @Override
    public Iterator<ScriptableObject.Slot> iterator() {
        return new Iter(this.firstAdded);
    }

    @Override
    public ScriptableObject.Slot query(Object key, int index) {
        if (this.slots == null) {
            return null;
        }
        int indexOrHash = key != null ? key.hashCode() : index;
        int slotIndex = EmbeddedSlotMap.getSlotIndex(this.slots.length, indexOrHash);
        ScriptableObject.Slot slot = this.slots[slotIndex];
        while (slot != null) {
            Object skey = slot.name;
            if (indexOrHash == slot.indexOrHash && (skey == key || key != null && key.equals(skey))) {
                return slot;
            }
            slot = slot.next;
        }
        return null;
    }

    @Override
    public ScriptableObject.Slot get(Object key, int index, ScriptableObject.SlotAccess accessType) {
        if (this.slots == null && accessType == ScriptableObject.SlotAccess.QUERY) {
            return null;
        }
        int indexOrHash = key != null ? key.hashCode() : index;
        ScriptableObject.Slot slot = null;
        if (this.slots != null) {
            int slotIndex = EmbeddedSlotMap.getSlotIndex(this.slots.length, indexOrHash);
            slot = this.slots[slotIndex];
            while (slot != null) {
                Object skey = slot.name;
                if (indexOrHash == slot.indexOrHash && (skey == key || key != null && key.equals(skey))) break;
                slot = slot.next;
            }
            switch (accessType) {
                case QUERY: {
                    return slot;
                }
                case MODIFY: 
                case MODIFY_CONST: {
                    if (slot == null) break;
                    return slot;
                }
                case MODIFY_GETTER_SETTER: {
                    if (!(slot instanceof ScriptableObject.GetterSlot)) break;
                    return slot;
                }
                case CONVERT_ACCESSOR_TO_DATA: {
                    if (slot instanceof ScriptableObject.GetterSlot) break;
                    return slot;
                }
            }
        }
        return this.createSlot(key, indexOrHash, accessType, slot);
    }

    private ScriptableObject.Slot createSlot(Object key, int indexOrHash, ScriptableObject.SlotAccess accessType, ScriptableObject.Slot existingSlot) {
        ScriptableObject.Slot newSlot;
        if (this.count == 0) {
            this.slots = new ScriptableObject.Slot[4];
        } else if (existingSlot != null) {
            ScriptableObject.Slot prev;
            int insertPos = EmbeddedSlotMap.getSlotIndex(this.slots.length, indexOrHash);
            ScriptableObject.Slot slot = prev = this.slots[insertPos];
            while (!(slot == null || slot.indexOrHash == indexOrHash && (slot.name == key || key != null && key.equals(slot.name)))) {
                prev = slot;
                slot = slot.next;
            }
            if (slot != null) {
                ScriptableObject.Slot newSlot2;
                if (accessType == ScriptableObject.SlotAccess.MODIFY_GETTER_SETTER && !(slot instanceof ScriptableObject.GetterSlot)) {
                    newSlot2 = new ScriptableObject.GetterSlot(key, indexOrHash, slot.getAttributes());
                } else if (accessType == ScriptableObject.SlotAccess.CONVERT_ACCESSOR_TO_DATA && slot instanceof ScriptableObject.GetterSlot) {
                    newSlot2 = new ScriptableObject.Slot(key, indexOrHash, slot.getAttributes());
                } else {
                    if (accessType == ScriptableObject.SlotAccess.MODIFY_CONST) {
                        return null;
                    }
                    return slot;
                }
                newSlot2.value = slot.value;
                newSlot2.next = slot.next;
                if (slot == this.firstAdded) {
                    this.firstAdded = newSlot2;
                } else {
                    ScriptableObject.Slot ps = this.firstAdded;
                    while (ps != null && ps.orderedNext != slot) {
                        ps = ps.orderedNext;
                    }
                    if (ps != null) {
                        ps.orderedNext = newSlot2;
                    }
                }
                newSlot2.orderedNext = slot.orderedNext;
                if (slot == this.lastAdded) {
                    this.lastAdded = newSlot2;
                }
                if (prev == slot) {
                    this.slots[insertPos] = newSlot2;
                } else {
                    prev.next = newSlot2;
                }
                return newSlot2;
            }
        }
        if (4 * (this.count + 1) > 3 * this.slots.length) {
            ScriptableObject.Slot[] newSlots = new ScriptableObject.Slot[this.slots.length * 2];
            EmbeddedSlotMap.copyTable(this.slots, newSlots);
            this.slots = newSlots;
        }
        ScriptableObject.Slot slot = newSlot = accessType == ScriptableObject.SlotAccess.MODIFY_GETTER_SETTER ? new ScriptableObject.GetterSlot(key, indexOrHash, 0) : new ScriptableObject.Slot(key, indexOrHash, 0);
        if (accessType == ScriptableObject.SlotAccess.MODIFY_CONST) {
            newSlot.setAttributes(13);
        }
        this.insertNewSlot(newSlot);
        return newSlot;
    }

    @Override
    public void addSlot(ScriptableObject.Slot newSlot) {
        if (this.slots == null) {
            this.slots = new ScriptableObject.Slot[4];
        }
        this.insertNewSlot(newSlot);
    }

    private void insertNewSlot(ScriptableObject.Slot newSlot) {
        ++this.count;
        if (this.lastAdded != null) {
            this.lastAdded.orderedNext = newSlot;
        }
        if (this.firstAdded == null) {
            this.firstAdded = newSlot;
        }
        this.lastAdded = newSlot;
        EmbeddedSlotMap.addKnownAbsentSlot(this.slots, newSlot);
    }

    @Override
    public void remove(Object key, int index) {
        int indexOrHash;
        int n = indexOrHash = key != null ? key.hashCode() : index;
        if (this.count != 0) {
            ScriptableObject.Slot prev;
            int slotIndex = EmbeddedSlotMap.getSlotIndex(this.slots.length, indexOrHash);
            ScriptableObject.Slot slot = prev = this.slots[slotIndex];
            while (!(slot == null || slot.indexOrHash == indexOrHash && (slot.name == key || key != null && key.equals(slot.name)))) {
                prev = slot;
                slot = slot.next;
            }
            if (slot != null) {
                if ((slot.getAttributes() & 4) != 0) {
                    Context cx = Context.getContext();
                    if (cx.isStrictMode()) {
                        throw ScriptRuntime.typeError1("msg.delete.property.with.configurable.false", key);
                    }
                    return;
                }
                --this.count;
                if (prev == slot) {
                    this.slots[slotIndex] = slot.next;
                } else {
                    prev.next = slot.next;
                }
                if (slot == this.firstAdded) {
                    prev = null;
                    this.firstAdded = slot.orderedNext;
                } else {
                    prev = this.firstAdded;
                    while (prev.orderedNext != slot) {
                        prev = prev.orderedNext;
                    }
                    prev.orderedNext = slot.orderedNext;
                }
                if (slot == this.lastAdded) {
                    this.lastAdded = prev;
                }
            }
        }
    }

    private static void copyTable(ScriptableObject.Slot[] oldSlots, ScriptableObject.Slot[] newSlots) {
        for (ScriptableObject.Slot slot : oldSlots) {
            while (slot != null) {
                ScriptableObject.Slot nextSlot = slot.next;
                slot.next = null;
                EmbeddedSlotMap.addKnownAbsentSlot(newSlots, slot);
                slot = nextSlot;
            }
        }
    }

    private static void addKnownAbsentSlot(ScriptableObject.Slot[] addSlots, ScriptableObject.Slot slot) {
        int insertPos = EmbeddedSlotMap.getSlotIndex(addSlots.length, slot.indexOrHash);
        ScriptableObject.Slot old = addSlots[insertPos];
        addSlots[insertPos] = slot;
        slot.next = old;
    }

    private static int getSlotIndex(int tableSize, int indexOrHash) {
        return indexOrHash & tableSize - 1;
    }

    private static final class Iter
    implements Iterator<ScriptableObject.Slot> {
        private ScriptableObject.Slot next;

        Iter(ScriptableObject.Slot slot) {
            this.next = slot;
        }

        @Override
        public boolean hasNext() {
            return this.next != null;
        }

        @Override
        public ScriptableObject.Slot next() {
            ScriptableObject.Slot ret = this.next;
            if (ret == null) {
                throw new NoSuchElementException();
            }
            this.next = this.next.orderedNext;
            return ret;
        }
    }
}

