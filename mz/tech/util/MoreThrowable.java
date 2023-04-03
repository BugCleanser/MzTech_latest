/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package mz.tech.util;

import com.google.common.collect.Lists;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MoreThrowable
extends Throwable {
    private static final long serialVersionUID = 1L;
    public List<Throwable> e;

    public MoreThrowable(Throwable ... e) {
        this.e = Lists.newArrayList((Object[])e);
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        ArrayList l = new ArrayList();
        this.e.forEach(e1 -> {
            if (e1 != null) {
                l.addAll(Lists.newArrayList((Object[])e1.getStackTrace()));
            }
        });
        return l.toArray(new StackTraceElement[l.size()]);
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        this.e.forEach(e1 -> {
            if (e1 != null) {
                e1.printStackTrace(s);
                s.print("\r\n\r\n");
            }
        });
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder("MoreThrowable{");
        this.e.forEach(e1 -> sb.append(String.valueOf(e1.getClass().getName()) + ":" + e1.getMessage() + ";"));
        sb.setCharAt(sb.length() - 1, '}');
        return sb.toString();
    }

    @Override
    public synchronized Throwable getCause() {
        ArrayList cs = new ArrayList();
        this.e.forEach(e1 -> {
            Throwable c2;
            if (e1 != null && (c2 = e1.getCause()) != null) {
                cs.add(c2);
            }
        });
        if (cs.isEmpty()) {
            return null;
        }
        if (cs.size() == 1) {
            return (Throwable)cs.get(0);
        }
        return new MoreThrowable(cs.toArray(new Throwable[cs.size()]));
    }
}

