package org.richfaces.tests.metamer.ftest.richCollapsibleSubTable;

import static org.jboss.test.selenium.guard.request.RequestTypeGuardFactory.guardXhr;
import static org.richfaces.tests.metamer.ftest.AbstractMetamerTest.pjq;

import java.util.Collection;

import org.jboss.test.selenium.dom.Event;
import org.jboss.test.selenium.locator.JQueryLocator;
import org.richfaces.ExpandMode;
import org.richfaces.model.SortMode;
import org.richfaces.tests.metamer.ftest.AbstractComponentAttributes;

public class CollapsibleSubTableAttributes extends AbstractComponentAttributes {
    
    JQueryLocator showDataLocator = pjq("input[id$=noDataCheckbox]");

    public void setShowData(boolean showData) {
        selenium.check(showDataLocator, showData);
        guardXhr(selenium).fireEvent(showDataLocator, Event.CLICK);
    }

    public void setExpandMode(ExpandMode expandMode) {
        setProperty("expandMode", expandMode);
    }

    public void setExpanded(Boolean expanded) {
        setProperty("expanded", expanded);
    }

    public void setFirst(Integer first) {
        setProperty("first", first);
    }

    public void setNoDataLabel(String noDataLabel) {
        setProperty("noDataLabel", noDataLabel);
    }

    public void setRendered(Boolean rendered) {
        setProperty("rendered", rendered);
    }

    public void setRows(Integer rows) {
        setProperty("rows", rows);
    }

    public void setSortMode(SortMode sortMode) {
        setProperty("sortMode", sortMode);
    }

    public void setSortPriority(Collection<String> sortPriority) {
        setProperty("sortPriority", sortPriority);
    }
}
