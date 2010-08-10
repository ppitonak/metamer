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
package org.richfaces.tests.metamer.ftest.a4jPoll;

import java.net.URL;

import org.jboss.test.selenium.dom.Event;
import org.jboss.test.selenium.locator.Attribute;
import org.jboss.test.selenium.locator.AttributeLocator;
import org.jboss.test.selenium.locator.IdLocator;
import org.jboss.test.selenium.locator.JQueryLocator;
import org.richfaces.tests.metamer.ftest.AbstractMetamerTest;
import org.testng.annotations.Test;

import static org.jboss.test.selenium.utils.URLUtils.buildUrl;
import static org.jboss.test.selenium.locator.LocatorFactory.*;
import static org.testng.Assert.*;
import static org.jboss.test.selenium.SystemProperties.isSeleniumDebug;
import static org.jboss.test.selenium.utils.text.SimplifiedFormat.format;
import static org.jboss.test.selenium.utils.PrimitiveUtils.*;

/**
 * Tests the a4j:poll component.
 * 
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestPoll extends AbstractMetamerTest {

    private static final Attribute ATTRIBUTE_TITLE = new Attribute("title");
    private static final int[] TEST_INTERVAL_VALUES = new int[]{1000, 5000, 500};
    private static final int TEST_INTERVAL_ITERATIONS = 5;
    private static final int MAX_DEVIATION = 100;
    private static final int MAX_AVERAGE_DEVIATION = 50;

    IdLocator attributeEnabled = id("form:attributes:enabledInput");
    IdLocator attributeInterval = id("form:attributes:intervalInput");
    JQueryLocator time = pjq("span[id$=time]");
    AttributeLocator<?> clientTime = pjq("span[id$=clientDate]").getAttribute(ATTRIBUTE_TITLE);

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/a4jPoll/simple.xhtml");
    }

    /**
     * <p>
     * Test the progress of polling for 3 different values from client side.
     * </p>
     * 
     * <p>
     * It defines the new interval value first for each iteration and then enable polling.
     * </p>
     * 
     * <p>
     * Then, it waits for first poll event (zero iteration).
     * </p>
     * 
     * <p>
     * For 5 following poll events it checks that runtime visible from client (output from JavaScript's new
     * Date().getTime()) between the events haven't greater deviation from defined interval than defined
     * {@link #MAX_DEVIATION}.
     * </p>
     * 
     * <p>
     * Test also computes average value of deviation and checks that the average value of all obtained particular
     * deviations isn't greater than {@link #MAX_AVERAGE_DEVIATION}.
     * </p>
     */
    @Test(groups = "client-side-perf")
    public void testIntervalFromClientPerspective() {

        long total = 0;
        long count = 0;
        for (int interval : TEST_INTERVAL_VALUES) {
            selenium.type(attributeInterval, String.valueOf(interval));
            selenium.waitForPageToLoad();

            selenium.check(attributeEnabled);
            selenium.fireEvent(attributeEnabled, Event.CHANGE);
            selenium.waitForPageToLoad();

            selenium.getPageExtensions().install();

            long currentTime = getClientTime();

            for (int i = 0; i <= TEST_INTERVAL_ITERATIONS; i++) {
                long runtime = System.currentTimeMillis();
                selenium.getRequestInterceptor().clearRequestTypeDone();
                selenium.getRequestInterceptor().waitForRequestTypeChange();
                runtime -= getClientTime();
                if (i > 0) {

                    long deviation = Math.abs(interval - Math.abs(runtime));
                    if (isSeleniumDebug()) {
                        System.out.println(format("deviation for interval {0}: {1}", interval, deviation));
                    }
                    assertTrue(deviation <= MAX_DEVIATION, format(
                        "Particular deviation ({2}) for interval {0} was greater than {1}", interval, MAX_DEVIATION,
                        deviation));
                    total += deviation;
                    count += 1;
                }
                currentTime += runtime;
            }

            selenium.uncheck(attributeEnabled);
            selenium.fireEvent(attributeEnabled, Event.CHANGE);
            selenium.waitForPageToLoad();
        }

        long averageDeviation = total / count;

        if (isSeleniumDebug()) {
            System.out.println(format("total average deviation: {0}", averageDeviation));
        }

        assertTrue(averageDeviation <= MAX_AVERAGE_DEVIATION, format(
            "Average deviation ({1}) was greater than given maximum {0}", MAX_AVERAGE_DEVIATION, averageDeviation));
    }

    /**
     * Returns the time of poll event (the time when arrived the response from server)
     * @return the time of poll event (the time when arrived the response from server)
     */
    private long getClientTime() {
        return asLong(selenium.getAttribute(clientTime));
    }

}
