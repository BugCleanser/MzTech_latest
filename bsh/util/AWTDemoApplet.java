/*
 * Decompiled with CFR 0.152.
 */
package bsh.util;

import bsh.Interpreter;
import bsh.util.AWTConsole;
import java.applet.Applet;
import java.awt.BorderLayout;

public class AWTDemoApplet
extends Applet {
    @Override
    public void init() {
        this.setLayout(new BorderLayout());
        AWTConsole console = new AWTConsole();
        this.add("Center", console);
        Interpreter interpreter = new Interpreter(console);
        new Thread(interpreter).start();
    }
}

