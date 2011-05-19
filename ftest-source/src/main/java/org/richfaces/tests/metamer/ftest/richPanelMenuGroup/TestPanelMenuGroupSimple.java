package org.richfaces.tests.metamer.ftest.richPanelMenuGroup;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.jboss.test.selenium.GuardRequest;
import org.jboss.test.selenium.request.RequestType;
import org.richfaces.PanelMenuMode;
import org.richfaces.tests.metamer.ftest.annotations.IssueTracking;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestPanelMenuGroupSimple extends AbstractPanelMenuGroupTest {

    private static String sampleImage = "/resources/images/loading.gif";
    private static String chevronDown = "chevronDown";
    private static String chevronDownClass = "rf-ico-chevron-down";

    @Test
    public void testData() {
        attributes.setData("RichFaces 4");
        attributes.setOncomplete("data = event.data");

        retrieveRequestTime.initializeValue();
        topGroup.toggle();
        waitGui.waitForChange(retrieveRequestTime);

        assertEquals(retrieveWindowData.retrieve(), "RichFaces 4");
    }

    @Test
    public void testDisabled() {
        menu.setGroupMode(null);
        assertFalse(topGroup.isDisabled());

        attributes.setDisabled(true);

        assertFalse(topGroup.isSelected());
        assertTrue(topGroup.isDisabled());

        new GuardRequest(RequestType.NONE) {
            public void command() {
                topGroup.toggle();
            }
        }.waitRequest();

        assertFalse(topGroup.isSelected());
        assertTrue(topGroup.isDisabled());
    }

    @Test
    public void testDisabledClass() {
        attributes.setDisabled(true);
        super.testStyleClass(topGroup, "disabledClass");
    }

    @Test
    public void testLeftDisabledIcon() {
        attributes.setLeftDisabledIcon(sampleImage);

        assertTrue(leftIcon.isTransparent());
        assertFalse(leftIcon.isCustomURL());

        attributes.setDisabled(true);

        assertTrue(leftIcon.isCustomURL());
        assertTrue(leftIcon.getCustomURL().endsWith(sampleImage));
    }

    @Test
    public void testLeftCollapsedIcon() {
        attributes.setLeftCollapsedIcon(chevronDown);

        topGroup.toggle();

        assertTrue(leftIcon.containsClass(chevronDownClass));

        attributes.setDisabled(true);

        assertTrue(leftIcon.isTransparent());
    }

    @Test
    public void testLeftExpandedIcon() {
        attributes.setLeftExpandedIcon(chevronDown);

        assertTrue(leftIcon.containsClass(chevronDownClass));

        attributes.setDisabled(true);

        assertTrue(leftIcon.isTransparent());
    }

    @Test
    public void testLimitRender() {
        attributes.setRender("renderChecker");
        attributes.setLimitRender(true);

        retrieveRequestTime.initializeValue();
        retrieveRenderChecker.initializeValue();
        topGroup.toggle();
        waitAjax.waitForChange(retrieveRenderChecker);
        assertFalse(retrieveRequestTime.isValueChanged());
    }

    @Test
    public void testRendered() {
        assertTrue(topGroup.isVisible());

        attributes.setRendered(false);

        assertFalse(topGroup.isVisible());
    }

    @Test
    public void testRightDisabledIcon() {
        attributes.setRightDisabledIcon(sampleImage);

        assertTrue(rightIcon.isTransparent());
        assertFalse(rightIcon.isCustomURL());

        attributes.setDisabled(true);

        assertTrue(rightIcon.isCustomURL());
        assertTrue(rightIcon.getCustomURL().endsWith(sampleImage));
    }

    @Test
    public void testRightExpandedIcon() {
        attributes.setRightExpandedIcon(chevronDown);

        assertTrue(rightIcon.containsClass(chevronDownClass));

        attributes.setDisabled(true);

        assertTrue(rightIcon.isTransparent());
    }

    @Test
    public void testRightCollapsedIcon() {
        attributes.setRightCollapsedIcon(chevronDown);

        topGroup.toggle();

        assertTrue(rightIcon.containsClass(chevronDownClass));

        attributes.setDisabled(true);

        assertTrue(rightIcon.isTransparent());
    }

    @Test
    public void testSelectable() {
        menu.setGroupMode(PanelMenuMode.ajax);

        attributes.setSelectable(false);
        topGroup.toggle();
        assertFalse(topGroup.isSelected());

        attributes.setSelectable(true);
        topGroup.toggle();
        assertTrue(topGroup.isSelected());
    }

    @Test
    public void testStatus() {
        attributes.setStatus("statusChecker");

        retrieveStatusChecker.initializeValue();
        topGroup.toggle();
        waitAjax.waitForChange(retrieveStatusChecker);
    }

    @Test
    @IssueTracking("https://issues.jboss.org/browse/RF-10485")
    public void testStyle() {
        super.testStyle(topGroup, "style");
    }

    @Test
    public void testStyleClass() {
        super.testStyleClass(topGroup, "styleClass");
    }
}
