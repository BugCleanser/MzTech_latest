/*
 * Decompiled with CFR 0.152.
 */
package bsh;

import bsh.BSHPrimaryExpression;
import bsh.CallStack;
import bsh.EvalError;
import bsh.Interpreter;
import bsh.InterpreterError;
import bsh.LHS;
import bsh.ParserConstants;
import bsh.Primitive;
import bsh.SimpleNode;
import bsh.UtilEvalError;

class BSHAssignment
extends SimpleNode
implements ParserConstants {
    public int operator;

    BSHAssignment(int id) {
        super(id);
    }

    @Override
    public Object eval(CallStack callstack, Interpreter interpreter) throws EvalError {
        SimpleNode rhsNode;
        Object rhs;
        BSHPrimaryExpression lhsNode = (BSHPrimaryExpression)this.jjtGetChild(0);
        if (lhsNode == null) {
            throw new InterpreterError("Error, null LHSnode");
        }
        boolean strictJava = interpreter.getStrictJava();
        LHS lhs = lhsNode.toLHS(callstack, interpreter);
        if (lhs == null) {
            throw new InterpreterError("Error, null LHS");
        }
        Object lhsValue = null;
        if (this.operator != 82) {
            try {
                lhsValue = lhs.getValue();
            }
            catch (UtilEvalError e) {
                throw e.toEvalError(this, callstack);
            }
        }
        if ((rhs = (rhsNode = (SimpleNode)this.jjtGetChild(1)).eval(callstack, interpreter)) == Primitive.VOID) {
            throw new EvalError("Void assignment.", this, callstack);
        }
        try {
            switch (this.operator) {
                case 82: {
                    return lhs.assign(rhs, strictJava);
                }
                case 119: {
                    return lhs.assign(this.operation(lhsValue, rhs, 103), strictJava);
                }
                case 120: {
                    return lhs.assign(this.operation(lhsValue, rhs, 104), strictJava);
                }
                case 121: {
                    return lhs.assign(this.operation(lhsValue, rhs, 105), strictJava);
                }
                case 122: {
                    return lhs.assign(this.operation(lhsValue, rhs, 106), strictJava);
                }
                case 123: 
                case 124: {
                    return lhs.assign(this.operation(lhsValue, rhs, 107), strictJava);
                }
                case 125: 
                case 126: {
                    return lhs.assign(this.operation(lhsValue, rhs, 109), strictJava);
                }
                case 127: {
                    return lhs.assign(this.operation(lhsValue, rhs, 111), strictJava);
                }
                case 128: {
                    return lhs.assign(this.operation(lhsValue, rhs, 112), strictJava);
                }
                case 129: 
                case 130: {
                    return lhs.assign(this.operation(lhsValue, rhs, 113), strictJava);
                }
                case 131: 
                case 132: {
                    return lhs.assign(this.operation(lhsValue, rhs, 115), strictJava);
                }
                case 133: 
                case 134: {
                    return lhs.assign(this.operation(lhsValue, rhs, 117), strictJava);
                }
            }
            throw new InterpreterError("unimplemented operator in assignment BSH");
        }
        catch (UtilEvalError e) {
            throw e.toEvalError(this, callstack);
        }
    }

    private Object operation(Object lhs, Object rhs, int kind) throws UtilEvalError {
        if (lhs instanceof String && rhs != Primitive.VOID) {
            if (kind != 103) {
                throw new UtilEvalError("Use of non + operator with String LHS");
            }
            return (String)lhs + rhs;
        }
        if (lhs instanceof Primitive || rhs instanceof Primitive) {
            if (lhs == Primitive.VOID || rhs == Primitive.VOID) {
                throw new UtilEvalError("Illegal use of undefined object or 'void' literal");
            }
            if (lhs == Primitive.NULL || rhs == Primitive.NULL) {
                throw new UtilEvalError("Illegal use of null object or 'null' literal");
            }
        }
        if ((lhs instanceof Boolean || lhs instanceof Character || lhs instanceof Number || lhs instanceof Primitive) && (rhs instanceof Boolean || rhs instanceof Character || rhs instanceof Number || rhs instanceof Primitive)) {
            return Primitive.binaryOperation(lhs, rhs, kind);
        }
        throw new UtilEvalError("Non primitive value in operator: " + lhs.getClass() + " " + tokenImage[kind] + " " + rhs.getClass());
    }
}

