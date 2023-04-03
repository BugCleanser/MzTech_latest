/*
 * Decompiled with CFR 0.152.
 */
package org.objectweb.asm.tree;

import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;

final class InsnList$InsnListIterator
implements ListIterator {
    AbstractInsnNode next;
    AbstractInsnNode prev;
    AbstractInsnNode remove;
    final /* synthetic */ InsnList this$0;

    InsnList$InsnListIterator(InsnList insnList, int n) {
        this.this$0 = insnList;
        if (n == insnList.size()) {
            this.next = null;
            this.prev = insnList.getLast();
        } else {
            this.next = insnList.get(n);
            this.prev = this.next.prev;
        }
    }

    public boolean hasNext() {
        return this.next != null;
    }

    public Object next() {
        AbstractInsnNode abstractInsnNode;
        if (this.next == null) {
            throw new NoSuchElementException();
        }
        this.prev = abstractInsnNode = this.next;
        this.next = abstractInsnNode.next;
        this.remove = abstractInsnNode;
        return abstractInsnNode;
    }

    public void remove() {
        if (this.remove != null) {
            if (this.remove == this.next) {
                this.next = this.next.next;
            } else {
                this.prev = this.prev.prev;
            }
        } else {
            throw new IllegalStateException();
        }
        this.this$0.remove(this.remove);
        this.remove = null;
    }

    public boolean hasPrevious() {
        return this.prev != null;
    }

    public Object previous() {
        AbstractInsnNode abstractInsnNode;
        this.next = abstractInsnNode = this.prev;
        this.prev = abstractInsnNode.prev;
        this.remove = abstractInsnNode;
        return abstractInsnNode;
    }

    public int nextIndex() {
        if (this.next == null) {
            return this.this$0.size();
        }
        if (this.this$0.cache == null) {
            this.this$0.cache = this.this$0.toArray();
        }
        return this.next.index;
    }

    public int previousIndex() {
        if (this.prev == null) {
            return -1;
        }
        if (this.this$0.cache == null) {
            this.this$0.cache = this.this$0.toArray();
        }
        return this.prev.index;
    }

    public void add(Object object) {
        if (this.next != null) {
            this.this$0.insertBefore(this.next, (AbstractInsnNode)object);
        } else if (this.prev != null) {
            this.this$0.insert(this.prev, (AbstractInsnNode)object);
        } else {
            this.this$0.add((AbstractInsnNode)object);
        }
        this.prev = (AbstractInsnNode)object;
        this.remove = null;
    }

    public void set(Object object) {
        if (this.remove != null) {
            this.this$0.set(this.remove, (AbstractInsnNode)object);
            if (this.remove == this.prev) {
                this.prev = (AbstractInsnNode)object;
            } else {
                this.next = (AbstractInsnNode)object;
            }
        } else {
            throw new IllegalStateException();
        }
    }
}

