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

import static org.jboss.test.selenium.encapsulated.JavaScript.js;
import static org.jboss.test.selenium.locator.LocatorFactory.jq;
import static org.jboss.test.selenium.utils.URLUtils.buildUrl;
import static org.testng.Assert.assertEquals;

import java.awt.Color;
import java.net.URL;

import org.jboss.cheiron.retriever.ColorRetriever;
import org.jboss.test.selenium.locator.JQueryLocator;
import org.richfaces.tests.metamer.ftest.AbstractMetamerTest;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestSimple extends AbstractMetamerTest {

    JQueryLocator button = jq("#jQueryTestButton");
    JQueryLocator rebind = jq("#rebindOneClickButton");
    JQueryLocator addLiveComponent = jq("input[id$=addComponentButton]");
    JQueryLocator liveTestComponent = jq("div.liveTestComponent");

    RichJQueryAttributes attributes = new RichJQueryAttributes();

    ColorRetriever buttonColorRetriver = new ColorRetriever().locator(button);

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/richJQuery/simple.xhtml");
    }

    @Test
    public void testDefaultTiming() {
        setupDomReadyTypeAttributes();
        attributes.setTiming(null);
        assertEquals(buttonColorRetriver.retrieve(), Color.RED);
    }

    @Test
    public void testTimingImmediate() {
        setupImmediateTypeAttributes();
        buttonColorRetriver.initializeValue();
        selenium.click(button);
        assertEquals(waitModel.waitForChangeAndReturn(buttonColorRetriver), Color.RED);
    }

    @Test
    public void testTimingDomReady() {
        setupDomReadyTypeAttributes();
        assertEquals(buttonColorRetriver.retrieve(), Color.RED);
    }

    @Test
    public void testAttachTypeOne() {
        setupImmediateTypeAttributes();
        attributes.setAttachType("one");
        attributes.setQuery(js("alert('first')"));

        selenium.click(button);
        waitGui.until(alertEquals.message("first"));
        selenium.click(button);
        selenium.click(button);

        for (int i = 0; i < 3; i++) {
            selenium.click(rebind);
            selenium.click(button);
            waitGui.until(alertEquals.message("one attachType rebound event"));
        }
    }

    @Test
    public void testAttachTypeLive() {
        for (int count = 1; count <= 4; count++) {
            if (count > 1) {
                selenium.click(addLiveComponent);
                waitAjax.until(countEquals.locator(liveTestComponent).count(count));
            }

            for (int i = 1; i <= count; i++) {
                JQueryLocator component = liveTestComponent.getNthOccurence(i);

                String message = selenium.getText(component);
                selenium.click(component);
                waitGui.until(alertEquals.message(message));
            }

        }
    }

    private void setupImmediateTypeAttributes() {
        attributes.setEvent("click");
        attributes.setQuery(js("$(this).css('color', 'red')"));
        attributes.setSelector(button);
        attributes.setTiming("immediate");
    }

    private void setupDomReadyTypeAttributes() {
        attributes.setEvent(null);
        attributes.setQuery(js("css('color', 'red')"));
        attributes.setSelector(button);
        attributes.setTiming("domready");
    }

}