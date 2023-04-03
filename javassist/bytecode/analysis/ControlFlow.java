/*
 * Decompiled with CFR 0.152.
 */
package javassist.bytecode.analysis;

import java.util.ArrayList;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.analysis.Analyzer;
import javassist.bytecode.analysis.Frame;
import javassist.bytecode.stackmap.BasicBlock;

public class ControlFlow {
    private CtClass clazz;
    private MethodInfo methodInfo;
    private Block[] basicBlocks;
    private Frame[] frames;

    public ControlFlow(CtMethod method) throws BadBytecode {
        this(method.getDeclaringClass(), method.getMethodInfo2());
    }

    public ControlFlow(CtClass ctclazz, MethodInfo minfo) throws BadBytecode {
        Block b;
        this.clazz = ctclazz;
        this.methodInfo = minfo;
        this.frames = null;
        this.basicBlocks = (Block[])new BasicBlock.Maker(){

            @Override
            protected BasicBlock makeBlock(int pos) {
                return new Block(pos, ControlFlow.this.methodInfo);
            }

            @Override
            protected BasicBlock[] makeArray(int size) {
                return new Block[size];
            }
        }.make(minfo);
        if (this.basicBlocks == null) {
            this.basicBlocks = new Block[0];
        }
        int size = this.basicBlocks.length;
        int[] counters = new int[size];
        int i = 0;
        while (i < size) {
            b = this.basicBlocks[i];
            b.index = i;
            b.entrances = new Block[b.incomings()];
            counters[i] = 0;
            ++i;
        }
        i = 0;
        while (i < size) {
            b = this.basicBlocks[i];
            int k = 0;
            while (k < b.exits()) {
                Block e = b.exit(k);
                int n = e.index;
                int n2 = counters[n];
                counters[n] = n2 + 1;
                e.entrances[n2] = b;
                ++k;
            }
            Catcher[] catchers = b.catchers();
            int k2 = 0;
            while (k2 < catchers.length) {
                Block catchBlock = catchers[k2].node;
                int n = catchBlock.index;
                int n3 = counters[n];
                counters[n] = n3 + 1;
                catchBlock.entrances[n3] = b;
                ++k2;
            }
            ++i;
        }
    }

    public Block[] basicBlocks() {
        return this.basicBlocks;
    }

    public Frame frameAt(int pos) throws BadBytecode {
        if (this.frames == null) {
            this.frames = new Analyzer().analyze(this.clazz, this.methodInfo);
        }
        return this.frames[pos];
    }

    public Node[] dominatorTree() {
        int size = this.basicBlocks.length;
        if (size == 0) {
            return null;
        }
        Node[] nodes = new Node[size];
        boolean[] visited = new boolean[size];
        int[] distance = new int[size];
        int i = 0;
        while (i < size) {
            nodes[i] = new Node(this.basicBlocks[i]);
            visited[i] = false;
            ++i;
        }
        Access access = new Access(nodes){

            @Override
            BasicBlock[] exits(Node n) {
                return n.block.getExit();
            }

            @Override
            BasicBlock[] entrances(Node n) {
                return ((Node)n).block.entrances;
            }
        };
        nodes[0].makeDepth1stTree(null, visited, 0, distance, access);
        do {
            int i2 = 0;
            while (i2 < size) {
                visited[i2] = false;
                ++i2;
            }
        } while (nodes[0].makeDominatorTree(visited, distance, access));
        Node.setChildren(nodes);
        return nodes;
    }

    public Node[] postDominatorTree() {
        boolean changed;
        int size = this.basicBlocks.length;
        if (size == 0) {
            return null;
        }
        Node[] nodes = new Node[size];
        boolean[] visited = new boolean[size];
        int[] distance = new int[size];
        int i = 0;
        while (i < size) {
            nodes[i] = new Node(this.basicBlocks[i]);
            visited[i] = false;
            ++i;
        }
        Access access = new Access(nodes){

            @Override
            BasicBlock[] exits(Node n) {
                return ((Node)n).block.entrances;
            }

            @Override
            BasicBlock[] entrances(Node n) {
                return n.block.getExit();
            }
        };
        int counter = 0;
        int i2 = 0;
        while (i2 < size) {
            if (nodes[i2].block.exits() == 0) {
                counter = nodes[i2].makeDepth1stTree(null, visited, counter, distance, access);
            }
            ++i2;
        }
        do {
            int i3 = 0;
            while (i3 < size) {
                visited[i3] = false;
                ++i3;
            }
            changed = false;
            i3 = 0;
            while (i3 < size) {
                if (nodes[i3].block.exits() == 0 && nodes[i3].makeDominatorTree(visited, distance, access)) {
                    changed = true;
                }
                ++i3;
            }
        } while (changed);
        Node.setChildren(nodes);
        return nodes;
    }

    static abstract class Access {
        Node[] all;

        Access(Node[] nodes) {
            this.all = nodes;
        }

        Node node(BasicBlock b) {
            return this.all[((Block)b).index];
        }

        abstract BasicBlock[] exits(Node var1);

        abstract BasicBlock[] entrances(Node var1);
    }

