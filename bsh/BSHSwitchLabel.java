/*
 * Decompiled with CFR 0.152.
 */
package bsh;

import bsh.CallStack;
import bsh.EvalError;
import bsh.Interpreter;
import bsh.SimpleNode;

class BSHSwitchLabel
extends SimpleNode {
    boolean isDefault;

    public BSHSwitchLabel(int id) {
        super(id);
    }

    @Override
    public Object eval(CallStack callstack, Interpreter interpreter) throws EvalError {
        if (this.isDefault) {
            return null;
        }
        SimpleNode label = (SimpleNode)this.jjtGetChild(0);
        return label.eval(callstack, interpreter);
    }
}

