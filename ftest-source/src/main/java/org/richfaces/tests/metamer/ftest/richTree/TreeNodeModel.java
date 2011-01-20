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

import static org.jboss.test.selenium.guard.request.RequestTypeGuardFactory.guardXhr;
import static org.jboss.test.selenium.locator.reference.ReferencedLocator.ref;

import org.jboss.test.selenium.dom.Event;
import org.jboss.test.selenium.framework.AjaxSelenium;
import org.jboss.test.selenium.framework.AjaxSeleniumProxy;
import org.jboss.test.selenium.geometry.Point;
import org.jboss.test.selenium.locator.ExtendedLocator;
import org.jboss.test.selenium.locator.JQueryLocator;
import org.jboss.test.selenium.locator.reference.ReferencedLocator;
import org.junit.Assert;
import org.richfaces.tests.metamer.ftest.model.AbstractModel;
import org.richfaces.tests.metamer.ftest.model.ModelIterable;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TreeNodeModel extends AbstractTreeNodeModel {

    private AjaxSelenium selenium = AjaxSeleniumProxy.getInstance();

    private ReferencedLocator<JQueryLocator> subnodes = ref(root, TreeModel.treeNode.getRawLocator());

    private String classNodeExpanded = "rf-tr-nd-exp";
    private String classNodeLeaf = "rf-tr-nd-lf";
    private String classNodeCollapsed = "rf-tr-nd-colps";
    private String classSelected = "rf-trn-sel";

    private ReferencedLocator<JQueryLocator> treeNode = ref(root, "> div.rf-trn");
    private ReferencedLocator<JQueryLocator> handle = ref(treeNode, "> span.rf-trn-hnd");
    private ReferencedLocator<JQueryLocator> content = ref(treeNode, "> span.rf-trn-cnt");
    private ReferencedLocator<JQueryLocator> icon = ref(content, "> .rf-trn-ico");
    private ReferencedLocator<JQueryLocator> label = ref(content, "> .rf-trn-lbl");

    public TreeNodeModel(JQueryLocator root) {
        super(root);
    }
    
    public ExtendedLocator<JQueryLocator> getTreeNode() {
        return treeNode;
    }

    public TreeNodeHandle getHandle() {
        return new TreeNodeHandle(handle.getReferenced());
    }

    public TreeNodeIcon getIcon() {
        return new TreeNodeIcon(icon.getReferenced());
    }

    public ExtendedLocator<JQueryLocator> getLabel() {
        return label;
    }

    public boolean isSelected() {
        return selenium.belongsClass(root.getLocator(), classSelected);
    }

    public boolean isExpanded() {
        return selenium.belongsClass(root.getLocator(), classNodeExpanded);
    }

    public boolean isCollapsed() {
        return selenium.belongsClass(root.getLocator(), classNodeCollapsed);
    }

    public boolean isLeaf() {
        return selenium.belongsClass(root.getLocator(), classNodeLeaf);
    }

    public String getLabelText() {
        return selenium.getText(label);
    }
    
    public void expand() {
        guardXhr(selenium).click(getHandle());
    }
    
    public void select() {
        guardXhr(selenium).clickAt(getLabel(), new Point(0,0));
    }
}
