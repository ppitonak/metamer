package org.richfaces.tests.metamer.ftest.model;

import static org.jboss.test.selenium.locator.reference.ReferencedLocator.ref;

import org.jboss.test.selenium.locator.ExtendedLocator;
import org.jboss.test.selenium.locator.JQueryLocator;
import org.jboss.test.selenium.locator.reference.ReferencedLocator;

public class CollapsibleSubTableToggler extends AbstractModel<JQueryLocator> {

    public CollapsibleSubTableToggler(JQueryLocator root) {
        super(root);
    }

    ReferencedLocator<JQueryLocator> collapsedTogglers = ref(root, "> span[id$=expand]");
    ReferencedLocator<JQueryLocator> expandedTogglers = ref(root, "> span[id$=collapse]");

    public ExtendedLocator<JQueryLocator> getCollapsed() {
        return collapsedTogglers;
    }

    public ExtendedLocator<JQueryLocator> getExpanded() {
        return expandedTogglers;
    }

}
