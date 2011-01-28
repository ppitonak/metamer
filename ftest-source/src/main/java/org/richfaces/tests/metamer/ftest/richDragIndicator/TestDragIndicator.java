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
package org.richfaces.tests.metamer.ftest.richDragIndicator;

import static org.jboss.test.selenium.locator.LocatorFactory.jq;
import static org.jboss.test.selenium.utils.URLUtils.buildUrl;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.net.URL;

import org.jboss.test.selenium.actions.Drag;
import org.jboss.test.selenium.request.RequestType;
import org.richfaces.tests.metamer.ftest.AbstractMetamerTest;
import org.richfaces.tests.metamer.ftest.richDragIndicator.Indicator.IndicatorState;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestDragIndicator extends AbstractMetamerTest {

    private final static String ACCEPT_CLASS = "sample-accept-class";
    private final static String REJECT_CLASS = "sample-reject-class";
    private final static String DRAGGING_CLASS = "sample-dragging-class";

    Draggable drg1 = new Draggable("drg1", pjq("[id$=draggable1]"));
    Draggable drg2 = new Draggable("drg2", pjq("[id$=draggable2]"));
    Draggable drg3 = new Draggable("drg3", pjq("[id$=draggable3]"));

    Droppable drop1 = new Droppable("drop1", pjq("[id$=droppable1]"));
    Droppable drop2 = new Droppable("drop2", pjq("[id$=droppable2]"));

    Indicator indicator = new Indicator("ind", jq("div.rf-ind[id$=indicator]"));

    DragIndicatorAttributes attributes = new DragIndicatorAttributes();

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/richDragIndicator/simple.xhtml");
    }

    @BeforeMethod
    public void setup() {
        attributes.setDraggingClass(DRAGGING_CLASS);
        attributes.setAcceptClass(ACCEPT_CLASS);
        attributes.setRejectClass(REJECT_CLASS);
        indicator.setDraggingClass(DRAGGING_CLASS);
        indicator.setAcceptClass(ACCEPT_CLASS);
        indicator.setRejectClass(REJECT_CLASS);
    }

    @Test
    public void testRendered() {
        attributes.setRendered(false);

        selenium.getPageExtensions().install();
        selenium.getRequestInterceptor().clearRequestTypeDone();

        Drag drag = new Drag(drg1, drop1);
        drag.start();
        assertFalse(selenium.isElementPresent(indicator));
        drag.enter();
        assertFalse(selenium.isElementPresent(indicator));

        drag.drop();

        waitModel.timeout(5000).waitForTimeout();
        assertEquals(selenium.getRequestInterceptor().getRequestTypeDone(), RequestType.NONE);
    }

    @Test
    public void testDragging() {
        Drag drag = new Drag(drg1, drg2);
        indicator.verifyState(IndicatorState.HIDDEN);

        drag.start();
        indicator.verifyState(IndicatorState.DRAGGING);

        drag.mouseOut();
        indicator.verifyState(IndicatorState.DRAGGING);

        drag.enter();
        indicator.verifyState(IndicatorState.DRAGGING);
    }

    @Test
    public void testAccepting() {
        Drag drag = new Drag(drg1, drop1);
        indicator.verifyState(IndicatorState.HIDDEN);

        drag.start();
        indicator.verifyState(IndicatorState.DRAGGING);

        drag.mouseOut();
        indicator.verifyState(IndicatorState.DRAGGING);

        drag.enter();
        indicator.verifyState(IndicatorState.ACCEPTING);
    }

    @Test
    public void testRejecting() {
        Drag drag = new Drag(drg1, drop2);
        indicator.verifyState(IndicatorState.HIDDEN);

        drag.start();
        indicator.verifyState(IndicatorState.DRAGGING);

        drag.mouseOut();
        indicator.verifyState(IndicatorState.DRAGGING);

        drag.enter();
        indicator.verifyState(IndicatorState.REJECTING);
    }

    @Test
    public void testMovingOverDifferentStates() {
        Drag drag = new Drag(drg1, drop2);
        drag.enter();
        indicator.verifyState(IndicatorState.REJECTING);

        drag.setDropTarget(drop1);
        drag.enter();
        indicator.verifyState(IndicatorState.ACCEPTING);

        drag.setDropTarget(drg1);
        drag.enter();
        indicator.verifyState(IndicatorState.DRAGGING);

        drag.setDropTarget(drop1);
        drag.enter();
        indicator.verifyState(IndicatorState.ACCEPTING);

        drag.setDropTarget(drg2);
        drag.enter();
        indicator.verifyState(IndicatorState.DRAGGING);

        drag.setDropTarget(drop2);
        drag.enter();
        indicator.verifyState(IndicatorState.REJECTING);

        drag.setDropTarget(drg2);
        drag.enter();
        indicator.verifyState(IndicatorState.DRAGGING);

        drag.setDropTarget(drop1);
        drag.enter();
        indicator.verifyState(IndicatorState.ACCEPTING);

        drag.setDropTarget(drop2);
        drag.enter();
        indicator.verifyState(IndicatorState.REJECTING);
    }

}
