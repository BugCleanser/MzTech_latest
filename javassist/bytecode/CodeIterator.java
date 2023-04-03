/*
 * Decompiled with CFR 0.152.
 */
package javassist.bytecode;

import java.util.ArrayList;
import java.util.List;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.ByteArray;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.ExceptionTable;
import javassist.bytecode.LineNumberAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.Opcode;
import javassist.bytecode.StackMap;
import javassist.bytecode.StackMapTable;

public class CodeIterator
implements Opcode {
    protected CodeAttribute codeAttr;
    protected byte[] bytecode;
    protected int endPos;
    protected int currentPos;
    protected int mark;
    protected int mark2;
    private static final int[] opcodeLength;

    static {
        int[] nArray = new int[202];
        nArray[0] = 1;
        nArray[1] = 1;
        nArray[2] = 1;
        nArray[3] = 1;
        nArray[4] = 1;
        nArray[5] = 1;
        nArray[6] = 1;
        nArray[7] = 1;
        nArray[8] = 1;
        nArray[9] = 1;
        nArray[10] = 1;
        nArray[11] = 1;
        nArray[12] = 1;
        nArray[13] = 1;
        nArray[14] = 1;
        nArray[15] = 1;
        nArray[16] = 2;
        nArray[17] = 3;
        nArray[18] = 2;
        nArray[19] = 3;
        nArray[20] = 3;
        nArray[21] = 2;
        nArray[22] = 2;
        nArray[23] = 2;
        nArray[24] = 2;
        nArray[25] = 2;
        nArray[26] = 1;
        nArray[27] = 1;
        nArray[28] = 1;
        nArray[29] = 1;
        nArray[30] = 1;
        nArray[31] = 1;
        nArray[32] = 1;
        nArray[33] = 1;
        nArray[34] = 1;
        nArray[35] = 1;
        nArray[36] = 1;
        nArray[37] = 1;
        nArray[38] = 1;
        nArray[39] = 1;
        nArray[40] = 1;
        nArray[41] = 1;
        nArray[42] = 1;
        nArray[43] = 1;
        nArray[44] = 1;
        nArray[45] = 1;
        nArray[46] = 1;
        nArray[47] = 1;
        nArray[48] = 1;
        nArray[49] = 1;
        nArray[50] = 1;
        nArray[51] = 1;
        nArray[52] = 1;
        nArray[53] = 1;
        nArray[54] = 2;
        nArray[55] = 2;
        nArray[56] = 2;
        nArray[57] = 2;
        nArray[58] = 2;
        nArray[59] = 1;
        nArray[60] = 1;
        nArray[61] = 1;
        nArray[62] = 1;
        nArray[63] = 1;
        nArray[64] = 1;
        nArray[65] = 1;
        nArray[66] = 1;
        nArray[67] = 1;
        nArray[68] = 1;
        nArray[69] = 1;
        nArray[70] = 1;
        nArray[71] = 1;
        nArray[72] = 1;
        nArray[73] = 1;
        nArray[74] = 1;
        nArray[75] = 1;
        nArray[76] = 1;
        nArray[77] = 1;
        nArray[78] = 1;
        nArray[79] = 1;
        nArray[80] = 1;
        nArray[81] = 1;
        nArray[82] = 1;
        nArray[83] = 1;
        nArray[84] = 1;
        nArray[85] = 1;
        nArray[86] = 1;
        nArray[87] = 1;
        nArray[88] = 1;
        nArray[89] = 1;
        nArray[90] = 1;
        nArray[91] = 1;
        nArray[92] = 1;
        nArray[93] = 1;
        nArray[94] = 1;
        nArray[95] = 1;
        nArray[96] = 1;
        nArray[97] = 1;
        nArray[98] = 1;
        nArray[99] = 1;
        nArray[100] = 1;
        nArray[101] = 1;
        nArray[102] = 1;
        nArray[103] = 1;
        nArray[104] = 1;
        nArray[105] = 1;
        nArray[106] = 1;
        nArray[107] = 1;
        nArray[108] = 1;
        nArray[109] = 1;
        nArray[110] = 1;
        nArray[111] = 1;
        nArray[112] = 1;
        nArray[113] = 1;
        nArray[114] = 1;
        nArray[115] = 1;
        nArray[116] = 1;
        nArray[117] = 1;
        nArray[118] = 1;
        nArray[119] = 1;
        nArray[120] = 1;
        nArray[121] = 1;
        nArray[122] = 1;
        nArray[123] = 1;
        nArray[124] = 1;
        nArray[125] = 1;
        nArray[126] = 1;
        nArray[127] = 1;
        nArray[128] = 1;
        nArray[129] = 1;
        nArray[130] = 1;
        nArray[131] = 1;
        nArray[132] = 3;
        nArray[133] = 1;
        nArray[134] = 1;
        nArray[135] = 1;
        nArray[136] = 1;
        nArray[137] = 1;
        nArray[138] = 1;
        nArray[139] = 1;
        nArray[140] = 1;
        nArray[141] = 1;
        nArray[142] = 1;
        nArray[143] = 1;
        nArray[144] = 1;
        nArray[145] = 1;
        nArray[146] = 1;
        nArray[147] = 1;
        nArray[148] = 1;
        nArray[149] = 1;
        nArray[150] = 1;
        nArray[151] = 1;
        nArray[152] = 1;
        nArray[153] = 3;
        nArray[154] = 3;
        nArray[155] = 3;
        nArray[156] = 3;
        nArray[157] = 3;
        nArray[158] = 3;
        nArray[159] = 3;
        nArray[160] = 3;
        nArray[161] = 3;
        nArray[162] = 3;
        nArray[163] = 3;
        nArray[164] = 3;
        nArray[165] = 3;
        nArray[166] = 3;
        nArray[167] = 3;
        nArray[168] = 3;
        nArray[169] = 2;
        nArray[172] = 1;
        nArray[173] = 1;
        nArray[174] = 1;
        nArray[175] = 1;
        nArray[176] = 1;
        nArray[177] = 1;
        nArray[178] = 3;
        nArray[179] = 3;
        nArray[180] = 3;
        nArray[181] = 3;
        nArray[182] = 3;
        nArray[183] = 3;
        nArray[184] = 3;
        nArray[185] = 5;
        nArray[186] = 5;
        nArray[187] = 3;
        nArray[188] = 2;
        nArray[189] = 3;
        nArray[190] = 1;
        nArray[191] = 1;
        nArray[192] = 3;
        nArray[193] = 3;
        nArray[194] = 1;
        nArray[195] = 1;
        nArray[197] = 4;
        nArray[198] = 3;
        nArray[199] = 3;
        nArray[200] = 5;
        nArray[201] = 5;
        opcodeLength = nArray;
    }

    protected CodeIterator(CodeAttribute ca) {
        this.codeAttr = ca;
        this.bytecode = ca.getCode();
        this.begin();
    }

    public void begin() {
        this.mark2 = 0;
        this.mark = 0;
        this.currentPos = 0;
        this.endPos = this.getCodeLength();
    }

    public void move(int index) {
        this.currentPos = index;
    }

    public void setMark(int index) {
        this.mark = index;
    }

    public void setMark2(int index) {
        this.mark2 = index;
    }

    public int getMark() {
        return this.mark;
    }

    public int getMark2() {
        return this.mark2;
    }

    public CodeAttribute get() {
        return this.codeAttr;
    }

    public int getCodeLength() {
        return this.bytecode.length;
    }

    public int byteAt(int index) {
        return this.bytecode[index] & 0xFF;
    }

    public int signedByteAt(int index) {
        return this.bytecode[index];
    }

    public void writeByte(int value, int index) {
        this.bytecode[index] = (byte)value;
    }

    public int u16bitAt(int index) {
        return ByteArray.readU16bit(this.bytecode, index);
    }

    public int s16bitAt(int index) {
        return ByteArray.readS16bit(this.bytecode, index);
    }

    public void write16bit(int value, int index) {
        ByteArray.write16bit(value, this.bytecode, index);
    }

    public int s32bitAt(int index) {
        return ByteArray.read32bit(this.bytecode, index);
    }

    public void write32bit(int value, int index) {
        ByteArray.write32bit(value, this.bytecode, index);
    }

    public void write(byte[] code, int index) {
        int len = code.length;
        int j = 0;
        while (j < len) {
            this.bytecode[index++] = code[j];
            ++j;
        }
    }

    public boolean hasNext() {
        return this.currentPos < this.endPos;
    }

    public int next() throws BadBytecode {
        int pos = this.currentPos;
        this.currentPos = CodeIterator.nextOpcode(this.bytecode, pos);
        return pos;
    }

    public int lookAhead() {
        return this.currentPos;
    }

    public int skipConstructor() throws BadBytecode {
        return this.skipSuperConstructor0(-1);
    }

    public int skipSuperConstructor() throws BadBytecode {
        return this.skipSuperConstructor0(0);
    }

    public int skipThisConstructor() throws BadBytecode {
        return this.skipSuperConstructor0(1);
    }

    private int skipSuperConstructor0(int skipThis) throws BadBytecode {
        this.begin();
        ConstPool cp = this.codeAttr.getConstPool();
        String thisClassName = this.codeAttr.getDeclaringClass();
        int nested = 0;
        while (this.hasNext()) {
            int mref;
            int index = this.next();
            int c = this.byteAt(index);
            if (c == 187) {
                ++nested;
                continue;
            }
            if (c != 183 || !cp.getMethodrefName(mref = ByteArray.readU16bit(this.bytecode, index + 1)).equals("<init>") || --nested >= 0) continue;
            if (skipThis < 0) {
                return index;
            }
            String cname = cp.getMethodrefClassName(mref);
            if (cname.equals(thisClassName) != skipThis > 0) break;
            return index;
        }
        this.begin();
        return -1;
    }

    public int insert(byte[] code) throws BadBytecode {
        return this.insert0(this.currentPos, code, false);
    }

    public void insert(int pos, byte[] code) throws BadBytecode {
        this.insert0(pos, code, false);
    }

    public int insertAt(int pos, byte[] code) throws BadBytecode {
        return this.insert0(pos, code, false);
    }

    public int insertEx(byte[] code) throws BadBytecode {
        return this.insert0(this.currentPos, code, true);
    }

    public void insertEx(int pos, byte[] code) throws BadBytecode {
        this.insert0(pos, code, true);
    }

    public int insertExAt(int pos, byte[] code) throws BadBytecode {
        return this.insert0(pos, code, true);
    }

    private int insert0(int pos, byte[] code, boolean exclusive) throws BadBytecode {
        int len = code.length;
        if (len <= 0) {
            return pos;
        }
        int p = pos = this.insertGapAt((int)pos, (int)len, (boolean)exclusive).position;
        int j = 0;
        while (j < len) {
            this.bytecode[p++] = code[j];
            ++j;
        }
        return pos;
    }

    public int insertGap(int length) throws BadBytecode {
        return this.insertGapAt((int)this.currentPos, (int)length, (boolean)false).position;
    }

    public int insertGap(int pos, int length) throws BadBytecode {
        return this.insertGapAt((int)pos, (int)length, (boolean)false).length;
    }

    public int insertExGap(int length) throws BadBytecode {
        return this.insertGapAt((int)this.currentPos, (int)length, (boolean)true).position;
    }

    public int insertExGap(int pos, int length) throws BadBytecode {
        return this.insertGapAt((int)pos, (int)length, (boolean)true).length;
    }

    public Gap insertGapAt(int pos, int length, boolean exclusive) throws BadBytecode {
        int length2;
        byte[] c;
        Gap gap = new Gap();
        if (length <= 0) {
            gap.position = pos;
            gap.length = 0;
            return gap;
        }
        if (this.bytecode.length + length > Short.MAX_VALUE) {
            c = this.insertGapCore0w(this.bytecode, pos, length, exclusive, this.get().getExceptionTable(), this.codeAttr, gap);
            pos = gap.position;
            length2 = length;
        } else {
            int cur = this.currentPos;
            c = CodeIterator.insertGapCore0(this.bytecode, pos, length, exclusive, this.get().getExceptionTable(), this.codeAttr);
            length2 = c.length - this.bytecode.length;
            gap.position = pos;
            gap.length = length2;
            if (cur >= pos) {
                this.currentPos = cur + length2;
            }
            if (this.mark > pos || this.mark == pos && exclusive) {
                this.mark += length2;
            }
            if (this.mark2 > pos || this.mark2 == pos && exclusive) {
                this.mark2 += length2;
            }
        }
        this.codeAttr.setCode(c);
        this.bytecode = c;
        this.endPos = this.getCodeLength();
        this.updateCursors(pos, length2);
        return gap;
    }

    protected void updateCursors(int pos, int length) {
    }

    public void insert(ExceptionTable et, int offset) {
        this.codeAttr.getExceptionTable().add(0, et, offset);
    }

    public int append(byte[] code) {
        int size = this.getCodeLength();
        int len = code.length;
        if (len <= 0) {
            return size;
        }
        this.appendGap(len);
        byte[] dest = this.bytecode;
        int i = 0;
        while (i < len) {
            dest[i + size] = code[i];
            ++i;
        }
        return size;
    }

    public void appendGap(int gapLength) {
        byte[] code = this.bytecode;
        int codeLength = code.length;
        byte[] newcode = new byte[codeLength + gapLength];
        int i = 0;
        while (i < codeLength) {
            newcode[i] = code[i];
            ++i;
        }
        i = codeLength;
        while (i < codeLength + gapLength) {
            newcode[i] = 0;
            ++i;
        }
        this.codeAttr.setCode(newcode);
        this.bytecode = newcode;
        this.endPos = this.getCodeLength();
    }

    public void append(ExceptionTable et, int offset) {
        ExceptionTable table = this.codeAttr.getExceptionTable();
        table.add(table.size(), et, offset);
    }

    static int nextOpcode(byte[] code, int index) throws BadBytecode {
        int opcode;
        try {
            opcode = code[index] & 0xFF;
        }
        catch (IndexOutOfBoundsException e) {
            throw new BadBytecode("invalid opcode address");
        }
        try {
            int len = opcodeLength[opcode];
            if (len > 0) {
                return index + len;
            }
            if (opcode == 196) {
                if (code[index + 1] == -124) {
                    return index + 6;
                }
                return index + 4;
            }
            int index2 = (index & 0xFFFFFFFC) + 8;
            if (opcode == 171) {
                int npairs = ByteArray.read32bit(code, index2);
                return index2 + npairs * 8 + 4;
            }
            if (opcode == 170) {
                int low = ByteArray.read32bit(code, index2);
                int high = ByteArray.read32bit(code, index2 + 4);
                return index2 + (high - low + 1) * 4 + 8;
            }
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            // empty catch block
        }
        throw new BadBytecode(opcode);
    }

    static byte[] insertGapCore0(byte[] code, int where, int gapLength, boolean exclusive, ExceptionTable etable, CodeAttribute ca) throws BadBytecode {
        if (gapLength <= 0) {
            return code;
        }
        try {
            return CodeIterator.insertGapCore1(code, where, gapLength, exclusive, etable, ca);
        }
        catch (AlignmentException e) {
            try {
                return CodeIterator.insertGapCore1(code, where, gapLength + 3 & 0xFFFFFFFC, exclusive, etable, ca);
            }
            catch (AlignmentException e2) {
                throw new RuntimeException("fatal error?");
            }
        }
    }

    private static byte[] insertGapCore1(byte[] code, int where, int gapLength, boolean exclusive, ExceptionTable etable, CodeAttribute ca) throws BadBytecode, AlignmentException {
        StackMap sm;
        StackMapTable smt;
        LocalVariableAttribute vta;
        LocalVariableAttribute va;
        int codeLength = code.length;
        byte[] newcode = new byte[codeLength + gapLength];
        CodeIterator.insertGap2(code, where, gapLength, codeLength, newcode, exclusive);
        etable.shiftPc(where, gapLength, exclusive);
        LineNumberAttribute na = (LineNumberAttribute)ca.getAttribute("LineNumberTable");
        if (na != null) {
            na.shiftPc(where, gapLength, exclusive);
        }
        if ((va = (LocalVariableAttribute)ca.getAttribute("LocalVariableTable")) != null) {
            va.shiftPc(where, gapLength, exclusive);
        }
        if ((vta = (LocalVariableAttribute)ca.getAttribute("LocalVariableTypeTable")) != null) {
            vta.shiftPc(where, gapLength, exclusive);
        }
        if ((smt = (StackMapTable)ca.getAttribute("StackMapTable")) != null) {
            smt.shiftPc(where, gapLength, exclusive);
        }
        if ((sm = (StackMap)ca.getAttribute("StackMap")) != null) {
            sm.shiftPc(where, gapLength, exclusive);
        }
        return newcode;
    }

    /*
     * Unable to fully structure code
     */
    private static void insertGap2(byte[] code, int where, int gapLength, int endPos, byte[] newcode, boolean exclusive) throws BadBytecode, AlignmentException {
        i = 0;
        j = 0;
        while (i < endPos) {
            block9: {
                block11: {
                    block10: {
                        block8: {
                            if (i == where) {
                                j2 = j + gapLength;
                                while (j < j2) {
                                    newcode[j++] = 0;
                                }
                            }
                            nextPos = CodeIterator.nextOpcode(code, i);
                            inst = code[i] & 255;
                            if ((153 > inst || inst > 168) && inst != 198 && inst != 199) break block8;
                            offset = code[i + 1] << 8 | code[i + 2] & 255;
                            offset = CodeIterator.newOffset(i, offset, where, gapLength, exclusive);
                            newcode[j] = code[i];
                            ByteArray.write16bit(offset, newcode, j + 1);
                            j += 3;
                            break block9;
                        }
                        if (inst != 200 && inst != 201) break block10;
                        offset = ByteArray.read32bit(code, i + 1);
                        offset = CodeIterator.newOffset(i, offset, where, gapLength, exclusive);
                        newcode[j++] = code[i];
                        ByteArray.write32bit(offset, newcode, j);
                        j += 4;
                        break block9;
                    }
                    if (inst != 170) break block11;
                    if (i != j && (gapLength & 3) != 0) {
                        throw new AlignmentException();
                    }
                    i2 = (i & -4) + 4;
                    j = CodeIterator.copyGapBytes(newcode, j, code, i, i2);
                    defaultbyte = CodeIterator.newOffset(i, ByteArray.read32bit(code, i2), where, gapLength, exclusive);
                    ByteArray.write32bit(defaultbyte, newcode, j);
                    lowbyte = ByteArray.read32bit(code, i2 + 4);
                    ByteArray.write32bit(lowbyte, newcode, j + 4);
                    highbyte = ByteArray.read32bit(code, i2 + 8);
                    ByteArray.write32bit(highbyte, newcode, j + 8);
                    j += 12;
                    i0 = i2 + 12;
                    i2 = i0 + (highbyte - lowbyte + 1) * 4;
                    while (i0 < i2) {
                        offset = CodeIterator.newOffset(i, ByteArray.read32bit(code, i0), where, gapLength, exclusive);
                        ByteArray.write32bit(offset, newcode, j);
                        j += 4;
                        i0 += 4;
                    }
                    break block9;
                }
                if (inst != 171) ** GOTO lbl70
                if (i != j && (gapLength & 3) != 0) {
                    throw new AlignmentException();
                }
                i2 = (i & -4) + 4;
                j = CodeIterator.copyGapBytes(newcode, j, code, i, i2);
                defaultbyte = CodeIterator.newOffset(i, ByteArray.read32bit(code, i2), where, gapLength, exclusive);
                ByteArray.write32bit(defaultbyte, newcode, j);
                npairs = ByteArray.read32bit(code, i2 + 4);
                ByteArray.write32bit(npairs, newcode, j + 4);
                j += 8;
                i0 = i2 + 8;
                i2 = i0 + npairs * 8;
                while (i0 < i2) {
                    ByteArray.copy32bit(code, i0, newcode, j);
                    offset = CodeIterator.newOffset(i, ByteArray.read32bit(code, i0 + 4), where, gapLength, exclusive);
                    ByteArray.write32bit(offset, newcode, j + 4);
                    j += 8;
                    i0 += 8;
                }
                break block9;
lbl-1000:
                // 1 sources

                {
                    newcode[j++] = code[i++];
lbl70:
                    // 2 sources

                    ** while (i < nextPos)
                }
            }
            i = nextPos;
        }
    }

    private static int copyGapBytes(byte[] newcode, int j, byte[] code, int i, int iEnd) {
        switch (iEnd - i) {
            case 4: {
                newcode[j++] = code[i++];
            }
            case 3: {
                newcode[j++] = code[i++];
            }
            case 2: {
                newcode[j++] = code[i++];
            }
            case 1: {
                newcode[j++] = code[i++];
            }
        }
        return j;
    }

    private static int newOffset(int i, int offset, int where, int gapLength, boolean exclusive) {
        int target = i + offset;
        if (i < where) {
            if (where < target || exclusive && where == target) {
                offset += gapLength;
            }
        } else if (i == where) {
            if (target < where) {
                offset -= gapLength;
            }
        } else if (target < where || !exclusive && where == target) {
            offset -= gapLength;
        }
        return offset;
    }

    static byte[] changeLdcToLdcW(byte[] code, ExceptionTable etable, CodeAttribute ca, CodeAttribute.LdcEntry ldcs) throws BadBytecode {
        Pointers pointers = new Pointers(0, 0, 0, 0, etable, ca);
        List<Branch> jumps = CodeIterator.makeJumpList(code, code.length, pointers);
        while (ldcs != null) {
            CodeIterator.addLdcW(ldcs, jumps);
            ldcs = ldcs.next;
        }
        byte[] r = CodeIterator.insertGap2w(code, 0, 0, false, jumps, pointers);
        return r;
    }

    private static void addLdcW(CodeAttribute.LdcEntry ldcs, List<Branch> jumps) {
        int where = ldcs.where;
        LdcW ldcw = new LdcW(where, ldcs.index);
        int s = jumps.size();
        int i = 0;
        while (i < s) {
            if (where < jumps.get((int)i).orgPos) {
                jumps.add(i, ldcw);
                return;
            }
            ++i;
        }
        jumps.add(ldcw);
    }

    private byte[] insertGapCore0w(byte[] code, int where, int gapLength, boolean exclusive, ExceptionTable etable, CodeAttribute ca, Gap newWhere) throws BadBytecode {
        if (gapLength <= 0) {
            return code;
        }
        Pointers pointers = new Pointers(this.currentPos, this.mark, this.mark2, where, etable, ca);
        List<Branch> jumps = CodeIterator.makeJumpList(code, code.length, pointers);
        byte[] r = CodeIterator.insertGap2w(code, where, gapLength, exclusive, jumps, pointers);
        this.currentPos = pointers.cursor;
        this.mark = pointers.mark;
        this.mark2 = pointers.mark2;
        int where2 = pointers.mark0;
        if (where2 == this.currentPos && !exclusive) {
            this.currentPos += gapLength;
        }
        if (exclusive) {
            where2 -= gapLength;
        }
        newWhere.position = where2;
        newWhere.length = gapLength;
        return r;
    }

    /*
     * Unable to fully structure code
     */
    private static byte[] insertGap2w(byte[] code, int where, int gapLength, boolean exclusive, List<Branch> jumps, Pointers ptrs) throws BadBytecode {
        if (gapLength > 0) {
            ptrs.shiftPc(where, gapLength, exclusive);
            for (Branch b : jumps) {
                b.shift(where, gapLength, exclusive);
            }
        }
        unstable = true;
        block1: while (true) {
            if (unstable) {
                unstable = false;
                var8_9 = jumps.iterator();
                block2: while (true) {
                    if (!var8_9.hasNext()) continue block1;
                    b = var8_9.next();
                    if (!b.expanded()) continue;
                    unstable = true;
                    p = b.pos;
                    delta = b.deltaSize();
                    ptrs.shiftPc(p, delta, false);
                    var12_13 = jumps.iterator();
                    while (true) {
                        if (var12_13.hasNext()) ** break;
                        continue block2;
                        bb = var12_13.next();
                        bb.shift(p, delta, false);
                    }
                    break;
                }
            }
            for (Branch b : jumps) {
                diff = b.gapChanged();
                if (diff <= 0) continue;
                unstable = true;
                p = b.pos;
                ptrs.shiftPc(p, diff, false);
                for (Branch bb : jumps) {
                    bb.shift(p, diff, false);
                }
            }
            if (!unstable) break;
        }
        return CodeIterator.makeExapndedCode(code, jumps, where, gapLength);
    }

    private static List<Branch> makeJumpList(byte[] code, int endPos, Pointers ptrs) throws BadBytecode {
        ArrayList<Branch> jumps = new ArrayList<Branch>();
        int i = 0;
        while (i < endPos) {
            int i2;
            int offset;
            int nextPos = CodeIterator.nextOpcode(code, i);
            int inst = code[i] & 0xFF;
            if (153 <= inst && inst <= 168 || inst == 198 || inst == 199) {
                offset = code[i + 1] << 8 | code[i + 2] & 0xFF;
                Branch16 b = inst == 167 || inst == 168 ? new Jump16(i, offset) : new If16(i, offset);
                jumps.add(b);
            } else if (inst == 200 || inst == 201) {
                offset = ByteArray.read32bit(code, i + 1);
                jumps.add(new Jump32(i, offset));
            } else if (inst == 170) {
                i2 = (i & 0xFFFFFFFC) + 4;
                int defaultbyte = ByteArray.read32bit(code, i2);
                int lowbyte = ByteArray.read32bit(code, i2 + 4);
                int highbyte = ByteArray.read32bit(code, i2 + 8);
                int i0 = i2 + 12;
                int size = highbyte - lowbyte + 1;
                int[] offsets = new int[size];
                int j = 0;
                while (j < size) {
                    offsets[j] = ByteArray.read32bit(code, i0);
                    i0 += 4;
                    ++j;
                }
                jumps.add(new Table(i, defaultbyte, lowbyte, highbyte, offsets, ptrs));
            } else if (inst == 171) {
                i2 = (i & 0xFFFFFFFC) + 4;
                int defaultbyte = ByteArray.read32bit(code, i2);
                int npairs = ByteArray.read32bit(code, i2 + 4);
                int i0 = i2 + 8;
                int[] matches = new int[npairs];
                int[] offsets = new int[npairs];
                int j = 0;
                while (j < npairs) {
                    matches[j] = ByteArray.read32bit(code, i0);
                    offsets[j] = ByteArray.read32bit(code, i0 + 4);
                    i0 += 8;
                    ++j;
                }
                jumps.add(new Lookup(i, defaultbyte, matches, offsets, ptrs));
            }
            i = nextPos;
        }
        return jumps;
    }

    private static byte[] makeExapndedCode(byte[] code, List<Branch> jumps, int where, int gapLength) throws BadBytecode {
        int bpos;
        Branch b;
        int n = jumps.size();
        int size = code.length + gapLength;
        for (Branch b2 : jumps) {
            size += b2.deltaSize();
        }
        byte[] newcode = new byte[size];
        int src = 0;
        int dest = 0;
        int bindex = 0;
        int len = code.length;
        if (n > 0) {
            b = jumps.get(0);
            bpos = b.orgPos;
        } else {
            b = null;
            bpos = len;
        }
        while (src < len) {
            if (src == where) {
                int pos2 = dest + gapLength;
                while (dest < pos2) {
                    newcode[dest++] = 0;
                }
            }
            if (src != bpos) {
                newcode[dest++] = code[src++];
                continue;
            }
            int s = b.write(src, code, dest, newcode);
            src += s;
            dest += s + b.deltaSize();
            if (++bindex < n) {
                b = jumps.get(bindex);
                bpos = b.orgPos;
                continue;
            }
            b = null;
            bpos = len;
        }
        return newcode;
    }

    static class AlignmentException
    extends Exception {
        private static final long serialVersionUID = 1L;

        AlignmentException() {
        }
    }

    static abstract class Branch {
        int pos;
        int orgPos;

        Branch(int p) {
            this.pos = this.orgPos = p;
        }

        void shift(int where, int gapLength, boolean exclusive) {
            if (where < this.pos || where == this.pos && exclusive) {
                this.pos += gapLength;
            }
        }

        static int shiftOffset(int i, int offset, int where, int gapLength, boolean exclusive) {
            int target = i + offset;
            if (i < where) {
                if (where < target || exclusive && where == target) {
                    offset += gapLength;
                }
            } else if (i == where) {
                if (target < where && exclusive) {
                    offset -= gapLength;
                } else if (where < target && !exclusive) {
                    offset += gapLength;
                }
            } else if (target < where || !exclusive && where == target) {
                offset -= gapLength;
            }
            return offset;
        }

        boolean expanded() {
            return false;
        }

        int gapChanged() {
            return 0;
        }

        int deltaSize() {
            return 0;
        }

        abstract int write(int var1, byte[] var2, int var3, byte[] var4) throws BadBytecode;
    }

    static abstract class Branch16
    extends Branch {
        int offset;
        int state;
        static final int BIT16 = 0;
        static final int EXPAND = 1;
        static final int BIT32 = 2;

        Branch16(int p, int off) {
            super(p);
            this.offset = off;
            this.state = 0;
        }

        @Override
        void shift(int where, int gapLength, boolean exclusive) {
            this.offset = Branch16.shiftOffset(this.pos, this.offset, where, gapLength, exclusive);
            super.shift(where, gapLength, exclusive);
            if (this.state == 0 && (this.offset < Short.MIN_VALUE || Short.MAX_VALUE < this.offset)) {
                this.state = 1;
            }
        }

        @Override
        boolean expanded() {
            if (this.state == 1) {
                this.state = 2;
                return true;
            }
            return false;
        }

        @Override
        abstract int deltaSize();

        abstract void write32(int var1, byte[] var2, int var3, byte[] var4);

        @Override
        int write(int src, byte[] code, int dest, byte[] newcode) {
            if (this.state == 2) {
                this.write32(src, code, dest, newcode);
            } else {
                newcode[dest] = code[src];
                ByteArray.write16bit(this.offset, newcode, dest + 1);
            }
            return 3;
        }
    }

    public static class Gap {
        public int position;
        public int length;
    }

    static class If16
    extends Branch16 {
        If16(int p, int off) {
            super(p, off);
        }

        @Override
        int deltaSize() {
            return this.state == 2 ? 5 : 0;
        }

        @Override
        void write32(int src, byte[] code, int dest, byte[] newcode) {
            newcode[dest] = (byte)this.opcode(code[src] & 0xFF);
            newcode[dest + 1] = 0;
            newcode[dest + 2] = 8;
            newcode[dest + 3] = -56;
            ByteArray.write32bit(this.offset - 3, newcode, dest + 4);
        }

        int opcode(int op) {
            if (op == 198) {
                return 199;
            }
            if (op == 199) {
                return 198;
            }
            if ((op - 153 & 1) == 0) {
                return op + 1;
            }
            return op - 1;
        }
    }

    static class Jump16
    extends Branch16 {
        Jump16(int p, int off) {
            super(p, off);
        }

        @Override
        int deltaSize() {
            return this.state == 2 ? 2 : 0;
        }

        @Override
        void write32(int src, byte[] code, int dest, byte[] newcode) {
            newcode[dest] = (byte)((code[src] & 0xFF) == 167 ? 200 : 201);
            ByteArray.write32bit(this.offset, newcode, dest + 1);
        }
    }

    static class Jump32
    extends Branch {
        int offset;

        Jump32(int p, int off) {
            super(p);
            this.offset = off;
        }

        @Override
        void shift(int where, int gapLength, boolean exclusive) {
            this.offset = Jump32.shiftOffset(this.pos, this.offset, where, gapLength, exclusive);
            super.shift(where, gapLength, exclusive);
        }

        @Override
        int write(int src, byte[] code, int dest, byte[] newcode) {
            newcode[dest] = code[src];
            ByteArray.write32bit(this.offset, newcode, dest + 1);
            return 5;
        }
    }

    static class LdcW
    extends Branch {
        int index;
        boolean state;

        LdcW(int p, int i) {
            super(p);
            this.index = i;
            this.state = true;
        }

        @Override
        boolean expanded() {
            if (this.state) {
                this.state = false;
                return true;
            }
            return false;
        }

        @Override
        int deltaSize() {
            return 1;
        }

        @Override
        int write(int srcPos, byte[] code, int destPos, byte[] newcode) {
            newcode[destPos] = 19;
            ByteArray.write16bit(this.index, newcode, destPos + 1);
            return 2;
        }
    }

    static class Lookup
    extends Switcher {
        int[] matches;

        Lookup(int pos, int defaultByte, int[] matches, int[] offsets, Pointers ptrs) {
            super(pos, defaultByte, offsets, ptrs);
            this.matches = matches;
        }

        @Override
        int write2(int dest, byte[] newcode) {
            int n = this.matches.length;
            ByteArray.write32bit(n, newcode, dest);
            dest += 4;
            int i = 0;
            while (i < n) {
                ByteArray.write32bit(this.matches[i], newcode, dest);
                ByteArray.write32bit(this.offsets[i], newcode, dest + 4);
                dest += 8;
                ++i;
            }
            return 4 + 8 * n;
        }

        @Override
        int tableSize() {
            return 4 + 8 * this.matches.length;
        }
    }

    static class Pointers {
        int cursor;
        int mark0;
        int mark;
        int mark2;
        ExceptionTable etable;
        LineNumberAttribute line;
        LocalVariableAttribute vars;
        LocalVariableAttribute types;
        StackMapTable stack;
        StackMap stack2;

        Pointers(int cur, int m, int m2, int m0, ExceptionTable et, CodeAttribute ca) {
            this.cursor = cur;
            this.mark = m;
            this.mark2 = m2;
            this.mark0 = m0;
            this.etable = et;
            this.line = (LineNumberAttribute)ca.getAttribute("LineNumberTable");
            this.vars = (LocalVariableAttribute)ca.getAttribute("LocalVariableTable");
            this.types = (LocalVariableAttribute)ca.getAttribute("LocalVariableTypeTable");
            this.stack = (StackMapTable)ca.getAttribute("StackMapTable");
            this.stack2 = (StackMap)ca.getAttribute("StackMap");
        }

        void shiftPc(int where, int gapLength, boolean exclusive) throws BadBytecode {
            if (where < this.cursor || where == this.cursor && exclusive) {
                this.cursor += gapLength;
            }
            if (where < this.mark || where == this.mark && exclusive) {
                this.mark += gapLength;
            }
            if (where < this.mark2 || where == this.mark2 && exclusive) {
                this.mark2 += gapLength;
            }
            if (where < this.mark0 || where == this.mark0 && exclusive) {
                this.mark0 += gapLength;
            }
            this.etable.shiftPc(where, gapLength, exclusive);
            if (this.line != null) {
                this.line.shiftPc(where, gapLength, exclusive);
            }
            if (this.vars != null) {
                this.vars.shiftPc(where, gapLength, exclusive);
            }
            if (this.types != null) {
                this.types.shiftPc(where, gapLength, exclusive);
            }
            if (this.stack != null) {
                this.stack.shiftPc(where, gapLength, exclusive);
            }
            if (this.stack2 != null) {
                this.stack2.shiftPc(where, gapLength, exclusive);
            }
        }

        void shiftForSwitch(int where, int gapLength) throws BadBytecode {
            if (this.stack != null) {
                this.stack.shiftForSwitch(where, gapLength);
            }
            if (this.stack2 != null) {
                this.stack2.shiftForSwitch(where, gapLength);
            }
        }
    }

    static abstract class Switcher
    extends Branch {
        int gap;
        int defaultByte;
        int[] offsets;
        Pointers pointers;

        Switcher(int pos, int defaultByte, int[] offsets, Pointers ptrs) {
            super(pos);
            this.gap = 3 - (pos & 3);
            this.defaultByte = defaultByte;
            this.offsets = offsets;
            this.pointers = ptrs;
        }

        @Override
        void shift(int where, int gapLength, boolean exclusive) {
            int p = this.pos;
            this.defaultByte = Switcher.shiftOffset(p, this.defaultByte, where, gapLength, exclusive);
            int num = this.offsets.length;
            int i = 0;
            while (i < num) {
                this.offsets[i] = Switcher.shiftOffset(p, this.offsets[i], where, gapLength, exclusive);
                ++i;
            }
            super.shift(where, gapLength, exclusive);
        }

        @Override
        int gapChanged() {
            int newGap = 3 - (this.pos & 3);
            if (newGap > this.gap) {
                int diff = newGap - this.gap;
                this.gap = newGap;
                return diff;
            }
            return 0;
        }

        @Override
        int deltaSize() {
            return this.gap - (3 - (this.orgPos & 3));
        }

        @Override
        int write(int src, byte[] code, int dest, byte[] newcode) throws BadBytecode {
            int padding = 3 - (this.pos & 3);
            int nops = this.gap - padding;
            int bytecodeSize = 5 + (3 - (this.orgPos & 3)) + this.tableSize();
            if (nops > 0) {
                this.adjustOffsets(bytecodeSize, nops);
            }
            newcode[dest++] = code[src];
            while (padding-- > 0) {
                newcode[dest++] = 0;
            }
            ByteArray.write32bit(this.defaultByte, newcode, dest);
            int size = this.write2(dest + 4, newcode);
            dest += size + 4;
            while (nops-- > 0) {
                newcode[dest++] = 0;
            }
            return 5 + (3 - (this.orgPos & 3)) + size;
        }

        abstract int write2(int var1, byte[] var2);

        abstract int tableSize();

        void adjustOffsets(int size, int nops) throws BadBytecode {
            this.pointers.shiftForSwitch(this.pos + size, nops);
            if (this.defaultByte == size) {
                this.defaultByte -= nops;
            }
            int i = 0;
            while (i < this.offsets.length) {
                if (this.offsets[i] == size) {
                    int n = i;
                    this.offsets[n] = this.offsets[n] - nops;
                }
                ++i;
            }
        }
    }

    static class Table
    extends Switcher {
        int low;
        int high;

        Table(int pos, int defaultByte, int low, int high, int[] offsets, Pointers ptrs) {
            super(pos, defaultByte, offsets, ptrs);
            this.low = low;
            this.high = high;
        }

        @Override
        int write2(int dest, byte[] newcode) {
            ByteArray.write32bit(this.low, newcode, dest);
            ByteArray.write32bit(this.high, newcode, dest + 4);
            int n = this.offsets.length;
            dest += 8;
            int i = 0;
            while (i < n) {
                ByteArray.write32bit(this.offsets[i], newcode, dest);
                dest += 4;
                ++i;
            }
            return 8 + 4 * n;
        }

        @Override
        int tableSize() {
            return 8 + 4 * this.offsets.length;
        }
    }
}

