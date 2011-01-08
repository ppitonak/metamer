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
package org.richfaces.tests.metamer.ftest.richList;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static org.jboss.test.selenium.dom.Event.CLICK;
import static org.jboss.test.selenium.dom.Event.DBLCLICK;
import static org.jboss.test.selenium.dom.Event.KEYDOWN;
import static org.jboss.test.selenium.dom.Event.KEYPRESS;
import static org.jboss.test.selenium.dom.Event.KEYUP;
import static org.jboss.test.selenium.dom.Event.MOUSEDOWN;
import static org.jboss.test.selenium.dom.Event.MOUSEMOVE;
import static org.jboss.test.selenium.dom.Event.MOUSEOUT;
import static org.jboss.test.selenium.dom.Event.MOUSEOVER;
import static org.jboss.test.selenium.dom.Event.MOUSEUP;
import static org.jboss.test.selenium.locator.LocatorFactory.jq;
import static org.jboss.test.selenium.utils.URLUtils.buildUrl;
import static org.testng.Assert.assertEquals;
import static org.richfaces.tests.metamer.ftest.richList.ListAttributes.Type;
import static org.richfaces.tests.metamer.ftest.richList.ListAttributes.Type.*;

import java.net.URL;
import java.util.List;

import org.jboss.test.selenium.dom.Event;
import org.richfaces.tests.metamer.bean.Model;
import org.richfaces.tests.metamer.ftest.AbstractMetamerTest;
import org.richfaces.tests.metamer.ftest.annotations.Inject;
import org.richfaces.tests.metamer.ftest.annotations.Use;
import org.richfaces.tests.metamer.model.Employee;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestSimple extends AbstractMetamerTest {

    protected static List<Employee> EMPLOYESS = Model.unmarshallEmployees();
    protected static final int ELEMENTS_TOTAL = EMPLOYESS.size();
    protected static final Event[] events = { CLICK, DBLCLICK, KEYDOWN, KEYPRESS, KEYUP, MOUSEDOWN, MOUSEMOVE,
        MOUSEOUT, MOUSEOVER, MOUSEUP };
    protected static final Integer[] ints = { -1, 0, 1, ELEMENTS_TOTAL / 2, ELEMENTS_TOTAL - 1, ELEMENTS_TOTAL,
        ELEMENTS_TOTAL + 1 };

    ListModel list;
    ListAttributes attributes = new ListAttributes();

    @Inject
    @Use(empty = true)
    Integer first;

    @Inject
    @Use(empty = true)
    Integer rows;

    @Inject
    @Use(empty = true)
    Event event;

    Type type = ORDERED;

    int expectedBegin;
    int displayedRows;
    int expectedEnd;
    List<Employee> expectedEmployees;

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/richList/simple.xhtml");
    }

    @BeforeMethod(alwaysRun = true)
    public void prepareAttributes() {
        list = new ListModel(jq("*[id$=richList]"));
        attributes.setType(type);
        list.setType(type);

        if (rows == null) {
            rows = 20;
        }

        if (first != null) {
            attributes.setFirst(first);
        }
        if (rows != null) {
            attributes.setRows(rows);
        }
    }

    @Test
    public void testRenderedAttribute() {
        attributes.setRendered("false");
        assertEquals(list.isRendered(), false);
    }

    @Test
    public void testDirAttribute() {
        testDir(list.getRoot());
    }

    @Test
    public void testLangAttribute() {
        testLang(list.getRoot());
    }

    @Test
    public void testStyleClassAttribute() {
        testStyleClass(list.getRoot(), "styleClass");
    }

    @Test
    public void testRowClassesAttribute() {
        testStyleClass(list.getRow(1), "rowClass");
        testStyleClass(list.getRow(rows), "rowClass");
    }

    @Test
    public void testStyle() {
        testStyle(list.getRoot(), "style");
    }

    @Test
    public void testTitle() {
        testTitle(list.getRoot());
    }

    @Test
    @Use(field = "event", value = "events")
    public void testEvent() {
        testFireEvent(event, list.getRoot());
    }

    @Test
    @Use(field = "event", value = "events")
    public void testRowEvent() {
        testFireEvent(event, list.getRow(1), "row" + event.getEventName());
        testFireEvent(event, list.getRow(rows), "row" + event.getEventName());
    }

    @Test
    @Use(field = "first", value = "ints")
    public void testFirstAttribute() {
        verifyRepeat();
    }

    @Test
    @Use(field = "rows", value = "ints")
    public void testRowsAttribute() {
        verifyRepeat();
    }

    private void verifyRepeat() {
        countExpectedValues();
        verifyCounts();
        verifyRows();
    }

    private void verifyCounts() {
        assertEquals(list.getTotalRowCount(), displayedRows);
    }

    private void verifyRows() {
        int rowCount = list.getTotalRowCount();
        for (int position = 1; position <= rowCount; position++) {
            Employee employee = expectedEmployees.get(position - 1);
            assertEquals(employee.getName(), list.getRowText(position));
        }
    }

    private void countExpectedValues() {

        // expected begin

        if (first == null || first < 0) {
            expectedBegin = 0;
        } else {
            expectedBegin = first;
        }

        expectedBegin = minMax(0, expectedBegin, ELEMENTS_TOTAL);

        // expected displayed rows

        if (rows == null || rows < 1 || rows > ELEMENTS_TOTAL) {
            displayedRows = ELEMENTS_TOTAL;
        } else {
            displayedRows = rows;
        }

        if (first != null && first < 0) {
            displayedRows = 0;
        }

        displayedRows = min(displayedRows, ELEMENTS_TOTAL - expectedBegin);

        // expected end

        if (rows == null || rows < 1) {
            expectedEnd = ELEMENTS_TOTAL - 1;
        } else {
            expectedEnd = rows - 1;
        }

        expectedEmployees = EMPLOYESS.subList(expectedBegin, expectedBegin + displayedRows);
    }

    private int minMax(int min, int value, int max) {
        return max(0, min(max, value));
    }
}