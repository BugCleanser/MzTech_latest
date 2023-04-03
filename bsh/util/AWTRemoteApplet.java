/*
 * Decompiled with CFR 0.152.
 */
package bsh.util;

import bsh.util.AWTConsole;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Label;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;

public class AWTRemoteApplet
extends Applet {
    OutputStream out;
    InputStream in;

    @Override
    public void init() {
        this.setLayout(new BorderLayout());
        try {
            URL base = this.getDocumentBase();
            Socket s = new Socket(base.getHost(), base.getPort() + 1);
            this.out = s.getOutputStream();
            this.in = s.getInputStream();
        }
        catch (IOException e) {
            this.add("Center", new Label("Remote Connection Failed", 1));
            return;
        }
        AWTConsole console = new AWTConsole(this.in, this.out);
        this.add("Center", console);
    }
}

