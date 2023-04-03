/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.javascript;

import java.io.IOException;
import java.io.Reader;
import org.mozilla.javascript.Kit;
import org.mozilla.javascript.ObjToIntMap;
import org.mozilla.javascript.Parser;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Token;

class TokenStream {
    private static final int EOF_CHAR = -1;
    private static final char BYTE_ORDER_MARK = '\ufeff';
    private boolean dirtyLine;
    String regExpFlags;
    private String string = "";
    private double number;
    private boolean isBinary;
    private boolean isOldOctal;
    private boolean isOctal;
    private boolean isHex;
    private int quoteChar;
    private char[] stringBuffer = new char[128];
    private int stringBufferTop;
    private ObjToIntMap allStrings = new ObjToIntMap(50);
    private final int[] ungetBuffer = new int[3];
    private int ungetCursor;
    private boolean hitEOF = false;
    private int lineStart = 0;
    private int lineEndChar = -1;
    int lineno;
    private String sourceString;
    private Reader sourceReader;
    private char[] sourceBuffer;
    private int sourceEnd;
    int sourceCursor;
    int cursor;
    int tokenBeg;
    int tokenEnd;
    Token.CommentType commentType;
    private boolean xmlIsAttribute;
    private boolean xmlIsTagContent;
    private int xmlOpenTagsCount;
    private Parser parser;
    private String commentPrefix = "";
    private int commentCursor = -1;

    TokenStream(Parser parser, Reader sourceReader, String sourceString, int lineno) {
        this.parser = parser;
        this.lineno = lineno;
        if (sourceReader != null) {
            if (sourceString != null) {
                Kit.codeBug();
            }
            this.sourceReader = sourceReader;
            this.sourceBuffer = new char[512];
            this.sourceEnd = 0;
        } else {
            if (sourceString == null) {
                Kit.codeBug();
            }
            this.sourceString = sourceString;
            this.sourceEnd = sourceString.length();
        }
        this.cursor = 0;
        this.sourceCursor = 0;
    }

    String tokenToString(int token) {
        return "";
    }

    static boolean isKeyword(String s, int version, boolean isStrict) {
        return 0 != TokenStream.stringToKeyword(s, version, isStrict);
    }

