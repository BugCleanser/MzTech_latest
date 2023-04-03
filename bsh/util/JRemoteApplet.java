/*
 * Decompiled with CFR 0.152.
 */
package bsh.util;

import bsh.util.JConsole;
import java.awt.BorderLayout;
import java.awt.Label;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import javax.swing.JApplet;

public class JRemoteApplet
extends JApplet {
    OutputStream out;
    InputStream in;

    @Override
    public void init() {
        this.getContentPane().setLayout(new BorderLayout());
        try {
            URL base = this.getDocumentBase();
            Socket s = new Socket(base.getHost(), base.getPort() + 1);
            this.out = s.getOutputStream();
            this.in = s.getInputStream();
        }
        catch (IOException e) {
            this.getContentPane().add("Center", new Label("Remote Connection Failed", 1));
            return;
        }
        JConsole console = new JConsole(this.in, this.out);
        this.getContentPane().add("Center", console);
    }
}

