/*
 * Decompiled with CFR 0.152.
 */
package bsh;

import bsh.BSHIfStatement;
import bsh.CallStack;
import bsh.EvalError;
import bsh.Interpreter;
import bsh.ParserConstants;
import bsh.Primitive;
import bsh.ReturnControl;
import bsh.SimpleNode;

class BSHWhileStatement
extends SimpleNode
implements ParserConstants {
    boolean isDoStatement;

    BSHWhileStatement(int id) {
        super(id);
    }

    @Override
    public Object eval(CallStack callstack, Interpreter interpreter) throws EvalError {
        SimpleNode body;
        SimpleNode condExp;
        int numChild = this.jjtGetNumChildren();
        if (this.isDoStatement) {
            condExp = (SimpleNode)this.jjtGetChild(1);
            body = (SimpleNode)this.jjtGetChild(0);
        } else {
            condExp = (SimpleNode)this.jjtGetChild(0);
            body = numChild > 1 ? (SimpleNode)this.jjtGetChild(1) : null;
        }
        boolean doOnceFlag = this.isDoStatement;
        while (doOnceFlag || BSHIfStatement.evaluateCondition(condExp, callstack, interpreter)) {
            Object ret;
            doOnceFlag = false;
            if (body == null || !((ret = body.eval(callstack, interpreter)) instanceof ReturnControl)) continue;
            switch (((ReturnControl)ret).kind) {
                case 46: {
                    return ret;
                }
                case 19: {
                    break;
                }
                case 12: {
                    return Primitive.VOID;
                }
            }
        }
        return Primitive.VOID;
    }
}

