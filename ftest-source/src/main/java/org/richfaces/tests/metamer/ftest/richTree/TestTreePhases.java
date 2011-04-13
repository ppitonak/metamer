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
package org.richfaces.tests.metamer.ftest.richTree;

import static org.jboss.test.selenium.locator.LocatorFactory.jq;
import static org.jboss.test.selenium.utils.URLUtils.buildUrl;

import java.net.URL;

import javax.faces.event.PhaseId;

import org.richfaces.tests.metamer.ftest.AbstractMetamerTest;
import org.richfaces.tests.metamer.ftest.annotations.Inject;
import org.richfaces.tests.metamer.ftest.annotations.Use;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestTreePhases extends AbstractMetamerTest {
    @Inject
    @Use(booleans = { true, false })
    Boolean immediate;

    private TreeAttributes treeAttributes = new TreeAttributes(jq("span[id*=attributes]"));
    private TreeModel tree = new TreeModel(pjq("div.rf-tr[id$=richTree]"));

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/richTree/simple.xhtml");
    }

    @BeforeMethod
    public void initialize() {
        treeAttributes.setImmediate(immediate);
    }

    @Test
    public void testPhasesSelection() {
        tree.getNode(2).expand();
        tree.getNode(2).getNode(3).select();
        phaseInfo.assertPhases(PhaseId.ANY_PHASE);
        phaseInfo.assertListener(PhaseId.APPLY_REQUEST_VALUES, "selection change listener invoked");
    }

    @Test
    public void testPhasesToggling() {
        tree.getNode(2).expand();
        PhaseId phaseId = (immediate) ? PhaseId.APPLY_REQUEST_VALUES : PhaseId.PROCESS_VALIDATIONS;
        phaseInfo.assertListener(phaseId, "tree toggle listener invoked");
    }
}
