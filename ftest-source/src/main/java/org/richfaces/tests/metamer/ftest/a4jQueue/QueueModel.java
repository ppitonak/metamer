package org.richfaces.tests.metamer.ftest.a4jQueue;

import static org.jboss.cheiron.retriever.RetrieverAdapter.integerAdapter;
import static org.jboss.cheiron.retriever.RetrieverAdapter.longAdapter;
import static org.jboss.test.selenium.SystemProperties.isSeleniumDebug;
import static org.jboss.test.selenium.dom.Event.KEYPRESS;
import static org.jboss.test.selenium.locator.Attribute.TITLE;
import static org.jboss.test.selenium.locator.reference.ReferencedLocator.ref;
import static org.jboss.test.selenium.utils.text.SimplifiedFormat.format;
import static org.jboss.test.selenium.waiting.retrievers.RetrieverFactory.RETRIEVE_ATTRIBUTE;
import static org.jboss.test.selenium.waiting.retrievers.RetrieverFactory.RETRIEVE_TEXT;
import static org.richfaces.tests.metamer.ftest.AbstractMetamerTest.pjq;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.jboss.test.selenium.waiting.WaitFactory.waitAjax;
import static org.jboss.test.selenium.waiting.WaitFactory.textEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.jboss.test.selenium.framework.AjaxSelenium;
import org.jboss.test.selenium.framework.AjaxSeleniumProxy;
import org.jboss.test.selenium.locator.AttributeLocator;
import org.jboss.test.selenium.locator.ElementLocator;
import org.jboss.test.selenium.locator.JQueryLocator;
import org.jboss.test.selenium.locator.reference.LocatorReference;
import org.jboss.test.selenium.locator.reference.ReferencedLocator;
import org.jboss.test.selenium.waiting.retrievers.Retriever;

public class QueueModel {

    private AjaxSelenium selenium = AjaxSeleniumProxy.getInstance();

    private Boolean event2Present = null;

    List<Long> deviations = new ArrayList<Long>();

    LocatorReference<JQueryLocator> form = new LocatorReference<JQueryLocator>(null);

    ReferencedLocator<JQueryLocator> input1 = ref(form, "input:text[id$=input1]");
    ReferencedLocator<JQueryLocator> input2 = ref(form, "input:text[id$=input2]");

    ElementLocator<?> events1 = ref(form, "span[id$=events1]");
    ElementLocator<?> events2 = ref(form, "span[id$=events2]");
    ElementLocator<?> requests = ref(form, "span[id$=requests]");
    ElementLocator<?> updates = ref(form, "span[id$=updates]");

    AttributeLocator<?> event1Time = ref(form, "span[id$=event1\\:outputTime]").getAttribute(TITLE);
    AttributeLocator<?> event2Time = ref(form, "span[id$=event2\\:outputTime]").getAttribute(TITLE);
    AttributeLocator<?> beginTime = ref(form, "span[id$=begin\\:outputTime]").getAttribute(TITLE);
    AttributeLocator<?> completeTime = ref(form, "span[id$=complete\\:outputTime]").getAttribute(TITLE);

    Retriever<Integer> retrieveEvent1Count = integerAdapter(RETRIEVE_TEXT.locator(events1));
    Retriever<Integer> retrieveEvent2Count = integerAdapter(RETRIEVE_TEXT.locator(events2));
    Retriever<Integer> retrieveRequestCount = integerAdapter(RETRIEVE_TEXT.locator(requests));
    Retriever<Integer> retrieveDOMUpdateCount = integerAdapter(RETRIEVE_TEXT.locator(updates));

    Retriever<Long> retrieveEvent1Time = longAdapter(RETRIEVE_ATTRIBUTE.attributeLocator(event1Time));
    Retriever<Long> retrieveEvent2Time = longAdapter(RETRIEVE_ATTRIBUTE.attributeLocator(event2Time));
    Retriever<Long> retrieveBeginTime = longAdapter(RETRIEVE_ATTRIBUTE.attributeLocator(beginTime));
    Retriever<Long> retrieveCompleteTime = longAdapter(RETRIEVE_ATTRIBUTE.attributeLocator(completeTime));

    public QueueModel() {
        this(pjq(""));
    }

    public QueueModel(JQueryLocator queueRoot) {
        form.setLocator(queueRoot);
    }

    private boolean isEvent2Present() {
        if (event2Present == null) {
            event2Present = selenium.isElementPresent(input2);
            assertEquals((boolean) event2Present, selenium.isElementPresent(event2Time.getAssociatedElement()));
        }
        return event2Present;
    }

