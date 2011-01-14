package org.richfaces.tests.metamer.ftest.richCollapsibleSubTable;

import java.util.LinkedList;
import java.util.List;

import org.richfaces.tests.metamer.bean.Model;
import org.richfaces.tests.metamer.ftest.AbstractMetamerTest;
import org.richfaces.tests.metamer.ftest.annotations.Inject;
import org.richfaces.tests.metamer.ftest.annotations.Use;
import org.richfaces.tests.metamer.ftest.model.CollapsibleSubTable;
import org.richfaces.tests.metamer.ftest.model.CollapsibleSubTableToggler;
import org.richfaces.tests.metamer.ftest.model.DataTable;
import org.richfaces.tests.metamer.model.Employee;
import org.testng.annotations.BeforeMethod;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

public abstract class AbstractCollapsibleSubTableTest extends AbstractMetamerTest {

    private static final List<Employee> EMPLOYEES = Model.unmarshallEmployees();

    CollapsibleSubTableAttributes attributes = new CollapsibleSubTableAttributes();
    DataTable model = new DataTable(pjq("table.rf-dt"));;

    @Inject
    @Use(value = "configuration*")
    Configuration configuration;
    Configuration configurationMen = new Configuration(1, "Men");
    Configuration configurationWomen = new Configuration(2, "Women");

    CollapsibleSubTable subtable;
    CollapsibleSubTableToggler toggler;
    List<Employee> employees;
    
    Configuration secondConfiguration;
    CollapsibleSubTable secondSubtable;
    CollapsibleSubTableToggler secondToggler;

    @BeforeMethod
    public void configure() {
        if (configuration != null) {
            subtable = configuration.subtable;
            toggler = configuration.toggler;
            employees = configuration.employees;
            
            secondConfiguration = configuration.name.equals("Men") ? configurationWomen : configurationMen;
            secondSubtable = secondConfiguration.subtable;
            secondToggler = secondConfiguration.toggler;
        }
    }

    public class Configuration {
        CollapsibleSubTable subtable;
        CollapsibleSubTableToggler toggler;
        List<Employee> employees;
        String name;

        public Configuration(int i, String name) {
            this.name = name;
            subtable = model.getSubtable(i);
            toggler = model.getToggler(i);
            final Employee.Sex sex = (i == 1) ? Employee.Sex.MALE : Employee.Sex.FEMALE;

            employees = new LinkedList<Employee>(Collections2.filter(EMPLOYEES, new Predicate<Employee>() {
                @Override
                public boolean apply(Employee input) {
                    return sex.equals(input.getSex());
                }
            }));
        }
        
        @Override
        public String toString() {
            return name;
        }
    }
}
