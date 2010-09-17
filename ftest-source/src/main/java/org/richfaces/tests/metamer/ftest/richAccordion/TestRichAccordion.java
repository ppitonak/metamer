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

package org.richfaces.tests.metamer.ftest.richAccordion;

import static org.jboss.test.selenium.guard.request.RequestTypeGuardFactory.guardHttp;
import static org.jboss.test.selenium.guard.request.RequestTypeGuardFactory.guardNoRequest;
import static org.jboss.test.selenium.guard.request.RequestTypeGuardFactory.guardXhr;
import static org.jboss.test.selenium.locator.LocatorFactory.jq;
import static org.jboss.test.selenium.utils.URLUtils.buildUrl;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.net.URL;

import org.jboss.test.selenium.css.CssProperty;
import org.jboss.test.selenium.dom.Event;
import org.jboss.test.selenium.encapsulated.JavaScript;
import org.jboss.test.selenium.locator.Attribute;
import org.jboss.test.selenium.locator.AttributeLocator;
import org.jboss.test.selenium.locator.JQueryLocator;
import org.jboss.test.selenium.waiting.ajax.JavaScriptCondition;
import org.jboss.test.selenium.waiting.conditions.IsDisplayed;
import org.richfaces.tests.metamer.ftest.AbstractMetamerTest;
import org.testng.annotations.Test;

/**
 * Test case for page /faces/components/richAccordion/simple.xhtml
 * 
 * @author <a href="mailto:ppitonak@redhat.com">Pavol Pitonak</a>
 * @version $Revision$
 */
public class TestRichAccordion extends AbstractMetamerTest {