    public void fireEvent(int countOfEvents) {
        fireEvent(Input.FIRST, countOfEvents);
    }

    public void fireEvent(Input event, int countOfEvents) {
        ElementLocator<?> input = (event == Input.FIRST) ? input1 : input2;
        for (int i = 0; i < countOfEvents; i++) {
            selenium.fireEvent(input, KEYPRESS);
        }
    }

    public void initializeCounts() {
        retrieveEvent1Count.initializeValue();
        retrieveRequestCount.initializeValue();
        retrieveDOMUpdateCount.initializeValue();
        if (isEvent2Present()) {
            retrieveEvent2Count.initializeValue();
        }
    }

    public void checkCounts(int events1, int requests, int domUpdates) {
        assertChangeIfNotEqualToOldValue(retrieveEvent1Count, events1, "event1Count");
        assertChangeIfNotEqualToOldValue(retrieveRequestCount, requests, "requestCount");
        assertChangeIfNotEqualToOldValue(retrieveDOMUpdateCount, domUpdates, "domUpdates");
    }

    public void checkCounts(int events1, int events2, int requests, int domUpdates) {
        assertChangeIfNotEqualToOldValue(retrieveEvent1Count, events1, "event1Count");
        assertChangeIfNotEqualToOldValue(retrieveRequestCount, requests, "requestCount");
        assertChangeIfNotEqualToOldValue(retrieveDOMUpdateCount, domUpdates, "domUpdates");
        if (isEvent2Present()) {
            assertChangeIfNotEqualToOldValue(retrieveEvent2Count, events2, "event2Count");
        }
    }

    private void assertChangeIfNotEqualToOldValue(Retriever<Integer> retrieveCount, Integer eventCount, String eventType) {
        if (!eventCount.equals(retrieveCount.getValue())) {
            assertEquals(waitAjax.failWith(eventType).waitForChangeAndReturn(retrieveCount), eventCount);
        } else {
            assertEquals(retrieveCount.retrieve(), eventCount);
        }
    }

    public void initializeTimes() {
        deviations.clear();
        retrieveEvent1Time.initializeValue();
        retrieveBeginTime.initializeValue();
        retrieveCompleteTime.initializeValue();
        if (isEvent2Present()) {
            retrieveEvent2Time.initializeValue();
        }
    }

    public void checkTimes(long requestDelay) {
        checkTimes(Input.FIRST, requestDelay);
    }

    public void checkTimes(Input event, long requestDelay) {
        Retriever<Long> retrieveEventTime = (event == Input.FIRST) ? retrieveEvent1Time : retrieveEvent2Time;
        long eventTime = waitAjax.waitForChangeAndReturn(retrieveEventTime);
        long beginTime = waitAjax.waitForChangeAndReturn(retrieveBeginTime);
        long actualDelay = beginTime - eventTime;
        long deviation = Math.abs(actualDelay - requestDelay);
        long maxDeviation = Math.max(100, requestDelay / 2);

        checkDeviation(deviation, maxDeviation);

        deviations.add(deviation);
    }

    public void checkNoDelayBetweenEvents() {
        long event1Time = retrieveEvent1Time.retrieve();
        long event2Time = retrieveEvent2Time.retrieve();
        long actualDelay = Math.abs(event1Time - event2Time);

        checkDeviation(actualDelay, 150);
    }

    public void checkDeviation(long deviation, long maxDeviation) {
        if (isSeleniumDebug()) {
            System.out.println(format("deviation: {0}", deviation));
        }

        assertTrue(deviation <= maxDeviation,
            format("Deviation ({0}) is greater than maxDeviation ({1})", deviation, maxDeviation));
    }

    public void checkDeviationMedian(long requestDelay) {
        long maximumDeviationMedian = Math.max(25, Math.min(50, requestDelay / 4));
        long deviationMedian = getMedian(deviations);
        if (isSeleniumDebug()) {
            System.out.println("deviationMedian: " + deviationMedian);
        }
        assertTrue(
            deviationMedian <= maximumDeviationMedian,
            format("Deviation median ({0}) should not be greater than defined maximum {1}", deviationMedian,
                maximumDeviationMedian));
    }

    private <T extends Comparable<T>> T getMedian(List<T> list) {
        List<T> listCopy = new ArrayList<T>(list);
        Collections.sort(listCopy);
        return listCopy.get(listCopy.size() / 2);
    }

    public static enum Input {
        FIRST, SECOND;
    }
}
