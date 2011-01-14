package org.richfaces.tests.metamer.ftest.richCollapsibleSubTable;

import static org.jboss.test.selenium.guard.request.RequestTypeGuardFactory.guard;
import static org.jboss.test.selenium.utils.URLUtils.buildUrl;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.net.URL;
import java.util.List;

import org.jboss.test.selenium.request.RequestType;
import org.richfaces.ExpandMode;
import org.richfaces.component.UICollapsibleSubTable;
import org.richfaces.tests.metamer.ftest.annotations.Inject;
import org.richfaces.tests.metamer.ftest.annotations.IssueTracking;
import org.richfaces.tests.metamer.ftest.annotations.Use;
import org.richfaces.tests.metamer.model.Employee;
import org.testng.annotations.Test;

public class TestCollapsibleSubTableSimple extends AbstractCollapsibleSubTableTest {

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/richCollapsibleSubTable/simple.xhtml");
    }

    @Inject
    @Use(empty = true)
    ExpandMode expandMode;

    @Test
    @Use(field = "expandMode", enumeration = true)
    @IssueTracking("https://issues.jboss.org/browse/RF-10181")
    public void testExpandMode() {
        final RequestType requestType = getRequestTypeForExpandMode();

        attributes.setExpandMode(expandMode);

        assertTrue(subtable.hasVisibleRows());
        assertTrue(secondSubtable.hasVisibleRows());

        if (expandMode == ExpandMode.none) {
            guard(selenium, requestType).click(toggler);
            assertEquals(subtable.hasVisibleRows(), ExpandMode.none == expandMode);
            assertTrue(secondSubtable.hasVisibleRows());
        } else {
            for (int i = 0; i < 2; i++) {
                guard(selenium, requestType).click(toggler);
                assertFalse(subtable.hasVisibleRows());
                assertTrue(secondSubtable.hasVisibleRows());

                guard(selenium, requestType).click(toggler);
                assertTrue(subtable.hasVisibleRows());
                assertTrue(secondSubtable.hasVisibleRows());
            }
        }
    }

    @Test
    @Use(field = "configuration", empty = true)
    public void testExpanded() {
        attributes.setExpanded(false);

        assertFalse(configurationMen.subtable.hasVisibleRows());
        assertFalse(configurationWomen.subtable.hasVisibleRows());

        selenium.click(configurationMen.toggler);
        assertTrue(configurationMen.subtable.hasVisibleRows());

        attributes.setExpanded(true);

        assertTrue(configurationMen.subtable.hasVisibleRows());
        assertTrue(configurationWomen.subtable.hasVisibleRows());

        new UICollapsibleSubTable().getCaptionClass();
    }

    @Test
    public void testFirst() {
        attributes.setFirst(2);

        List<Employee> visibleEmployees = employees.subList(2, subtable.getRowCount());

        for (int i = 0; i < visibleEmployees.size(); i++) {
            String name = selenium.getText(subtable.getCell(1, i + 1));
            String title = selenium.getText(subtable.getCell(2, i + 1));

            assertEquals(name, visibleEmployees.get(i).getName());
            assertEquals(title, visibleEmployees.get(i).getTitle());
        }
    }

    @Test
    @Use(field = "configuration", empty = true)
    public void testRendered() {
        attributes.setRendered(false);

        assertFalse(configurationMen.subtable.hasVisibleRows());
        assertFalse(configurationWomen.subtable.hasVisibleRows());

        selenium.click(configurationMen.toggler);
        assertFalse(configurationMen.subtable.hasVisibleRows());

        attributes.setRendered(true);

        assertTrue(configurationMen.subtable.hasVisibleRows());
        assertTrue(configurationWomen.subtable.hasVisibleRows());
    }

    @Test
    public void testRows() {
        attributes.setRows(11);

        List<Employee> visibleEmployees = employees.subList(0, 11);

        assertEquals(subtable.getRowCount(), 11);

        for (int i = 0; i < visibleEmployees.size(); i++) {
            String name = selenium.getText(subtable.getCell(1, i + 1));
            String title = selenium.getText(subtable.getCell(2, i + 1));

            assertEquals(name, visibleEmployees.get(i).getName());
            assertEquals(title, visibleEmployees.get(i).getTitle());
        }
    }

    @Test
    @IssueTracking("https://issues.jboss.org/browse/RF-10217")
    @Use(field = "configuration", empty = true)
    public void testClasses() {
        // TODO classes are currently not working
        fail();
    }

    private RequestType getRequestTypeForExpandMode() {
        switch (expandMode) {
            case ajax:
                return RequestType.XHR;
            case server:
                return RequestType.HTTP;
            default:
                return RequestType.NONE;
        }
    }
}
