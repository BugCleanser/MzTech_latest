/*
 * Decompiled with CFR 0.152.
 */
package bsh;

import bsh.BSHAmbiguousName;
import bsh.BSHBlock;
import bsh.CallStack;
import bsh.ClassGenerator;
import bsh.EvalError;
import bsh.Interpreter;
import bsh.Modifiers;
import bsh.SimpleNode;

class BSHClassDeclaration
extends SimpleNode {
    static final String CLASSINITNAME = "_bshClassInit";
    String name;
    Modifiers modifiers;
    int numInterfaces;
    boolean extend;
    boolean isInterface;
    private Class<?> generatedClass;

    BSHClassDeclaration(int id) {
        super(id);
    }

    @Override
    public synchronized Object eval(CallStack callstack, Interpreter interpreter) throws EvalError {
        if (this.generatedClass == null) {
            this.generatedClass = this.generateClass(callstack, interpreter);
        }
        return this.generatedClass;
    }

    private Class<?> generateClass(CallStack callstack, Interpreter interpreter) throws EvalError {
        int child = 0;
        Class superClass = null;
        if (this.extend) {
            BSHAmbiguousName superNode = (BSHAmbiguousName)this.jjtGetChild(child++);
            superClass = superNode.toClass(callstack, interpreter);
        }
        Class[] interfaces = new Class[this.numInterfaces];
        for (int i = 0; i < this.numInterfaces; ++i) {
            BSHAmbiguousName node = (BSHAmbiguousName)this.jjtGetChild(child++);
            interfaces[i] = node.toClass(callstack, interpreter);
            if (interfaces[i].isInterface()) continue;
            throw new EvalError("Type: " + node.text + " is not an interface!", this, callstack);
        }
        BSHBlock block = child < this.jjtGetNumChildren() ? (BSHBlock)this.jjtGetChild(child) : new BSHBlock(25);
        return ClassGenerator.getClassGenerator().generateClass(this.name, this.modifiers, interfaces, superClass, block, this.isInterface, callstack, interpreter);
    }

    @Override
    public String toString() {
        return "ClassDeclaration: " + this.name;
    }
}

