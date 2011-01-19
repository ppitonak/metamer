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
package org.richfaces.tests.metamer.bean;

import java.io.Serializable;
//import java.util.Collection;
//import java.util.EventObject;
//import java.util.HashSet;
//import java.util.LinkedList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

//import org.ajax4jsf.event.PushEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Managed bean for a4j:push.
 * 
 * @author Nick Belaevski, <a href="mailto:ppitonak@redhat.com">Pavol Pitonak</a>
 * @version $Revision$
 */
@ManagedBean(name = "a4jPushEventProcuder")
@ApplicationScoped
public class A4JPushEventProcuder implements Serializable {

    private static final long serialVersionUID = 4532283098337277878L;
    private Logger logger;
//    FIXME example has to be reimplemented because of new implementation of a4j:push
//    private Collection<PushEventListener> registeredListeners = new HashSet<PushEventListener>();

    @PostConstruct
    public void init() {
        logger = LoggerFactory.getLogger(getClass());
    }
//    public void registerListener(PushEventListener listener) {
//        synchronized (registeredListeners) {
//            registeredListeners.add(listener);
//        }
//    }
//
//    public void produceEvent() {
//        Collection<PushEventListener> listeners;
//        synchronized (registeredListeners) {
//            listeners = new LinkedList<PushEventListener>(registeredListeners);
//        }
//        for (PushEventListener listener : listeners) {
//            listener.onEvent(new EventObject(A4JPushBean.class));
//        }
//        logger.debug("push event (listeners: " + listeners.size() + ")");
//    }
}
