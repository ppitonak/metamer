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
package org.richfaces.tests.metamer.ftest.a4jAttachQueue;

import org.jboss.test.selenium.encapsulated.JavaScript;
import org.jboss.test.selenium.locator.JQueryLocator;
import org.jboss.test.selenium.locator.ExtendedLocator;
import org.richfaces.tests.metamer.ftest.AbstractComponentAttributes;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class AttachQueueAttributes extends AbstractComponentAttributes {

    public AttachQueueAttributes() {
    }

    public AttachQueueAttributes(ExtendedLocator<JQueryLocator> root) {
        super(root);
    }

    public void setIgnoreDupResponses(boolean ignoreDupResponses) {
        setProperty("ignoreDupResponses", ignoreDupResponses);
    }

    public void setName(String name) {
        setProperty("name", name);
    }

    public void setOnRequestDequeue(JavaScript onRequestDequeue) {
        setProperty("onrequestdequeue", onRequestDequeue);
    }

    public void setOnRequestQueue(JavaScript onRequestQueue) {
        setProperty("onrequestqueue", onRequestQueue);
    }

    public void setQueueId(String queueId) {
        setProperty("queueId", queueId);
    }

    public void setRendered(boolean rendered) {
        setProperty("rendered", rendered);
    }

    public void setRequestDelay(long requestDelay) {
        setProperty("requestDelay", requestDelay);
    }

    public void setRequestGroupingId(String requestGroupingId) {
        setProperty("rquestGroupingId", requestGroupingId);
    }

    public void setTimeout(long timeout) {
        setProperty("timeout", timeout);
    }
}
