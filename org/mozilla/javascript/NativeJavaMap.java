/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.javascript;

import java.util.ArrayList;
import java.util.Map;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;

public class NativeJavaMap
extends NativeJavaObject {
    private Map<Object, Object> map;

    public NativeJavaMap(Scriptable scope, Object map) {
        super(scope, map, map.getClass());
        assert (map instanceof Map);
        this.map = (Map)map;
    }

    @Override
    public String getClassName() {
        return "JavaMap";
    }

    @Override
    public boolean has(String name, Scriptable start) {
        if (this.map.containsKey(name)) {
            return true;
        }
        return super.has(name, start);
    }

    @Override
    public boolean has(int index, Scriptable start) {
        if (this.map.containsKey(index)) {
            return true;
        }
        return super.has(index, start);
    }

    @Override
    public Object get(String name, Scriptable start) {
        if (this.map.containsKey(name)) {
            Context cx = Context.getContext();
            Object obj = this.map.get(name);
            return cx.getWrapFactory().wrap(cx, this, obj, obj.getClass());
        }
        return super.get(name, start);
    }

    @Override
    public Object get(int index, Scriptable start) {
        if (this.map.containsKey(index)) {
            Context cx = Context.getContext();
            Object obj = this.map.get(index);
            return cx.getWrapFactory().wrap(cx, this, obj, obj.getClass());
        }
        return super.get(index, start);
    }

    @Override
    public void put(String name, Scriptable start, Object value) {
        this.map.put(name, Context.jsToJava(value, Object.class));
    }

    @Override
    public void put(int index, Scriptable start, Object value) {
        this.map.put(index, Context.jsToJava(value, Object.class));
    }

    @Override
    public Object[] getIds() {
        ArrayList<Object> ids = new ArrayList<Object>(this.map.size());
        for (Object key : this.map.keySet()) {
            if (key instanceof Integer) {
                ids.add((Integer)key);
                continue;
            }
            ids.add(ScriptRuntime.toString(key));
        }
        return ids.toArray();
    }
}

