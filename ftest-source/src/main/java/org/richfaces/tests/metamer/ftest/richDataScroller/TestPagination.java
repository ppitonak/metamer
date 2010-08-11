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
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.net.URL;

import org.jboss.test.selenium.locator.IdLocator;
import org.jboss.test.selenium.locator.JQueryLocator;
import org.richfaces.tests.metamer.ftest.AbstractMetamerTest;
import org.richfaces.tests.metamer.ftest.annotations.Inject;
import org.richfaces.tests.metamer.ftest.annotations.Use;
import org.richfaces.tests.metamer.ftest.model.DataScroller;
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

    @Inject
    @Use(ints = { 2, 3 })
    int fastStep;

    @Inject
    @Use(ints = { 3, 4 })
    int maxPages;

    @Inject
    @Use("dataScrollerLocator*")
    JQueryLocator dataScrollerLocator;
    JQueryLocator dataScrollerLocator1 = PaginationTester.DATA_SCROLLER_OUTSIDE_TABLE;
    JQueryLocator dataScrollerLocator2 = PaginationTester.DATA_SCROLLER_IN_TABLE_FOOTER;

    PaginationTester paginationTester = new PaginationTester() {

        @Override
        protected void verifyBeforeScrolling() {
            tableText = dataTable.getTableText();
        }

        @Override
        protected void verifyAfterScrolling() {
            assertFalse(tableText.equals(dataTable.getTableText()));
            assertEquals(maxPages, dataScroller.getCountOfVisiblePages());
        }
    };

    IdLocator attributeFastStep = id("form:attributes:fastStepInput");
    IdLocator attributeMaxPages = id("form:attributes:maxPagesInput");

    DataScroller dataScroller = paginationTester.getDataScroller();
    DataTable dataTable = new DataTable(pjq("table.rf-dt[id$=richDataTable]"));

    String tableText;

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/richDataScroller/simple.xhtml");
    }

    @BeforeMethod
    public void prepareComponent() {
        guardHttp(selenium).type(attributeFastStep, String.valueOf(fastStep));
        guardHttp(selenium).type(attributeMaxPages, String.valueOf(maxPages));

        dataScroller.setRoot(dataScrollerLocator);
        dataScroller.setFastStep(fastStep);

        int lastPage = dataScroller.obtainLastPage();
        dataScroller.setLastPage(lastPage);
        paginationTester.initializeTestedPages(lastPage);
    }

    @Test
    public void testNumberedPages() {
        paginationTester.testNumberedPages();
    }
}
