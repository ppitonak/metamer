package org.richfaces.tests.metamer.model.treeAdaptor;

public interface LazyLoadingListener<T> {
    void notify(T instance);
}
