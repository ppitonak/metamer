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
package org.richfaces.tests.metamer.listener;

import java.util.HashSet;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PreDestroyApplicationEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;
import javax.naming.InitialContext;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.core.config.Configuration;
import org.hornetq.core.config.impl.ConfigurationImpl;
import org.hornetq.core.remoting.impl.netty.NettyAcceptorFactory;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;
import org.hornetq.core.server.HornetQServer;
import org.hornetq.core.server.HornetQServers;
import org.hornetq.jms.server.JMSServerManager;
import org.hornetq.jms.server.config.ConnectionFactoryConfiguration;
import org.hornetq.jms.server.config.impl.ConnectionFactoryConfigurationImpl;
import org.hornetq.jms.server.impl.JMSServerManagerImpl;
import org.richfaces.application.ServiceTracker;
import org.richfaces.application.push.PushContextFactory;
import org.richfaces.tests.metamer.bean.VersionBean;
import org.richfaces.tests.metamer.bean.VersionBean.FailToRetrieveInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Listener that initializes HornetQ in Tomcat.
 * 
 * @author Nick Belaevski
 * @author <a href="mailto:ppitonak@redhat.com">Pavol Pitonak</a>
 * @version $Revision$
 */
public class HornetQInitializer implements SystemEventListener {

    private static Logger logger;
    private JMSServerManager serverManager;

    public HornetQInitializer() {
        logger = LoggerFactory.getLogger(getClass());
    }

    public void processEvent(SystemEvent event) throws AbortProcessingException {
        String serverType = null;

        try {
            serverType = VersionBean.getTomcatVersionInfo();
        } catch (FailToRetrieveInfo e) {
            serverType = e.getMessage();
        }

        if (serverType != null && serverType.contains("Tomcat")) {
            try {
                startHornetQ();
            } catch (Exception e) {
                throw new AbortProcessingException(e);
            }

            //force push context initialization so that its PreDestroyApplicationevent listener is added before HornetQ stopper
            ServiceTracker.getService(PushContextFactory.class).getPushContext();

            Application application = FacesContext.getCurrentInstance().getApplication();
            application.subscribeToEvent(PreDestroyApplicationEvent.class, this);
        } else {
            try {
                stopHornetQ();
            } catch (Exception e) {
                throw new AbortProcessingException(e);
            }
        }
    }

    private void stopHornetQ() throws Exception {
        if (serverManager != null) {
            logger.info("stopping HornetQ");
            serverManager.stop();
            serverManager = null;
        }
    }

    private void startHornetQ() throws Exception {
        logger.info("starting HornetQ");

        // create the Configuration and set the properties accordingly
        Configuration configuration = new ConfigurationImpl();
        configuration.setPersistenceEnabled(false);
        configuration.setSecurityEnabled(false);

        TransportConfiguration transpConf = new TransportConfiguration(NettyAcceptorFactory.class.getName());

        HashSet<TransportConfiguration> setTransp = new HashSet<TransportConfiguration>();
        setTransp.add(transpConf);

        configuration.setAcceptorConfigurations(setTransp);

        // create and start the server
        HornetQServer server = HornetQServers.newHornetQServer(configuration);

        serverManager = new JMSServerManagerImpl(server);

        //if you want to use JNDI, simple inject a context here or don't call this method and make sure the JNDI parameters are set.  
        InitialContext context = new InitialContext();
        serverManager.setContext(context);
        serverManager.start();

        ConnectionFactoryConfiguration connectionFactoryConfiguration = new ConnectionFactoryConfigurationImpl("ConnectionFactory",
                new TransportConfiguration(NettyConnectorFactory.class.getName()), (String) null);
        connectionFactoryConfiguration.setUseGlobalPools(false);

        serverManager.createConnectionFactory(false, connectionFactoryConfiguration, "ConnectionFactory");

        serverManager.createTopic(false, "metamer", "/topic/metamer");
    }

    public boolean isListenerForSource(Object source) {
        return true;
    }
}
