/*
 * Decompiled with CFR 0.152.
 */
package bsh;

import bsh.BSHAllocationExpression;
import bsh.BSHAmbiguousName;
import bsh.BSHArguments;
import bsh.BSHArrayDimensions;
import bsh.BSHArrayInitializer;
import bsh.BSHAssignment;
import bsh.BSHBinaryExpression;
import bsh.BSHBlock;
import bsh.BSHCastExpression;
import bsh.BSHClassDeclaration;
import bsh.BSHEnhancedForStatement;
import bsh.BSHForStatement;
import bsh.BSHFormalComment;
import bsh.BSHFormalParameter;
import bsh.BSHFormalParameters;
import bsh.BSHIfStatement;
import bsh.BSHImportDeclaration;
import bsh.BSHLiteral;
import bsh.BSHMethodDeclaration;
import bsh.BSHMethodInvocation;
import bsh.BSHPackageDeclaration;
import bsh.BSHPrimaryExpression;
import bsh.BSHPrimarySuffix;
import bsh.BSHPrimitiveType;
import bsh.BSHReturnStatement;
import bsh.BSHReturnType;
import bsh.BSHStatementExpressionList;
import bsh.BSHSwitchLabel;
import bsh.BSHSwitchStatement;
import bsh.BSHTernaryExpression;
import bsh.BSHThrowStatement;
import bsh.BSHTryStatement;
import bsh.BSHType;
import bsh.BSHTypedVariableDeclaration;
import bsh.BSHUnaryExpression;
import bsh.BSHVariableDeclarator;
import bsh.BSHWhileStatement;
import bsh.Interpreter;
import bsh.JJTParserState;
import bsh.JavaCharStream;
import bsh.Modifiers;
import bsh.Node;
import bsh.ParseException;
import bsh.ParserConstants;
import bsh.ParserTokenManager;
import bsh.ParserTreeConstants;
import bsh.Primitive;
import bsh.SimpleNode;
import bsh.Token;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Enumeration;
import java.util.Vector;

