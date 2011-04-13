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

import static org.jboss.test.selenium.guard.request.RequestTypeGuardFactory.guardXhr;
import static org.jboss.test.selenium.locator.LocatorFactory.jq;
import static org.jboss.test.selenium.utils.URLUtils.buildUrl;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.net.URL;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.jboss.test.selenium.locator.JQueryLocator;
import org.jboss.test.selenium.utils.text.SimplifiedFormat;
import org.richfaces.component.SwitchType;
import org.richfaces.tests.metamer.ftest.AbstractMetamerTest;
import org.richfaces.tests.metamer.ftest.annotations.Inject;
import org.richfaces.tests.metamer.ftest.annotations.Use;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
@Use(field = "selectionPaths", value = "")
public class TestTreeSelection extends AbstractMetamerTest {

    protected Integer[][] selectionPaths = new Integer[][] { { 2, 3 }, { 3, 4 }, { 4, 1, 1 }, { 4 }, { 4, 1 },
        { 1, 5 }, { 2, 3, 3 } };

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/richTree/simple.xhtml");
    }

    protected TreeAttributes treeAttributes = new TreeAttributes(jq("span[id*=attributes]"));
    protected TreeModel tree = new TreeModel(pjq("div.rf-tr[id$=richTree]"));
    protected TreeNodeModel treeNode;

    @Inject
    @Use(value = "selectionTypes")
    SwitchType selectionType;
    SwitchType[] selectionTypes = new SwitchType[] { SwitchType.ajax, SwitchType.client };
    SwitchType[] eventEnabledSelectionTypes = new SwitchType[] { SwitchType.ajax };

    JQueryLocator expandAll = jq("input:submit[id$=expandAll]");
    JQueryLocator selection = jq("span[id$=selection]");
    JQueryLocator clientId = jq("span[id$=selectionEventClientId]");
    JQueryLocator newSelection = jq("span[id$=selectionEventNewSelection]");
    JQueryLocator oldSelection = jq("span[id$=selectionEventOldSelection]");

    @BeforeMethod
    public void testInitialize() {
        treeAttributes.setSelectionType(selectionType);
        tree.setSelectionType(selectionType);
    }

    @Test
    public void testTopLevelSelection() {
        assertEquals(tree.getAnySelectedNodesCount(), 0);

        for (TreeNodeModel treeNode : tree.getNodes()) {
            assertFalse(treeNode.isSelected());
            assertTrue(treeNode.isCollapsed());
            treeNode.select();
            assertTrue(treeNode.isSelected());
            assertTrue(treeNode.isCollapsed());

            assertEquals(tree.getAnySelectedNodesCount(), 1);
        }
    }

    @Test
    public void testSubNodesSelection() {
        expandAll();
        assertEquals(tree.getAnySelectedNodesCount(), 0);

        for (Integer[] path : selectionPaths) {
            treeNode = null;
            for (int index : path) {
                treeNode = (treeNode == null) ? tree.getNode(index) : treeNode.getNode(index);
            }
            assertFalse(treeNode.isSelected());
            treeNode.select();
            assertTrue(treeNode.isSelected());
            assertEquals(tree.getAnySelectedNodesCount(), 1);
        }
    }

    @Test
    @Use(field = "selectionType", value = "eventEnabledSelectionTypes")
    public void testSubNodesSelectionEvents() {
        expandAll();
        Integer[] old = null;
        for (Integer[] path : selectionPaths) {
            treeNode = null;
            for (int index : path) {
                treeNode = (treeNode == null) ? tree.getNode(index) : treeNode.getNode(index);
            }
            treeNode.select();
            assertEquals(getClientId(), "richTree");
            assertEquals(
                getSelection(),
                path,
                SimplifiedFormat.format("Actual Selection ({0}) doesn't correspond to expected ({1})",
                    Arrays.deepToString(getSelection()), Arrays.deepToString(path)));
            assertEquals(
                getNewSelection(),
                path,
                SimplifiedFormat.format("Actual New selection ({0}) doesn't correspond to expected ({1})",
                    Arrays.deepToString(getNewSelection()), Arrays.deepToString(path)));
            if (old != null) {
                assertEquals(
                    getOldSelection(),
                    old,
                    SimplifiedFormat.format("Actual Old selection ({0}) doesn't correspond to expected ({1})",
                        Arrays.deepToString(getOldSelection()), Arrays.deepToString(old)));
            } else {
                assertEquals(selenium.getText(oldSelection), "[]");
            }
            old = getNewSelection();
        }
    }

    protected void expandAll() {
        guardXhr(selenium).click(expandAll);
    }

    protected Integer[] getIntsFromString(String string) {
        Pattern pattern = Pattern.compile(".*\\[((?:(?:\\d+)(?:, )?)+)\\].*");
        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            String[] strings = StringUtils.split(matcher.group(1), ", ");
            Integer[] numbers = new Integer[strings.length];
            for (int i = 0; i < strings.length; i++) {
                numbers[i] = Integer.valueOf(strings[i]) + 1;
            }
            return numbers;
        }
        throw new IllegalStateException("pattern does not match");
    }

    private Integer[] getSelection() {
        String string = selenium.getText(selection);
        return getIntsFromString(string);
    }

    private Integer[] getNewSelection() {
        String string = selenium.getText(newSelection);
        return getIntsFromString(string);
    }

    private Integer[] getOldSelection() {
        String string = selenium.getText(oldSelection);
        return getIntsFromString(string);
    }

    private String getClientId() {
        return selenium.getText(clientId);
    }
}
