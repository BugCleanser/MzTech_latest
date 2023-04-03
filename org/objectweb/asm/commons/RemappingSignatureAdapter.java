/*
 * Decompiled with CFR 0.152.
 */
package org.objectweb.asm.commons;

import org.objectweb.asm.commons.Remapper;
import org.objectweb.asm.signature.SignatureVisitor;

public class RemappingSignatureAdapter
extends SignatureVisitor {
    private final SignatureVisitor v;
    private final Remapper remapper;
    private String className;

    public RemappingSignatureAdapter(SignatureVisitor signatureVisitor, Remapper remapper) {
        this(327680, signatureVisitor, remapper);
    }

    protected RemappingSignatureAdapter(int n, SignatureVisitor signatureVisitor, Remapper remapper) {
        super(n);
        this.v = signatureVisitor;
        this.remapper = remapper;
    }

    public void visitClassType(String string) {
        this.className = string;
        this.v.visitClassType(this.remapper.mapType(string));
    }

    public void visitInnerClassType(String string) {
        String string2 = this.remapper.mapType(this.className) + '$';
        this.className = this.className + '$' + string;
        String string3 = this.remapper.mapType(this.className);
        int n = string3.startsWith(string2) ? string2.length() : string3.lastIndexOf(36) + 1;
        this.v.visitInnerClassType(string3.substring(n));
    }

    public void visitFormalTypeParameter(String string) {
        this.v.visitFormalTypeParameter(string);
    }

    public void visitTypeVariable(String string) {
        this.v.visitTypeVariable(string);
    }

    public SignatureVisitor visitArrayType() {
        this.v.visitArrayType();
        return this;
    }

    public void visitBaseType(char c) {
        this.v.visitBaseType(c);
    }

    public SignatureVisitor visitClassBound() {
        this.v.visitClassBound();
        return this;
    }

    public SignatureVisitor visitExceptionType() {
        this.v.visitExceptionType();
        return this;
    }

    public SignatureVisitor visitInterface() {
        this.v.visitInterface();
        return this;
    }

    public SignatureVisitor visitInterfaceBound() {
        this.v.visitInterfaceBound();
        return this;
    }

    public SignatureVisitor visitParameterType() {
        this.v.visitParameterType();
        return this;
    }

    public SignatureVisitor visitReturnType() {
        this.v.visitReturnType();
        return this;
    }

    public SignatureVisitor visitSuperclass() {
        this.v.visitSuperclass();
        return this;
    }

    public void visitTypeArgument() {
        this.v.visitTypeArgument();
    }

    public SignatureVisitor visitTypeArgument(char c) {
        this.v.visitTypeArgument(c);
        return this;
    }

    public void visitEnd() {
        this.v.visitEnd();
    }
}

