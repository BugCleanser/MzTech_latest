/*
 * Decompiled with CFR 0.152.
 */
package bsh;

import bsh.Reflect;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class StringUtil {
    public static String[] split(String s, String delim) {
        ArrayList<String> v = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(s, delim);
        while (st.hasMoreTokens()) {
            v.add(st.nextToken());
        }
        return v.toArray(new String[0]);
    }

    public static String maxCommonPrefix(String one, String two) {
        int i = 0;
        while (one.regionMatches(0, two, 0, i)) {
            ++i;
        }
        return one.substring(0, i - 1);
    }

    public static String methodString(String name, Class[] types) {
        StringBuilder sb = new StringBuilder(name + "(");
        if (types.length > 0) {
            sb.append(" ");
        }
        for (int i = 0; i < types.length; ++i) {
            Class c = types[i];
            sb.append((c == null ? "null" : c.getName()) + (i < types.length - 1 ? ", " : " "));
        }
        sb.append(")");
        return sb.toString();
    }

    public static String normalizeClassName(Class type) {
        return Reflect.normalizeClassName(type);
    }
}

