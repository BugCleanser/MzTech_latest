/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.junit.experimental.categories.Category
 *  org.junit.runners.BlockJUnit4ClassRunner
 *  org.junit.runners.model.FrameworkMethod
 *  org.junit.runners.model.InitializationError
 */
package bsh;

import bsh.TestFilter;
import java.util.Iterator;
import java.util.List;
import org.junit.experimental.categories.Category;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

public class FilteredTestRunner
extends BlockJUnit4ClassRunner {
    public FilteredTestRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    protected List<FrameworkMethod> getChildren() {
        List children = super.getChildren();
        Iterator iterator = children.iterator();
        block3: while (iterator.hasNext()) {
            Class[] value;
            FrameworkMethod child = (FrameworkMethod)iterator.next();
            Category category = (Category)child.getAnnotation(Category.class);
            if (category == null) continue;
            for (Class categoryClass : value = category.value()) {
                if (!TestFilter.class.isAssignableFrom(categoryClass)) continue;
                try {
                    TestFilter testFilter = (TestFilter)categoryClass.newInstance();
                    if (!testFilter.skip()) continue;
                    System.out.println("skipping test " + child.getMethod() + " due filter " + categoryClass.getSimpleName());
                    iterator.remove();
                    continue block3;
                }
                catch (InstantiationException e) {
                    throw new AssertionError((Object)e);
                }
                catch (IllegalAccessException e) {
                    throw new AssertionError((Object)e);
                }
            }
        }
        return children;
    }
}

