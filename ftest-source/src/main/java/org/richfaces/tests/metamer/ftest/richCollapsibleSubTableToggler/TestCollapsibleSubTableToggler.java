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
package org.richfaces.tests.metamer.ftest.richCollapsibleSubTableToggler;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
import static org.jboss.test.selenium.locator.Attribute.SRC;
import static org.jboss.test.selenium.dom.Event.*;
import static org.jboss.test.selenium.locator.LocatorFactory.jq;
import static org.jboss.test.selenium.utils.URLUtils.buildUrl;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.net.URL;

import org.jboss.test.selenium.dom.Event;
import org.jboss.test.selenium.locator.JQueryLocator;
import org.richfaces.tests.metamer.ftest.AbstractMetamerTest;
import org.richfaces.tests.metamer.ftest.annotations.Inject;
import org.richfaces.tests.metamer.ftest.annotations.IssueTracking;
import org.richfaces.tests.metamer.ftest.annotations.Use;
import org.testng.annotations.Test;

public class TestCollapsibleSubTableToggler extends AbstractMetamerTest {

    private final static String IMAGE_URL = "/resources/images/star.png";
    private final static String LABEL = "Label";

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/richCollapsibleSubTableToggler/simple.xhtml");
    }

    CollapsibleSubTableTogglerAttributes attributes = new CollapsibleSubTableTogglerAttributes();

    JQueryLocator togglers = pjq("span[id$=richSTTControl]");
    JQueryLocator collapsedTogglers = togglers.getChild(jq("span[id$=expand]"));
    JQueryLocator expandedTogglers = togglers.getChild(jq("span[id$=collapse]"));
    JQueryLocator link = jq("a");
    JQueryLocator image = jq("img");

    JQueryLocator subtables = pjq("table.rf-dt > tbody.rf-cst");

    @Inject
    @Use(empty = true)
    Event event;

    Event[] events = new Event[] { DBLCLICK, MOUSEDOWN, MOUSEUP };

    @Test
    @Use(field = "event", value = "events")
    public void testEvent() {
        attributes.setEvent(Event.DBLCLICK);

        TogglerTester togglerTester = new TogglerTester(image, image);
        togglerTester.event = Event.DBLCLICK;
        togglerTester.testToggler();
    }

    @Test
    public void testRendered() {
        attributes.setRendered(false);

        assertFalse(selenium.isElementPresent(togglers));
    }

    @Test
    public void testCollapsedLabel() {
        attributes.setCollapseLabel(LABEL);

        new TogglerTester(link, image) {

            @Override
            public void verifyBefore() {
                assertEquals(selenium.getText(togglerExpanded), LABEL);
            }

            @Override
            public void verifyMiddle() {
                assertFalse(selenium.isVisible(togglerExpanded));
                assertTrue(selenium.isVisible(togglerCollapsed));
            }

            @Override
            public void verifyAfter() {
                assertTrue(selenium.isVisible(togglerExpanded));
                assertFalse(selenium.isVisible(togglerCollapsed));
                assertEquals(selenium.getText(togglerExpanded), LABEL);
            }
        }.testToggler();
    }

    @Test
    public void testExpandedLabel() {
        attributes.setExpandLabel(LABEL);

        new TogglerTester(image, link) {
            String imageUrl;

            @Override
            public void verifyBefore() {
                selenium.getAttribute(togglerExpanded.getAttribute(SRC));
                assertTrue(selenium.isVisible(togglerExpanded));
                assertFalse(selenium.isVisible(togglerCollapsed));
                imageUrl = selenium.getAttribute(togglerExpanded.getAttribute(SRC));
            }

            @Override
            public void verifyAfter() {
                assertTrue(selenium.isVisible(togglerExpanded));
                assertFalse(selenium.isVisible(togglerCollapsed));
                assertEquals(selenium.getAttribute(togglerExpanded.getAttribute(SRC)), imageUrl);
            }

            @Override
            public void verifyMiddle() {
                assertFalse(selenium.isVisible(togglerExpanded));
                assertTrue(selenium.isVisible(togglerCollapsed));
                assertEquals(selenium.getText(togglerCollapsed), LABEL);
            }
        }.testToggler();
    }

    @IssueTracking("https://issues.jboss.org/browse/RF-9725")
    @Test
    public void testExpandedIcon() {
        attributes.setExpandIcon(IMAGE_URL);

        new TogglerTester(image, image) {

            @Override
            public void verifyBefore() {
                assertTrue(selenium.isVisible(togglerExpanded));
                assertFalse(selenium.isVisible(togglerCollapsed));
                assertTrue(selenium.getAttribute(togglerCollapsed.getAttribute(SRC)).contains(IMAGE_URL));

            }

            @Override
            public void verifyMiddle() {
                assertFalse(selenium.isVisible(togglerExpanded));
                assertTrue(selenium.isVisible(togglerCollapsed));
                assertFalse(selenium.getAttribute(togglerCollapsed.getAttribute(SRC)).contains(IMAGE_URL));
            }

            @Override
            public void verifyAfter() {
                assertTrue(selenium.isVisible(togglerExpanded));
                assertFalse(selenium.isVisible(togglerCollapsed));
                assertTrue(selenium.getAttribute(togglerCollapsed.getAttribute(SRC)).contains(IMAGE_URL));
            }
        }.testToggler();
    }

    @IssueTracking("https://issues.jboss.org/browse/RF-9725")
    @Test
    public void testCollapsedIcon() {
        attributes.setCollapseIcon(IMAGE_URL);

        new TogglerTester(image, image) {

            String imageUrl;

            @Override
            public void verifyBefore() {
                assertTrue(selenium.isVisible(togglerExpanded));
                assertFalse(selenium.isVisible(togglerCollapsed));
                imageUrl = selenium.getAttribute(togglerExpanded.getAttribute(SRC));
            }

            @Override
            public void verifyMiddle() {
                assertFalse(selenium.isVisible(togglerExpanded));
                assertTrue(selenium.isVisible(togglerCollapsed));
                assertTrue(selenium.getAttribute(togglerCollapsed.getAttribute(SRC)).contains(IMAGE_URL));
            }

            @Override
            public void verifyAfter() {
                assertTrue(selenium.isVisible(togglerExpanded));
                assertFalse(selenium.isVisible(togglerCollapsed));
                assertEquals(selenium.getAttribute(togglerExpanded.getAttribute(SRC)), imageUrl);
            }
        }.testToggler();
    }

    public class TogglerTester {

        Event event = Event.CLICK;

        JQueryLocator expander;
        JQueryLocator collapser;
        JQueryLocator togglerRoot;

        JQueryLocator subtable;
        JQueryLocator togglerExpanded;
        JQueryLocator togglerCollapsed;

        public TogglerTester(JQueryLocator expander, JQueryLocator collapser) {
            this.expander = expander;
            this.collapser = collapser;
        }

        public void testToggler() {
            for (int i = 1; i <= 2; i++) {
                subtable = subtables.getNthOccurence(i);
                togglerRoot = togglers.getNthOccurence(i);
                togglerExpanded = expandedTogglers.getNthOccurence(i).getChild(expander);
                togglerCollapsed = collapsedTogglers.getNthOccurence(i).getChild(collapser);

                verifyBefore();

                if (event == Event.CLICK) {
                    selenium.click(togglerExpanded);
                } else {
                    selenium.fireEvent(togglerRoot, event);
                }
                waitGui.until(isNotDisplayed.locator(subtable));

                verifyMiddle();

                if (event == Event.CLICK) {
                    selenium.click(togglerCollapsed);
                } else {
                    selenium.fireEvent(togglerRoot, event);
                }
                waitGui.until(isDisplayed.locator(subtable));

                verifyAfter();
            }
        }

        public void verifyBefore() {
        }

        public void verifyMiddle() {
        }

        public void verifyAfter() {
        }
    }
}
