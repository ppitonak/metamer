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

import org.richfaces.tests.metamer.model.*;

import java.net.URL;
import java.util.List;
import javax.annotation.PostConstruct;

import javax.faces.FacesException;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Application scoped managed bean holding models usable e.g. in iteration components.
 *
 * @author Exadel, <a href="mailto:ppitonak@redhat.com">Pavol Pitonak</a>
 * @version $Revision$
 */
@ManagedBean
@ApplicationScoped
public class Model {

    private List<Capital> capitalsList;
    private List<Employee> employeesList;
    private Logger logger;

    @PostConstruct
    public void init() {
        logger = LoggerFactory.getLogger(getClass());
    }

    @XmlRootElement(name = "capitals")
    private static final class CapitalsHolder {

        private List<Capital> capitals;

        @XmlElement(name = "capital")
        public List<Capital> getCapitals() {
            return capitals;
        }

        public void setCapitals(List<Capital> capitals) {
            this.capitals = capitals;
        }
    }

    @XmlRootElement(name = "employees")
    private static final class EmployeesHolder {

        private List<Employee> employees;

        @XmlElement(name = "employee")
        public List<Employee> getEmployees() {
            return employees;
        }

        public void setEmployees(List<Employee> employees) {
            this.employees = employees;
        }
    }

    /**
     * Model containing US states, their capitals and timezones.
     * 
     * @return list of US states and their capitals
     */
    public synchronized List<Capital> getCapitals() {
        if (capitalsList == null) {
            try {
                capitalsList = unmarshallCapitals();
            } catch (JAXBException e) {
                throw new FacesException(e.getMessage(), e);
            }
        }

        return capitalsList;
    }

    /**
     * Unmarshalls the list of capitals
     * 
     * @return the list of capitals
     * @throws JAXBException
     *             if any unexpected errors occurs during unmarshalling
     */
    @SuppressWarnings("restriction")
    public static final List<Capital> unmarshallCapitals() throws JAXBException {
        ClassLoader ccl = Thread.currentThread().getContextClassLoader();
        URL resource = ccl.getResource("org/richfaces/tests/metamer/model/capitals.xml");
        JAXBContext context = JAXBContext.newInstance(CapitalsHolder.class);
        CapitalsHolder capitalsHolder = (CapitalsHolder) context.createUnmarshaller().unmarshal(resource);
        return capitalsHolder.getCapitals();
    }

    /**
     * Model containing employees. Can be used to test various components inside iteration components.
     * 
     * @return list of employees
     */
    public synchronized List<Employee> getEmployees() {
        if (employeesList == null) {
            ClassLoader ccl = Thread.currentThread().getContextClassLoader();
            URL resource = ccl.getResource("org/richfaces/tests/metamer/model/employees.xml");

            JAXBContext context;
            try {
                context = JAXBContext.newInstance(EmployeesHolder.class);
                EmployeesHolder employeesHolder = (EmployeesHolder) context.createUnmarshaller().unmarshal(resource);
                employeesList = employeesHolder.getEmployees();
            } catch (JAXBException e) {
                throw new FacesException(e.getMessage(), e);
            }
        }

        return employeesList;
    }
}
