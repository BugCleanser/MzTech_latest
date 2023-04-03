/*
 * Decompiled with CFR 0.152.
 */
package bsh.util;

import bsh.NameSpace;
import bsh.util.SessiondConnection;
import java.io.IOException;
import java.net.ServerSocket;

public class Sessiond
extends Thread {
    private ServerSocket ss;
    NameSpace globalNameSpace;

    public Sessiond(NameSpace globalNameSpace, int port) throws IOException {
        this.ss = new ServerSocket(port);
        this.globalNameSpace = globalNameSpace;
    }

    @Override
    public void run() {
        try {
            while (true) {
                new SessiondConnection(this.globalNameSpace, this.ss.accept()).start();
            }
        }
        catch (IOException e) {
            System.out.println(e);
            return;
        }
    }
}

