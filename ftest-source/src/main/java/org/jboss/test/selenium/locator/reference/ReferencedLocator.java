/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.test.selenium.locator.reference;

import org.jboss.test.selenium.locator.AbstractElementLocator;
import org.jboss.test.selenium.locator.CompoundableLocator;
import org.jboss.test.selenium.locator.ElementLocationStrategy;
import org.jboss.test.selenium.locator.IterableLocator;
import org.jboss.test.selenium.utils.text.SimplifiedFormat;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 * 
 * @param <T>
 *            type of referenced locator
 */
public class ReferencedLocator<T extends IterableLocator<T> & CompoundableLocator<T>> extends AbstractElementLocator<T>
    implements IterableLocator<T>, CompoundableLocator<T> {

    private LocatorReference<T> reference;
    private String addition;

    public ReferencedLocator(LocatorReference<T> reference, String locator) {
        super("not-used");
        this.reference = reference;
        this.addition = locator;
    }

    public static <N extends IterableLocator<N> & CompoundableLocator<N>> ReferencedLocator<N> ref(
        LocatorReference<N> reference, String locator) {
        return new ReferencedLocator<N>(reference, locator);
    }

    public T getReferenced() {
        T referencedLocator = reference.getLocator();

        @SuppressWarnings("unchecked")
        Class<T> tClass = (Class<T>) referencedLocator.getClass();

        try {
            T newInstance = tClass.getConstructor(String.class).newInstance(addition);

            return referencedLocator.getDescendant(newInstance);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String getRawLocator() {
        return getReferenced().getRawLocator();
    }

    public ElementLocationStrategy getLocationStrategy() {
        return reference.getLocator().getLocationStrategy();
    }

    public T getChild(T elementLocator) {
        return getReferenced().getChild(elementLocator);
    }

    public T getDescendant(T elementLocator) {
        return getReferenced().getDescendant(elementLocator);
    }

    public T getNthChildElement(int index) {
        return getReferenced().getNthChildElement(index);
    }

    public T getNthOccurence(int index) {
        return getReferenced().getNthOccurence(index);
    }

    public Iterable<T> getAllChildren() {
        return getReferenced().getAllChildren();
    }

    public Iterable<T> getChildren(T elementLocator) {
        return getReferenced().getChildren(elementLocator);
    }

    public Iterable<T> getDescendants(T elementLocator) {
        return getReferenced().getDescendants(elementLocator);
    }

    @SuppressWarnings("unchecked")
    public T format(Object... args) {
        String newAddition = SimplifiedFormat.format(addition, args);
        try {
            return (T) this.getClass().getConstructor(LocatorReference.class, String.class)
                .newInstance(reference, newAddition);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
