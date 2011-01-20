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
package org.richfaces.tests.metamer.ftest.richTree;

import org.jboss.test.selenium.locator.ExtendedLocator;
import org.jboss.test.selenium.locator.JQueryLocator;
import org.richfaces.component.SwitchType;
import org.richfaces.tests.metamer.ftest.AbstractComponentAttributes;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TreeAttributes extends AbstractComponentAttributes {

    public TreeAttributes() {
        super();
    }

    public <T extends ExtendedLocator<JQueryLocator>> TreeAttributes(T root) {
        super(root);
    }

    public void setData(String data) {
        setProperty("data", data);
    }

    public void setExecute(String execute) {
        setProperty("execute", execute);
    }

    public void setIconCollapsed(String iconCollapsed) {
        setProperty("iconCollapsed", iconCollapsed);
    }

    public void setIconExpanded(String iconExpanded) {
        setProperty("iconExpanded", iconExpanded);
    }

    public void setIconLeaf(String iconLeaf) {
        setProperty("iconLeaf", iconLeaf);
    }

    public void setImmediate(Boolean immediate) {
        setProperty("immediate", immediate);
    }

    public void setKeepSaved(Boolean keepSaved) {
        setProperty("keepSaved", keepSaved);
    }

    public void setLang(String lang) {
        setProperty("lang", lang);
    }

    public void setLimitRender(Boolean limitRender) {
        setProperty("limitRender", limitRender);
    }

    public void setRender(String render) {
        setProperty("render", render);
    }

    public void setRendered(Boolean rendered) {
        setProperty("rendered", rendered);
    }

    public void setSelectionType(SwitchType selectionType) {
        setProperty("selectionType", selectionType);
    }

    public void setStatus(String status) {
        setProperty("status", status);
    }

    public void setTitle(String title) {
        setProperty("title", title);
    }

    public void setToggleNodeEvent(String toggleNodeEvent) {
        setProperty("toggleNodeEvent", toggleNodeEvent);
    }

    public void setToggleType(SwitchType toggleType) {
        setProperty("toggleType", toggleType);
    }
}
