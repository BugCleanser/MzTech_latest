/*
 * Decompiled with CFR 0.152.
 */
package bsh;

import bsh.InterpreterError;
import bsh.NameSpace;
import java.io.Serializable;
import java.util.EmptyStackException;
import java.util.Stack;

public final class CallStack
implements Serializable {
    private static final long serialVersionUID = 0L;
    private final Stack<NameSpace> stack = new Stack();

    public CallStack() {
    }

    public CallStack(NameSpace namespace) {
        this.push(namespace);
    }

    public void clear() {
        this.stack.removeAllElements();
    }

    public void push(NameSpace ns) {
        this.stack.push(ns);
    }

    public NameSpace top() {
        return this.stack.peek();
    }

    public NameSpace get(int depth) {
        int size = this.stack.size();
        if (depth >= size) {
            return NameSpace.JAVACODE;
        }
        return (NameSpace)this.stack.get(size - 1 - depth);
    }

    public void set(int depth, NameSpace ns) {
        this.stack.set(this.stack.size() - 1 - depth, ns);
    }

    public NameSpace pop() {
        try {
            return this.stack.pop();
        }
        catch (EmptyStackException e) {
            throw new InterpreterError("pop on empty CallStack");
        }
    }

    public NameSpace swap(NameSpace newTop) {
        int last = this.stack.size() - 1;
        NameSpace oldTop = (NameSpace)this.stack.get(last);
        this.stack.set(last, newTop);
        return oldTop;
    }

    public int depth() {
        return this.stack.size();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CallStack:\n");
        for (int i = this.stack.size() - 1; i >= 0; --i) {
            sb.append("\t" + this.stack.get(i) + "\n");
        }
        return sb.toString();
    }

    public CallStack copy() {
        CallStack cs = new CallStack();
        cs.stack.addAll(this.stack);
        return cs;
    }
}

