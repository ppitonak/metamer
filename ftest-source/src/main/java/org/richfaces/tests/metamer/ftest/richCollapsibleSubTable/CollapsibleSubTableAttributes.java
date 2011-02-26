/*******************************************************************************
 * JBoss, Home of Professional Open Source
 * Copyright 2010-2011, Red Hat, Inc. and individual contributors
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
package org.richfaces.tests.metamer.ftest.richCollapsibleSubTable;

import static org.richfaces.tests.metamer.ftest.AbstractMetamerTest.pjq;

import java.util.Collection;

import org.jboss.test.selenium.GuardRequest;
import org.jboss.test.selenium.dom.Event;
import org.jboss.test.selenium.locator.JQueryLocator;
import org.jboss.test.selenium.request.RequestType;
import org.richfaces.ExpandMode;
import org.richfaces.model.SortMode;
import org.richfaces.tests.metamer.ftest.AbstractComponentAttributes;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class CollapsibleSubTableAttributes extends AbstractComponentAttributes {

    JQueryLocator showDataLocator = pjq("input[id$=noDataCheckbox]");

    public void setShowData(final boolean showData) {
        new GuardRequest(RequestType.XHR) {
            public void command() {
                selenium.check(showDataLocator, showData);
                selenium.fireEvent(showDataLocator, Event.CLICK);
                selenium.fireEvent(showDataLocator, Event.CHANGE);
            }
        }.waitRequest();
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

    public Integer getRows() {
        return Integer.valueOf(getProperty("rows"));
    }

    public void setSortMode(SortMode sortMode) {
        setProperty("sortMode", sortMode);
    }

    public void setSortPriority(Collection<String> sortPriority) {
        setProperty("sortPriority", sortPriority);
    }

    public void setColumnClasses(String columnClasses) {
        setProperty("columnClasses", columnClasses);
    }

    public void setRowClasses(String rowClasses) {
        setProperty("rowClasses", rowClasses);
    }

    public void setRowClass(String rowClass) {
        setProperty("rowClass", rowClass);
    }

    public void setHeaderClass(String headerClass) {
        setProperty("headerClass", headerClass);
    }

    public void setFooterClass(String footerClass) {
        setProperty("footerClass", footerClass);
    }
}
