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
package org.richfaces.tests.metamer.ftest.richTree;

import static org.jboss.test.selenium.JQuerySelectors.append;
import static org.jboss.test.selenium.JQuerySelectors.not;
import static org.jboss.test.selenium.dom.Event.CLICK;
import static org.jboss.test.selenium.dom.Event.DBLCLICK;
import static org.jboss.test.selenium.dom.Event.KEYDOWN;
import static org.jboss.test.selenium.dom.Event.KEYPRESS;
import static org.jboss.test.selenium.dom.Event.KEYUP;
import static org.jboss.test.selenium.dom.Event.MOUSEDOWN;
import static org.jboss.test.selenium.dom.Event.MOUSEMOVE;
import static org.jboss.test.selenium.dom.Event.MOUSEOUT;
import static org.jboss.test.selenium.dom.Event.MOUSEOVER;
import static org.jboss.test.selenium.dom.Event.MOUSEUP;
import static org.jboss.test.selenium.guard.request.RequestTypeGuardFactory.guard;
import static org.jboss.test.selenium.guard.request.RequestTypeGuardFactory.guardXhr;
import static org.jboss.test.selenium.locator.LocatorFactory.jq;
import static org.jboss.test.selenium.utils.URLUtils.buildUrl;
import static org.jboss.test.selenium.utils.text.SimplifiedFormat.format;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.net.URL;