    private JQueryLocator accordion = pjq("div[id$=accordion]");
    private JQueryLocator[] itemHeaders = {pjq("div[id$=item1:header]"), pjq("div[id$=item2:header]"),
        pjq("div[id$=item3:header]"), pjq("div[id$=item4:header]"), pjq("div[id$=item5:header]")};
    private JQueryLocator[] itemContents = {pjq("div[id$=item1:content]"), pjq("div[id$=item2:content]"),
        pjq("div[id$=item3:content]"), pjq("div[id$=item4:content]"), pjq("div[id$=item5:content]")};
    private IsDisplayed isDisplayed = IsDisplayed.getInstance();
    private String[] phasesNames = {"RESTORE_VIEW 1", "APPLY_REQUEST_VALUES 2", "PROCESS_VALIDATIONS 3",
        "UPDATE_MODEL_VALUES 4", "INVOKE_APPLICATION 5", "RENDER_RESPONSE 6"};

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/richAccordion/simple.xhtml");
    }

    @Test
    public void testInit() {
        boolean isDisplayed = selenium.isDisplayed(accordion);
        assertTrue(isDisplayed, "Accordion is not present on the page.");

        for (int i = 0; i < 5; i++) {
            isDisplayed = selenium.isDisplayed(itemHeaders[i]);
            assertTrue(isDisplayed, "Item" + (i + 1) + "'s header should be visible.");
        }

        isDisplayed = selenium.isDisplayed(itemContents[0]);
        assertTrue(isDisplayed, "Content of item1 should be visible.");

        for (int i = 1; i < 5; i++) {
            isDisplayed = selenium.isDisplayed(itemContents[i]);
            assertFalse(isDisplayed, "Item" + (i + 1) + "'s content should not be visible.");
        }
    }

    @Test
    public void testSwitchTypeNull() {
        for (int i = 2; i >= 0; i--) {
            final int index = i;
            guardXhr(selenium).click(itemHeaders[index]);
            waitGui.failWith("Item " + index + " is not displayed.").until(isDisplayed.locator(itemContents[index]));
        }
    }

    @Test
    public void testSwitchTypeAjax() {
        JQueryLocator selectOption = pjq("input[type=radio][id$=switchTypeInput:0]");
        selenium.click(selectOption);
        selenium.waitForPageToLoad();

        testSwitchTypeNull();
    }

    @Test
    public void testSwitchTypeClient() {
        JQueryLocator selectOption = pjq("input[type=radio][id$=switchTypeInput:1]");
        selenium.click(selectOption);
        selenium.waitForPageToLoad();

        for (int i = 2; i >= 0; i--) {
            final int index = i;
            guardNoRequest(selenium).click(itemHeaders[index]);
            waitGui.failWith("Item " + index + " is not displayed.").until(isDisplayed.locator(itemContents[index]));
        }
    }

    @Test
    public void testSwitchTypeServer() {
        JQueryLocator selectOption = pjq("input[type=radio][id$=switchTypeInput:3]");
        selenium.click(selectOption);
        selenium.waitForPageToLoad();

        for (int i = 2; i >= 0; i--) {
            final int index = i;
            guardHttp(selenium).click(itemHeaders[index]);
            waitGui.failWith("Item " + index + " is not displayed.").until(isDisplayed.locator(itemContents[index]));
        }
    }

    @Test
    public void testBypassUpdates() {
        JQueryLocator input = pjq("input[type=checkbox][id$=bypassUpdatesInput]");
        selenium.click(input);
        selenium.waitForPageToLoad();

        selenium.click(itemHeaders[2]);
        waitGui.failWith("Item 3 is not displayed.").until(isDisplayed.locator(itemContents[2]));

        JQueryLocator[] phases = {jq("div#phasesPanel li"), jq("div#phasesPanel li:eq(0)"),
            jq("div#phasesPanel li:eq(1)"), jq("div#phasesPanel li:eq(2)"), jq("div#phasesPanel li:eq(3)")};

        final String msg = "Update model values and Invoke application phases should be skipped.";
        assertEquals(selenium.getCount(phases[0]), 4, msg);
        assertEquals(selenium.getText(phases[1]), phasesNames[0], msg);
        assertEquals(selenium.getText(phases[2]), phasesNames[1], msg);
        assertEquals(selenium.getText(phases[3]), phasesNames[2], msg);
        assertEquals(selenium.getText(phases[4]), phasesNames[5], msg);
    }

    @Test
    public void testCycledSwitching() {
        String accordionId = selenium.getEval(new JavaScript("window.testedComponentId"));
        String result = null;

        // RichFaces.$('form:accordion').nextItem('item4') will be null
        result = selenium.getEval(new JavaScript("window.RichFaces.$('" + accordionId + "').nextItem('item4')"));
        assertEquals(result, "null", "Result of function nextItem('item4')");

        // RichFaces.$('form:accordion').prevItem('item1') will be null
        result = selenium.getEval(new JavaScript("window.RichFaces.$('" + accordionId + "').prevItem('item1')"));
        assertEquals(result, "null", "Result of function prevItem('item1')");

        JQueryLocator input = pjq("input[type=checkbox][id$=cycledSwitchingInput]");
        selenium.click(input);
        selenium.waitForPageToLoad();

        // RichFaces.$('form:accordion').nextItem('item5') will be item1
        result = selenium.getEval(new JavaScript("window.RichFaces.$('" + accordionId + "').nextItem('item5')"));
        assertEquals(result, "item1", "Result of function nextItem('item5')");

        // RichFaces.$('form:accordion').prevItem('item1') will be item5
        result = selenium.getEval(new JavaScript("window.RichFaces.$('" + accordionId + "').prevItem('item1')"));
        assertEquals(result, "item5", "Result of function prevItem('item1')");
    }

    @Test
    public void testDir() {
        JQueryLocator ltrInput = pjq("input[type=radio][id$=dirInput:0]");
        JQueryLocator rtlInput = pjq("input[type=radio][id$=dirInput:2]");
        AttributeLocator<?> dirAttribute = accordion.getAttribute(new Attribute("dir"));

        // dir = null
        assertFalse(selenium.isAttributePresent(dirAttribute), "Attribute dir should not be present.");

        // dir = ltr
        selenium.click(ltrInput);
        selenium.waitForPageToLoad();
        assertTrue(selenium.isAttributePresent(dirAttribute), "Attribute dir should be present.");
        String value = selenium.getAttribute(dirAttribute);
        assertEquals(value, "ltr", "Attribute dir");

        // dir = rtl
        selenium.click(rtlInput);
        selenium.waitForPageToLoad();
        assertTrue(selenium.isAttributePresent(dirAttribute), "Attribute dir should be present.");
        value = selenium.getAttribute(dirAttribute);
        assertEquals(value, "rtl", "Attribute dir");
    }

    @Test
    public void testHeight() {
        JQueryLocator input = pjq("input[type=text][id$=heightInput]");
        AttributeLocator<?> attribute = accordion.getAttribute(new Attribute("style"));

        // height = null
        assertFalse(selenium.isAttributePresent(attribute), "Attribute style should not be present.");

        // height = 300px
        selenium.type(input, "300px");
        selenium.waitForPageToLoad(TIMEOUT);

        assertTrue(selenium.isAttributePresent(attribute), "Attribute style should be present.");
        String value = selenium.getStyle(accordion, CssProperty.HEIGHT);
        assertEquals(value, "300px", "Attribute width");
    }

    @Test
    public void testImmediate() {
        JQueryLocator input = pjq("input[type=checkbox][id$=immediateInput]");
        selenium.click(input);
        selenium.waitForPageToLoad();

        selenium.click(itemHeaders[2]);
        waitGui.failWith("Item 3 is not displayed.").until(isDisplayed.locator(itemContents[2]));

        JQueryLocator[] phases = {jq("div#phasesPanel li"), jq("div#phasesPanel li:eq(0)"),
            jq("div#phasesPanel li:eq(1)"), jq("div#phasesPanel li:eq(2)")};

        final String msg = "Process validations, Update model values and Invoke application phases should be skipped.";
        assertEquals(selenium.getCount(phases[0]), 3, msg);
        assertEquals(selenium.getText(phases[1]), phasesNames[0], msg);
        assertEquals(selenium.getText(phases[2]), phasesNames[1], msg);
        assertEquals(selenium.getText(phases[3]), phasesNames[5], msg);
    }

    @Test
    public void testItemContentClass() {
        testStyleClass(itemContents[2], "itemContentClass");
    }

    @Test
    public void testItemHeaderClass() {
        testStyleClass(itemHeaders[2], "itemHeaderClass");
    }

    @Test
    public void testItemHeaderClassActive() {
        testStyleClass(itemHeaders[0], "itemHeaderClassActive");
    }

    @Test
    public void testItemHeaderClassDisabled() {
        testStyleClass(itemHeaders[3], "itemHeaderClassDisabled");
    }

    @Test
    public void testItemHeaderClassInactive() {
        testStyleClass(itemHeaders[1], "itemHeaderClassInactive");
    }

    @Test
    public void testItemchangeEvents() {
        JQueryLocator obicInput = pjq("input[id$=onbeforeitemchangeInput]");
        JQueryLocator oicInput = pjq("input[id$=onitemchangeInput]");
        final String obicValue = "alert('onbeforeitemchange')";
        final String oicValue = "alert('onitemchange')";

        selenium.type(obicInput, obicValue);
        selenium.waitForPageToLoad(TIMEOUT);
        selenium.type(oicInput, oicValue);
        selenium.waitForPageToLoad(TIMEOUT);

        selenium.click(itemHeaders[2]);

        waitGui.until(new JavaScriptCondition() {
            public JavaScript getJavaScriptCondition() {
                return new JavaScript("selenium.isAlertPresent()");
            }
        });

        assertEquals(selenium.getAlert(), "onbeforeitemchange", "Event beforeitemchange was not fired");

        waitGui.until(new JavaScriptCondition() {
            public JavaScript getJavaScriptCondition() {
                return new JavaScript("selenium.isAlertPresent()");
            }
        });

        assertEquals(selenium.getAlert(), "onitemchange", "Event itemchange was not fired");
    }

    @Test
    public void testLang() {
        JQueryLocator langInput = pjq("input[type=text][id$=langInput]");

        // lang = null
        AttributeLocator<?> langAttr = accordion.getAttribute(new Attribute("xml|lang"));
        assertFalse(selenium.isAttributePresent(langAttr), "Attribute xml:lang should not be present.");

        selenium.type(langInput, "sk");
        selenium.waitForPageToLoad();

        // lang = sk
        langAttr = accordion.getAttribute(new Attribute("lang"));
        assertTrue(selenium.isAttributePresent(langAttr), "Attribute xml:lang should be present.");
        assertEquals(selenium.getAttribute(langAttr), "sk", "Attribute xml:lang should be present.");
    }

    @Test
    public void testOnclick() {
        testFireEvent(Event.CLICK, accordion);
    }

    @Test
    public void testOndblclick() {
        testFireEvent(Event.DBLCLICK, accordion);
    }

    @Test
    public void testOnmousedown() {
        testFireEvent(Event.MOUSEDOWN, accordion);
    }

    @Test
    public void testOnmousemove() {
        testFireEvent(Event.MOUSEMOVE, accordion);
    }

    @Test
    public void testOnmouseout() {
        testFireEvent(Event.MOUSEOUT, accordion);
    }

    @Test
    public void testOnmouseover() {
        testFireEvent(Event.MOUSEOVER, accordion);
    }

    @Test
    public void testOnmouseup() {
        testFireEvent(Event.MOUSEUP, accordion);
    }

    @Test
    public void testRendered() {
        JQueryLocator input = pjq("input[type=checkbox][id$=renderedInput]");
        selenium.click(input);
        selenium.waitForPageToLoad();

        assertFalse(selenium.isElementPresent(accordion), "Accordion should not be rendered when rendered=false.");
    }

    @Test
    public void testStyle() {
        testStyle(accordion);
    }

    @Test
    public void testStyleClass() {
        testStyleClass(accordion, "styleClass");
    }

    @Test
    public void testTitle() {
        JQueryLocator input = pjq("input[type=text][id$=titleInput]");
        AttributeLocator<?> attribute = accordion.getAttribute(new Attribute("title"));

        // title = null
        assertFalse(selenium.isAttributePresent(attribute), "Attribute title should not be present.");

        // title = "RichFaces Accordion"
        selenium.type(input, "RichFaces Accordion");
        selenium.waitForPageToLoad(TIMEOUT);

        assertTrue(selenium.isAttributePresent(attribute), "Attribute title should be present.");
        String value = selenium.getAttribute(attribute);
        assertEquals(value, "RichFaces Accordion", "Attribute title");
    }

    @Test
    public void testWidth() {
        JQueryLocator input = pjq("input[type=text][id$=widthInput]");
        AttributeLocator<?> attribute = accordion.getAttribute(new Attribute("style"));

        // width = null
        assertFalse(selenium.isAttributePresent(attribute), "Attribute style should not be present.");

        // width = 50%
        selenium.type(input, "50%");
        selenium.waitForPageToLoad(TIMEOUT);

        assertTrue(selenium.isAttributePresent(attribute), "Attribute style should be present.");
        String value = selenium.getStyle(accordion, CssProperty.WIDTH);
        assertEquals(value, "50%", "Attribute width");
    }
}
