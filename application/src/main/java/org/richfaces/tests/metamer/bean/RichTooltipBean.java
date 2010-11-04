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
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.richfaces.component.UITooltip;
import org.richfaces.tests.metamer.Attributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Managed bean for rich:list.
 * 
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
@ManagedBean(name = "richTooltipBean")
@ViewScoped
public class RichTooltipBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private static Logger logger;
    private Attributes attributes;

    private int counter = 0;

    /**
     * Initializes the managed bean.
     */
    @PostConstruct
    public void init() {
        logger = LoggerFactory.getLogger(getClass());
        logger.debug("initializing bean " + getClass().getName());

        attributes = Attributes.getUIComponentAttributes(UITooltip.class, getClass(), false);
        
        
        // set defaults
        attributes.setAttribute("attached", true);
        attributes.setAttribute("rendered", true);
        attributes.setAttribute("followMouse", true);
        attributes.setAttribute("disabled", false);
        attributes.setAttribute("layout", "inline");
        attributes.setAttribute("direction", "bottomRight");
        attributes.setAttribute("mode", "client");
        attributes.setAttribute("showEvent", "mouseenter");
        attributes.setAttribute("hideEvent", "mouseleave");
        attributes.setAttribute("target", "form:panel");
        
        // intentionally hidden attributes
        attributes.remove("bypassUpdates");
        attributes.remove("converter");
        attributes.remove("data");
        attributes.remove("execute");
        attributes.remove("limitToList");
        attributes.remove("localValue");
        attributes.remove("render");
        attributes.remove("value");
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public int getCounter() {
        return counter++;
    }

    public Date getTooltipDate() {
        return new Date();
    }
}
