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
import static org.richfaces.tests.metamer.ftest.AbstractMetamerTest.pjq;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.net.URL;

import org.jboss.cheiron.halt.XHRHalter;
import org.jboss.test.selenium.locator.ElementLocator;
import org.jboss.test.selenium.waiting.retrievers.Retriever;
import org.richfaces.tests.metamer.ftest.AbstractMetamerTest;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestFormQueue extends AbstractMetamerTest {

    static final Long GLOBAL_DELAY = 10000L;
    static final Long DELAY_A = 3000L;
    static final Long DELAY_B = 5000L;

    int deviationTotal;
    int deviationCount;

    QueueLocators formQueueA = prepareLocators("formQueue1");
    QueueLocators formQueueB = prepareLocators("formQueue2");
    QueueLocators globalQueue = prepareLocators("globalQueue");

    private static QueueLocators prepareLocators(String identifier) {
        return new QueueLocators(identifier, pjq("div.rf-p[id$={0}Panel]").format(identifier), pjq(
            "div.rf-p[id$={0}AttributesPanel]").format(identifier));
    }

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/a4jQueue/formQueue.xhtml");
    }

    @Test
    public void testTimingOneQueueTwoEvents() {
        formQueueA.attributes.setRequestDelay(DELAY_A);
        globalQueue.attributes.setRequestDelay(GLOBAL_DELAY);

        initializeTimes(formQueueA);

        XHRHalter.enable();

        fireEvent(formQueueA, Event.FIRST, 2);
        fireEvent(formQueueA, Event.SECOND, 3);

        XHRHalter halter = XHRHalter.getHandleBlocking();
        halter.complete();
        halter.waitForOpen();
        halter.complete();

        checkTimes(formQueueA, Event.SECOND, DELAY_A);
    }

    @Test
    public void testCountsOneQueueTwoEvents() {
        formQueueA.attributes.setRequestDelay(DELAY_A);
        globalQueue.attributes.setRequestDelay(GLOBAL_DELAY);

        initializeCounts(formQueueA);

        XHRHalter.enable();

        fireEvent(formQueueA, Event.FIRST, 2);
        checkCounts(formQueueA, 2, 0, 0, 0);
        fireEvent(formQueueA, Event.SECOND, 3);
        checkCounts(formQueueA, 2, 3, 1, 0);

        XHRHalter halter = XHRHalter.getHandleBlocking();
        checkCounts(formQueueA, 2, 3, 1, 0);
        halter.complete();
        checkCounts(formQueueA, 2, 3, 2, 1);
        halter.waitForOpen();
        checkCounts(formQueueA, 2, 3, 2, 1);
        halter.complete();
        checkCounts(formQueueA, 2, 3, 2, 2);
    }

    @Test
    public void testTimingTwoQueuesFourEvents() {
        formQueueA.attributes.setRequestDelay(DELAY_A);
        formQueueB.attributes.setRequestDelay(DELAY_B);
        globalQueue.attributes.setRequestDelay(GLOBAL_DELAY);

        initializeTimes(formQueueA);
        initializeTimes(formQueueB);

        XHRHalter.enable();

        fireEvent(formQueueA, Event.FIRST, 1);
        fireEvent(formQueueA, Event.SECOND, 1);
        fireEvent(formQueueB, Event.FIRST, 1);
        fireEvent(formQueueB, Event.SECOND, 1);

        XHRHalter halter = XHRHalter.getHandleBlocking();
        halter.complete();
        halter.waitForOpen();
        halter.complete();
        halter.waitForOpen();
        halter.complete();
        halter.waitForOpen();
        halter.complete();

        checkTimes(formQueueB, Event.SECOND, DELAY_B);

        assertTrue(formQueueA.retrieveBeginTime.retrieve() - formQueueA.retrieveEvent1Time.retrieve() < 1000);
        assertTrue(formQueueA.retrieveBeginTime.retrieve() - formQueueA.retrieveEvent2Time.retrieve() < 1000);
        assertTrue(formQueueB.retrieveBeginTime.retrieve() - formQueueB.retrieveEvent1Time.retrieve() > 3000);
    }

    @Test
    public void testCountsTwoQueuesThreeEvents() {
        formQueueA.attributes.setRequestDelay(DELAY_A);
        formQueueB.attributes.setRequestDelay(DELAY_B);
        globalQueue.attributes.setRequestDelay(GLOBAL_DELAY);

        initializeCounts(formQueueA);
        initializeCounts(formQueueB);

        XHRHalter.enable();

        fireEvent(formQueueA, Event.FIRST, 1);
        checkCounts(formQueueA, 1, 0, 0, 0);
        fireEvent(formQueueA, Event.SECOND, 1);
        checkCounts(formQueueA, 1, 1, 1, 0);
        fireEvent(formQueueB, Event.FIRST, 1);
        checkCounts(formQueueB, 1, 0, 0, 0);
        fireEvent(formQueueB, Event.SECOND, 1);

        checkCounts(formQueueA, 1, 1, 1, 0);
        checkCounts(formQueueB, 1, 1, 0, 0);

        XHRHalter halter = XHRHalter.getHandleBlocking();
        halter.complete();
        checkCounts(formQueueA, 1, 1, 2, 1);
        halter.waitForOpen();
        halter.complete();
        checkCounts(formQueueA, 1, 1, 2, 2);
        checkCounts(formQueueB, 1, 1, 1, 0);
        halter.waitForOpen();
        halter.complete();
        halter.waitForOpen();
        checkCounts(formQueueB, 1, 1, 2, 1);
        halter.complete();

        checkCounts(formQueueA, 1, 1, 2, 2);
        checkCounts(formQueueB, 1, 1, 2, 2);
    }

    private void initializeTimes(QueueLocators formQueueLocators) {
        deviationTotal = 0;
        deviationCount = 0;
        formQueueLocators.retrieveEvent1Time.initializeValue();
        formQueueLocators.retrieveEvent2Time.initializeValue();
        formQueueLocators.retrieveBeginTime.initializeValue();
        formQueueLocators.retrieveCompleteTime.initializeValue();
    }

    private void fireEvent(QueueLocators formQueueLocators, Event event, int countOfEvents) {
        ElementLocator<?> input = (event == Event.FIRST) ? formQueueLocators.input1 : formQueueLocators.input2;
        for (int i = 0; i < countOfEvents; i++) {
            selenium.fireEvent(input, KEYPRESS);
        }
    }

    private void initializeCounts(QueueLocators formQueueLocators) {
        formQueueLocators.retrieveEvent1Count.initializeValue();
        formQueueLocators.retrieveEvent2Count.initializeValue();
        formQueueLocators.retrieveRequestCount.initializeValue();
        formQueueLocators.retrieveDOMUpdateCount.initializeValue();
    }

    private void checkCounts(QueueLocators formQueueLocators, int events1, int events2, int requests, int domUpdates) {
        assertChangeIfNotEqualToOldValue(formQueueLocators.retrieveEvent1Count, events1, "event1Count");
        assertChangeIfNotEqualToOldValue(formQueueLocators.retrieveEvent2Count, events2, "event2Count");
        assertChangeIfNotEqualToOldValue(formQueueLocators.retrieveRequestCount, requests, "requestCount");
        assertChangeIfNotEqualToOldValue(formQueueLocators.retrieveDOMUpdateCount, domUpdates, "domUpdates");
    }

    private void assertChangeIfNotEqualToOldValue(Retriever<Integer> retrieveCount, Integer eventCount, String eventType) {
        if (!eventCount.equals(retrieveCount.getValue())) {
            assertEquals(waitAjax.failWith(eventType).waitForChangeAndReturn(retrieveCount), eventCount);
        } else {
            assertEquals(retrieveCount.retrieve(), eventCount);
        }
    }

    private void checkTimes(QueueLocators formQueueLocators, Event event, long requestDelay) {
        Retriever<Long> retrieveEventTime = (event == Event.FIRST) ? formQueueLocators.retrieveEvent1Time
            : formQueueLocators.retrieveEvent2Time;
        long eventTime = waitAjax.waitForChangeAndReturn(retrieveEventTime);
        long beginTime = waitAjax.waitForChangeAndReturn(formQueueLocators.retrieveBeginTime);
        long actualDelay = beginTime - eventTime;
        long deviation = Math.abs(actualDelay - requestDelay);
        long maxDeviation = Math.max(100, requestDelay);

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

    private enum Event {
        FIRST, SECOND;
    }
}