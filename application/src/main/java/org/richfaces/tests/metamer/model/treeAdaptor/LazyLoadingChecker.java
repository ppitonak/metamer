package org.richfaces.tests.metamer.model.treeAdaptor;

import static java.lang.reflect.Modifier.isStatic;

import java.lang.reflect.Method;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

import org.apache.commons.lang.Validate;

public final class LazyLoadingChecker<T> implements MethodHandler {

    T instance;
    Reference<LazyLoadingListener<T>> listenerReference = null;

    private LazyLoadingChecker(T instance, Reference<LazyLoadingListener<T>> listenerReference) {
        this.instance = instance;
        this.listenerReference = listenerReference;
    }

    @SuppressWarnings("unchecked")
    public static <T> T wrapInstance(T instance, Reference<LazyLoadingListener<T>> listenerReference) {
        Validate.notNull(listenerReference);
        ProxyFactory f = new ProxyFactory();
        f.setSuperclass(instance.getClass());
        Class<?> c = f.createClass();

        T proxy;
        try {
            proxy = (T) c.newInstance();
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }

        ((ProxyObject) proxy).setHandler(new LazyLoadingChecker<T>(instance, listenerReference));
        return proxy;
    }

    @Override
    public Object invoke(Object self, Method m, Method proceed, Object[] args) throws Throwable {
        if (!isStatic(m.getModifiers())) {
            LazyLoadingListener<T> listener = listenerReference.get();
            if (listener != null) {
                listener.notify(instance);
            } else {
                throw new IllegalStateException();
            }
        }
        Object result = m.invoke(instance, args);
        return result;
    }
}
