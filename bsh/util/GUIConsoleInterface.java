/*
 * Decompiled with CFR 0.152.
 */
package bsh.util;

import bsh.ConsoleInterface;
import bsh.util.NameCompletion;
import java.awt.Color;

public interface GUIConsoleInterface
extends ConsoleInterface {
    public void print(Object var1, Color var2);

    public void setNameCompletion(NameCompletion var1);

    public void setWaitFeedback(boolean var1);
}

