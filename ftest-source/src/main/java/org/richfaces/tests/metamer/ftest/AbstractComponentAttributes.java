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
package org.richfaces.tests.metamer.ftest;

import org.apache.commons.lang.Validate;
import org.jboss.test.selenium.dom.Event;
import org.jboss.test.selenium.framework.AjaxSelenium;
import org.jboss.test.selenium.framework.AjaxSeleniumProxy;
import org.jboss.test.selenium.locator.AttributeLocator;
import org.jboss.test.selenium.locator.ElementLocator;
import org.jboss.test.selenium.locator.JQueryLocator;

import static org.jboss.test.selenium.guard.request.RequestTypeGuardFactory.guardHttp;
import static org.jboss.test.selenium.guard.request.RequestTypeGuardFactory.guardXhr;
import static org.richfaces.tests.metamer.ftest.AbstractMetamerTest.pjq;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class AbstractComponentAttributes {

    private Type type;

    public static class Type {
        public static Type SERVER = new Type();
        public static Type AJAX = new Type();
    }

    public AbstractComponentAttributes() {
        this(Type.SERVER);
    }

    public AbstractComponentAttributes(Type type) {
        Validate.notNull(type);
        this.type = type;
    }

    AjaxSelenium selenium = AjaxSeleniumProxy.getInstance();

    JQueryLocator propertyLocator = pjq("input[id$={0}Input]");

    protected String getProperty(String propertyName) {
        final ElementLocator<?> locator = propertyLocator.format(propertyName);
        return selenium.getValue(locator);
    }
    
    protected void setProperty(String propertyName, Object value) {
        final ElementLocator<?> locator = propertyLocator.format(propertyName);
        final AttributeLocator<?> typeLocator = locator.getAttribute(new org.jboss.test.selenium.locator.Attribute(
            "type"));

        String inputType = selenium.getAttribute(typeLocator);

        String valueAsString = value.toString();
        // INPUT TEXT
        if ("text".equals(inputType)) {
            if (type == Type.SERVER) {
                guardHttp(selenium).type(locator, valueAsString);
            } else if (type == Type.AJAX) {
                guardXhr(selenium).type(locator, valueAsString);
            }
            // INPUT CHECKBOX
        } else if ("checkbox".equals(inputType)) {
            boolean checked = Boolean.valueOf(valueAsString);

            if (type == Type.SERVER) {
                guardHttp(selenium).check(locator, checked);
            } else if (type == Type.AJAX) {
                selenium.check(locator, checked);
                guardXhr(selenium).fireEvent(locator, Event.CHANGE);
            }
        }
    }
}
