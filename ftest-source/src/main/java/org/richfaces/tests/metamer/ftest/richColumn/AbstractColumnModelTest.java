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

import java.util.List;

import org.jboss.test.selenium.locator.JQueryLocator;
import org.richfaces.tests.metamer.bean.Model;
import org.richfaces.tests.metamer.ftest.AbstractMetamerTest;
import org.richfaces.tests.metamer.model.Capital;
import org.testng.annotations.BeforeMethod;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public abstract class AbstractColumnModelTest extends AbstractMetamerTest {

    JQueryLocator table = pjq("table.rf-dt[id$=richDataTable]");
    JQueryLocator header = table.getChild(jq("thead.rf-dt-thd"));

    JQueryLocator headerRow = header.getChild(jq("tr.rf-dt-hdr"));
    JQueryLocator headerCell = jq("th.rf-dt-hdr-c");

    JQueryLocator bodyRow = table.getChild(jq("tbody.rf-dt-b")).getChild(jq("tr.rf-dt-r"));
    JQueryLocator bodyCell = jq("td.rf-dt-c");

    ColumnAttributes attributes = new ColumnAttributes();

    List<Capital> capitals;

    @BeforeMethod
    public void prepareModel() {
        capitals = Model.unmarshallCapitals();
    }

    public Capital getCapital(int index) {
        String state = selenium.getText(bodyCell(index + 1, 1));
        String name = selenium.getText(bodyCell(index + 1, 2));
        Capital result = new Capital();
        result.setName(name);
        result.setState(state);
        return result;
    }

    public JQueryLocator bodyCell(int row, int column) {
        return bodyRow.getNthChildElement(row).getChild(bodyCell).getNthChildElement(column);
    }
}
