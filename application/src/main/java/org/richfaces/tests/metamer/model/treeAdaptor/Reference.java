package org.richfaces.tests.metamer.model.treeAdaptor;

import java.io.Serializable;

public interface Reference<T> extends Serializable {
    
    public T get();
}
