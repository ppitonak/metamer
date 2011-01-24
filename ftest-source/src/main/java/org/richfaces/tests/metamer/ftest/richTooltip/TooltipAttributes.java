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
package org.richfaces.tests.metamer.ftest.richTooltip;

import org.richfaces.TooltipLayout;
import org.richfaces.TooltipMode;
import org.richfaces.component.Positioning;
import org.richfaces.tests.metamer.ftest.AbstractComponentAttributes;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TooltipAttributes extends AbstractComponentAttributes {

    public void setData(String data) {
        setProperty("data", data);
    }

    public void setDir(String dir) {
        setProperty("dir", dir);
    }

    public void setDirection(Positioning direction) {
        setProperty("direction", direction);
    }

    public void setDisabled(Boolean disabled) {
        setProperty("disabled", disabled);
    }

    public void setExecute(String execute) {
        setProperty("execute", execute);
    }

    public void setFollowMouse(Boolean followMouse) {
        setProperty("followMouse", followMouse);
    }

    public void setHideDelay(Integer hideDelay) {
        setProperty("hideDelay", hideDelay);
    }

    public void setHideEvent(String hideEvent) {
        setProperty("hideEvent", hideEvent);
    }

    public void setHorizontalOffset(Integer horizontalOffset) {
        setProperty("horizontalOffset", horizontalOffset);
    }

    public void setJointPoint(Positioning positioning) {
        setProperty("positioning", positioning);
    }

    public void setLang(String lang) {
        setProperty("lang", lang);
    }

    public void setLayout(TooltipLayout layout) {
        setProperty("layout", layout);
    }

    public void setLimitRender(Boolean limitRender) {
        setProperty("limitRender", limitRender);
    }

    public void setMode(TooltipMode mode) {
        setProperty("mode", mode);
    }

    public void setOnbeforehide(String onbeforehide) {
        setProperty("onbeforehide", onbeforehide);
    }

    public void setOnbeforeshow(String onbeforeshow) {
        setProperty("onbeforeshow", onbeforeshow);
    }

    public void setOnhide(String onhide) {
        setProperty("onhide", onhide);
    }

    public void setOnshow(String onshow) {
        setProperty("onshow", onshow);
    }

    public void setRender(String render) {
        setProperty("render", render);
    }

    public void setRendered(Boolean rendered) {
        setProperty("rendered", rendered);
    }

    public void setShowDelay(Integer showDelay) {
        setProperty("showDelay", showDelay);
    }

    public void setShowEvent(String showEvent) {
        setProperty("showEvent", showEvent);
    }

    public void setStatus(String status) {
        setProperty("status", status);
    }

    public void setStyle(String style) {
        setProperty("style", style);
    }

    public void setStyleClass(String styleClass) {
        setProperty("styleClass", styleClass);
    }

    public void setTarget(String target) {
        setProperty("target", target);
    }

    public void setTitle(String title) {
        setProperty("title", title);
    }

    public void setVerticalOffset(Integer verticalOffset) {
        setProperty("verticalOffset", verticalOffset);
    }

    public void setZindex(Integer zindex) {
        setProperty("zindex", zindex);
    }
}
