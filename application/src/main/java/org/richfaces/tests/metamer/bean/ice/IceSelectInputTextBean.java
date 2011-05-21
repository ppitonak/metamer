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
package org.richfaces.tests.metamer.bean.ice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.richfaces.tests.metamer.model.Capital;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Managed bean for ice:selectInputText.
 *
 * @author <a href="mailto:ppitonak@redhat.com">Pavol Pitonak</a>
 * @version $Revision$
 */
@ManagedBean(name = "iceSelectInputTextBean")
@ViewScoped
public class IceSelectInputTextBean implements Serializable {

    private static Logger logger;
    @ManagedProperty(value = "#{model.capitals}")
    private List<Capital> capitals;
    private String selected;
    private List<String> matchesList = new LinkedList<String>();

    /**
     * Initializes the managed bean.
     */
    @PostConstruct
    public void init() {
        logger = LoggerFactory.getLogger(getClass());
        logger.debug("initializing bean " + getClass().getName());
    }

    public List<Capital> getCapitals() {
        return capitals;
    }

    public void setCapitals(List<Capital> capitals) {
        this.capitals = capitals;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public List<SelectItem> getList() {
        List<SelectItem> result = new ArrayList<SelectItem>(matchesList.size());
        for (String state : matchesList) {
            result.add(new SelectItem(state));
        }
        return result;
    }

    public void setList(List<String> matchesList) {
        this.matchesList = matchesList;
    }

    public void updateList(ValueChangeEvent event) {
        matchesList = new ArrayList<String>();
        String prefix = (String) event.getNewValue();
        if (prefix != null && prefix.length() > 0) {
            Iterator<Capital> iterator = capitals.iterator();
            while (iterator.hasNext()) {
                Capital elem = ((Capital) iterator.next());
                if ((elem.getState() != null && elem.getState().toLowerCase().indexOf(prefix.toLowerCase()) == 0)
                        || "".equals(prefix)) {
                    matchesList.add(elem.getState());
                }
            }
        } else {
            for (Capital capital : capitals) {
                matchesList.add(capital.getState());
            }
        }
    }
}
