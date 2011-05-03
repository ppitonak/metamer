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
package org.richfaces.tests.metamer.bean.p;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.richfaces.tests.metamer.model.Capital;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Managed bean for p:autoComplete.
 *
 * @author <a href="mailto:ppitonak@redhat.com">Pavol Pitonak</a>
 * @version $Revision$
 */
@ManagedBean(name = "pAutoCompleteBean")
@ViewScoped
public class PAutoCompleteBean implements Serializable {

    private static final long serialVersionUID = -1L;
    private static Logger logger;
    @ManagedProperty(value = "#{model.capitals}")
    private List<Capital> capitals;
    private String value;

    /**
     * Initializes the managed bean.
     */
    @PostConstruct
    public void init() {
        logger = LoggerFactory.getLogger(getClass());
        logger.debug("initializing bean " + getClass().getName());
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<String> complete(String prefix) {
        ArrayList<String> result = new ArrayList<String>();
        if (prefix.length() > 0) {
            Iterator<Capital> iterator = capitals.iterator();
            while (iterator.hasNext()) {
                Capital elem = ((Capital) iterator.next());
                if ((elem.getState() != null && elem.getState().toLowerCase().indexOf(prefix.toLowerCase()) == 0)
                        || "".equals(prefix)) {
                    result.add(elem.getState());
                }
            }
        } else {
            for (int i = 0; i < capitals.size(); i++) {
                result.add(capitals.get(i).getState());
            }
        }
        return result;
    }

    public void setCapitals(List<Capital> capitals) {
        this.capitals = capitals;
    }
}
