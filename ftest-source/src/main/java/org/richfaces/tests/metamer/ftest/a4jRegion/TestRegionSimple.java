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
package org.richfaces.tests.metamer.ftest.a4jRegion;

import static org.jboss.test.selenium.utils.URLUtils.buildUrl;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.net.URL;

import org.jboss.test.selenium.dom.Event;
import org.jboss.test.selenium.locator.JQueryLocator;
import org.jboss.test.selenium.waiting.retrievers.TextRetriever;
import org.richfaces.tests.metamer.ftest.AbstractComponentAttributes;
import org.richfaces.tests.metamer.ftest.AbstractMetamerTest;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestRegionSimple extends AbstractMetamerTest {

    RegionAttributes attributes = new RegionAttributes();

    JQueryLocator nameInput = pjq("input:text[id$=user2NameInput]");
    JQueryLocator emailInput = pjq("input:text[id$=user2EmailInput]");

    JQueryLocator nameOutput = pjq("span[id$=user2NameOutput]");
    JQueryLocator emailOutput = pjq("span[id$=user2EmailOutput]");

    TextRetriever nameRetriever = retrieveText.locator(nameOutput);
    TextRetriever emailRetriever = retrieveText.locator(emailOutput);

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/a4jRegion/simple.xhtml");
    }

    @Test
    public void testExecution() {
        nameRetriever.initializeValue();

        selenium.type(nameInput, "abc");
        selenium.fireEvent(nameInput, Event.KEYUP);

        waitAjax.waitForChange(nameRetriever);

        emailRetriever.initializeValue();

        selenium.type(emailInput, "abc");
        selenium.fireEvent(emailInput, Event.KEYUP);

        waitAjax.waitForChange(emailRetriever);
    }

    @Test
    public void testRendered() {
        assertTrue(selenium.isElementPresent(nameInput));
        assertTrue(selenium.isElementPresent(emailInput));
        assertTrue(selenium.isElementPresent(nameOutput));
        assertTrue(selenium.isElementPresent(emailOutput));

        attributes.setRendered(false);

        assertFalse(selenium.isElementPresent(nameInput));
        assertFalse(selenium.isElementPresent(emailInput));
        assertTrue(selenium.isElementPresent(nameOutput));
        assertTrue(selenium.isElementPresent(emailOutput));
    }

    private class RegionAttributes extends AbstractComponentAttributes {
        public void setRendered(Boolean rendered) {
            setProperty("rendered", rendered);
        }
    }
}
