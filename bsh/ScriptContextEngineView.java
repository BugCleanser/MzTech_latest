/*
 * Decompiled with CFR 0.152.
 */
package bsh;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.script.ScriptContext;

public class ScriptContextEngineView
implements Map<String, Object> {
    ScriptContext context;

    public ScriptContextEngineView(ScriptContext context) {
        this.context = context;
    }

    @Override
    public int size() {
        return this.totalKeySet().size();
    }

    @Override
    public boolean isEmpty() {
        return this.totalKeySet().size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        if (key instanceof String) {
            return this.context.getAttributesScope((String)key) != -1;
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        Set<Object> values = this.totalValueSet();
        return values.contains(value);
    }

    @Override
    public Object get(Object key) {
        return this.context.getAttribute((String)key);
    }

    @Override
    public Object put(String key, Object value) {
        Object oldValue = this.context.getAttribute(key, 100);
        this.context.setAttribute(key, value, 100);
        return oldValue;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Object> t) {
        this.context.getBindings(100).putAll(t);
    }

    @Override
    public Object remove(Object okey) {
        String key = (String)okey;
        Object oldValue = this.context.getAttribute(key, 100);
        this.context.removeAttribute(key, 100);
        return oldValue;
    }

    @Override
    public void clear() {
        this.context.getBindings(100).clear();
    }

    @Override
    public Set<String> keySet() {
        return this.totalKeySet();
    }

    @Override
    public Collection<Object> values() {
        return this.totalValueSet();
    }

    @Override
    public Set<Map.Entry<String, Object>> entrySet() {
        throw new Error("unimplemented");
    }

    private Set<String> totalKeySet() {
        HashSet keys = new HashSet();
        List<Integer> scopes = this.context.getScopes();
        for (int i : scopes) {
            keys.addAll(this.context.getBindings(i).keySet());
        }
        return Collections.unmodifiableSet(keys);
    }

    private Set<Object> totalValueSet() {
        HashSet values = new HashSet();
        List<Integer> scopes = this.context.getScopes();
        for (int i : scopes) {
            values.addAll(this.context.getBindings(i).values());
        }
        return Collections.unmodifiableSet(values);
    }
}

