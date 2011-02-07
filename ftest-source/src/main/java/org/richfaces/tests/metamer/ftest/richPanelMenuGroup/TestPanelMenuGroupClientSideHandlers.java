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
package org.richfaces.tests.metamer.ftest.richPanelMenuGroup;

import static org.jboss.test.selenium.utils.URLUtils.buildUrl;
import static org.richfaces.PanelMenuMode.ajax;
import static org.richfaces.PanelMenuMode.client;
import static org.richfaces.PanelMenuMode.server;

import java.net.URL;

import org.richfaces.tests.metamer.ftest.annotations.Inject;
import org.richfaces.tests.metamer.ftest.annotations.Use;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestPanelMenuGroupClientSideHandlers extends AbstractPanelMenuGroupTest {

    @Inject
    @Use(empty = true)
    String event;
    String[] ajaxExpansionEvents = new String[] { "begin", "beforedomupdate", "beforeexpand", "beforeselect",
        "beforeswitch", "switch", "expand", "select", "complete" };
    String[] ajaxCollapsionEvents = new String[] { "begin", "beforedomupdate", "beforecollapse", "beforeswitch",
        "switch", "collapse", "complete" };
    String[] clientExpansionEvents = new String[] { "beforeexpand", "beforeselect", "beforeswitch", "switch", "expand" };
    String[] clientCollapsionEvents = new String[] { "beforecollapse", "beforeswitch", "switch", "collapse" };
    String[] serverExpansionEvents = new String[] { "switch", "expand" };
    String[] serverCollapsionEvents = new String[] { "switch", "collapse" };

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/richPanelMenuGroup/simple.xhtml");
    }

    @Test
    @Use(field = "event", value = "ajaxCollapsionEvents")
    public void testClientSideCollapsionEvent() {
        attributes.setMode(ajax);
        toggleGroup();
        super.testRequestEventsBefore(event);
        toggleGroup();
        super.testRequestEventsAfter(event);
    }

    @Test
    @Use(field = "event", value = "ajaxExpansionEvents")
    public void testClientSideExpansionEvent() {
        attributes.setMode(ajax);
        super.testRequestEventsBefore(event);
        toggleGroup();
        super.testRequestEventsAfter(event);
    }

    @Test
    public void testClientSideExpansionEventsOrderClient() {
        attributes.setMode(client);
        super.testRequestEventsBefore(serverExpansionEvents);
        toggleGroup();
        super.testRequestEventsAfter(serverExpansionEvents);
    }

    @Test
    public void testClientSideCollapsionEventsOrderClient() {
        attributes.setMode(client);
        toggleGroup();
        super.testRequestEventsBefore(clientCollapsionEvents);
        toggleGroup();
        super.testRequestEventsAfter(clientCollapsionEvents);
    }

    @Test
    public void testClientSideExpansionEventsOrderAjax() {
        attributes.setMode(ajax);
        super.testRequestEventsBefore(ajaxExpansionEvents);
        toggleGroup();
        super.testRequestEventsAfter(ajaxExpansionEvents);
    }

    @Test
    public void testClientSideCollapsionEventsOrderAjax() {
        attributes.setMode(ajax);
        toggleGroup();
        super.testRequestEventsBefore(ajaxCollapsionEvents);
        toggleGroup();
        super.testRequestEventsAfter(ajaxCollapsionEvents);
    }

    @Test
    public void testClientSideExpansionEventsOrderServer() {
        attributes.setMode(server);
        toggleGroup();
        super.testRequestEventsBefore(serverExpansionEvents);
        toggleGroup();
        super.testRequestEventsAfter(serverExpansionEvents);
    }

    @Test
    public void testClientSideCollapsionEventsOrderServer() {
        attributes.setMode(server);
        toggleGroup();
        super.testRequestEventsBefore(serverCollapsionEvents);
        toggleGroup();
        super.testRequestEventsAfter(serverCollapsionEvents);
    }
}
