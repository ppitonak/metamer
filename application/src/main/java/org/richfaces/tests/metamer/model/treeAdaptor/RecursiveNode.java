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

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class RecursiveNode extends Node {
    private static final int CHILDREN = 4;
    private static final int LEVELS = 2;
    private static final List<RecursiveNode> EMPTY_LIST = new LinkedList<RecursiveNode>();
    private static final Map<Integer, RecursiveNode> EMPTY_MAP = new HashMap<Integer, RecursiveNode>();

    int number;
    List<RecursiveNode> children = null;
    NodeMap nodeMap = new NodeMap();
    ModelNode model;

    public RecursiveNode() {
        super(null, null, null);
    }

    protected RecursiveNode(Node parent, AtomicReference<Boolean> nullable, int number,
        Reference<LazyLoadingListener<Node>> lazyLoadingListenerReference) {
        super(parent, nullable, lazyLoadingListenerReference);
        this.number = number;
    }

    public static RecursiveNode getInstance(Node parent, AtomicReference<Boolean> nullable, int number,
        Reference<LazyLoadingListener<Node>> lazyLoadingListenerReference) {
        return lazyLoadingChecker(new RecursiveNode(parent, nullable, number, lazyLoadingListenerReference));
    }

    public int getNumber() {
        return number;
    }

    public boolean isLeaf() {
        return getRecursionLevel() >= LEVELS + (isOddBranch() ? 0 : 1);
    }

    public List<RecursiveNode> getRecursiveList() {
        if (isLeaf()) {
            return getEmptyList();
        }
        if (children == null) {
            children = createChildren(this, nullable, null);
        }
        return children;
    }

    public Map<Integer, RecursiveNode> getRecursiveMap() {
        if (isLeaf()) {
            return getEmptyMap();
        }
        return nodeMap;
    }

    private List<RecursiveNode> getEmptyList() {
        return nullable.get() ? null : EMPTY_LIST;
    }

    private Map<Integer, RecursiveNode> getEmptyMap() {
        return nullable.get() ? null : EMPTY_MAP;
    }

    public String getLabel() {
        String parentLabel = (isRoot() ? "" : parent.getLabel() + "-") + "R-";
        String recursionLabel = (isRecursionRoot() ? parentLabel : parent.getLabel() + ".") + number;
        return recursionLabel;
    }

    public ModelNode getModel() {
        if (isLeaf() && !isPreceededByModel()) {
            if (model == null) {
                model = ModelNode.getInstance(this, nullable, null);
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

    public static List<RecursiveNode> createChildren(Node parent, AtomicReference<Boolean> nullable,
        Reference<LazyLoadingListener<Node>> lazyLoadingListenerReference) {
        List<RecursiveNode> children = new LinkedList<RecursiveNode>();
        for (int i = 0; i < CHILDREN; i++) {
            RecursiveNode node = RecursiveNode.getInstance(parent, nullable, i, lazyLoadingListenerReference);
            children.add(node);
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

    @Override
    public String toString() {
        return getLabel();
    }

    public class NodeMap implements Map<Integer, RecursiveNode> {

        @Override
        public int size() {
            return getRecursiveList().size();
        }

        @Override
        public boolean isEmpty() {
            return getRecursiveList().isEmpty();
        }

        @Override
        public boolean containsKey(Object key) {
            throw new UnsupportedOperationException("not implemented");
        }

        @Override
        public boolean containsValue(Object value) {
            throw new UnsupportedOperationException("not implemented");
        }

        @Override
        public RecursiveNode get(Object key) {
            if (key instanceof Integer) {
                return getRecursiveList().get((Integer) key);
            }
            throw new IllegalStateException("there is no value for the key '" + key + "' (type "
                + key.getClass().getName() + ")");
        }

        @Override
        public RecursiveNode put(Integer key, RecursiveNode value) {
            throw new UnsupportedOperationException("not supported");
        }

        @Override
        public RecursiveNode remove(Object key) {
            throw new UnsupportedOperationException("not supported");
        }

        @Override
        public void putAll(Map<? extends Integer, ? extends RecursiveNode> m) {
            throw new UnsupportedOperationException("not supported");
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException("not supported");
        }

        @Override
        public Set<Integer> keySet() {
            HashSet<Integer> set = new HashSet<Integer>();
            for (int i = 0; i < getRecursiveList().size(); i++) {
                set.add(i);
            }
            return set;
        }

        @Override
        public Collection<RecursiveNode> values() {
            return getRecursiveList();
        }

        @Override
        public Set<java.util.Map.Entry<Integer, RecursiveNode>> entrySet() {
            HashSet<Map.Entry<Integer, RecursiveNode>> set = new HashSet<Map.Entry<Integer, RecursiveNode>>();
            int i = 0;
            for (RecursiveNode node : getRecursiveList()) {
                set.add(new MapEntry(i++, node));
            }
            return set;
        }

    }

    public class MapEntry implements Map.Entry<Integer, RecursiveNode> {

        int key;
        RecursiveNode node;

        public MapEntry(int key, RecursiveNode node) {
            this.key = key;
            this.node = node;
        }

        @Override
        public Integer getKey() {
            return key;
        }

        @Override
        public RecursiveNode getValue() {
            return node;
        }

        @Override
        public RecursiveNode setValue(RecursiveNode value) {
            throw new UnsupportedOperationException("not supported");
        }
    }
}