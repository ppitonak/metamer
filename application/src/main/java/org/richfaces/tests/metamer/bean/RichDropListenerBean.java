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

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.richfaces.event.DropEvent;
import org.richfaces.tests.metamer.model.drag.DragValue;
import org.richfaces.tests.metamer.model.drag.DropValue;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
@RequestScoped
@ManagedBean(name = "richDropListenerBean")
public class RichDropListenerBean {

    private DropEvent dropEvent;

    @ManagedProperty("#{richDropTargetBean}")
    private RichDropTargetBean richDropTargetBean;

    public void processDragging(DropEvent dropEvent) {
        DragValue dragValue = (DragValue) dropEvent.getDragValue();
        DropValue dropValue = (DropValue) dropEvent.getDropValue();

        this.dropEvent = dropEvent;
        richDropTargetBean.increaseDropValue();
        richDropTargetBean.getDroppedValues().put(dropValue, dragValue);
    }

    public DropEvent getDropEvent() {
        return dropEvent;
    }

    public RichDropTargetBean getRichDropTargetBean() {
        return richDropTargetBean;
    }

    public void setRichDropTargetBean(RichDropTargetBean richDropBehaviorBean) {
        this.richDropTargetBean = richDropBehaviorBean;
    }
}
