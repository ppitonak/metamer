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
package org.richfaces.tests.metamer.ftest.richToolbar;

import static org.jboss.test.selenium.utils.URLUtils.buildUrl;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.net.URL;

import org.jboss.test.selenium.dom.Event;
import org.jboss.test.selenium.locator.JQueryLocator;
import org.richfaces.tests.metamer.ftest.AbstractMetamerTest;
import org.richfaces.tests.metamer.ftest.annotations.Inject;
import org.richfaces.tests.metamer.ftest.annotations.Use;
import org.testng.annotations.Test;

/**
 * Test case for page /faces/components/richToolbar/simple.xhtml
 * 
 * @author <a href="mailto:ppitonak@redhat.com">Pavol Pitonak</a>
 * @version $Revision$
 */
public class TestRichToolbar extends AbstractMetamerTest {

    private JQueryLocator toolbar = pjq("table[id$=toolbar]");
    private JQueryLocator separator = pjq("td.rf-tb-sep");
    private JQueryLocator[] items = {pjq("td[id$=createDocument_itm]"), pjq("td[id$=createFolder_itm]"),
        pjq("td[id$=copy_itm]"), pjq("td[id$=save_itm]"), pjq("td[id$=saveAs_itm]"), pjq("td[id$=saveAll_itm]"),
        pjq("td[id$=input_itm]"), pjq("td[id$=button_itm]")};
    @Inject
    @Use(empty = true)
    private JQueryLocator item;

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/richToolbar/simple.xhtml");
    }

    @Test
    public void testInit() {
        assertTrue(selenium.isElementPresent(toolbar), "Toolbar should be present on the page.");
        assertTrue(selenium.isVisible(toolbar), "Toolbar should be visible.");
        assertFalse(selenium.isElementPresent(separator), "Item separator should not be present on the page.");
    }

    @Test
    @Use(field = "item", value = "items")
    public void testInitItems() {
        assertTrue(selenium.isElementPresent(item), "Item (" + item + ") should be present on the page.");
        assertTrue(selenium.isVisible(item), "Item (" + item + ") should be visible.");
    }

    @Test
    public void testHeight() {
        selenium.type(pjq("input[id$=heightInput]"), "");
        selenium.waitForPageToLoad();

        testHtmlAttribute(toolbar, "height", "50px");
    }

    @Test
    @Use(field = "item", value = "items")
    public void testItemClass() {
        testStyleClass(item, "itemClass");
    }

    @Test
    @Use(field = "item", value = "items")
    public void testItemStyle() {
        testStyle(item, "itemStyle");
    }

    @Test
    @Use(field = "item", value = "items")
    public void testOnitemclick() {
        testFireEvent(Event.CLICK, item, "itemclick");
    }

    @Test
    @Use(field = "item", value = "items")
    public void testOnitemdblclick() {
        testFireEvent(Event.DBLCLICK, item, "itemdblclick");
    }

    @Test
    @Use(field = "item", value = "items")
    public void testOnitemkeydown() {
        testFireEvent(Event.KEYDOWN, item, "itemkeydown");
    }

    @Test
    @Use(field = "item", value = "items")
    public void testOnitemkeypress() {
        testFireEvent(Event.KEYPRESS, item, "itemkeypress");
    }

    @Test
    @Use(field = "item", value = "items")
    public void testOnitemkeyup() {
        testFireEvent(Event.KEYUP, item, "itemkeyup");
    }

    @Test
    @Use(field = "item", value = "items")
    public void testOnitemmousedown() {
        testFireEvent(Event.MOUSEDOWN, item, "itemmousedown");
    }

    @Test
    @Use(field = "item", value = "items")
    public void testOnitemmousemove() {
        testFireEvent(Event.MOUSEMOVE, item, "itemmousemove");
    }

    @Test
    @Use(field = "item", value = "items")
    public void testOnitemmouseout() {
        testFireEvent(Event.MOUSEOUT, item, "itemmouseout");
    }

    @Test
    @Use(field = "item", value = "items")
    public void testOnitemmouseover() {
        testFireEvent(Event.MOUSEOVER, item, "itemmouseover");
    }

    @Test
    @Use(field = "item", value = "items")
    public void testOnitemmouseup() {
        testFireEvent(Event.MOUSEUP, item, "itemmouseup");
    }

    @Test
    public void testRendered() {
        JQueryLocator input = pjq("input[type=radio][name$=renderedInput][value=false]");
        selenium.click(input);
        selenium.waitForPageToLoad();

        assertFalse(selenium.isElementPresent(toolbar), "Toolbar should not be rendered when rendered=false.");
        assertTrue(selenium.isDisplayed(toolbar), "Toolbar should be displayed when item 1 is not rendered.");
    }

    @Test
    public void testWidth() {
        selenium.type(pjq("input[id$=widthInput]"), "");
        selenium.waitForPageToLoad();

        testHtmlAttribute(toolbar, "width", "700px");
    }
}
