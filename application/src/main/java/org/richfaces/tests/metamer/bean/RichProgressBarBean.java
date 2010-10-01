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
import javax.faces.bean.SessionScoped;
import org.richfaces.component.UIProgressBar;

import org.richfaces.tests.metamer.Attributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Managed bean for rich:progressBar.
 *
 * @author <a href="mailto:ppitonak@redhat.com">Pavol Pitonak</a>
 * @version $Revision$
 */
@ManagedBean(name = "richProgressBarBean")
@SessionScoped
public class RichProgressBarBean implements Serializable {

    private static final long serialVersionUID = -1L;
    private static Logger logger;
    private Attributes attributes;
    private boolean buttonRendered = true;
    private Long startTime;

    /**
     * Initializes the managed bean.
     */
    @PostConstruct
    public void init() {
        logger = LoggerFactory.getLogger(getClass());
        logger.debug("initializing bean " + getClass().getName());

        attributes = Attributes.getUIComponentAttributes(UIProgressBar.class, getClass());

        attributes.setAttribute("maxValue", 100);
        attributes.setAttribute("minValue", -1);
        attributes.setAttribute("interval", 1000);
        attributes.setAttribute("rendered", true);

        // attributes tested in another way
        attributes.remove("mode");
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public String startProcess() {
        attributes.get("enabled").setValue(true);
        setButtonRendered(false);
        setStartTime(new Date().getTime());
        return null;
    }

    public Long getCurrentValue() {
        if (Boolean.TRUE.equals(attributes.get("enabled").getValue())) {
            Long current = (new Date().getTime() - startTime) / 1000;
            if (current > 100) {
                setButtonRendered(true);
            } else if (current.equals(0L)) {
                return new Long(1);
            }
            return (new Date().getTime() - startTime) / 1000;
        }
        if (startTime == null) {
            return Long.valueOf(-1);
        } else {
            return Long.valueOf(101);
        }
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public boolean isButtonRendered() {
        return buttonRendered;
    }

    public void setButtonRendered(boolean buttonRendered) {
        this.buttonRendered = buttonRendered;
    }
}
