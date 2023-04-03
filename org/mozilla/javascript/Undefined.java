/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.javascript;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.mozilla.javascript.Scriptable;

public class Undefined
implements Serializable {
    private static final long serialVersionUID = 9195680630202616767L;
    public static final Object instance = new Undefined();
    public static final Scriptable SCRIPTABLE_UNDEFINED = (Scriptable)Proxy.newProxyInstance(Undefined.class.getClassLoader(), new Class[]{Scriptable.class}, new InvocationHandler(){

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) {
            if (method.getName().equals("toString")) {
                return "undefined";
            }
            if (method.getName().equals("equals")) {
                return args.length > 0 && Undefined.isUndefined(args[0]);
            }
            throw new UnsupportedOperationException("undefined doesn't support " + method.getName());
        }
    });

    private Undefined() {
    }

    public Object readResolve() {
        return instance;
    }

    public boolean equals(Object obj) {
        return Undefined.isUndefined(obj) || super.equals(obj);
    }

    public int hashCode() {
        return 0;
    }

    public static boolean isUndefined(Object obj) {
        return instance == obj || SCRIPTABLE_UNDEFINED == obj;
    }
}

