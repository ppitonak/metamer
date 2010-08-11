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
package org.richfaces.tests.metamer.ftest.a4jPush;

import static org.jboss.test.selenium.utils.PrimitiveUtils.asLong;
import static org.jboss.test.selenium.utils.text.SimplifiedFormat.format;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.jboss.test.selenium.locator.Attribute;
import org.jboss.test.selenium.locator.AttributeLocator;
import org.richfaces.tests.metamer.ftest.annotations.Inject;
import org.richfaces.tests.metamer.ftest.annotations.Templates;
import org.richfaces.tests.metamer.ftest.annotations.Use;
import org.testng.annotations.Test;

/**
 * Tests the interval attribute for a4j:push
 * 
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestInterval extends AbstractPushTest {
    
    private static final int DEFAULT_COUNTER_STEP = 2;
    private static final int ITERATION_COUNT = 3;
    private static final int MULTIPLE_PUSH_COUNT = 5;

    @Inject
    int interval;

    AttributeLocator<?> clientTime = pjq("span[id$=clientDate]").getAttribute(Attribute.TITLE);

    long startTime;
    int counter;
    int counterStep;

    long deviationTotal = 0;
    long deviationCount = 0;

    /**
     * <p>
     * For interval 1000, test that the interval between push event triggered and event observered by client haven't
     * greater deviation than the one interval.
     * </p>
     * 
     * <p>
     * First set the given interval into the interval attribute.
     * </p>
     * 
     * <p>
     * Then push and wait for result of update triggered by observation of push event.
     * </p>
     * 
     * <p>
     * Then repeat push and wait 6 times with 1 event triggered and 5 fast events triggered in sequence.
     * </p>
     */
    @Test
    @Use(field = "interval", ints = { 1000 })
    public void testClientAllTemplates() throws HttpException, IOException {
        testClient();
    }

    /**
     * <p>
     * For interval two different values (small and big), test that the interval between push event triggered and event
     * observered by client haven't greater deviation than the one interval.
     * </p>
     * 
     * <p>
     * First set the given interval into the interval attribute.
     * </p>
     * 
     * <p>
     * Then push and wait for result of update triggered by observation of push event.
     * </p>
     * 
     * <p>
     * Then repeat push and wait 6 times with 1 event triggered and 5 fast events triggered in sequence.
     * </p>
     */
    @Test
    @Use(field = "interval", ints = { 500, 5000 })
    @Templates(value = "plain")
    public void testClientDifferentIntervals() throws HttpException, IOException {
        testClient();
    }

    private void testClient() throws HttpException, IOException {
        counterStep = DEFAULT_COUNTER_STEP;
        pushAttributes.setInterval(interval);

        pushAndWait(1);
        for (int i = 0; i < ITERATION_COUNT; i++) {
            startIntervalAndCounter();
            pushAndWait(1);
            validateIntervalAndCounter();

            startIntervalAndCounter();
            pushAndWait(MULTIPLE_PUSH_COUNT);
            validateIntervalAndCounter();
        }

        validateAverageDeviation();
    }

    /**
     * Remembers start of the time frame and current value of counter.
     */
    private void startIntervalAndCounter() {
        startTime = getClientTime();
        counter = getCounter();
    }

    /**
     * <p>
     * Obtains current value of counter and end of the time frame to compute the run time.
     * </p>
     * 
     * <p>
     * Validates that run time haven't greater deviation from given maximum {@link #MAX_DEVIATION}.
     * </p>
     * 
     * <p>
     * Remembers the deviation as part of total deviation for computing average value.
     * </p>
     * 
     * <p>
     * Validates that counter have been increased by right value.
     * </p>
     */
    private void validateIntervalAndCounter() {
        long runTime = getClientTime() - startTime;
        long deviation = Math.abs(interval - runTime);

        if (seleniumDebug) {
            System.out.println(format("deviation for interval {0}: {1}", interval, deviation));
        }

        assertTrue(deviation <= interval,
            format("Deviation ({0}) is greater than one interval {1}", deviation, interval));

        deviationTotal += deviation;
        deviationCount += 1;

        int newCounter = getCounter();
        assertEquals(newCounter, counter + counterStep);
        counter = newCounter;

    }

    /**
     * Validates the average deviations from intervals.
     */
    private void validateAverageDeviation() {
        long maximumAvgDeviation = Math.min(interval / 4, 1000);
        long averageDeviation = deviationTotal / deviationCount;
        if (seleniumDebug) {
            System.out.println("averageDeviation: " + averageDeviation);
        }
        assertTrue(
            averageDeviation <= maximumAvgDeviation,
            format("Average deviation for all the intervals ({0}) should not be greater than defined maximum {1}",
                averageDeviation, maximumAvgDeviation));
    }

    /**
     * Returns the time of push event (the time when arrived the response from server)
     * 
     * @return the time of push event (the time when arrived the response from server)
     */
    private long getClientTime() {
        return asLong(selenium.getAttribute(clientTime));
    }
}
