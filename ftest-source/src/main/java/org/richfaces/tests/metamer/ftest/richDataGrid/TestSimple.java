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
package org.richfaces.tests.metamer.ftest.richDataGrid;

import static org.jboss.test.selenium.guard.request.RequestTypeGuardFactory.guardHttp;
import static org.jboss.test.selenium.utils.URLUtils.buildUrl;
import static java.lang.Math.*;

import java.net.URL;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.jboss.test.selenium.locator.ElementLocator;
import org.jboss.test.selenium.locator.JQueryLocator;
import org.richfaces.tests.metamer.bean.Model;
import org.richfaces.tests.metamer.ftest.AbstractMetamerTest;
import org.richfaces.tests.metamer.ftest.annotations.Inject;
import org.richfaces.tests.metamer.ftest.annotations.Use;
import org.richfaces.tests.metamer.ftest.model.DataGrid;
import org.richfaces.tests.metamer.model.Capital;
import org.richfaces.util.CollectionsUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.inject.internal.Collections2;
import com.google.inject.internal.cglib.core.CollectionUtils;

import static org.jboss.test.selenium.locator.LocatorFactory.*;
import static org.testng.Assert.*;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestSimple extends AbstractMetamerTest {

    private static final int ELEMENTS_TOTAL = 50;

    List<Capital> capitals;

    JQueryLocator attributeColumns = pjq("input[id$=columnsInput]");
    JQueryLocator attributeElements = pjq("input[id$=elementsInput]");
    JQueryLocator attributeFirst = pjq("input[id$=firstInput]");

    DataGrid dataGrid = new DataGrid(jq("table.rf-dg[id$=richDataGrid]"));

    @Inject
    @Use(ints = { 3 })
    Integer columns;

    @Inject
    @Use(empty = true)
    Integer elements;

    @Inject
    @Use(empty = true)
    Integer first;

    @SuppressWarnings("restriction")
    public TestSimple() throws JAXBException {
        capitals = Model.unmarshallCapitals();
    }

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/richDataGrid/simple.xhtml");
    }

    @BeforeMethod
    public void prepareAttributes() {
        prepareAttribute(attributeColumns, columns);
        prepareAttribute(attributeElements, elements);
        prepareAttribute(attributeFirst, first);
    }

    private void prepareAttribute(ElementLocator<?> inputLocator, Object value) {
        String v = value == null ? "" : value.toString();
        guardHttp(selenium).type(inputLocator, v);
    }

    @Test
    @Use(field = "columns", ints = { 1, 3, 11, ELEMENTS_TOTAL / 2, ELEMENTS_TOTAL - 1, ELEMENTS_TOTAL,
        ELEMENTS_TOTAL + 1 })
    public void testColumnsAttribute() {
        verifyCounts();
        verifyElements();
    }

    @Test
    @Use(field = "elements", ints = { 0, 1, ELEMENTS_TOTAL / 2, ELEMENTS_TOTAL - 1, ELEMENTS_TOTAL, ELEMENTS_TOTAL + 1 })
    public void testElementsAttribute() {
        verifyCounts();
        verifyElements();
    }

    @Test
    @Use(field = "first", ints = { 0, 1, ELEMENTS_TOTAL / 2, ELEMENTS_TOTAL - 1, ELEMENTS_TOTAL, ELEMENTS_TOTAL + 1 })
    public void testFirstAttribute() {
        verifyCounts();
        verifyElements();
    }

    private void verifyCounts() {
        int nFirst = first == null ? 0 : min(ELEMENTS_TOTAL, max(0, first));
        int nElements = (elements == null ? ELEMENTS_TOTAL : min(elements, ELEMENTS_TOTAL)) - nFirst;
        int nColumns = columns;
        double nRows = ceil((float) nElements / columns);

        assertEquals(dataGrid.getElementCount(), (int) nElements);
        assertEquals(dataGrid.getColumnCount(), (int) nColumns);
        assertEquals(dataGrid.getRowCount(), (int) nRows);
    }

    private void verifyElements() {
        int nFirst = first == null ? 0 : min(ELEMENTS_TOTAL, max(0, first));
        int nElements = (elements == null ? ELEMENTS_TOTAL : min(elements, ELEMENTS_TOTAL)) - nFirst;

        Iterator<Capital> capitalIterator = capitals.subList(nFirst, nFirst + nElements).iterator();
        Iterator<JQueryLocator> elementIterator = dataGrid.iterateElements();

        while (capitalIterator.hasNext()) {
            final Capital capital = capitalIterator.next();
            if (!elementIterator.hasNext()) {
                fail("there should be next element for state name: " + capital.getState());
            }
            final JQueryLocator element = elementIterator.next().getChild(jq("span"));
            assertEquals(selenium.getText(element), capital.getState());
        }
    }
}
