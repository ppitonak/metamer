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
package org.richfaces.tests.metamer.ftest.a4jQueue;

import static org.jboss.test.selenium.dom.Event.KEYPRESS;
import static org.jboss.test.selenium.utils.URLUtils.buildUrl;
import static org.jboss.test.selenium.utils.text.SimplifiedFormat.format;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.net.URL;

import org.jboss.cheiron.halt.XHRHalter;
import org.jboss.cheiron.halt.XHRState;
import org.jboss.test.selenium.waiting.retrievers.Retriever;
import org.richfaces.tests.metamer.ftest.AbstractMetamerTest;
import org.richfaces.tests.metamer.ftest.annotations.Inject;
import org.richfaces.tests.metamer.ftest.annotations.IssueTracking;
import org.richfaces.tests.metamer.ftest.annotations.Use;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestGlobalQueue extends AbstractMetamerTest {

    QueueLocators queue = new QueueLocators("globalQueue", pjq(""), pjq("#attributeForm"));

    QueueAttributes attributes = new QueueAttributes();

    @Inject
    @Use(empty = false)
    Integer requestDelay;

    int deviationTotal;
    int deviationCount;

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/a4jQueue/globalQueue.xhtml");
    }

    /**
     * Tests delay between time last event occurs and time when event triggers request (begin).
     */
    @Test
    @Use(field = "requestDelay", ints = { 0, 500, 1500, 5000 })
    public void testRequestDelay() {
        attributes.setRequestDelay(requestDelay);

        initializeTimes();
        for (int i = 0; i < 5; i++) {
            fireEvents(1);
            checkTimes();
        }
        checkAvgDeviation();
    }

    /**
     * Events from one source should be stacked as occurs, while last event isn't delayed by configured requestDelay.
     */
    @Test
    public void testMultipleRequestsWithDelay() {
        attributes.setRequestDelay(3000);

        initializeCounts();

        XHRHalter.enable();

        fireEvents(4);
        XHRHalter handle = XHRHalter.getHandleBlocking();
        handle.send();
        handle.complete();

        checkCounts(4, 1, 1);

        fireEvents(3);
        handle.waitForOpen();
        handle.send();
        handle.complete();

        checkCounts(7, 2, 2);

        XHRHalter.disable();
    }

    /**
     * <p>
     * When no requestDelay (0) is set, events should fire request immediately.
     * </p>
     * 
     * <p>
     * However, when one event is waiting in queue for processing of previous request, events should be stacked.
     * </p>
     */
    @Test
    public void testMultipleRequestsWithNoDelay() {
        attributes.setRequestDelay(0);

        initializeCounts();

        XHRHalter.enable();

        fireEvents(1);
        checkCounts(1, 1, 0);

        XHRHalter handle = XHRHalter.getHandleBlocking();
        handle.send();

        fireEvents(1);
        checkCounts(2, 1, 0);

        handle.complete();
        checkCounts(2, 2, 1);

        handle.waitForOpen();
        handle.send();
        fireEvents(4);
        checkCounts(6, 2, 1);

        handle.complete();
        checkCounts(6, 3, 2);

        handle.waitForOpen();
        handle.send();
        fireEvents(1);
        checkCounts(7, 3, 2);

        handle.complete();
        checkCounts(7, 4, 3);

        handle.waitForOpen();
        handle.send();
        checkCounts(7, 4, 3);

        handle.complete();
        checkCounts(7, 4, 4);

        XHRHalter.disable();
    }
    
    @Test
    @IssueTracking("https://jira.jboss.org/browse/RF-9328")
    public void testRendered() {
        attributes.setRequestDelay(1500);
        attributes.setRendered(false);
        
        initializeTimes();
        fireEvents(1);
        
        // check that no requestDelay is applied while renderer=false
        checkTimes(0);
        // TODO should check that no attributes is applied with renderes=false
    }

    // TODO not implemented yet
    public void testTimeout() {
        attributes.setRequestDelay(0);
        attributes.setTimeout(1000);

        XHRHalter.enable();

        fireEvents(1);
        XHRHalter handle = XHRHalter.getHandleBlocking();
        handle.continueBefore(XHRState.COMPLETE);
        fireEvents(10);

        XHRHalter.disable();

        // fireEvents(1);
        // fireEvents(1);
    }

    // TODO not implemented yet
    public void testIgnoreDuplicatedResponses() {
        attributes.setIgnoreDupResponses(true);

        XHRHalter.enable();
        fireEvents(1);
        XHRHalter handle = XHRHalter.getHandleBlocking();
        handle.send();
        fireEvents(1);
        handle.complete();
        handle.waitForOpen();
    }

    private void initializeTimes() {
        deviationTotal = 0;
        deviationCount = 0;
        queue.retrieveEvent1Time.initializeValue();
        queue.retrieveBeginTime.initializeValue();
        queue.retrieveCompleteTime.initializeValue();
    }

    private void fireEvents(int countOfEvents) {
        for (int i = 0; i < countOfEvents; i++) {
            selenium.fireEvent(queue.input1, KEYPRESS);
        }
    }

    private void initializeCounts() {
        queue.retrieveEvent1Count.initializeValue();
        queue.retrieveRequestCount.initializeValue();
        queue.retrieveDOMUpdateCount.initializeValue();
    }

    private void checkCounts(int events, int requests, int domUpdates) {
        assertChangeIfNotEqualToOldValue(queue.retrieveEvent1Count, events, "eventCount");
        assertChangeIfNotEqualToOldValue(queue.retrieveRequestCount, requests, "requestCount");
        assertChangeIfNotEqualToOldValue(queue.retrieveDOMUpdateCount, domUpdates, "domUpdates");
    }

    private void assertChangeIfNotEqualToOldValue(Retriever<Integer> retrieveCount, Integer eventCount, String eventType) {
        if (!eventCount.equals(retrieveCount.getValue())) {
            assertEquals(waitAjax.failWith(eventType).waitForChangeAndReturn(retrieveCount), eventCount);
        } else {
            assertEquals(retrieveCount.retrieve(), eventCount);
        }
    }
    
    private void checkTimes() {
        checkTimes(requestDelay);
    }

    private void checkTimes(long requestDelay) {
        long eventTime = waitAjax.waitForChangeAndReturn(queue.retrieveEvent1Time);
        long beginTime = waitAjax.waitForChangeAndReturn(queue.retrieveBeginTime);
        long actualDelay = beginTime - eventTime;
        long deviation = Math.abs(actualDelay - requestDelay);
        long maxDeviation = Math.max(50, requestDelay);

        if (seleniumDebug) {
            System.out.println(format("deviation for requestDelay {0}: {1}", requestDelay, deviation));
        }

        assertTrue(
            deviation <= maxDeviation,
            format("Deviation ({0}) is greater than maxDeviation ({1}) for requestDelay {2}", deviation, maxDeviation,
                requestDelay));

        deviationTotal += deviation;
        deviationCount += 1;
    }

    private void checkAvgDeviation() {
        long maximumAvgDeviation = Math.max(25, Math.min(50, requestDelay / 4));
        long averageDeviation = deviationTotal / deviationCount;
        if (seleniumDebug) {
            System.out.println("averageDeviation: " + averageDeviation);
        }
        assertTrue(
            averageDeviation <= maximumAvgDeviation,
            format(
                "Average deviation for all tests of requestDelay ({0}) should not be greater than defined maximum {1}",
                averageDeviation, maximumAvgDeviation));
    }
}