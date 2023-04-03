/*
 * Decompiled with CFR 0.152.
 */
package bsh;

import bsh.BshScriptEngineFactory;
import bsh.EvalError;
import bsh.ExternalNameSpace;
import bsh.Interpreter;
import bsh.InterpreterError;
import bsh.NameSpace;
import bsh.ParseException;
import bsh.PreparsedScript;
import bsh.ScriptContextEngineView;
import bsh.TargetError;
import bsh.This;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javax.script.AbstractScriptEngine;
import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

public class BshScriptEngine
extends AbstractScriptEngine
implements Compilable,
Invocable {
    static final String engineNameSpaceKey = "org_beanshell_engine_namespace";
    private BshScriptEngineFactory factory;
    private Interpreter interpreter;

    public BshScriptEngine() {
        this((BshScriptEngineFactory)null);
    }

    public BshScriptEngine(BshScriptEngineFactory factory) {
        this.factory = factory;
        this.getInterpreter();
    }

    protected Interpreter getInterpreter() {
        if (this.interpreter == null) {
            this.interpreter = new Interpreter();
            this.interpreter.setNameSpace(null);
        }
        return this.interpreter;
    }

    @Override
    public Object eval(String script, ScriptContext scriptContext) throws ScriptException {
        return this.evalSource(script, scriptContext);
    }

    @Override
    public Object eval(Reader reader, ScriptContext scriptContext) throws ScriptException {
        return this.evalSource(reader, scriptContext);
    }

    private Object evalSource(Object source, ScriptContext scriptContext) throws ScriptException {
        NameSpace contextNameSpace = BshScriptEngine.getEngineNameSpace(scriptContext);
        Interpreter bsh = this.getInterpreter();
        bsh.setNameSpace(contextNameSpace);
        bsh.setOut(this.toPrintStream(scriptContext.getWriter()));
        bsh.setErr(this.toPrintStream(scriptContext.getErrorWriter()));
        try {
            if (source instanceof Reader) {
                return bsh.eval((Reader)source);
            }
            return bsh.eval((String)source);
        }
        catch (ParseException e) {
            throw new ScriptException(e.toString(), e.getErrorSourceFile(), e.getErrorLineNumber());
        }
        catch (TargetError e) {
            ScriptException se = new ScriptException(e.toString(), e.getErrorSourceFile(), e.getErrorLineNumber());
            se.initCause(e.getTarget());
            throw se;
        }
        catch (EvalError e) {
            throw new ScriptException(e.toString(), e.getErrorSourceFile(), e.getErrorLineNumber());
        }
        catch (InterpreterError e) {
            throw new ScriptException(e.toString());
        }
    }

    private PrintStream toPrintStream(Writer writer) {
        return new PrintStream(new WriterOutputStream(writer));
    }

    private static NameSpace getEngineNameSpace(ScriptContext scriptContext) {
        NameSpace ns = (NameSpace)scriptContext.getAttribute(engineNameSpaceKey, 100);
        if (ns == null) {
            ScriptContextEngineView engineView = new ScriptContextEngineView(scriptContext);
            ns = new ExternalNameSpace(null, "javax_script_context", engineView);
            scriptContext.setAttribute(engineNameSpaceKey, ns, 100);
        }
        return ns;
    }

    @Override
    public Bindings createBindings() {
        return new SimpleBindings();
    }

    @Override
    public ScriptEngineFactory getFactory() {
        if (this.factory == null) {
            this.factory = new BshScriptEngineFactory();
        }
        return this.factory;
    }

    @Override
    public CompiledScript compile(String script) throws ScriptException {
        try {
            final PreparsedScript preparsed = new PreparsedScript(script);
            return new CompiledScript(){

                @Override
                public Object eval(ScriptContext context) throws ScriptException {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    ArrayList<Integer> scopes = new ArrayList<Integer>(context.getScopes());
                    Collections.sort(scopes);
                    Collections.reverse(scopes);
                    for (Integer scope : scopes) {
                        map.putAll(context.getBindings(scope));
                    }
                    preparsed.setOut(BshScriptEngine.this.toPrintStream(context.getWriter()));
                    preparsed.setErr(BshScriptEngine.this.toPrintStream(context.getErrorWriter()));
                    try {
                        return preparsed.invoke(map);
                    }
                    catch (EvalError e) {
                        throw BshScriptEngine.this.constructScriptException(e);
                    }
                }

                @Override
                public ScriptEngine getEngine() {
                    return BshScriptEngine.this;
                }
            };
        }
        catch (EvalError e) {
            throw this.constructScriptException(e);
        }
    }

    private ScriptException constructScriptException(EvalError e) {
        return new ScriptException(e.getMessage(), e.getErrorSourceFile(), e.getErrorLineNumber());
    }

    private static String convertToString(Reader reader) throws IOException {
        int len;
        StringBuffer buffer = new StringBuffer(64);
        char[] cb = new char[64];
        while ((len = reader.read(cb)) != -1) {
            buffer.append(cb, 0, len);
        }
        return buffer.toString();
    }

    @Override
    public CompiledScript compile(Reader script) throws ScriptException {
        try {
            return this.compile(BshScriptEngine.convertToString(script));
        }
        catch (IOException e) {
            throw new ScriptException(e);
        }
    }

    @Override
    public Object invokeMethod(Object thiz, String name, Object ... args) throws ScriptException, NoSuchMethodException {
        if (!(thiz instanceof This)) {
            throw new ScriptException("Illegal objec type: " + thiz.getClass());
        }
        This bshObject = (This)thiz;
        try {
            return bshObject.invokeMethod(name, args);
        }
        catch (ParseException e) {
            throw new ScriptException(e.toString(), e.getErrorSourceFile(), e.getErrorLineNumber());
        }
        catch (TargetError e) {
            ScriptException se = new ScriptException(e.toString(), e.getErrorSourceFile(), e.getErrorLineNumber());
            se.initCause(e.getTarget());
            throw se;
        }
        catch (EvalError e) {
            throw new ScriptException(e.toString(), e.getErrorSourceFile(), e.getErrorLineNumber());
        }
        catch (InterpreterError e) {
            throw new ScriptException(e.toString());
        }
    }

    @Override
    public Object invokeFunction(String name, Object ... args) throws ScriptException, NoSuchMethodException {
        return this.invokeMethod(this.getGlobal(), name, args);
    }

    @Override
    public <T> T getInterface(Class<T> clasz) {
        return clasz.cast(this.getGlobal().getInterface(clasz));
    }

    @Override
    public <T> T getInterface(Object thiz, Class<T> clasz) {
        if (!(thiz instanceof This)) {
            throw new IllegalArgumentException("invalid object type: " + thiz.getClass());
        }
        This bshThis = (This)thiz;
        return clasz.cast(bshThis.getInterface(clasz));
    }

    private This getGlobal() {
        return BshScriptEngine.getEngineNameSpace(this.getContext()).getThis(this.getInterpreter());
    }

    class WriterOutputStream
    extends OutputStream {
        Writer writer;

        WriterOutputStream(Writer writer) {
            this.writer = writer;
        }

        @Override
        public void write(int b) throws IOException {
            this.writer.write(b);
        }

        @Override
        public void flush() throws IOException {
            this.writer.flush();
        }

        @Override
        public void close() throws IOException {
            this.writer.close();
        }
    }
}

