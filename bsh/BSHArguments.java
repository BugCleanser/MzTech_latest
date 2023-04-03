/*
 * Decompiled with CFR 0.152.
 */
package bsh;

import bsh.CallStack;
import bsh.EvalError;
import bsh.Interpreter;
import bsh.Primitive;
import bsh.SimpleNode;

class BSHArguments
extends SimpleNode {
    BSHArguments(int id) {
        super(id);
    }

    public Object[] getArguments(CallStack callstack, Interpreter interpreter) throws EvalError {
        Object[] args = new Object[this.jjtGetNumChildren()];
        for (int i = 0; i < args.length; ++i) {
            args[i] = ((SimpleNode)this.jjtGetChild(i)).eval(callstack, interpreter);
            if (args[i] != Primitive.VOID) continue;
            throw new EvalError("Undefined argument: " + ((SimpleNode)this.jjtGetChild(i)).getText(), this, callstack);
        }
        return args;
    }
}

