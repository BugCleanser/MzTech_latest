/*
 * Decompiled with CFR 0.152.
 */
package javassist.bytecode;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;
import javassist.bytecode.AttributeInfo;
import javassist.bytecode.ByteArray;
import javassist.bytecode.ConstPool;

public class BootstrapMethodsAttribute
extends AttributeInfo {
    public static final String tag = "BootstrapMethods";

    BootstrapMethodsAttribute(ConstPool cp, int n, DataInputStream in) throws IOException {
        super(cp, n, in);
    }

    public BootstrapMethodsAttribute(ConstPool cp, BootstrapMethod[] methods) {
        super(cp, tag);
        int size = 2;
        int i = 0;
        while (i < methods.length) {
            size += 4 + methods[i].arguments.length * 2;
            ++i;
        }
        byte[] data = new byte[size];
        ByteArray.write16bit(methods.length, data, 0);
        int pos = 2;
        int i2 = 0;
        while (i2 < methods.length) {
            ByteArray.write16bit(methods[i2].methodRef, data, pos);
            ByteArray.write16bit(methods[i2].arguments.length, data, pos + 2);
            int[] args = methods[i2].arguments;
            pos += 4;
            int k = 0;
            while (k < args.length) {
                ByteArray.write16bit(args[k], data, pos);
                pos += 2;
                ++k;
            }
            ++i2;
        }
        this.set(data);
    }

    public BootstrapMethod[] getMethods() {
        byte[] data = this.get();
        int num = ByteArray.readU16bit(data, 0);
        BootstrapMethod[] methods = new BootstrapMethod[num];
        int pos = 2;
        int i = 0;
        while (i < num) {
            int ref = ByteArray.readU16bit(data, pos);
            int len = ByteArray.readU16bit(data, pos + 2);
            int[] args = new int[len];
            pos += 4;
            int k = 0;
            while (k < len) {
                args[k] = ByteArray.readU16bit(data, pos);
                pos += 2;
                ++k;
            }
            methods[i] = new BootstrapMethod(ref, args);
            ++i;
        }
        return methods;
    }

    @Override
    public AttributeInfo copy(ConstPool newCp, Map<String, String> classnames) {
        BootstrapMethod[] methods = this.getMethods();
        ConstPool thisCp = this.getConstPool();
        int i = 0;
        while (i < methods.length) {
            BootstrapMethod m = methods[i];
            m.methodRef = thisCp.copy(m.methodRef, newCp, classnames);
            int k = 0;
            while (k < m.arguments.length) {
                m.arguments[k] = thisCp.copy(m.arguments[k], newCp, classnames);
                ++k;
            }
            ++i;
        }
        return new BootstrapMethodsAttribute(newCp, methods);
    }

    public static class BootstrapMethod {
        public int methodRef;
        public int[] arguments;

        public BootstrapMethod(int method, int[] args) {
            this.methodRef = method;
            this.arguments = args;
        }
    }
}

