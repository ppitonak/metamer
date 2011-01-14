package org.richfaces.tests.metamer.ftest.richCollapsibleSubTable;

import static org.jboss.test.selenium.utils.URLUtils.buildUrl;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.net.URL;

import org.testng.annotations.Test;

public class TestCollapsibleSubTableFacets extends AbstractCollapsibleSubTableTest {

    private static final String SAMPLE_STRING = "Abc123!@#ĚščСам";
    private static final String EMPTY_STRING = "";

    @Override
    public URL getTestUrl() {

        return buildUrl(contextPath, "faces/components/richCollapsibleSubTable/facets.xhtml");
    }

    @Test
    public void testNoDataFacet() {
        assertTrue(subtable.hasVisibleRows());
        attributes.setShowData(false);
        assertFalse(subtable.hasVisibleRows());

        assertTrue(subtable.isNoData());
        assertEquals(selenium.getText(subtable.getNoData()), EMPTY_STRING);

        facets.setNoData(SAMPLE_STRING);

        attributes.setShowData(true);
        assertTrue(subtable.hasVisibleRows());

        attributes.setShowData(false);
        assertFalse(subtable.hasVisibleRows());

        assertTrue(subtable.isNoData());
        assertEquals(selenium.getText(subtable.getNoData()), SAMPLE_STRING);
    }

    @Test
    public void testHeaderInstantChange() {
        facets.setHeader(SAMPLE_STRING);
        assertEquals(selenium.getText(subtable.getHeader()), SAMPLE_STRING);

        facets.setHeader(EMPTY_STRING);
        assertEquals(selenium.getText(subtable.getHeader()), EMPTY_STRING);
    }

    @Test
    public void testFooterInstantChange() {
        facets.setFooter(SAMPLE_STRING);
        assertEquals(selenium.getText(subtable.getFooter()), SAMPLE_STRING);

        facets.setFooter(EMPTY_STRING);
        assertEquals(selenium.getText(subtable.getFooter()), EMPTY_STRING);
    }
}
