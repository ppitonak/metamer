package org.richfaces.tests.metamer.ftest.a4jStatus;

import org.richfaces.tests.metamer.ftest.AbstractComponentAttributes;

public class StatusFacets extends AbstractComponentAttributes {

    public StatusFacets() {
        super(AbstractComponentAttributes.Type.AJAX);
    }

    public void setStartText(String startText) {
        setProperty("facetStartText", startText);
    }

    public String getStartText() {
        return getProperty("facetStartText");
    }

    public void setStopText(String stopText) {
        setProperty("facetStopText", stopText);
    }

    public String getStopText() {
        return getProperty("facetStopText");
    }

    public void setErrorText(String errorText) {
        setProperty("facetErrorText", errorText);
    }

    public String getErrorText() {
        return getProperty("facetErrorText");
    }
}