public class Parser
implements ParserTreeConstants,
ParserConstants {
    protected JJTParserState jjtree = new JJTParserState();
    boolean retainComments = false;
    public ParserTokenManager token_source;
    JavaCharStream jj_input_stream;
    public Token token;
    public Token jj_nt;
    private int jj_ntk;
    private Token jj_scanpos;
    private Token jj_lastpos;
    private int jj_la;
    public boolean lookingAhead = false;
    private boolean jj_semLA;
    private int jj_gen;
    private final int[] jj_la1 = new int[92];
    private static int[] jj_la1_0;
    private static int[] jj_la1_1;
    private static int[] jj_la1_2;
    private static int[] jj_la1_3;
    private static int[] jj_la1_4;
    private final JJCalls[] jj_2_rtns = new JJCalls[31];
    private boolean jj_rescan = false;
    private int jj_gc = 0;
    private final LookaheadSuccess jj_ls = new LookaheadSuccess();
    private Vector jj_expentries = new Vector();
    private int[] jj_expentry;
    private int jj_kind = -1;
    private int[] jj_lasttokens = new int[100];
    private int jj_endpos;

    public void setRetainComments(boolean b) {
        this.retainComments = b;
    }

    void jjtreeOpenNodeScope(Node n) {
        ((SimpleNode)n).firstToken = this.getToken(1);
    }

    void jjtreeCloseNodeScope(Node n) {
        ((SimpleNode)n).lastToken = this.getToken(0);
    }

    void reInitInput(Reader in) {
        this.ReInit(in);
    }

    public SimpleNode popNode() {
        if (this.jjtree.nodeArity() > 0) {
            return (SimpleNode)this.jjtree.popNode();
        }
        return null;
    }

    void reInitTokenInput(Reader in) {
        this.jj_input_stream.ReInit(in, this.jj_input_stream.getEndLine(), this.jj_input_stream.getEndColumn());
    }

    public static void main(String[] args) throws IOException, ParseException {
        boolean print = false;
        int i = 0;
        if (args[0].equals("-p")) {
            ++i;
            print = true;
        }
        while (i < args.length) {
            FileReader in = new FileReader(args[i]);
            Parser parser = new Parser(in);
            parser.setRetainComments(true);
            while (!parser.Line()) {
                if (!print) continue;
                System.out.println(parser.popNode());
            }
            ++i;
        }
    }

    boolean isRegularForStatement() {
        int curTok = 1;
        Token tok = this.getToken(curTok++);
        if (tok.kind != 30) {
            return false;
        }
        tok = this.getToken(curTok++);
        if (tok.kind != 73) {
            return false;
        }
        while (true) {
            tok = this.getToken(curTok++);
            switch (tok.kind) {
                case 90: {
                    return false;
                }
                case 79: {
                    return true;
                }
                case 0: {
                    return false;
                }
            }
        }
    }

    ParseException createParseException(String message, Exception e) {
        Token errortok = this.token;
        int line = errortok.beginLine;
        int column = errortok.beginColumn;
        String mess = errortok.kind == 0 ? tokenImage[0] : errortok.image;
        return new ParseException("Parse error at line " + line + ", column " + column + " : " + message, e);
    }

    int parseInt(String s) throws NumberFormatException {
        int i;
        int radix;
        if (s.startsWith("0x") || s.startsWith("0X")) {
            radix = 16;
            i = 2;
        } else if (s.startsWith("0") && s.length() > 1) {
            radix = 8;
            i = 1;
        } else {
            radix = 10;
            i = 0;
        }
        int result = 0;
        int len = s.length();
        while (i < len) {
            if (result < 0) {
                throw new NumberFormatException("Number too big for integer type: " + s);
            }
            result *= radix;
            int digit = Character.digit(s.charAt(i), radix);
            if (digit < 0) {
                throw new NumberFormatException("Invalid integer type: " + s);
            }
            result += digit;
            ++i;
        }
        return result;
    }

    long parseLong(String s) throws NumberFormatException {
        int i;
        int radix;
        if (s.startsWith("0x") || s.startsWith("0X")) {
            radix = 16;
            i = 2;
        } else if (s.startsWith("0") && s.length() > 1) {
            radix = 8;
            i = 1;
        } else {
            radix = 10;
            i = 0;
        }
        long result = 0L;
        int len = s.length();
        while (i < len) {
            if (result < 0L) {
                throw new NumberFormatException("Number too big for long type: " + s);
            }
            result *= (long)radix;
            int digit = Character.digit(s.charAt(i), radix);
            if (digit < 0) {
                throw new NumberFormatException("Invalid long type: " + s);
            }
            result += (long)digit;
            ++i;
        }
        return result;
    }

    public final boolean Line() throws ParseException {
        switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 0: {
                this.jj_consume_token(0);
                Interpreter.debug("End of File!");
                return true;
            }
        }
        this.jj_la1[0] = this.jj_gen;
        if (this.jj_2_1(1)) {
            this.BlockStatement();
            return false;
        }
        this.jj_consume_token(-1);
        throw new ParseException();
    }

    public final Modifiers Modifiers(int context, boolean lookahead) throws ParseException {
        Modifiers mods = null;
        block18: while (true) {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 10: 
                case 27: 
                case 39: 
                case 43: 
                case 44: 
                case 45: 
                case 48: 
                case 49: 
                case 51: 
                case 52: 
                case 58: {
                    break;
                }
                default: {
                    this.jj_la1[1] = this.jj_gen;
                    break block18;
                }
            }
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 43: {
                    this.jj_consume_token(43);
                    break;
                }
                case 44: {
                    this.jj_consume_token(44);
                    break;
                }
                case 45: {
                    this.jj_consume_token(45);
                    break;
                }
                case 51: {
                    this.jj_consume_token(51);
                    break;
                }
                case 27: {
                    this.jj_consume_token(27);
                    break;
                }
                case 39: {
                    this.jj_consume_token(39);
                    break;
                }
                case 52: {
                    this.jj_consume_token(52);
                    break;
                }
                case 58: {
                    this.jj_consume_token(58);
                    break;
                }
                case 10: {
                    this.jj_consume_token(10);
                    break;
                }
                case 48: {
                    this.jj_consume_token(48);
                    break;
                }
                case 49: {
                    this.jj_consume_token(49);
                    break;
                }
                default: {
                    this.jj_la1[2] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
            if (lookahead) continue;
            try {
                if (mods == null) {
                    mods = new Modifiers();
                }
                mods.addModifier(context, this.getToken((int)0).image);
            }
            catch (IllegalStateException e) {
                throw this.createParseException(e.getMessage(), e);
            }
        }
        return mods;
    }

    public final void ClassDeclaration() throws ParseException {
        BSHClassDeclaration jjtn000 = new BSHClassDeclaration(1);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        this.jjtreeOpenNodeScope(jjtn000);
        try {
            Modifiers mods = this.Modifiers(0, false);
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 13: {
                    this.jj_consume_token(13);
                    break;
                }
                case 37: {
                    this.jj_consume_token(37);
                    jjtn000.isInterface = true;
                    break;
                }
                default: {
                    this.jj_la1[3] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
            Token name = this.jj_consume_token(70);
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 25: {
                    this.jj_consume_token(25);
                    this.AmbiguousName();
                    jjtn000.extend = true;
                    break;
                }
                default: {
                    this.jj_la1[4] = this.jj_gen;
                }
            }
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 33: {
                    int numInterfaces;
                    this.jj_consume_token(33);
                    jjtn000.numInterfaces = numInterfaces = this.NameList();
                    break;
                }
                default: {
                    this.jj_la1[5] = this.jj_gen;
                }
            }
            this.Block();
            this.jjtree.closeNodeScope((Node)jjtn000, true);
            jjtc000 = false;
            this.jjtreeCloseNodeScope(jjtn000);
            jjtn000.modifiers = mods;
            jjtn000.name = name.image;
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (jjte000 instanceof ParseException) {
                throw (ParseException)jjte000;
            }
            throw (Error)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
                this.jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void MethodDeclaration() throws ParseException {
        BSHMethodDeclaration jjtn000 = new BSHMethodDeclaration(2);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        this.jjtreeOpenNodeScope(jjtn000);
        Token t = null;
        try {
            Modifiers mods;
            jjtn000.modifiers = mods = this.Modifiers(1, false);
            if (this.jj_2_2(Integer.MAX_VALUE)) {
                t = this.jj_consume_token(70);
                jjtn000.name = t.image;
            } else {
                switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                    case 11: 
                    case 14: 
                    case 17: 
                    case 22: 
                    case 29: 
                    case 36: 
                    case 38: 
                    case 47: 
                    case 57: 
                    case 70: {
                        this.ReturnType();
                        t = this.jj_consume_token(70);
                        jjtn000.name = t.image;
                        break;
                    }
                    default: {
                        this.jj_la1[6] = this.jj_gen;
                        this.jj_consume_token(-1);
                        throw new ParseException();
                    }
                }
            }
            this.FormalParameters();
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 54: {
                    int count;
                    this.jj_consume_token(54);
                    jjtn000.numThrows = count = this.NameList();
                    break;
                }
                default: {
                    this.jj_la1[7] = this.jj_gen;
                }
            }
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 75: {
                    this.Block();
                    return;
                }
                case 79: {
                    this.jj_consume_token(79);
                    return;
                }
                default: {
                    this.jj_la1[8] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (!(jjte000 instanceof ParseException)) throw (Error)jjte000;
            throw (ParseException)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
                this.jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    public final void PackageDeclaration() throws ParseException {
        BSHPackageDeclaration jjtn000 = new BSHPackageDeclaration(3);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        this.jjtreeOpenNodeScope(jjtn000);
        try {
            this.jj_consume_token(42);
            this.AmbiguousName();
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (jjte000 instanceof ParseException) {
                throw (ParseException)jjte000;
            }
            throw (Error)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
                this.jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void ImportDeclaration() throws ParseException {
        BSHImportDeclaration jjtn000 = new BSHImportDeclaration(4);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        this.jjtreeOpenNodeScope(jjtn000);
        Token s = null;
        Token t = null;
        try {
            if (this.jj_2_3(3)) {
                switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                    case 48: {
                        s = this.jj_consume_token(48);
                        break;
                    }
                    default: {
                        this.jj_la1[9] = this.jj_gen;
                    }
                }
                this.jj_consume_token(34);
                this.AmbiguousName();
                switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                    case 81: {
                        t = this.jj_consume_token(81);
                        this.jj_consume_token(105);
                        break;
                    }
                    default: {
                        this.jj_la1[10] = this.jj_gen;
                    }
                }
                this.jj_consume_token(79);
                this.jjtree.closeNodeScope((Node)jjtn000, true);
                jjtc000 = false;
                this.jjtreeCloseNodeScope(jjtn000);
                if (s != null) {
                    jjtn000.staticImport = true;
                }
                if (t == null) return;
                jjtn000.importPackage = true;
                return;
            }
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 34: {
                    this.jj_consume_token(34);
                    this.jj_consume_token(105);
                    this.jj_consume_token(79);
                    this.jjtree.closeNodeScope((Node)jjtn000, true);
                    jjtc000 = false;
                    this.jjtreeCloseNodeScope(jjtn000);
                    jjtn000.superImport = true;
                    return;
                }
                default: {
                    this.jj_la1[11] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (!(jjte000 instanceof ParseException)) throw (Error)jjte000;
            throw (ParseException)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
                this.jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    public final void VariableDeclarator() throws ParseException {
        BSHVariableDeclarator jjtn000 = new BSHVariableDeclarator(5);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        this.jjtreeOpenNodeScope(jjtn000);
        try {
            Token t = this.jj_consume_token(70);
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 82: {
                    this.jj_consume_token(82);
                    this.VariableInitializer();
                    break;
                }
                default: {
                    this.jj_la1[12] = this.jj_gen;
                }
            }
            this.jjtree.closeNodeScope((Node)jjtn000, true);
            jjtc000 = false;
            this.jjtreeCloseNodeScope(jjtn000);
            jjtn000.name = t.image;
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (jjte000 instanceof ParseException) {
                throw (ParseException)jjte000;
            }
            throw (Error)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
                this.jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    public final void VariableInitializer() throws ParseException {
        switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 75: {
                this.ArrayInitializer();
                break;
            }
            case 11: 
            case 14: 
            case 17: 
            case 22: 
            case 26: 
            case 29: 
            case 36: 
            case 38: 
            case 40: 
            case 41: 
            case 47: 
            case 55: 
            case 57: 
            case 60: 
            case 64: 
            case 66: 
            case 67: 
            case 68: 
            case 70: 
            case 73: 
            case 87: 
            case 88: 
            case 101: 
            case 102: 
            case 103: 
            case 104: {
                this.Expression();
                break;
            }
            default: {
                this.jj_la1[13] = this.jj_gen;
                this.jj_consume_token(-1);
                throw new ParseException();
            }
        }
    }

    public final void ArrayInitializer() throws ParseException {
        BSHArrayInitializer jjtn000 = new BSHArrayInitializer(6);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        this.jjtreeOpenNodeScope(jjtn000);
        try {
            this.jj_consume_token(75);
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 11: 
                case 14: 
                case 17: 
                case 22: 
                case 26: 
                case 29: 
                case 36: 
                case 38: 
                case 40: 
                case 41: 
                case 47: 
                case 55: 
                case 57: 
                case 60: 
                case 64: 
                case 66: 
                case 67: 
                case 68: 
                case 70: 
                case 73: 
                case 75: 
                case 87: 
                case 88: 
                case 101: 
                case 102: 
                case 103: 
                case 104: {
                    this.VariableInitializer();
                    while (this.jj_2_4(2)) {
                        this.jj_consume_token(80);
                        this.VariableInitializer();
                    }
                    break;
                }
                default: {
                    this.jj_la1[14] = this.jj_gen;
                }
            }
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 80: {
                    this.jj_consume_token(80);
                    break;
                }
                default: {
                    this.jj_la1[15] = this.jj_gen;
                }
            }
            this.jj_consume_token(76);
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (jjte000 instanceof ParseException) {
                throw (ParseException)jjte000;
            }
            throw (Error)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
                this.jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    public final void FormalParameters() throws ParseException {
        BSHFormalParameters jjtn000 = new BSHFormalParameters(7);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        this.jjtreeOpenNodeScope(jjtn000);
        try {
            this.jj_consume_token(73);
            block2 : switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 11: 
                case 14: 
                case 17: 
                case 22: 
                case 27: 
                case 29: 
                case 36: 
                case 38: 
                case 47: 
                case 70: {
                    this.FormalParameter();
                    while (true) {
                        switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                            case 80: {
                                break;
                            }
                            default: {
                                this.jj_la1[16] = this.jj_gen;
                                break block2;
                            }
                        }
                        this.jj_consume_token(80);
                        this.FormalParameter();
                    }
                }
                default: {
                    this.jj_la1[17] = this.jj_gen;
                }
            }
            this.jj_consume_token(74);
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (jjte000 instanceof ParseException) {
                throw (ParseException)jjte000;
            }
            throw (Error)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
                this.jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void FormalParameter() throws ParseException {
        BSHFormalParameter jjtn000 = new BSHFormalParameter(8);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        this.jjtreeOpenNodeScope(jjtn000);
        try {
            if (this.jj_2_5(2)) {
                switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                    case 27: {
                        this.jj_consume_token(27);
                        break;
                    }
                    default: {
                        this.jj_la1[18] = this.jj_gen;
                    }
                }
                this.Type();
                switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                    case 85: {
                        this.TypeArguments();
                        break;
                    }
                    default: {
                        this.jj_la1[19] = this.jj_gen;
                    }
                }
                Token t = this.jj_consume_token(70);
                this.jjtree.closeNodeScope((Node)jjtn000, true);
                jjtc000 = false;
                this.jjtreeCloseNodeScope(jjtn000);
                jjtn000.name = t.image;
                return;
            }
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 70: {
                    Token t = this.jj_consume_token(70);
                    this.jjtree.closeNodeScope((Node)jjtn000, true);
                    jjtc000 = false;
                    this.jjtreeCloseNodeScope(jjtn000);
                    jjtn000.name = t.image;
                    return;
                }
                default: {
                    this.jj_la1[20] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (!(jjte000 instanceof ParseException)) throw (Error)jjte000;
            throw (ParseException)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
                this.jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    public final void Type() throws ParseException {
        BSHType jjtn000 = new BSHType(9);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        this.jjtreeOpenNodeScope(jjtn000);
        try {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 11: 
                case 14: 
                case 17: 
                case 22: 
                case 29: 
                case 36: 
                case 38: 
                case 47: {
                    this.PrimitiveType();
                    break;
                }
                case 70: {
                    this.AmbiguousName();
                    break;
                }
                default: {
                    this.jj_la1[21] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
            while (this.jj_2_6(2)) {
                this.jj_consume_token(77);
                this.jj_consume_token(78);
                jjtn000.addArrayDimension();
            }
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (jjte000 instanceof ParseException) {
                throw (ParseException)jjte000;
            }
            throw (Error)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
                this.jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void ReturnType() throws ParseException {
        BSHReturnType jjtn000 = new BSHReturnType(10);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        this.jjtreeOpenNodeScope(jjtn000);
        try {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 57: {
                    this.jj_consume_token(57);
                    this.jjtree.closeNodeScope((Node)jjtn000, true);
                    jjtc000 = false;
                    this.jjtreeCloseNodeScope(jjtn000);
                    jjtn000.isVoid = true;
                    return;
                }
                case 11: 
                case 14: 
                case 17: 
                case 22: 
                case 29: 
                case 36: 
                case 38: 
                case 47: 
                case 70: {
                    this.Type();
                    switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 85: {
                            this.TypeArguments();
                            return;
                        }
                    }
                    this.jj_la1[22] = this.jj_gen;
                    return;
                }
                default: {
                    this.jj_la1[23] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (!(jjte000 instanceof ParseException)) throw (Error)jjte000;
            throw (ParseException)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
                this.jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void PrimitiveType() throws ParseException {
        BSHPrimitiveType jjtn000 = new BSHPrimitiveType(11);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        this.jjtreeOpenNodeScope(jjtn000);
        try {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 11: {
                    this.jj_consume_token(11);
                    this.jjtree.closeNodeScope((Node)jjtn000, true);
                    jjtc000 = false;
                    this.jjtreeCloseNodeScope(jjtn000);
                    jjtn000.type = Boolean.TYPE;
                    return;
                }
                case 17: {
                    this.jj_consume_token(17);
                    this.jjtree.closeNodeScope((Node)jjtn000, true);
                    jjtc000 = false;
                    this.jjtreeCloseNodeScope(jjtn000);
                    jjtn000.type = Character.TYPE;
                    return;
                }
                case 14: {
                    this.jj_consume_token(14);
                    this.jjtree.closeNodeScope((Node)jjtn000, true);
                    jjtc000 = false;
                    this.jjtreeCloseNodeScope(jjtn000);
                    jjtn000.type = Byte.TYPE;
                    return;
                }
                case 47: {
                    this.jj_consume_token(47);
                    this.jjtree.closeNodeScope((Node)jjtn000, true);
                    jjtc000 = false;
                    this.jjtreeCloseNodeScope(jjtn000);
                    jjtn000.type = Short.TYPE;
                    return;
                }
                case 36: {
                    this.jj_consume_token(36);
                    this.jjtree.closeNodeScope((Node)jjtn000, true);
                    jjtc000 = false;
                    this.jjtreeCloseNodeScope(jjtn000);
                    jjtn000.type = Integer.TYPE;
                    return;
                }
                case 38: {
                    this.jj_consume_token(38);
                    this.jjtree.closeNodeScope((Node)jjtn000, true);
                    jjtc000 = false;
                    this.jjtreeCloseNodeScope(jjtn000);
                    jjtn000.type = Long.TYPE;
                    return;
                }
                case 29: {
                    this.jj_consume_token(29);
                    this.jjtree.closeNodeScope((Node)jjtn000, true);
                    jjtc000 = false;
                    this.jjtreeCloseNodeScope(jjtn000);
                    jjtn000.type = Float.TYPE;
                    return;
                }
                case 22: {
                    this.jj_consume_token(22);
                    this.jjtree.closeNodeScope((Node)jjtn000, true);
                    jjtc000 = false;
                    this.jjtreeCloseNodeScope(jjtn000);
                    jjtn000.type = Double.TYPE;
                    return;
                }
                default: {
                    this.jj_la1[24] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
                this.jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void AmbiguousName() throws ParseException {
        BSHAmbiguousName jjtn000 = new BSHAmbiguousName(12);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        this.jjtreeOpenNodeScope(jjtn000);
        try {
            Token t = this.jj_consume_token(70);
            StringBuilder s = new StringBuilder(t.image);
            while (this.jj_2_7(2)) {
                this.jj_consume_token(81);
                t = this.jj_consume_token(70);
                s.append("." + t.image);
            }
            this.jjtree.closeNodeScope((Node)jjtn000, true);
            jjtc000 = false;
            this.jjtreeCloseNodeScope(jjtn000);
            jjtn000.text = s.toString();
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
                this.jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    public final int NameList() throws ParseException {
        int count = 0;
        this.AmbiguousName();
        ++count;
        block3: while (true) {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 80: {
                    break;
                }
                default: {
                    this.jj_la1[25] = this.jj_gen;
                    break block3;
                }
            }
            this.jj_consume_token(80);
            this.AmbiguousName();
            ++count;
        }
        return count;
    }

    public final void Expression() throws ParseException {
        if (this.jj_2_8(Integer.MAX_VALUE)) {
            this.Assignment();
        } else {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 11: 
                case 14: 
                case 17: 
                case 22: 
                case 26: 
                case 29: 
                case 36: 
                case 38: 
                case 40: 
                case 41: 
                case 47: 
                case 55: 
                case 57: 
                case 60: 
                case 64: 
                case 66: 
                case 67: 
                case 68: 
                case 70: 
                case 73: 
                case 87: 
                case 88: 
                case 101: 
                case 102: 
                case 103: 
                case 104: {
                    this.ConditionalExpression();
                    break;
                }
                default: {
                    this.jj_la1[26] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
        }
    }

    public final void Assignment() throws ParseException {
        BSHAssignment jjtn000 = new BSHAssignment(13);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        this.jjtreeOpenNodeScope(jjtn000);
        try {
            int op;
            this.PrimaryExpression();
            jjtn000.operator = op = this.AssignmentOperator();
            this.Expression();
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (jjte000 instanceof ParseException) {
                throw (ParseException)jjte000;
            }
            throw (Error)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
                this.jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    public final int AssignmentOperator() throws ParseException {
        switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 82: {
                this.jj_consume_token(82);
                break;
            }
            case 121: {
                this.jj_consume_token(121);
                break;
            }
            case 122: {
                this.jj_consume_token(122);
                break;
            }
            case 128: {
                this.jj_consume_token(128);
                break;
            }
            case 119: {
                this.jj_consume_token(119);
                break;
            }
            case 120: {
                this.jj_consume_token(120);
                break;
            }
            case 123: {
                this.jj_consume_token(123);
                break;
            }
            case 127: {
                this.jj_consume_token(127);
                break;
            }
            case 125: {
                this.jj_consume_token(125);
                break;
            }
            case 129: {
                this.jj_consume_token(129);
                break;
            }
            case 130: {
                this.jj_consume_token(130);
                break;
            }
            case 131: {
                this.jj_consume_token(131);
                break;
            }
            case 132: {
                this.jj_consume_token(132);
                break;
            }
            case 133: {
                this.jj_consume_token(133);
                break;
            }
            case 134: {
                this.jj_consume_token(134);
                break;
            }
            default: {
                this.jj_la1[27] = this.jj_gen;
                this.jj_consume_token(-1);
                throw new ParseException();
            }
        }
        Token t = this.getToken(0);
        return t.kind;
    }

    public final void ConditionalExpression() throws ParseException {
        this.ConditionalOrExpression();
        switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 89: {
                this.jj_consume_token(89);
                this.Expression();
                this.jj_consume_token(90);
                BSHTernaryExpression jjtn001 = new BSHTernaryExpression(14);
                boolean jjtc001 = true;
                this.jjtree.openNodeScope(jjtn001);
                this.jjtreeOpenNodeScope(jjtn001);
                try {
                    this.ConditionalExpression();
                    break;
                }
                catch (Throwable jjte001) {
                    if (jjtc001) {
                        this.jjtree.clearNodeScope(jjtn001);
                        jjtc001 = false;
                    } else {
                        this.jjtree.popNode();
                    }
                    if (jjte001 instanceof RuntimeException) {
                        throw (RuntimeException)jjte001;
                    }
                    if (jjte001 instanceof ParseException) {
                        throw (ParseException)jjte001;
                    }
                    throw (Error)jjte001;
                }
                finally {
                    if (jjtc001) {
                        this.jjtree.closeNodeScope((Node)jjtn001, 3);
                        this.jjtreeCloseNodeScope(jjtn001);
                    }
                }
            }
            default: {
                this.jj_la1[28] = this.jj_gen;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void ConditionalOrExpression() throws ParseException {
        Token t = null;
        this.ConditionalAndExpression();
        while (true) {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 97: 
                case 98: {
                    break;
                }
                default: {
                    this.jj_la1[29] = this.jj_gen;
                    return;
                }
            }
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 97: {
                    t = this.jj_consume_token(97);
                    break;
                }
                case 98: {
                    t = this.jj_consume_token(98);
                    break;
                }
                default: {
                    this.jj_la1[30] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
            this.ConditionalAndExpression();
            BSHBinaryExpression jjtn001 = new BSHBinaryExpression(15);
            boolean jjtc001 = true;
            this.jjtree.openNodeScope(jjtn001);
            this.jjtreeOpenNodeScope(jjtn001);
            try {
                this.jjtree.closeNodeScope((Node)jjtn001, 2);
                jjtc001 = false;
                this.jjtreeCloseNodeScope(jjtn001);
                jjtn001.kind = t.kind;
                continue;
            }
            finally {
                if (!jjtc001) continue;
                this.jjtree.closeNodeScope((Node)jjtn001, 2);
                this.jjtreeCloseNodeScope(jjtn001);
                continue;
            }
            break;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void ConditionalAndExpression() throws ParseException {
        Token t = null;
        this.InclusiveOrExpression();
        while (true) {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 99: 
                case 100: {
                    break;
                }
                default: {
                    this.jj_la1[31] = this.jj_gen;
                    return;
                }
            }
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 99: {
                    t = this.jj_consume_token(99);
                    break;
                }
                case 100: {
                    t = this.jj_consume_token(100);
                    break;
                }
                default: {
                    this.jj_la1[32] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
            this.InclusiveOrExpression();
            BSHBinaryExpression jjtn001 = new BSHBinaryExpression(15);
            boolean jjtc001 = true;
            this.jjtree.openNodeScope(jjtn001);
            this.jjtreeOpenNodeScope(jjtn001);
            try {
                this.jjtree.closeNodeScope((Node)jjtn001, 2);
                jjtc001 = false;
                this.jjtreeCloseNodeScope(jjtn001);
                jjtn001.kind = t.kind;
                continue;
            }
            finally {
                if (!jjtc001) continue;
                this.jjtree.closeNodeScope((Node)jjtn001, 2);
                this.jjtreeCloseNodeScope(jjtn001);
                continue;
            }
            break;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void InclusiveOrExpression() throws ParseException {
        Token t = null;
        this.ExclusiveOrExpression();
        while (true) {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 109: 
                case 110: {
                    break;
                }
                default: {
                    this.jj_la1[33] = this.jj_gen;
                    return;
                }
            }
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 109: {
                    t = this.jj_consume_token(109);
                    break;
                }
                case 110: {
                    t = this.jj_consume_token(110);
                    break;
                }
                default: {
                    this.jj_la1[34] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
            this.ExclusiveOrExpression();
            BSHBinaryExpression jjtn001 = new BSHBinaryExpression(15);
            boolean jjtc001 = true;
            this.jjtree.openNodeScope(jjtn001);
            this.jjtreeOpenNodeScope(jjtn001);
            try {
                this.jjtree.closeNodeScope((Node)jjtn001, 2);
                jjtc001 = false;
                this.jjtreeCloseNodeScope(jjtn001);
                jjtn001.kind = t.kind;
                continue;
            }
            finally {
                if (!jjtc001) continue;
                this.jjtree.closeNodeScope((Node)jjtn001, 2);
                this.jjtreeCloseNodeScope(jjtn001);
                continue;
            }
            break;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void ExclusiveOrExpression() throws ParseException {
        Token t = null;
        this.AndExpression();
        while (true) {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 111: {
                    break;
                }
                default: {
                    this.jj_la1[35] = this.jj_gen;
                    return;
                }
            }
            t = this.jj_consume_token(111);
            this.AndExpression();
            BSHBinaryExpression jjtn001 = new BSHBinaryExpression(15);
            boolean jjtc001 = true;
            this.jjtree.openNodeScope(jjtn001);
            this.jjtreeOpenNodeScope(jjtn001);
            try {
                this.jjtree.closeNodeScope((Node)jjtn001, 2);
                jjtc001 = false;
                this.jjtreeCloseNodeScope(jjtn001);
                jjtn001.kind = t.kind;
                continue;
            }
            finally {
                if (!jjtc001) continue;
                this.jjtree.closeNodeScope((Node)jjtn001, 2);
                this.jjtreeCloseNodeScope(jjtn001);
                continue;
            }
            break;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void AndExpression() throws ParseException {
        Token t = null;
        this.EqualityExpression();
        while (true) {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 107: 
                case 108: {
                    break;
                }
                default: {
                    this.jj_la1[36] = this.jj_gen;
                    return;
                }
            }
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 107: {
                    t = this.jj_consume_token(107);
                    break;
                }
                case 108: {
                    t = this.jj_consume_token(108);
                    break;
                }
                default: {
                    this.jj_la1[37] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
            this.EqualityExpression();
            BSHBinaryExpression jjtn001 = new BSHBinaryExpression(15);
            boolean jjtc001 = true;
            this.jjtree.openNodeScope(jjtn001);
            this.jjtreeOpenNodeScope(jjtn001);
            try {
                this.jjtree.closeNodeScope((Node)jjtn001, 2);
                jjtc001 = false;
                this.jjtreeCloseNodeScope(jjtn001);
                jjtn001.kind = t.kind;
                continue;
            }
            finally {
                if (!jjtc001) continue;
                this.jjtree.closeNodeScope((Node)jjtn001, 2);
                this.jjtreeCloseNodeScope(jjtn001);
                continue;
            }
            break;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void EqualityExpression() throws ParseException {
        Token t = null;
        this.InstanceOfExpression();
        while (true) {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 91: 
                case 96: {
                    break;
                }
                default: {
                    this.jj_la1[38] = this.jj_gen;
                    return;
                }
            }
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 91: {
                    t = this.jj_consume_token(91);
                    break;
                }
                case 96: {
                    t = this.jj_consume_token(96);
                    break;
                }
                default: {
                    this.jj_la1[39] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
            this.InstanceOfExpression();
            BSHBinaryExpression jjtn001 = new BSHBinaryExpression(15);
            boolean jjtc001 = true;
            this.jjtree.openNodeScope(jjtn001);
            this.jjtreeOpenNodeScope(jjtn001);
            try {
                this.jjtree.closeNodeScope((Node)jjtn001, 2);
                jjtc001 = false;
                this.jjtreeCloseNodeScope(jjtn001);
                jjtn001.kind = t.kind;
                continue;
            }
            finally {
                if (!jjtc001) continue;
                this.jjtree.closeNodeScope((Node)jjtn001, 2);
                this.jjtreeCloseNodeScope(jjtn001);
                continue;
            }
            break;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void InstanceOfExpression() throws ParseException {
        Token t = null;
        this.RelationalExpression();
        switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 35: {
                t = this.jj_consume_token(35);
                this.Type();
                BSHBinaryExpression jjtn001 = new BSHBinaryExpression(15);
                boolean jjtc001 = true;
                this.jjtree.openNodeScope(jjtn001);
                this.jjtreeOpenNodeScope(jjtn001);
                try {
                    this.jjtree.closeNodeScope((Node)jjtn001, 2);
                    jjtc001 = false;
                    this.jjtreeCloseNodeScope(jjtn001);
                    jjtn001.kind = t.kind;
                    break;
                }
                finally {
                    if (jjtc001) {
                        this.jjtree.closeNodeScope((Node)jjtn001, 2);
                        this.jjtreeCloseNodeScope(jjtn001);
                    }
                }
            }
            default: {
                this.jj_la1[40] = this.jj_gen;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void RelationalExpression() throws ParseException {
        Token t = null;
        this.ShiftExpression();
        while (true) {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 83: 
                case 84: 
                case 85: 
                case 86: 
                case 92: 
                case 93: 
                case 94: 
                case 95: {
                    break;
                }
                default: {
                    this.jj_la1[41] = this.jj_gen;
                    return;
                }
            }
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 85: {
                    t = this.jj_consume_token(85);
                    break;
                }
                case 86: {
                    t = this.jj_consume_token(86);
                    break;
                }
                case 83: {
                    t = this.jj_consume_token(83);
                    break;
                }
                case 84: {
                    t = this.jj_consume_token(84);
                    break;
                }
                case 92: {
                    t = this.jj_consume_token(92);
                    break;
                }
                case 93: {
                    t = this.jj_consume_token(93);
                    break;
                }
                case 94: {
                    t = this.jj_consume_token(94);
                    break;
                }
                case 95: {
                    t = this.jj_consume_token(95);
                    break;
                }
                default: {
                    this.jj_la1[42] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
            this.ShiftExpression();
            BSHBinaryExpression jjtn001 = new BSHBinaryExpression(15);
            boolean jjtc001 = true;
            this.jjtree.openNodeScope(jjtn001);
            this.jjtreeOpenNodeScope(jjtn001);
            try {
                this.jjtree.closeNodeScope((Node)jjtn001, 2);
                jjtc001 = false;
                this.jjtreeCloseNodeScope(jjtn001);
                jjtn001.kind = t.kind;
                continue;
            }
            finally {
                if (!jjtc001) continue;
                this.jjtree.closeNodeScope((Node)jjtn001, 2);
                this.jjtreeCloseNodeScope(jjtn001);
                continue;
            }
            break;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void ShiftExpression() throws ParseException {
        Token t = null;
        this.AdditiveExpression();
        while (true) {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 113: 
                case 114: 
                case 115: 
                case 116: 
                case 117: 
                case 118: {
                    break;
                }
                default: {
                    this.jj_la1[43] = this.jj_gen;
                    return;
                }
            }
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 113: {
                    t = this.jj_consume_token(113);
                    break;
                }
                case 114: {
                    t = this.jj_consume_token(114);
                    break;
                }
                case 115: {
                    t = this.jj_consume_token(115);
                    break;
                }
                case 116: {
                    t = this.jj_consume_token(116);
                    break;
                }
                case 117: {
                    t = this.jj_consume_token(117);
                    break;
                }
                case 118: {
                    t = this.jj_consume_token(118);
                    break;
                }
                default: {
                    this.jj_la1[44] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
            this.AdditiveExpression();
            BSHBinaryExpression jjtn001 = new BSHBinaryExpression(15);
            boolean jjtc001 = true;
            this.jjtree.openNodeScope(jjtn001);
            this.jjtreeOpenNodeScope(jjtn001);
            try {
                this.jjtree.closeNodeScope((Node)jjtn001, 2);
                jjtc001 = false;
                this.jjtreeCloseNodeScope(jjtn001);
                jjtn001.kind = t.kind;
                continue;
            }
            finally {
                if (!jjtc001) continue;
                this.jjtree.closeNodeScope((Node)jjtn001, 2);
                this.jjtreeCloseNodeScope(jjtn001);
                continue;
            }
            break;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void AdditiveExpression() throws ParseException {
        Token t = null;
        this.MultiplicativeExpression();
        while (true) {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 103: 
                case 104: {
                    break;
                }
                default: {
                    this.jj_la1[45] = this.jj_gen;
                    return;
                }
            }
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 103: {
                    t = this.jj_consume_token(103);
                    break;
                }
                case 104: {
                    t = this.jj_consume_token(104);
                    break;
                }
                default: {
                    this.jj_la1[46] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
            this.MultiplicativeExpression();
            BSHBinaryExpression jjtn001 = new BSHBinaryExpression(15);
            boolean jjtc001 = true;
            this.jjtree.openNodeScope(jjtn001);
            this.jjtreeOpenNodeScope(jjtn001);
            try {
                this.jjtree.closeNodeScope((Node)jjtn001, 2);
                jjtc001 = false;
                this.jjtreeCloseNodeScope(jjtn001);
                jjtn001.kind = t.kind;
                continue;
            }
            finally {
                if (!jjtc001) continue;
                this.jjtree.closeNodeScope((Node)jjtn001, 2);
                this.jjtreeCloseNodeScope(jjtn001);
                continue;
            }
            break;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void MultiplicativeExpression() throws ParseException {
        Token t = null;
        this.UnaryExpression();
        while (true) {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 105: 
                case 106: 
                case 112: {
                    break;
                }
                default: {
                    this.jj_la1[47] = this.jj_gen;
                    return;
                }
            }
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 105: {
                    t = this.jj_consume_token(105);
                    break;
                }
                case 106: {
                    t = this.jj_consume_token(106);
                    break;
                }
                case 112: {
                    t = this.jj_consume_token(112);
                    break;
                }
                default: {
                    this.jj_la1[48] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
            this.UnaryExpression();
            BSHBinaryExpression jjtn001 = new BSHBinaryExpression(15);
            boolean jjtc001 = true;
            this.jjtree.openNodeScope(jjtn001);
            this.jjtreeOpenNodeScope(jjtn001);
            try {
                this.jjtree.closeNodeScope((Node)jjtn001, 2);
                jjtc001 = false;
                this.jjtreeCloseNodeScope(jjtn001);
                jjtn001.kind = t.kind;
                continue;
            }
            finally {
                if (!jjtc001) continue;
                this.jjtree.closeNodeScope((Node)jjtn001, 2);
                this.jjtreeCloseNodeScope(jjtn001);
                continue;
            }
            break;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void UnaryExpression() throws ParseException {
        Token t = null;
        switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 103: 
            case 104: {
                switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                    case 103: {
                        t = this.jj_consume_token(103);
                        break;
                    }
                    case 104: {
                        t = this.jj_consume_token(104);
                        break;
                    }
                    default: {
                        this.jj_la1[49] = this.jj_gen;
                        this.jj_consume_token(-1);
                        throw new ParseException();
                    }
                }
                this.UnaryExpression();
                BSHUnaryExpression jjtn001 = new BSHUnaryExpression(16);
                boolean jjtc001 = true;
                this.jjtree.openNodeScope(jjtn001);
                this.jjtreeOpenNodeScope(jjtn001);
                try {
                    this.jjtree.closeNodeScope((Node)jjtn001, 1);
                    jjtc001 = false;
                    this.jjtreeCloseNodeScope(jjtn001);
                    jjtn001.kind = t.kind;
                    break;
                }
                finally {
                    if (jjtc001) {
                        this.jjtree.closeNodeScope((Node)jjtn001, 1);
                        this.jjtreeCloseNodeScope(jjtn001);
                    }
                }
            }
            case 101: {
                this.PreIncrementExpression();
                break;
            }
            case 102: {
                this.PreDecrementExpression();
                break;
            }
            case 11: 
            case 14: 
            case 17: 
            case 22: 
            case 26: 
            case 29: 
            case 36: 
            case 38: 
            case 40: 
            case 41: 
            case 47: 
            case 55: 
            case 57: 
            case 60: 
            case 64: 
            case 66: 
            case 67: 
            case 68: 
            case 70: 
            case 73: 
            case 87: 
            case 88: {
                this.UnaryExpressionNotPlusMinus();
                break;
            }
            default: {
                this.jj_la1[50] = this.jj_gen;
                this.jj_consume_token(-1);
                throw new ParseException();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void PreIncrementExpression() throws ParseException {
        Token t = null;
        t = this.jj_consume_token(101);
        this.PrimaryExpression();
        BSHUnaryExpression jjtn001 = new BSHUnaryExpression(16);
        boolean jjtc001 = true;
        this.jjtree.openNodeScope(jjtn001);
        this.jjtreeOpenNodeScope(jjtn001);
        try {
            this.jjtree.closeNodeScope((Node)jjtn001, 1);
            jjtc001 = false;
            this.jjtreeCloseNodeScope(jjtn001);
            jjtn001.kind = t.kind;
        }
        finally {
            if (jjtc001) {
                this.jjtree.closeNodeScope((Node)jjtn001, 1);
                this.jjtreeCloseNodeScope(jjtn001);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void PreDecrementExpression() throws ParseException {
        Token t = null;
        t = this.jj_consume_token(102);
        this.PrimaryExpression();
        BSHUnaryExpression jjtn001 = new BSHUnaryExpression(16);
        boolean jjtc001 = true;
        this.jjtree.openNodeScope(jjtn001);
        this.jjtreeOpenNodeScope(jjtn001);
        try {
            this.jjtree.closeNodeScope((Node)jjtn001, 1);
            jjtc001 = false;
            this.jjtreeCloseNodeScope(jjtn001);
            jjtn001.kind = t.kind;
        }
        finally {
            if (jjtc001) {
                this.jjtree.closeNodeScope((Node)jjtn001, 1);
                this.jjtreeCloseNodeScope(jjtn001);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void UnaryExpressionNotPlusMinus() throws ParseException {
        Token t = null;
        block1 : switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 87: 
            case 88: {
                switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                    case 88: {
                        t = this.jj_consume_token(88);
                        break;
                    }
                    case 87: {
                        t = this.jj_consume_token(87);
                        break;
                    }
                    default: {
                        this.jj_la1[51] = this.jj_gen;
                        this.jj_consume_token(-1);
                        throw new ParseException();
                    }
                }
                this.UnaryExpression();
                BSHUnaryExpression jjtn001 = new BSHUnaryExpression(16);
                boolean jjtc001 = true;
                this.jjtree.openNodeScope(jjtn001);
                this.jjtreeOpenNodeScope(jjtn001);
                try {
                    this.jjtree.closeNodeScope((Node)jjtn001, 1);
                    jjtc001 = false;
                    this.jjtreeCloseNodeScope(jjtn001);
                    jjtn001.kind = t.kind;
                    break;
                }
                finally {
                    if (jjtc001) {
                        this.jjtree.closeNodeScope((Node)jjtn001, 1);
                        this.jjtreeCloseNodeScope(jjtn001);
                    }
                }
            }
            default: {
                this.jj_la1[52] = this.jj_gen;
                if (this.jj_2_9(Integer.MAX_VALUE)) {
                    this.CastExpression();
                    break;
                }
                switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                    case 11: 
                    case 14: 
                    case 17: 
                    case 22: 
                    case 26: 
                    case 29: 
                    case 36: 
                    case 38: 
                    case 40: 
                    case 41: 
                    case 47: 
                    case 55: 
                    case 57: 
                    case 60: 
                    case 64: 
                    case 66: 
                    case 67: 
                    case 68: 
                    case 70: 
                    case 73: {
                        this.PostfixExpression();
                        break block1;
                    }
                }
                this.jj_la1[53] = this.jj_gen;
                this.jj_consume_token(-1);
                throw new ParseException();
            }
        }
    }

    public final void CastLookahead() throws ParseException {
        if (this.jj_2_10(2)) {
            this.jj_consume_token(73);
            this.PrimitiveType();
        } else if (this.jj_2_11(Integer.MAX_VALUE)) {
            this.jj_consume_token(73);
            this.AmbiguousName();
            this.jj_consume_token(77);
            this.jj_consume_token(78);
        } else {
            block0 : switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 73: {
                    this.jj_consume_token(73);
                    this.AmbiguousName();
                    this.jj_consume_token(74);
                    switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 88: {
                            this.jj_consume_token(88);
                            break block0;
                        }
                        case 87: {
                            this.jj_consume_token(87);
                            break block0;
                        }
                        case 73: {
                            this.jj_consume_token(73);
                            break block0;
                        }
                        case 70: {
                            this.jj_consume_token(70);
                            break block0;
                        }
                        case 40: {
                            this.jj_consume_token(40);
                            break block0;
                        }
                        case 26: 
                        case 41: 
                        case 55: 
                        case 57: 
                        case 60: 
                        case 64: 
                        case 66: 
                        case 67: 
                        case 68: {
                            this.Literal();
                            break block0;
                        }
                    }
                    this.jj_la1[54] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
                default: {
                    this.jj_la1[55] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void PostfixExpression() throws ParseException {
        Token t = null;
        if (this.jj_2_12(Integer.MAX_VALUE)) {
            this.PrimaryExpression();
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 101: {
                    t = this.jj_consume_token(101);
                    break;
                }
                case 102: {
                    t = this.jj_consume_token(102);
                    break;
                }
                default: {
                    this.jj_la1[56] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
            BSHUnaryExpression jjtn001 = new BSHUnaryExpression(16);
            boolean jjtc001 = true;
            this.jjtree.openNodeScope(jjtn001);
            this.jjtreeOpenNodeScope(jjtn001);
            try {
                this.jjtree.closeNodeScope((Node)jjtn001, 1);
                jjtc001 = false;
                this.jjtreeCloseNodeScope(jjtn001);
                jjtn001.kind = t.kind;
                jjtn001.postfix = true;
            }
            finally {
                if (jjtc001) {
                    this.jjtree.closeNodeScope((Node)jjtn001, 1);
                    this.jjtreeCloseNodeScope(jjtn001);
                }
            }
        } else {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 11: 
                case 14: 
                case 17: 
                case 22: 
                case 26: 
                case 29: 
                case 36: 
                case 38: 
                case 40: 
                case 41: 
                case 47: 
                case 55: 
                case 57: 
                case 60: 
                case 64: 
                case 66: 
                case 67: 
                case 68: 
                case 70: 
                case 73: {
                    this.PrimaryExpression();
                    break;
                }
                default: {
                    this.jj_la1[57] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void CastExpression() throws ParseException {
        BSHCastExpression jjtn000 = new BSHCastExpression(17);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        this.jjtreeOpenNodeScope(jjtn000);
        try {
            if (this.jj_2_13(Integer.MAX_VALUE)) {
                this.jj_consume_token(73);
                this.Type();
                this.jj_consume_token(74);
                this.UnaryExpression();
                return;
            }
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 73: {
                    this.jj_consume_token(73);
                    this.Type();
                    this.jj_consume_token(74);
                    this.UnaryExpressionNotPlusMinus();
                    return;
                }
                default: {
                    this.jj_la1[58] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (!(jjte000 instanceof ParseException)) throw (Error)jjte000;
            throw (ParseException)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
                this.jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    public final void PrimaryExpression() throws ParseException {
        block14: {
            BSHPrimaryExpression jjtn000 = new BSHPrimaryExpression(18);
            boolean jjtc000 = true;
            this.jjtree.openNodeScope(jjtn000);
            this.jjtreeOpenNodeScope(jjtn000);
            try {
                this.PrimaryPrefix();
                while (true) {
                    switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 75: 
                        case 77: 
                        case 81: {
                            break;
                        }
                        default: {
                            this.jj_la1[59] = this.jj_gen;
                            break block14;
                        }
                    }
                    this.PrimarySuffix();
                }
            }
            catch (Throwable jjte000) {
                if (jjtc000) {
                    this.jjtree.clearNodeScope(jjtn000);
                    jjtc000 = false;
                } else {
                    this.jjtree.popNode();
                }
                if (jjte000 instanceof RuntimeException) {
                    throw (RuntimeException)jjte000;
                }
                if (jjte000 instanceof ParseException) {
                    throw (ParseException)jjte000;
                }
                throw (Error)jjte000;
            }
            finally {
                if (jjtc000) {
                    this.jjtree.closeNodeScope((Node)jjtn000, true);
                    this.jjtreeCloseNodeScope(jjtn000);
                }
            }
        }
    }

    public final void MethodInvocation() throws ParseException {
        BSHMethodInvocation jjtn000 = new BSHMethodInvocation(19);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        this.jjtreeOpenNodeScope(jjtn000);
        try {
            this.AmbiguousName();
            this.Arguments();
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (jjte000 instanceof ParseException) {
                throw (ParseException)jjte000;
            }
            throw (Error)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
                this.jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    public final void PrimaryPrefix() throws ParseException {
        block0 : switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 26: 
            case 41: 
            case 55: 
            case 57: 
            case 60: 
            case 64: 
            case 66: 
            case 67: 
            case 68: {
                this.Literal();
                break;
            }
            case 73: {
                this.jj_consume_token(73);
                this.Expression();
                this.jj_consume_token(74);
                break;
            }
            case 40: {
                this.AllocationExpression();
                break;
            }
            default: {
                this.jj_la1[60] = this.jj_gen;
                if (this.jj_2_14(Integer.MAX_VALUE)) {
                    this.MethodInvocation();
                    break;
                }
                if (this.jj_2_15(Integer.MAX_VALUE)) {
                    this.Type();
                    break;
                }
                switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                    case 70: {
                        this.AmbiguousName();
                        break block0;
                    }
                }
                this.jj_la1[61] = this.jj_gen;
                this.jj_consume_token(-1);
                throw new ParseException();
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void PrimarySuffix() throws ParseException {
        BSHPrimarySuffix jjtn000 = new BSHPrimarySuffix(20);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        this.jjtreeOpenNodeScope(jjtn000);
        Token t = null;
        try {
            if (this.jj_2_16(2)) {
                this.jj_consume_token(81);
                this.jj_consume_token(13);
                this.jjtree.closeNodeScope((Node)jjtn000, true);
                jjtc000 = false;
                this.jjtreeCloseNodeScope(jjtn000);
                jjtn000.operation = 0;
                return;
            }
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 77: {
                    this.jj_consume_token(77);
                    this.Expression();
                    this.jj_consume_token(78);
                    this.jjtree.closeNodeScope((Node)jjtn000, true);
                    jjtc000 = false;
                    this.jjtreeCloseNodeScope(jjtn000);
                    jjtn000.operation = 1;
                    return;
                }
                case 81: {
                    this.jj_consume_token(81);
                    t = this.jj_consume_token(70);
                    switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 73: {
                            this.Arguments();
                            break;
                        }
                        default: {
                            this.jj_la1[62] = this.jj_gen;
                        }
                    }
                    this.jjtree.closeNodeScope((Node)jjtn000, true);
                    jjtc000 = false;
                    this.jjtreeCloseNodeScope(jjtn000);
                    jjtn000.operation = 2;
                    jjtn000.field = t.image;
                    return;
                }
                case 75: {
                    this.jj_consume_token(75);
                    this.Expression();
                    this.jj_consume_token(76);
                    this.jjtree.closeNodeScope((Node)jjtn000, true);
                    jjtc000 = false;
                    this.jjtreeCloseNodeScope(jjtn000);
                    jjtn000.operation = 3;
                    return;
                }
                default: {
                    this.jj_la1[63] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (!(jjte000 instanceof ParseException)) throw (Error)jjte000;
            throw (ParseException)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
                this.jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void Literal() throws ParseException {
        BSHLiteral jjtn000 = new BSHLiteral(21);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        this.jjtreeOpenNodeScope(jjtn000);
        try {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 60: {
                    Token x = this.jj_consume_token(60);
                    this.jjtree.closeNodeScope((Node)jjtn000, true);
                    jjtc000 = false;
                    this.jjtreeCloseNodeScope(jjtn000);
                    String literal = x.image;
                    char ch = literal.charAt(literal.length() - 1);
                    if (ch == 'l' || ch == 'L') {
                        literal = literal.substring(0, literal.length() - 1);
                        try {
                            jjtn000.value = new Primitive(this.parseLong(literal));
                            return;
                        }
                        catch (NumberFormatException e) {
                            throw this.createParseException(e.getMessage(), e);
                        }
                    }
                    try {
                        jjtn000.value = new Primitive(this.parseInt(literal));
                        return;
                    }
                    catch (NumberFormatException e) {
                        throw this.createParseException(e.getMessage(), e);
                    }
                }
                case 64: {
                    Token x = this.jj_consume_token(64);
                    this.jjtree.closeNodeScope((Node)jjtn000, true);
                    jjtc000 = false;
                    this.jjtreeCloseNodeScope(jjtn000);
                    String literal = x.image;
                    char ch = literal.charAt(literal.length() - 1);
                    if (ch == 'f' || ch == 'F') {
                        literal = literal.substring(0, literal.length() - 1);
                        jjtn000.value = new Primitive(new Float(literal).floatValue());
                        return;
                    } else {
                        if (ch == 'd' || ch == 'D') {
                            literal = literal.substring(0, literal.length() - 1);
                        }
                        jjtn000.value = new Primitive(new Double(literal));
                        return;
                    }
                }
                case 66: {
                    Token x = this.jj_consume_token(66);
                    this.jjtree.closeNodeScope((Node)jjtn000, true);
                    jjtc000 = false;
                    this.jjtreeCloseNodeScope(jjtn000);
                    try {
                        jjtn000.charSetup(x.image.substring(1, x.image.length() - 1));
                        return;
                    }
                    catch (Exception e) {
                        throw this.createParseException("Error parsing character: " + x.image, e);
                    }
                }
                case 67: {
                    Token x = this.jj_consume_token(67);
                    this.jjtree.closeNodeScope((Node)jjtn000, true);
                    jjtc000 = false;
                    this.jjtreeCloseNodeScope(jjtn000);
                    try {
                        jjtn000.stringSetup(x.image.substring(1, x.image.length() - 1));
                        return;
                    }
                    catch (Exception e) {
                        throw this.createParseException("Error parsing string: " + x.image, e);
                    }
                }
                case 68: {
                    Token x = this.jj_consume_token(68);
                    this.jjtree.closeNodeScope((Node)jjtn000, true);
                    jjtc000 = false;
                    this.jjtreeCloseNodeScope(jjtn000);
                    try {
                        jjtn000.value = x.image.substring(3, x.image.length() - 3);
                        return;
                    }
                    catch (Exception e) {
                        throw this.createParseException("Error parsing long string: " + x.image, e);
                    }
                }
                case 26: 
                case 55: {
                    boolean b = this.BooleanLiteral();
                    this.jjtree.closeNodeScope((Node)jjtn000, true);
                    jjtc000 = false;
                    this.jjtreeCloseNodeScope(jjtn000);
                    jjtn000.value = b ? Primitive.TRUE : Primitive.FALSE;
                    return;
                }
                case 41: {
                    this.NullLiteral();
                    this.jjtree.closeNodeScope((Node)jjtn000, true);
                    jjtc000 = false;
                    this.jjtreeCloseNodeScope(jjtn000);
                    jjtn000.value = Primitive.NULL;
                    return;
                }
                case 57: {
                    this.VoidLiteral();
                    this.jjtree.closeNodeScope((Node)jjtn000, true);
                    jjtc000 = false;
                    this.jjtreeCloseNodeScope(jjtn000);
                    jjtn000.value = Primitive.VOID;
                    return;
                }
                default: {
                    this.jj_la1[64] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (!(jjte000 instanceof ParseException)) throw (Error)jjte000;
            throw (ParseException)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
                this.jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    public final boolean BooleanLiteral() throws ParseException {
        switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 55: {
                this.jj_consume_token(55);
                return true;
            }
            case 26: {
                this.jj_consume_token(26);
                return false;
            }
        }
        this.jj_la1[65] = this.jj_gen;
        this.jj_consume_token(-1);
        throw new ParseException();
    }

    public final void NullLiteral() throws ParseException {
        this.jj_consume_token(41);
    }

    public final void VoidLiteral() throws ParseException {
        this.jj_consume_token(57);
    }

    public final void Arguments() throws ParseException {
        BSHArguments jjtn000 = new BSHArguments(22);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        this.jjtreeOpenNodeScope(jjtn000);
        try {
            this.jj_consume_token(73);
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 11: 
                case 14: 
                case 17: 
                case 22: 
                case 26: 
                case 29: 
                case 36: 
                case 38: 
                case 40: 
                case 41: 
                case 47: 
                case 55: 
                case 57: 
                case 60: 
                case 64: 
                case 66: 
                case 67: 
                case 68: 
                case 70: 
                case 73: 
                case 87: 
                case 88: 
                case 101: 
                case 102: 
                case 103: 
                case 104: {
                    this.ArgumentList();
                    break;
                }
                default: {
                    this.jj_la1[66] = this.jj_gen;
                }
            }
            this.jj_consume_token(74);
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (jjte000 instanceof ParseException) {
                throw (ParseException)jjte000;
            }
            throw (Error)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
                this.jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    public final void ArgumentList() throws ParseException {
        this.Expression();
        block3: while (true) {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 80: {
                    break;
                }
                default: {
                    this.jj_la1[67] = this.jj_gen;
                    break block3;
                }
            }
            this.jj_consume_token(80);
            this.Expression();
        }
    }

    public final void TypeArguments() throws ParseException {
        this.jj_consume_token(85);
        this.jj_consume_token(70);
        this.jj_consume_token(83);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void AllocationExpression() throws ParseException {
        BSHAllocationExpression jjtn000 = new BSHAllocationExpression(23);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        this.jjtreeOpenNodeScope(jjtn000);
        try {
            if (this.jj_2_18(2)) {
                this.jj_consume_token(40);
                this.PrimitiveType();
                this.ArrayDimensions();
                return;
            }
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 40: {
                    this.jj_consume_token(40);
                    this.AmbiguousName();
                    switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 85: {
                            this.TypeArguments();
                            break;
                        }
                        default: {
                            this.jj_la1[68] = this.jj_gen;
                        }
                    }
                    switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 77: {
                            this.ArrayDimensions();
                            return;
                        }
                        case 73: {
                            this.Arguments();
                            if (!this.jj_2_17(2)) return;
                            this.Block();
                            return;
                        }
                        default: {
                            this.jj_la1[69] = this.jj_gen;
                            this.jj_consume_token(-1);
                            throw new ParseException();
                        }
                    }
                }
                default: {
                    this.jj_la1[70] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (!(jjte000 instanceof ParseException)) throw (Error)jjte000;
            throw (ParseException)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
                this.jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void ArrayDimensions() throws ParseException {
        BSHArrayDimensions jjtn000 = new BSHArrayDimensions(24);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        this.jjtreeOpenNodeScope(jjtn000);
        try {
            if (this.jj_2_21(2)) {
                do {
                    this.jj_consume_token(77);
                    this.Expression();
                    this.jj_consume_token(78);
                    jjtn000.addDefinedDimension();
                } while (this.jj_2_19(2));
                while (this.jj_2_20(2)) {
                    this.jj_consume_token(77);
                    this.jj_consume_token(78);
                    jjtn000.addUndefinedDimension();
                }
                return;
            }
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 77: {
                    block13: while (true) {
                        this.jj_consume_token(77);
                        this.jj_consume_token(78);
                        jjtn000.addUndefinedDimension();
                        switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                            case 77: {
                                continue block13;
                            }
                        }
                        break;
                    }
                    this.jj_la1[71] = this.jj_gen;
                    this.ArrayInitializer();
                    return;
                }
                default: {
                    this.jj_la1[72] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (!(jjte000 instanceof ParseException)) throw (Error)jjte000;
            throw (ParseException)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
                this.jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    public final void Statement() throws ParseException {
        if (this.jj_2_22(2)) {
            this.LabeledStatement();
        } else {
            block0 : switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 75: {
                    this.Block();
                    break;
                }
                case 79: {
                    this.EmptyStatement();
                    break;
                }
                case 11: 
                case 14: 
                case 17: 
                case 22: 
                case 26: 
                case 29: 
                case 36: 
                case 38: 
                case 40: 
                case 41: 
                case 47: 
                case 55: 
                case 57: 
                case 60: 
                case 64: 
                case 66: 
                case 67: 
                case 68: 
                case 70: 
                case 73: 
                case 87: 
                case 88: 
                case 101: 
                case 102: 
                case 103: 
                case 104: {
                    this.StatementExpression();
                    this.jj_consume_token(79);
                    break;
                }
                case 50: {
                    this.SwitchStatement();
                    break;
                }
                case 32: {
                    this.IfStatement();
                    break;
                }
                case 59: {
                    this.WhileStatement();
                    break;
                }
                case 21: {
                    this.DoStatement();
                    break;
                }
                default: {
                    this.jj_la1[73] = this.jj_gen;
                    if (this.isRegularForStatement()) {
                        this.ForStatement();
                        break;
                    }
                    switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 30: {
                            this.EnhancedForStatement();
                            break block0;
                        }
                        case 12: {
                            this.BreakStatement();
                            break block0;
                        }
                        case 19: {
                            this.ContinueStatement();
                            break block0;
                        }
                        case 46: {
                            this.ReturnStatement();
                            break block0;
                        }
                        case 51: {
                            this.SynchronizedStatement();
                            break block0;
                        }
                        case 53: {
                            this.ThrowStatement();
                            break block0;
                        }
                        case 56: {
                            this.TryStatement();
                            break block0;
                        }
                    }
                    this.jj_la1[74] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
        }
    }

    public final void LabeledStatement() throws ParseException {
        this.jj_consume_token(70);
        this.jj_consume_token(90);
        this.Statement();
    }

    public final void Block() throws ParseException {
        BSHBlock jjtn000 = new BSHBlock(25);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        this.jjtreeOpenNodeScope(jjtn000);
        try {
            this.jj_consume_token(75);
            while (this.jj_2_23(1)) {
                this.BlockStatement();
            }
            this.jj_consume_token(76);
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (jjte000 instanceof ParseException) {
                throw (ParseException)jjte000;
            }
            throw (Error)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
                this.jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    public final void BlockStatement() throws ParseException {
        if (this.jj_2_24(Integer.MAX_VALUE)) {
            this.ClassDeclaration();
        } else if (this.jj_2_25(Integer.MAX_VALUE)) {
            this.MethodDeclaration();
        } else if (this.jj_2_26(Integer.MAX_VALUE)) {
            this.MethodDeclaration();
        } else if (this.jj_2_27(Integer.MAX_VALUE)) {
            this.TypedVariableDeclaration();
            this.jj_consume_token(79);
        } else if (this.jj_2_28(1)) {
            this.Statement();
        } else {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 34: 
                case 48: {
                    this.ImportDeclaration();
                    break;
                }
                case 42: {
                    this.PackageDeclaration();
                    break;
                }
                case 69: {
                    this.FormalComment();
                    break;
                }
                default: {
                    this.jj_la1[75] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void FormalComment() throws ParseException {
        BSHFormalComment jjtn000 = new BSHFormalComment(26);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        this.jjtreeOpenNodeScope(jjtn000);
        try {
            Token t = this.jj_consume_token(69);
            this.jjtree.closeNodeScope((Node)jjtn000, this.retainComments);
            jjtc000 = false;
            this.jjtreeCloseNodeScope(jjtn000);
            jjtn000.text = t.image;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, this.retainComments);
                this.jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    public final void EmptyStatement() throws ParseException {
        this.jj_consume_token(79);
    }

    public final void StatementExpression() throws ParseException {
        this.Expression();
    }

    public final void SwitchStatement() throws ParseException {
        BSHSwitchStatement jjtn000 = new BSHSwitchStatement(27);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        this.jjtreeOpenNodeScope(jjtn000);
        try {
            this.jj_consume_token(50);
            this.jj_consume_token(73);
            this.Expression();
            this.jj_consume_token(74);
            this.jj_consume_token(75);
            block8: while (true) {
                switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                    case 15: 
                    case 20: {
                        break;
                    }
                    default: {
                        this.jj_la1[76] = this.jj_gen;
                        break block8;
                    }
                }
                this.SwitchLabel();
                while (true) {
                    if (!this.jj_2_29(1)) continue block8;
                    this.BlockStatement();
                }
                break;
            }
            this.jj_consume_token(76);
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (jjte000 instanceof ParseException) {
                throw (ParseException)jjte000;
            }
            throw (Error)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
                this.jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void SwitchLabel() throws ParseException {
        BSHSwitchLabel jjtn000 = new BSHSwitchLabel(28);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        this.jjtreeOpenNodeScope(jjtn000);
        try {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 15: {
                    this.jj_consume_token(15);
                    this.Expression();
                    this.jj_consume_token(90);
                    return;
                }
                case 20: {
                    this.jj_consume_token(20);
                    this.jj_consume_token(90);
                    this.jjtree.closeNodeScope((Node)jjtn000, true);
                    jjtc000 = false;
                    this.jjtreeCloseNodeScope(jjtn000);
                    jjtn000.isDefault = true;
                    return;
                }
                default: {
                    this.jj_la1[77] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (!(jjte000 instanceof ParseException)) throw (Error)jjte000;
            throw (ParseException)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
                this.jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void IfStatement() throws ParseException {
        BSHIfStatement jjtn000 = new BSHIfStatement(29);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        this.jjtreeOpenNodeScope(jjtn000);
        try {
            this.jj_consume_token(32);
            this.jj_consume_token(73);
            this.Expression();
            this.jj_consume_token(74);
            this.Statement();
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 23: {
                    this.jj_consume_token(23);
                    this.Statement();
                    return;
                }
                default: {
                    this.jj_la1[78] = this.jj_gen;
                    return;
                }
            }
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (!(jjte000 instanceof ParseException)) throw (Error)jjte000;
            throw (ParseException)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
                this.jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    public final void WhileStatement() throws ParseException {
        BSHWhileStatement jjtn000 = new BSHWhileStatement(30);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        this.jjtreeOpenNodeScope(jjtn000);
        try {
            this.jj_consume_token(59);
            this.jj_consume_token(73);
            this.Expression();
            this.jj_consume_token(74);
            this.Statement();
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (jjte000 instanceof ParseException) {
                throw (ParseException)jjte000;
            }
            throw (Error)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
                this.jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    public final void DoStatement() throws ParseException {
        BSHWhileStatement jjtn000 = new BSHWhileStatement(30);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        this.jjtreeOpenNodeScope(jjtn000);
        try {
            this.jj_consume_token(21);
            this.Statement();
            this.jj_consume_token(59);
            this.jj_consume_token(73);
            this.Expression();
            this.jj_consume_token(74);
            this.jj_consume_token(79);
            this.jjtree.closeNodeScope((Node)jjtn000, true);
            jjtc000 = false;
            this.jjtreeCloseNodeScope(jjtn000);
            jjtn000.isDoStatement = true;
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (jjte000 instanceof ParseException) {
                throw (ParseException)jjte000;
            }
            throw (Error)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
                this.jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    public final void ForStatement() throws ParseException {
        BSHForStatement jjtn000 = new BSHForStatement(31);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        this.jjtreeOpenNodeScope(jjtn000);
        Object t = null;
        try {
            this.jj_consume_token(30);
            this.jj_consume_token(73);
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 10: 
                case 11: 
                case 14: 
                case 17: 
                case 22: 
                case 26: 
                case 27: 
                case 29: 
                case 36: 
                case 38: 
                case 39: 
                case 40: 
                case 41: 
                case 43: 
                case 44: 
                case 45: 
                case 47: 
                case 48: 
                case 49: 
                case 51: 
                case 52: 
                case 55: 
                case 57: 
                case 58: 
                case 60: 
                case 64: 
                case 66: 
                case 67: 
                case 68: 
                case 70: 
                case 73: 
                case 87: 
                case 88: 
                case 101: 
                case 102: 
                case 103: 
                case 104: {
                    this.ForInit();
                    jjtn000.hasForInit = true;
                    break;
                }
                default: {
                    this.jj_la1[79] = this.jj_gen;
                }
            }
            this.jj_consume_token(79);
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 11: 
                case 14: 
                case 17: 
                case 22: 
                case 26: 
                case 29: 
                case 36: 
                case 38: 
                case 40: 
                case 41: 
                case 47: 
                case 55: 
                case 57: 
                case 60: 
                case 64: 
                case 66: 
                case 67: 
                case 68: 
                case 70: 
                case 73: 
                case 87: 
                case 88: 
                case 101: 
                case 102: 
                case 103: 
                case 104: {
                    this.Expression();
                    jjtn000.hasExpression = true;
                    break;
                }
                default: {
                    this.jj_la1[80] = this.jj_gen;
                }
            }
            this.jj_consume_token(79);
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 11: 
                case 14: 
                case 17: 
                case 22: 
                case 26: 
                case 29: 
                case 36: 
                case 38: 
                case 40: 
                case 41: 
                case 47: 
                case 55: 
                case 57: 
                case 60: 
                case 64: 
                case 66: 
                case 67: 
                case 68: 
                case 70: 
                case 73: 
                case 87: 
                case 88: 
                case 101: 
                case 102: 
                case 103: 
                case 104: {
                    this.ForUpdate();
                    jjtn000.hasForUpdate = true;
                    break;
                }
                default: {
                    this.jj_la1[81] = this.jj_gen;
                }
            }
            this.jj_consume_token(74);
            this.Statement();
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (jjte000 instanceof ParseException) {
                throw (ParseException)jjte000;
            }
            throw (Error)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
                this.jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void EnhancedForStatement() throws ParseException {
        BSHEnhancedForStatement jjtn000 = new BSHEnhancedForStatement(32);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        this.jjtreeOpenNodeScope(jjtn000);
        Token t = null;
        try {
            if (this.jj_2_30(4)) {
                this.jj_consume_token(30);
                this.jj_consume_token(73);
                t = this.jj_consume_token(70);
                this.jj_consume_token(90);
                this.Expression();
                this.jj_consume_token(74);
                this.Statement();
                this.jjtree.closeNodeScope((Node)jjtn000, true);
                jjtc000 = false;
                this.jjtreeCloseNodeScope(jjtn000);
                jjtn000.varName = t.image;
                return;
            }
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 30: {
                    this.jj_consume_token(30);
                    this.jj_consume_token(73);
                    this.Type();
                    t = this.jj_consume_token(70);
                    this.jj_consume_token(90);
                    this.Expression();
                    this.jj_consume_token(74);
                    this.Statement();
                    this.jjtree.closeNodeScope((Node)jjtn000, true);
                    jjtc000 = false;
                    this.jjtreeCloseNodeScope(jjtn000);
                    jjtn000.varName = t.image;
                    return;
                }
                default: {
                    this.jj_la1[82] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (!(jjte000 instanceof ParseException)) throw (Error)jjte000;
            throw (ParseException)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
                this.jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    public final void ForInit() throws ParseException {
        Object t = null;
        if (this.jj_2_31(Integer.MAX_VALUE)) {
            this.TypedVariableDeclaration();
        } else {
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 11: 
                case 14: 
                case 17: 
                case 22: 
                case 26: 
                case 29: 
                case 36: 
                case 38: 
                case 40: 
                case 41: 
                case 47: 
                case 55: 
                case 57: 
                case 60: 
                case 64: 
                case 66: 
                case 67: 
                case 68: 
                case 70: 
                case 73: 
                case 87: 
                case 88: 
                case 101: 
                case 102: 
                case 103: 
                case 104: {
                    this.StatementExpressionList();
                    break;
                }
                default: {
                    this.jj_la1[83] = this.jj_gen;
                    this.jj_consume_token(-1);
                    throw new ParseException();
                }
            }
        }
    }

    public final void TypedVariableDeclaration() throws ParseException {
        BSHTypedVariableDeclaration jjtn000 = new BSHTypedVariableDeclaration(33);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        this.jjtreeOpenNodeScope(jjtn000);
        Object t = null;
        try {
            Modifiers mods = this.Modifiers(2, false);
            this.Type();
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 85: {
                    this.TypeArguments();
                    break;
                }
                default: {
                    this.jj_la1[84] = this.jj_gen;
                }
            }
            this.VariableDeclarator();
            block11: while (true) {
                switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                    case 80: {
                        break;
                    }
                    default: {
                        this.jj_la1[85] = this.jj_gen;
                        break block11;
                    }
                }
                this.jj_consume_token(80);
                this.VariableDeclarator();
            }
            this.jjtree.closeNodeScope((Node)jjtn000, true);
            jjtc000 = false;
            this.jjtreeCloseNodeScope(jjtn000);
            jjtn000.modifiers = mods;
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (jjte000 instanceof ParseException) {
                throw (ParseException)jjte000;
            }
            throw (Error)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
                this.jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    public final void StatementExpressionList() throws ParseException {
        block14: {
            BSHStatementExpressionList jjtn000 = new BSHStatementExpressionList(34);
            boolean jjtc000 = true;
            this.jjtree.openNodeScope(jjtn000);
            this.jjtreeOpenNodeScope(jjtn000);
            try {
                this.StatementExpression();
                while (true) {
                    switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 80: {
                            break;
                        }
                        default: {
                            this.jj_la1[86] = this.jj_gen;
                            break block14;
                        }
                    }
                    this.jj_consume_token(80);
                    this.StatementExpression();
                }
            }
            catch (Throwable jjte000) {
                if (jjtc000) {
                    this.jjtree.clearNodeScope(jjtn000);
                    jjtc000 = false;
                } else {
                    this.jjtree.popNode();
                }
                if (jjte000 instanceof RuntimeException) {
                    throw (RuntimeException)jjte000;
                }
                if (jjte000 instanceof ParseException) {
                    throw (ParseException)jjte000;
                }
                throw (Error)jjte000;
            }
            finally {
                if (jjtc000) {
                    this.jjtree.closeNodeScope((Node)jjtn000, true);
                    this.jjtreeCloseNodeScope(jjtn000);
                }
            }
        }
    }

    public final void ForUpdate() throws ParseException {
        this.StatementExpressionList();
    }

    public final void BreakStatement() throws ParseException {
        BSHReturnStatement jjtn000 = new BSHReturnStatement(35);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        this.jjtreeOpenNodeScope(jjtn000);
        try {
            this.jj_consume_token(12);
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 70: {
                    this.jj_consume_token(70);
                    break;
                }
                default: {
                    this.jj_la1[87] = this.jj_gen;
                }
            }
            this.jj_consume_token(79);
            this.jjtree.closeNodeScope((Node)jjtn000, true);
            jjtc000 = false;
            this.jjtreeCloseNodeScope(jjtn000);
            jjtn000.kind = 12;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
                this.jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    public final void ContinueStatement() throws ParseException {
        BSHReturnStatement jjtn000 = new BSHReturnStatement(35);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        this.jjtreeOpenNodeScope(jjtn000);
        try {
            this.jj_consume_token(19);
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 70: {
                    this.jj_consume_token(70);
                    break;
                }
                default: {
                    this.jj_la1[88] = this.jj_gen;
                }
            }
            this.jj_consume_token(79);
            this.jjtree.closeNodeScope((Node)jjtn000, true);
            jjtc000 = false;
            this.jjtreeCloseNodeScope(jjtn000);
            jjtn000.kind = 19;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
                this.jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    public final void ReturnStatement() throws ParseException {
        BSHReturnStatement jjtn000 = new BSHReturnStatement(35);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        this.jjtreeOpenNodeScope(jjtn000);
        try {
            this.jj_consume_token(46);
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 11: 
                case 14: 
                case 17: 
                case 22: 
                case 26: 
                case 29: 
                case 36: 
                case 38: 
                case 40: 
                case 41: 
                case 47: 
                case 55: 
                case 57: 
                case 60: 
                case 64: 
                case 66: 
                case 67: 
                case 68: 
                case 70: 
                case 73: 
                case 87: 
                case 88: 
                case 101: 
                case 102: 
                case 103: 
                case 104: {
                    this.Expression();
                    break;
                }
                default: {
                    this.jj_la1[89] = this.jj_gen;
                }
            }
            this.jj_consume_token(79);
            this.jjtree.closeNodeScope((Node)jjtn000, true);
            jjtc000 = false;
            this.jjtreeCloseNodeScope(jjtn000);
            jjtn000.kind = 46;
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (jjte000 instanceof ParseException) {
                throw (ParseException)jjte000;
            }
            throw (Error)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
                this.jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    public final void SynchronizedStatement() throws ParseException {
        BSHBlock jjtn000 = new BSHBlock(25);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        this.jjtreeOpenNodeScope(jjtn000);
        try {
            this.jj_consume_token(51);
            this.jj_consume_token(73);
            this.Expression();
            this.jj_consume_token(74);
            this.Block();
            this.jjtree.closeNodeScope((Node)jjtn000, true);
            jjtc000 = false;
            this.jjtreeCloseNodeScope(jjtn000);
            jjtn000.isSynchronized = true;
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (jjte000 instanceof ParseException) {
                throw (ParseException)jjte000;
            }
            throw (Error)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
                this.jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    public final void ThrowStatement() throws ParseException {
        BSHThrowStatement jjtn000 = new BSHThrowStatement(36);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        this.jjtreeOpenNodeScope(jjtn000);
        try {
            this.jj_consume_token(53);
            this.Expression();
            this.jj_consume_token(79);
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (jjte000 instanceof ParseException) {
                throw (ParseException)jjte000;
            }
            throw (Error)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
                this.jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    public final void TryStatement() throws ParseException {
        BSHTryStatement jjtn000 = new BSHTryStatement(37);
        boolean jjtc000 = true;
        this.jjtree.openNodeScope(jjtn000);
        this.jjtreeOpenNodeScope(jjtn000);
        boolean closed = false;
        try {
            this.jj_consume_token(56);
            this.Block();
            block11: while (true) {
                switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                    case 16: {
                        break;
                    }
                    default: {
                        this.jj_la1[90] = this.jj_gen;
                        break block11;
                    }
                }
                this.jj_consume_token(16);
                this.jj_consume_token(73);
                this.FormalParameter();
                this.jj_consume_token(74);
                this.Block();
                closed = true;
            }
            switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                case 28: {
                    this.jj_consume_token(28);
                    this.Block();
                    closed = true;
                    break;
                }
                default: {
                    this.jj_la1[91] = this.jj_gen;
                }
            }
            this.jjtree.closeNodeScope((Node)jjtn000, true);
            jjtc000 = false;
            this.jjtreeCloseNodeScope(jjtn000);
            if (!closed) {
                throw this.generateParseException();
            }
        }
        catch (Throwable jjte000) {
            if (jjtc000) {
                this.jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                this.jjtree.popNode();
            }
            if (jjte000 instanceof RuntimeException) {
                throw (RuntimeException)jjte000;
            }
            if (jjte000 instanceof ParseException) {
                throw (ParseException)jjte000;
            }
            throw (Error)jjte000;
        }
        finally {
            if (jjtc000) {
                this.jjtree.closeNodeScope((Node)jjtn000, true);
                this.jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final boolean jj_2_1(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            boolean bl = !this.jj_3_1();
            return bl;
        }
        catch (LookaheadSuccess ls) {
            boolean bl = true;
            return bl;
        }
        finally {
            this.jj_save(0, xla);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final boolean jj_2_2(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            boolean bl = !this.jj_3_2();
            return bl;
        }
        catch (LookaheadSuccess ls) {
            boolean bl = true;
            return bl;
        }
        finally {
            this.jj_save(1, xla);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final boolean jj_2_3(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            boolean bl = !this.jj_3_3();
            return bl;
        }
        catch (LookaheadSuccess ls) {
            boolean bl = true;
            return bl;
        }
        finally {
            this.jj_save(2, xla);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final boolean jj_2_4(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            boolean bl = !this.jj_3_4();
            return bl;
        }
        catch (LookaheadSuccess ls) {
            boolean bl = true;
            return bl;
        }
        finally {
            this.jj_save(3, xla);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final boolean jj_2_5(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            boolean bl = !this.jj_3_5();
            return bl;
        }
        catch (LookaheadSuccess ls) {
            boolean bl = true;
            return bl;
        }
        finally {
            this.jj_save(4, xla);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final boolean jj_2_6(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            boolean bl = !this.jj_3_6();
            return bl;
        }
        catch (LookaheadSuccess ls) {
            boolean bl = true;
            return bl;
        }
        finally {
            this.jj_save(5, xla);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final boolean jj_2_7(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            boolean bl = !this.jj_3_7();
            return bl;
        }
        catch (LookaheadSuccess ls) {
            boolean bl = true;
            return bl;
        }
        finally {
            this.jj_save(6, xla);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final boolean jj_2_8(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            boolean bl = !this.jj_3_8();
            return bl;
        }
        catch (LookaheadSuccess ls) {
            boolean bl = true;
            return bl;
        }
        finally {
            this.jj_save(7, xla);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final boolean jj_2_9(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            boolean bl = !this.jj_3_9();
            return bl;
        }
        catch (LookaheadSuccess ls) {
            boolean bl = true;
            return bl;
        }
        finally {
            this.jj_save(8, xla);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final boolean jj_2_10(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            boolean bl = !this.jj_3_10();
            return bl;
        }
        catch (LookaheadSuccess ls) {
            boolean bl = true;
            return bl;
        }
        finally {
            this.jj_save(9, xla);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final boolean jj_2_11(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            boolean bl = !this.jj_3_11();
            return bl;
        }
        catch (LookaheadSuccess ls) {
            boolean bl = true;
            return bl;
        }
        finally {
            this.jj_save(10, xla);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final boolean jj_2_12(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            boolean bl = !this.jj_3_12();
            return bl;
        }
        catch (LookaheadSuccess ls) {
            boolean bl = true;
            return bl;
        }
        finally {
            this.jj_save(11, xla);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final boolean jj_2_13(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            boolean bl = !this.jj_3_13();
            return bl;
        }
        catch (LookaheadSuccess ls) {
            boolean bl = true;
            return bl;
        }
        finally {
            this.jj_save(12, xla);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final boolean jj_2_14(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            boolean bl = !this.jj_3_14();
            return bl;
        }
        catch (LookaheadSuccess ls) {
            boolean bl = true;
            return bl;
        }
        finally {
            this.jj_save(13, xla);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final boolean jj_2_15(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            boolean bl = !this.jj_3_15();
            return bl;
        }
        catch (LookaheadSuccess ls) {
            boolean bl = true;
            return bl;
        }
        finally {
            this.jj_save(14, xla);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final boolean jj_2_16(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            boolean bl = !this.jj_3_16();
            return bl;
        }
        catch (LookaheadSuccess ls) {
            boolean bl = true;
            return bl;
        }
        finally {
            this.jj_save(15, xla);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final boolean jj_2_17(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            boolean bl = !this.jj_3_17();
            return bl;
        }
        catch (LookaheadSuccess ls) {
            boolean bl = true;
            return bl;
        }
        finally {
            this.jj_save(16, xla);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final boolean jj_2_18(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            boolean bl = !this.jj_3_18();
            return bl;
        }
        catch (LookaheadSuccess ls) {
            boolean bl = true;
            return bl;
        }
        finally {
            this.jj_save(17, xla);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final boolean jj_2_19(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            boolean bl = !this.jj_3_19();
            return bl;
        }
        catch (LookaheadSuccess ls) {
            boolean bl = true;
            return bl;
        }
        finally {
            this.jj_save(18, xla);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final boolean jj_2_20(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            boolean bl = !this.jj_3_20();
            return bl;
        }
        catch (LookaheadSuccess ls) {
            boolean bl = true;
            return bl;
        }
        finally {
            this.jj_save(19, xla);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final boolean jj_2_21(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            boolean bl = !this.jj_3_21();
            return bl;
        }
        catch (LookaheadSuccess ls) {
            boolean bl = true;
            return bl;
        }
        finally {
            this.jj_save(20, xla);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final boolean jj_2_22(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            boolean bl = !this.jj_3_22();
            return bl;
        }
        catch (LookaheadSuccess ls) {
            boolean bl = true;
            return bl;
        }
        finally {
            this.jj_save(21, xla);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final boolean jj_2_23(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            boolean bl = !this.jj_3_23();
            return bl;
        }
        catch (LookaheadSuccess ls) {
            boolean bl = true;
            return bl;
        }
        finally {
            this.jj_save(22, xla);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final boolean jj_2_24(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            boolean bl = !this.jj_3_24();
            return bl;
        }
        catch (LookaheadSuccess ls) {
            boolean bl = true;
            return bl;
        }
        finally {
            this.jj_save(23, xla);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final boolean jj_2_25(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            boolean bl = !this.jj_3_25();
            return bl;
        }
        catch (LookaheadSuccess ls) {
            boolean bl = true;
            return bl;
        }
        finally {
            this.jj_save(24, xla);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final boolean jj_2_26(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            boolean bl = !this.jj_3_26();
            return bl;
        }
        catch (LookaheadSuccess ls) {
            boolean bl = true;
            return bl;
        }
        finally {
            this.jj_save(25, xla);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final boolean jj_2_27(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            boolean bl = !this.jj_3_27();
            return bl;
        }
        catch (LookaheadSuccess ls) {
            boolean bl = true;
            return bl;
        }
        finally {
            this.jj_save(26, xla);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final boolean jj_2_28(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            boolean bl = !this.jj_3_28();
            return bl;
        }
        catch (LookaheadSuccess ls) {
            boolean bl = true;
            return bl;
        }
        finally {
            this.jj_save(27, xla);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final boolean jj_2_29(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            boolean bl = !this.jj_3_29();
            return bl;
        }
        catch (LookaheadSuccess ls) {
            boolean bl = true;
            return bl;
        }
        finally {
            this.jj_save(28, xla);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final boolean jj_2_30(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            boolean bl = !this.jj_3_30();
            return bl;
        }
        catch (LookaheadSuccess ls) {
            boolean bl = true;
            return bl;
        }
        finally {
            this.jj_save(29, xla);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final boolean jj_2_31(int xla) {
        this.jj_la = xla;
        this.jj_lastpos = this.jj_scanpos = this.token;
        try {
            boolean bl = !this.jj_3_31();
            return bl;
        }
        catch (LookaheadSuccess ls) {
            boolean bl = true;
            return bl;
        }
        finally {
            this.jj_save(30, xla);
        }
    }

    private final boolean jj_3R_150() {
        if (this.jj_scan_token(40)) {
            return true;
        }
        if (this.jj_3R_29()) {
            return true;
        }
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_156()) {
            this.jj_scanpos = xsp;
        }
        xsp = this.jj_scanpos;
        if (this.jj_3R_157()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_158()) {
                return true;
            }
        }
        return false;
    }

    private final boolean jj_3R_59() {
        if (this.jj_scan_token(85)) {
            return true;
        }
        if (this.jj_scan_token(70)) {
            return true;
        }
        return this.jj_scan_token(83);
    }

    private final boolean jj_3_18() {
        if (this.jj_scan_token(40)) {
            return true;
        }
        if (this.jj_3R_37()) {
            return true;
        }
        return this.jj_3R_155();
    }

    private final boolean jj_3R_134() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3_18()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_150()) {
                return true;
            }
        }
        return false;
    }

    private final boolean jj_3R_74() {
        return this.jj_3R_111();
    }

    private final boolean jj_3R_40() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_73()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_74()) {
                return true;
            }
        }
        return false;
    }

    private final boolean jj_3R_73() {
        return this.jj_3R_110();
    }

    private final boolean jj_3R_152() {
        if (this.jj_scan_token(80)) {
            return true;
        }
        return this.jj_3R_40();
    }

    private final boolean jj_3R_138() {
        Token xsp;
        if (this.jj_3R_40()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_152());
        this.jj_scanpos = xsp;
        return false;
    }

    private final boolean jj_3R_109() {
        return this.jj_3R_138();
    }

    private final boolean jj_3R_79() {
        Token xsp;
        if (this.jj_3R_29()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_115());
        this.jj_scanpos = xsp;
        return false;
    }

    private final boolean jj_3R_72() {
        if (this.jj_scan_token(73)) {
            return true;
        }
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_109()) {
            this.jj_scanpos = xsp;
        }
        return this.jj_scan_token(74);
    }

    private final boolean jj_3_7() {
        if (this.jj_scan_token(81)) {
            return true;
        }
        return this.jj_scan_token(70);
    }

    private final boolean jj_3R_29() {
        Token xsp;
        if (this.jj_scan_token(70)) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3_7());
        this.jj_scanpos = xsp;
        return false;
    }

    private final boolean jj_3R_161() {
        return this.jj_scan_token(26);
    }

    private final boolean jj_3R_160() {
        return this.jj_scan_token(55);
    }

    private final boolean jj_3R_154() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_160()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_161()) {
                return true;
            }
        }
        return false;
    }

    private final boolean jj_3R_71() {
        return this.jj_scan_token(22);
    }

    private final boolean jj_3R_70() {
        return this.jj_scan_token(29);
    }

    private final boolean jj_3R_69() {
        return this.jj_scan_token(38);
    }

    private final boolean jj_3R_68() {
        return this.jj_scan_token(36);
    }

    private final boolean jj_3R_112() {
        return this.jj_3R_59();
    }

    private final boolean jj_3R_67() {
        return this.jj_scan_token(47);
    }

    private final boolean jj_3R_58() {
        return this.jj_3R_29();
    }

    private final boolean jj_3R_149() {
        return this.jj_scan_token(57);
    }

    private final boolean jj_3R_66() {
        return this.jj_scan_token(14);
    }

    private final boolean jj_3R_65() {
        return this.jj_scan_token(17);
    }

    private final boolean jj_3R_64() {
        return this.jj_scan_token(11);
    }

    private final boolean jj_3R_37() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_64()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_65()) {
                this.jj_scanpos = xsp;
                if (this.jj_3R_66()) {
                    this.jj_scanpos = xsp;
                    if (this.jj_3R_67()) {
                        this.jj_scanpos = xsp;
                        if (this.jj_3R_68()) {
                            this.jj_scanpos = xsp;
                            if (this.jj_3R_69()) {
                                this.jj_scanpos = xsp;
                                if (this.jj_3R_70()) {
                                    this.jj_scanpos = xsp;
                                    if (this.jj_3R_71()) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private final boolean jj_3R_148() {
        return this.jj_scan_token(41);
    }

    private final boolean jj_3R_33() {
        return this.jj_3R_59();
    }

    private final boolean jj_3R_147() {
        return this.jj_3R_154();
    }

    private final boolean jj_3R_77() {
        if (this.jj_3R_32()) {
            return true;
        }
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_112()) {
            this.jj_scanpos = xsp;
        }
        return false;
    }

    private final boolean jj_3R_43() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_76()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_77()) {
                return true;
            }
        }
        return false;
    }

    private final boolean jj_3R_76() {
        return this.jj_scan_token(57);
    }

    private final boolean jj_3_6() {
        if (this.jj_scan_token(77)) {
            return true;
        }
        return this.jj_scan_token(78);
    }

    private final boolean jj_3R_146() {
        return this.jj_scan_token(68);
    }

    private final boolean jj_3R_57() {
        return this.jj_3R_37();
    }

    private final boolean jj_3R_114() {
        if (this.jj_scan_token(80)) {
            return true;
        }
        return this.jj_3R_113();
    }

    private final boolean jj_3R_197() {
        if (this.jj_scan_token(28)) {
            return true;
        }
        return this.jj_3R_39();
    }

    private final boolean jj_3R_196() {
        if (this.jj_scan_token(16)) {
            return true;
        }
        if (this.jj_scan_token(73)) {
            return true;
        }
        if (this.jj_3R_113()) {
            return true;
        }
        if (this.jj_scan_token(74)) {
            return true;
        }
        return this.jj_3R_39();
    }

    private final boolean jj_3R_145() {
        return this.jj_scan_token(67);
    }

    private final boolean jj_3R_32() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_57()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_58()) {
                return true;
            }
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3_6());
        this.jj_scanpos = xsp;
        return false;
    }

    private final boolean jj_3R_128() {
        Token xsp;
        if (this.jj_scan_token(56)) {
            return true;
        }
        if (this.jj_3R_39()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_196());
        xsp = this.jj_scanpos = xsp;
        if (this.jj_3R_197()) {
            this.jj_scanpos = xsp;
        }
        return false;
    }

    private final boolean jj_3R_144() {
        return this.jj_scan_token(66);
    }

    private final boolean jj_3R_184() {
        if (this.jj_scan_token(80)) {
            return true;
        }
        return this.jj_3R_183();
    }

    private final boolean jj_3_4() {
        if (this.jj_scan_token(80)) {
            return true;
        }
        return this.jj_3R_31();
    }

    private final boolean jj_3R_140() {
        return this.jj_scan_token(70);
    }

    private final boolean jj_3_5() {
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(27)) {
            this.jj_scanpos = xsp;
        }
        if (this.jj_3R_32()) {
            return true;
        }
        xsp = this.jj_scanpos;
        if (this.jj_3R_33()) {
            this.jj_scanpos = xsp;
        }
        return this.jj_scan_token(70);
    }

    private final boolean jj_3R_78() {
        Token xsp;
        if (this.jj_3R_113()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_114());
        this.jj_scanpos = xsp;
        return false;
    }

    private final boolean jj_3R_113() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3_5()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_140()) {
                return true;
            }
        }
        return false;
    }

    private final boolean jj_3R_127() {
        if (this.jj_scan_token(53)) {
            return true;
        }
        if (this.jj_3R_40()) {
            return true;
        }
        return this.jj_scan_token(79);
    }

    private final boolean jj_3R_44() {
        if (this.jj_scan_token(73)) {
            return true;
        }
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_78()) {
            this.jj_scanpos = xsp;
        }
        return this.jj_scan_token(74);
    }

    private final boolean jj_3R_195() {
        return this.jj_3R_40();
    }

    private final boolean jj_3R_169() {
        Token xsp;
        if (this.jj_3R_31()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3_4());
        this.jj_scanpos = xsp;
        return false;
    }

    private final boolean jj_3R_143() {
        return this.jj_scan_token(64);
    }

    private final boolean jj_3R_126() {
        if (this.jj_scan_token(51)) {
            return true;
        }
        if (this.jj_scan_token(73)) {
            return true;
        }
        if (this.jj_3R_40()) {
            return true;
        }
        if (this.jj_scan_token(74)) {
            return true;
        }
        return this.jj_3R_39();
    }

    private final boolean jj_3R_100() {
        if (this.jj_scan_token(75)) {
            return true;
        }
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_169()) {
            this.jj_scanpos = xsp;
        }
        xsp = this.jj_scanpos;
        if (this.jj_scan_token(80)) {
            this.jj_scanpos = xsp;
        }
        return this.jj_scan_token(76);
    }

    private final boolean jj_3R_30() {
        if (this.jj_scan_token(81)) {
            return true;
        }
        return this.jj_scan_token(105);
    }

    private final boolean jj_3R_217() {
        if (this.jj_scan_token(80)) {
            return true;
        }
        return this.jj_3R_116();
    }

    private final boolean jj_3R_187() {
        if (this.jj_scan_token(82)) {
            return true;
        }
        return this.jj_3R_31();
    }

    private final boolean jj_3R_125() {
        if (this.jj_scan_token(46)) {
            return true;
        }
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_195()) {
            this.jj_scanpos = xsp;
        }
        return this.jj_scan_token(79);
    }

    private final boolean jj_3R_56() {
        return this.jj_3R_40();
    }

    private final boolean jj_3R_55() {
        return this.jj_3R_100();
    }

    private final boolean jj_3R_31() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_55()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_56()) {
                return true;
            }
        }
        return false;
    }

    private final boolean jj_3R_124() {
        if (this.jj_scan_token(19)) {
            return true;
        }
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(70)) {
            this.jj_scanpos = xsp;
        }
        return this.jj_scan_token(79);
    }

    private final boolean jj_3R_123() {
        if (this.jj_scan_token(12)) {
            return true;
        }
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(70)) {
            this.jj_scanpos = xsp;
        }
        return this.jj_scan_token(79);
    }

    private final boolean jj_3R_142() {
        return this.jj_scan_token(60);
    }

    private final boolean jj_3R_133() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_142()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_143()) {
                this.jj_scanpos = xsp;
                if (this.jj_3R_144()) {
                    this.jj_scanpos = xsp;
                    if (this.jj_3R_145()) {
                        this.jj_scanpos = xsp;
                        if (this.jj_3R_146()) {
                            this.jj_scanpos = xsp;
                            if (this.jj_3R_147()) {
                                this.jj_scanpos = xsp;
                                if (this.jj_3R_148()) {
                                    this.jj_scanpos = xsp;
                                    if (this.jj_3R_149()) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private final boolean jj_3R_151() {
        return this.jj_3R_72();
    }

    private final boolean jj_3R_183() {
        if (this.jj_scan_token(70)) {
            return true;
        }
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_187()) {
            this.jj_scanpos = xsp;
        }
        return false;
    }

    private final boolean jj_3R_202() {
        return this.jj_3R_212();
    }

    private final boolean jj_3R_182() {
        return this.jj_3R_59();
    }

    private final boolean jj_3R_108() {
        return this.jj_3R_133();
    }

    private final boolean jj_3R_212() {
        Token xsp;
        if (this.jj_3R_116()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_217());
        this.jj_scanpos = xsp;
        return false;
    }

    private final boolean jj_3R_96() {
        if (this.jj_3R_42()) {
            return true;
        }
        if (this.jj_3R_32()) {
            return true;
        }
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_182()) {
            this.jj_scanpos = xsp;
        }
        if (this.jj_3R_183()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_184());
        this.jj_scanpos = xsp;
        return false;
    }

    private final boolean jj_3R_132() {
        if (this.jj_scan_token(34)) {
            return true;
        }
        if (this.jj_scan_token(105)) {
            return true;
        }
        return this.jj_scan_token(79);
    }

    private final boolean jj_3R_137() {
        if (this.jj_scan_token(75)) {
            return true;
        }
        if (this.jj_3R_40()) {
            return true;
        }
        return this.jj_scan_token(76);
    }

    private final boolean jj_3R_136() {
        if (this.jj_scan_token(81)) {
            return true;
        }
        if (this.jj_scan_token(70)) {
            return true;
        }
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_151()) {
            this.jj_scanpos = xsp;
        }
        return false;
    }

    private final boolean jj_3_31() {
        if (this.jj_3R_42()) {
            return true;
        }
        if (this.jj_3R_32()) {
            return true;
        }
        return this.jj_scan_token(70);
    }

    private final boolean jj_3_3() {
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(48)) {
            this.jj_scanpos = xsp;
        }
        if (this.jj_scan_token(34)) {
            return true;
        }
        if (this.jj_3R_29()) {
            return true;
        }
        xsp = this.jj_scanpos;
        if (this.jj_3R_30()) {
            this.jj_scanpos = xsp;
        }
        return this.jj_scan_token(79);
    }

    private final boolean jj_3R_97() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3_3()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_132()) {
                return true;
            }
        }
        return false;
    }

    private final boolean jj_3R_135() {
        if (this.jj_scan_token(77)) {
            return true;
        }
        if (this.jj_3R_40()) {
            return true;
        }
        return this.jj_scan_token(78);
    }

    private final boolean jj_3R_98() {
        if (this.jj_scan_token(42)) {
            return true;
        }
        return this.jj_3R_29();
    }

    private final boolean jj_3_2() {
        if (this.jj_scan_token(70)) {
            return true;
        }
        return this.jj_scan_token(73);
    }

    private final boolean jj_3R_211() {
        return this.jj_3R_212();
    }

    private final boolean jj_3R_181() {
        return this.jj_3R_39();
    }

    private final boolean jj_3_16() {
        if (this.jj_scan_token(81)) {
            return true;
        }
        return this.jj_scan_token(13);
    }

    private final boolean jj_3R_107() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3_16()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_135()) {
                this.jj_scanpos = xsp;
                if (this.jj_3R_136()) {
                    this.jj_scanpos = xsp;
                    if (this.jj_3R_137()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private final boolean jj_3R_180() {
        if (this.jj_scan_token(54)) {
            return true;
        }
        return this.jj_3R_79();
    }

    private final boolean jj_3R_210() {
        return this.jj_3R_96();
    }

    private final boolean jj_3R_201() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_210()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_211()) {
                return true;
            }
        }
        return false;
    }

    private final boolean jj_3_15() {
        if (this.jj_3R_32()) {
            return true;
        }
        if (this.jj_scan_token(81)) {
            return true;
        }
        return this.jj_scan_token(13);
    }

    private final boolean jj_3_14() {
        return this.jj_3R_38();
    }

    private final boolean jj_3R_130() {
        return this.jj_scan_token(70);
    }

    private final boolean jj_3R_141() {
        if (this.jj_scan_token(30)) {
            return true;
        }
        if (this.jj_scan_token(73)) {
            return true;
        }
        if (this.jj_3R_32()) {
            return true;
        }
        if (this.jj_scan_token(70)) {
            return true;
        }
        if (this.jj_scan_token(90)) {
            return true;
        }
        if (this.jj_3R_40()) {
            return true;
        }
        if (this.jj_scan_token(74)) {
            return true;
        }
        return this.jj_3R_47();
    }

    private final boolean jj_3R_131() {
        if (this.jj_3R_43()) {
            return true;
        }
        return this.jj_scan_token(70);
    }

    private final boolean jj_3R_95() {
        if (this.jj_3R_42()) {
            return true;
        }
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_130()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_131()) {
                return true;
            }
        }
        if (this.jj_3R_44()) {
            return true;
        }
        xsp = this.jj_scanpos;
        if (this.jj_3R_180()) {
            this.jj_scanpos = xsp;
        }
        xsp = this.jj_scanpos;
        if (this.jj_3R_181()) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(79)) {
                return true;
            }
        }
        return false;
    }

    private final boolean jj_3R_191() {
        if (this.jj_scan_token(23)) {
            return true;
        }
        return this.jj_3R_47();
    }

    private final boolean jj_3R_106() {
        return this.jj_3R_29();
    }

    private final boolean jj_3_30() {
        if (this.jj_scan_token(30)) {
            return true;
        }
        if (this.jj_scan_token(73)) {
            return true;
        }
        if (this.jj_scan_token(70)) {
            return true;
        }
        if (this.jj_scan_token(90)) {
            return true;
        }
        if (this.jj_3R_40()) {
            return true;
        }
        if (this.jj_scan_token(74)) {
            return true;
        }
        return this.jj_3R_47();
    }

    private final boolean jj_3R_122() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3_30()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_141()) {
                return true;
            }
        }
        return false;
    }

    private final boolean jj_3R_105() {
        return this.jj_3R_32();
    }

    private final boolean jj_3R_192() {
        return this.jj_3R_201();
    }

    private final boolean jj_3R_61() {
        return this.jj_3R_107();
    }

    private final boolean jj_3R_129() {
        return this.jj_scan_token(37);
    }

    private final boolean jj_3R_104() {
        return this.jj_3R_38();
    }

    private final boolean jj_3R_194() {
        return this.jj_3R_202();
    }

    private final boolean jj_3R_193() {
        return this.jj_3R_40();
    }

    private final boolean jj_3R_103() {
        return this.jj_3R_134();
    }

    private final boolean jj_3R_102() {
        if (this.jj_scan_token(73)) {
            return true;
        }
        if (this.jj_3R_40()) {
            return true;
        }
        return this.jj_scan_token(74);
    }

    private final boolean jj_3R_179() {
        if (this.jj_scan_token(33)) {
            return true;
        }
        return this.jj_3R_79();
    }

    private final boolean jj_3R_60() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_101()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_102()) {
                this.jj_scanpos = xsp;
                if (this.jj_3R_103()) {
                    this.jj_scanpos = xsp;
                    if (this.jj_3R_104()) {
                        this.jj_scanpos = xsp;
                        if (this.jj_3R_105()) {
                            this.jj_scanpos = xsp;
                            if (this.jj_3R_106()) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private final boolean jj_3R_101() {
        return this.jj_3R_133();
    }

    private final boolean jj_3R_178() {
        if (this.jj_scan_token(25)) {
            return true;
        }
        return this.jj_3R_29();
    }

    private final boolean jj_3R_38() {
        if (this.jj_3R_29()) {
            return true;
        }
        return this.jj_3R_72();
    }

    private final boolean jj_3R_121() {
        if (this.jj_scan_token(30)) {
            return true;
        }
        if (this.jj_scan_token(73)) {
            return true;
        }
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_192()) {
            this.jj_scanpos = xsp;
        }
        if (this.jj_scan_token(79)) {
            return true;
        }
        xsp = this.jj_scanpos;
        if (this.jj_3R_193()) {
            this.jj_scanpos = xsp;
        }
        if (this.jj_scan_token(79)) {
            return true;
        }
        xsp = this.jj_scanpos;
        if (this.jj_3R_194()) {
            this.jj_scanpos = xsp;
        }
        if (this.jj_scan_token(74)) {
            return true;
        }
        return this.jj_3R_47();
    }

    private final boolean jj_3R_94() {
        if (this.jj_3R_42()) {
            return true;
        }
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(13)) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_129()) {
                return true;
            }
        }
        if (this.jj_scan_token(70)) {
            return true;
        }
        xsp = this.jj_scanpos;
        if (this.jj_3R_178()) {
            this.jj_scanpos = xsp;
        }
        xsp = this.jj_scanpos;
        if (this.jj_3R_179()) {
            this.jj_scanpos = xsp;
        }
        return this.jj_3R_39();
    }

    private final boolean jj_3_13() {
        if (this.jj_scan_token(73)) {
            return true;
        }
        return this.jj_3R_37();
    }

    private final boolean jj_3R_34() {
        Token xsp;
        if (this.jj_3R_60()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_61());
        this.jj_scanpos = xsp;
        return false;
    }

    private final boolean jj_3R_120() {
        if (this.jj_scan_token(21)) {
            return true;
        }
        if (this.jj_3R_47()) {
            return true;
        }
        if (this.jj_scan_token(59)) {
            return true;
        }
        if (this.jj_scan_token(73)) {
            return true;
        }
        if (this.jj_3R_40()) {
            return true;
        }
        if (this.jj_scan_token(74)) {
            return true;
        }
        return this.jj_scan_token(79);
    }

    private final boolean jj_3R_224() {
        if (this.jj_scan_token(73)) {
            return true;
        }
        if (this.jj_3R_32()) {
            return true;
        }
        if (this.jj_scan_token(74)) {
            return true;
        }
        return this.jj_3R_215();
    }

    private final boolean jj_3R_223() {
        if (this.jj_scan_token(73)) {
            return true;
        }
        if (this.jj_3R_32()) {
            return true;
        }
        if (this.jj_scan_token(74)) {
            return true;
        }
        return this.jj_3R_198();
    }

    private final boolean jj_3R_221() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_223()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_224()) {
                return true;
            }
        }
        return false;
    }

    private final boolean jj_3_12() {
        if (this.jj_3R_34()) {
            return true;
        }
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(101)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(102)) {
                return true;
            }
        }
        return false;
    }

    private final boolean jj_3R_119() {
        if (this.jj_scan_token(59)) {
            return true;
        }
        if (this.jj_scan_token(73)) {
            return true;
        }
        if (this.jj_3R_40()) {
            return true;
        }
        if (this.jj_scan_token(74)) {
            return true;
        }
        return this.jj_3R_47();
    }

    private final boolean jj_3R_226() {
        return this.jj_3R_34();
    }

    private final boolean jj_3_11() {
        if (this.jj_scan_token(73)) {
            return true;
        }
        if (this.jj_3R_29()) {
            return true;
        }
        return this.jj_scan_token(77);
    }

    private final boolean jj_3_29() {
        return this.jj_3R_28();
    }

    private final boolean jj_3R_118() {
        if (this.jj_scan_token(32)) {
            return true;
        }
        if (this.jj_scan_token(73)) {
            return true;
        }
        if (this.jj_3R_40()) {
            return true;
        }
        if (this.jj_scan_token(74)) {
            return true;
        }
        if (this.jj_3R_47()) {
            return true;
        }
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_191()) {
            this.jj_scanpos = xsp;
        }
        return false;
    }

    private final boolean jj_3R_225() {
        if (this.jj_3R_34()) {
            return true;
        }
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(101)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(102)) {
                return true;
            }
        }
        return false;
    }

    private final boolean jj_3R_222() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_225()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_226()) {
                return true;
            }
        }
        return false;
    }

    private final boolean jj_3R_75() {
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(43)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(44)) {
                this.jj_scanpos = xsp;
                if (this.jj_scan_token(45)) {
                    this.jj_scanpos = xsp;
                    if (this.jj_scan_token(51)) {
                        this.jj_scanpos = xsp;
                        if (this.jj_scan_token(27)) {
                            this.jj_scanpos = xsp;
                            if (this.jj_scan_token(39)) {
                                this.jj_scanpos = xsp;
                                if (this.jj_scan_token(52)) {
                                    this.jj_scanpos = xsp;
                                    if (this.jj_scan_token(58)) {
                                        this.jj_scanpos = xsp;
                                        if (this.jj_scan_token(10)) {
                                            this.jj_scanpos = xsp;
                                            if (this.jj_scan_token(48)) {
                                                this.jj_scanpos = xsp;
                                                if (this.jj_scan_token(49)) {
                                                    return true;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private final boolean jj_3R_63() {
        if (this.jj_scan_token(73)) {
            return true;
        }
        if (this.jj_3R_29()) {
            return true;
        }
        if (this.jj_scan_token(74)) {
            return true;
        }
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(88)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(87)) {
                this.jj_scanpos = xsp;
                if (this.jj_scan_token(73)) {
                    this.jj_scanpos = xsp;
                    if (this.jj_scan_token(70)) {
                        this.jj_scanpos = xsp;
                        if (this.jj_scan_token(40)) {
                            this.jj_scanpos = xsp;
                            if (this.jj_3R_108()) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private final boolean jj_3R_209() {
        if (this.jj_scan_token(20)) {
            return true;
        }
        return this.jj_scan_token(90);
    }

    private final boolean jj_3R_62() {
        if (this.jj_scan_token(73)) {
            return true;
        }
        if (this.jj_3R_29()) {
            return true;
        }
        if (this.jj_scan_token(77)) {
            return true;
        }
        return this.jj_scan_token(78);
    }

    private final boolean jj_3_9() {
        return this.jj_3R_36();
    }

    private final boolean jj_3R_208() {
        if (this.jj_scan_token(15)) {
            return true;
        }
        if (this.jj_3R_40()) {
            return true;
        }
        return this.jj_scan_token(90);
    }

    private final boolean jj_3R_200() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_208()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_209()) {
                return true;
            }
        }
        return false;
    }

    private final boolean jj_3R_42() {
        Token xsp;
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_75());
        this.jj_scanpos = xsp;
        return false;
    }

    private final boolean jj_3R_36() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3_10()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_62()) {
                this.jj_scanpos = xsp;
                if (this.jj_3R_63()) {
                    return true;
                }
            }
        }
        return false;
    }

    private final boolean jj_3_10() {
        if (this.jj_scan_token(73)) {
            return true;
        }
        return this.jj_3R_37();
    }

    private final boolean jj_3R_190() {
        Token xsp;
        if (this.jj_3R_200()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3_29());
        this.jj_scanpos = xsp;
        return false;
    }

    private final boolean jj_3R_220() {
        return this.jj_3R_222();
    }

    private final boolean jj_3R_117() {
        Token xsp;
        if (this.jj_scan_token(50)) {
            return true;
        }
        if (this.jj_scan_token(73)) {
            return true;
        }
        if (this.jj_3R_40()) {
            return true;
        }
        if (this.jj_scan_token(74)) {
            return true;
        }
        if (this.jj_scan_token(75)) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_190());
        this.jj_scanpos = xsp;
        return this.jj_scan_token(76);
    }

    private final boolean jj_3R_219() {
        return this.jj_3R_221();
    }

    private final boolean jj_3R_218() {
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(88)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(87)) {
                return true;
            }
        }
        return this.jj_3R_198();
    }

    private final boolean jj_3R_215() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_218()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_219()) {
                this.jj_scanpos = xsp;
                if (this.jj_3R_220()) {
                    return true;
                }
            }
        }
        return false;
    }

    private final boolean jj_3R_46() {
        return this.jj_3R_59();
    }

    private final boolean jj_3R_214() {
        if (this.jj_scan_token(102)) {
            return true;
        }
        return this.jj_3R_34();
    }

    private final boolean jj_3_1() {
        return this.jj_3R_28();
    }

    private final boolean jj_3R_216() {
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(105)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(106)) {
                this.jj_scanpos = xsp;
                if (this.jj_scan_token(112)) {
                    return true;
                }
            }
        }
        return this.jj_3R_198();
    }

    private final boolean jj_3R_213() {
        if (this.jj_scan_token(101)) {
            return true;
        }
        return this.jj_3R_34();
    }

    private final boolean jj_3R_45() {
        if (this.jj_scan_token(54)) {
            return true;
        }
        return this.jj_3R_79();
    }

    private final boolean jj_3R_116() {
        return this.jj_3R_40();
    }

    private final boolean jj_3R_206() {
        return this.jj_3R_215();
    }

    private final boolean jj_3R_205() {
        return this.jj_3R_214();
    }

    private final boolean jj_3R_204() {
        return this.jj_3R_213();
    }

    private final boolean jj_3R_203() {
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(103)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(104)) {
                return true;
            }
        }
        return this.jj_3R_198();
    }

    private final boolean jj_3R_198() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_203()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_204()) {
                this.jj_scanpos = xsp;
                if (this.jj_3R_205()) {
                    this.jj_scanpos = xsp;
                    if (this.jj_3R_206()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private final boolean jj_3R_99() {
        return this.jj_scan_token(69);
    }

    private final boolean jj_3R_188() {
        Token xsp;
        if (this.jj_3R_198()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_216());
        this.jj_scanpos = xsp;
        return false;
    }

    private final boolean jj_3R_207() {
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(103)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(104)) {
                return true;
            }
        }
        return this.jj_3R_188();
    }

    private final boolean jj_3R_185() {
        Token xsp;
        if (this.jj_3R_188()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_207());
        this.jj_scanpos = xsp;
        return false;
    }

    private final boolean jj_3R_54() {
        return this.jj_3R_99();
    }

    private final boolean jj_3_27() {
        if (this.jj_3R_42()) {
            return true;
        }
        if (this.jj_3R_32()) {
            return true;
        }
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_46()) {
            this.jj_scanpos = xsp;
        }
        return this.jj_scan_token(70);
    }

    private final boolean jj_3R_53() {
        return this.jj_3R_98();
    }

    private final boolean jj_3R_199() {
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(113)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(114)) {
                this.jj_scanpos = xsp;
                if (this.jj_scan_token(115)) {
                    this.jj_scanpos = xsp;
                    if (this.jj_scan_token(116)) {
                        this.jj_scanpos = xsp;
                        if (this.jj_scan_token(117)) {
                            this.jj_scanpos = xsp;
                            if (this.jj_scan_token(118)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return this.jj_3R_185();
    }

    private final boolean jj_3R_52() {
        return this.jj_3R_97();
    }

    private final boolean jj_3_26() {
        if (this.jj_3R_42()) {
            return true;
        }
        if (this.jj_scan_token(70)) {
            return true;
        }
        if (this.jj_3R_44()) {
            return true;
        }
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_45()) {
            this.jj_scanpos = xsp;
        }
        return this.jj_scan_token(75);
    }

    private final boolean jj_3R_177() {
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(91)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(96)) {
                return true;
            }
        }
        return this.jj_3R_172();
    }

    private final boolean jj_3R_176() {
        Token xsp;
        if (this.jj_3R_185()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_199());
        this.jj_scanpos = xsp;
        return false;
    }

    private final boolean jj_3_28() {
        return this.jj_3R_47();
    }

    private final boolean jj_3_25() {
        if (this.jj_3R_42()) {
            return true;
        }
        if (this.jj_3R_43()) {
            return true;
        }
        if (this.jj_scan_token(70)) {
            return true;
        }
        return this.jj_scan_token(73);
    }

    private final boolean jj_3R_51() {
        if (this.jj_3R_96()) {
            return true;
        }
        return this.jj_scan_token(79);
    }

    private final boolean jj_3R_189() {
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(85)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(86)) {
                this.jj_scanpos = xsp;
                if (this.jj_scan_token(83)) {
                    this.jj_scanpos = xsp;
                    if (this.jj_scan_token(84)) {
                        this.jj_scanpos = xsp;
                        if (this.jj_scan_token(92)) {
                            this.jj_scanpos = xsp;
                            if (this.jj_scan_token(93)) {
                                this.jj_scanpos = xsp;
                                if (this.jj_scan_token(94)) {
                                    this.jj_scanpos = xsp;
                                    if (this.jj_scan_token(95)) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return this.jj_3R_176();
    }

    private final boolean jj_3_24() {
        if (this.jj_3R_42()) {
            return true;
        }
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(13)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(37)) {
                return true;
            }
        }
        return false;
    }

    private final boolean jj_3R_174() {
        Token xsp;
        if (this.jj_3R_176()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_189());
        this.jj_scanpos = xsp;
        return false;
    }

    private final boolean jj_3R_50() {
        return this.jj_3R_95();
    }

    private final boolean jj_3R_186() {
        if (this.jj_scan_token(35)) {
            return true;
        }
        return this.jj_3R_32();
    }

    private final boolean jj_3R_49() {
        return this.jj_3R_95();
    }

    private final boolean jj_3R_172() {
        if (this.jj_3R_174()) {
            return true;
        }
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_186()) {
            this.jj_scanpos = xsp;
        }
        return false;
    }

    private final boolean jj_3R_48() {
        return this.jj_3R_94();
    }

    private final boolean jj_3R_28() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_48()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_49()) {
                this.jj_scanpos = xsp;
                if (this.jj_3R_50()) {
                    this.jj_scanpos = xsp;
                    if (this.jj_3R_51()) {
                        this.jj_scanpos = xsp;
                        if (this.jj_3_28()) {
                            this.jj_scanpos = xsp;
                            if (this.jj_3R_52()) {
                                this.jj_scanpos = xsp;
                                if (this.jj_3R_53()) {
                                    this.jj_scanpos = xsp;
                                    if (this.jj_3R_54()) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private final boolean jj_3_23() {
        return this.jj_3R_28();
    }

    private final boolean jj_3R_173() {
        if (this.jj_scan_token(111)) {
            return true;
        }
        return this.jj_3R_167();
    }

    private final boolean jj_3R_170() {
        Token xsp;
        if (this.jj_3R_172()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_177());
        this.jj_scanpos = xsp;
        return false;
    }

    private final boolean jj_3R_39() {
        Token xsp;
        if (this.jj_scan_token(75)) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3_23());
        this.jj_scanpos = xsp;
        return this.jj_scan_token(76);
    }

    private final boolean jj_3R_175() {
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(107)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(108)) {
                return true;
            }
        }
        return this.jj_3R_170();
    }

    private final boolean jj_3R_41() {
        if (this.jj_scan_token(70)) {
            return true;
        }
        if (this.jj_scan_token(90)) {
            return true;
        }
        return this.jj_3R_47();
    }

    private final boolean jj_3R_167() {
        Token xsp;
        if (this.jj_3R_170()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_175());
        this.jj_scanpos = xsp;
        return false;
    }

    private final boolean jj_3R_93() {
        return this.jj_3R_128();
    }

    private final boolean jj_3R_92() {
        return this.jj_3R_127();
    }

    private final boolean jj_3R_164() {
        Token xsp;
        if (this.jj_3R_167()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_173());
        this.jj_scanpos = xsp;
        return false;
    }

    private final boolean jj_3R_91() {
        return this.jj_3R_126();
    }

    private final boolean jj_3R_90() {
        return this.jj_3R_125();
    }

    private final boolean jj_3R_162() {
        if (this.jj_scan_token(89)) {
            return true;
        }
        if (this.jj_3R_40()) {
            return true;
        }
        if (this.jj_scan_token(90)) {
            return true;
        }
        return this.jj_3R_111();
    }

    private final boolean jj_3R_89() {
        return this.jj_3R_124();
    }

    private final boolean jj_3R_171() {
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(109)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(110)) {
                return true;
            }
        }
        return this.jj_3R_164();
    }

    private final boolean jj_3R_88() {
        return this.jj_3R_123();
    }

    private final boolean jj_3R_159() {
        Token xsp;
        if (this.jj_3R_164()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_171());
        this.jj_scanpos = xsp;
        return false;
    }

    private final boolean jj_3R_87() {
        return this.jj_3R_122();
    }

    private final boolean jj_3R_86() {
        return this.jj_3R_121();
    }

    private final boolean jj_3R_85() {
        return this.jj_3R_120();
    }

    private final boolean jj_3R_168() {
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(99)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(100)) {
                return true;
            }
        }
        return this.jj_3R_159();
    }

    private final boolean jj_3R_84() {
        return this.jj_3R_119();
    }

    private final boolean jj_3R_153() {
        Token xsp;
        if (this.jj_3R_159()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_168());
        this.jj_scanpos = xsp;
        return false;
    }

    private final boolean jj_3R_83() {
        return this.jj_3R_118();
    }

    private final boolean jj_3R_82() {
        return this.jj_3R_117();
    }

    private final boolean jj_3R_81() {
        if (this.jj_3R_116()) {
            return true;
        }
        return this.jj_scan_token(79);
    }

    private final boolean jj_3R_165() {
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(97)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(98)) {
                return true;
            }
        }
        return this.jj_3R_153();
    }

    private final boolean jj_3_17() {
        return this.jj_3R_39();
    }

    private final boolean jj_3R_80() {
        return this.jj_3R_39();
    }

    private final boolean jj_3R_139() {
        Token xsp;
        if (this.jj_3R_153()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_165());
        this.jj_scanpos = xsp;
        return false;
    }

    private final boolean jj_3R_47() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3_22()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_80()) {
                this.jj_scanpos = xsp;
                if (this.jj_scan_token(79)) {
                    this.jj_scanpos = xsp;
                    if (this.jj_3R_81()) {
                        this.jj_scanpos = xsp;
                        if (this.jj_3R_82()) {
                            this.jj_scanpos = xsp;
                            if (this.jj_3R_83()) {
                                this.jj_scanpos = xsp;
                                if (this.jj_3R_84()) {
                                    this.jj_scanpos = xsp;
                                    if (this.jj_3R_85()) {
                                        this.jj_scanpos = xsp;
                                        this.lookingAhead = true;
                                        this.jj_semLA = this.isRegularForStatement();
                                        this.lookingAhead = false;
                                        if (!this.jj_semLA || this.jj_3R_86()) {
                                            this.jj_scanpos = xsp;
                                            if (this.jj_3R_87()) {
                                                this.jj_scanpos = xsp;
                                                if (this.jj_3R_88()) {
                                                    this.jj_scanpos = xsp;
                                                    if (this.jj_3R_89()) {
                                                        this.jj_scanpos = xsp;
                                                        if (this.jj_3R_90()) {
                                                            this.jj_scanpos = xsp;
                                                            if (this.jj_3R_91()) {
                                                                this.jj_scanpos = xsp;
                                                                if (this.jj_3R_92()) {
                                                                    this.jj_scanpos = xsp;
                                                                    if (this.jj_3R_93()) {
                                                                        return true;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private final boolean jj_3_22() {
        return this.jj_3R_41();
    }

    private final boolean jj_3R_156() {
        return this.jj_3R_59();
    }

    private final boolean jj_3R_111() {
        if (this.jj_3R_139()) {
            return true;
        }
        Token xsp = this.jj_scanpos;
        if (this.jj_3R_162()) {
            this.jj_scanpos = xsp;
        }
        return false;
    }

    private final boolean jj_3R_166() {
        if (this.jj_scan_token(77)) {
            return true;
        }
        return this.jj_scan_token(78);
    }

    private final boolean jj_3R_158() {
        if (this.jj_3R_72()) {
            return true;
        }
        Token xsp = this.jj_scanpos;
        if (this.jj_3_17()) {
            this.jj_scanpos = xsp;
        }
        return false;
    }

    private final boolean jj_3R_163() {
        Token xsp;
        if (this.jj_3R_166()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3R_166());
        this.jj_scanpos = xsp;
        return this.jj_3R_100();
    }

    private final boolean jj_3_20() {
        if (this.jj_scan_token(77)) {
            return true;
        }
        return this.jj_scan_token(78);
    }

    private final boolean jj_3R_157() {
        return this.jj_3R_155();
    }

    private final boolean jj_3_19() {
        if (this.jj_scan_token(77)) {
            return true;
        }
        if (this.jj_3R_40()) {
            return true;
        }
        return this.jj_scan_token(78);
    }

    private final boolean jj_3R_35() {
        Token xsp = this.jj_scanpos;
        if (this.jj_scan_token(82)) {
            this.jj_scanpos = xsp;
            if (this.jj_scan_token(121)) {
                this.jj_scanpos = xsp;
                if (this.jj_scan_token(122)) {
                    this.jj_scanpos = xsp;
                    if (this.jj_scan_token(128)) {
                        this.jj_scanpos = xsp;
                        if (this.jj_scan_token(119)) {
                            this.jj_scanpos = xsp;
                            if (this.jj_scan_token(120)) {
                                this.jj_scanpos = xsp;
                                if (this.jj_scan_token(123)) {
                                    this.jj_scanpos = xsp;
                                    if (this.jj_scan_token(127)) {
                                        this.jj_scanpos = xsp;
                                        if (this.jj_scan_token(125)) {
                                            this.jj_scanpos = xsp;
                                            if (this.jj_scan_token(129)) {
                                                this.jj_scanpos = xsp;
                                                if (this.jj_scan_token(130)) {
                                                    this.jj_scanpos = xsp;
                                                    if (this.jj_scan_token(131)) {
                                                        this.jj_scanpos = xsp;
                                                        if (this.jj_scan_token(132)) {
                                                            this.jj_scanpos = xsp;
                                                            if (this.jj_scan_token(133)) {
                                                                this.jj_scanpos = xsp;
                                                                if (this.jj_scan_token(134)) {
                                                                    return true;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private final boolean jj_3_21() {
        Token xsp;
        if (this.jj_3_19()) {
            return true;
        }
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3_19());
        this.jj_scanpos = xsp;
        do {
            xsp = this.jj_scanpos;
        } while (!this.jj_3_20());
        this.jj_scanpos = xsp;
        return false;
    }

    private final boolean jj_3R_155() {
        Token xsp = this.jj_scanpos;
        if (this.jj_3_21()) {
            this.jj_scanpos = xsp;
            if (this.jj_3R_163()) {
                return true;
            }
        }
        return false;
    }

    private final boolean jj_3R_115() {
        if (this.jj_scan_token(80)) {
            return true;
        }
        return this.jj_3R_29();
    }

    private final boolean jj_3_8() {
        if (this.jj_3R_34()) {
            return true;
        }
        return this.jj_3R_35();
    }

    private final boolean jj_3R_110() {
        if (this.jj_3R_34()) {
            return true;
        }
        if (this.jj_3R_35()) {
            return true;
        }
        return this.jj_3R_40();
    }

    private static void jj_la1_0() {
        jj_la1_0 = new int[]{1, 0x8000400, 0x8000400, 8192, 0x2000000, 0, 541214720, 0, 0, 0, 0, 0, 0, 608323584, 608323584, 0, 0, 675432448, 0x8000000, 0, 0, 541214720, 0, 541214720, 541214720, 0, 608323584, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 608323584, 0, 0, 608323584, 0x4000000, 0, 0, 608323584, 0, 0, 0x4000000, 0, 0, 0, 0x4000000, 0x4000000, 608323584, 0, 0, 0, 0, 0, 0, 610420736, 1074270208, 0, 0x108000, 0x108000, 0x800000, 742542336, 608323584, 608323584, 0x40000000, 608323584, 0, 0, 0, 0, 0, 608323584, 65536, 0x10000000};
    }

    private static void jj_la1_1() {
        jj_la1_1 = new int[]{0, 68892800, 68892800, 32, 0, 2, 33587280, 0x400000, 0, 65536, 0, 4, 0, 310412112, 310412112, 0, 0, 32848, 0, 0, 0, 32848, 0, 33587280, 32848, 0, 310412112, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 310412112, 0, 0, 310412112, 310379264, 0, 0, 310412112, 0, 0, 310379264, 0, 0, 0, 310379008, 0x800000, 310412112, 0, 0, 0, 256, 0, 0, 444891985, 19415040, 66564, 0, 0, 0, 379304912, 310412112, 310412112, 0, 310412112, 0, 0, 0, 0, 0, 310412112, 0, 0};
    }

    private static void jj_la1_2() {
        jj_la1_2 = new int[]{0, 0, 0, 0, 0, 0, 64, 0, 34816, 0, 131072, 0, 262144, 25168477, 25168477, 65536, 65536, 64, 0, 0x200000, 64, 64, 0x200000, 64, 0, 65536, 25166429, 262144, 0x2000000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0x8000000, 0x8000000, 0, -260571136, -260571136, 0, 0, 0, 0, 0, 0, 0, 25166429, 0x1800000, 0x1800000, 605, 25166429, 512, 0, 605, 512, 141312, 541, 64, 512, 141312, 29, 0, 25166429, 65536, 0x200000, 8704, 0, 8192, 8192, 25201245, 0, 32, 0, 0, 0, 25166429, 25166429, 25166429, 0, 25166429, 0x200000, 65536, 65536, 64, 64, 25166429, 0, 0};
    }

    private static void jj_la1_3() {
        jj_la1_3 = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 480, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 480, -1350565888, 0, 6, 6, 24, 24, 24576, 24576, 32768, 6144, 6144, 1, 1, 0, 0, 0, 0x7E0000, 0x7E0000, 384, 384, 67072, 67072, 384, 480, 0, 0, 0, 0, 0, 96, 0, 0, 0, 0, 0, 0, 0, 0, 0, 480, 0, 0, 0, 0, 0, 0, 480, 0, 0, 0, 0, 0, 480, 480, 480, 0, 480, 0, 0, 0, 0, 0, 480, 0, 0};
    }

    private static void jj_la1_4() {
        jj_la1_4 = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 127, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    }

    public Parser(InputStream stream) {
        int i;
        this.jj_input_stream = new JavaCharStream(stream, 1, 1);
        this.token_source = new ParserTokenManager(this.jj_input_stream);
        this.token = new Token();
        this.jj_ntk = -1;
        this.jj_gen = 0;
        for (i = 0; i < 92; ++i) {
            this.jj_la1[i] = -1;
        }
        for (i = 0; i < this.jj_2_rtns.length; ++i) {
            this.jj_2_rtns[i] = new JJCalls();
        }
    }

    public void ReInit(InputStream stream) {
        int i;
        this.jj_input_stream.ReInit(stream, 1, 1);
        this.token_source.ReInit(this.jj_input_stream);
        this.token = new Token();
        this.jj_ntk = -1;
        this.jjtree.reset();
        this.jj_gen = 0;
        for (i = 0; i < 92; ++i) {
            this.jj_la1[i] = -1;
        }
        for (i = 0; i < this.jj_2_rtns.length; ++i) {
            this.jj_2_rtns[i] = new JJCalls();
        }
    }

    public Parser(Reader stream) {
        int i;
        this.jj_input_stream = new JavaCharStream(stream, 1, 1);
        this.token_source = new ParserTokenManager(this.jj_input_stream);
        this.token = new Token();
        this.jj_ntk = -1;
        this.jj_gen = 0;
        for (i = 0; i < 92; ++i) {
            this.jj_la1[i] = -1;
        }
        for (i = 0; i < this.jj_2_rtns.length; ++i) {
            this.jj_2_rtns[i] = new JJCalls();
        }
    }

    public void ReInit(Reader stream) {
        int i;
        this.jj_input_stream.ReInit(stream, 1, 1);
        this.token_source.ReInit(this.jj_input_stream);
        this.token = new Token();
        this.jj_ntk = -1;
        this.jjtree.reset();
        this.jj_gen = 0;
        for (i = 0; i < 92; ++i) {
            this.jj_la1[i] = -1;
        }
        for (i = 0; i < this.jj_2_rtns.length; ++i) {
            this.jj_2_rtns[i] = new JJCalls();
        }
    }

    public Parser(ParserTokenManager tm) {
        int i;
        this.token_source = tm;
        this.token = new Token();
        this.jj_ntk = -1;
        this.jj_gen = 0;
        for (i = 0; i < 92; ++i) {
            this.jj_la1[i] = -1;
        }
        for (i = 0; i < this.jj_2_rtns.length; ++i) {
            this.jj_2_rtns[i] = new JJCalls();
        }
    }

    public void ReInit(ParserTokenManager tm) {
        int i;
        this.token_source = tm;
        this.token = new Token();
        this.jj_ntk = -1;
        this.jjtree.reset();
        this.jj_gen = 0;
        for (i = 0; i < 92; ++i) {
            this.jj_la1[i] = -1;
        }
        for (i = 0; i < this.jj_2_rtns.length; ++i) {
            this.jj_2_rtns[i] = new JJCalls();
        }
    }

    private final Token jj_consume_token(int kind) throws ParseException {
        Token oldToken = this.token;
        this.token = oldToken.next != null ? this.token.next : (this.token.next = this.token_source.getNextToken());
        this.jj_ntk = -1;
        if (this.token.kind == kind) {
            ++this.jj_gen;
            if (++this.jj_gc > 100) {
                this.jj_gc = 0;
                for (int i = 0; i < this.jj_2_rtns.length; ++i) {
                    JJCalls c = this.jj_2_rtns[i];
                    while (c != null) {
                        if (c.gen < this.jj_gen) {
                            c.first = null;
                        }
                        c = c.next;
                    }
                }
            }
            return this.token;
        }
        this.token = oldToken;
        this.jj_kind = kind;
        throw this.generateParseException();
    }

    private final boolean jj_scan_token(int kind) {
        if (this.jj_scanpos == this.jj_lastpos) {
            --this.jj_la;
            if (this.jj_scanpos.next == null) {
                this.jj_scanpos = this.jj_scanpos.next = this.token_source.getNextToken();
                this.jj_lastpos = this.jj_scanpos.next;
            } else {
                this.jj_lastpos = this.jj_scanpos = this.jj_scanpos.next;
            }
        } else {
            this.jj_scanpos = this.jj_scanpos.next;
        }
        if (this.jj_rescan) {
            int i = 0;
            Token tok = this.token;
            while (tok != null && tok != this.jj_scanpos) {
                ++i;
                tok = tok.next;
            }
            if (tok != null) {
                this.jj_add_error_token(kind, i);
            }
        }
        if (this.jj_scanpos.kind != kind) {
            return true;
        }
        if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
            throw this.jj_ls;
        }
        return false;
    }

    public final Token getNextToken() {
        this.token = this.token.next != null ? this.token.next : (this.token.next = this.token_source.getNextToken());
        this.jj_ntk = -1;
        ++this.jj_gen;
        return this.token;
    }

    public final Token getToken(int index) {
        Token t = this.lookingAhead ? this.jj_scanpos : this.token;
        for (int i = 0; i < index; ++i) {
            t = t.next != null ? t.next : (t.next = this.token_source.getNextToken());
        }
        return t;
    }

    private final int jj_ntk() {
        this.jj_nt = this.token.next;
        if (this.jj_nt == null) {
            this.token.next = this.token_source.getNextToken();
            this.jj_ntk = this.token.next.kind;
            return this.jj_ntk;
        }
        this.jj_ntk = this.jj_nt.kind;
        return this.jj_ntk;
    }

    private void jj_add_error_token(int kind, int pos) {
        if (pos >= 100) {
            return;
        }
        if (pos == this.jj_endpos + 1) {
            this.jj_lasttokens[this.jj_endpos++] = kind;
        } else if (this.jj_endpos != 0) {
            this.jj_expentry = new int[this.jj_endpos];
            for (int i = 0; i < this.jj_endpos; ++i) {
                this.jj_expentry[i] = this.jj_lasttokens[i];
            }
            boolean exists = false;
            Enumeration e = this.jj_expentries.elements();
            while (e.hasMoreElements()) {
                int[] oldentry = (int[])e.nextElement();
                if (oldentry.length != this.jj_expentry.length) continue;
                exists = true;
                for (int i = 0; i < this.jj_expentry.length; ++i) {
                    if (oldentry[i] == this.jj_expentry[i]) continue;
                    exists = false;
                    break;
                }
                if (!exists) continue;
                break;
            }
            if (!exists) {
                this.jj_expentries.addElement(this.jj_expentry);
            }
            if (pos != 0) {
                this.jj_endpos = pos;
                this.jj_lasttokens[this.jj_endpos - 1] = kind;
            }
        }
    }

    public ParseException generateParseException() {
        int i;
        this.jj_expentries.removeAllElements();
        boolean[] la1tokens = new boolean[135];
        for (i = 0; i < 135; ++i) {
            la1tokens[i] = false;
        }
        if (this.jj_kind >= 0) {
            la1tokens[this.jj_kind] = true;
            this.jj_kind = -1;
        }
        for (i = 0; i < 92; ++i) {
            if (this.jj_la1[i] != this.jj_gen) continue;
            for (int j = 0; j < 32; ++j) {
                if ((jj_la1_0[i] & 1 << j) != 0) {
                    la1tokens[j] = true;
                }
                if ((jj_la1_1[i] & 1 << j) != 0) {
                    la1tokens[32 + j] = true;
                }
                if ((jj_la1_2[i] & 1 << j) != 0) {
                    la1tokens[64 + j] = true;
                }
                if ((jj_la1_3[i] & 1 << j) != 0) {
                    la1tokens[96 + j] = true;
                }
                if ((jj_la1_4[i] & 1 << j) == 0) continue;
                la1tokens[128 + j] = true;
            }
        }
        for (i = 0; i < 135; ++i) {
            if (!la1tokens[i]) continue;
            this.jj_expentry = new int[1];
            this.jj_expentry[0] = i;
            this.jj_expentries.addElement(this.jj_expentry);
        }
        this.jj_endpos = 0;
        this.jj_rescan_token();
        this.jj_add_error_token(0, 0);
        int[][] exptokseq = new int[this.jj_expentries.size()][];
        for (int i2 = 0; i2 < this.jj_expentries.size(); ++i2) {
            exptokseq[i2] = (int[])this.jj_expentries.elementAt(i2);
        }
        return new ParseException(this.token, exptokseq, tokenImage);
    }

    public final void enable_tracing() {
    }

    public final void disable_tracing() {
    }

    private final void jj_rescan_token() {
        this.jj_rescan = true;
        for (int i = 0; i < 31; ++i) {
            JJCalls p = this.jj_2_rtns[i];
            do {
                if (p.gen <= this.jj_gen) continue;
                this.jj_la = p.arg;
                this.jj_lastpos = this.jj_scanpos = p.first;
                switch (i) {
                    case 0: {
                        this.jj_3_1();
                        break;
                    }
                    case 1: {
                        this.jj_3_2();
                        break;
                    }
                    case 2: {
                        this.jj_3_3();
                        break;
                    }
                    case 3: {
                        this.jj_3_4();
                        break;
                    }
                    case 4: {
                        this.jj_3_5();
                        break;
                    }
                    case 5: {
                        this.jj_3_6();
                        break;
                    }
                    case 6: {
                        this.jj_3_7();
                        break;
                    }
                    case 7: {
                        this.jj_3_8();
                        break;
                    }
                    case 8: {
                        this.jj_3_9();
                        break;
                    }
                    case 9: {
                        this.jj_3_10();
                        break;
                    }
                    case 10: {
                        this.jj_3_11();
                        break;
                    }
                    case 11: {
                        this.jj_3_12();
                        break;
                    }
                    case 12: {
                        this.jj_3_13();
                        break;
                    }
                    case 13: {
                        this.jj_3_14();
                        break;
                    }
                    case 14: {
                        this.jj_3_15();
                        break;
                    }
                    case 15: {
                        this.jj_3_16();
                        break;
                    }
                    case 16: {
                        this.jj_3_17();
                        break;
                    }
                    case 17: {
                        this.jj_3_18();
                        break;
                    }
                    case 18: {
                        this.jj_3_19();
                        break;
                    }
                    case 19: {
                        this.jj_3_20();
                        break;
                    }
                    case 20: {
                        this.jj_3_21();
                        break;
                    }
                    case 21: {
                        this.jj_3_22();
                        break;
                    }
                    case 22: {
                        this.jj_3_23();
                        break;
                    }
                    case 23: {
                        this.jj_3_24();
                        break;
                    }
                    case 24: {
                        this.jj_3_25();
                        break;
                    }
                    case 25: {
                        this.jj_3_26();
                        break;
                    }
                    case 26: {
                        this.jj_3_27();
                        break;
                    }
                    case 27: {
                        this.jj_3_28();
                        break;
                    }
                    case 28: {
                        this.jj_3_29();
                        break;
                    }
                    case 29: {
                        this.jj_3_30();
                        break;
                    }
                    case 30: {
                        this.jj_3_31();
                    }
                }
            } while ((p = p.next) != null);
        }
        this.jj_rescan = false;
    }

    private final void jj_save(int index, int xla) {
        JJCalls p = this.jj_2_rtns[index];
        while (p.gen > this.jj_gen) {
            if (p.next == null) {
                p = p.next = new JJCalls();
                break;
            }
            p = p.next;
        }
        p.gen = this.jj_gen + xla - this.jj_la;
        p.first = this.token;
        p.arg = xla;
    }

    static {
        Parser.jj_la1_0();
        Parser.jj_la1_1();
        Parser.jj_la1_2();
        Parser.jj_la1_3();
        Parser.jj_la1_4();
    }

    static final class JJCalls {
        int gen;
        Token first;
        int arg;
        JJCalls next;

        JJCalls() {
        }
    }

    private static final class LookaheadSuccess
    extends Error {
        private LookaheadSuccess() {
        }
    }
}

