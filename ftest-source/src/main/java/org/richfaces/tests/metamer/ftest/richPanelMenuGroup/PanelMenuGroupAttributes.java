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
package org.richfaces.tests.metamer.ftest.richPanelMenuGroup;

import org.richfaces.PanelMenuMode;
import org.richfaces.tests.metamer.ftest.AbstractComponentAttributes;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class PanelMenuGroupAttributes extends AbstractComponentAttributes {
    public void setBypassUpdates(Boolean bypassUpdates) {
        setProperty("bypassUpdates", bypassUpdates);
    }

    public void setCollapseEvent(String collapseEvent) {
        setProperty("collapseEvent", collapseEvent);
    }

    public void setData(String data) {
        setProperty("data", data);
    }

    public void setDisabled(Boolean disabled) {
        setProperty("disabled", disabled);
    }

    public void setDisabledClass(String disabledClass) {
        setProperty("disabledClass", disabledClass);
    }

    public void setExecute(String execute) {
        setProperty("execute", execute);
    }

    public void setExpandEvent(String expandEvent) {
        setProperty("expandEvent", expandEvent);
    }

    public void setExpanded(Boolean expanded) {
        setProperty("expanded", expanded);
    }

    public void setImmediate(Boolean immediate) {
        setProperty("immediate", immediate);
    }

    public void setLeftCollapsedIcon(String leftCollapsedIcon) {
        setProperty("leftCollapsedIcon", leftCollapsedIcon);
    }

    public void setLeftDisabledIcon(String leftDisabledIcon) {
        setProperty("leftDisabledIcon", leftDisabledIcon);
    }

    public void setLeftExpandedIcon(String leftExpandedIcon) {
        setProperty("leftExpandedIcon", leftExpandedIcon);
    }

    public void setLimitRender(String limitRender) {
        setProperty("limitRender", limitRender);
    }

    public void setMode(PanelMenuMode mode) {
        setProperty("mode", mode);
    }

    public void setRender(String render) {
        setProperty("render", render);
    }

    public void setRendered(Boolean rendered) {
        setProperty("rendered", rendered);
    }

    public void setRightCollapsedIcon(String rightCollapsedIcon) {
        setProperty("rightCollapsedIcon", rightCollapsedIcon);
    }

    public void setRightDisabledIcon(String rightDisabledIcon) {
        setProperty("rightDisabledIcon", rightDisabledIcon);
    }

    public void setRightExpandedIcon(String rightExpandedIcon) {
        setProperty("rightExpandedIcon", rightExpandedIcon);
    }

    public void setSelectable(Boolean selectable) {
        setProperty("selectable", selectable);
    }

    public void setStatus(String status) {
        setProperty("status", status);
    }
    
    public void setStyle (String style) {
        setProperty("style", style);
    }

    public void setStyleClass(String styleClass) {
        setProperty("styleClass", styleClass);
    }
}
