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
package org.richfaces.tests.metamer.ftest.model;

import org.jboss.test.selenium.framework.AjaxSelenium;
import org.jboss.test.selenium.framework.AjaxSeleniumProxy;
import org.jboss.test.selenium.locator.JQueryLocator;
import org.jboss.test.selenium.locator.reference.ReferencedLocator;

import static org.jboss.test.selenium.locator.reference.ReferencedLocator.*;

/**
 * Provides methods to control DataTable
 * 
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class DataTable extends AbstractModel<JQueryLocator> {

    AjaxSelenium selenium = AjaxSeleniumProxy.getInstance();

    ReferencedLocator<JQueryLocator> tableRows = ref(root, "> div.rf-edt-b table table tr");

    public DataTable(JQueryLocator root) {
        super(root);
    }
    
    public DataTable(String name, JQueryLocator root) {
        super(name, root);
    }

    public int getCountOfTableRows() {
        return selenium.getCount(tableRows);
    }

    public String getTableText() {
        return selenium.getText(root.getLocator());
    }
}
