/*
 * Decompiled with CFR 0.152.
 */
package org.objectweb.asm.commons;

import org.objectweb.asm.Handle;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.SignatureRemapper;
import org.objectweb.asm.signature.SignatureReader;
import org.objectweb.asm.signature.SignatureVisitor;
import org.objectweb.asm.signature.SignatureWriter;

public abstract class Remapper {
    public String mapDesc(String string) {
        Type type = Type.getType(string);
        switch (type.getSort()) {
            case 9: {
                String string2 = this.mapDesc(type.getElementType().getDescriptor());
                for (int i = 0; i < type.getDimensions(); ++i) {
                    string2 = '[' + string2;
                }
                return string2;
            }
            case 10: {
                String string3 = this.map(type.getInternalName());
                if (string3 == null) break;
                return 'L' + string3 + ';';
            }
        }
        return string;
    }

    private Type mapType(Type type) {
        switch (type.getSort()) {
            case 9: {
                String string = this.mapDesc(type.getElementType().getDescriptor());
                for (int i = 0; i < type.getDimensions(); ++i) {
                    string = '[' + string;
                }
                return Type.getType(string);
            }
            case 10: {
                String string = this.map(type.getInternalName());
                return string != null ? Type.getObjectType(string) : type;
            }
            case 11: {
                return Type.getMethodType(this.mapMethodDesc(type.getDescriptor()));
            }
        }
        return type;
    }

    public String mapType(String string) {
        if (string == null) {
            return null;
        }
        return this.mapType(Type.getObjectType(string)).getInternalName();
    }

    public String[] mapTypes(String[] stringArray) {
        String[] stringArray2 = null;
        boolean bl = false;
        for (int i = 0; i < stringArray.length; ++i) {
            String string = stringArray[i];
            String string2 = this.map(string);
            if (string2 != null && stringArray2 == null) {
                stringArray2 = new String[stringArray.length];
                if (i > 0) {
                    System.arraycopy(stringArray, 0, stringArray2, 0, i);
                }
                bl = true;
            }
            if (!bl) continue;
            stringArray2[i] = string2 == null ? string : string2;
        }
        return bl ? stringArray2 : stringArray;
    }

    public String mapMethodDesc(String string) {
        if ("()V".equals(string)) {
            return string;
        }
        Type[] typeArray = Type.getArgumentTypes(string);
        StringBuffer stringBuffer = new StringBuffer("(");
        for (int i = 0; i < typeArray.length; ++i) {
            stringBuffer.append(this.mapDesc(typeArray[i].getDescriptor()));
        }
        Type type = Type.getReturnType(string);
        if (type == Type.VOID_TYPE) {
            stringBuffer.append(")V");
            return stringBuffer.toString();
        }
        stringBuffer.append(')').append(this.mapDesc(type.getDescriptor()));
        return stringBuffer.toString();
    }

    public Object mapValue(Object object) {
        if (object instanceof Type) {
            return this.mapType((Type)object);
        }
        if (object instanceof Handle) {
            Handle handle = (Handle)object;
            return new Handle(handle.getTag(), this.mapType(handle.getOwner()), this.mapMethodName(handle.getOwner(), handle.getName(), handle.getDesc()), this.mapMethodDesc(handle.getDesc()), handle.isInterface());
        }
        return object;
    }

    public String mapSignature(String string, boolean bl) {
        if (string == null) {
            return null;
        }
        SignatureReader signatureReader = new SignatureReader(string);
        SignatureWriter signatureWriter = new SignatureWriter();
        SignatureVisitor signatureVisitor = this.createSignatureRemapper(signatureWriter);
        if (bl) {
            signatureReader.acceptType(signatureVisitor);
        } else {
            signatureReader.accept(signatureVisitor);
        }
        return signatureWriter.toString();
    }

    protected SignatureVisitor createRemappingSignatureAdapter(SignatureVisitor signatureVisitor) {
        return new SignatureRemapper(signatureVisitor, this);
    }

    protected SignatureVisitor createSignatureRemapper(SignatureVisitor signatureVisitor) {
        return this.createRemappingSignatureAdapter(signatureVisitor);
    }

    public String mapMethodName(String string, String string2, String string3) {
        return string2;
    }

    public String mapInvokeDynamicMethodName(String string, String string2) {
        return string;
    }

    public String mapFieldName(String string, String string2, String string3) {
        return string2;
    }

    public String map(String string) {
        return string;
    }
}

