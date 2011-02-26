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

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.richfaces.tests.metamer.Attributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Managed bean for p:ajaxStatus.
 * 
 * @author <a href="mailto:ppitonak@redhat.com">Pavol Pitonak</a>
 * @version $Revision$
 */
@ManagedBean(name = "pAjaxStatusBean")
@ViewScoped
public class PAjaxStatusBean implements Serializable {

    private static final long serialVersionUID = -1L;
    private static Logger logger;
    private Attributes attributes;
    private String facetPrestartValue = "PRESTART";
    private String facetStartValue = "START";
    private String facetCompleteValue = "COMPLETE";
    private String facetSuccessValue = "SUCCESS";
    private String facetErrorValue = "ERROR";

    /**
     * Initializes the managed bean.
     */
    @PostConstruct
    public void init() {
        logger = LoggerFactory.getLogger(getClass());
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public String getFacetPrestartValue() {
        return facetPrestartValue;
    }

    public void setFacetPrestartValue(String facetPrestartValue) {
        this.facetPrestartValue = facetPrestartValue;
    }

    public String getFacetStartValue() {
        return facetStartValue;
    }

    public void setFacetStartValue(String facetStartValue) {
        this.facetStartValue = facetStartValue;
    }

    public String getFacetCompleteValue() {
        return facetCompleteValue;
    }

    public void setFacetCompleteValue(String facetCompleteValue) {
        this.facetCompleteValue = facetCompleteValue;
    }

    public String getFacetSuccessValue() {
        return facetSuccessValue;
    }

    public void setFacetSuccessValue(String facetSuccessValue) {
        this.facetSuccessValue = facetSuccessValue;
    }

    public String getFacetErrorValue() {
        return facetErrorValue;
    }

    public void setFacetErrorValue(String facetErrorValue) {
        this.facetErrorValue = facetErrorValue;
    }

    /**
     * Action that creates a 2-second delay.
     * 
     * @throws InterruptedException
     *             if any thread has interrupted the current thread. The interrupted status of the current thread is
     *             cleared when this exception is thrown.
     */
    public void delay() throws InterruptedException {
        Thread.sleep(2000);
    }
}
