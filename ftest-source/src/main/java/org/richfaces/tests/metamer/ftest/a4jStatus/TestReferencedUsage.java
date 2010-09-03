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
package org.richfaces.tests.metamer.ftest.a4jStatus;

import static org.jboss.test.selenium.utils.URLUtils.buildUrl;
import static org.testng.Assert.assertEquals;

import java.net.URL;

import org.jboss.cheiron.halt.SendHalt;
import org.jboss.test.selenium.locator.JQueryLocator;
import org.jboss.test.selenium.waiting.retrievers.TextRetriever;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestReferencedUsage extends AbstracStatusTest {

    JQueryLocator status1 = pjq("span[id$=status1]");
    JQueryLocator status2 = pjq("span[id$=status2]");

    TextRetriever retrieveStatus1 = retrieveText.locator(status1);
    TextRetriever retrieveStatus2 = retrieveText.locator(status2);

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/a4jStatus/referencedUsage.xhtml");
    }

    @Test
    public void testClickBothButtonsInSequence() {
        SendHalt.enable();
        selenium.click(button1);
        SendHalt halt = SendHalt.getHalt();
        assertEquals(retrieveStatus1.retrieve(), "START");
        assertEquals(retrieveStatus2.retrieve(), "STOP");
        halt.unhalt();
        waitAjax.waitForChange("START", retrieveStatus1);
        selenium.click(button2);
        halt = SendHalt.getHalt();
        assertEquals(retrieveStatus1.retrieve(), "STOP");
        assertEquals(retrieveStatus2.retrieve(), "START");
        halt.unhalt();
        waitAjax.waitForChange("START", retrieveStatus2);
        assertEquals(retrieveStatus1.retrieve(), "STOP");
        assertEquals(retrieveStatus2.retrieve(), "STOP");
        SendHalt.disable();
    }

    @Test
    public void testClickBothButtonsImmediately() {
        SendHalt.enable();
        selenium.click(button1);
        selenium.click(button2);
        SendHalt halt = SendHalt.getHalt();
        assertEquals(retrieveStatus1.retrieve(), "START");
        assertEquals(retrieveStatus2.retrieve(), "STOP");
        halt.unhalt();
        halt = SendHalt.getHalt();
        assertEquals(retrieveStatus1.retrieve(), "STOP");
        assertEquals(retrieveStatus2.retrieve(), "START");
        halt.unhalt();
        waitAjax.waitForChange("START", retrieveStatus2);
        assertEquals(retrieveStatus1.retrieve(), "STOP");
        assertEquals(retrieveStatus2.retrieve(), "STOP");
        SendHalt.disable();
    }

    /**
     * @Test TODO: selenium is causing 3 requests, but manually we triggers 2 requests (use Firebug to reproduce)
     */
    public void testClickFirstButtonThenSecondButtonThenAgainFirstButtonImmediately() {
        SendHalt.enable();
        selenium.click(button1);
        selenium.click(button2);
        selenium.click(button1);
        SendHalt halt = SendHalt.getHalt();
        assertEquals(retrieveStatus1.retrieve(), "START");
        assertEquals(retrieveStatus2.retrieve(), "STOP");
        halt.unhalt();
        halt = SendHalt.getHalt();
        assertEquals(retrieveStatus1.retrieve(), "START");
        assertEquals(retrieveStatus2.retrieve(), "STOP");
        halt.unhalt();
        waitAjax.waitForChange("START", retrieveStatus1);
        assertEquals(retrieveStatus1.retrieve(), "STOP");
        assertEquals(retrieveStatus2.retrieve(), "STOP");
        SendHalt.disable();
    }

    @Test
    public void testDoubleClick() {
        SendHalt.enable();
        selenium.click(button1);
        SendHalt halt = SendHalt.getHalt();
        assertEquals(retrieveStatus1.retrieve(), "START");
        assertEquals(retrieveStatus2.retrieve(), "STOP");
        halt.unhalt();
        selenium.click(button1);
        halt = SendHalt.getHalt();
        assertEquals(retrieveStatus1.retrieve(), "START");
        assertEquals(retrieveStatus2.retrieve(), "STOP");
        halt.unhalt();
        waitAjax.waitForChange("START", retrieveStatus1);
        assertEquals(retrieveStatus1.retrieve(), "STOP");
        assertEquals(retrieveStatus2.retrieve(), "STOP");
        SendHalt.disable();
    }

}
