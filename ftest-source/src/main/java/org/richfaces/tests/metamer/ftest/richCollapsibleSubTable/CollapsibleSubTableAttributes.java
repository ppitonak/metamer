package org.richfaces.tests.metamer.ftest.richCollapsibleSubTable;

import java.util.Collection;

import org.richfaces.ExpandMode;
import org.richfaces.model.SortMode;
import org.richfaces.tests.metamer.ftest.AbstractComponentAttributes;

public class CollapsibleSubTableAttributes extends AbstractComponentAttributes {
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
