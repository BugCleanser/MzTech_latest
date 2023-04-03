/*
 * Decompiled with CFR 0.152.
 */
package bsh;

import bsh.BshMethod;
import bsh.InterpreterError;
import bsh.Modifiers;
import bsh.NameSpace;
import bsh.Primitive;
import bsh.UtilEvalError;
import bsh.Variable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class ExternalNameSpace
extends NameSpace {
    private Map<String, Object> externalMap;

    public ExternalNameSpace() {
        this(null, "External Map Namespace", null);
    }

    public ExternalNameSpace(NameSpace parent, String name, Map<String, Object> externalMap) {
        super(parent, name);
        if (externalMap == null) {
            externalMap = new HashMap<String, Object>();
        }
        this.externalMap = externalMap;
    }

    public Map<String, Object> getMap() {
        return this.externalMap;
    }

    public void setMap(Map<String, Object> map) {
        this.externalMap = null;
        this.clear();
        this.externalMap = map;
    }

    @Override
    void setVariable(String name, Object value, boolean strictJava, boolean recurse) throws UtilEvalError {
        super.setVariable(name, value, strictJava, recurse);
        this.putExternalMap(name, value);
    }

    @Override
    public void unsetVariable(String name) {
        super.unsetVariable(name);
        this.externalMap.remove(name);
    }

    @Override
    public String[] getVariableNames() {
        HashSet<String> nameSet = new HashSet<String>();
        String[] nsNames = super.getVariableNames();
        nameSet.addAll(Arrays.asList(nsNames));
        nameSet.addAll(this.externalMap.keySet());
        return nameSet.toArray(new String[0]);
    }

    @Override
    protected Variable getVariableImpl(String name, boolean recurse) throws UtilEvalError {
        Variable var;
        Object value = this.externalMap.get(name);
        if (value == null && this.externalMap.containsKey(name)) {
            value = Primitive.NULL;
        }
        if (value == null) {
            super.unsetVariable(name);
            var = super.getVariableImpl(name, recurse);
        } else {
            Variable localVar = super.getVariableImpl(name, false);
            var = localVar == null ? new Variable(name, (Class)null, value, (Modifiers)null) : localVar;
        }
        return var;
    }

    @Override
    public Variable[] getDeclaredVariables() {
        return super.getDeclaredVariables();
    }

    @Override
    public void setTypedVariable(String name, Class type, Object value, Modifiers modifiers) throws UtilEvalError {
        super.setTypedVariable(name, type, value, modifiers);
        this.putExternalMap(name, value);
    }

    @Override
    public void setMethod(BshMethod method) throws UtilEvalError {
        super.setMethod(method);
    }

    @Override
    public BshMethod getMethod(String name, Class[] sig, boolean declaredOnly) throws UtilEvalError {
        return super.getMethod(name, sig, declaredOnly);
    }

    @Override
    protected void getAllNamesAux(List<String> list) {
        super.getAllNamesAux(list);
    }

    @Override
    public void clear() {
        super.clear();
        this.externalMap.clear();
    }

    protected void putExternalMap(String name, Object value) {
        if (value instanceof Variable) {
            try {
                value = this.unwrapVariable((Variable)value);
            }
            catch (UtilEvalError ute) {
                throw new InterpreterError("unexpected UtilEvalError");
            }
        }
        if (value instanceof Primitive) {
            value = Primitive.unwrap((Primitive)value);
        }
        this.externalMap.put(name, value);
    }
}

