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
package org.richfaces.tests.metamer.model.treeAdaptor;

import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public abstract class Node implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Node parent;
    private AtomicReference<Boolean> nullable;
    private Reference<LazyLoadingListener<Node>> lazyLoadingListenerReference = new NodeReference();

    protected Node(Node parent, AtomicReference<Boolean> nullable,
        Reference<LazyLoadingListener<Node>> lazyLoadingListenerReference) {
        this.parent = parent;
        this.nullable = nullable;
        if (lazyLoadingListenerReference != null) {
            this.lazyLoadingListenerReference = lazyLoadingListenerReference;
        }
    }

    public static <T extends Node> T lazyLoadingChecker(T node) {
        return (T) LazyLoadingChecker.wrapInstance(node, node.getLazyLoadingListenerReference());
    }

    public Node getParent() {
        return parent;
    }

    public boolean isRoot() {
        return parent == null;
    }
    
    public AtomicReference<Boolean> getNullable() {
        return nullable;
    }

    public int getLevel() {
        return isRoot() ? 1 : parent.getLevel() + 1;
    }

    public Node getRoot() {
        return isRoot() ? this : parent.getRoot();
    }

    public Iterable<Node> getPredecessorsFromRoot() {
        Deque<Node> list = new LinkedList<Node>();
        list.addFirst(this);

        while (list.getFirst().parent != null) {
            list.addFirst(list.getFirst().parent);
        }

        return list;
    }

    public abstract String getLabel();

    public Reference<LazyLoadingListener<Node>> getLazyLoadingListenerReference() {
        return lazyLoadingListenerReference;
    }

    public void setLazyLoadingListener(Reference<LazyLoadingListener<Node>> lazyLoadingListener) {
        this.lazyLoadingListenerReference = lazyLoadingListener;
    }

    public class NodeReference implements Reference<LazyLoadingListener<Node>> {
        @Override
        public LazyLoadingListener<Node> get() {
            Node root = getRoot();
            if (root == Node.this) {
                return null;
            }
            return getRoot().getLazyLoadingListenerReference().get();
        }
    }
}