    private static int stringToKeyword(String name, int version, boolean isStrict) {
        if (version < 200) {
            return TokenStream.stringToKeywordForJS(name);
        }
        return TokenStream.stringToKeywordForES(name, isStrict);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static int stringToKeywordForJS(String name) {
        int Id_break = 121;
        int Id_case = 116;
        int Id_continue = 122;
        int Id_default = 117;
        int Id_delete = 31;
        int Id_do = 119;
        int Id_else = 114;
        int Id_export = 128;
        int Id_false = 44;
        int Id_for = 120;
        int Id_function = 110;
        int Id_if = 113;
        int Id_in = 52;
        int Id_let = 154;
        int Id_new = 30;
        int Id_null = 42;
        int Id_return = 4;
        int Id_switch = 115;
        int Id_this = 43;
        int Id_true = 45;
        int Id_typeof = 32;
        int Id_var = 123;
        int Id_void = 127;
        int Id_while = 118;
        int Id_with = 124;
        int Id_yield = 73;
        int Id_abstract = 128;
        int Id_boolean = 128;
        int Id_byte = 128;
        int Id_catch = 125;
        int Id_char = 128;
        int Id_class = 128;
        int Id_const = 155;
        int Id_debugger = 161;
        int Id_double = 128;
        int Id_enum = 128;
        int Id_extends = 128;
        int Id_final = 128;
        int Id_finally = 126;
        int Id_float = 128;
        int Id_goto = 128;
        int Id_implements = 128;
        int Id_import = 128;
        int Id_instanceof = 53;
        int Id_int = 128;
        int Id_interface = 128;
        int Id_long = 128;
        int Id_native = 128;
        int Id_package = 128;
        int Id_private = 128;
        int Id_protected = 128;
        int Id_public = 128;
        int Id_short = 128;
        int Id_static = 128;
        int Id_super = 128;
        int Id_synchronized = 128;
        int Id_throw = 50;
        int Id_throws = 128;
        int Id_transient = 128;
        int Id_try = 82;
        int Id_volatile = 128;
        String s = name;
        int id = 0;
        String X = null;
        block0 : switch (s.length()) {
            case 2: {
                char c = s.charAt(1);
                if (c == 'f') {
                    if (s.charAt(0) != 'i') break;
                    return 113;
                }
                if (c == 'n') {
                    if (s.charAt(0) != 'i') break;
                    return 52;
                }
                if (c != 'o' || s.charAt(0) != 'd') break;
                return 119;
            }
            case 3: {
                switch (s.charAt(0)) {
                    case 'f': {
                        if (s.charAt(2) != 'r' || s.charAt(1) != 'o') break;
                        return 120;
                    }
                    case 'i': {
                        if (s.charAt(2) != 't' || s.charAt(1) != 'n') break;
                        return 128;
                    }
                    case 'l': {
                        if (s.charAt(2) != 't' || s.charAt(1) != 'e') break;
                        return 154;
                    }
                    case 'n': {
                        if (s.charAt(2) != 'w' || s.charAt(1) != 'e') break;
                        return 30;
                    }
                    case 't': {
                        if (s.charAt(2) != 'y' || s.charAt(1) != 'r') break;
                        return 82;
                    }
                    case 'v': {
                        if (s.charAt(2) != 'r' || s.charAt(1) != 'a') break;
                        return 123;
                    }
                }
                break;
            }
            case 4: {
                switch (s.charAt(0)) {
                    case 'b': {
                        X = "byte";
                        id = 128;
                        break;
                    }
                    case 'c': {
                        char c = s.charAt(3);
                        if (c == 'e') {
                            if (s.charAt(2) != 's' || s.charAt(1) != 'a') break;
                            return 116;
                        }
                        if (c != 'r' || s.charAt(2) != 'a' || s.charAt(1) != 'h') break;
                        return 128;
                    }
                    case 'e': {
                        char c = s.charAt(3);
                        if (c == 'e') {
                            if (s.charAt(2) != 's' || s.charAt(1) != 'l') break;
                            return 114;
                        }
                        if (c != 'm' || s.charAt(2) != 'u' || s.charAt(1) != 'n') break;
                        return 128;
                    }
                    case 'g': {
                        X = "goto";
                        id = 128;
                        break;
                    }
                    case 'l': {
                        X = "long";
                        id = 128;
                        break;
                    }
                    case 'n': {
                        X = "null";
                        id = 42;
                        break;
                    }
                    case 't': {
                        char c = s.charAt(3);
                        if (c == 'e') {
                            if (s.charAt(2) != 'u' || s.charAt(1) != 'r') break;
                            return 45;
                        }
                        if (c != 's' || s.charAt(2) != 'i' || s.charAt(1) != 'h') break;
                        return 43;
                    }
                    case 'v': {
                        X = "void";
                        id = 127;
                        break;
                    }
                    case 'w': {
                        X = "with";
                        id = 124;
                        break;
                    }
                }
                break;
            }
            case 5: {
                switch (s.charAt(2)) {
                    case 'a': {
                        X = "class";
                        id = 128;
                        break;
                    }
                    case 'e': {
                        char c = s.charAt(0);
                        if (c == 'b') {
                            X = "break";
                            id = 121;
                            break;
                        }
                        if (c != 'y') break;
                        X = "yield";
                        id = 73;
                        break;
                    }
                    case 'i': {
                        X = "while";
                        id = 118;
                        break;
                    }
                    case 'l': {
                        X = "false";
                        id = 44;
                        break;
                    }
                    case 'n': {
                        char c = s.charAt(0);
                        if (c == 'c') {
                            X = "const";
                            id = 155;
                            break;
                        }
                        if (c != 'f') break;
                        X = "final";
                        id = 128;
                        break;
                    }
                    case 'o': {
                        char c = s.charAt(0);
                        if (c == 'f') {
                            X = "float";
                            id = 128;
                            break;
                        }
                        if (c != 's') break;
                        X = "short";
                        id = 128;
                        break;
                    }
                    case 'p': {
                        X = "super";
                        id = 128;
                        break;
                    }
                    case 'r': {
                        X = "throw";
                        id = 50;
                        break;
                    }
                    case 't': {
                        X = "catch";
                        id = 125;
                        break;
                    }
                }
                break;
            }
            case 6: {
                switch (s.charAt(1)) {
                    case 'a': {
                        X = "native";
                        id = 128;
                        break;
                    }
                    case 'e': {
                        char c = s.charAt(0);
                        if (c == 'd') {
                            X = "delete";
                            id = 31;
                            break;
                        }
                        if (c != 'r') break;
                        X = "return";
                        id = 4;
                        break;
                    }
                    case 'h': {
                        X = "throws";
                        id = 128;
                        break;
                    }
                    case 'm': {
                        X = "import";
                        id = 128;
                        break;
                    }
                    case 'o': {
                        X = "double";
                        id = 128;
                        break;
                    }
                    case 't': {
                        X = "static";
                        id = 128;
                        break;
                    }
                    case 'u': {
                        X = "public";
                        id = 128;
                        break;
                    }
                    case 'w': {
                        X = "switch";
                        id = 115;
                        break;
                    }
                    case 'x': {
                        X = "export";
                        id = 128;
                        break;
                    }
                    case 'y': {
                        X = "typeof";
                        id = 32;
                        break;
                    }
                }
                break;
            }
            case 7: {
                switch (s.charAt(1)) {
                    case 'a': {
                        X = "package";
                        id = 128;
                        break block0;
                    }
                    case 'e': {
                        X = "default";
                        id = 117;
                        break block0;
                    }
                    case 'i': {
                        X = "finally";
                        id = 126;
                        break block0;
                    }
                    case 'o': {
                        X = "boolean";
                        id = 128;
                        break block0;
                    }
                    case 'r': {
                        X = "private";
                        id = 128;
                        break block0;
                    }
                    case 'x': {
                        X = "extends";
                        id = 128;
                        break block0;
                    }
                }
                break;
            }
            case 8: {
                switch (s.charAt(0)) {
                    case 'a': {
                        X = "abstract";
                        id = 128;
                        break block0;
                    }
                    case 'c': {
                        X = "continue";
                        id = 122;
                        break block0;
                    }
                    case 'd': {
                        X = "debugger";
                        id = 161;
                        break block0;
                    }
                    case 'f': {
                        X = "function";
                        id = 110;
                        break block0;
                    }
                    case 'v': {
                        X = "volatile";
                        id = 128;
                        break block0;
                    }
                }
                break;
            }
            case 9: {
                char c = s.charAt(0);
                if (c == 'i') {
                    X = "interface";
                    id = 128;
                    break;
                }
                if (c == 'p') {
                    X = "protected";
                    id = 128;
                    break;
                }
                if (c != 't') break;
                X = "transient";
                id = 128;
                break;
            }
            case 10: {
                char c = s.charAt(1);
                if (c == 'm') {
                    X = "implements";
                    id = 128;
                    break;
                }
                if (c != 'n') break;
                X = "instanceof";
                id = 53;
                break;
            }
            case 12: {
                X = "synchronized";
                id = 128;
            }
        }
        if (X != null && X != s && !X.equals(s)) {
            return 0;
        }
        if (id != 0) return id & 0xFF;
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static int stringToKeywordForES(String name, boolean isStrict) {
        int Id_break = 121;
        int Id_case = 116;
        int Id_catch = 125;
        int Id_class = 128;
        int Id_const = 155;
        int Id_continue = 122;
        int Id_debugger = 161;
        int Id_default = 117;
        int Id_delete = 31;
        int Id_do = 119;
        int Id_else = 114;
        int Id_export = 128;
        int Id_extends = 128;
        int Id_finally = 126;
        int Id_for = 120;
        int Id_function = 110;
        int Id_if = 113;
        int Id_import = 128;
        int Id_in = 52;
        int Id_instanceof = 53;
        int Id_new = 30;
        int Id_return = 4;
        int Id_super = 128;
        int Id_switch = 115;
        int Id_this = 43;
        int Id_throw = 50;
        int Id_try = 82;
        int Id_typeof = 32;
        int Id_var = 123;
        int Id_void = 127;
        int Id_while = 118;
        int Id_with = 124;
        int Id_yield = 73;
        int Id_await = 128;
        int Id_enum = 128;
        int Id_implements = 128;
        int Id_interface = 128;
        int Id_package = 128;
        int Id_private = 128;
        int Id_protected = 128;
        int Id_public = 128;
        int Id_false = 44;
        int Id_null = 42;
        int Id_true = 45;
        int Id_let = 154;
        int Id_static = 128;
        String s = name;
        int id = 0;
        String X = null;
        block0 : switch (s.length()) {
            case 2: {
                char c = s.charAt(1);
                if (c == 'f') {
                    if (s.charAt(0) != 'i') break;
                    return 113;
                }
                if (c == 'n') {
                    if (s.charAt(0) != 'i') break;
                    return 52;
                }
                if (c != 'o' || s.charAt(0) != 'd') break;
                return 119;
            }
            case 3: {
                switch (s.charAt(0)) {
                    case 'f': {
                        if (s.charAt(2) != 'r' || s.charAt(1) != 'o') break;
                        return 120;
                    }
                    case 'l': {
                        if (s.charAt(2) != 't' || s.charAt(1) != 'e') break;
                        return 154;
                    }
                    case 'n': {
                        if (s.charAt(2) != 'w' || s.charAt(1) != 'e') break;
                        return 30;
                    }
                    case 't': {
                        if (s.charAt(2) != 'y' || s.charAt(1) != 'r') break;
                        return 82;
                    }
                    case 'v': {
                        if (s.charAt(2) != 'r' || s.charAt(1) != 'a') break;
                        return 123;
                    }
                }
                break;
            }
            case 4: {
                switch (s.charAt(0)) {
                    case 'c': {
                        char c = s.charAt(3);
                        if (c != 'e' || s.charAt(2) != 's' || s.charAt(1) != 'a') break;
                        return 116;
                    }
                    case 'e': {
                        char c = s.charAt(3);
                        if (c == 'e') {
                            if (s.charAt(2) != 's' || s.charAt(1) != 'l') break;
                            return 114;
                        }
                        if (c != 'm' || s.charAt(2) != 'u' || s.charAt(1) != 'n') break;
                        return 128;
                    }
                    case 'n': {
                        X = "null";
                        id = 42;
                        break;
                    }
                    case 't': {
                        char c = s.charAt(3);
                        if (c == 'e') {
                            if (s.charAt(2) != 'u' || s.charAt(1) != 'r') break;
                            return 45;
                        }
                        if (c != 's' || s.charAt(2) != 'i' || s.charAt(1) != 'h') break;
                        return 43;
                    }
                    case 'v': {
                        X = "void";
                        id = 127;
                        break;
                    }
                    case 'w': {
                        X = "with";
                        id = 124;
                        break;
                    }
                }
                break;
            }
            case 5: {
                switch (s.charAt(2)) {
                    case 'a': {
                        char c = s.charAt(0);
                        if (c == 'c') {
                            X = "class";
                            id = 128;
                            break;
                        }
                        if (c != 'a') break;
                        X = "await";
                        id = 128;
                        break;
                    }
                    case 'e': {
                        char c = s.charAt(0);
                        if (c == 'b') {
                            X = "break";
                            id = 121;
                            break;
                        }
                        if (c != 'y') break;
                        X = "yield";
                        id = 73;
                        break;
                    }
                    case 'i': {
                        X = "while";
                        id = 118;
                        break;
                    }
                    case 'l': {
                        X = "false";
                        id = 44;
                        break;
                    }
                    case 'n': {
                        X = "const";
                        id = 155;
                        break;
                    }
                    case 'p': {
                        X = "super";
                        id = 128;
                        break;
                    }
                    case 'r': {
                        X = "throw";
                        id = 50;
                        break;
                    }
                    case 't': {
                        X = "catch";
                        id = 125;
                        break;
                    }
                }
                break;
            }
            case 6: {
                switch (s.charAt(1)) {
                    case 'e': {
                        char c = s.charAt(0);
                        if (c == 'd') {
                            X = "delete";
                            id = 31;
                            break;
                        }
                        if (c != 'r') break;
                        X = "return";
                        id = 4;
                        break;
                    }
                    case 'm': {
                        X = "import";
                        id = 128;
                        break;
                    }
                    case 't': {
                        if (isStrict) {
                            X = "static";
                            id = 128;
                            break;
                        }
                    }
                    case 'u': {
                        if (isStrict) {
                            X = "public";
                            id = 128;
                            break;
                        }
                    }
                    case 'w': {
                        X = "switch";
                        id = 115;
                        break;
                    }
                    case 'x': {
                        X = "export";
                        id = 128;
                        break;
                    }
                    case 'y': {
                        X = "typeof";
                        id = 32;
                        break;
                    }
                }
                break;
            }
            case 7: {
                switch (s.charAt(1)) {
                    case 'a': {
                        if (isStrict) {
                            X = "package";
                            id = 128;
                            break block0;
                        }
                    }
                    case 'e': {
                        X = "default";
                        id = 117;
                        break block0;
                    }
                    case 'i': {
                        X = "finally";
                        id = 126;
                        break block0;
                    }
                    case 'r': {
                        if (isStrict) {
                            X = "private";
                            id = 128;
                            break block0;
                        }
                    }
                    case 'x': {
                        X = "extends";
                        id = 128;
                        break block0;
                    }
                }
                break;
            }
            case 8: {
                switch (s.charAt(0)) {
                    case 'c': {
                        X = "continue";
                        id = 122;
                        break block0;
                    }
                    case 'd': {
                        X = "debugger";
                        id = 161;
                        break block0;
                    }
                    case 'f': {
                        X = "function";
                        id = 110;
                        break block0;
                    }
                }
                break;
            }
            case 9: {
                char c = s.charAt(0);
                if (c == 'i' && isStrict) {
                    X = "interface";
                    id = 128;
                    break;
                }
                if (c != 'p' || !isStrict) break;
                X = "protected";
                id = 128;
                break;
            }
            case 10: {
                char c = s.charAt(1);
                if (c == 'm' && isStrict) {
                    X = "implements";
                    id = 128;
                    break;
                }
                if (c != 'n') break;
                X = "instanceof";
                id = 53;
            }
        }
        if (X != null && X != s && !X.equals(s)) {
            return 0;
        }
        if (id != 0) return id & 0xFF;
        return 0;
    }

    final String getSourceString() {
        return this.sourceString;
    }

    final int getLineno() {
        return this.lineno;
    }

    final String getString() {
        return this.string;
    }

    final char getQuoteChar() {
        return (char)this.quoteChar;
    }

    final double getNumber() {
        return this.number;
    }

    final boolean isNumberBinary() {
        return this.isBinary;
    }

    final boolean isNumberOldOctal() {
        return this.isOldOctal;
    }

    final boolean isNumberOctal() {
        return this.isOctal;
    }

    final boolean isNumberHex() {
        return this.isHex;
    }

    final boolean eof() {
        return this.hitEOF;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final int getToken() throws IOException {
        boolean lookForSlash;
        int c;
        block142: {
            block147: {
                double dval;
                String numString;
                boolean isEmpty;
                int base;
                block146: {
                    block145: {
                        boolean isUnicodeEscapeStart;
                        block143: {
                            block144: {
                                boolean es6;
                                boolean identifierStart;
                                do {
                                    if ((c = this.getChar()) == -1) {
                                        this.tokenBeg = this.cursor - 1;
                                        this.tokenEnd = this.cursor;
                                        return 0;
                                    }
                                    if (c != 10) continue;
                                    this.dirtyLine = false;
                                    this.tokenBeg = this.cursor - 1;
                                    this.tokenEnd = this.cursor;
                                    return 1;
                                } while (TokenStream.isJSSpace(c));
                                if (c != 45) {
                                    this.dirtyLine = true;
                                }
                                this.tokenBeg = this.cursor - 1;
                                this.tokenEnd = this.cursor;
                                if (c == 64) {
                                    return 148;
                                }
                                isUnicodeEscapeStart = false;
                                if (c == 92) {
                                    c = this.getChar();
                                    if (c == 117) {
                                        identifierStart = true;
                                        isUnicodeEscapeStart = true;
                                        this.stringBufferTop = 0;
                                    } else {
                                        identifierStart = false;
                                        this.ungetChar(c);
                                        c = 92;
                                    }
                                } else {
                                    identifierStart = Character.isJavaIdentifierStart((char)c);
                                    if (identifierStart) {
                                        this.stringBufferTop = 0;
                                        this.addToString(c);
                                    }
                                }
                                if (identifierStart) break block143;
                                if (!TokenStream.isDigit(c) && (c != 46 || !TokenStream.isDigit(this.peekChar()))) break block144;
                                this.stringBufferTop = 0;
                                base = 10;
                                this.isBinary = false;
                                this.isOctal = false;
                                this.isOldOctal = false;
                                this.isHex = false;
                                boolean bl = es6 = this.parser.compilerEnv.getLanguageVersion() >= 200;
                                if (c == 48) {
                                    c = this.getChar();
                                    if (c == 120 || c == 88) {
                                        base = 16;
                                        this.isHex = true;
                                        c = this.getChar();
                                    } else if (es6 && (c == 111 || c == 79)) {
                                        base = 8;
                                        this.isOctal = true;
                                        c = this.getChar();
                                    } else if (es6 && (c == 98 || c == 66)) {
                                        base = 2;
                                        this.isBinary = true;
                                        c = this.getChar();
                                    } else if (TokenStream.isDigit(c)) {
                                        base = 8;
                                        this.isOldOctal = true;
                                    } else {
                                        this.addToString(48);
                                    }
                                }
                                isEmpty = true;
                                if (base != 16) break block145;
                                while (0 <= Kit.xDigitToInt(c, 0)) {
                                    this.addToString(c);
                                    c = this.getChar();
                                    isEmpty = false;
                                }
                                break block146;
                            }
                            if (c == 34 || c == 39 || c == 96) break block147;
                            switch (c) {
                                case 59: {
                                    return 83;
                                }
                                case 91: {
                                    return 84;
                                }
                                case 93: {
                                    return 85;
                                }
                                case 123: {
                                    return 86;
                                }
                                case 125: {
                                    return 87;
                                }
                                case 40: {
                                    return 88;
                                }
                                case 41: {
                                    return 89;
                                }
                                case 44: {
                                    return 90;
                                }
                                case 63: {
                                    return 103;
                                }
                                case 58: {
                                    if (this.matchChar(58)) {
                                        return 145;
                                    }
                                    return 104;
                                }
                                case 46: {
                                    if (this.matchChar(46)) {
                                        return 144;
                                    }
                                    if (this.matchChar(40)) {
                                        return 147;
                                    }
                                    return 109;
                                }
                                case 124: {
                                    if (this.matchChar(124)) {
                                        return 105;
                                    }
                                    if (this.matchChar(61)) {
                                        return 92;
                                    }
                                    return 9;
                                }
                                case 94: {
                                    if (this.matchChar(61)) {
                                        return 93;
                                    }
                                    return 10;
                                }
                                case 38: {
                                    if (this.matchChar(38)) {
                                        return 106;
                                    }
                                    if (this.matchChar(61)) {
                                        return 94;
                                    }
                                    return 11;
                                }
                                case 61: {
                                    if (this.matchChar(61)) {
                                        if (this.matchChar(61)) {
                                            return 46;
                                        }
                                        return 12;
                                    }
                                    if (this.matchChar(62)) {
                                        return 165;
                                    }
                                    return 91;
                                }
                                case 33: {
                                    if (!this.matchChar(61)) {
                                        return 26;
                                    }
                                    if (this.matchChar(61)) {
                                        return 47;
                                    }
                                    return 13;
                                }
                                case 60: {
                                    if (this.matchChar(33)) {
                                        if (this.matchChar(45)) {
                                            if (this.matchChar(45)) {
                                                this.tokenBeg = this.cursor - 4;
                                                this.skipLine();
                                                this.commentType = Token.CommentType.HTML;
                                                return 162;
                                            }
                                            this.ungetCharIgnoreLineEnd(45);
                                        }
                                        this.ungetCharIgnoreLineEnd(33);
                                    }
                                    if (this.matchChar(60)) {
                                        if (this.matchChar(61)) {
                                            return 95;
                                        }
                                        return 18;
                                    }
                                    if (this.matchChar(61)) {
                                        return 15;
                                    }
                                    return 14;
                                }
                                case 62: {
                                    if (this.matchChar(62)) {
                                        if (this.matchChar(62)) {
                                            if (this.matchChar(61)) {
                                                return 97;
                                            }
                                            return 20;
                                        }
                                        if (this.matchChar(61)) {
                                            return 96;
                                        }
                                        return 19;
                                    }
                                    if (this.matchChar(61)) {
                                        return 17;
                                    }
                                    return 16;
                                }
                                case 42: {
                                    if (this.matchChar(61)) {
                                        return 100;
                                    }
                                    return 23;
                                }
                                case 47: {
                                    this.markCommentStart();
                                    if (this.matchChar(47)) {
                                        this.tokenBeg = this.cursor - 2;
                                        this.skipLine();
                                        this.commentType = Token.CommentType.LINE;
                                        return 162;
                                    }
                                    if (this.matchChar(42)) {
                                        lookForSlash = false;
                                        this.tokenBeg = this.cursor - 2;
                                        if (this.matchChar(42)) {
                                            lookForSlash = true;
                                            this.commentType = Token.CommentType.JSDOC;
                                            break block142;
                                        } else {
                                            this.commentType = Token.CommentType.BLOCK_COMMENT;
                                        }
                                        break block142;
                                    } else {
                                        if (this.matchChar(61)) {
                                            return 101;
                                        }
                                        return 24;
                                    }
                                }
                                case 37: {
                                    if (this.matchChar(61)) {
                                        return 102;
                                    }
                                    return 25;
                                }
                                case 126: {
                                    return 27;
                                }
                                case 43: {
                                    if (this.matchChar(61)) {
                                        return 98;
                                    }
                                    if (this.matchChar(43)) {
                                        return 107;
                                    }
                                    return 21;
                                }
                                case 45: {
                                    if (this.matchChar(61)) {
                                        c = 99;
                                    } else if (this.matchChar(45)) {
                                        if (!this.dirtyLine && this.matchChar(62)) {
                                            this.markCommentStart("--");
                                            this.skipLine();
                                            this.commentType = Token.CommentType.HTML;
                                            return 162;
                                        }
                                        c = 108;
                                    } else {
                                        c = 22;
                                    }
                                    this.dirtyLine = true;
                                    return c;
                                }
                                default: {
                                    this.parser.addError("msg.illegal.character", c);
                                    return -1;
                                }
                            }
                        }
                        boolean containsEscape = isUnicodeEscapeStart;
                        while (true) {
                            int escapeVal;
                            if (isUnicodeEscapeStart) {
                                escapeVal = 0;
                            } else {
                                c = this.getChar();
                                if (c == 92) {
                                    c = this.getChar();
                                    if (c != 117) {
                                        this.parser.addError("msg.illegal.character", c);
                                        return -1;
                                    }
                                    isUnicodeEscapeStart = true;
                                    containsEscape = true;
                                    continue;
                                }
                                if (c != -1 && c != 65279 && Character.isJavaIdentifierPart((char)c)) {
                                    this.addToString(c);
                                    continue;
                                }
                                this.ungetChar(c);
                                String str = this.getStringFromBuffer();
                                if (!containsEscape) {
                                    int result = TokenStream.stringToKeyword(str, this.parser.compilerEnv.getLanguageVersion(), this.parser.inUseStrictDirective());
                                    if (result != 0) {
                                        if ((result == 154 || result == 73) && this.parser.compilerEnv.getLanguageVersion() < 170) {
                                            this.string = result == 154 ? "let" : "yield";
                                            result = 39;
                                        }
                                        this.string = (String)this.allStrings.intern(str);
                                        if (result != 128) {
                                            return result;
                                        }
                                        if (this.parser.compilerEnv.getLanguageVersion() >= 200) {
                                            return result;
                                        }
                                        if (!this.parser.compilerEnv.isReservedKeywordAsIdentifier()) {
                                            return result;
                                        }
                                    }
                                } else if (TokenStream.isKeyword(str, this.parser.compilerEnv.getLanguageVersion(), this.parser.inUseStrictDirective())) {
                                    str = TokenStream.convertLastCharToHex(str);
                                }
                                this.string = (String)this.allStrings.intern(str);
                                return 39;
                            }
                            for (int i = 0; i != 4 && (escapeVal = Kit.xDigitToInt(c = this.getChar(), escapeVal)) >= 0; ++i) {
                            }
                            if (escapeVal < 0) {
                                this.parser.addError("msg.invalid.escape");
                                return -1;
                            }
                            this.addToString(escapeVal);
                            isUnicodeEscapeStart = false;
                        }
                    }
                    while (48 <= c && c <= 57) {
                        if (base == 8 && c >= 56) {
                            if (!this.isOldOctal) {
                                this.parser.addError("msg.caught.nfe");
                                return -1;
                            }
                            this.parser.addWarning("msg.bad.octal.literal", c == 56 ? "8" : "9");
                            base = 10;
                        } else if (base == 2 && c >= 50) {
                            this.parser.addError("msg.caught.nfe");
                            return -1;
                        }
                        this.addToString(c);
                        c = this.getChar();
                        isEmpty = false;
                    }
                }
                if (isEmpty && (this.isBinary || this.isOctal || this.isHex)) {
                    this.parser.addError("msg.caught.nfe");
                    return -1;
                }
                boolean isInteger = true;
                if (base == 10 && (c == 46 || c == 101 || c == 69)) {
                    isInteger = false;
                    if (c == 46) {
                        do {
                            this.addToString(c);
                        } while (TokenStream.isDigit(c = this.getChar()));
                    }
                    if (c == 101 || c == 69) {
                        this.addToString(c);
                        c = this.getChar();
                        if (c == 43 || c == 45) {
                            this.addToString(c);
                            c = this.getChar();
                        }
                        if (!TokenStream.isDigit(c)) {
                            this.parser.addError("msg.missing.exponent");
                            return -1;
                        }
                        do {
                            this.addToString(c);
                        } while (TokenStream.isDigit(c = this.getChar()));
                    }
                }
                this.ungetChar(c);
                this.string = numString = this.getStringFromBuffer();
                if (base == 10 && !isInteger) {
                    try {
                        dval = Double.parseDouble(numString);
                    }
                    catch (NumberFormatException ex) {
                        this.parser.addError("msg.caught.nfe");
                        return -1;
                    }
                } else {
                    dval = ScriptRuntime.stringPrefixToNumber(numString, 0, base);
                }
                this.number = dval;
                return 40;
            }
            this.quoteChar = c;
            this.stringBufferTop = 0;
            c = this.getChar(false);
            block46: while (true) {
                if (c == this.quoteChar) {
                    String str = this.getStringFromBuffer();
                    this.string = (String)this.allStrings.intern(str);
                    return 41;
                }
                if (c == 10 || c == -1) {
                    this.ungetChar(c);
                    this.tokenEnd = this.cursor;
                    this.parser.addError("msg.unterminated.string.lit");
                    return -1;
                }
                if (c == 92) {
                    c = this.getChar();
                    switch (c) {
                        case 98: {
                            c = 8;
                            break;
                        }
                        case 102: {
                            c = 12;
                            break;
                        }
                        case 110: {
                            c = 10;
                            break;
                        }
                        case 114: {
                            c = 13;
                            break;
                        }
                        case 116: {
                            c = 9;
                            break;
                        }
                        case 118: {
                            c = 11;
                            break;
                        }
                        case 117: {
                            int escapeStart = this.stringBufferTop;
                            this.addToString(117);
                            int escapeVal = 0;
                            for (int i = 0; i != 4; ++i) {
                                c = this.getChar();
                                escapeVal = Kit.xDigitToInt(c, escapeVal);
                                if (escapeVal < 0) continue block46;
                                this.addToString(c);
                            }
                            this.stringBufferTop = escapeStart;
                            c = escapeVal;
                            break;
                        }
                        case 120: {
                            c = this.getChar();
                            int escapeVal = Kit.xDigitToInt(c, 0);
                            if (escapeVal < 0) {
                                this.addToString(120);
                                continue block46;
                            }
                            int c1 = c;
                            c = this.getChar();
                            escapeVal = Kit.xDigitToInt(c, escapeVal);
                            if (escapeVal < 0) {
                                this.addToString(120);
                                this.addToString(c1);
                                continue block46;
                            }
                            c = escapeVal;
                            break;
                        }
                        case 10: {
                            c = this.getChar();
                            continue block46;
                        }
                        default: {
                            if (48 > c || c >= 56) break;
                            int val = c - 48;
                            c = this.getChar();
                            if (48 <= c && c < 56) {
                                val = 8 * val + c - 48;
                                c = this.getChar();
                                if (48 <= c && c < 56 && val <= 31) {
                                    val = 8 * val + c - 48;
                                    c = this.getChar();
                                }
                            }
                            this.ungetChar(c);
                            c = val;
                        }
                    }
                }
                this.addToString(c);
                c = this.getChar(false);
            }
        }
        while (true) {
            if ((c = this.getChar()) == -1) {
                this.tokenEnd = this.cursor - 1;
                this.parser.addError("msg.unterminated.comment");
                return 162;
            }
            if (c == 42) {
                lookForSlash = true;
                continue;
            }
            if (c == 47) {
                if (!lookForSlash) continue;
                this.tokenEnd = this.cursor;
                return 162;
            }
            lookForSlash = false;
            this.tokenEnd = this.cursor;
        }
    }

    private static boolean isAlpha(int c) {
        if (c <= 90) {
            return 65 <= c;
        }
        return 97 <= c && c <= 122;
    }

    static boolean isDigit(int c) {
        return 48 <= c && c <= 57;
    }

    static boolean isJSSpace(int c) {
        if (c <= 127) {
            return c == 32 || c == 9 || c == 12 || c == 11;
        }
        return c == 160 || c == 65279 || Character.getType((char)c) == 12;
    }

    private static boolean isJSFormatChar(int c) {
        return c > 127 && Character.getType((char)c) == 16;
    }

    void readRegExp(int startToken) throws IOException {
        int c;
        int start = this.tokenBeg;
        this.stringBufferTop = 0;
        if (startToken == 101) {
            this.addToString(61);
        } else {
            if (startToken != 24) {
                Kit.codeBug();
            }
            if (this.peekChar() == 42) {
                this.tokenEnd = this.cursor - 1;
                this.string = new String(this.stringBuffer, 0, this.stringBufferTop);
                this.parser.reportError("msg.unterminated.re.lit");
                return;
            }
        }
        boolean inCharSet = false;
        while ((c = this.getChar()) != 47 || inCharSet) {
            if (c == 10 || c == -1) {
                this.ungetChar(c);
                this.tokenEnd = this.cursor - 1;
                this.string = new String(this.stringBuffer, 0, this.stringBufferTop);
                this.parser.reportError("msg.unterminated.re.lit");
                return;
            }
            if (c == 92) {
                this.addToString(c);
                c = this.getChar();
                if (c == 10 || c == -1) {
                    this.ungetChar(c);
                    this.tokenEnd = this.cursor - 1;
                    this.string = new String(this.stringBuffer, 0, this.stringBufferTop);
                    this.parser.reportError("msg.unterminated.re.lit");
                    return;
                }
            } else if (c == 91) {
                inCharSet = true;
            } else if (c == 93) {
                inCharSet = false;
            }
            this.addToString(c);
        }
        int reEnd = this.stringBufferTop;
        while (true) {
            if (this.matchChar(103)) {
                this.addToString(103);
                continue;
            }
            if (this.matchChar(105)) {
                this.addToString(105);
                continue;
            }
            if (this.matchChar(109)) {
                this.addToString(109);
                continue;
            }
            if (!this.matchChar(121)) break;
            this.addToString(121);
        }
        this.tokenEnd = start + this.stringBufferTop + 2;
        if (TokenStream.isAlpha(this.peekChar())) {
            this.parser.reportError("msg.invalid.re.flag");
        }
        this.string = new String(this.stringBuffer, 0, reEnd);
        this.regExpFlags = new String(this.stringBuffer, reEnd, this.stringBufferTop - reEnd);
    }

    String readAndClearRegExpFlags() {
        String flags = this.regExpFlags;
        this.regExpFlags = null;
        return flags;
    }

    boolean isXMLAttribute() {
        return this.xmlIsAttribute;
    }

    int getFirstXMLToken() throws IOException {
        this.xmlOpenTagsCount = 0;
        this.xmlIsAttribute = false;
        this.xmlIsTagContent = false;
        if (!this.canUngetChar()) {
            return -1;
        }
        this.ungetChar(60);
        return this.getNextXMLToken();
    }

    int getNextXMLToken() throws IOException {
        this.tokenBeg = this.cursor;
        this.stringBufferTop = 0;
        int c = this.getChar();
        while (c != -1) {
            if (this.xmlIsTagContent) {
                switch (c) {
                    case 62: {
                        this.addToString(c);
                        this.xmlIsTagContent = false;
                        this.xmlIsAttribute = false;
                        break;
                    }
                    case 47: {
                        this.addToString(c);
                        if (this.peekChar() != 62) break;
                        c = this.getChar();
                        this.addToString(c);
                        this.xmlIsTagContent = false;
                        --this.xmlOpenTagsCount;
                        break;
                    }
                    case 123: {
                        this.ungetChar(c);
                        this.string = this.getStringFromBuffer();
                        return 146;
                    }
                    case 34: 
                    case 39: {
                        this.addToString(c);
                        if (this.readQuotedString(c)) break;
                        return -1;
                    }
                    case 61: {
                        this.addToString(c);
                        this.xmlIsAttribute = true;
                        break;
                    }
                    case 9: 
                    case 10: 
                    case 13: 
                    case 32: {
                        this.addToString(c);
                        break;
                    }
                    default: {
                        this.addToString(c);
                        this.xmlIsAttribute = false;
                    }
                }
                if (!this.xmlIsTagContent && this.xmlOpenTagsCount == 0) {
                    this.string = this.getStringFromBuffer();
                    return 149;
                }
            } else {
                block8 : switch (c) {
                    case 60: {
                        this.addToString(c);
                        c = this.peekChar();
                        switch (c) {
                            case 33: {
                                c = this.getChar();
                                this.addToString(c);
                                c = this.peekChar();
                                switch (c) {
                                    case 45: {
                                        c = this.getChar();
                                        this.addToString(c);
                                        c = this.getChar();
                                        if (c == 45) {
                                            this.addToString(c);
                                            if (this.readXmlComment()) break block8;
                                            return -1;
                                        }
                                        this.stringBufferTop = 0;
                                        this.string = null;
                                        this.parser.addError("msg.XML.bad.form");
                                        return -1;
                                    }
                                    case 91: {
                                        c = this.getChar();
                                        this.addToString(c);
                                        if (this.getChar() == 67 && this.getChar() == 68 && this.getChar() == 65 && this.getChar() == 84 && this.getChar() == 65 && this.getChar() == 91) {
                                            this.addToString(67);
                                            this.addToString(68);
                                            this.addToString(65);
                                            this.addToString(84);
                                            this.addToString(65);
                                            this.addToString(91);
                                            if (this.readCDATA()) break block8;
                                            return -1;
                                        }
                                        this.stringBufferTop = 0;
                                        this.string = null;
                                        this.parser.addError("msg.XML.bad.form");
                                        return -1;
                                    }
                                    default: {
                                        if (this.readEntity()) break block8;
                                        return -1;
                                    }
                                }
                            }
                            case 63: {
                                c = this.getChar();
                                this.addToString(c);
                                if (this.readPI()) break block8;
                                return -1;
                            }
                            case 47: {
                                c = this.getChar();
                                this.addToString(c);
                                if (this.xmlOpenTagsCount == 0) {
                                    this.stringBufferTop = 0;
                                    this.string = null;
                                    this.parser.addError("msg.XML.bad.form");
                                    return -1;
                                }
                                this.xmlIsTagContent = true;
                                --this.xmlOpenTagsCount;
                                break;
                            }
                            default: {
                                this.xmlIsTagContent = true;
                                ++this.xmlOpenTagsCount;
                                break;
                            }
                        }
                        break;
                    }
                    case 123: {
                        this.ungetChar(c);
                        this.string = this.getStringFromBuffer();
                        return 146;
                    }
                    default: {
                        this.addToString(c);
                    }
                }
            }
            c = this.getChar();
        }
        this.tokenEnd = this.cursor;
        this.stringBufferTop = 0;
        this.string = null;
        this.parser.addError("msg.XML.bad.form");
        return -1;
    }

    private boolean readQuotedString(int quote) throws IOException {
        int c = this.getChar();
        while (c != -1) {
            this.addToString(c);
            if (c == quote) {
                return true;
            }
            c = this.getChar();
        }
        this.stringBufferTop = 0;
        this.string = null;
        this.parser.addError("msg.XML.bad.form");
        return false;
    }

    private boolean readXmlComment() throws IOException {
        int c = this.getChar();
        while (c != -1) {
            this.addToString(c);
            if (c == 45 && this.peekChar() == 45) {
                c = this.getChar();
                this.addToString(c);
                if (this.peekChar() != 62) continue;
                c = this.getChar();
                this.addToString(c);
                return true;
            }
            c = this.getChar();
        }
        this.stringBufferTop = 0;
        this.string = null;
        this.parser.addError("msg.XML.bad.form");
        return false;
    }

    private boolean readCDATA() throws IOException {
        int c = this.getChar();
        while (c != -1) {
            this.addToString(c);
            if (c == 93 && this.peekChar() == 93) {
                c = this.getChar();
                this.addToString(c);
                if (this.peekChar() != 62) continue;
                c = this.getChar();
                this.addToString(c);
                return true;
            }
            c = this.getChar();
        }
        this.stringBufferTop = 0;
        this.string = null;
        this.parser.addError("msg.XML.bad.form");
        return false;
    }

    private boolean readEntity() throws IOException {
        int declTags = 1;
        int c = this.getChar();
        while (c != -1) {
            this.addToString(c);
            switch (c) {
                case 60: {
                    ++declTags;
                    break;
                }
                case 62: {
                    if (--declTags != 0) break;
                    return true;
                }
            }
            c = this.getChar();
        }
        this.stringBufferTop = 0;
        this.string = null;
        this.parser.addError("msg.XML.bad.form");
        return false;
    }

    private boolean readPI() throws IOException {
        int c = this.getChar();
        while (c != -1) {
            this.addToString(c);
            if (c == 63 && this.peekChar() == 62) {
                c = this.getChar();
                this.addToString(c);
                return true;
            }
            c = this.getChar();
        }
        this.stringBufferTop = 0;
        this.string = null;
        this.parser.addError("msg.XML.bad.form");
        return false;
    }

    private String getStringFromBuffer() {
        this.tokenEnd = this.cursor;
        return new String(this.stringBuffer, 0, this.stringBufferTop);
    }

    private void addToString(int c) {
        int N = this.stringBufferTop;
        if (N == this.stringBuffer.length) {
            char[] tmp = new char[this.stringBuffer.length * 2];
            System.arraycopy(this.stringBuffer, 0, tmp, 0, N);
            this.stringBuffer = tmp;
        }
        this.stringBuffer[N] = (char)c;
        this.stringBufferTop = N + 1;
    }

    private boolean canUngetChar() {
        return this.ungetCursor == 0 || this.ungetBuffer[this.ungetCursor - 1] != 10;
    }

    private void ungetChar(int c) {
        if (this.ungetCursor != 0 && this.ungetBuffer[this.ungetCursor - 1] == 10) {
            Kit.codeBug();
        }
        this.ungetBuffer[this.ungetCursor++] = c;
        --this.cursor;
    }

    private boolean matchChar(int test) throws IOException {
        int c = this.getCharIgnoreLineEnd();
        if (c == test) {
            this.tokenEnd = this.cursor;
            return true;
        }
        this.ungetCharIgnoreLineEnd(c);
        return false;
    }

    private int peekChar() throws IOException {
        int c = this.getChar();
        this.ungetChar(c);
        return c;
    }

    private int getChar() throws IOException {
        return this.getChar(true);
    }

    private int getChar(boolean skipFormattingChars) throws IOException {
        int c;
        block12: {
            if (this.ungetCursor != 0) {
                ++this.cursor;
                return this.ungetBuffer[--this.ungetCursor];
            }
            while (true) {
                if (this.sourceString != null) {
                    if (this.sourceCursor == this.sourceEnd) {
                        this.hitEOF = true;
                        return -1;
                    }
                    ++this.cursor;
                    c = this.sourceString.charAt(this.sourceCursor++);
                } else {
                    if (this.sourceCursor == this.sourceEnd && !this.fillSourceBuffer()) {
                        this.hitEOF = true;
                        return -1;
                    }
                    ++this.cursor;
                    c = this.sourceBuffer[this.sourceCursor++];
                }
                if (this.lineEndChar >= 0) {
                    if (this.lineEndChar == 13 && c == 10) {
                        this.lineEndChar = 10;
                        continue;
                    }
                    this.lineEndChar = -1;
                    this.lineStart = this.sourceCursor - 1;
                    ++this.lineno;
                }
                if (c <= 127) {
                    if (c == 10 || c == 13) {
                        this.lineEndChar = c;
                        c = 10;
                    }
                    break block12;
                }
                if (c == 65279) {
                    return c;
                }
                if (!skipFormattingChars || !TokenStream.isJSFormatChar(c)) break;
            }
            if (ScriptRuntime.isJSLineTerminator(c)) {
                this.lineEndChar = c;
                c = 10;
            }
        }
        return c;
    }

    private int getCharIgnoreLineEnd() throws IOException {
        int c;
        block9: {
            if (this.ungetCursor != 0) {
                ++this.cursor;
                return this.ungetBuffer[--this.ungetCursor];
            }
            do {
                if (this.sourceString != null) {
                    if (this.sourceCursor == this.sourceEnd) {
                        this.hitEOF = true;
                        return -1;
                    }
                    ++this.cursor;
                    c = this.sourceString.charAt(this.sourceCursor++);
                } else {
                    if (this.sourceCursor == this.sourceEnd && !this.fillSourceBuffer()) {
                        this.hitEOF = true;
                        return -1;
                    }
                    ++this.cursor;
                    c = this.sourceBuffer[this.sourceCursor++];
                }
                if (c <= 127) {
                    if (c == 10 || c == 13) {
                        this.lineEndChar = c;
                        c = 10;
                    }
                    break block9;
                }
                if (c != 65279) continue;
                return c;
            } while (TokenStream.isJSFormatChar(c));
            if (ScriptRuntime.isJSLineTerminator(c)) {
                this.lineEndChar = c;
                c = 10;
            }
        }
        return c;
    }

    private void ungetCharIgnoreLineEnd(int c) {
        this.ungetBuffer[this.ungetCursor++] = c;
        --this.cursor;
    }

    private void skipLine() throws IOException {
        int c;
        while ((c = this.getChar()) != -1 && c != 10) {
        }
        this.ungetChar(c);
        this.tokenEnd = this.cursor;
    }

    final int getOffset() {
        int n = this.sourceCursor - this.lineStart;
        if (this.lineEndChar >= 0) {
            --n;
        }
        return n;
    }

    private final int charAt(int index) {
        if (index < 0) {
            return -1;
        }
        if (this.sourceString != null) {
            if (index >= this.sourceEnd) {
                return -1;
            }
            return this.sourceString.charAt(index);
        }
        if (index >= this.sourceEnd) {
            int oldSourceCursor = this.sourceCursor;
            try {
                if (!this.fillSourceBuffer()) {
                    return -1;
                }
            }
            catch (IOException ioe) {
                return -1;
            }
            index -= oldSourceCursor - this.sourceCursor;
        }
        return this.sourceBuffer[index];
    }

    private final String substring(int beginIndex, int endIndex) {
        if (this.sourceString != null) {
            return this.sourceString.substring(beginIndex, endIndex);
        }
        int count = endIndex - beginIndex;
        return new String(this.sourceBuffer, beginIndex, count);
    }

    final String getLine() {
        int lineEnd = this.sourceCursor;
        if (this.lineEndChar >= 0) {
            if (this.lineEndChar == 10 && this.charAt(--lineEnd - 1) == 13) {
                --lineEnd;
            }
        } else {
            int c;
            int lineLength = lineEnd - this.lineStart;
            while ((c = this.charAt(this.lineStart + lineLength)) != -1 && !ScriptRuntime.isJSLineTerminator(c)) {
                ++lineLength;
            }
            lineEnd = this.lineStart + lineLength;
        }
        return this.substring(this.lineStart, lineEnd);
    }

    final String getLine(int position, int[] linep) {
        assert (position >= 0 && position <= this.cursor);
        assert (linep.length == 2);
        int delta = this.cursor + this.ungetCursor - position;
        int cur = this.sourceCursor;
        if (delta > cur) {
            return null;
        }
        int end = 0;
        int lines = 0;
        while (delta > 0) {
            assert (cur > 0);
            int c = this.charAt(cur - 1);
            if (ScriptRuntime.isJSLineTerminator(c)) {
                if (c == 10 && this.charAt(cur - 2) == 13) {
                    --delta;
                    --cur;
                }
                ++lines;
                end = cur - 1;
            }
            --delta;
            --cur;
        }
        int start = 0;
        int offset = 0;
        while (cur > 0) {
            int c = this.charAt(cur - 1);
            if (ScriptRuntime.isJSLineTerminator(c)) {
                start = cur;
                break;
            }
            --cur;
            ++offset;
        }
        linep[0] = this.lineno - lines + (this.lineEndChar >= 0 ? 1 : 0);
        linep[1] = offset;
        if (lines == 0) {
            return this.getLine();
        }
        return this.substring(start, end);
    }

    private boolean fillSourceBuffer() throws IOException {
        int n;
        if (this.sourceString != null) {
            Kit.codeBug();
        }
        if (this.sourceEnd == this.sourceBuffer.length) {
            if (this.lineStart != 0 && !this.isMarkingComment()) {
                System.arraycopy(this.sourceBuffer, this.lineStart, this.sourceBuffer, 0, this.sourceEnd - this.lineStart);
                this.sourceEnd -= this.lineStart;
                this.sourceCursor -= this.lineStart;
                this.lineStart = 0;
            } else {
                char[] tmp = new char[this.sourceBuffer.length * 2];
                System.arraycopy(this.sourceBuffer, 0, tmp, 0, this.sourceEnd);
                this.sourceBuffer = tmp;
            }
        }
        if ((n = this.sourceReader.read(this.sourceBuffer, this.sourceEnd, this.sourceBuffer.length - this.sourceEnd)) < 0) {
            return false;
        }
        this.sourceEnd += n;
        return true;
    }

    public int getCursor() {
        return this.cursor;
    }

    public int getTokenBeg() {
        return this.tokenBeg;
    }

    public int getTokenEnd() {
        return this.tokenEnd;
    }

    public int getTokenLength() {
        return this.tokenEnd - this.tokenBeg;
    }

    public Token.CommentType getCommentType() {
        return this.commentType;
    }

    private void markCommentStart() {
        this.markCommentStart("");
    }

    private void markCommentStart(String prefix) {
        if (this.parser.compilerEnv.isRecordingComments() && this.sourceReader != null) {
            this.commentPrefix = prefix;
            this.commentCursor = this.sourceCursor - 1;
        }
    }

    private boolean isMarkingComment() {
        return this.commentCursor != -1;
    }

    final String getAndResetCurrentComment() {
        if (this.sourceString != null) {
            if (this.isMarkingComment()) {
                Kit.codeBug();
            }
            return this.sourceString.substring(this.tokenBeg, this.tokenEnd);
        }
        if (!this.isMarkingComment()) {
            Kit.codeBug();
        }
        StringBuilder comment = new StringBuilder(this.commentPrefix);
        comment.append(this.sourceBuffer, this.commentCursor, this.getTokenLength() - this.commentPrefix.length());
        this.commentCursor = -1;
        return comment.toString();
    }

    private static String convertLastCharToHex(String str) {
        int lastIndex = str.length() - 1;
        StringBuilder buf = new StringBuilder(str.substring(0, lastIndex));
        buf.append("\\u");
        String hexCode = Integer.toHexString(str.charAt(lastIndex));
        for (int i = 0; i < 4 - hexCode.length(); ++i) {
            buf.append('0');
        }
        buf.append(hexCode);
        return buf.toString();
    }
}

