/*
 * Decompiled with CFR 0.152.
 */
package javassist;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Comparator;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.ClassFile;
import javassist.bytecode.Descriptor;

public class SerialVersionUID {
    public static void setSerialVersionUID(CtClass clazz) throws CannotCompileException, NotFoundException {
        try {
            clazz.getDeclaredField("serialVersionUID");
            return;
        }
        catch (NotFoundException notFoundException) {
            if (!SerialVersionUID.isSerializable(clazz)) {
                return;
            }
            CtField field = new CtField(CtClass.longType, "serialVersionUID", clazz);
            field.setModifiers(26);
            clazz.addField(field, String.valueOf(SerialVersionUID.calculateDefault(clazz)) + "L");
            return;
        }
    }

    private static boolean isSerializable(CtClass clazz) throws NotFoundException {
        ClassPool pool = clazz.getClassPool();
        return clazz.subtypeOf(pool.get("java.io.Serializable"));
    }

    public static long calculateDefault(CtClass clazz) throws CannotCompileException {
        try {
            int mods;
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(bout);
            ClassFile classFile = clazz.getClassFile();
            String javaName = SerialVersionUID.javaName(clazz);
            out.writeUTF(javaName);
            CtMethod[] methods = clazz.getDeclaredMethods();
            int classMods = clazz.getModifiers();
            if ((classMods & 0x200) != 0) {
                classMods = methods.length > 0 ? (classMods |= 0x400) : (classMods &= 0xFFFFFBFF);
            }
            out.writeInt(classMods);
            Object[] interfaces = classFile.getInterfaces();
            int i = 0;
            while (i < interfaces.length) {
                interfaces[i] = SerialVersionUID.javaName((String)interfaces[i]);
                ++i;
            }
            Arrays.sort(interfaces);
            i = 0;
            while (i < interfaces.length) {
                out.writeUTF((String)interfaces[i]);
                ++i;
            }
            CtField[] fields = clazz.getDeclaredFields();
            Arrays.sort(fields, new Comparator<CtField>(){

                @Override
                public int compare(CtField field1, CtField field2) {
                    return field1.getName().compareTo(field2.getName());
                }
            });
            int i2 = 0;
            while (i2 < fields.length) {
                CtField field = fields[i2];
                int mods2 = field.getModifiers();
                if ((mods2 & 2) == 0 || (mods2 & 0x88) == 0) {
                    out.writeUTF(field.getName());
                    out.writeInt(mods2);
                    out.writeUTF(field.getFieldInfo2().getDescriptor());
                }
                ++i2;
            }
            if (classFile.getStaticInitializer() != null) {
                out.writeUTF("<clinit>");
                out.writeInt(8);
                out.writeUTF("()V");
            }
            CtConstructor[] constructors = clazz.getDeclaredConstructors();
            Arrays.sort(constructors, new Comparator<CtConstructor>(){

                @Override
                public int compare(CtConstructor c1, CtConstructor c2) {
                    return c1.getMethodInfo2().getDescriptor().compareTo(c2.getMethodInfo2().getDescriptor());
                }
            });
            int i3 = 0;
            while (i3 < constructors.length) {
                CtConstructor constructor = constructors[i3];
                mods = constructor.getModifiers();
                if ((mods & 2) == 0) {
                    out.writeUTF("<init>");
                    out.writeInt(mods);
                    out.writeUTF(constructor.getMethodInfo2().getDescriptor().replace('/', '.'));
                }
                ++i3;
            }
            Arrays.sort(methods, new Comparator<CtMethod>(){

                @Override
                public int compare(CtMethod m1, CtMethod m2) {
                    int value = m1.getName().compareTo(m2.getName());
                    if (value == 0) {
                        value = m1.getMethodInfo2().getDescriptor().compareTo(m2.getMethodInfo2().getDescriptor());
                    }
                    return value;
                }
            });
            i3 = 0;
            while (i3 < methods.length) {
                CtMethod method = methods[i3];
                mods = method.getModifiers() & 0xD3F;
                if ((mods & 2) == 0) {
                    out.writeUTF(method.getName());
                    out.writeInt(mods);
                    out.writeUTF(method.getMethodInfo2().getDescriptor().replace('/', '.'));
                }
                ++i3;
            }
            out.flush();
            MessageDigest digest = MessageDigest.getInstance("SHA");
            byte[] digested = digest.digest(bout.toByteArray());
            long hash = 0L;
            int i4 = Math.min(digested.length, 8) - 1;
            while (i4 >= 0) {
                hash = hash << 8 | (long)(digested[i4] & 0xFF);
                --i4;
            }
            return hash;
        }
        catch (IOException e) {
            throw new CannotCompileException(e);
        }
        catch (NoSuchAlgorithmException e) {
            throw new CannotCompileException(e);
        }
    }

    private static String javaName(CtClass clazz) {
        return Descriptor.toJavaName(Descriptor.toJvmName(clazz));
    }

    private static String javaName(String name) {
        return Descriptor.toJavaName(Descriptor.toJvmName(name));
    }
}

