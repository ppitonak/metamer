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

import static org.jboss.test.selenium.utils.URLUtils.buildUrl;
import static org.testng.Assert.assertEquals;

import java.net.URL;

import org.richfaces.tests.metamer.ftest.annotations.Use;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestSimple extends AbstractListTest {
    
    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/richList/simple.xhtml");
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
        verifyList();
    }

    @Test
    @Use(field = "rows", value = "ints")
    public void testRowsAttribute() {
        verifyList();
    }

}