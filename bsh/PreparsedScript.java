/*
 * Decompiled with CFR 0.152.
 */
package bsh;

import bsh.BshMethod;
import bsh.EvalError;
import bsh.Interpreter;
import bsh.NameSpace;
import bsh.Primitive;
import bsh.This;
import bsh.UtilEvalError;
import bsh.classpath.ClassManagerImpl;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.Map;

public class PreparsedScript {
    private final BshMethod _method;
    private final Interpreter _interpreter;

    public PreparsedScript(String source) throws EvalError {
        this(source, PreparsedScript.getDefaultClassLoader());
    }

    private static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        }
        catch (SecurityException securityException) {
            // empty catch block
        }
        if (cl == null) {
            cl = PreparsedScript.class.getClassLoader();
        }
        if (cl != null) {
            return cl;
        }
        return ClassLoader.getSystemClassLoader();
    }

    public PreparsedScript(String source, ClassLoader classLoader) throws EvalError {
        ClassManagerImpl classManager = new ClassManagerImpl();
        classManager.setClassLoader(classLoader);
        NameSpace nameSpace = new NameSpace(classManager, "global");
        this._interpreter = new Interpreter(new StringReader(""), System.out, System.err, false, nameSpace, null, null);
        try {
            This callable = (This)this._interpreter.eval("__execute() { " + source + "\n}\nreturn this;");
            this._method = callable.getNameSpace().getMethod("__execute", new Class[0], false);
        }
        catch (UtilEvalError e) {
            throw new IllegalStateException(e);
        }
    }

    public Object invoke(Map<String, ?> context) throws EvalError {
        NameSpace nameSpace = new NameSpace(this._interpreter.getClassManager(), "BeanshellExecutable");
        nameSpace.setParent(this._interpreter.getNameSpace());
        BshMethod method = new BshMethod(this._method.getName(), this._method.getReturnType(), this._method.getParameterNames(), this._method.getParameterTypes(), this._method.methodBody, nameSpace, this._method.getModifiers());
        for (Map.Entry<String, ?> entry : context.entrySet()) {
            try {
                Object value = entry.getValue();
                nameSpace.setVariable(entry.getKey(), value != null ? value : Primitive.NULL, false);
            }
            catch (UtilEvalError e) {
                throw new EvalError("cannot set variable '" + entry.getKey() + '\'', null, null, e);
            }
        }
        Object result = method.invoke(new Object[0], this._interpreter);
        if (result instanceof Primitive) {
            if (((Primitive)result).getType() == Void.TYPE) {
                return null;
            }
            return ((Primitive)result).getValue();
        }
        return result;
    }

    public void setOut(PrintStream value) {
        this._interpreter.setOut(value);
    }

    public void setErr(PrintStream value) {
        this._interpreter.setErr(value);
    }
}

