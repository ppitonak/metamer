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

package org.richfaces.tests.metamer.bean;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.ajax4jsf.model.DataComponentState;
import org.richfaces.component.SortOrder;
import org.richfaces.component.UIExtendedDataTable;
import org.richfaces.model.Filter;
import org.richfaces.model.SortMode;
import org.richfaces.tests.metamer.Attributes;
import org.richfaces.tests.metamer.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Managed bean for rich:extendedDataTable.
 * 
 * @author <a href="mailto:ppitonak@redhat.com">Pavol Pitonak</a>
 * @version $Revision$
 */
@ManagedBean(name = "richExtendedDataTableBean")
@ViewScoped
public class RichExtendedDataTableBean implements Serializable {

    private static final long serialVersionUID = 481478880649809L;
    private static Logger logger;
    private Attributes attributes;
    private DataComponentState dataTableState;
    private Map<Object, Integer> stateMap = new HashMap<Object, Integer>();
    private int page = 1;
    // true = model, false = empty table
    private boolean state = true;

    // sorting
    private Map<String, ColumnSorting> sorting = new ColumnSortingMap();

    // filtering
    private Map<String, Object> filtering = new HashMap<String, Object>();

    // facets
    private Map<String, String> facets = new HashMap<String, String>();

    private UIExtendedDataTable binding;

    /**
     * Initializes the managed bean.
     */
    @PostConstruct
    public void init() {
        logger = LoggerFactory.getLogger(getClass());
        logger.debug("initializing bean " + getClass().getName());

        attributes = Attributes.getUIComponentAttributes(UIExtendedDataTable.class, getClass());

        attributes.setAttribute("rendered", true);
        attributes.setAttribute("rows", 30);
        attributes.setAttribute("styleClass", "extended-data-table");
        attributes.setAttribute("style", null);

        // hidden attributes
        attributes.remove("filterVar");
        attributes.remove("filteringListeners");
        attributes.remove("iterationState");
        attributes.remove("iterationStatusVar");
        attributes.remove("relativeRowIndex");
        attributes.remove("rowAvailable");
        attributes.remove("rowCount");
        attributes.remove("rowData");
        attributes.remove("rowIndex");
        attributes.remove("rowIndex");
        attributes.remove("rowKey");
        attributes.remove("rowKeyConverter");
        attributes.remove("sortingListeners");
        attributes.remove("clientFirst");
        attributes.remove("clientRows");

        // TODO these must be tested in other way
        attributes.remove("componentState");
        attributes.remove("rowKeyVar");
        attributes.remove("stateVar");
        attributes.remove("value");
        attributes.remove("var");
        attributes.remove("keepSaved");

        // TODO can be these set as attributes or only as facets?
        attributes.remove("caption");
        attributes.remove("header");
        attributes.remove("footer");
        attributes.remove("noData");

        // facets initial values
        facets.put("noData", "There is no data.");
        facets.put("caption", "Caption");
        facets.put("header", "Header");
        facets.put("columnStateHeader", "State Header");
        facets.put("columnStateFooter", "State Footer");
        facets.put("columnCapitalHeader", "Capital Header");
        facets.put("columnCapitalFooter", "Capital Footer");
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public UIExtendedDataTable getBinding() {
        return binding;
    }

    public void setBinding(UIExtendedDataTable binding) {
        this.binding = binding;
    }

    /**
     * Getter for page.
     * 
     * @return page number that will be used by data scroller
     */
    public int getPage() {
        return page;
    }

    /**
     * Setter for page.
     * 
     * @param page
     *            page number that will be used by data scroller
     */
    public void setPage(int page) {
        this.page = page;
    }

    public Map<Object, Integer> getStateMap() {
        return stateMap;
    }

    public void setStateMap(Map<Object, Integer> stateMap) {
        this.stateMap = stateMap;
    }

    public DataComponentState getDataTableState() {
        return dataTableState;
    }

    public void setDataTableState(DataComponentState dataTableState) {
        this.dataTableState = dataTableState;
    }

    /**
     * Getter for state.
     * 
     * @return true if data should be displayed in table
     */
    public boolean isState() {
        return state;
    }

    /**
     * Setter for state.
     * 
     * @param state
     *            true if data should be displayed in table
     */
    public void setState(boolean state) {
        this.state = state;
    }

    public Filter<?> getFilterSexImpl() {
        return new Filter<Employee>() {

            public boolean accept(Employee e) {
                String sex = (String) getFiltering().get("sex");
                if (sex == null || sex.length() == 0 || sex.equalsIgnoreCase("all")
                    || sex.equalsIgnoreCase(e.getSex().toString())) {
                    return true;
                }
                return false;
            }
        };
    }

    public Map<String, String> getFacets() {
        return facets;
    }

    public Map<String, ColumnSorting> getSorting() {
        return sorting;
    }

    public Map<String, Object> getFiltering() {
        return filtering;
    }

    public class ColumnSortingMap extends TreeMap<String, ColumnSorting> {
        private static final long serialVersionUID = 1L;

        public ColumnSorting get(Object key) {
            if (key instanceof String && !containsKey(key)) {
                String columnName = (String) key;
                put(columnName, new ColumnSorting(columnName));
            }
            return super.get(key);
        }
    }

    public class ColumnSorting {
        private String columnName;
        private SortOrder order = SortOrder.unsorted;

        public ColumnSorting(String key) {
            this.columnName = key;
        }

        public SortOrder getOrder() {
            return order;
        }

        public void setOrder(SortOrder order) {
            this.order = order;
        }

        @SuppressWarnings("unchecked")
        public void reverseOrder() {
            SortMode mode = binding.getSortMode();

            Object sortOrderObject = getAttributes().get("sortPriority").getValue();
            Collection<String> sortPriority;
            if (sortOrderObject instanceof Collection) {
                sortPriority = (Collection<String>) sortOrderObject;
            } else {
                throw new IllegalStateException("sortOrder attribute have to be Collection");
            }

            if (SortMode.single.equals(mode)) {
                sorting.clear();
                sorting.put(columnName, this);

                sortPriority.clear();
            } else {
                sortPriority.remove(columnName);
            }

            sortPriority.add(columnName);

            if (SortOrder.ascending.equals(order)) {
                order = SortOrder.descending;
            } else {
                order = SortOrder.ascending;
            }
        }
    }
}
