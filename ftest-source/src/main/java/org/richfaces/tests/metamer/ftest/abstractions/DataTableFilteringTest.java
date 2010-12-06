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
package org.richfaces.tests.metamer.ftest.abstractions;

import static org.jboss.test.selenium.guard.request.RequestTypeGuardFactory.guardXhr;
import static org.jboss.test.selenium.locator.LocatorFactory.jq;
import static org.jboss.test.selenium.locator.option.OptionLocatorFactory.optionValue;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Collection;
import java.util.List;

import org.jboss.test.selenium.dom.Event;
import org.jboss.test.selenium.locator.Attribute;
import org.jboss.test.selenium.locator.AttributeLocator;
import org.jboss.test.selenium.locator.JQueryLocator;
import org.jboss.test.selenium.locator.option.OptionValueLocator;
import org.richfaces.model.Filter;
import org.richfaces.tests.metamer.model.Employee;
import org.richfaces.tests.metamer.model.Employee.Sex;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public abstract class DataTableFilteringTest extends AbstractDataTableTest {

    private static final String[] FILTER_NAMES = new String[] { "ivan", "Гог", null, "Š" };
    private static final String[] FILTER_TITLES = new String[] { "Director", null, "CEO" };
    private static final Integer[] FILTER_NUMBER_OF_KIDS = new Integer[] { 2, 100, null, 5 };

    JQueryLocator selectSex = jq("select");
    JQueryLocator inputName = jq("input");
    JQueryLocator inputTitle = jq("input");
    JQueryLocator inputNumberOfKids1 = jq("input");
    JQueryLocator inputNumberOfKids2 = jq("input");

    FilteringDataTable filtering = new FilteringDataTable();

    public ExpectedEmployee filterEmployee;
    public List<Employee> expectedEmployees;
    public int rows;

    @BeforeMethod
    public void setup() {
        filterEmployee = new ExpectedEmployee();
    }

    @Test
    public void testFilterSex() {
        filtering.selectSex(Sex.MALE);
        filterEmployee.sex = Sex.MALE;
        verifyFiltering();

        filtering.selectSex(Sex.FEMALE);
        filterEmployee.sex = Sex.FEMALE;
        verifyFiltering();

        filtering.selectSex(null);
        filterEmployee.sex = null;
        verifyFiltering();
    }

    @Test
    public void testFilterName() {
        for (String filterName : FILTER_NAMES) {
            filtering.selectName(filterName);
            filterEmployee.name = filterName;
            verifyFiltering();
        }
    }

    @Test
    public void testFilterTitle() {
        for (String filterTitle : FILTER_TITLES) {
            filtering.selectTitle(filterTitle);
            filterEmployee.title = filterTitle;
            verifyFiltering();
        }
    }

    @Test
    public void testFilterNumberOfKids1() {
        for (Integer filterNumberOfKids : FILTER_NUMBER_OF_KIDS) {
            filtering.selectNumberOfKids1(filterNumberOfKids);
            filterEmployee.numberOfKids1 = filterNumberOfKids;
            verifyFiltering();
        }
    }

    @Test
    public void testFilterCombinations() {
        filtering.selectTitle("Technology");
        filterEmployee.title = "Technology";
        verifyFiltering();

        filtering.selectNumberOfKids1(1);
        filterEmployee.numberOfKids1 = 1;
        verifyFiltering();

        filtering.selectSex(Sex.MALE);
        filterEmployee.sex = Sex.MALE;
        verifyFiltering();

        filtering.selectName("9");
        filterEmployee.name = "9";
        verifyFiltering();

        filtering.selectNumberOfKids1(1);
        filterEmployee.numberOfKids1 = 1;
        verifyFiltering();

        filtering.selectSex(Sex.FEMALE);
        filterEmployee.sex = Sex.FEMALE;
        verifyFiltering();
    }

    @Test
    public void testRefresh() {
        dataScroller1.gotoFirstPage();
        rows = model.getRows();

        filtering.selectName("an");
        filterEmployee.name = "an";

        dataScroller1.gotoLastPage();
        int lastPage = dataScroller1.getCurrentPage();
        assertTrue(lastPage > 1);

        rerenderAll();
        assertEquals(dataScroller1.getCurrentPage(), lastPage);
        verifyPageContent(lastPage);

        fullPageRefresh();
        assertEquals(dataScroller1.getCurrentPage(), lastPage);
        verifyPageContent(lastPage);
    }

    public void verifyFiltering() {
        expectedEmployees = filter(EMPLOYEES, getFilter());

        dataScroller1.gotoFirstPage();
        rows = model.getRows();
        verifyPageContent(1);

        dataScroller1.gotoPage(2);
        verifyPageContent(2);

        dataScroller1.gotoLastPage();
        int lastPage = dataScroller1.getCurrentPage();
        verifyPageContent(lastPage);

        dataScroller1.gotoPage(lastPage - 1);
        verifyPageContent(lastPage - 1);
    }

    public void verifyPageContent(int page) {
        for (int row = 0; row < model.getRows(); row++) {
            int index = (page - 1) * rows + row;
            Employee expectedEmployee = expectedEmployees.get(index);
            filtering.verifyRow(expectedEmployee, row);
        }
    }

    private class ExpectedEmployee {
        Sex sex;
        String name;
        String title;
        Integer numberOfKids1;
    }

    private Filter<Employee> getFilter() {
        return new Filter<Employee>() {
            @Override
            public boolean accept(Employee employee) {
                boolean result = true;
                if (filterEmployee.sex != null) {
                    result &= employee.getSex() == filterEmployee.sex;
                }
                if (filterEmployee.name != null) {
                    result &= employee.getName().toLowerCase().contains(filterEmployee.name.toLowerCase());
                }
                if (filterEmployee.title != null) {
                    result &= employee.getTitle().equals(filterEmployee.title);
                }
                if (filterEmployee.numberOfKids1 != null) {
                    result &= employee.getNumberOfKids() >= filterEmployee.numberOfKids1;
                }
                return result;
            }
        };
    }

    @SuppressWarnings("unchecked")
    private <E, T extends Collection<E>> T filter(T collection, Filter<E> filter) {
        T filteredCollection;
        try {
            filteredCollection = (T) collection.getClass().newInstance();

            for (E element : collection) {
                if (filter.accept(element)) {
                    filteredCollection.add(element);
                }
            }

            return (T) filteredCollection;
        } catch (Exception e) {
            throw new IllegalStateException("Cannot construct new collection", e);
        }
    }

    private class FilteringDataTable {
        public void verifyRow(Employee expectedEmployee, int row) {
            verifySex(expectedEmployee.getSex(), row);
            verifyElement(COLUMN_NAME, row, expectedEmployee.getName());
            verifyElement(COLUMN_TITLE, row, expectedEmployee.getTitle());
            verifyElement(COLUMN_NUMBER_OF_KIDS1, row, expectedEmployee.getNumberOfKids());
            verifyElement(COLUMN_NUMBER_OF_KIDS2, row, expectedEmployee.getNumberOfKids());
        }

        public void selectSex(Sex sex) {
            JQueryLocator select = model.getColumnHeader(COLUMN_SEX).getDescendant(selectSex);
            OptionValueLocator option = sex == null ? optionValue("ALL") : optionValue(sex.toString());
            guardXhr(selenium).select(select, option);
        }

        public void verifySex(Sex expectedSex, int row) {
            JQueryLocator rowLocator = model.getElement(COLUMN_SEX, row);
            AttributeLocator<?> sexLocator = rowLocator.getDescendant(jq("img")).getAttribute(Attribute.ALT);
            String sexString = selenium.getAttribute(sexLocator);
            Sex actualSex = Sex.valueOf(sexString);
            assertEquals(actualSex, expectedSex);
        }

        public void selectName(String name) {
            JQueryLocator input = model.getColumnHeader(COLUMN_NAME).getDescendant(inputName);
            selenium.type(inputName, name);
            guardXhr(selenium).fireEvent(input, Event.BLUR);
        }

        public void selectTitle(String title) {
            JQueryLocator input = model.getColumnHeader(COLUMN_TITLE).getDescendant(inputTitle);
            selenium.type(inputName, title);
            guardXhr(selenium).fireEvent(input, Event.BLUR);
        }

        public void selectNumberOfKids1(int numberOfKids) {
            JQueryLocator input = model.getColumnHeader(COLUMN_NUMBER_OF_KIDS1).getDescendant(inputNumberOfKids1);
            selenium.type(inputName, Integer.toString(numberOfKids));
            guardXhr(selenium).fireEvent(input, Event.BLUR);
        }

        public void verifyElement(int column, int row, Object expectedValue) {
            JQueryLocator locator = model.getColumnHeader(COLUMN_NAME);
            String text = selenium.getText(locator);
            assertEquals(text, expectedValue.toString());
        }

    }
}