    public static class Block
    extends BasicBlock {
        public Object clientData = null;
        int index;
        MethodInfo method;
        Block[] entrances;

        Block(int pos, MethodInfo minfo) {
            super(pos);
            this.method = minfo;
        }

        @Override
        protected void toString2(StringBuffer sbuf) {
            super.toString2(sbuf);
            sbuf.append(", incoming{");
            int i = 0;
            while (i < this.entrances.length) {
                sbuf.append(this.entrances[i].position).append(", ");
                ++i;
            }
            sbuf.append("}");
        }

        BasicBlock[] getExit() {
            return this.exit;
        }

        public int index() {
            return this.index;
        }

        public int position() {
            return this.position;
        }

        public int length() {
            return this.length;
        }

        public int incomings() {
            return this.incoming;
        }

        public Block incoming(int n) {
            return this.entrances[n];
        }

        public int exits() {
            return this.exit == null ? 0 : this.exit.length;
        }

        public Block exit(int n) {
            return (Block)this.exit[n];
        }

        public Catcher[] catchers() {
            ArrayList<Catcher> catchers = new ArrayList<Catcher>();
            BasicBlock.Catch c = this.toCatch;
            while (c != null) {
                catchers.add(new Catcher(c));
                c = c.next;
            }
            return catchers.toArray(new Catcher[catchers.size()]);
        }
    }

    public static class Catcher {
        private Block node;
        private int typeIndex;

        Catcher(BasicBlock.Catch c) {
            this.node = (Block)c.body;
            this.typeIndex = c.typeIndex;
        }

        public Block block() {
            return this.node;
        }

        public String type() {
            if (this.typeIndex == 0) {
                return "java.lang.Throwable";
            }
            return this.node.method.getConstPool().getClassInfo(this.typeIndex);
        }
    }

    public static class Node {
        private Block block;
        private Node parent;
        private Node[] children;

        Node(Block b) {
            this.block = b;
            this.parent = null;
        }

        public String toString() {
            StringBuffer sbuf = new StringBuffer();
            sbuf.append("Node[pos=").append(this.block().position());
            sbuf.append(", parent=");
            sbuf.append(this.parent == null ? "*" : Integer.toString(this.parent.block().position()));
            sbuf.append(", children{");
            int i = 0;
            while (i < this.children.length) {
                sbuf.append(this.children[i].block().position()).append(", ");
                ++i;
            }
            sbuf.append("}]");
            return sbuf.toString();
        }

        public Block block() {
            return this.block;
        }

        public Node parent() {
            return this.parent;
        }

        public int children() {
            return this.children.length;
        }

        public Node child(int n) {
            return this.children[n];
        }

        int makeDepth1stTree(Node caller, boolean[] visited, int counter, int[] distance, Access access) {
            int index = this.block.index;
            if (visited[index]) {
                return counter;
            }
            visited[index] = true;
            this.parent = caller;
            BasicBlock[] exits = access.exits(this);
            if (exits != null) {
                int i = 0;
                while (i < exits.length) {
                    Node n = access.node(exits[i]);
                    counter = n.makeDepth1stTree(this, visited, counter, distance, access);
                    ++i;
                }
            }
            distance[index] = counter++;
            return counter;
        }

        boolean makeDominatorTree(boolean[] visited, int[] distance, Access access) {
            BasicBlock[] entrances;
            int index = this.block.index;
            if (visited[index]) {
                return false;
            }
            visited[index] = true;
            boolean changed = false;
            BasicBlock[] exits = access.exits(this);
            if (exits != null) {
                int i = 0;
                while (i < exits.length) {
                    Node n = access.node(exits[i]);
                    if (n.makeDominatorTree(visited, distance, access)) {
                        changed = true;
                    }
                    ++i;
                }
            }
            if ((entrances = access.entrances(this)) != null) {
                int i = 0;
                while (i < entrances.length) {
                    Node n;
                    if (this.parent != null && (n = Node.getAncestor(this.parent, access.node(entrances[i]), distance)) != this.parent) {
                        this.parent = n;
                        changed = true;
                    }
                    ++i;
                }
            }
            return changed;
        }

        private static Node getAncestor(Node n1, Node n2, int[] distance) {
            while (n1 != n2) {
                if (distance[n1.block.index] < distance[n2.block.index]) {
                    n1 = n1.parent;
                } else {
                    n2 = n2.parent;
                }
                if (n1 != null && n2 != null) continue;
                return null;
            }
            return n1;
        }

        private static void setChildren(Node[] all) {
            int size = all.length;
            int[] nchildren = new int[size];
            int i = 0;
            while (i < size) {
                nchildren[i] = 0;
                ++i;
            }
            i = 0;
            while (i < size) {
                Node p = all[i].parent;
                if (p != null) {
                    int n = p.block.index;
                    nchildren[n] = nchildren[n] + 1;
                }
                ++i;
            }
            i = 0;
            while (i < size) {
                all[i].children = new Node[nchildren[i]];
                ++i;
            }
            i = 0;
            while (i < size) {
                nchildren[i] = 0;
                ++i;
            }
            i = 0;
            while (i < size) {
                Node n = all[i];
                Node p = n.parent;
                if (p != null) {
                    int n2 = p.block.index;
                    int n3 = nchildren[n2];
                    nchildren[n2] = n3 + 1;
                    p.children[n3] = n;
                }
                ++i;
            }
        }
    }
}

