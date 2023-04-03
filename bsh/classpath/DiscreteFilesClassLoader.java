/*
 * Decompiled with CFR 0.152.
 */
package bsh.classpath;

import bsh.BshClassManager;
import bsh.classpath.BshClassLoader;
import bsh.classpath.BshClassPath;
import java.util.HashMap;

public class DiscreteFilesClassLoader
extends BshClassLoader {
    ClassSourceMap map;

    public DiscreteFilesClassLoader(BshClassManager classManager, ClassSourceMap map) {
        super(classManager);
        this.map = map;
    }

    @Override
    public Class findClass(String name) throws ClassNotFoundException {
        BshClassPath.ClassSource source = this.map.get(name);
        if (source != null) {
            byte[] code = source.getCode(name);
            return this.defineClass(name, code, 0, code.length);
        }
        return super.findClass(name);
    }

    public String toString() {
        return super.toString() + "for files: " + this.map;
    }

    public static class ClassSourceMap
    extends HashMap {
        @Override
        public void put(String name, BshClassPath.ClassSource source) {
            super.put(name, source);
        }

        public BshClassPath.ClassSource get(String name) {
            return (BshClassPath.ClassSource)super.get(name);
        }
    }
}

