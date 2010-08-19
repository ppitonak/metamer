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
package org.richfaces.tests.metamer.ftest.richJQuery;

import org.richfaces.component.JQueryTiming;
import org.richfaces.tests.metamer.ftest.AbstractComponentAttributes;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class RichJQueryAttributes extends AbstractComponentAttributes {
    public void setAttachType(String attachType) {
        setProperty("attachType", attachType);
    }

    public void setEvent(String event) {
        setProperty("event", event);
    }

    public void setQuery(String query) {
        setProperty("query", query);
    }

    public void setRendered(String rendered) {
        setProperty("rendered", rendered);
    }

    public void setSelector(String selector) {
        setProperty("selector", selector);
    }

    public void setTiming(JQueryTiming timing) {
        setProperty("timing", timing.toString());
    }
}
