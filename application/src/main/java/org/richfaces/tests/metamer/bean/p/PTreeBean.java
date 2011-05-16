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
package org.richfaces.tests.metamer.bean.p;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Managed bean for PrimeFaces tree.
 *
 * @author <a href="mailto:ppitonak@redhat.com">Pavol Pitonak</a>
 * @version $Revision$
 */
@ManagedBean
@ViewScoped
public class PTreeBean implements Serializable {

    private static Logger logger;
    private TreeNode root;

    /**
     * Initializes the managed bean.
     */
    @PostConstruct
    public void init() {
        logger = LoggerFactory.getLogger(getClass());
        logger.debug("initializing bean " + getClass().getName());

        root = new DefaultTreeNode("Root", null);
        TreeNode node0 = new DefaultTreeNode("Node 0", root);
        TreeNode node1 = new DefaultTreeNode("Node 1", root);
        TreeNode node2 = new DefaultTreeNode("Node 2", root);
        TreeNode node3 = new DefaultTreeNode("Node 3", root);
        
        TreeNode node00 = new DefaultTreeNode("Node 0.0", node0);
        TreeNode node10 = new DefaultTreeNode("Node 1.0", node1);
        TreeNode node20 = new DefaultTreeNode("Node 2.0", node2);
        TreeNode node30 = new DefaultTreeNode("Node 3.0", node3);
        
        TreeNode node01 = new DefaultTreeNode("Node 0.1", node0);
        TreeNode node11 = new DefaultTreeNode("Node 1.1", node1);
        TreeNode node21 = new DefaultTreeNode("Node 2.1", node2);
        TreeNode node31 = new DefaultTreeNode("Node 3.1", node3);
    
        TreeNode node02 = new DefaultTreeNode("Node 0.2", node0);
        TreeNode node12 = new DefaultTreeNode("Node 1.2", node1);
        TreeNode node22 = new DefaultTreeNode("Node 2.2", node2);
        TreeNode node32 = new DefaultTreeNode("Node 3.2", node3);
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }
}
