/*******************************************************************************
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
 *******************************************************************************/
package org.richfaces.tests.metamer.ftest.model;

import org.jboss.test.selenium.locator.IterableLocator;
import org.jboss.test.selenium.locator.reference.LocatorReference;

/**
 * Abstract DataModel defining root reference for deriving all other locators.
 * 
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 * 
 * @param <T>
 *            the iterable locator type of root
 */
public abstract class AbstractModel<T extends IterableLocator<T>> {

    protected LocatorReference<T> root = new LocatorReference<T>(null);
    private String name = this.getClass().getSimpleName();

    public AbstractModel(T root) {
        setRoot(root);
    }

    public AbstractModel(String name, T root) {
        setRoot(root);
        this.name = name;
    }

    public void setRoot(T root) {
        this.root.setLocator(root);
    }

    public T getRoot() {
        return root.getLocator();
    }

    @Override
    public String toString() {
        return name;
    }
}
