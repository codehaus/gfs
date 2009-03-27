/**
 * Copyright 2007 Marcel Overdijk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.codehaus.groovy.grails.plugins.flex;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.groovy.grails.commons.GrailsClassUtils;
import org.codehaus.groovy.grails.commons.GrailsServiceClass;

import flex.messaging.MessageBroker;
import flex.messaging.services.RemotingService;
import flex.messaging.services.remoting.RemotingDestination;

public class FlexUtils {

    private static final String SPRING_FACTORY_ID = "spring";
    private static final String GRAILS_REMOTING_SERVICE_ID = "grails-remoting-service";
    private static final String EXPOSE_PROPERTY = "expose";
    private static final String DESTINATION_PROPERTY = "destination";
    private static final String FLEX_REMOTING = "flex-remoting";
    
    private static final Log LOG = LogFactory.getLog(FlexUtils.class);
    
    public static void addSpringFactory(MessageBroker mb) {
        // add spring factory if it's not yet registered
        if (!mb.getFactories().containsKey(SPRING_FACTORY_ID)) {
            mb.addFactory(SPRING_FACTORY_ID, new SpringFactory());
            LOG.info("SpringFactory added to message broker");
        }
    }
    
    public static RemotingService getGrailsRemotingService(MessageBroker mb) {
        return (RemotingService) mb.getService(GRAILS_REMOTING_SERVICE_ID);
    }

    public static boolean hasFlexRemotingConvention(GrailsServiceClass serviceClass) {
        List exposeList = (List) GrailsClassUtils.getStaticPropertyValue(serviceClass.getClazz(), EXPOSE_PROPERTY);
        if (exposeList != null && exposeList.contains(FLEX_REMOTING)) {
            return true;
        }
        return false;
    }
    
    public static void createRemotingDestination(MessageBroker mb, GrailsServiceClass serviceClass) {
        RemotingService rs = getGrailsRemotingService(mb);
        String destination = getDestination(serviceClass);
        RemotingDestination rd = (RemotingDestination) rs.createDestination(destination);
        rd.setFactory(SPRING_FACTORY_ID);
        rd.setSource(serviceClass.getPropertyName());
        
        // if the service is already started also start the destination
        // this is needed for reloading and creating new Grails services
        if (rs.isStarted()) {
            rd.start();
        }
        
        LOG.info("Flex remoting destination created for " + serviceClass.getShortName());            
    }
    
    private static String getDestination(GrailsServiceClass serviceClass) {
        String destination = (String) GrailsClassUtils.getStaticPropertyValue(serviceClass.getClazz(), DESTINATION_PROPERTY);
        if (StringUtils.isBlank(destination)) {
            destination = serviceClass.getPropertyName();
        }
        return destination;
    }
    
}
