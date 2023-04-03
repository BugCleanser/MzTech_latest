/*
 * Decompiled with CFR 0.152.
 */
package bsh;

import bsh.BshClassManager;
import bsh.UtilEvalError;
import java.lang.reflect.Field;
import java.util.Hashtable;

public class Capabilities {
    private static volatile boolean accessibility = false;
    private static Hashtable classes = new Hashtable();

    public static boolean haveSwing() {
        return Capabilities.classExists("javax.swing.JButton");
    }

    public static boolean haveAccessibility() {
        return accessibility;
    }

    public static void setAccessibility(boolean b) throws Unavailable {
        if (!b) {
            accessibility = false;
        } else {
            try {
                String.class.getDeclaredMethods();
                try {
                    Field field = Capabilities.class.getField("classes");
                    field.setAccessible(true);
                    field.setAccessible(false);
                }
                catch (NoSuchFieldException field) {}
            }
            catch (SecurityException e) {
                throw new Unavailable("Accessibility unavailable: " + e);
            }
            accessibility = true;
        }
        BshClassManager.clearResolveCache();
    }

    public static boolean classExists(String name) {
        Object c = classes.get(name);
        if (c == null) {
            try {
                c = Class.forName(name);
            }
            catch (ClassNotFoundException classNotFoundException) {
                // empty catch block
            }
            if (c != null) {
                classes.put(c, "unused");
            }
        }
        return c != null;
    }

    public static class Unavailable
    extends UtilEvalError {
        public Unavailable(String s) {
            super(s);
        }
    }
}

