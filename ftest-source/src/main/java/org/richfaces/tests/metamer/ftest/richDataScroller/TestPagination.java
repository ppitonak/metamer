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
package org.richfaces.tests.metamer.ftest.richDataScroller;

import static org.jboss.test.selenium.guard.request.RequestTypeGuardFactory.guardHttp;
import static org.jboss.test.selenium.locator.LocatorFactory.id;
import static org.jboss.test.selenium.utils.URLUtils.buildUrl;
import static org.testng.Assert.*;

import java.net.URL;

import org.jboss.test.selenium.locator.IdLocator;
import org.richfaces.tests.metamer.ftest.AbstractMetamerTest;
import org.richfaces.tests.metamer.ftest.annotations.Inject;
import org.richfaces.tests.metamer.ftest.annotations.Use;
import org.richfaces.tests.metamer.ftest.model.AssertingDataScroller;
import org.richfaces.tests.metamer.ftest.model.DataTable;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test the functionality of switching pages using DataScroller bound to DataTable.
 * 
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestPagination extends AbstractMetamerTest {

    private static final int[] PAGES = new int[] { 3, 6, 1, 4, 6, 2, 4, 5 };

    @Inject
    @Use(value = { "dataScroller*" })
    AssertingDataScroller dataScroller;
    AssertingDataScroller dataScroller1 = new AssertingDataScroller("outside-table", pjq("span.rf-ds[id$=scroller1]"));
    AssertingDataScroller dataScroller2 = new AssertingDataScroller("in-table-footer", pjq("span.rf-ds[id$=scroller2]"));

    IdLocator attributeFastStep = id("form:attributes:fastStepInput");
    IdLocator attributeMaxPages = id("form:attributes:maxPagesInput");

    DataTable dataTable = new DataTable(pjq("table.rf-dt[id$=richDataTable]"));

    @Inject
    @Use(ints = { 2, 3 })
    int fastStep;

    @Inject
    @Use(ints = { 3, 4 })
    int maxPages;

    public TestPagination() {
    }

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/richDataScroller/simple.xhtml");
    }

    @BeforeMethod
    public void prepareAttributes() {
        guardHttp(selenium).type(attributeFastStep, String.valueOf(fastStep));
        guardHttp(selenium).type(attributeMaxPages, String.valueOf(maxPages));
    }

    @Test
    public void testNumberedPagesWithMaxPagesAndFastStep() {
        dataScroller.setFastStep(fastStep);
        dataScroller.setLastPage(dataScroller.getLastPage());

        for (int pageNumber : PAGES) {
            String tableText = dataTable.getTableText();
            dataScroller.gotoPage(pageNumber);
            assertFalse(tableText.equals(dataTable.getTableText()));
            assertEquals(maxPages, dataScroller.getCountOfVisiblePages());
        }
    }
}
