/*
 * Decompiled with CFR 0.152.
 */
package bsh;

import java.lang.reflect.Array;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

public final class CollectionManager {
    private static final CollectionManager manager = new CollectionManager();

    public static synchronized CollectionManager getCollectionManager() {
        return manager;
    }

    public boolean isBshIterable(Object obj) {
        try {
            this.getBshIterator(obj);
            return true;
        }
        catch (IllegalArgumentException e) {
            return false;
        }
    }

    public Iterator getBshIterator(Object obj) throws IllegalArgumentException {
        if (obj == null) {
            throw new NullPointerException("Cannot iterate over null.");
        }
        if (obj instanceof Enumeration) {
            final Enumeration enumeration = (Enumeration)obj;
            return new Iterator<Object>(){

                @Override
                public boolean hasNext() {
                    return enumeration.hasMoreElements();
                }

                @Override
                public Object next() {
                    return enumeration.nextElement();
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }
        if (obj instanceof Iterator) {
            return (Iterator)obj;
        }
        if (obj instanceof Iterable) {
            return ((Iterable)obj).iterator();
        }
        if (obj.getClass().isArray()) {
            final Object array = obj;
            return new Iterator(){
                private int index = 0;
                private final int length = Array.getLength(array);

                @Override
                public boolean hasNext() {
                    return this.index < this.length;
                }

                public Object next() {
                    return Array.get(array, this.index++);
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }
        if (obj instanceof CharSequence) {
            return this.getBshIterator(obj.toString().toCharArray());
        }
        throw new IllegalArgumentException("Cannot iterate over object of type " + obj.getClass());
    }

    public boolean isMap(Object obj) {
        return obj instanceof Map;
    }

    public Object getFromMap(Object map, Object key) {
        return ((Map)map).get(key);
    }

    public Object putInMap(Object map, Object key, Object value) {
        return ((Map)map).put(key, value);
    }
}

