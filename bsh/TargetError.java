/*
 * Decompiled with CFR 0.152.
 */
package bsh;

import bsh.CallStack;
import bsh.EvalError;
import bsh.Interpreter;
import bsh.InterpreterError;
import bsh.SimpleNode;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;

public final class TargetError
extends EvalError {
    private final boolean inNativeCode;

    public TargetError(String msg, Throwable t, SimpleNode node, CallStack callstack, boolean inNativeCode) {
        super(msg, node, callstack, t);
        this.inNativeCode = inNativeCode;
    }

    public TargetError(Throwable t, SimpleNode node, CallStack callstack) {
        this("TargetError", t, node, callstack, false);
    }

    public Throwable getTarget() {
        Throwable target = this.getCause();
        if (target instanceof InvocationTargetException) {
            return ((InvocationTargetException)target).getTargetException();
        }
        return target;
    }

    @Override
    public String toString() {
        return super.toString() + "\nTarget exception: " + this.printTargetError(this.getCause());
    }

    @Override
    public String getMessage() {
        return super.getMessage() + "\nTarget exception: " + this.printTargetError(this.getCause());
    }

    @Override
    public void printStackTrace() {
        this.printStackTrace(false, System.err);
    }

    @Override
    public void printStackTrace(PrintStream out) {
        this.printStackTrace(false, out);
    }

    public void printStackTrace(boolean debug, PrintStream out) {
        if (debug) {
            super.printStackTrace(out);
            out.println("--- Target Stack Trace ---");
        }
        this.getCause().printStackTrace(out);
    }

    private String printTargetError(Throwable t) {
        return this.getCause().toString() + "\n" + this.xPrintTargetError(t);
    }

    private String xPrintTargetError(Throwable t) {
        String getTarget = "import java.lang.reflect.UndeclaredThrowableException;String result=\"\";while ( target instanceof UndeclaredThrowableException ) {\ttarget=target.getUndeclaredThrowable(); \tresult+=\"Nested: \"+target.toString();}return result;";
        Interpreter i = new Interpreter();
        try {
            i.set("target", t);
            return (String)i.eval(getTarget);
        }
        catch (EvalError e) {
            throw new InterpreterError("xprintarget: " + e.toString());
        }
    }

    public boolean inNativeCode() {
        return this.inNativeCode;
    }
}

