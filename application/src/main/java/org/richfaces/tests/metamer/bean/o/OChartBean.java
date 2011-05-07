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
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.openfaces.component.chart.ChartModel;
import org.openfaces.component.chart.PlainModel;
import org.openfaces.component.chart.PlainSeries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Managed bean for o:chart.
 *
 * @author <a href="mailto:ppitonak@redhat.com">Pavol Pitonak</a>
 * @version $Revision$
 */
@ManagedBean(name = "oChartBean")
@ViewScoped
public class OChartBean implements Serializable {

    private static final long serialVersionUID = -1L;
    private static Logger logger;

    /**
     * Initializes the managed bean.
     */
    @PostConstruct
    public void init() {
        logger = LoggerFactory.getLogger(getClass());
        logger.debug("initializing bean " + getClass().getName());
    }

    public ChartModel getPopulation() {
        Map data = new HashMap();
        data.put("London, United Kingdom", new Integer(7615000));
        data.put("Berlin, Germany", new Integer(3396990));
        data.put("Madrid, Spain", new Integer(3155359));
        data.put("Rome, Italy", new Integer(2542003));
        data.put("Paris, France", new Integer(2142800));

        PlainSeries series = new PlainSeries();
        series.setData(data);
        series.setKey("Largest cities of the European Union");

        PlainModel model = new PlainModel();
        model.addSeries(series);
        return model;
    }
}
