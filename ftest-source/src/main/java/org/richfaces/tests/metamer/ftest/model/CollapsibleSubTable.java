package org.richfaces.tests.metamer.ftest.model;

import static org.jboss.test.selenium.locator.LocatorFactory.jq;

import org.jboss.test.selenium.framework.AjaxSelenium;
import org.jboss.test.selenium.framework.AjaxSeleniumProxy;
import org.jboss.test.selenium.locator.JQueryLocator;

public class CollapsibleSubTable extends AbstractModel<JQueryLocator> {

    AjaxSelenium selenium = AjaxSeleniumProxy.getInstance();

    JQueryLocator subtableRow = jq("tr[class^=rf-cst][class$=-r]");
    JQueryLocator subtableCell = jq("td.rf-cst-c");

    JQueryLocator visible = jq("{0}:visible");

    public CollapsibleSubTable(JQueryLocator root) {
        super(root);
    }

    public Iterable<JQueryLocator> getRows() {
        return root.getLocator().getChildren(subtableRow);
    }

    public JQueryLocator getRow(int rowIndex) {
        return root.getLocator().getChild(subtableRow).getNthOccurence(rowIndex);
    }

    public int getRowCount() {
        return selenium.getCount(root.getLocator().getChild(subtableRow));
    }

    public boolean hasVisibleRows() {
        JQueryLocator locator = root.getLocator().getChild(subtableRow);
        locator = visible.format(locator.getRawLocator());
        return selenium.isElementPresent(locator);
    }

    public JQueryLocator getCell(int column, int row) {
        return getRow(row).getChild(subtableCell).getNthOccurence(column);
    }
}
