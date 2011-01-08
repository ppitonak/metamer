package org.richfaces.tests.metamer.ftest.richList;

import static org.jboss.test.selenium.utils.URLUtils.buildUrl;

import java.net.URL;

import org.richfaces.tests.metamer.ftest.annotations.Inject;
import org.richfaces.tests.metamer.ftest.annotations.Use;
import org.richfaces.tests.metamer.ftest.model.DataScroller;
import org.richfaces.tests.metamer.ftest.richDataScroller.PaginationTester;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestScroller extends AbstractListTest {

    @Inject
    DataScroller dataScroller;
    DataScroller dataScroller1 = PaginationTester.DATA_SCROLLER_OUTSIDE_TABLE;
    DataScroller dataScroller2 = PaginationTester.DATA_SCROLLER_IN_TABLE_FOOTER;

    PaginationTester paginationTester = new PaginationTester() {

        @Override
        protected void verifyBeforeScrolling() {
        }

        @Override
        protected void verifyAfterScrolling() {
            int currentPage = dataScroller.getCurrentPage();
            first = rows * (currentPage - 1);
            verifyList();
        }
    };

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/richList/scroller.xhtml");
    }

    @BeforeMethod
    public void prepareComponent() {
        paginationTester.setDataScroller(dataScroller);

        int lastPage = dataScroller.obtainLastPage();
        dataScroller.setLastPage(lastPage);
        paginationTester.initializeTestedPages(lastPage);
    }

    @Test
    @Use(field = "dataScroller", value = "dataScroller*")
    public void testScrollerWithRowsAttribute() {
        paginationTester.testNumberedPages();
    }
}
