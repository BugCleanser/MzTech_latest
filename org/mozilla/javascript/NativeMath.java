/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.javascript;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

final class NativeMath
extends IdScriptableObject {
    private static final long serialVersionUID = -8838847185801131569L;
    private static final Object MATH_TAG = "Math";
    private static final double LOG2E = 1.4426950408889634;
    private static final Double Double32 = 32.0;
    private static final int Id_toSource = 1;
    private static final int Id_abs = 2;
    private static final int Id_acos = 3;
    private static final int Id_asin = 4;
    private static final int Id_atan = 5;
    private static final int Id_atan2 = 6;
    private static final int Id_ceil = 7;
    private static final int Id_cos = 8;
    private static final int Id_exp = 9;
    private static final int Id_floor = 10;
    private static final int Id_log = 11;
    private static final int Id_max = 12;
    private static final int Id_min = 13;
    private static final int Id_pow = 14;
    private static final int Id_random = 15;
    private static final int Id_round = 16;
    private static final int Id_sin = 17;
    private static final int Id_sqrt = 18;
    private static final int Id_tan = 19;
    private static final int Id_cbrt = 20;
    private static final int Id_cosh = 21;
    private static final int Id_expm1 = 22;
    private static final int Id_hypot = 23;
    private static final int Id_log1p = 24;
    private static final int Id_log10 = 25;
    private static final int Id_sinh = 26;
    private static final int Id_tanh = 27;
    private static final int Id_imul = 28;
    private static final int Id_trunc = 29;
    private static final int Id_acosh = 30;
    private static final int Id_asinh = 31;
    private static final int Id_atanh = 32;
    private static final int Id_sign = 33;
    private static final int Id_log2 = 34;
    private static final int Id_fround = 35;
    private static final int Id_clz32 = 36;
    private static final int LAST_METHOD_ID = 36;
    private static final int Id_E = 37;
    private static final int Id_PI = 38;
    private static final int Id_LN10 = 39;
    private static final int Id_LN2 = 40;
    private static final int Id_LOG2E = 41;
    private static final int Id_LOG10E = 42;
    private static final int Id_SQRT1_2 = 43;
    private static final int Id_SQRT2 = 44;
    private static final int MAX_ID = 44;

    static void init(Scriptable scope, boolean sealed) {
        NativeMath obj = new NativeMath();
        obj.activatePrototypeMap(44);
        obj.setPrototype(NativeMath.getObjectPrototype(scope));
        obj.setParentScope(scope);
        if (sealed) {
            obj.sealObject();
        }
        ScriptableObject.defineProperty(scope, "Math", obj, 2);
    }

    private NativeMath() {
    }

    @Override
    public String getClassName() {
        return "Math";
    }

    @Override
    protected void initPrototypeId(int id) {
        if (id <= 36) {
            String name;
            int arity;
            switch (id) {
                case 1: {
                    arity = 0;
                    name = "toSource";
                    break;
                }
                case 2: {
                    arity = 1;
                    name = "abs";
                    break;
                }
                case 3: {
                    arity = 1;
                    name = "acos";
                    break;
                }
                case 30: {
                    arity = 1;
                    name = "acosh";
                    break;
                }
                case 4: {
                    arity = 1;
                    name = "asin";
                    break;
                }
                case 31: {
                    arity = 1;
                    name = "asinh";
                    break;
                }
                case 5: {
                    arity = 1;
                    name = "atan";
                    break;
                }
                case 32: {
                    arity = 1;
                    name = "atanh";
                    break;
                }
                case 6: {
                    arity = 2;
                    name = "atan2";
                    break;
                }
                case 20: {
                    arity = 1;
                    name = "cbrt";
                    break;
                }
                case 7: {
                    arity = 1;
                    name = "ceil";
                    break;
                }
                case 36: {
                    arity = 1;
                    name = "clz32";
                    break;
                }
                case 8: {
                    arity = 1;
                    name = "cos";
                    break;
                }
                case 21: {
                    arity = 1;
                    name = "cosh";
                    break;
                }
                case 9: {
                    arity = 1;
                    name = "exp";
                    break;
                }
                case 22: {
                    arity = 1;
                    name = "expm1";
                    break;
                }
                case 10: {
                    arity = 1;
                    name = "floor";
                    break;
                }
                case 35: {
                    arity = 1;
                    name = "fround";
                    break;
                }
                case 23: {
                    arity = 2;
                    name = "hypot";
                    break;
                }
                case 28: {
                    arity = 2;
                    name = "imul";
                    break;
                }
                case 11: {
                    arity = 1;
                    name = "log";
                    break;
                }
                case 24: {
                    arity = 1;
                    name = "log1p";
                    break;
                }
                case 25: {
                    arity = 1;
                    name = "log10";
                    break;
                }
                case 34: {
                    arity = 1;
                    name = "log2";
                    break;
                }
                case 12: {
                    arity = 2;
                    name = "max";
                    break;
                }
                case 13: {
                    arity = 2;
                    name = "min";
                    break;
                }
                case 14: {
                    arity = 2;
                    name = "pow";
                    break;
                }
                case 15: {
                    arity = 0;
                    name = "random";
                    break;
                }
                case 16: {
                    arity = 1;
                    name = "round";
                    break;
                }
                case 33: {
                    arity = 1;
                    name = "sign";
                    break;
                }
                case 17: {
                    arity = 1;
                    name = "sin";
                    break;
                }
                case 26: {
                    arity = 1;
                    name = "sinh";
                    break;
                }
                case 18: {
                    arity = 1;
                    name = "sqrt";
                    break;
                }
                case 19: {
                    arity = 1;
                    name = "tan";
                    break;
                }
                case 27: {
                    arity = 1;
                    name = "tanh";
                    break;
                }
                case 29: {
                    arity = 1;
                    name = "trunc";
                    break;
                }
                default: {
                    throw new IllegalStateException(String.valueOf(id));
                }
            }
            this.initPrototypeMethod(MATH_TAG, id, name, arity);
        } else {
            String name;
            double x;
            switch (id) {
                case 37: {
                    x = Math.E;
                    name = "E";
                    break;
                }
                case 38: {
                    x = Math.PI;
                    name = "PI";
                    break;
                }
                case 39: {
                    x = 2.302585092994046;
                    name = "LN10";
                    break;
                }
                case 40: {
                    x = 0.6931471805599453;
                    name = "LN2";
                    break;
                }
                case 41: {
                    x = 1.4426950408889634;
                    name = "LOG2E";
                    break;
                }
                case 42: {
                    x = 0.4342944819032518;
                    name = "LOG10E";
                    break;
                }
                case 43: {
                    x = 0.7071067811865476;
                    name = "SQRT1_2";
                    break;
                }
                case 44: {
                    x = 1.4142135623730951;
                    name = "SQRT2";
                    break;
                }
                default: {
                    throw new IllegalStateException(String.valueOf(id));
                }
            }
            this.initPrototypeValue(id, name, (Object)ScriptRuntime.wrapNumber(x), 7);
        }
    }

    @Override
    public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
        double x;
        if (!f.hasTag(MATH_TAG)) {
            return super.execIdCall(f, cx, scope, thisObj, args);
        }
        int methodId = f.methodId();
        block0 : switch (methodId) {
            case 1: {
                return "Math";
            }
            case 2: {
                x = ScriptRuntime.toNumber(args, 0);
                x = x == 0.0 ? 0.0 : (x < 0.0 ? -x : x);
                break;
            }
            case 3: 
            case 4: {
                x = ScriptRuntime.toNumber(args, 0);
                if (!Double.isNaN(x) && -1.0 <= x && x <= 1.0) {
                    x = methodId == 3 ? Math.acos(x) : Math.asin(x);
                    break;
                }
                x = Double.NaN;
                break;
            }
            case 30: {
                double x2 = ScriptRuntime.toNumber(args, 0);
                if (!Double.isNaN(x2)) {
                    return Math.log(x2 + Math.sqrt(x2 * x2 - 1.0));
                }
                return ScriptRuntime.NaNobj;
            }
            case 31: {
                double x3 = ScriptRuntime.toNumber(args, 0);
                if (Double.isInfinite(x3)) {
                    return x3;
                }
                if (!Double.isNaN(x3)) {
                    if (x3 == 0.0) {
                        if (1.0 / x3 > 0.0) {
                            return ScriptRuntime.zeroObj;
                        }
                        return ScriptRuntime.negativeZeroObj;
                    }
                    return Math.log(x3 + Math.sqrt(x3 * x3 + 1.0));
                }
                return ScriptRuntime.NaNobj;
            }
            case 5: {
                x = ScriptRuntime.toNumber(args, 0);
                x = Math.atan(x);
                break;
            }
            case 32: {
                double x4 = ScriptRuntime.toNumber(args, 0);
                if (!Double.isNaN(x4) && -1.0 <= x4 && x4 <= 1.0) {
                    if (x4 == 0.0) {
                        if (1.0 / x4 > 0.0) {
                            return ScriptRuntime.zeroObj;
                        }
                        return ScriptRuntime.negativeZeroObj;
                    }
                    return 0.5 * Math.log((x4 + 1.0) / (x4 - 1.0));
                }
                return ScriptRuntime.NaNobj;
            }
            case 6: {
                x = ScriptRuntime.toNumber(args, 0);
                x = Math.atan2(x, ScriptRuntime.toNumber(args, 1));
                break;
            }
            case 20: {
                x = ScriptRuntime.toNumber(args, 0);
                x = Math.cbrt(x);
                break;
            }
            case 7: {
                x = ScriptRuntime.toNumber(args, 0);
                x = Math.ceil(x);
                break;
            }
            case 36: {
                double x5 = ScriptRuntime.toNumber(args, 0);
                if (x5 == 0.0 || Double.isNaN(x5) || Double.isInfinite(x5)) {
                    return Double32;
                }
                long n = ScriptRuntime.toUint32(x5);
                if (n == 0L) {
                    return Double32;
                }
                return 31.0 - Math.floor(Math.log(n >>> 0) * 1.4426950408889634);
            }
            case 8: {
                x = ScriptRuntime.toNumber(args, 0);
                x = Double.isInfinite(x) ? Double.NaN : Math.cos(x);
                break;
            }
            case 21: {
                x = ScriptRuntime.toNumber(args, 0);
                x = Math.cosh(x);
                break;
            }
            case 23: {
                x = NativeMath.js_hypot(args);
                break;
            }
            case 9: {
                x = ScriptRuntime.toNumber(args, 0);
                x = x == Double.POSITIVE_INFINITY ? x : (x == Double.NEGATIVE_INFINITY ? 0.0 : Math.exp(x));
                break;
            }
            case 22: {
                x = ScriptRuntime.toNumber(args, 0);
                x = Math.expm1(x);
                break;
            }
            case 10: {
                x = ScriptRuntime.toNumber(args, 0);
                x = Math.floor(x);
                break;
            }
            case 35: {
                x = ScriptRuntime.toNumber(args, 0);
                x = (float)x;
                break;
            }
            case 28: {
                x = NativeMath.js_imul(args);
                break;
            }
            case 11: {
                x = ScriptRuntime.toNumber(args, 0);
                x = x < 0.0 ? Double.NaN : Math.log(x);
                break;
            }
            case 24: {
                x = ScriptRuntime.toNumber(args, 0);
                x = Math.log1p(x);
                break;
            }
            case 25: {
                x = ScriptRuntime.toNumber(args, 0);
                x = Math.log10(x);
                break;
            }
            case 34: {
                x = ScriptRuntime.toNumber(args, 0);
                x = x < 0.0 ? Double.NaN : Math.log(x) * 1.4426950408889634;
                break;
            }
            case 12: 
            case 13: {
                x = methodId == 12 ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
                for (int i = 0; i != args.length; ++i) {
                    double d = ScriptRuntime.toNumber(args[i]);
                    if (Double.isNaN(d)) {
                        x = d;
                        break block0;
                    }
                    x = methodId == 12 ? Math.max(x, d) : Math.min(x, d);
                }
                break;
            }
            case 14: {
                x = ScriptRuntime.toNumber(args, 0);
                x = NativeMath.js_pow(x, ScriptRuntime.toNumber(args, 1));
                break;
            }
            case 15: {
                x = Math.random();
                break;
            }
            case 16: {
                x = ScriptRuntime.toNumber(args, 0);
                if (Double.isNaN(x) || Double.isInfinite(x)) break;
                long l = Math.round(x);
                if (l != 0L) {
                    x = l;
                    break;
                }
                if (x < 0.0) {
                    x = ScriptRuntime.negativeZero;
                    break;
                }
                if (x == 0.0) break;
                x = 0.0;
                break;
            }
            case 33: {
                double x6 = ScriptRuntime.toNumber(args, 0);
                if (!Double.isNaN(x6)) {
                    if (x6 == 0.0) {
                        if (1.0 / x6 > 0.0) {
                            return ScriptRuntime.zeroObj;
                        }
                        return ScriptRuntime.negativeZeroObj;
                    }
                    return Math.signum(x6);
                }
                return ScriptRuntime.NaNobj;
            }
            case 17: {
                x = ScriptRuntime.toNumber(args, 0);
                x = Double.isInfinite(x) ? Double.NaN : Math.sin(x);
                break;
            }
            case 26: {
                x = ScriptRuntime.toNumber(args, 0);
                x = Math.sinh(x);
                break;
            }
            case 18: {
                x = ScriptRuntime.toNumber(args, 0);
                x = Math.sqrt(x);
                break;
            }
            case 19: {
                x = ScriptRuntime.toNumber(args, 0);
                x = Math.tan(x);
                break;
            }
            case 27: {
                x = ScriptRuntime.toNumber(args, 0);
                x = Math.tanh(x);
                break;
            }
            case 29: {
                x = ScriptRuntime.toNumber(args, 0);
                x = NativeMath.js_trunc(x);
                break;
            }
            default: {
                throw new IllegalStateException(String.valueOf(methodId));
            }
        }
        return ScriptRuntime.wrapNumber(x);
    }

    private static double js_pow(double x, double y) {
        double result;
        if (Double.isNaN(y)) {
            result = y;
        } else if (y == 0.0) {
            result = 1.0;
        } else if (x == 0.0) {
            long y_long;
            result = 1.0 / x > 0.0 ? (y > 0.0 ? 0.0 : Double.POSITIVE_INFINITY) : ((double)(y_long = (long)y) == y && (y_long & 1L) != 0L ? (y > 0.0 ? -0.0 : Double.NEGATIVE_INFINITY) : (y > 0.0 ? 0.0 : Double.POSITIVE_INFINITY));
        } else {
            result = Math.pow(x, y);
            if (Double.isNaN(result)) {
                if (y == Double.POSITIVE_INFINITY) {
                    if (x < -1.0 || 1.0 < x) {
                        result = Double.POSITIVE_INFINITY;
                    } else if (-1.0 < x && x < 1.0) {
                        result = 0.0;
                    }
                } else if (y == Double.NEGATIVE_INFINITY) {
                    if (x < -1.0 || 1.0 < x) {
                        result = 0.0;
                    } else if (-1.0 < x && x < 1.0) {
                        result = Double.POSITIVE_INFINITY;
                    }
                } else if (x == Double.POSITIVE_INFINITY) {
                    result = y > 0.0 ? Double.POSITIVE_INFINITY : 0.0;
                } else if (x == Double.NEGATIVE_INFINITY) {
                    long y_long = (long)y;
                    result = (double)y_long == y && (y_long & 1L) != 0L ? (y > 0.0 ? Double.NEGATIVE_INFINITY : -0.0) : (y > 0.0 ? Double.POSITIVE_INFINITY : 0.0);
                }
            }
        }
        return result;
    }

    private static double js_hypot(Object[] args) {
        if (args == null) {
            return 0.0;
        }
        double y = 0.0;
        boolean hasNaN = false;
        boolean hasInfinity = false;
        for (Object o : args) {
            double d = ScriptRuntime.toNumber(o);
            if (Double.isNaN(d)) {
                hasNaN = true;
                continue;
            }
            if (Double.isInfinite(d)) {
                hasInfinity = true;
                continue;
            }
            y += d * d;
        }
        if (hasInfinity) {
            return Double.POSITIVE_INFINITY;
        }
        if (hasNaN) {
            return Double.NaN;
        }
        return Math.sqrt(y);
    }

    private static double js_trunc(double d) {
        return d < 0.0 ? Math.ceil(d) : Math.floor(d);
    }

    private static int js_imul(Object[] args) {
        if (args == null) {
            return 0;
        }
        int x = ScriptRuntime.toInt32(args, 0);
        int y = ScriptRuntime.toInt32(args, 1);
        return x * y;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected int findPrototypeId(String s) {
        int id = 0;
        String X = null;
        switch (s.length()) {
            case 1: {
                if (s.charAt(0) != 'E') break;
                return 37;
            }
            case 2: {
                if (s.charAt(0) != 'P' || s.charAt(1) != 'I') break;
                return 38;
            }
            case 3: {
                switch (s.charAt(0)) {
                    case 'L': {
                        if (s.charAt(2) != '2' || s.charAt(1) != 'N') break;
                        return 40;
                    }
                    case 'a': {
                        if (s.charAt(2) != 's' || s.charAt(1) != 'b') break;
                        return 2;
                    }
                    case 'c': {
                        if (s.charAt(2) != 's' || s.charAt(1) != 'o') break;
                        return 8;
                    }
                    case 'e': {
                        if (s.charAt(2) != 'p' || s.charAt(1) != 'x') break;
                        return 9;
                    }
                    case 'l': {
                        if (s.charAt(2) != 'g' || s.charAt(1) != 'o') break;
                        return 11;
                    }
                    case 'm': {
                        char c = s.charAt(2);
                        if (c == 'n') {
                            if (s.charAt(1) != 'i') break;
                            return 13;
                        }
                        if (c != 'x' || s.charAt(1) != 'a') break;
                        return 12;
                    }
                    case 'p': {
                        if (s.charAt(2) != 'w' || s.charAt(1) != 'o') break;
                        return 14;
                    }
                    case 's': {
                        if (s.charAt(2) != 'n' || s.charAt(1) != 'i') break;
                        return 17;
                    }
                    case 't': {
                        if (s.charAt(2) != 'n' || s.charAt(1) != 'a') break;
                        return 19;
                    }
                }
                break;
            }
            case 4: {
                switch (s.charAt(1)) {
                    case 'N': {
                        X = "LN10";
                        id = 39;
                        break;
                    }
                    case 'a': {
                        X = "tanh";
                        id = 27;
                        break;
                    }
                    case 'b': {
                        X = "cbrt";
                        id = 20;
                        break;
                    }
                    case 'c': {
                        X = "acos";
                        id = 3;
                        break;
                    }
                    case 'e': {
                        X = "ceil";
                        id = 7;
                        break;
                    }
                    case 'i': {
                        char c = s.charAt(3);
                        if (c == 'h') {
                            if (s.charAt(0) != 's' || s.charAt(2) != 'n') break;
                            return 26;
                        }
                        if (c != 'n' || s.charAt(0) != 's' || s.charAt(2) != 'g') break;
                        return 33;
                    }
                    case 'm': {
                        X = "imul";
                        id = 28;
                        break;
                    }
                    case 'o': {
                        char c = s.charAt(0);
                        if (c == 'c') {
                            if (s.charAt(2) != 's' || s.charAt(3) != 'h') break;
                            return 21;
                        }
                        if (c != 'l' || s.charAt(2) != 'g' || s.charAt(3) != '2') break;
                        return 34;
                    }
                    case 'q': {
                        X = "sqrt";
                        id = 18;
                        break;
                    }
                    case 's': {
                        X = "asin";
                        id = 4;
                        break;
                    }
                    case 't': {
                        X = "atan";
                        id = 5;
                        break;
                    }
                }
                break;
            }
            case 5: {
                switch (s.charAt(0)) {
                    case 'L': {
                        X = "LOG2E";
                        id = 41;
                        break;
                    }
                    case 'S': {
                        X = "SQRT2";
                        id = 44;
                        break;
                    }
                    case 'a': {
                        char c = s.charAt(1);
                        if (c == 'c') {
                            X = "acosh";
                            id = 30;
                            break;
                        }
                        if (c == 's') {
                            X = "asinh";
                            id = 31;
                            break;
                        }
                        if (c != 't') break;
                        c = s.charAt(4);
                        if (c == '2') {
                            if (s.charAt(2) != 'a' || s.charAt(3) != 'n') break;
                            return 6;
                        }
                        if (c != 'h' || s.charAt(2) != 'a' || s.charAt(3) != 'n') break;
                        return 32;
                    }
                    case 'c': {
                        X = "clz32";
                        id = 36;
                        break;
                    }
                    case 'e': {
                        X = "expm1";
                        id = 22;
                        break;
                    }
                    case 'f': {
                        X = "floor";
                        id = 10;
                        break;
                    }
                    case 'h': {
                        X = "hypot";
                        id = 23;
                        break;
                    }
                    case 'l': {
                        char c = s.charAt(4);
                        if (c == '0') {
                            X = "log10";
                            id = 25;
                            break;
                        }
                        if (c != 'p') break;
                        X = "log1p";
                        id = 24;
                        break;
                    }
                    case 'r': {
                        X = "round";
                        id = 16;
                        break;
                    }
                    case 't': {
                        X = "trunc";
                        id = 29;
                        break;
                    }
                }
                break;
            }
            case 6: {
                char c = s.charAt(0);
                if (c == 'L') {
                    X = "LOG10E";
                    id = 42;
                    break;
                }
                if (c == 'f') {
                    X = "fround";
                    id = 35;
                    break;
                }
                if (c != 'r') break;
                X = "random";
                id = 15;
                break;
            }
            case 7: {
                X = "SQRT1_2";
                id = 43;
                break;
            }
            case 8: {
                X = "toSource";
                id = 1;
            }
        }
        if (X == null) return id;
        if (X == s) return id;
        if (X.equals(s)) return id;
        return 0;
    }
}

