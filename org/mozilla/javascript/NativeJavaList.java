/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.javascript;

import java.util.List;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Symbol;
import org.mozilla.javascript.SymbolKey;
import org.mozilla.javascript.Undefined;

public class NativeJavaList
extends NativeJavaObject {
    private List<Object> list;

    public NativeJavaList(Scriptable scope, Object list) {
        super(scope, list, list.getClass());
        assert (list instanceof List);
        this.list = (List)list;
    }

    @Override
    public String getClassName() {
        return "JavaList";
    }

    @Override
    public boolean has(String name, Scriptable start) {
        if (name.equals("length")) {
            return true;
        }
        return super.has(name, start);
    }

    @Override
    public boolean has(int index, Scriptable start) {
        if (this.isWithValidIndex(index)) {
            return true;
        }
        return super.has(index, start);
    }

    @Override
    public boolean has(Symbol key, Scriptable start) {
        if (SymbolKey.IS_CONCAT_SPREADABLE.equals(key)) {
            return true;
        }
        return super.has(key, start);
    }

    @Override
    public Object get(String name, Scriptable start) {
        if ("length".equals(name)) {
            return this.list.size();
        }
        return super.get(name, start);
    }

    @Override
    public Object get(int index, Scriptable start) {
        if (this.isWithValidIndex(index)) {
            Context cx = Context.getContext();
            Object obj = this.list.get(index);
            return cx.getWrapFactory().wrap(cx, this, obj, obj.getClass());
        }
        return Undefined.instance;
    }

    @Override
    public Object get(Symbol key, Scriptable start) {
        if (SymbolKey.IS_CONCAT_SPREADABLE.equals(key)) {
            return Boolean.TRUE;
        }
        return super.get(key, start);
    }

    @Override
    public void put(int index, Scriptable start, Object value) {
        if (this.isWithValidIndex(index)) {
            this.list.set(index, Context.jsToJava(value, Object.class));
            return;
        }
        super.put(index, start, value);
    }

    @Override
    public Object[] getIds() {
        List list = (List)this.javaObject;
        Object[] result = new Object[list.size()];
        int i = list.size();
        while (--i >= 0) {
            result[i] = i;
        }
        return result;
    }

    private boolean isWithValidIndex(int index) {
        return index >= 0 && index < this.list.size();
    }
}

