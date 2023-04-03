/*
 * Decompiled with CFR 0.152.
 */
package bsh;

import bsh.Capabilities;
import bsh.EvalError;
import bsh.Interpreter;
import bsh.InterpreterError;
import bsh.Name;
import bsh.UtilEvalError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class BshClassManager {
    private Interpreter declaringInterpreter;
    protected ClassLoader externalClassLoader;
    protected transient Map<String, Class> absoluteClassCache = new Hashtable<String, Class>();
    protected transient Set<String> absoluteNonClasses = Collections.synchronizedSet(new HashSet());
    protected volatile transient Map<SignatureKey, Method> resolvedObjectMethods = new Hashtable<SignatureKey, Method>();
    protected volatile transient Map<SignatureKey, Method> resolvedStaticMethods = new Hashtable<SignatureKey, Method>();
    private transient Set<String> definingClasses = Collections.synchronizedSet(new HashSet());
    protected transient Map<String, String> definingClassesBaseNames = new Hashtable<String, String>();
    private static final Map<BshClassManager, Object> classManagers = Collections.synchronizedMap(new WeakHashMap());
    protected transient Hashtable associatedClasses = new Hashtable();

    static void clearResolveCache() {
        BshClassManager[] managers;
        for (BshClassManager m : managers = classManagers.keySet().toArray(new BshClassManager[0])) {
            m.resolvedObjectMethods = new Hashtable<SignatureKey, Method>();
            m.resolvedStaticMethods = new Hashtable<SignatureKey, Method>();
        }
    }

    public static BshClassManager createClassManager(Interpreter interpreter) {
        BshClassManager manager;
        if (Capabilities.classExists("bsh.classpath.ClassManagerImpl")) {
            try {
                Class<?> clazz = Class.forName("bsh.classpath.ClassManagerImpl");
                manager = (BshClassManager)clazz.newInstance();
            }
            catch (Exception e) {
                throw new InterpreterError("Error loading classmanager", e);
            }
        } else {
            manager = new BshClassManager();
        }
        if (interpreter == null) {
            interpreter = new Interpreter();
        }
        manager.declaringInterpreter = interpreter;
        classManagers.put(manager, null);
        return manager;
    }

    public boolean classExists(String name) {
        return this.classForName(name) != null;
    }

    public Class classForName(String name) {
        if (this.isClassBeingDefined(name)) {
            throw new InterpreterError("Attempting to load class in the process of being defined: " + name);
        }
        Class<?> clas = null;
        try {
            clas = this.plainClassForName(name);
        }
        catch (ClassNotFoundException classNotFoundException) {
            // empty catch block
        }
        if (clas == null && this.declaringInterpreter.getCompatibility()) {
            clas = this.loadSourceClass(name);
        }
        return clas;
    }

    protected Class<?> loadSourceClass(String name) {
        block5: {
            String fileName = "/" + name.replace('.', '/') + ".java";
            InputStream in = this.getResourceAsStream(fileName);
            if (in == null) {
                return null;
            }
            try {
                Interpreter.debug("Loading class from source file: " + fileName);
                this.declaringInterpreter.eval(new InputStreamReader(in));
            }
            catch (EvalError e) {
                if (!Interpreter.DEBUG) break block5;
                e.printStackTrace();
            }
        }
        try {
            return this.plainClassForName(name);
        }
        catch (ClassNotFoundException e) {
            Interpreter.debug("Class not found in source file: " + name);
            return null;
        }
    }

    public Class plainClassForName(String name) throws ClassNotFoundException {
        Class<?> c = null;
        c = this.externalClassLoader != null ? this.externalClassLoader.loadClass(name) : Class.forName(name);
        this.cacheClassInfo(name, c);
        return c;
    }

    public URL getResource(String path) {
        URL url = null;
        if (this.externalClassLoader != null) {
            url = this.externalClassLoader.getResource(path.substring(1));
        }
        if (url == null) {
            url = Interpreter.class.getResource(path);
        }
        return url;
    }

    public InputStream getResourceAsStream(String path) {
        InputStream in = null;
        if (this.externalClassLoader != null) {
            in = this.externalClassLoader.getResourceAsStream(path.substring(1));
        }
        if (in == null) {
            in = Interpreter.class.getResourceAsStream(path);
        }
        return in;
    }

    public void cacheClassInfo(String name, Class value) {
        if (value != null) {
            this.absoluteClassCache.put(name, value);
        } else {
            this.absoluteNonClasses.add(name);
        }
    }

    public void associateClass(Class clas) {
        this.associatedClasses.put(clas.getName(), clas);
    }

    public Class getAssociatedClass(String name) {
        return (Class)this.associatedClasses.get(name);
    }

    public void cacheResolvedMethod(Class clas, Class[] types, Method method) {
        if (Interpreter.DEBUG) {
            Interpreter.debug("cacheResolvedMethod putting: " + clas + " " + method);
        }
        SignatureKey sk = new SignatureKey(clas, method.getName(), types);
        if (Modifier.isStatic(method.getModifiers())) {
            this.resolvedStaticMethods.put(sk, method);
        } else {
            this.resolvedObjectMethods.put(sk, method);
        }
    }

    protected Method getResolvedMethod(Class clas, String methodName, Class[] types, boolean onlyStatic) {
        SignatureKey sk = new SignatureKey(clas, methodName, types);
        Method method = this.resolvedStaticMethods.get(sk);
        if (method == null && !onlyStatic) {
            method = this.resolvedObjectMethods.get(sk);
        }
        if (Interpreter.DEBUG) {
            if (method == null) {
                Interpreter.debug("getResolvedMethod cache MISS: " + clas + " - " + methodName);
            } else {
                Interpreter.debug("getResolvedMethod cache HIT: " + clas + " - " + method);
            }
        }
        return method;
    }

    protected void clearCaches() {
        this.absoluteNonClasses = Collections.synchronizedSet(new HashSet());
        this.absoluteClassCache = new Hashtable<String, Class>();
        this.resolvedObjectMethods = new Hashtable<SignatureKey, Method>();
        this.resolvedStaticMethods = new Hashtable<SignatureKey, Method>();
    }

    public void setClassLoader(ClassLoader externalCL) {
        this.externalClassLoader = externalCL;
        this.classLoaderChanged();
    }

    public void addClassPath(URL path) throws IOException {
    }

    public void reset() {
        this.clearCaches();
    }

    public void setClassPath(URL[] cp) throws UtilEvalError {
        throw BshClassManager.cmUnavailable();
    }

    public void reloadAllClasses() throws UtilEvalError {
        throw BshClassManager.cmUnavailable();
    }

    public void reloadClasses(String[] classNames) throws UtilEvalError {
        throw BshClassManager.cmUnavailable();
    }

    public void reloadPackage(String pack) throws UtilEvalError {
        throw BshClassManager.cmUnavailable();
    }

    protected void doSuperImport() throws UtilEvalError {
        throw BshClassManager.cmUnavailable();
    }

    protected boolean hasSuperImport() {
        return false;
    }

    protected String getClassNameByUnqName(String name) throws UtilEvalError {
        throw BshClassManager.cmUnavailable();
    }

    public void addListener(Listener l) {
    }

    public void removeListener(Listener l) {
    }

    public void dump(PrintWriter pw) {
        pw.println("BshClassManager: no class manager.");
    }

    protected void definingClass(String className) {
        String cur;
        String baseName = Name.suffix(className, 1);
        int i = baseName.indexOf("$");
        if (i != -1) {
            baseName = baseName.substring(i + 1);
        }
        if ((cur = this.definingClassesBaseNames.get(baseName)) != null) {
            throw new InterpreterError("Defining class problem: " + className + ": BeanShell cannot yet simultaneously define two or more dependent classes of the same name.  Attempt to define: " + className + " while defining: " + cur);
        }
        this.definingClasses.add(className);
        this.definingClassesBaseNames.put(baseName, className);
    }

    protected boolean isClassBeingDefined(String className) {
        return this.definingClasses.contains(className);
    }

    protected String getClassBeingDefined(String className) {
        String baseName = Name.suffix(className, 1);
        return this.definingClassesBaseNames.get(baseName);
    }

    protected void doneDefiningClass(String className) {
        String baseName = Name.suffix(className, 1);
        this.definingClasses.remove(className);
        this.definingClassesBaseNames.remove(baseName);
    }

    public Class defineClass(String name, byte[] code) {
        throw new InterpreterError("Can't create class (" + name + ") without class manager package.");
    }

    protected void classLoaderChanged() {
    }

    protected static UtilEvalError cmUnavailable() {
        return new Capabilities.Unavailable("ClassLoading features unavailable.");
    }

    static class SignatureKey {
        Class clas;
        Class[] types;
        String methodName;
        int hashCode = 0;

        SignatureKey(Class clas, String methodName, Class[] types) {
            this.clas = clas;
            this.methodName = methodName;
            this.types = types;
        }

        public int hashCode() {
            if (this.hashCode == 0) {
                this.hashCode = this.clas.hashCode() * this.methodName.hashCode();
                if (this.types == null) {
                    return this.hashCode;
                }
                for (int i = 0; i < this.types.length; ++i) {
                    int hc = this.types[i] == null ? 21 : this.types[i].hashCode();
                    this.hashCode = this.hashCode * (i + 1) + hc;
                }
            }
            return this.hashCode;
        }

        public boolean equals(Object o) {
            SignatureKey target = (SignatureKey)o;
            if (this.types == null) {
                return target.types == null;
            }
            if (this.clas != target.clas) {
                return false;
            }
            if (!this.methodName.equals(target.methodName)) {
                return false;
            }
            if (this.types.length != target.types.length) {
                return false;
            }
            for (int i = 0; i < this.types.length; ++i) {
                if (!(this.types[i] == null ? target.types[i] != null : !this.types[i].equals(target.types[i]))) continue;
                return false;
            }
            return true;
        }
    }

    public static interface Listener {
        public void classLoaderChanged();
    }
}

