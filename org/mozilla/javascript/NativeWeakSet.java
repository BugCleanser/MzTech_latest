/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.javascript;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.WeakHashMap;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.NativeSet;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Symbol;
import org.mozilla.javascript.SymbolKey;
import org.mozilla.javascript.Undefined;

public class NativeWeakSet
extends IdScriptableObject {
    private static final long serialVersionUID = 2065753364224029534L;
    private static final Object MAP_TAG = "WeakSet";
    private boolean instanceOfWeakSet = false;
    private transient WeakHashMap<Scriptable, Boolean> map = new WeakHashMap();
    private static final int Id_constructor = 1;
    private static final int Id_add = 2;
    private static final int Id_delete = 3;
    private static final int Id_has = 4;
    private static final int SymbolId_toStringTag = 5;
    private static final int MAX_PROTOTYPE_ID = 5;

    static void init(Scriptable scope, boolean sealed) {
        NativeWeakSet m = new NativeWeakSet();
        m.exportAsJSClass(5, scope, sealed);
    }

    @Override
    public String getClassName() {
        return "WeakSet";
    }

    @Override
    public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
        if (!f.hasTag(MAP_TAG)) {
            return super.execIdCall(f, cx, scope, thisObj, args);
        }
        int id = f.methodId();
        switch (id) {
            case 1: {
                if (thisObj == null) {
                    NativeWeakSet ns = new NativeWeakSet();
                    ns.instanceOfWeakSet = true;
                    if (args.length > 0) {
                        NativeSet.loadFromIterable(cx, scope, ns, args[0]);
                    }
                    return ns;
                }
                throw ScriptRuntime.typeError1("msg.no.new", "WeakSet");
            }
            case 2: {
                return NativeWeakSet.realThis(thisObj, f).js_add(args.length > 0 ? args[0] : Undefined.instance);
            }
            case 3: {
                return NativeWeakSet.realThis(thisObj, f).js_delete(args.length > 0 ? args[0] : Undefined.instance);
            }
            case 4: {
                return NativeWeakSet.realThis(thisObj, f).js_has(args.length > 0 ? args[0] : Undefined.instance);
            }
        }
        throw new IllegalArgumentException("WeakMap.prototype has no method: " + f.getFunctionName());
    }

    private Object js_add(Object key) {
        if (!ScriptRuntime.isObject(key)) {
            throw ScriptRuntime.typeError1("msg.arg.not.object", ScriptRuntime.typeof(key));
        }
        this.map.put((Scriptable)key, Boolean.TRUE);
        return this;
    }

    private Object js_delete(Object key) {
        if (!ScriptRuntime.isObject(key)) {
            return Boolean.FALSE;
        }
        return this.map.remove(key) != null;
    }

    private Object js_has(Object key) {
        if (!ScriptRuntime.isObject(key)) {
            return Boolean.FALSE;
        }
        return this.map.containsKey(key);
    }

    private static NativeWeakSet realThis(Scriptable thisObj, IdFunctionObject f) {
        if (thisObj == null) {
            throw NativeWeakSet.incompatibleCallError(f);
        }
        try {
            NativeWeakSet ns = (NativeWeakSet)thisObj;
            if (!ns.instanceOfWeakSet) {
                throw NativeWeakSet.incompatibleCallError(f);
            }
            return ns;
        }
        catch (ClassCastException cce) {
            throw NativeWeakSet.incompatibleCallError(f);
        }
    }

    @Override
    protected void initPrototypeId(int id) {
        String s;
        int arity;
        if (id == 5) {
            this.initPrototypeValue(5, SymbolKey.TO_STRING_TAG, (Object)this.getClassName(), 3);
            return;
        }
        String fnName = null;
        switch (id) {
            case 1: {
                arity = 0;
                s = "constructor";
                break;
            }
            case 2: {
                arity = 1;
                s = "add";
                break;
            }
            case 3: {
                arity = 1;
                s = "delete";
                break;
            }
            case 4: {
                arity = 1;
                s = "has";
                break;
            }
            default: {
                throw new IllegalArgumentException(String.valueOf(id));
            }
        }
        this.initPrototypeMethod(MAP_TAG, id, s, fnName, arity);
    }

    @Override
    protected int findPrototypeId(Symbol k) {
        if (SymbolKey.TO_STRING_TAG.equals(k)) {
            return 5;
        }
        return 0;
    }

    @Override
    protected int findPrototypeId(String s) {
        int id;
        block6: {
            String X;
            block5: {
                int s_length;
                block3: {
                    char c;
                    block4: {
                        id = 0;
                        X = null;
                        s_length = s.length();
                        if (s_length != 3) break block3;
                        c = s.charAt(0);
                        if (c != 'a') break block4;
                        if (s.charAt(2) != 'd' || s.charAt(1) != 'd') break block5;
                        id = 2;
                        break block6;
                    }
                    if (c != 'h' || s.charAt(2) != 's' || s.charAt(1) != 'a') break block5;
                    id = 4;
                    break block6;
                }
                if (s_length == 6) {
                    X = "delete";
                    id = 3;
                } else if (s_length == 11) {
                    X = "constructor";
                    id = 1;
                }
            }
            if (X == null || X == s || X.equals(s)) break block6;
            id = 0;
        }
        return id;
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.map = new WeakHashMap();
    }
}

