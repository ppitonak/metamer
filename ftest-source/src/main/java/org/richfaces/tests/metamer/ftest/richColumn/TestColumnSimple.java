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
package org.richfaces.tests.metamer.ftest.richColumn;

import static org.jboss.test.selenium.locator.Attribute.COLSPAN;
import static org.jboss.test.selenium.locator.Attribute.ROWSPAN;
import static org.jboss.test.selenium.utils.URLUtils.buildUrl;
import static org.testng.Assert.assertEquals;

import java.net.URL;

import org.jboss.test.selenium.locator.JQueryLocator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestColumnSimple extends AbstractColumnModelTest {

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/richColumn/simple.xhtml");
    }

    @BeforeMethod
    public void checkInitialState() {
        assertEquals(headerCount(1), 1);
        assertEquals(headerCount(2), 2);
        assertEquals(headerCount(3), 1);
        assertEquals(headerCount(4), 1);
        assertEquals(bodyCount(1), 2);

        assertEquals(selenium.getAttribute(headerCell(1, 1).getAttribute(COLSPAN)), "2");
        assertEquals(selenium.getAttribute(headerCell(2, 2).getAttribute(ROWSPAN)), "2");
    }

    @Test
    public void testBreakRowBefore() {
        attributes.setBreakRowBefore(false);

        assertEquals(headerCount(1), 3);
        assertEquals(headerCount(2), 1);
        assertEquals(headerCount(3), 1);

        assertEquals(selenium.getAttribute(headerCell(1, 1).getAttribute(COLSPAN)), "2");
        assertEquals(selenium.getAttribute(headerCell(1, 3).getAttribute(ROWSPAN)), "2");
    }

    @Test
    public void testColspan() {
        attributes.setColspan(1);

        assertEquals(headerCount(1), 1);
        assertEquals(headerCount(2), 2);
        assertEquals(headerCount(3), 1);
        assertEquals(headerCount(4), 1);

        assertEquals(selenium.getAttribute(headerCell(1, 1).getAttribute(COLSPAN)), "1");
        assertEquals(selenium.getAttribute(headerCell(2, 2).getAttribute(ROWSPAN)), "2");
    }

    @Test
    public void testRowspanTo1() {
        attributes.setRowspan(1);

        assertEquals(headerCount(1), 1);
        assertEquals(headerCount(2), 2);
        assertEquals(headerCount(3), 2);

        assertEquals(selenium.getAttribute(headerCell(1, 1).getAttribute(COLSPAN)), "2");
        assertEquals(selenium.getAttribute(headerCell(2, 2).getAttribute(ROWSPAN)), "1");
    }

    @Test
    public void testRowspanTo3() {
        attributes.setRowspan(3);

        assertEquals(headerCount(1), 1);
        assertEquals(headerCount(2), 2);
        assertEquals(headerCount(3), 1);
        assertEquals(headerCount(4), 1);

        assertEquals(selenium.getAttribute(headerCell(1, 1).getAttribute(COLSPAN)), "2");
        assertEquals(selenium.getAttribute(headerCell(2, 2).getAttribute(ROWSPAN)), "3");
    }

    @Test
    public void testRendered() {
        attributes.setRendered(false);

        assertEquals(headerCount(1), 1);
        assertEquals(headerCount(2), 2);
        assertEquals(headerCount(3), 1);
        assertEquals(headerCount(4), 1);
        assertEquals(bodyCount(1), 1);

        assertEquals(selenium.getAttribute(headerCell(1, 1).getAttribute(COLSPAN)), "2");
        assertEquals(selenium.getAttribute(headerCell(2, 2).getAttribute(ROWSPAN)), "2");
    }

    public JQueryLocator headerCell(int row, int column) {
        return model.getHeaderCell(row, column);
    }

    public int headerCount(int row) {
        return model.getHeaderRowCellCount(row);
    }

    public int bodyCount(int row) {
        return model.getBodyRowCellCount(row);
    }

}
