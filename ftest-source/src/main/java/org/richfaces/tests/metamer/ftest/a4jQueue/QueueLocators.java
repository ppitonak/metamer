package org.richfaces.tests.metamer.ftest.a4jQueue;

import static org.jboss.cheiron.retriever.RetrieverAdapter.integerAdapter;
import static org.jboss.cheiron.retriever.RetrieverAdapter.longAdapter;
import static org.jboss.test.selenium.locator.Attribute.TITLE;
import static org.jboss.test.selenium.locator.reference.ReferencedLocator.ref;
import static org.jboss.test.selenium.waiting.retrievers.RetrieverFactory.RETRIEVE_ATTRIBUTE;
import static org.jboss.test.selenium.waiting.retrievers.RetrieverFactory.RETRIEVE_TEXT;
import static org.richfaces.tests.metamer.ftest.AbstractMetamerTest.pjq;

import org.jboss.test.selenium.locator.AttributeLocator;
import org.jboss.test.selenium.locator.ElementLocator;
import org.jboss.test.selenium.locator.ExtendedLocator;
import org.jboss.test.selenium.locator.JQueryLocator;
import org.jboss.test.selenium.locator.reference.LocatorReference;
import org.jboss.test.selenium.locator.reference.ReferencedLocator;
import org.jboss.test.selenium.waiting.retrievers.Retriever;

public class QueueLocators {

    LocatorReference<JQueryLocator> form = new LocatorReference<JQueryLocator>(null);
    LocatorReference<JQueryLocator> attributesPanel = new LocatorReference<JQueryLocator>(null);

    ReferencedLocator<JQueryLocator> input1 = ref(form, "input:text[id$=input1]");
    ReferencedLocator<JQueryLocator> input2 = ref(form, "input:text[id$=input2]");
    ReferencedLocator<JQueryLocator> attributesTable = ref(attributesPanel, "table.attributes");

    QueueAttributes attributes = new QueueAttributes(attributesTable);

    ElementLocator<?> events1 = ref(form, "span[id$=events1]");
    ElementLocator<?> events2 = ref(form, "span[id$=events2]");
    ElementLocator<?> requests = ref(form, "span[id$=requests]");
    ElementLocator<?> updates = ref(form, "span[id$=updates]");

    AttributeLocator<?> event1Time = ref(form, "span[id$=eventTime1\\:outputTime]").getAttribute(TITLE);
    AttributeLocator<?> event2Time = ref(form, "span[id$=eventTime2\\:outputTime]").getAttribute(TITLE);
    AttributeLocator<?> beginTime = ref(form, "span[id$=beginTime\\:outputTime]").getAttribute(TITLE);
    AttributeLocator<?> completeTime = ref(form, "span[id$=completeTime\\:outputTime]").getAttribute(TITLE);

    Retriever<Integer> retrieveEvent1Count = integerAdapter(RETRIEVE_TEXT.locator(events1));
    Retriever<Integer> retrieveEvent2Count = integerAdapter(RETRIEVE_TEXT.locator(events2));
    Retriever<Integer> retrieveRequestCount = integerAdapter(RETRIEVE_TEXT.locator(requests));
    Retriever<Integer> retrieveDOMUpdateCount = integerAdapter(RETRIEVE_TEXT.locator(updates));

    Retriever<Long> retrieveEvent1Time = longAdapter(RETRIEVE_ATTRIBUTE.attributeLocator(event1Time));
    Retriever<Long> retrieveEvent2Time = longAdapter(RETRIEVE_ATTRIBUTE.attributeLocator(event2Time));
    Retriever<Long> retrieveBeginTime = longAdapter(RETRIEVE_ATTRIBUTE.attributeLocator(beginTime));
    Retriever<Long> retrieveCompleteTime = longAdapter(RETRIEVE_ATTRIBUTE.attributeLocator(completeTime));

    private String identifier;

    public QueueLocators(String identifier, JQueryLocator queueRoot, JQueryLocator queueAttributesPanel) {
        this.identifier = identifier;

        form.setLocator(queueRoot);
        attributesPanel.setLocator(queueAttributesPanel);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        QueueLocators other = (QueueLocators) obj;
        if (identifier == null) {
            if (other.identifier != null) {
                return false;
            }
        } else if (!identifier.equals(other.identifier)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FormQueueLocators [identifier=" + identifier + "]";
    }
}
