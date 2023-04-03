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
import org.mozilla.javascript.NativeMap;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Symbol;
import org.mozilla.javascript.SymbolKey;
import org.mozilla.javascript.Undefined;

public class NativeWeakMap
extends IdScriptableObject {
    private static final long serialVersionUID = 8670434366883930453L;
    private static final Object MAP_TAG = "WeakMap";
    private boolean instanceOfWeakMap = false;
    private transient WeakHashMap<Scriptable, Object> map = new WeakHashMap();
    private static final Object NULL_VALUE = new Object();
    private static final int Id_constructor = 1;
    private static final int Id_delete = 2;
    private static final int Id_get = 3;
    private static final int Id_has = 4;
    private static final int Id_set = 5;
    private static final int SymbolId_toStringTag = 6;
    private static final int MAX_PROTOTYPE_ID = 6;

    static void init(Scriptable scope, boolean sealed) {
        NativeWeakMap m = new NativeWeakMap();
        m.exportAsJSClass(6, scope, sealed);
    }

    @Override
    public String getClassName() {
        return "WeakMap";
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
                    NativeWeakMap nm = new NativeWeakMap();
                    nm.instanceOfWeakMap = true;
                    if (args.length > 0) {
                        NativeMap.loadFromIterable(cx, scope, nm, args[0]);
                    }
                    return nm;
                }
                throw ScriptRuntime.typeError1("msg.no.new", "WeakMap");
            }
            case 2: {
                return NativeWeakMap.realThis(thisObj, f).js_delete(args.length > 0 ? args[0] : Undefined.instance);
            }
            case 3: {
                return NativeWeakMap.realThis(thisObj, f).js_get(args.length > 0 ? args[0] : Undefined.instance);
            }
            case 4: {
                return NativeWeakMap.realThis(thisObj, f).js_has(args.length > 0 ? args[0] : Undefined.instance);
            }
            case 5: {
                return NativeWeakMap.realThis(thisObj, f).js_set(args.length > 0 ? args[0] : Undefined.instance, args.length > 1 ? args[1] : Undefined.instance);
            }
        }
        throw new IllegalArgumentException("WeakMap.prototype has no method: " + f.getFunctionName());
    }

    private Object js_delete(Object key) {
        if (!ScriptRuntime.isObject(key)) {
            return Boolean.FALSE;
        }
        return this.map.remove(key) != null;
    }

    private Object js_get(Object key) {
        if (!ScriptRuntime.isObject(key)) {
            return Undefined.instance;
        }
        Object result = this.map.get(key);
        if (result == null) {
            return Undefined.instance;
        }
        if (result == NULL_VALUE) {
            return null;
        }
        return result;
    }

    private Object js_has(Object key) {
        if (!ScriptRuntime.isObject(key)) {
            return Boolean.FALSE;
        }
        return this.map.containsKey(key);
    }

    private Object js_set(Object key, Object v) {
        if (!ScriptRuntime.isObject(key)) {
            throw ScriptRuntime.typeError1("msg.arg.not.object", ScriptRuntime.typeof(key));
        }
        Object value = v == null ? NULL_VALUE : v;
        this.map.put((Scriptable)key, value);
        return this;
    }

    private static NativeWeakMap realThis(Scriptable thisObj, IdFunctionObject f) {
        if (thisObj == null) {
            throw NativeWeakMap.incompatibleCallError(f);
        }
        try {
            NativeWeakMap nm = (NativeWeakMap)thisObj;
            if (!nm.instanceOfWeakMap) {
                throw NativeWeakMap.incompatibleCallError(f);
            }
            return nm;
        }
        catch (ClassCastException cce) {
            throw NativeWeakMap.incompatibleCallError(f);
        }
    }

    @Override
    protected void initPrototypeId(int id) {
        String s;
        int arity;
        if (id == 6) {
            this.initPrototypeValue(6, SymbolKey.TO_STRING_TAG, (Object)this.getClassName(), 3);
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
                s = "delete";
                break;
            }
            case 3: {
                arity = 1;
                s = "get";
                break;
            }
            case 4: {
                arity = 1;
                s = "has";
                break;
            }
            case 5: {
                arity = 2;
                s = "set";
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
            return 6;
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
                    block7: {
                        block4: {
                            id = 0;
                            X = null;
                            s_length = s.length();
                            if (s_length != 3) break block3;
                            c = s.charAt(0);
                            if (c != 'g') break block4;
                            if (s.charAt(2) != 't' || s.charAt(1) != 'e') break block5;
                            id = 3;
                            break block6;
                        }
                        if (c != 'h') break block7;
                        if (s.charAt(2) != 's' || s.charAt(1) != 'a') break block5;
                        id = 4;
                        break block6;
                    }
                    if (c != 's' || s.charAt(2) != 't' || s.charAt(1) != 'e') break block5;
                    id = 5;
                    break block6;
                }
                if (s_length == 6) {
                    X = "delete";
                    id = 2;
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

