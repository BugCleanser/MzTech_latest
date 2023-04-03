/*
 * Decompiled with CFR 0.152.
 */
package mz.tech.util.message;

public abstract class ShowOnMouse {
    public abstract String getAction();

    public abstract String getValue();

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int[] nArray = this.getValue().chars().toArray();
        int n = nArray.length;
        int n2 = 0;
        while (n2 < n) {
            int c2 = nArray[n2];
            if (c2 == 34 || c2 == 92) {
                sb.append("\\");
            }
            sb.append((char)c2);
            ++n2;
        }
        return "{\"action\":\"" + this.getAction() + "\",\"value\":\"" + sb.toString() + "\"}";
    }
}

