/*
 * Decompiled with CFR 0.152.
 */
package javassist.tools.reflect;

import java.io.PrintStream;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.tools.reflect.CompiledClass;
import javassist.tools.reflect.Reflection;

public class Compiler {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            Compiler.help(System.err);
            return;
        }
        CompiledClass[] entries = new CompiledClass[args.length];
        int n = Compiler.parse(args, entries);
        if (n < 1) {
            System.err.println("bad parameter.");
            return;
        }
        Compiler.processClasses(entries, n);
    }

    private static void processClasses(CompiledClass[] entries, int n) throws Exception {
        Reflection implementor = new Reflection();
        ClassPool pool = ClassPool.getDefault();
        implementor.start(pool);
        int i = 0;
        while (i < n) {
            CtClass c = pool.get(entries[i].classname);
            if (entries[i].metaobject != null || entries[i].classobject != null) {
                String metaobj = entries[i].metaobject == null ? "javassist.tools.reflect.Metaobject" : entries[i].metaobject;
                String classobj = entries[i].classobject == null ? "javassist.tools.reflect.ClassMetaobject" : entries[i].classobject;
                if (!implementor.makeReflective(c, pool.get(metaobj), pool.get(classobj))) {
                    System.err.println("Warning: " + c.getName() + " is reflective.  It was not changed.");
                }
                System.err.println(String.valueOf(c.getName()) + ": " + metaobj + ", " + classobj);
            } else {
                System.err.println(String.valueOf(c.getName()) + ": not reflective");
            }
            ++i;
        }
        i = 0;
        while (i < n) {
            implementor.onLoad(pool, entries[i].classname);
            pool.get(entries[i].classname).writeFile();
            ++i;
        }
    }

    private static int parse(String[] args, CompiledClass[] result) {
        int n = -1;
        int i = 0;
        while (i < args.length) {
            String a = args[i];
            if (a.equals("-m")) {
                if (n < 0 || i + 1 > args.length) {
                    return -1;
                }
                result[n].metaobject = args[++i];
            } else if (a.equals("-c")) {
                if (n < 0 || i + 1 > args.length) {
                    return -1;
                }
                result[n].classobject = args[++i];
            } else {
                if (a.charAt(0) == '-') {
                    return -1;
                }
                CompiledClass cc = new CompiledClass();
                cc.classname = a;
                cc.metaobject = null;
                cc.classobject = null;
                result[++n] = cc;
            }
            ++i;
        }
        return n + 1;
    }

    private static void help(PrintStream out) {
        out.println("Usage: java javassist.tools.reflect.Compiler");
        out.println("            (<class> [-m <metaobject>] [-c <class metaobject>])+");
    }
}

