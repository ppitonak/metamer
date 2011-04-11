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
package org.richfaces.tests.metamer.ftest.richPanelMenu;

import static org.testng.Assert.assertTrue;

import org.richfaces.tests.metamer.ftest.model.PanelMenu.Icon;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestPanelMenuIcon extends AbstractPanelMenuTest {

    private static String CUSTOM_URL = "/resources/images/loading.gif";

    @Test
    public void testGroupCollapsedLeftIcon() {
        attributes.setGroupCollapsedLeftIcon(CUSTOM_URL);
        group2.toggle();
        verifyIcon(group24.getLeftIcon());
    }

    @Test
    public void testGroupCollapsedRightIcon() {
        attributes.setGroupCollapsedRightIcon(CUSTOM_URL);
        group2.toggle();
        verifyIcon(group24.getRightIcon());
    }

    @Test
    public void testGroupDisabledLeftIcon() {
        attributes.setGroupDisabledLeftIcon(CUSTOM_URL);
        group2.toggle();
        assertTrue(group26.getLeftIcon().isCustomURL());
        assertTrue(group26.getLeftIcon().getCustomURL().endsWith(CUSTOM_URL));
        verifyIcon(group26.getLeftIcon());
    }

    @Test
    public void testDisabledRightIcon() {
        attributes.setGroupDisabledRightIcon(CUSTOM_URL);
        group2.toggle();
        verifyIcon(group26.getRightIcon());
    }

    @Test
    public void testGroupExpandedLeftIcon() {
        attributes.setGroupExpandedLeftIcon(CUSTOM_URL);
        group2.toggle();
        group24.toggle();
        verifyIcon(group24.getLeftIcon());
    }

    @Test
    public void testGroupExpandedRightIcon() {
        attributes.setGroupExpandedRightIcon(CUSTOM_URL);
        group2.toggle();
        group24.toggle();
        verifyIcon(group24.getRightIcon());
    }

    @Test
    public void testItemDisabledLeftIcon() {
        attributes.setItemDisabledLeftIcon(CUSTOM_URL);
        group2.toggle();
        verifyIcon(item25.getLeftIcon());
    }

    @Test
    public void testItemDisabledRightIcon() {
        attributes.setItemDisabledRightIcon(CUSTOM_URL);
        group2.toggle();
        verifyIcon(item25.getRightIcon());
    }

    @Test
    public void testItemLeftIcon() {
        attributes.setItemLeftIcon(CUSTOM_URL);
        group2.toggle();
        verifyIcon(item22.getLeftIcon());
    }

    @Test
    public void testItemRightIcon() {
        attributes.setItemRightIcon(CUSTOM_URL);
        group2.toggle();
        verifyIcon(item22.getRightIcon());
    }

    @Test
    public void testTopGroupCollapsedLeftIcon() {
        attributes.setTopGroupCollapsedLeftIcon(CUSTOM_URL);
        verifyIcon(group1.getLeftIcon());
    }

    @Test
    public void testTopGroupCollapsedRightIcon() {
        attributes.setTopGroupCollapsedRightIcon(CUSTOM_URL);
        verifyIcon(group1.getRightIcon());
    }

    @Test
    public void testTopGroupDisabledLeftIcon() {
        attributes.setTopGroupDisabledLeftIcon(CUSTOM_URL);
        verifyIcon(group4.getLeftIcon());
    }

    @Test
    public void testTopGroupDisabledRightIcon() {
        attributes.setTopGroupDisabledRightIcon(CUSTOM_URL);
        verifyIcon(group4.getRightIcon());
    }

    @Test
    public void testTopGroupExpandedLeftIcon() {
        attributes.setTopGroupExpandedLeftIcon(CUSTOM_URL);
        group1.toggle();
        verifyIcon(group1.getLeftIcon());
    }

    @Test
    public void testTopGroupExpandedRightIcon() {
        attributes.setTopGroupExpandedRightIcon(CUSTOM_URL);
        group1.toggle();
        verifyIcon(group1.getRightIcon());
    }

    @Test
    public void testTopItemDisabledLeftIcon() {
        attributes.setTopItemDisabledLeftIcon(CUSTOM_URL);
        verifyIcon(item4.getLeftIcon());
    }

    @Test
    public void testTopItemDisabledRightIcon() {
        attributes.setTopItemDisabledRightIcon(CUSTOM_URL);
        verifyIcon(item4.getRightIcon());
    }

    @Test
    public void testTopItemLeftIcon() {
        attributes.setTopItemLeftIcon(CUSTOM_URL);
        verifyIcon(item3.getLeftIcon());
    }

    @Test
    public void testTopItemRightIcon() {
        attributes.setTopItemRightIcon(CUSTOM_URL);
        verifyIcon(item3.getRightIcon());
    }

    private void verifyIcon(Icon icon) {
        assertTrue(icon.isCustomURL());
        assertTrue(icon.getCustomURL().endsWith(CUSTOM_URL));
    }
}
