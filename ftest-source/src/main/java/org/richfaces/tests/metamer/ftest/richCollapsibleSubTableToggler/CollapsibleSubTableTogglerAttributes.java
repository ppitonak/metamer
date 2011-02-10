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
package org.richfaces.tests.metamer.ftest.richCollapsibleSubTableToggler;

import org.jboss.test.selenium.dom.Event;
import org.richfaces.tests.metamer.ftest.AbstractComponentAttributes;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class CollapsibleSubTableTogglerAttributes extends AbstractComponentAttributes {
    public void setCollapsedIcon(String collapsedIcon) {
        setProperty("collapsedIcon", collapsedIcon);
    }

    public void setCollapsedLabel(String collapsedLabel) {
        setProperty("collapsedLabel", collapsedLabel);
    }

    public void setEvent(Event event) {
        setProperty("event", event);
    }

    public void setExpandedIcon(String expandedIcon) {
        setProperty("expandedIcon", expandedIcon);
    }

    public void setExpandedLabel(String expandedLabel) {
        setProperty("expandedLabel", expandedLabel);
    }

    public void setRendered(Boolean rendered) {
        setProperty("rendered", rendered);
    }
}
