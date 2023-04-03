/*
 * Decompiled with CFR 0.152.
 */
package bsh;

import bsh.CallStack;
import bsh.NameSpace;
import bsh.SimpleNode;

public class EvalError
extends Exception {
    private SimpleNode node;
    private String message;
    private final CallStack callstack;

    public EvalError(String s, SimpleNode node, CallStack callstack, Throwable cause) {
        this(s, node, callstack);
        this.initCause(cause);
    }

    public EvalError(String s, SimpleNode node, CallStack callstack) {
        this.message = s;
        this.node = node;
        this.callstack = callstack == null ? null : callstack.copy();
    }

    @Override
    public String getMessage() {
        String trace = this.node != null ? " : at Line: " + this.node.getLineNumber() + " : in file: " + this.node.getSourceFile() + " : " + this.node.getText() : ": <at unknown location>";
        if (this.callstack != null) {
            trace = trace + "\n" + this.getScriptStackTrace();
        }
        return this.getRawMessage() + trace;
    }

    public void reThrow(String msg) throws EvalError {
        this.prependMessage(msg);
        throw this;
    }

    SimpleNode getNode() {
        return this.node;
    }

    void setNode(SimpleNode node) {
        this.node = node;
    }

    public String getErrorText() {
        if (this.node != null) {
            return this.node.getText();
        }
        return "<unknown error>";
    }

    public int getErrorLineNumber() {
        if (this.node != null) {
            return this.node.getLineNumber();
        }
        return -1;
    }

    public String getErrorSourceFile() {
        if (this.node != null) {
            return this.node.getSourceFile();
        }
        return "<unknown file>";
    }

    public String getScriptStackTrace() {
        if (this.callstack == null) {
            return "<Unknown>";
        }
        String trace = "";
        CallStack stack = this.callstack.copy();
        while (stack.depth() > 0) {
            NameSpace ns = stack.pop();
            SimpleNode node = ns.getNode();
            if (!ns.isMethod) continue;
            trace = trace + "\nCalled from method: " + ns.getName();
            if (node == null) continue;
            trace = trace + " : at Line: " + node.getLineNumber() + " : in file: " + node.getSourceFile() + " : " + node.getText();
        }
        return trace;
    }

    public String getRawMessage() {
        return this.message;
    }

    private void prependMessage(String s) {
        if (s == null) {
            return;
        }
        this.message = this.message == null ? s : s + " : " + this.message;
    }
}

