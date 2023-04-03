/*
 * Decompiled with CFR 0.152.
 */
package bsh;

import bsh.BSHAmbiguousName;
import bsh.CallStack;
import bsh.EvalError;
import bsh.Interpreter;
import bsh.NameSpace;
import bsh.Primitive;
import bsh.SimpleNode;

public class BSHPackageDeclaration
extends SimpleNode {
    public BSHPackageDeclaration(int id) {
        super(id);
    }

    @Override
    public Object eval(CallStack callstack, Interpreter interpreter) throws EvalError {
        BSHAmbiguousName name = (BSHAmbiguousName)this.jjtGetChild(0);
        NameSpace namespace = callstack.top();
        namespace.setPackage(name.text);
        namespace.importPackage(name.text);
        return Primitive.VOID;
    }
}

