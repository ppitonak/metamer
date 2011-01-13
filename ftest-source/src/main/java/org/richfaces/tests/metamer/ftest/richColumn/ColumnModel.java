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
package org.richfaces.tests.metamer.ftest.richColumn;

import static org.jboss.test.selenium.locator.LocatorFactory.jq;
import static org.jboss.test.selenium.locator.reference.ReferencedLocator.ref;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.jboss.test.selenium.framework.AjaxSelenium;
import org.jboss.test.selenium.framework.AjaxSeleniumProxy;
import org.jboss.test.selenium.locator.JQueryLocator;
import org.jboss.test.selenium.locator.reference.ReferencedLocator;
import org.richfaces.tests.metamer.ftest.model.AbstractModel;
import org.richfaces.tests.metamer.model.Capital;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class ColumnModel extends AbstractModel<JQueryLocator> {

    AjaxSelenium selenium = AjaxSeleniumProxy.getInstance();

    private ReferencedLocator<JQueryLocator> tableHeader = ref(root, "> thead.rf-dt-thd");
    private ReferencedLocator<JQueryLocator> tableBody = ref(root, "> tbody.rf-dt-b");

    private ReferencedLocator<JQueryLocator> headerRow = ref(tableHeader, "> tr.rf-dt-hdr");
    private ReferencedLocator<JQueryLocator> bodyRow = ref(tableBody, "> tr.rf-dt-r");

    private JQueryLocator headerCell = jq("th.rf-dt-hdr-c");
    private JQueryLocator bodyCell = jq("td.rf-dt-c");

    public ColumnModel(String name, JQueryLocator root) {
        super(name, root);
    }

    public int getBodyRowCellCount(int row) {
        return selenium.getCount(bodyRow.getNthChildElement(row).getChild(bodyCell));
    }

    public int getHeaderRowCellCount(int row) {
        return selenium.getCount(headerRow.getNthChildElement(row).getChild(headerCell));
    }

    public JQueryLocator getBodyCell(int row, int column) {
        return bodyRow.getNthChildElement(row).getChild(bodyCell).getNthChildElement(column);
    }

    public JQueryLocator getHeaderCell(int row, int column) {
        return headerRow.getNthChildElement(row).getChild(headerCell).getNthChildElement(column);
    }

    public Capital getCapital(int index) {
        String state = selenium.getText(getBodyCell(index + 1, 1));
        String name = selenium.getText(getBodyCell(index + 1, 2));
        Capital result = new Capital();
        result.setName(name);
        result.setState(state);
        return result;
    }
    
    public Collection<Capital> getCapitals() {
        int count = getBodyRowCount();
        List<Capital> capitals = new LinkedList<Capital>();
        for (int i = 0; i < count; i++) {
            capitals.add(getCapital(i));
        }
        return capitals;
    }
    
    public int getBodyRowCount() {
        return selenium.getCount(bodyRow.getChild(bodyCell).getNthChildElement(1));
    }
}
