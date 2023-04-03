/*
 * Decompiled with CFR 0.152.
 */
package bsh;

import bsh.CallStack;
import bsh.EvalError;
import bsh.Interpreter;
import bsh.SimpleNode;

public class UtilEvalError
extends Exception {
    protected UtilEvalError() {
    }

    public UtilEvalError(String s) {
        super(s);
    }

    public UtilEvalError(String s, Throwable cause) {
        super(s, cause);
    }

    public EvalError toEvalError(String msg, SimpleNode node, CallStack callstack) {
        if (Interpreter.DEBUG) {
            this.printStackTrace();
        }
        msg = msg == null ? "" : msg + ": ";
        return new EvalError(msg + this.getMessage(), node, callstack, this);
    }

    public EvalError toEvalError(SimpleNode node, CallStack callstack) {
        return this.toEvalError(null, node, callstack);
    }
}

