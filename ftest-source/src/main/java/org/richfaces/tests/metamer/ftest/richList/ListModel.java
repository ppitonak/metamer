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
package org.richfaces.tests.metamer.ftest.richList;

import static org.jboss.test.selenium.locator.LocatorFactory.jq;
import static org.jboss.test.selenium.locator.reference.ReferencedLocator.ref;

import org.jboss.test.selenium.framework.AjaxSelenium;
import org.jboss.test.selenium.framework.AjaxSeleniumProxy;
import org.jboss.test.selenium.locator.JQueryLocator;
import org.jboss.test.selenium.locator.reference.ReferencedLocator;
import org.richfaces.tests.metamer.ftest.model.AbstractModel;
import org.richfaces.tests.metamer.ftest.richList.ListAttributes.Type;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class ListModel extends AbstractModel<JQueryLocator> {

    public ListModel(JQueryLocator root) {
        super(root);
    }

    Type type;
    
    ReferencedLocator<JQueryLocator> rows = ref(root, "li");

    private AjaxSelenium selenium = AjaxSeleniumProxy.getInstance();

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isRendered() {
        return selenium.isElementPresent(getRoot());
    }

    public int getTotalRowCount() {
        return selenium.getCount(getRoot().getChild(getInnerElement()));
    }

    public JQueryLocator getRow(int position) {
        return getRoot().getChild(getInnerElement()).getNthChildElement(position);
    }
    
    public Iterable<JQueryLocator> getRows() {
        return getRoot().getChild(getInnerElement()).getAllChildren();
    }

    public String getRowText(int position) {
        return selenium.getText(getRow(position));
    }

    private JQueryLocator getInnerElement() {
        switch (type) {
            case ORDERED:
                return jq("li.rf-olst-itm");
            case UNORDERED:
                return jq("li.rf-ulst-itm");
            case DEFINITIONS:
                return jq("dd.rf-dlst-dfn");
        }
        throw new IllegalStateException();
    }
}