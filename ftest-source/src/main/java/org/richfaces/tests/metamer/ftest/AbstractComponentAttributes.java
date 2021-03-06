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
package org.richfaces.tests.metamer.ftest;

import static org.jboss.test.selenium.guard.request.RequestTypeGuardFactory.guard;
import static org.jboss.test.selenium.locator.Attribute.VALUE;
import static org.jboss.test.selenium.locator.LocatorFactory.jq;
import static org.jboss.test.selenium.locator.reference.ReferencedLocator.referenceInferred;
import static org.richfaces.tests.metamer.ftest.AbstractMetamerTest.pjq;

import org.jboss.test.selenium.dom.Event;
import org.jboss.test.selenium.framework.AjaxSelenium;
import org.jboss.test.selenium.framework.AjaxSeleniumProxy;
import org.jboss.test.selenium.interception.CommandContext;
import org.jboss.test.selenium.interception.CommandInterceptionException;
import org.jboss.test.selenium.interception.CommandInterceptor;
import org.jboss.test.selenium.locator.Attribute;
import org.jboss.test.selenium.locator.AttributeLocator;
import org.jboss.test.selenium.locator.ElementLocator;
import org.jboss.test.selenium.locator.ExtendedLocator;
import org.jboss.test.selenium.locator.JQueryLocator;
import org.jboss.test.selenium.locator.option.OptionValueLocator;
import org.jboss.test.selenium.locator.reference.LocatorReference;
import org.jboss.test.selenium.locator.reference.ReferencedLocator;
import org.jboss.test.selenium.request.RequestType;
import org.jboss.test.selenium.waiting.Wait;

import com.thoughtworks.selenium.SeleniumException;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class AbstractComponentAttributes {

    protected AjaxSelenium selenium = AjaxSeleniumProxy.getInstance();
    LocatorReference<ExtendedLocator<JQueryLocator>> root = new LocatorReference<ExtendedLocator<JQueryLocator>>(
        pjq(""));
    ReferencedLocator<JQueryLocator> propertyLocator = referenceInferred(root, ":input[id*=:{0}Input]{1}");

    RequestType requestType = RequestType.HTTP;

    public AbstractComponentAttributes() {
    }

    public <T extends ExtendedLocator<JQueryLocator>> AbstractComponentAttributes(T root) {
        this.root.setLocator(root);
    }

    public ExtendedLocator<JQueryLocator> getRoot() {
        return root.getLocator();
    }

    public void setRoot(ExtendedLocator<JQueryLocator> root) {
        this.root.setLocator(root);
    }

    protected String getProperty(String propertyName) {
        final ReferencedLocator<JQueryLocator> locator = propertyLocator.format(propertyName, "");
        if (selenium.getCount(locator) > 1) {
            return selenium.getAttribute(propertyLocator.format(propertyName, "[checked]").getAttribute(VALUE));
        }
        return selenium.getValue(locator);
    }

    protected void setProperty(String propertyName, Object value) {
        selenium.getInterceptionProxy().registerInterceptor(new RepeatForElementNotFound());

        ExtendedLocator<JQueryLocator> locator = propertyLocator.format(propertyName, "");
        final AttributeLocator<?> typeLocator = locator.getAttribute(Attribute.TYPE);
        final ExtendedLocator<JQueryLocator> optionLocator = locator.getChild(jq("option"));

        String inputType = null;
        if (selenium.getCount(propertyLocator.format(propertyName)) > 1) {
            inputType = "radio";
        } else if (selenium.getCount(optionLocator) > 1) {
            inputType = "select";
        } else {
            inputType = selenium.getAttribute(typeLocator);
        }

        if (value == null) {
            value = "";
        }

        String valueAsString = value.toString();

        if ("text".equals(inputType)) {
            applyText(locator, valueAsString);
        } else if ("checkbox".equals(inputType)) {
            boolean checked = Boolean.valueOf(valueAsString);
            applyCheckbox(locator, checked);
        } else if ("radio".equals(inputType)) {
            locator = propertyLocator.format(propertyName, "[value="
                + ("".equals(valueAsString) ? "null" : valueAsString) + "]");

            if (!selenium.isChecked(locator)) {
                applyRadio(locator);
            }
        } else if ("select".equals(inputType)) {
            String curValue = selenium.getValue(locator);
            if (valueAsString.equals(curValue)) {
                return;
            }
            applySelect(locator, valueAsString);
        }

        selenium.getInterceptionProxy().unregisterInterceptorType(RepeatForElementNotFound.class);
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    protected void applyText(ElementLocator<?> locator, String value) {
        guard(selenium, requestType).type(locator, value);
    }

    protected void applyCheckbox(ElementLocator<?> locator, boolean checked) {
        selenium.check(locator, checked);
        guard(selenium, requestType).fireEvent(locator, Event.CHANGE);
    }

    protected void applyRadio(ElementLocator<?> locator) {
        guard(selenium, requestType).click(locator);
    }

    protected void applySelect(ElementLocator<?> locator, String value) {
        OptionValueLocator optionLocator = new OptionValueLocator(value);
        guard(selenium, requestType).select(locator, optionLocator);
    }

    public void setOncomplete(String oncomplete) {
        setProperty("oncomplete", oncomplete);
    }

    private class RepeatForElementNotFound implements CommandInterceptor {
        @Override
        public void intercept(CommandContext ctx) throws CommandInterceptionException {
            for (int i = 1; i <= 3; i++) {
                try {
                    ctx.invoke();
                    break;
                } catch (SeleniumException e) {
                    if (i == 3) {
                        throw e;
                    }
                    if (e.getMessage().matches("ERROR: Element .* not found")) {
                        Wait.waitAjax().timeout(500).interval(100).waitForTimeout();
                        continue;
                    }
                    throw e;
                }
            }
        }
    }
}
