/*
 * Decompiled with CFR 0.152.
 */
package bsh;

import bsh.CallStack;
import bsh.EvalError;
import bsh.Interpreter;
import bsh.ParserConstants;
import bsh.Primitive;
import bsh.ReturnControl;
import bsh.SimpleNode;

class BSHReturnStatement
extends SimpleNode
implements ParserConstants {
    public int kind;

    BSHReturnStatement(int id) {
        super(id);
    }

    @Override
    public Object eval(CallStack callstack, Interpreter interpreter) throws EvalError {
        Object value = this.jjtGetNumChildren() > 0 ? ((SimpleNode)this.jjtGetChild(0)).eval(callstack, interpreter) : Primitive.VOID;
        return new ReturnControl(this.kind, value, this);
    }
}

