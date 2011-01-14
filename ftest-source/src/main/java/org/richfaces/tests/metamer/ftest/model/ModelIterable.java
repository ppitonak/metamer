package org.richfaces.tests.metamer.ftest.model;

import java.lang.reflect.Constructor;
import java.util.Iterator;

import org.jboss.test.selenium.locator.ExtendedLocator;

public class ModelIterable<E extends ExtendedLocator<E>, T extends AbstractModel<E>> implements Iterable<T> {

    Iterable<E> iterable;
    Class<T> classT;

    public ModelIterable(Iterable<E> iterable, Class<T> classT) {
        this.iterable = iterable;
        this.classT = classT;
    }

    @Override
    public Iterator<T> iterator() {
        return new ModelIterator();
    }

    public class ModelIterator implements Iterator<T> {

        Iterator<E> iterator = iterable.iterator();

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public T next() {
            ExtendedLocator<E> locator = iterator.next();

            try {
                Constructor<T> constructor = classT.getConstructor(ExtendedLocator.class);
                T newInstance = constructor.newInstance(locator);
                return newInstance;
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }

        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("unsupported operation");
        }
    }

}
