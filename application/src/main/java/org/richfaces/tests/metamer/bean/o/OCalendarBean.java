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
package org.richfaces.tests.metamer.bean.o;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;
import org.richfaces.tests.metamer.bean.RichBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Managed bean for o:calendar.
 *
 * @author <a href="mailto:ppitonak@redhat.com">Pavol Pitonak</a>
 * @version $Revision$
 */
@ManagedBean(name = "oCalendarBean")
@ViewScoped
public class OCalendarBean implements Serializable {

    private static final long serialVersionUID = -1L;
    private static Logger logger;
    private Date date = new Date();
    private TimeZone timeZone = TimeZone.getTimeZone("UTC");
    private Date fromDate;
    private Date toDate;

    /**
     * Initializes the managed bean.
     */
    @PostConstruct
    public void init() {
        logger = LoggerFactory.getLogger(getClass());
        logger.debug("initializing bean " + getClass().getName());
        Calendar cal = Calendar.getInstance(timeZone);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MONTH, 2);
        cal.set(Calendar.YEAR, 2011);
        fromDate = cal.getTime();
        cal.set(Calendar.DAY_OF_MONTH, 31);
        cal.set(Calendar.MONTH, 6);
        cal.set(Calendar.YEAR, 2012);
        toDate = cal.getTime();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    /**
     * A value change listener that logs to the page old and new value.
     *
     * @param event
     *            an event representing the activation of a user interface component
     */
    public void valueChangeListener(ValueChangeEvent event) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy hh:mm");
        sdf.setTimeZone(timeZone);

        String oldDate = "null";
        String newDate = "null";

        if (event.getOldValue() != null) {
            oldDate = sdf.format((Date) event.getOldValue());
        }
        if (event.getNewValue() != null) {
            newDate = sdf.format((Date) event.getNewValue());
        }

        RichBean.logToPage("* value changed: " + oldDate + " -> " + newDate);
    }

    public void validateDate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            return;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime((Date) value);
        int componentYear = cal.get(Calendar.YEAR);

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Date too far in the past.",
                "Select a date from year 1991 or newer.");

        if (componentYear < 1991) {
            FacesContext.getCurrentInstance().addMessage("form:calendar", message);
        }
    }
}
