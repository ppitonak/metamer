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
package org.richfaces.tests.metamer.bean.ice;

import com.icesoft.faces.component.tree.IceUserObject;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Managed bean for ice:tree.
 *
 * @author <a href="mailto:ppitonak@redhat.com">Pavol Pitonak</a>
 * @version $Revision$
 */
@ManagedBean(name = "iceTreeBean")
@ViewScoped
public class IceTreeBean implements Serializable {

    private static Logger logger;
    private DefaultTreeModel model;

    /**
     * Initializes the managed bean.
     */
    @PostConstruct
    public void init() {
        logger = LoggerFactory.getLogger(getClass());
        logger.debug("initializing bean " + getClass().getName());

        // create root node with its children expanded
        DefaultMutableTreeNode rootTreeNode = new DefaultMutableTreeNode();
        IceUserObject rootObject = new IceUserObject(rootTreeNode);
        rootObject.setText("Root Node");
        rootObject.setExpanded(true);
        rootTreeNode.setUserObject(rootObject);

        // model is accessed by by the ice:tree component via a getter method
        model = new DefaultTreeModel(rootTreeNode);

        // add some child nodes
        for (int i = 0; i < 3; i++) {
            DefaultMutableTreeNode branchNode = new DefaultMutableTreeNode();
            IceUserObject branchObject = new IceUserObject(branchNode);
            branchObject.setText("node " + i);
            branchNode.setUserObject(branchObject);
            for (int j = 0; j < 5; j++) {
                DefaultMutableTreeNode subbranchNode = new DefaultMutableTreeNode();
                IceUserObject subbranchObject = new IceUserObject(subbranchNode);
                subbranchObject.setText("node " + i + "." + j);
                subbranchNode.setUserObject(subbranchObject);
                branchNode.add(subbranchNode);
            }
            rootTreeNode.add(branchNode);
        }
    }

    public DefaultTreeModel getModel() {
        return model;
    }
}
