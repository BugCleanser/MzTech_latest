/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.javascript;

import java.util.Iterator;
import java.util.LinkedHashMap;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.SlotMap;

public class HashSlotMap
implements SlotMap {
    private final LinkedHashMap<Object, ScriptableObject.Slot> map = new LinkedHashMap();

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public ScriptableObject.Slot query(Object key, int index) {
        Object name = key == null ? String.valueOf(index) : key;
        return this.map.get(name);
    }

    @Override
    public ScriptableObject.Slot get(Object key, int index, ScriptableObject.SlotAccess accessType) {
        Object name = key == null ? String.valueOf(index) : key;
        ScriptableObject.Slot slot = this.map.get(name);
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
        return this.createSlot(key, index, name, accessType);
    }

    private ScriptableObject.Slot createSlot(Object key, int index, Object name, ScriptableObject.SlotAccess accessType) {
        ScriptableObject.Slot newSlot;
        ScriptableObject.Slot slot = this.map.get(name);
        if (slot != null) {
            ScriptableObject.Slot newSlot2;
            if (accessType == ScriptableObject.SlotAccess.MODIFY_GETTER_SETTER && !(slot instanceof ScriptableObject.GetterSlot)) {
                newSlot2 = new ScriptableObject.GetterSlot(name, slot.indexOrHash, slot.getAttributes());
            } else if (accessType == ScriptableObject.SlotAccess.CONVERT_ACCESSOR_TO_DATA && slot instanceof ScriptableObject.GetterSlot) {
                newSlot2 = new ScriptableObject.Slot(name, slot.indexOrHash, slot.getAttributes());
            } else {
                if (accessType == ScriptableObject.SlotAccess.MODIFY_CONST) {
                    return null;
                }
                return slot;
            }
            newSlot2.value = slot.value;
            this.map.put(name, newSlot2);
            return newSlot2;
        }
        ScriptableObject.Slot slot2 = newSlot = accessType == ScriptableObject.SlotAccess.MODIFY_GETTER_SETTER ? new ScriptableObject.GetterSlot(key, index, 0) : new ScriptableObject.Slot(key, index, 0);
        if (accessType == ScriptableObject.SlotAccess.MODIFY_CONST) {
            newSlot.setAttributes(13);
        }
        this.addSlot(newSlot);
        return newSlot;
    }

    @Override
    public void addSlot(ScriptableObject.Slot newSlot) {
        Object name = newSlot.name == null ? String.valueOf(newSlot.indexOrHash) : newSlot.name;
        this.map.put(name, newSlot);
    }

    @Override
    public void remove(Object key, int index) {
        Object name = key == null ? String.valueOf(index) : key;
        ScriptableObject.Slot slot = this.map.get(name);
        if (slot != null) {
            if ((slot.getAttributes() & 4) != 0) {
                Context cx = Context.getContext();
                if (cx.isStrictMode()) {
                    throw ScriptRuntime.typeError1("msg.delete.property.with.configurable.false", key);
                }
                return;
            }
            this.map.remove(name);
        }
    }

    @Override
    public Iterator<ScriptableObject.Slot> iterator() {
        return this.map.values().iterator();
    }
}