import org.jboss.test.selenium.dom.Event;
import org.jboss.test.selenium.locator.ElementLocator;
import org.jboss.test.selenium.locator.ExtendedLocator;
import org.jboss.test.selenium.locator.JQueryLocator;
import org.jboss.test.selenium.request.RequestType;
import org.richfaces.component.SwitchType;
import org.richfaces.tests.metamer.ftest.AbstractMetamerTest;
import org.richfaces.tests.metamer.ftest.annotations.Inject;
import org.richfaces.tests.metamer.ftest.annotations.IssueTracking;
import org.richfaces.tests.metamer.ftest.annotations.Use;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestTreeSimple extends AbstractMetamerTest {

    private final static String IMAGE_URL = "/resources/images/loading.gif";

    @Inject
    @Use(empty = true)
    Event eventToFire;
    Event[] eventsToFire = new Event[] { MOUSEDOWN, MOUSEUP, MOUSEOVER, MOUSEOUT };

    @Inject
    @Use(empty = true)
    Event domEvent;
    Event[] domEvents = { CLICK, DBLCLICK, KEYDOWN, KEYPRESS, KEYUP, MOUSEDOWN, MOUSEMOVE, MOUSEOUT, MOUSEOVER, MOUSEUP };

    TreeModel tree = new TreeModel(pjq("div.rf-tr[id$=richTree]"));
    TreeNodeModel treeNode;

    TreeAttributes attributes = new TreeAttributes(pjq("span[id*=attributes]"));
    TreeNodeAttributes[] nodeAttributes = new TreeNodeAttributes[] {
        new TreeNodeAttributes(pjq("span[id*=treeNode1Attributes]")),
        new TreeNodeAttributes(pjq("span[id*=treeNode2Attributes]")),
        new TreeNodeAttributes(pjq("span[id*=treeNode3Attributes]")) };

    JQueryLocator expandAll = jq("input:submit[id$=expandAll]");

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/richTree/simple.xhtml");
    }

    @Test
    public void testData() {
        attributes.setData("RichFaces 4");
        attributes.setOncomplete("data = event.data");

        retrieveRequestTime.initializeValue();
        tree.getNode(1).select();
        waitGui.waitForChange(retrieveRequestTime);

        assertEquals(retrieveWindowData.retrieve(), "RichFaces 4");
    }

    @Test
    public void testDir() {
        super.testDir(tree);
    }

    @Test
    public void testExecute() {
        attributes.setExecute("executeChecker @this");
        tree.getNode(1).select();
        assertTrue(selenium.isTextPresent("* executeChecker"));
    }

    @Test
    public void testHandleClass() {
        expandAll();
        super.testStyleClass(tree.getAnyNode().getHandle(), "handleClass");
    }

    @Test
    public void testIconClass() {
        expandAll();
        super.testStyleClass(tree.getAnyNode().getIcon(), "iconClass");
    }

    @Test
    public void testLabelClass() {
        expandAll();
        super.testStyleClass(tree.getAnyNode().getLabel(), "labelClass");
    }

    @Test
    public void testNodeClass() {
        expandAll();
        super.testStyleClass(tree.getAnyNode().getTreeNode(), "nodeClass");
    }

    @Test
    public void testIconCollapsed() {
        attributes.setIconCollapsed(IMAGE_URL);

        for (int i = 0; i < 3; i++) {
            ExtendedLocator<JQueryLocator> icons = tree.getAnyNode().getIcon();
            JQueryLocator iconsWithTheGivenUrl = append(icons, format("[src$={0}]", IMAGE_URL));
            JQueryLocator iconsWithoutTheGivenUrl = not(icons, format("[src$={0}]", IMAGE_URL));

            int with = selenium.getCount(iconsWithTheGivenUrl);
            int without = selenium.getCount(iconsWithoutTheGivenUrl);

            assertEquals(with > 0, i < 2);
            assertEquals(without > 0, i > 0);

            expandLevel(i);
        }
    }

    @Test
    public void testIconExpanded() {
        attributes.setIconExpanded(IMAGE_URL);

        for (int i = 0; i < 3; i++) {
            ExtendedLocator<JQueryLocator> icons = tree.getAnyNode().getIcon();
            JQueryLocator iconsWithTheGivenUrl = append(icons, format("[src$={0}]", IMAGE_URL));
            JQueryLocator iconsWithoutTheGivenUrl = not(not(icons, ".rf-trn-ico-lf"), format("[src$={0}]", IMAGE_URL));

            int with = selenium.getCount(iconsWithTheGivenUrl);
            int without = selenium.getCount(iconsWithoutTheGivenUrl);

            assertEquals(with > 0, i > 0);
            assertEquals(without > 0, i < 2);

            expandLevel(i);
        }
    }

    @Test
    public void testIconLeaf() {
        attributes.setIconLeaf(IMAGE_URL);

        for (int i = 0; i < 3; i++) {
            ExtendedLocator<JQueryLocator> icons = tree.getAnyNode().getIcon();
            JQueryLocator iconsWithTheGivenUrl = append(icons, format("[src$={0}]", IMAGE_URL));
            JQueryLocator iconsWithoutTheGivenUrl = not(not(icons, ".rf-trn-ico-exp"), format("[src$={0}]", IMAGE_URL));

            int with = selenium.getCount(iconsWithTheGivenUrl);
            int without = selenium.getCount(iconsWithoutTheGivenUrl);

            assertEquals(with > 0, i > 1);
            assertEquals(without > 0, i < 2);

            expandLevel(i);
        }
    }

    @Test
    public void testLang() {
        super.testLang(tree);
    }

    @Test
    public void testLimitRender() {
        attributes.setRender("@this renderChecker");
        attributes.setLimitRender(true);
        retrieveRenderChecker.initializeValue();
        String requestTime = retrieveRequestTime.retrieve();
        tree.getNode(1).select();
        waitGui.waitForChange(retrieveRenderChecker);
        assertEquals(retrieveRequestTime.retrieve(), requestTime);
    }

    @Test
    public void testSelectionClientSideEvents() {
        String[] events = new String[] { "beforeselectionchange", "begin", "beforedomupdate", "complete",
            "selectionchange" };
        testRequestEventsBefore(events);
        tree.getNode(1).select();
        testRequestEventsAfter(events);
    }

    @Test
    @IssueTracking("https://issues.jboss.org/browse/RF-10265")
    public void testToggleClientSideEvents() {
        String[] events = new String[] { "beforenodetoggle", "begin", "beforedomupdate", "complete", "nodetoggle" };
        testRequestEventsBefore(events);
        tree.getNode(1).expand();
        testRequestEventsAfter(events);
    }

    @Test
    @Use(field = "domEvent", value = "domEvents")
    public void testDomEvents() {
        testFireEvent(domEvent, tree);
    }

    @Test
    public void testRender() {
        attributes.setRender("@this renderChecker");
        retrieveRenderChecker.initializeValue();
        tree.getNode(1).select();
        waitGui.waitForChange(retrieveRenderChecker);
    }

    @Test
    public void testRendered() {
        assertTrue(selenium.isElementPresent(tree) && selenium.isVisible(tree));
        attributes.setRendered(false);
        assertFalse(selenium.isElementPresent(tree));
    }

    @Test
    public void testStatus() {
        retrieveStatusChecker.initializeValue();
        tree.getNode(1).select();
        assertFalse(retrieveStatusChecker.isValueChanged());

        attributes.setStatus("statusChecker");
        retrieveStatusChecker.initializeValue();
        tree.getNode(1).select();
        assertTrue(retrieveStatusChecker.isValueChanged());
    }

    @Test
    public void testStyle() {
        this.testStyle(tree, "style");
    }

    @Test
    public void testStyleClass() {
        this.testStyleClass(tree, "styleClass");
    }

    @Test
    public void testTitle() {
        this.testTitle(tree);
    }

    @Test
    @Use(field = "eventToFire", value = "eventsToFire")
    public void testToggleNodeEvent() {
        treeNode = tree.getNode(2);
        attributes.setSelectionType(SwitchType.client);
        ExtendedLocator<JQueryLocator> target = treeNode.getLabel();

        for (Event eventToSetup : eventsToFire) {
            assertTrue(treeNode.isCollapsed());
            attributes.setToggleNodeEvent(eventToSetup.getEventName());

            fireEvent(target, eventToFire, eventToSetup);

            if (eventToFire == eventToSetup) {
                assertTrue(treeNode.isExpanded());
                fireEvent(target, eventToFire, eventToSetup);
                assertTrue(treeNode.isCollapsed());
            } else {
                assertTrue(treeNode.isCollapsed());
            }
        }
    }

    private void fireEvent(ElementLocator<?> target, Event eventToFire, Event eventToSetup) {
        RequestType requestType = (eventToFire == eventToSetup) ? RequestType.XHR : RequestType.NONE;
        if (eventToFire == MOUSEDOWN) {
            guard(selenium, requestType).mouseDown(target);
        }
        if (eventToFire == MOUSEUP) {
            guard(selenium, requestType).mouseUp(target);
        }
        if (eventToFire == MOUSEOVER) {
            guard(selenium, requestType).mouseOver(target);
        }
        if (eventToFire == MOUSEOUT) {
            guard(selenium, requestType).mouseOut(target);
        }
    }

    private void expandAll() {
        guardXhr(selenium).click(expandAll);
    }

    private void expandLevel(int level) {
        switch (level) {
            case 0:
                for (TreeNodeModel treeNode1 : tree.getNodes()) {
                    treeNode1.expand();
                }
                break;
            case 1:
                for (TreeNodeModel treeNode1 : tree.getNodes()) {
                    for (TreeNodeModel treeNode2 : treeNode1.getNodes()) {
                        treeNode2.expand();
                    }
                }
            default:
        }
    }
}
