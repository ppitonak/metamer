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

import static org.jboss.test.selenium.locator.LocatorFactory.jq;
import static org.testng.Assert.assertEquals;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.richfaces.model.SortMode;
import org.richfaces.tests.metamer.model.Employee;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public abstract class DataTableSortingTest extends AbstractDataTableTest {

    @Test
    public void testSortModeSingle() {
        attributes.setSortMode(SortMode.single);

        sortByColumn(COLUMN_TITLE);
        verifySortingByColumns("title");

        sortByColumn(COLUMN_SEX);
        verifySortingByColumns("sex");

        sortByColumn(COLUMN_NAME);
        verifySortingByColumns("name");

        sortByColumn(COLUMN_NUMBER_OF_KIDS1);
        verifySortingByColumns("numberOfKids");

        rerenderAll();
        verifySortingByColumns("numberOfKids");

        fullPageRefresh();
        verifySortingByColumns("numberOfKids");
    }

    @Test
    public void testSortModeMulti() {
        attributes.setSortMode(SortMode.single);

        sortByColumn(COLUMN_TITLE);
        verifySortingByColumns("title");

        sortByColumn(COLUMN_SEX);
        verifySortingByColumns("title", "sex");

        sortByColumn(COLUMN_NUMBER_OF_KIDS1);
        verifySortingByColumns("title", "sex", "numberOfKids");

        sortByColumn(COLUMN_NAME);
        verifySortingByColumns("title", "sex", "numberOfKids", "name");

        sortByColumn(COLUMN_NUMBER_OF_KIDS1);
        verifySortingByColumns("title", "sex", "name", "numberOfKids");

        sortByColumn(COLUMN_TITLE);
        verifySortingByColumns("sex", "name", "numberOfKids", "title");

        rerenderAll();
        verifySortingByColumns("sex", "name", "numberOfKids", "title");

        fullPageRefresh();
        verifySortingByColumns("sex", "name", "numberOfKids", "title");
    }

    int rowIndex;
    int modelIndex;
    List<Employee> sortedEmployees;

    public void sortByColumn(int column) {
        selenium.click(model.getColumnHeader(column).getDescendant(jq("a")));
    }

    public void verifySortingByColumns(String... columns) {
        Comparator<Employee> employeeComparator = getPropertyComparator(Employee.class, columns);
        sortedEmployees = new ArrayList<Employee>(EMPLOYEES);
        Collections.sort(sortedEmployees, employeeComparator);

        int firstPageRows = model.getRows();

        dataScroller1.gotoFirstPage();

        for (rowIndex = 0; rowIndex < model.getRows(); rowIndex++) {
            modelIndex = rowIndex;
            verifyRow(rowIndex, modelIndex);
        }

        dataScroller1.gotoPage(2);

        for (rowIndex = 0; rowIndex < model.getRows(); rowIndex++) {
            modelIndex = firstPageRows + rowIndex;
            verifyRow(rowIndex, modelIndex);
        }

        dataScroller1.gotoLastPage();

        for (rowIndex = 0; rowIndex < model.getRows(); rowIndex++) {
            modelIndex = EMPLOYEES.size() - model.getRows() + rowIndex;
            verifyRow(rowIndex, modelIndex);
        }
    }

    public void verifyRow(int rowIndex, int modelIndex) {
        assertEquals(employees.getSex(rowIndex), sortedEmployees.get(modelIndex).getSex());
        assertEquals(employees.getName(rowIndex), sortedEmployees.get(modelIndex));
        assertEquals(employees.getTitle(rowIndex), sortedEmployees.get(modelIndex).getTitle());
        assertEquals(employees.getNumberOfKids(rowIndex), sortedEmployees.get(modelIndex).getNumberOfKids());
    }

    public <T> Comparator<T> getPropertyComparator(final Class<T> classT, final String... properties) {
        return new Comparator<T>() {

            @Override
            public int compare(T o1, T o2) {
                for (String property : properties) {
                    String getterName = "get" + StringUtils.capitalize(property);
                    try {

                        Method getter = classT.getClass().getMethod(getterName);
                        Object got1 = getter.invoke(o1);
                        Object got2 = getter.invoke(o2);
                        Method compareTo = got1.getClass().getMethod("compareTo", got2.getClass());
                        int result = (Integer) compareTo.invoke(got1, got2);
                        if (result != 0) {
                            return result;
                        }
                    } catch (Exception e) {
                        throw new IllegalArgumentException("Cannot obtain property '" + property + "'", e);
                    }
                }
                return 0;
            }

        };
    }
}
