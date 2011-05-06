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
package org.richfaces.tests.metamer.ftest.richMessage;

/**
 * This should be enum for all know attributes.
 * Need to implement an "retriever" method to get 
 * values from key such as "class" by "_class" key
 *
 * @author <a href="mailto:jjamrich@redhat.com">Jan Jamrich</a>
 * @version $Revision$
 */
public enum RichMessageAttributes {

    AJAX_RENDERED("ajaxRendered"),
    RENDERED("rendered"),
    DIR("dir"),
    FOR("for"),
    LANG("lang"),
    TITLE("title"),
    CLASS("class"),
    STYLE("style"),
    STYLE_CLASS("styleClass");
    private String value;

    RichMessageAttributes(String val) {
        this.value = val;
    }

    @Override
    public String toString() {
        return value;
    }
}
