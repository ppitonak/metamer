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

import static org.jboss.test.selenium.locator.reference.ReferencedLocator.ref;
import static org.jboss.test.selenium.locator.LocatorFactory.*;

import java.util.Iterator;

import org.jboss.test.selenium.framework.AjaxSelenium;
import org.jboss.test.selenium.framework.AjaxSeleniumProxy;
import org.jboss.test.selenium.locator.JQueryLocator;
import org.jboss.test.selenium.locator.reference.ReferencedLocator;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class DataGrid extends AbstractModel<JQueryLocator> {

    AjaxSelenium selenium = AjaxSeleniumProxy.getInstance();

    ReferencedLocator<JQueryLocator> rows = ref(root, "> tbody.rf-dg-body > tr.rf-dg-r");
    JQueryLocator rowToNonEmptyElement = jq("td.rf-dg-c:not(:empty)");
    JQueryLocator rowToElement = jq("td.rf-dg-c");

    public DataGrid(JQueryLocator root) {
        super(root);
    }

    public DataGrid(String name, JQueryLocator root) {
        super(name, root);
    }

    public int getElementCount() {
        return selenium.getCount(rows.getChild(rowToNonEmptyElement));
    }

    public int getColumnCount() {
        return selenium.getCount(rows.getNthOccurence(1).getChild(rowToElement));
    }

    public int getRowCount() {
        return selenium.getCount(rows.getChild(rowToElement).getNthChildElement(1));
    }

    public JQueryLocator getElementOnCoordinates(int row, int column) {
        return rows.getNthOccurence(row).getChild(rowToNonEmptyElement).getNthChildElement(column);
    }

    public JQueryLocator getElementOnIndex(int index) {
        return rows.getChild(rowToNonEmptyElement).getNthOccurence(index);
    }

    public Iterator<JQueryLocator> iterateElements() {
        return rows.getDescendants(rowToNonEmptyElement).iterator();
    }
}
