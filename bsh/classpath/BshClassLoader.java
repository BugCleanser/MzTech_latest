/*
 * Decompiled with CFR 0.152.
 */
package bsh.classpath;

import bsh.BshClassManager;
import bsh.Interpreter;
import bsh.classpath.BshClassPath;
import bsh.classpath.ClassManagerImpl;
import java.net.URL;
import java.net.URLClassLoader;

public class BshClassLoader
extends URLClassLoader {
    BshClassManager classManager;

    public BshClassLoader(BshClassManager classManager, URL[] bases) {
        super(bases);
        this.classManager = classManager;
    }

    public BshClassLoader(BshClassManager classManager, BshClassPath bcp) {
        this(classManager, bcp.getPathComponents());
    }

    protected BshClassLoader(BshClassManager classManager) {
        this(classManager, new URL[0]);
    }

    @Override
    public void addURL(URL url) {
        super.addURL(url);
    }

    public Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
        if (name.startsWith("java.")) {
            return super.loadClass(name, resolve);
        }
        Class c = this.findLoadedClass(name);
        if (c != null) {
            return c;
        }
        if (name.startsWith("bsh")) {
            try {
                return Interpreter.class.getClassLoader().loadClass(name);
            }
            catch (ClassNotFoundException classNotFoundException) {
                // empty catch block
            }
        }
        c = this.findClass(name);
        if (resolve) {
            this.resolveClass(c);
        }
        return c;
    }

    protected Class findClass(String name) throws ClassNotFoundException {
        ClassManagerImpl bcm = (ClassManagerImpl)this.getClassManager();
        ClassLoader cl = bcm.getLoaderForClass(name);
        if (cl != null && cl != this) {
            try {
                return cl.loadClass(name);
            }
            catch (ClassNotFoundException e) {
                throw new ClassNotFoundException("Designated loader could not find class: " + e);
            }
        }
        if (this.getURLs().length > 0) {
            try {
                return super.findClass(name);
            }
            catch (ClassNotFoundException classNotFoundException) {
                // empty catch block
            }
        }
        if ((cl = bcm.getBaseLoader()) != null && cl != this) {
            try {
                return cl.loadClass(name);
            }
            catch (ClassNotFoundException classNotFoundException) {
                // empty catch block
            }
        }
        return bcm.plainClassForName(name);
    }

    BshClassManager getClassManager() {
        return this.classManager;
    }
}

