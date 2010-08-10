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

package org.richfaces.tests.metamer;

/**
 * Representation of a template. The name of the template can be used as
 * a view parameter.
 *
 * @author <a href="mailto:ppitonak@redhat.com">Pavol Pitonak</a>
 * @version $Revision$
 */
public enum Template {
    PLAIN ("plain", "Plain"),
    REDDIV ("redDiv", "Red div"),
    BLUEDIV ("blueDiv", "Blue div"),
    RICHDATATABLE1 ("richDataTable1", "Rich Data Table Row 1"),
    RICHDATATABLE2 ("richDataTable2", "Rich Data Table Row 2"),
    HDATATABLE1 ("hDataTable1", "JSF Data Table Row 1"),
    HDATATABLE2 ("hDataTable2", "JSF Data Table Row 2"),
    UIREPEAT1 ("uiRepeat1", "UI Repeat Row 1"),
    UIREPEAT2 ("uiRepeat2", "UI Repeat Row 2"),
    A4JREPEAT1 ("a4jRepeat1", "A4J Repeat Row 1"),
    A4JREPEAT2 ("a4jRepeat2", "A4J Repeat Row 2");

    /**
     * identifier of a template
     */
    private String name;

    /**
     * human-readable name of the template
     */
    private String desc;

    /**
     * Private constructor.
     * 
     * @param name
     * @param prefix
     */
    private Template(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    /**
     * Gets value of name field.
     * @return value of name field
     */
    public String getName() {
        return name;
    }

    /**
     * Gets value of desc field.
     * @return value of desc field
     */
    public String getDesc() {
        return desc;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return name;
    }
}
