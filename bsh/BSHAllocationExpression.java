/*
 * Decompiled with CFR 0.152.
 */
package bsh;

import bsh.BSHAmbiguousName;
import bsh.BSHArguments;
import bsh.BSHArrayDimensions;
import bsh.BSHBlock;
import bsh.BSHPrimitiveType;
import bsh.CallStack;
import bsh.ClassGenerator;
import bsh.ClassGeneratorUtil;
import bsh.ClassIdentifier;
import bsh.EvalError;
import bsh.GeneratedClass;
import bsh.Interpreter;
import bsh.Modifiers;
import bsh.Name;
import bsh.NameSpace;
import bsh.Primitive;
import bsh.Reflect;
import bsh.ReflectError;
import bsh.SimpleNode;
import bsh.TargetError;
import bsh.This;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;

class BSHAllocationExpression
extends SimpleNode {
    private static int innerClassCount = 0;

    BSHAllocationExpression(int id) {
        super(id);
    }

    @Override
    public Object eval(CallStack callstack, Interpreter interpreter) throws EvalError {
        SimpleNode type = (SimpleNode)this.jjtGetChild(0);
        SimpleNode args = (SimpleNode)this.jjtGetChild(1);
        if (type instanceof BSHAmbiguousName) {
            BSHAmbiguousName name = (BSHAmbiguousName)type;
            if (args instanceof BSHArguments) {
                return this.objectAllocation(name, (BSHArguments)args, callstack, interpreter);
            }
            return this.objectArrayAllocation(name, (BSHArrayDimensions)args, callstack, interpreter);
        }
        return this.primitiveArrayAllocation((BSHPrimitiveType)type, (BSHArrayDimensions)args, callstack, interpreter);
    }

    private Object objectAllocation(BSHAmbiguousName nameNode, BSHArguments argumentsNode, CallStack callstack, Interpreter interpreter) throws EvalError {
        boolean hasBody;
        NameSpace namespace = callstack.top();
        Object[] args = argumentsNode.getArguments(callstack, interpreter);
        if (args == null) {
            throw new EvalError("Null args in new.", this, callstack);
        }
        Object obj = nameNode.toObject(callstack, interpreter, false);
        obj = nameNode.toObject(callstack, interpreter, true);
        Class type = null;
        if (!(obj instanceof ClassIdentifier)) {
            throw new EvalError("Unknown class: " + nameNode.text, this, callstack);
        }
        type = ((ClassIdentifier)obj).getTargetClass();
        boolean bl = hasBody = this.jjtGetNumChildren() > 2;
        if (hasBody) {
            BSHBlock body = (BSHBlock)this.jjtGetChild(2);
            if (type.isInterface()) {
                return this.constructWithInterfaceBody(type, args, body, callstack, interpreter);
            }
            return this.constructWithClassBody(type, args, body, callstack, interpreter);
        }
        return this.constructObject(type, args, callstack, interpreter);
    }

    private Object constructObject(Class<?> type, Object[] args, CallStack callstack, Interpreter interpreter) throws EvalError {
        Object obj;
        boolean isGeneratedClass = GeneratedClass.class.isAssignableFrom(type);
        if (isGeneratedClass) {
            ClassGeneratorUtil.registerConstructorContext(callstack, interpreter);
        }
        try {
            obj = Reflect.constructObject(type, args);
        }
        catch (ReflectError e) {
            throw new EvalError("Constructor error: " + e.getMessage(), this, callstack);
        }
        catch (InvocationTargetException e) {
            Interpreter.debug("The constructor threw an exception:\n\t" + e.getTargetException());
            throw new TargetError("Object constructor", e.getTargetException(), this, callstack, true);
        }
        finally {
            if (isGeneratedClass) {
                ClassGeneratorUtil.registerConstructorContext(null, null);
            }
        }
        String className = type.getName();
        if (className.indexOf("$") == -1) {
            return obj;
        }
        This ths = callstack.top().getThis(null);
        NameSpace instanceNameSpace = Name.getClassNameSpace(ths.getNameSpace());
        if (instanceNameSpace != null && className.startsWith(instanceNameSpace.getName() + "$")) {
            ClassGenerator.getClassGenerator().setInstanceNameSpaceParent(obj, className, instanceNameSpace);
        }
        return obj;
    }

    private Object constructWithClassBody(Class type, Object[] args, BSHBlock block, CallStack callstack, Interpreter interpreter) throws EvalError {
        String name = callstack.top().getName() + "$" + ++innerClassCount;
        Modifiers modifiers = new Modifiers();
        modifiers.addModifier(0, "public");
        Class clas = ClassGenerator.getClassGenerator().generateClass(name, modifiers, null, type, block, false, callstack, interpreter);
        try {
            return Reflect.constructObject(clas, args);
        }
        catch (Exception e) {
            Throwable cause = e;
            if (e instanceof InvocationTargetException) {
                cause = ((InvocationTargetException)e).getTargetException();
            }
            throw new EvalError("Error constructing inner class instance: " + e, this, callstack, cause);
        }
    }

    private Object constructWithInterfaceBody(Class type, Object[] args, BSHBlock body, CallStack callstack, Interpreter interpreter) throws EvalError {
        NameSpace namespace = callstack.top();
        NameSpace local = new NameSpace(namespace, "AnonymousBlock");
        callstack.push(local);
        body.eval(callstack, interpreter, true);
        callstack.pop();
        local.importStatic(type);
        return local.getThis(interpreter).getInterface(type);
    }

    private Object objectArrayAllocation(BSHAmbiguousName nameNode, BSHArrayDimensions dimensionsNode, CallStack callstack, Interpreter interpreter) throws EvalError {
        NameSpace namespace = callstack.top();
        Class type = nameNode.toClass(callstack, interpreter);
        if (type == null) {
            throw new EvalError("Class " + nameNode.getName(namespace) + " not found.", this, callstack);
        }
        return this.arrayAllocation(dimensionsNode, type, callstack, interpreter);
    }

    private Object primitiveArrayAllocation(BSHPrimitiveType typeNode, BSHArrayDimensions dimensionsNode, CallStack callstack, Interpreter interpreter) throws EvalError {
        Class type = typeNode.getType();
        return this.arrayAllocation(dimensionsNode, type, callstack, interpreter);
    }

    private Object arrayAllocation(BSHArrayDimensions dimensionsNode, Class type, CallStack callstack, Interpreter interpreter) throws EvalError {
        Object result = dimensionsNode.eval(type, callstack, interpreter);
        if (result != Primitive.VOID) {
            return result;
        }
        return this.arrayNewInstance(type, dimensionsNode, callstack);
    }

    private Object arrayNewInstance(Class type, BSHArrayDimensions dimensionsNode, CallStack callstack) throws EvalError {
        if (dimensionsNode.numUndefinedDims > 0) {
            Object proto = Array.newInstance(type, new int[dimensionsNode.numUndefinedDims]);
            type = proto.getClass();
        }
        try {
            return Array.newInstance(type, dimensionsNode.definedDimensions);
        }
        catch (NegativeArraySizeException e1) {
            throw new TargetError(e1, (SimpleNode)this, callstack);
        }
        catch (Exception e) {
            throw new EvalError("Can't construct primitive array: " + e.getMessage(), this, callstack);
        }
    }
}

