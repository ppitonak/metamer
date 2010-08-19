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
package org.richfaces.tests.metamer.ftest.a4jStatus;

import org.jboss.test.selenium.locator.ElementLocator;
import org.jboss.test.selenium.locator.JQueryLocator;
import org.richfaces.tests.metamer.ftest.AbstractComponentAttributes;

import static org.richfaces.tests.metamer.ftest.AbstractMetamerTest.pjq;
import static org.jboss.test.selenium.guard.request.RequestTypeGuardFactory.guardXhr;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class StatusFacets extends AbstractComponentAttributes {

    JQueryLocator applyFacetsButton = pjq("input[id$=applyFacets]");

    @Override
    protected void applyText(ElementLocator<?> locator, String value) {
        selenium.type(locator, value);
        guardXhr(selenium).click(applyFacetsButton);
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
