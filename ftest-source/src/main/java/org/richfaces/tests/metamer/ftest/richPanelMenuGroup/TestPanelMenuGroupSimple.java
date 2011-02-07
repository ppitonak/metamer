package org.richfaces.tests.metamer.ftest.richPanelMenuGroup;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.jboss.test.selenium.GuardRequest;
import org.jboss.test.selenium.request.RequestType;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TestPanelMenuGroupSimple extends AbstractPanelMenuGroupTest {

    private static String SAMPLE_IMAGE = "/resources/images/loading.gif";
    private static String CHEVRON_DOWN = "chevronDown";
    private static String CHEVRON_DOWN_CLASS = "rf-ico-chevron-down";

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
        attributes.setLeftDisabledIcon(SAMPLE_IMAGE);

        assertTrue(leftIcon.isTransparent());
        assertFalse(leftIcon.isCustomURL());

        attributes.setDisabled(true);

        assertTrue(leftIcon.isCustomURL());
        assertTrue(leftIcon.getCustomURL().endsWith(SAMPLE_IMAGE));
    }

    @Test
    public void testLeftCollapsedIcon() {
        attributes.setLeftCollapsedIcon(CHEVRON_DOWN);

        topGroup.toggle();

        assertTrue(leftIcon.containsClass(CHEVRON_DOWN_CLASS));

        attributes.setDisabled(true);

        assertTrue(leftIcon.isTransparent());
    }

    @Test
    public void testLeftExpandedIcon() {
        attributes.setLeftExpandedIcon(CHEVRON_DOWN);

        assertTrue(leftIcon.containsClass(CHEVRON_DOWN_CLASS));

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
        attributes.setRightDisabledIcon(SAMPLE_IMAGE);

        assertTrue(rightIcon.isTransparent());
        assertFalse(rightIcon.isCustomURL());

        attributes.setDisabled(true);

        assertTrue(rightIcon.isCustomURL());
        assertTrue(rightIcon.getCustomURL().endsWith(SAMPLE_IMAGE));
    }

    @Test
    public void testRightExpandedIcon() {
        attributes.setRightExpandedIcon(CHEVRON_DOWN);

        assertTrue(rightIcon.containsClass(CHEVRON_DOWN_CLASS));

        attributes.setDisabled(true);

        assertTrue(rightIcon.isTransparent());
    }

    @Test
    public void testRightCollapsedIcon() {
        attributes.setRightCollapsedIcon(CHEVRON_DOWN);

        topGroup.toggle();

        assertTrue(rightIcon.containsClass(CHEVRON_DOWN_CLASS));

        attributes.setDisabled(true);

        assertTrue(rightIcon.isTransparent());
    }

    @Test
    public void testSelectable() {
        attributes.setSelectable(false);

        new GuardRequest(RequestType.XHR) {
            public void command() {
                topGroup.toggle();
            }
        }.waitRequest();

        assertFalse(topGroup.isSelected());

        attributes.setSelectable(true);

        new GuardRequest(RequestType.XHR) {
            public void command() {
                topGroup.toggle();
            }
        }.waitRequest();

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
    public void testStyle() {
        super.testStyle(topGroup, "style");
    }

    @Test
    public void testStyleClass() {
        super.testStyleClass(topGroup, "styleClass");
    }
}
