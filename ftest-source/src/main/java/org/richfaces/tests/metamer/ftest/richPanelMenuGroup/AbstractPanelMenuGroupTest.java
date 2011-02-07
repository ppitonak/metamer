package org.richfaces.tests.metamer.ftest.richPanelMenuGroup;

import static org.jboss.test.selenium.utils.URLUtils.buildUrl;

import java.net.URL;

import org.jboss.test.selenium.GuardRequest;
import org.jboss.test.selenium.request.RequestType;
import org.richfaces.tests.metamer.ftest.AbstractMetamerTest;
import org.richfaces.tests.metamer.ftest.model.PanelMenu;

public abstract class AbstractPanelMenuGroupTest extends AbstractMetamerTest {

    PanelMenuGroupAttributes attributes = new PanelMenuGroupAttributes();
    PanelMenu menu = new PanelMenu(pjq("div.rf-pm[id$=panelMenu]"));
    PanelMenu.Group topGroup = menu.getGroupContains("Group 2");
    PanelMenu.Group subGroup = topGroup.getGroupContains("Group 2.3");
    PanelMenu.Icon leftIcon = topGroup.getLeftIcon();
    PanelMenu.Icon rightIcon = topGroup.getRightIcon();

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/richPanelMenuGroup/simple.xhtml");
    }
    
    void toggleGroup() {
        new GuardRequest(getRequestTypeForMode()) {
            @Override
            public void command() {
                topGroup.toggle();
            }
        }.waitRequest();
    }

    RequestType getRequestTypeForMode() {
        switch (attributes.getMode()) {
            case ajax:
                return RequestType.XHR;
            case server:
                return RequestType.HTTP;
            default:
                return RequestType.NONE;
        }
    }
}
