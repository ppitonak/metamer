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
package org.richfaces.tests.metamer.ftest.richMessage;

import static org.jboss.test.selenium.guard.request.RequestTypeGuardFactory.guardHttp;
import static org.jboss.test.selenium.locator.LocatorFactory.jq;
import static org.jboss.test.selenium.locator.reference.ReferencedLocator.ref;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.jboss.test.selenium.dom.Event;
import org.jboss.test.selenium.locator.Attribute;
import org.jboss.test.selenium.locator.AttributeLocator;
import org.jboss.test.selenium.locator.ElementLocator;
import org.jboss.test.selenium.locator.ExtendedLocator;
import org.jboss.test.selenium.locator.JQueryLocator;
import org.jboss.test.selenium.waiting.EventFiredCondition;
import org.richfaces.tests.metamer.ftest.AbstractMetamerTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  Common test case for rich:message component
 *
 * @author <a href="mailto:jjamrich@redhat.com">Jan Jamrich</a>
 * @version $Revision$
 */
public abstract class AbstractRichMessageTest extends AbstractMetamerTest {

    private static Logger logger = LoggerFactory.getLogger(AbstractRichMessageTest.class);
    protected RichMessageComponentAttributes attributes = new RichMessageComponentAttributes();
    // controls 
    protected JQueryLocator wrongValuesBtn = pjq("input[type=button][id$=setWrongValuesButton]");
    protected JQueryLocator correctValuesBtn = pjq("input[type=button][id$=setCorrectValuesButton]");
    protected JQueryLocator hCommandBtn = pjq("input[id$=hButton]");
    protected JQueryLocator a4jCommandBtn = pjq("input[id$=a4jButton]");
    // component's locators
    protected static JQueryLocator message4Input1 = pjq("span[id$=simpleInputMsg1]");
    protected static JQueryLocator message4Input2 = pjq("span[id$=simpleInputMsg2]");
    protected static JQueryLocator messages = pjq("span[id$=msgs]");

    /**
     * Because of message and messages have many attributes very similar,
     * this method helps test method distinguish between metamer
     * page for rich:message and rich:messages (there are different element IDs
     * per page and for messages there are messages container rendered
     * conditionally) 
     * @return ElementLocator for container with rich:message(s)
     */
    public abstract JQueryLocator getTestElemLocator();

    /**
     * This method implementation specific for rich:message and rich:messages
     * help distinguish between them, and return correct locator
     * @return JQueryLocator for container with summary or detail of message(s) component
     */
    public abstract JQueryLocator getSummaryElemLocator();

    public abstract JQueryLocator getDetailElemLocator();

    public void testHtmlAttribute(ElementLocator<?> element, RichMessageAttributes attribute, String value) {

        AttributeLocator<?> attr = element.getAttribute(new Attribute(attribute.toString()));

        selenium.type(pjq("input[id$=" + attribute + "Input]"), value);
        if (logger.isDebugEnabled()) {
            logger.debug(" ######## attribute = " + attribute);
        }

        selenium.waitForPageToLoad();

        // generate validation message 
        generateValidationMessages(false);

        assertTrue(selenium.getAttribute(attr).contains(value), "Attribute " + attribute + " should contain \"" + value
                + "\".");
    }

    /**
     * A helper method for testing javascripts events. It sets alert('testedevent') to the input field for given event
     * and fires the event. Then it checks the message in the alert dialog.
     * 
     * @param event
     *            JavaScript event to be tested
     * @param element
     *            locator of tested element
     */
    @Override
    protected void testFireEvent(Event event, ElementLocator<?> element) {
        ElementLocator<?> eventInput = pjq("input[id$=on" + event.getEventName() + "Input]");
        String value = "metamerEvents += \"" + event.getEventName() + " \"";

        guardHttp(selenium).type(eventInput, value);

        // generate validation messages
        generateValidationMessages(false);

        selenium.fireEvent(element, event);

        waitGui.failWith("Attribute on" + event.getEventName() + " does not work correctly").until(
                new EventFiredCondition(event));
    }

    /**
     * A helper method for testing attribute "class". It sets "metamer-ftest-class" to the input field and checks that
     * it was changed on the page.
     * 
     * @param element
     *            locator of tested element
     * @param attribute
     *            name of the attribute that will be set (e.g. styleClass, headerClass, itemContentClass)
     */
    @Override
    protected void testStyleClass(ExtendedLocator<JQueryLocator> element, String attribute) {
        final String styleClass = "metamer-ftest-class";
        selenium.type(ref(attributesRoot, "input[id$=" + attribute + "Input]"), styleClass);
        selenium.waitForPageToLoad();

        generateValidationMessages(false);

        JQueryLocator elementWhichHasntThatClass = jq(element.getRawLocator() + ":not(.{0})").format(styleClass);
        assertTrue(selenium.isElementPresent(element));
        assertFalse(selenium.isElementPresent(elementWhichHasntThatClass));
    }

