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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.richfaces.component.SortOrder;

import org.ajax4jsf.model.DataComponentState;
import org.richfaces.component.UIDataTable;
import org.richfaces.model.Filter;
import org.richfaces.tests.metamer.Attributes;
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
@SessionScoped
public class RichDataTableBean implements Serializable {

    private static final long serialVersionUID = 4814439475400649809L;
    private static Logger logger;
    private Attributes attributes;
    private DataComponentState dataTableState;
    private Map<Object, Integer> stateMap = new HashMap<Object, Integer>();
    private int page = 1;
    // true = model, false = empty table
    private boolean state = true;
    // sorting
    private SortOrder capitalsOrder = SortOrder.unsorted;
    private SortOrder statesOrder = SortOrder.unsorted;
    // filtering
    private String sexFilter;
    private String nameFilter;
    private String titleFilter;
    private int kidsFilter;
    private int kidsFilter2;
    // facets
    private Map<String, String> facets = new HashMap<String, String>();

    /**
     * Initializes the managed bean.
     */
    @PostConstruct
    public void init() {
        logger = LoggerFactory.getLogger(getClass());
        logger.debug("initializing bean " + getClass().getName());

        attributes = Attributes.getUIComponentAttributes(UIDataTable.class, getClass());

        attributes.setAttribute("rendered", true);
        attributes.setAttribute("rows", 10);
        attributes.get("sortPriority").setType(Collection.class);
        
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

    public SortOrder getCapitalsOrder() {
        return capitalsOrder;
    }

    public void setCapitalsOrder(SortOrder capitalsOrder) {
        this.capitalsOrder = capitalsOrder;
    }

    public SortOrder getStatesOrder() {
        return statesOrder;
    }

    public void setStatesOrder(SortOrder statesOrder) {
        this.statesOrder = statesOrder;
    }

    public String getSexFilter() {
        return sexFilter;
    }

    public void setSexFilter(String sexFilter) {
        this.sexFilter = sexFilter;
    }

    public String getNameFilter() {
        return nameFilter;
    }

    public void setNameFilter(String nameFilter) {
        this.nameFilter = nameFilter;
    }

    public String getTitleFilter() {
        return titleFilter;
    }

    public void setTitleFilter(String titleFilter) {
        this.titleFilter = titleFilter;
    }

    public int getKidsFilter() {
        return kidsFilter;
    }

    public void setKidsFilter(int kidsFilter) {
        this.kidsFilter = kidsFilter;
    }

    public int getKidsFilter2() {
        return kidsFilter2;
    }

    public void setKidsFilter2(int kidsFilter2) {
        this.kidsFilter2 = kidsFilter2;
    }

    public void sortByCapitals() {
        statesOrder = SortOrder.unsorted;
        if (capitalsOrder.equals(SortOrder.ascending)) {
            setCapitalsOrder(SortOrder.descending);
        } else {
            setCapitalsOrder(SortOrder.ascending);
        }
    }

    public void sortByStates() {
        capitalsOrder = SortOrder.unsorted;
        if (statesOrder.equals(SortOrder.ascending)) {
            setStatesOrder(SortOrder.descending);
        } else {
            setStatesOrder(SortOrder.ascending);
        }
    }
    
    public void sortReset() {
        setStatesOrder(SortOrder.ascending);
        setCapitalsOrder(SortOrder.unsorted);
    }

    public Filter<?> getFilterSexImpl() {
        return new Filter<Employee>() {

            public boolean accept(Employee e) {
                String sex = getSexFilter();
                if (sex == null || sex.length() == 0 || sex.equalsIgnoreCase("all") || sex.equalsIgnoreCase(e.getSex().toString())) {
                    return true;
                }
                return false;
            }
        };
    }

    public Filter<?> getFilterKidsImpl() {
        return new Filter<Employee>() {

            public boolean accept(Employee e) {
                int kids = getKidsFilter();
                if (e.getNumberOfKids() >= kids) {
                    return true;
                }
                return false;
            }
        };
    }
    
    public Map<String, String> getFacets() {
        return facets;
    }
}
