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
import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.richfaces.component.UIDataScroller;
import org.richfaces.tests.metamer.Attributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Managed bean for rich:dataScroller.
 *
 * @author <a href="mailto:ppitonak@redhat.com">Pavol Pitonak</a>
 * @version $Revision$
 */
@ManagedBean(name = "richDataScrollerBean")
@SessionScoped
public class RichDataScrollerBean implements Serializable {

    private static final long serialVersionUID = 122475400649809L;
    private static Logger logger;
    private Attributes attributes;
    private int page = 1;
    private boolean state = true;
    
    /**
     * Initializes the managed bean.
     */
    @PostConstruct
    public void init() {
        logger = LoggerFactory.getLogger(getClass());
        logger.debug("initializing bean " + getClass().getName());

        attributes = Attributes.getComponentAttributesFromClass(UIDataScroller.class, getClass());
        
        attributes.setAttribute("boundaryControls", "show");
        attributes.setAttribute("fastControls", "show");
        attributes.setAttribute("fastStep", 1);
        attributes.setAttribute("lastPageMode", "short");
        attributes.setAttribute("maxPages", 10);
        attributes.setAttribute("rendered", true);
        attributes.setAttribute("render", "richDataTable");

        // FIXME doesn't work: could not find dataTable for datascroller scroller1
        attributes.remove("for");

        // will be tested in another way
        attributes.remove("page");

    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    /**
     * Getter for page.
     * @return number of the page to which table is scrolled
     */
    public int getPage() {
        return page;
    }

    /**
     * Setter for page.
     * @param page number of the page to which table is scrolled
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * Getter for state.
     * @return true if non-empty data model should be used in table
     */
    public boolean isState() {
        return state;
    }

    /**
     * Setter for state.
     * @param state true if non-empty data model should be used in table
     */
    public void setState(boolean state) {
        this.state = state;
    }

}
