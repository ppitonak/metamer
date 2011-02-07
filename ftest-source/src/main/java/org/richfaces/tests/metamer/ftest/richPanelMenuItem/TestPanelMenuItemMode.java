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
package org.richfaces.tests.metamer.ftest.richPanelMenuItem;

import static javax.faces.event.PhaseId.APPLY_REQUEST_VALUES;
import static javax.faces.event.PhaseId.INVOKE_APPLICATION;
import static javax.faces.event.PhaseId.PROCESS_VALIDATIONS;
import static javax.faces.event.PhaseId.RENDER_RESPONSE;
import static javax.faces.event.PhaseId.RESTORE_VIEW;
import static javax.faces.event.PhaseId.UPDATE_MODEL_VALUES;
import static org.jboss.test.selenium.utils.URLUtils.buildUrl;

import java.net.URL;
import java.util.LinkedList;

import javax.faces.event.PhaseId;

import org.jboss.test.selenium.GuardRequest;
import org.jboss.test.selenium.request.RequestType;
import org.richfaces.PanelMenuMode;
import org.richfaces.tests.metamer.ftest.AbstractMetamerTest;
import org.richfaces.tests.metamer.ftest.annotations.Inject;
import org.richfaces.tests.metamer.ftest.annotations.Use;
import org.richfaces.tests.metamer.ftest.model.PanelMenu;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestPanelMenuItemMode extends AbstractMetamerTest {

    PanelMenuItemAttributes attributes = new PanelMenuItemAttributes();
    PanelMenu menu = new PanelMenu(pjq("div.rf-pm[id$=panelMenu]"));
    PanelMenu.Item item = menu.getGroup(1).getItem(2);

    @Inject
    @Use(booleans = { true, false })
    Boolean immediate;

    @Inject
    @Use(booleans = { true, false })
    Boolean bypassUpdates;

    @Inject
    @Use(enumeration = true)
    PanelMenuMode mode = PanelMenuMode.ajax;

    @Inject
    @Use("listeners")
    String listener;
    String[] listeners = new String[] { "phases", "action invoked", "action listener invoked", "executeChecker",
        "item changed" };

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/richPanelMenuItem/simple.xhtml");
    }

    @Test
    public void testMode() {
        attributes.setImmediate(immediate);
        attributes.setBypassUpdates(bypassUpdates);
        attributes.setMode(mode);

        attributes.setExecute("@this executeChecker");

        new GuardRequest(getRequestTypeForMode()) {
            @Override
            public void command() {
                item.select();
            }
        }.waitRequest();

        if (mode != PanelMenuMode.client) {
            if ("phases".equals(listener)) {
                phaseInfo.assertPhases(getExpectedPhases());
            } else {
                phaseInfo.assertListener(getExecutionPhase(), listener);
            }
        }
    }

    private PhaseId[] getExpectedPhases() {
        LinkedList<PhaseId> list = new LinkedList<PhaseId>();
        list.add(RESTORE_VIEW);
        list.add(APPLY_REQUEST_VALUES);
        if (!immediate) {
            list.add(PROCESS_VALIDATIONS);
        }
        if (!immediate && !bypassUpdates) {
            list.add(UPDATE_MODEL_VALUES);
            list.add(INVOKE_APPLICATION);
        }
        list.add(RENDER_RESPONSE);
        return list.toArray(new PhaseId[list.size()]);
    }

    private PhaseId getExecutionPhase() {
        PhaseId[] phases = getExpectedPhases();
        return phases[phases.length - 2];
    }

    private RequestType getRequestTypeForMode() {
        switch (mode) {
            case ajax:
                return RequestType.XHR;
            case server:
                return RequestType.HTTP;
            default:
                return RequestType.NONE;
        }
    }
}
