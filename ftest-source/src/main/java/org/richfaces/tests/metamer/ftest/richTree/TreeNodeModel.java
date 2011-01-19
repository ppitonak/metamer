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

import static org.jboss.test.selenium.locator.reference.ReferencedLocator.ref;

import org.jboss.test.selenium.framework.AjaxSelenium;
import org.jboss.test.selenium.framework.AjaxSeleniumProxy;
import org.jboss.test.selenium.locator.ExtendedLocator;
import org.jboss.test.selenium.locator.JQueryLocator;
import org.jboss.test.selenium.locator.reference.ReferencedLocator;
import org.richfaces.tests.metamer.ftest.model.AbstractModel;
import org.richfaces.tests.metamer.ftest.model.ModelIterable;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class TreeNodeModel extends AbstractModel<JQueryLocator> {

    AjaxSelenium selenium = AjaxSeleniumProxy.getInstance();

    ReferencedLocator<JQueryLocator> subnodes = ref(root, TreeModel.treeNode.getRawLocator());

    String classNodeExpanded = "rf-tr-nd-exp";
    String classNodeLeaf = "rf-tr-nd-lf";
    String classNodeCollapsed = "rf-tr-nd-lf";
    String classSelected = "rf-trn-sel";

    ReferencedLocator<JQueryLocator> treeNode = ref(root, "> div.rf-trn");
    ReferencedLocator<JQueryLocator> handle = ref(treeNode, "> span.rf-trn-hnd");
    ReferencedLocator<JQueryLocator> content = ref(treeNode, "> span.rf-trn-cnt");
    ReferencedLocator<JQueryLocator> icon = ref(content, "> span.rf-trn-ico");
    ReferencedLocator<JQueryLocator> label = ref(content, "> span.rf-trn-lbl");

    public TreeNodeModel(JQueryLocator root) {
        super(root);
    }

    public Iterable<TreeNodeModel> getNodes() {
        return new ModelIterable<JQueryLocator, TreeNodeModel>(subnodes.getAllOccurrences(), TreeNodeModel.class);
    }

    public JQueryLocator getNode(int index) {
        return subnodes.getNthOccurence(index);
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
}
