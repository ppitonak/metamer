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
package org.richfaces.tests.metamer.model.treeAdaptor;

import java.util.LinkedList;
import java.util.List;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class RecursiveNode extends Node {
    private static final int CHILDREN = 4;
    private static final int LEVELS = 2;
    private static final List<RecursiveNode> EMPTY_LIST = new LinkedList<RecursiveNode>();

    int number;
    List<RecursiveNode> children = null;
    ModelNode model;

    public RecursiveNode(Node parent, boolean nullable, int number) {
        super(parent, nullable);
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public boolean isLeaf() {
        return getRecursionLevel() >= LEVELS + (isOddBranch() ? 0 : 1);
    }

    public List<RecursiveNode> getRecursive() {
        if (isLeaf()) {
            return getEmpty();
        }
        if (children == null) {
            children = createChildren(this, nullable);
        }
        return children;
    }

    private List<RecursiveNode> getEmpty() {
        return nullable ? null : EMPTY_LIST;
    }

    public String getLabel() {
        String parentLabel = (isRoot() ? "" : parent.getLabel() + "-") + "R-";
        String recursionLabel = (isRecursionRoot() ? parentLabel : parent.getLabel() + ".") + number;
        return recursionLabel;
    }

    public ModelNode getModel() {
        if (isLeaf() && !isPreceededByModel()) {
            if (model == null) {
                model = new ModelNode(this, nullable);
            }
            return model;
        }
        return null;
    }

    public int getRecursionLevel() {
        return isRecursionRoot() ? 1 : 1 + ((RecursiveNode) getParent()).getRecursionLevel();
    }

    private boolean isRecursionRoot() {
        if (isRoot()) {
            return true;
        } else {
            return !(parent instanceof RecursiveNode);
        }
    }

    public static List<RecursiveNode> createChildren(Node parent, boolean nullable) {
        List<RecursiveNode> children = new LinkedList<RecursiveNode>();
        for (int i = 0; i < CHILDREN; i++) {
            children.add(new RecursiveNode(parent, nullable, i));
        }
        return children;
    }

    private boolean isPreceededByModel() {
        for (Node predecessor : getPredecessorsFromRoot()) {
            if (predecessor instanceof ModelNode) {
                return true;
            }
        }
        return false;
    }

    private boolean isOddBranch() {
        for (Node predecessor : getPredecessorsFromRoot()) {
            if (predecessor instanceof RecursiveNode) {
                return ((RecursiveNode) predecessor).number % 2 == 1;
            }
        }
        return this.number % 2 == 1;
    }
}