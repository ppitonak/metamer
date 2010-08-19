package org.richfaces.tests.metamer.ftest.a4jStatus;

import org.richfaces.tests.metamer.ftest.AbstractComponentAttributes;

public class StatusFacets extends AbstractComponentAttributes {

    public void setStartText(String startText) {
        setProperty("facetStartText", startText);
    }

    public void setStopText(String stopText) {
        setProperty("facetStopText", stopText);
    }

    public void setErrorText(String errorText) {
        setProperty("facetErrorText", errorText);
    }
}
