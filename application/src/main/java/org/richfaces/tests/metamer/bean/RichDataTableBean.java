/*******************************************************************************
 * JBoss, Home of Professional Open Source
 * Copyright 2010-2011, Red Hat, Inc. and individual contributors
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.ajax4jsf.model.DataComponentState;
import org.richfaces.component.UIDataTable;
import org.richfaces.component.UIDataTableBase;
import org.richfaces.model.Filter;
import org.richfaces.tests.metamer.Attributes;
import org.richfaces.tests.metamer.ColumnSortingMap;
import org.richfaces.tests.metamer.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Managed bean for rich:dataTable.
 * 
 * @author <a href="mailto:ppitonak@redhat.com">Pavol Pitonak</a>
 * @version $Revision$
 */
@ManagedBean(name = "richDataTableBean")
@ViewScoped
public class RichDataTableBean implements Serializable {

    private static final long serialVersionUID = 4814439475400649809L;
    private static Logger logger;
    private Attributes attributes;
    private UIDataTable binding;
    private DataComponentState dataTableState;
    private Map<Object, Integer> stateMap = new HashMap<Object, Integer>();
    private int page = 1;
    // true = model, false = empty table
    private boolean state = true;

    // sorting
    private ColumnSortingMap sorting = new ColumnSortingMap() {
        private static final long serialVersionUID = 1L;

        protected UIDataTableBase getBinding() {
            return binding;
        }

        protected Attributes getAttributes() {
            return attributes;
        }
    };

    // filtering
    private Map<String, Object> filtering = new HashMap<String, Object>();

    // facets
    private Map<String, String> facets = new HashMap<String, String>();

    /**
     * Initializes the managed bean.
     */
    @PostConstruct
    public void init() {
        logger = LoggerFactory.getLogger(getClass());
        logger.debug("initializing bean " + getClass().getName());

        attributes = Attributes.getComponentAttributesFromFacesConfig(UIDataTable.class, getClass());

        attributes.setAttribute("rendered", true);
        attributes.setAttribute("rows", 10);

        // hidden attributes
        attributes.remove("filteringListeners");
        attributes.remove("sortingListeners");
        attributes.remove("filterVar");
        attributes.remove("iterationState");
        attributes.remove("iterationStatusVar");
        attributes.remove("rowAvailable");
        attributes.remove("rowCount");
        attributes.remove("rowData");
        attributes.remove("rowIndex");
        attributes.remove("rowKey");
        attributes.remove("rowKeyConverter");
        attributes.remove("relativeRowIndex");

        // TODO these must be tested in other way
        attributes.remove("componentState");
        attributes.remove("rowKeyVar");
        attributes.remove("stateVar");
        attributes.remove("selection");
        attributes.remove("var");
        attributes.remove("value");
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
    
    public void setBinding(UIDataTable binding) {
        this.binding = binding;
    }
    
    public UIDataTable getBinding() {
        return binding;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public int getPage() {
        return page;
    }

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

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Date getDate() {
        return new Date();
    }

    public Map<String, String> getFacets() {
        return facets;
    }

    public ColumnSortingMap getSorting() {
        return sorting;
    }

    public Map<String, Object> getFiltering() {
        return filtering;
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

}
