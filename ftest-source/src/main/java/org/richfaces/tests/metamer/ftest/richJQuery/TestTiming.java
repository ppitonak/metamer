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
package org.richfaces.tests.metamer.ftest.richJQuery;

import java.net.URL;

import org.jboss.test.selenium.css.CssProperty;
import org.jboss.test.selenium.encapsulated.JavaScript;
import org.jboss.test.selenium.locator.JQueryLocator;
import org.jboss.test.selenium.waiting.conditions.StyleEquals;
import org.richfaces.component.JQueryTiming;
import org.richfaces.tests.metamer.ftest.AbstractMetamerTest;
import org.testng.annotations.Test;

import static org.jboss.test.selenium.utils.URLUtils.*;
import static org.jboss.test.selenium.locator.LocatorFactory.*;
import static org.jboss.test.selenium.guard.request.RequestTypeGuardFactory.guardXhr;
import static org.testng.Assert.assertEquals;
import static org.jboss.test.selenium.encapsulated.JavaScript.js;
import static org.jboss.test.selenium.utils.text.SimplifiedFormat.format;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestTiming extends AbstractMetamerTest {

    JQueryLocator button = jq("input:button[id$=jQueryTestButton]");

    RichJQueryAttributes attributes = new RichJQueryAttributes();

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/richJQuery/simple.xhtml");
    }

    private void setupImmediateTypeAttributes() {
        attributes.setEvent("click");
        attributes.setQuery(js("$(this).css('color', 'red')"));
        attributes.setSelector(button);
        attributes.setTiming(JQueryTiming.immediate);
    }
    
    private void setupDomReadyTypeAttributes() {
        attributes.setEvent(null);
        attributes.setQuery(js("css('color', 'red')"));
        attributes.setSelector(button);
        attributes.setTiming(JQueryTiming.domready);
    }

    @Test
    public void testImmediate() {
        setupImmediateTypeAttributes();
        selenium.click(button);
        waitGui.until(styleEquals.locator(button).property(CssProperty.COLOR).value("red"));
    }

    @Test
    public void testDomReady() {
        setupDomReadyTypeAttributes();
        assertEquals("red", selenium.getStyle(button, CssProperty.COLOR));
    }
    
    @Test
    public void testDomReadyByDefault() {
        setupDomReadyTypeAttributes();
        attributes.setTiming(null);
        assertEquals("red", selenium.getStyle(button, CssProperty.COLOR));
    }
}