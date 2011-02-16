package org.richfaces.tests.metamer.model.treeAdaptor;

import java.io.Serializable;

public interface LazyLoadingListener<T> extends Serializable {
    void notify(T instance);
}
