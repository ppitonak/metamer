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
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Managed bean for PrimeFaces charts.
 *
 * @author <a href="mailto:ppitonak@redhat.com">Pavol Pitonak</a>
 * @version $Revision$
 */
@ManagedBean
@ViewScoped
public class ChartBean implements Serializable {

    public class Sale {

        private String brand;
        private int amount;

        public Sale() {
        }

        public Sale(String brand, int amount) {
            this.brand = brand;
            this.amount = amount;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }
    }

    public class BirthData {

        private int year;
        private int boys;
        private int girls;

        public BirthData() {
        }

        public BirthData(int year, int boys, int girls) {
            this.year = year;
            this.boys = boys;
            this.girls = girls;
        }

        public int getBoys() {
            return boys;
        }

        public void setBoys(int boys) {
            this.boys = boys;
        }

        public int getGirls() {
            return girls;
        }

        public void setGirls(int girls) {
            this.girls = girls;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }
    }
    private static Logger logger;
    private List<Sale> sales;
    private List<BirthData> births;

    /**
     * Initializes the managed bean.
     */
    @PostConstruct
    public void init() {
        logger = LoggerFactory.getLogger(getClass());
        logger.debug("initializing bean " + getClass().getName());

        sales = new ArrayList<Sale>(4);
        sales.add(new Sale("Brand 1", 540));
        sales.add(new Sale("Brand 2", 325));
        sales.add(new Sale("Brand 3", 702));
        sales.add(new Sale("Brand 4", 367));

        births = new ArrayList<BirthData>(5);
        births.add(new BirthData(2006, 120, 52));
        births.add(new BirthData(2007, 134, 46));
        births.add(new BirthData(2008, 100, 94));
        births.add(new BirthData(2009, 91, 102));
        births.add(new BirthData(2010, 87, 88));
    }

    public List<Sale> getSales() {
        return sales;
    }

    public List<BirthData> getBirths() {
        return births;
    }
}
