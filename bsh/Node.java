/*
 * Decompiled with CFR 0.152.
 */
package bsh;

import java.io.Serializable;

interface Node
extends Serializable {
    public void jjtOpen();

    public void jjtClose();

    public void jjtSetParent(Node var1);

    public Node jjtGetParent();

    public void jjtAddChild(Node var1, int var2);

    public Node jjtGetChild(int var1);

    public int jjtGetNumChildren();
}

