/*
 * Decompiled with CFR 0.152.
 */
package bsh;

import bsh.BshMethod;
import bsh.CallStack;
import bsh.EvalError;
import bsh.Interpreter;
import bsh.NameSpace;
import bsh.Primitive;
import bsh.SimpleNode;
import bsh.StringUtil;
import bsh.TargetError;
import bsh.Types;
import bsh.UtilEvalError;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class This
implements Serializable,
Runnable {
    final NameSpace namespace;
    transient Interpreter declaringInterpreter;
    private Map<Integer, Object> interfaces;
    private final InvocationHandler invocationHandler = new Handler();

    static This getThis(NameSpace namespace, Interpreter declaringInterpreter) {
        return new This(namespace, declaringInterpreter);
    }

    public Object getInterface(Class clas) {
        return this.getInterface(new Class[]{clas});
    }

    public Object getInterface(Class[] ca) {
        if (this.interfaces == null) {
            this.interfaces = new HashMap<Integer, Object>();
        }
        int hash = 21;
        for (int i = 0; i < ca.length; ++i) {
            hash *= ca[i].hashCode() + 3;
        }
        Integer hashKey = new Integer(hash);
        Object interf = this.interfaces.get(hashKey);
        if (interf == null) {
            ClassLoader classLoader = ca[0].getClassLoader();
            interf = Proxy.newProxyInstance(classLoader, ca, this.invocationHandler);
            this.interfaces.put(hashKey, interf);
        }
        return interf;
    }

    This(NameSpace namespace, Interpreter declaringInterpreter) {
        this.namespace = namespace;
        this.declaringInterpreter = declaringInterpreter;
    }

    public NameSpace getNameSpace() {
        return this.namespace;
    }

    public String toString() {
        return "'this' reference to Bsh object: " + this.namespace;
    }

    @Override
    public void run() {
        try {
            this.invokeMethod("run", new Object[0]);
        }
        catch (EvalError e) {
            this.declaringInterpreter.error("Exception in runnable:" + e);
        }
    }

    public Object invokeMethod(String name, Object[] args) throws EvalError {
        return this.invokeMethod(name, args, null, null, null, false);
    }

    public Object invokeMethod(String methodName, Object[] args, Interpreter interpreter, CallStack callstack, SimpleNode callerInfo, boolean declaredOnly) throws EvalError {
        if (args == null) {
            args = new Object[]{};
        } else {
            Object[] oa = new Object[args.length];
            for (int i = 0; i < args.length; ++i) {
                oa[i] = args[i] == null ? Primitive.NULL : args[i];
            }
            args = oa;
        }
        if (interpreter == null) {
            interpreter = this.declaringInterpreter;
        }
        if (callstack == null) {
            callstack = new CallStack(this.namespace);
        }
        if (callerInfo == null) {
            callerInfo = SimpleNode.JAVACODE;
        }
        Class[] types = Types.getTypes(args);
        BshMethod bshMethod = null;
        try {
            bshMethod = this.namespace.getMethod(methodName, types, declaredOnly);
        }
        catch (UtilEvalError utilEvalError) {
            // empty catch block
        }
        if (bshMethod != null) {
            return bshMethod.invoke(args, interpreter, callstack, callerInfo);
        }
        if (methodName.equals("toString") && args.length == 0) {
            return this.toString();
        }
        if (methodName.equals("hashCode") && args.length == 0) {
            return new Integer(this.hashCode());
        }
        if (methodName.equals("equals") && args.length == 1) {
            Object obj = args[0];
            return this == obj ? Boolean.TRUE : Boolean.FALSE;
        }
        if (methodName.equals("clone") && args.length == 0) {
            NameSpace ns = new NameSpace(this.namespace, this.namespace.getName() + " clone");
            try {
                for (String varName : this.namespace.getVariableNames()) {
                    ns.setLocalVariable(varName, this.namespace.getVariable(varName, false), false);
                }
                for (BshMethod method : this.namespace.getMethods()) {
                    ns.setMethod(method);
                }
            }
            catch (UtilEvalError e) {
                throw e.toEvalError(SimpleNode.JAVACODE, callstack);
            }
            return ns.getThis(this.declaringInterpreter);
        }
        try {
            bshMethod = this.namespace.getMethod("invoke", new Class[]{null, null});
        }
        catch (UtilEvalError utilEvalError) {
            // empty catch block
        }
        if (bshMethod != null) {
            return bshMethod.invoke(new Object[]{methodName, args}, interpreter, callstack, callerInfo);
        }
        throw new EvalError("Method " + StringUtil.methodString(methodName, types) + " not found in bsh scripted object: " + this.namespace.getName(), callerInfo, callstack);
    }

    public static void bind(This ths, NameSpace namespace, Interpreter declaringInterpreter) {
        ths.namespace.setParent(namespace);
        ths.declaringInterpreter = declaringInterpreter;
    }

    static boolean isExposedThisMethod(String name) {
        return name.equals("getClass") || name.equals("invokeMethod") || name.equals("getInterface") || name.equals("wait") || name.equals("notify") || name.equals("notifyAll");
    }

    class Handler
    implements InvocationHandler,
    Serializable {
        Handler() {
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            try {
                return this.invokeImpl(proxy, method, args);
            }
            catch (TargetError te) {
                Throwable t = te.getTarget();
                Class<?> c = t.getClass();
                String msg = t.getMessage();
                try {
                    Throwable t2 = msg == null ? (Throwable)c.getConstructor(new Class[0]).newInstance(new Object[0]) : (Throwable)c.getConstructor(String.class).newInstance(msg);
                    t2.initCause(te);
                    throw t2;
                }
                catch (NoSuchMethodException e) {
                    throw t;
                }
            }
            catch (EvalError ee) {
                if (Interpreter.DEBUG) {
                    Interpreter.debug("EvalError in scripted interface: " + This.this.toString() + ": " + ee);
                }
                throw ee;
            }
        }

        public Object invokeImpl(Object proxy, Method method, Object[] args) throws EvalError {
            String methodName = method.getName();
            CallStack callstack = new CallStack(This.this.namespace);
            BshMethod equalsMethod = null;
            try {
                equalsMethod = This.this.namespace.getMethod("equals", new Class[]{Object.class});
            }
            catch (UtilEvalError utilEvalError) {
                // empty catch block
            }
            if (methodName.equals("equals") && equalsMethod == null) {
                Object obj = args[0];
                return proxy == obj;
            }
            BshMethod toStringMethod = null;
            try {
                toStringMethod = This.this.namespace.getMethod("toString", new Class[0]);
            }
            catch (UtilEvalError utilEvalError) {
                // empty catch block
            }
            if (methodName.equals("toString") && toStringMethod == null) {
                Class<?>[] ints = proxy.getClass().getInterfaces();
                StringBuilder sb = new StringBuilder(This.this.toString() + "\nimplements:");
                for (int i = 0; i < ints.length; ++i) {
                    sb.append(" " + ints[i].getName() + (ints.length > 1 ? "," : ""));
                }
                return sb.toString();
            }
            Class[] paramTypes = method.getParameterTypes();
            return Primitive.unwrap(This.this.invokeMethod(methodName, Primitive.wrap(args, paramTypes)));
        }
    }
}

