/*******************************************************************************
 * JBoss, Home of Professional Open Source
 * Copyright 2010-2011, Red Hat, Inc. and individual contributors
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
package org.richfaces.tests.metamer.ftest.richList;

import org.jboss.test.selenium.locator.ExtendedLocator;
import org.jboss.test.selenium.locator.JQueryLocator;
import org.richfaces.component.ListType;
import org.richfaces.tests.metamer.ftest.AbstractComponentAttributes;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class ListAttributes extends AbstractComponentAttributes {

    public ListAttributes() {
    }

    public ListAttributes(ExtendedLocator<JQueryLocator> root) {
        super(root);
    }

    public void setFirst(int first) {
        setProperty("first", first);
    }

    public void setRendered(String rendered) {
        setProperty("rendered", rendered);
    }

    public void setRows(int rows) {
        setProperty("rows", rows);
    }

    public void setDir(String dir) {
        setProperty("dir", dir);
    }

    public void setFirst(Integer first) {
        setProperty("first", first);
    }

    public void setLang(String lang) {
        setProperty("lang", lang);
    }

    public void setRowClass(String rowClass) {
        setProperty("rowClass", rowClass);
    }

    public void setRows(Integer rows) {
        setProperty("rows", rows);
    }

    public void setStyle(String style) {
        setProperty("style", style);
    }

    public void setStyleClass(String styleClass) {
        setProperty("styleClass", styleClass);
    }

    public void setTitle(String title) {
        setProperty("title", title);
    }

    public void setType(ListType type) {
        setProperty("type", type);
    }

}