    /**
     * Set wrong values into appropriate inputs and generate validation
     * messages by submitting form.
     * 
     * There are 2 possible ways how to submit: by h:commandButton
     * or by a4j:commandButton. Switch between them is done by 'byAjax' param
     * 
     * @param Boolean <b>byAjax</b> - use to choose submit button type used to submit form 
     */
    public void generateValidationMessages(Boolean byAjax) {
        waitModel.until(elementPresent.locator(wrongValuesBtn));
        selenium.click(wrongValuesBtn);
        if (byAjax) {
            waitModel.until(elementPresent.locator(a4jCommandBtn));
            selenium.click(a4jCommandBtn);
        } else {
            waitModel.until(elementPresent.locator(hCommandBtn));
            selenium.click(hCommandBtn);
            selenium.waitForPageToLoad();
        }
    }

    protected void waitForAttribute(RichMessageAttributes attr) {
        waitGui.until(attributeEquals.locator(getTestElemLocator().getAttribute(new Attribute(attr.toString()))).text(attr.toString()));
    }

    // ==================== test methods ====================
    /**
     * ajaxRendered attribute change behavior: messages are displayed
     * after action performed by a4j:button (not only by h:command*)
     */
    public void testAjaxRendered() {
        // with set to false, element with id$=simpleInputMsg shouldn't appear

        // by default is ajaxRendered set to true
        generateValidationMessages(true);
        waitGui.until(elementPresent.locator(getTestElemLocator()));

        // then disable ajaxRendered
        attributes.setAjaxRendered(Boolean.FALSE);
        generateValidationMessages(true);
        waitGui.until(isNotDisplayed.locator(getTestElemLocator()));
    }

    /**
     * This attribute could disable displaying message
     */
    public void testRendered() {
        // with set to false, element with id$=simpleInputMsg shouldn't appear

        attributes.setRendered(Boolean.TRUE);
        generateValidationMessages(false);
        waitGui.until(elementPresent.locator(getTestElemLocator()));

        // now disable rendering message
        attributes.setRendered(Boolean.FALSE);
        generateValidationMessages(false);
        waitGui.until(isNotDisplayed.locator(getTestElemLocator()));
    }

    /**
     * Attribute for managing display Summary
     */
    public void testShowSummary() {
        // span with class=rf-msg-sum should appear when set to true

        attributes.setShowSummary(Boolean.TRUE);
        generateValidationMessages(false);
        waitModel.until(elementPresent.locator(getSummaryElemLocator()));

        attributes.setShowSummary(Boolean.FALSE);
        generateValidationMessages(false);
        waitGui.until(isNotDisplayed.locator(getSummaryElemLocator()));
    }

    /**
     * Attribute for managing display Detail
     */
    public void testShowDetail() {
        // span with class=rf-msg-det should appear when set to true

        attributes.setShowDetail(Boolean.TRUE);
        generateValidationMessages(false);
        waitGui.until(elementPresent.locator(getDetailElemLocator()));

        attributes.setShowDetail(Boolean.FALSE);
        generateValidationMessages(false);
        waitGui.until(isNotDisplayed.locator(getDetailElemLocator()));
    }

    public void testTitle() {
        testHtmlAttribute(getTestElemLocator(), RichMessageAttributes.TITLE, "Title test");
    }

    public void testDir() {
        testHtmlAttribute(getTestElemLocator(), RichMessageAttributes.DIR, "rtl");
    }

    public void testLang() {
        testHtmlAttribute(getTestElemLocator(), RichMessageAttributes.LANG, "US.en");
    }

    public void testStyle() {
        testHtmlAttribute(getTestElemLocator(), RichMessageAttributes.STYLE, "color: blue;");
    }

    public void testStyleClass() {
        // attribute styleClass is propagated as class attribute in target HTML element 
        testStyleClass(getTestElemLocator(), RichMessageAttributes.STYLE_CLASS.toString());
    }

    public void testOnClick() {
        testFireEvent(Event.CLICK, getTestElemLocator());
    }

    public void testOnDblClick() {
        testFireEvent(Event.DBLCLICK, getTestElemLocator());
    }

    public void testOnKeyDown() {
        testFireEvent(Event.KEYDOWN, getTestElemLocator());
    }

    public void testOnKeyPress() {
        testFireEvent(Event.KEYPRESS, getTestElemLocator());
    }

    public void testOnKeyUp() {
        testFireEvent(Event.KEYUP, getTestElemLocator());
    }

    public void testOnMouseDown() {
        testFireEvent(Event.MOUSEDOWN, getTestElemLocator());
    }

    public void testOnMouseMove() {
        testFireEvent(Event.MOUSEMOVE, getTestElemLocator());
    }

    public void testOnMouseOut() {
        testFireEvent(Event.MOUSEOUT, getTestElemLocator());
    }

    public void testOnMouseOver() {
        testFireEvent(Event.MOUSEOVER, getTestElemLocator());
    }

    public void testOnMouseUp() {
        testFireEvent(Event.MOUSEUP, getTestElemLocator());
    }
}
